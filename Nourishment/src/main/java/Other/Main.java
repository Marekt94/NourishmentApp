/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import View.BasicView.KonfigView;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import Entities.Produkty;
import View.LoginPanel;
import View.BasicView.MainDialog;
import View.MainMenuPanel;
import View.BasicView.MainWindow;
import View.ProduktView;
import View.BasicView.TitlePanel;
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
        MainWindow mainWindow = new MainWindow(new KonfigView(), "Menu", new MainMenuPanel());
        mainWindow.setVisible(true);
        MainDialog connectinDialog = new MainDialog(null, true, new KonfigView(), "Logowanie do DB", new LoginPanel());
        connectinDialog.setVisible(true);
        if (connectinDialog.getResult() == false){
            System.exit(0);
        }
    }
    
}
