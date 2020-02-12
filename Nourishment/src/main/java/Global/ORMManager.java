/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import Entities.Potrawy;
import Entities.PotrawyWDniu;
import Global.GlobalConfig;
import Entities.Produkty;
import Entities.ProduktyWPotrawie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.hibernate.Filter;
import org.hibernate.Query;
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
    
    public List<? extends Serializable> askForObjects(Class myType){
        List<Serializable> list = null;
        Set<Serializable> set = new HashSet<Serializable>();

        session = sessionFactory.openSession();
        session.beginTransaction();
        for (Serializable object : (List<Serializable>) session.createCriteria(myType).list()){
            set.add(object);
        } 
        list = new ArrayList<Serializable>(set);
        session.close();
        return (List<Serializable>) list;
    }
    
    public <E> Boolean addToDB(List<E> list){
        if (session.isOpen()){ session.close();}
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (int i = 0; i < list.size(); i++) {
                session.saveOrUpdate(list.get(i));
                if (i % GlobalConfig.BATCH_SIZE == 0){
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
            session.close();
            return false;      
        }
        return true;
    }
    
    public Boolean disconnect(){
        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
        return true;
    }
    
    public List<? extends Serializable> filterByDate(String dateFrom, String  dateTo){
        List<PotrawyWDniu> list = null;
        Set<PotrawyWDniu> set = new HashSet<PotrawyWDniu>();

        session = sessionFactory.openSession();
        Filter filter = session.enableFilter("dataFilter");
        filter.setParameter("dataFrom", dateFrom);
        filter.setParameter("dataTo", dateTo);
        session.beginTransaction();
        for (PotrawyWDniu object : (List<PotrawyWDniu>) session.createCriteria(PotrawyWDniu.class).list()){
            set.add(object);
        } 
        list = new ArrayList<PotrawyWDniu>(set);
        session.disableFilter("dateFilter");
        session.close();
        return (List<PotrawyWDniu>) list;        
    } 
}
