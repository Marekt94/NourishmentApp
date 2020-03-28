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
import Other.MyComparator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        configuration = new Configuration(); 
        configuration.configure();
    }
    
    public Boolean connect(Properties properties){
        try {
            configuration.setProperties(properties);
            configuration.setProperty("hibernate.jdbc.batch_size", GlobalConfig.BATCH_SIZE.toString());
            
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
        return askForObjects(myType, "id", true);
    }
    
    public List<? extends Serializable> askForObjects(Class myType, String sortedColumn, Boolean ascending){
        List<Serializable> list = null;
        Set<Serializable> set = new HashSet<Serializable>();

        session = sessionFactory.openSession();
        session.beginTransaction();
        for (Serializable object : (List<Serializable>) session.createCriteria(myType).list()){
            set.add(object);
        } 
        list = new ArrayList<Serializable>(set);
        session.close();
        if (!sortedColumn.equals("")){
            list.sort(new MyComparator(sortedColumn, myType, ascending));
        }
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
    
    public <E> Boolean deleteFromDB(List<E> list){
        if (session.isOpen()){ session.close();}
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (int i = 0; i < list.size(); i++) {
                session.delete(list.get(i));
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
    
    public List<? extends Serializable> filterByDate(Class myType, String dateFrom, String  dateTo){
        List<Serializable> list = null;
        Set<Serializable> set = new HashSet<Serializable>();
        String filterName = null;
        
        if (dateFrom.isEmpty() && dateTo.isEmpty()){
          list = (List<Serializable>) askForObjects(myType); 
          return list;
        }
        else{
            session = sessionFactory.openSession();
            if (!dateFrom.isEmpty() && !dateTo.isEmpty()){
                filterName = "dataFilterBoth";
                Filter filter = session.enableFilter(filterName);
                filter.setParameter("dataFrom", dateFrom);
                filter.setParameter("dataTo", dateTo);
            }
            else if(!dateFrom.isEmpty() && dateTo.isEmpty()){
                filterName = "dataFilterFrom";
                Filter filter = session.enableFilter(filterName);
                filter.setParameter("dataFrom", dateFrom);          
            }
            else{
                filterName = "dataFilterTo";
                Filter filter = session.enableFilter(filterName);
                filter.setParameter("dataTo", dateTo);            
            }
        }
        session.beginTransaction();
        list = session.createCriteria(myType).list();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        list = new ArrayList<Serializable>(set);
        session.disableFilter(filterName);
        session.close();
        return list;        
    } 
}
