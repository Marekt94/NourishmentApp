/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import View.BasicView.MainDialog;
import Global.GlobalFun;
import View.BasicView.KonfigView;
import Interfaces.MyPanelInterface;
import Global.ORMManager;
import Entities.Produkty;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Marek
 */
public class ListaPotrawPanel extends javax.swing.JPanel implements MyPanelInterface{
    private List<Potrawy> mealList;
    private List<Potrawy> newOrEditedMeals;

    @Override
    public void updateView() {
        GlobalFun.updateTable(mealList, jTable1);
    }

    @Override
    public <E> List<E> getNewOrEditedObjectList() {
        return (List<E>) newOrEditedMeals;
    }

    @Override
    public <E> void unpack(E object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> void unpack(List<E> objectList){
        String[] titleList = null;

        for (E meal : objectList){
            mealList.add((Potrawy) meal);
        } 
        
        GlobalFun.updateTable(mealList, jTable1);
    }
    

    @Override
    public void pack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private KonfigView konfigView;
    
    @Override
    public Boolean execute() {
        ORMManager oRMManager = ORMManager.getOrmManager();
        return oRMManager.addToDB(newOrEditedMeals);
    }
     
    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView);
        return true;
    }

    @Override
    public KonfigView getKonfigView() {
        return konfigView;
    }

    /**
     * Creates new form ListaProduktowView
     */
    public ListaPotrawPanel() {
        initComponents();
        mealList = new ArrayList<Potrawy>();
        newOrEditedMeals = new ArrayList<Potrawy>();
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
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(776, 521));
        setLayout(new java.awt.BorderLayout(5, 5));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (jTable1.getSelectedRow() > -1){
            Potrawy meal;
            konfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
            konfigView.setExtendedState(JFrame.NORMAL);
            
            MainDialog mainWindow = new MainDialog(null, true, konfigView, "Potrawy", new PotrawyView());
            meal = mealList.get(jTable1.getSelectedRow());
            mainWindow.getMyWindowManager().unpackWindow(meal);
            
            mainWindow.setVisible(true);
            
            if (mainWindow.getResult()){
                newOrEditedMeals.add(meal);
            }
            
            GlobalFun.updateTable(mealList, jTable1);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        konfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
        konfigView.setExtendedState(JFrame.NORMAL);
        
        MainDialog mainWindow = new MainDialog(null, true, konfigView, "Potrawy", new PotrawyView());
        Potrawy meal = new Potrawy();
        mainWindow.getMyWindowManager().unpackWindow(meal);
        
        mainWindow.setVisible(true);
        if (mainWindow.getResult()){
            mealList.add(meal);
            newOrEditedMeals.add(meal);
            GlobalFun.updateTable(mealList, jTable1);
        }
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
