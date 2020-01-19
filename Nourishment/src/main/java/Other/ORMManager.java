/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        configuration.configure();
        
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            return false;
        }
        
        session = sessionFactory.openSession();
//        session.beginTransaction();
        
//        List<Produkty> produkty = session.createCriteria(Produkty.class).list();
//        for (int i = 0; i < produkty.size(); i++) {
//            System.out.print(produkty.get(i).getNazwa() + "\n");
//        }
//        session.close();
//        sessionFactory.close();
        return true;
    } 
    
    public List<Produkty> askForProducts(){
        List<Produkty> productsList = null;
        
        session.beginTransaction();
        productsList = session.createCriteria(Produkty.class).list();
        
        return productsList;
    }
    
    public Boolean disconnect(){
        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
        return true;
    }
}
