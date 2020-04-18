/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Interfaces.MyPanelInterface;
import Interfaces.MyWindowManagerInterface;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.KeyStroke;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 *
 * @author Marek
 */
public class MyWindowManager implements MyWindowManagerInterface{
    private TitlePanel titlePanel = null;
    private KonfigView konfigView;
    private MyPanelInterface workingPanel;
    private Boolean result;

    public Boolean getResult() {
        return result;
    }
    
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
        this.konfigView = new KonfigView(konfigView, konfigView.getPanelID());
        workingPanel = panel;
        
        titlePanel = new TitlePanel(title);
        if (workingPanel != null){
            titlePanel.getWorkingPanel().add((JPanel) workingPanel, BorderLayout.CENTER);
            setKonfigViewToChildPanel((JPanel)titlePanel);
        }
        mainWindow.add(titlePanel, BorderLayout.CENTER);
        titlePanel.getPnlBtnPanel().setVisible(this.konfigView.getIsBtnVisible());
        if (konfigView.getIsBtnVisible()){
            titlePanel.getBtnOk().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result = workingPanel.execute();
                    mainWindow.setVisible(!result);
                }
            });
            titlePanel.getBtnCancel().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    workingPanel.rollback();
                    mainWindow.setVisible(false);
                }
            });
        }
        setTraversalKeys(mainWindow);
    }
    
    private void setTraversalKeys(Window mainWindow){
        setTraversalKey(mainWindow, KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, "DOWN");
        setTraversalKey(mainWindow, KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, "UP");
    }
    
    private void setTraversalKey(Window mainWindow, Integer traversal, String key){
        Set set = new HashSet(mainWindow.getFocusTraversalKeys(traversal));
        set.add(KeyStroke.getKeyStroke(key));
        mainWindow.setFocusTraversalKeys(traversal, set);       
    }
    
    @Override
    public <E> void unpackWindow(E object){
        workingPanel.unpack(object);
    }
    
    @Override
    public <E> void unpackWindow(List<E> objectList){
        workingPanel.unpack(objectList);
    }
    
    private void setKonfigViewToChildPanel(JPanel panel){
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel){
                if (((JPanel) component).getComponentCount() > 0){
                    setKonfigViewToChildPanel((JPanel) component);
                }
                if(component instanceof MyPanelInterface){
                    ((MyPanelInterface)component).init(konfigView);
                }
            }
        }
    }
    
    public MyWindowManager(){
        
    }
    
    public void setResizeListener(Window window){
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if ((window instanceof JFrame) && (((JFrame) window).getExtendedState() == JFrame.NORMAL)){
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Integer taskbarHeight = Toolkit.getDefaultToolkit().getScreenInsets(window.getGraphicsConfiguration()).bottom; 

                    Integer screenHeight = window.getSize().height;
                    Integer screenWidth = window.getSize().width;

                    if (screenHeight > screenSize.height){
                        screenHeight = screenSize.height - taskbarHeight;
                    }

                    if (screenWidth > screenSize.width){
                        screenWidth = screenSize.width;
                    }

                    window.setSize(screenWidth, screenHeight); 
                }
            }
        });
    }
}
