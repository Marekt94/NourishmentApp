/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Entities.Potrawy;
import Global.GlobalFun;
import Global.ORMManager;
import Interfaces.MyPanelInterface;
import View.PotrawyView;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author Marek
 */
public class BaseListPanel extends javax.swing.JPanel implements MyPanelInterface{
    private MyPanelInterface detailPanel = null;
    private String detailPanelTitle = null;
    private Class detailEntityClass = null;
    
    protected KonfigView konfigView = null;
    protected KonfigView detailPanelKonfigView = null;
    
    protected List<Serializable> initObjectList = null;
    protected List<Serializable> objectList = null;
    protected List<Serializable> newOrEditedObjectList = null;
    
    
    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView);
        detailPanelKonfigView = new KonfigView(konfigView);
        detailPanelKonfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
        detailPanelKonfigView.setExtendedState(JFrame.NORMAL);
        return true;
    }

    @Override
    public KonfigView getKonfigView() {
        return konfigView;
    }

    @Override
    public Boolean execute() {
        ORMManager oRMManager = ORMManager.getOrmManager();
        return oRMManager.addToDB(newOrEditedObjectList);
    }

    @Override
    public <E> void unpack(E object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        for (E object : objectList){
            this.objectList.add((Serializable) object);
        } 
        GlobalFun.deepListCopy((List<Serializable>) this.objectList, (List<Serializable>) initObjectList);
        
        GlobalFun.updateTable(objectList, tblObjects);
    }

    @Override
    public <E> List<E> getNewOrEditedObjectList() {
        return (List<E>) newOrEditedObjectList;
    }

    @Override
    public void pack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateView() {
        GlobalFun.updateTable(objectList, tblObjects);
    }

    @Override
    public void rollback() {
        newOrEditedObjectList.clear();
        objectList.clear();
        GlobalFun.deepListCopy((List<Serializable>) initObjectList, (List<Serializable>) objectList);
    }

    @Override
    public <E> List<E> getObjectsList() {
        return (List<E>) objectList;
    }

    @Override
    public <E> E getCurrentObject() {
        if (tblObjects.getSelectedRow() > -1){ 
            return (E) objectList.get(tblObjects.getSelectedRow());
        }
        else{
            return null;
        }
    }

    
    public BaseListPanel(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        initComponents();
        this.detailPanel = detailPanel;
        this.detailPanelTitle = detailPanelTitle;
        this.detailEntityClass = detailEntityClass;
        
        initObjectList = new ArrayList<Serializable>();
        objectList = new ArrayList<Serializable>();
        newOrEditedObjectList = new ArrayList<Serializable>();
    }
    
    public JTable getTblObjects(){
        return tblObjects;
    }
    
    public void editObject(MyPanelInterface detailPanel, String title){
        if (tblObjects.getSelectedRow() > -1){
            Serializable object;
            
            MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
            object = objectList.get(tblObjects.getSelectedRow());
            mainWindow.getMyWindowManager().unpackWindow(object);
            
            mainWindow.setVisible(true);
            
            if (mainWindow.getResult()){
                newOrEditedObjectList.add(object);
            }
            
            updateView();
        }        
    }
    
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType){        
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
        mainWindow.getMyWindowManager().unpackWindow(object);
        
        mainWindow.setVisible(true);
        if (mainWindow.getResult()){
            objectList.add(object);
            newOrEditedObjectList.add(object);
            updateView();
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblObjects = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        tblObjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblObjects);

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 300));
        jPanel1.setLayout(new java.awt.GridLayout(20, 0, 5, 5));

        btnEdit.setText("Edytuj");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit);

        btnAdd.setText("Dodaj");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 776, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(5, 5, 5)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editObject(detailPanel, detailPanelTitle);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addObject(detailPanel, detailPanelTitle, detailEntityClass);
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblObjects;
    // End of variables declaration//GEN-END:variables
}
