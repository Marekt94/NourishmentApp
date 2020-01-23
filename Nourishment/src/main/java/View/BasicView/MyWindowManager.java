/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Interfaces.MyPanelInterface;
import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.JPanel;

/**
 *
 * @author Marek
 */
public class MyWindowManager {
    private TitlePanel titlePanel = null;
    private KonfigView konfigView;
    private MyPanelInterface workingPanel;  
    
    public TitlePanel getTitlePanlel() {
        return titlePanel;
    }

    public KonfigView getKonfigView() {
        return konfigView;
    }

    public MyPanelInterface getWorkingPanel() {
        return workingPanel;
    }  
    
    public void create(Window mainWindow, KonfigView konfigView, String title, MyPanelInterface panel){
        this.konfigView = new KonfigView(konfigView);
        workingPanel = panel;
        
        titlePanel = new TitlePanel(konfigView, title);
        if (workingPanel != null){
            workingPanel.init(konfigView);
            titlePanel.getjPanel1().add((JPanel) workingPanel);
        }
        mainWindow.add(titlePanel, BorderLayout.CENTER);        
    }
    
    public MyWindowManager(){
        
    }
}
