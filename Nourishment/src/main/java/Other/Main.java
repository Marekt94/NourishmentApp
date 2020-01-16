/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import Other.Produkty;
import View.MainMenuPanel;
import View.MainWindow;
import View.ProduktView;
import View.TitlePanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Marek
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        List<Produkty> produkty = session.createCriteria(Produkty.class).list();
        for (int i = 0; i < produkty.size(); i++) {
            System.out.print(produkty.get(i).getNazwa() + "\n");
        }
        session.close();
        sessionFactory.close();
        KonfigView konfigView = new KonfigView();
        MainWindow mainWindow = new MainWindow(konfigView, "Menu", new MainMenuPanel(konfigView));
    
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
    
}
