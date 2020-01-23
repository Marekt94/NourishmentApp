/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import Global.GlobalConfig;
import Entities.Produkty;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.SerializationHelper;

/**
 *
 * @author Marek
 */
public class ORMManager {
    private static ORMManager ormManager;
    private SessionFactory sessionFactory;
    private Configuration configuration;
    private Session session;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static ORMManager getOrmManager() {
        if (ormManager == null){
            ormManager = new ORMManager();
        }
        return ormManager;
    }
    
    private ORMManager(){
        
        
    }
    
    public Boolean connect(String username, String password){
        configuration = new Configuration();
        if (username.isEmpty()){
            configuration.setProperty("hibernate.connection.username", "sysdba");
        }
        else{
            configuration.setProperty("hibernate.connection.username", username);
        }
        if (password.isEmpty()){
            configuration.setProperty("hibernate.connection.password", "masterkey");
        }
        else{
            configuration.setProperty("hibernate.connection.password", password);
        }
        configuration.setProperty("hibernate.jdbc.batch_size", GlobalConfig.BATCH_SIZE.toString());
        
        try {
            configuration.configure();
            
            sessionFactory = configuration.buildSessionFactory();
            
            session = sessionFactory.openSession();
            session.close();
        } catch (Exception e){
          e.printStackTrace();
          return false;      
        }
        return true;
    } 
    
    public List<Produkty> askForProducts(){
        List<Produkty> productsList = null;
        
        session = sessionFactory.openSession();
        session.beginTransaction();
        productsList = session.createCriteria(Produkty.class).list();
        session.close();
        return productsList;
    }
    
    public Boolean addProductsList(List<Produkty> productsList){
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (int i = 0; i < productsList.size(); i++) {
                session.saveOrUpdate(productsList.get(i));
                if (i % GlobalConfig.BATCH_SIZE == 0){
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
            session.close();
        } catch (Exception e){
            return false;      
        }
        return true;
    }
    
    public Boolean disconnect(){
        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
        return true;
    }
}