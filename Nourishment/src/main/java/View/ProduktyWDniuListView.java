/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Global.GlobalFun;
import Global.ORMManager;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
import View.BasicView.FilterPanel;
import View.BasicView.MainDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marek
 */
public class ProduktyWDniuListView extends BaseListPanel {
    List<Potrawy> potrawyList = null;

    /**
     * Creates new form ProduktyWDniuListView
     */
    public ProduktyWDniuListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        ORMManager ormManager = ORMManager.getOrmManager();
        JPanel filterPanel = new JPanel();
        FilterPanel filter = new FilterPanel();
        filterPanel.setLayout(new BorderLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Opcje"));
        filterPanel.add(filter,BorderLayout.WEST);
        filter.btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unpack(ORMManager.getOrmManager().filterByDate(filter.getDataFrom(), filter.getDataTo()));
            }
        });
        this.add(filterPanel,BorderLayout.NORTH);
        
        JButton printButton = new JButton("Drukuj");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("");
            }
        });
        addButton(printButton);
        potrawyList = (List<Potrawy>) ormManager.askForObjects(Potrawy.class);
    }

    @Override
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType) {
        MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
        Serializable object = null;
        try {
            try {
                object = (Serializable) objectType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainWindow.getMyWindowManager().unpackWindow(potrawyList);
        mainWindow.getMyWindowManager().unpackWindow(object);
        
        mainWindow.setVisible(true);
        if (mainWindow.getResult()){
            objectList.add(object);
            newOrEditedObjectList.add(object);
            updateView();
        } 
    }

    @Override
    public void editObject(MyPanelInterface detailPanel, String title) {
        if (tblObjects.getSelectedRow() > -1){
            Serializable object;
            
            MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
            object = objectList.get(tblObjects.getSelectedRow());
            mainWindow.getMyWindowManager().unpackWindow(potrawyList);
            mainWindow.getMyWindowManager().unpackWindow(object);
            
            mainWindow.setVisible(true);
            
            if (mainWindow.getResult()){
                newOrEditedObjectList.add(object);
            }
            
            updateView();
        }
    }
    
    @Override
    public void updateView() {
        String[] ommitedColumns = {"mnoznikSniadanie", "mnoznikDrugieSniadanie",
                                   "mnoznikObiad", "mnoznikKolacja",
                                   "mnoznikPodwieczorek", "mnoznikLunch",
                                   "czy5dni"};
        GlobalFun.updateTable(objectList, tblObjects, ommitedColumns);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
