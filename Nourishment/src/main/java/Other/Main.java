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
import Global.GlobalConfig;
import Global.ORMManager;
import View.LoginPanel;
import View.BasicView.MainDialog;
import View.MainMenuPanel;
import View.BasicView.MainWindow;
import View.ProduktView;
import View.BasicView.TitlePanel;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 *
 * @author Marek
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainDialog connectingDialog = new MainDialog(null, true, new KonfigView(GlobalConfig.LOGIN_PANEL_ID), "Logowanie", new LoginPanel());
        connectingDialog.unpackWindow(ORMManager.getOrmManager().getConfiguration().getProperties());
        connectingDialog.setVisible(true);
        if (!connectingDialog.getResult()){
            System.exit(0);
        }
        KonfigView konfigView = new KonfigView(GlobalConfig.MENU_ID);
        konfigView.setIsBtnVisible(false);
        MainWindow mainWindow = new MainWindow(null, konfigView, "Menu", new MainMenuPanel());
        mainWindow.setVisible(true);
    }
}
