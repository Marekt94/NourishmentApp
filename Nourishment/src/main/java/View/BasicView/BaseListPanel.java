/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Entities.Potrawy;
import Global.GlobalFun;
import Global.ORMManager;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPanelInterface;
import Other.AfterClickSorter;
import Other.AfterClickSorterModel;
import Other.PreferencesManager;
import View.ChoosenColumnsPanel;
import View.PotrawyView;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author Marek
 */
public class BaseListPanel extends javax.swing.JPanel implements MyListPanelInterface{
    private MyPanelInterface detailPanel = null;
    private String detailPanelTitle = null;
    private Class detailEntityClass = null;
    private AfterClickSorterModel sorterModel = null;
    
    protected KonfigView konfigView = null;
    protected KonfigView detailPanelKonfigView = null;
    
    protected List<Serializable> initObjectList = null;
    protected List<Serializable> objectList = null;
    protected List<Serializable> newOrEditedObjectList = null;
    protected List<Serializable> objectToDeleteList = null;
    protected List<String> omittedColumns = null;
    
    protected String columnPrefix = "column";

    @Override
    public void savePreferences() {
        if (konfigView != null){
            String [] prefixes = {konfigView.getPanelID().toString(), columnPrefix};
            PreferencesManager.saveOmittedColumns(prefixes, this.getClass(), omittedColumns);
        }
    }

    @Override
    public void addButton(JButton button, Integer keyboardKey) {
        GridLayout panelLayout = (GridLayout) jPanel2.getLayout();
        panelLayout.setRows(panelLayout.getRows() + 1);
        jPanel2.add(button);
        if (keyboardKey != null){
            button.setMnemonic(keyboardKey);
        }
    }
    
    
    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView, konfigView.getPanelID());
        detailPanelKonfigView = new KonfigView(konfigView, konfigView.getPanelID());
        detailPanelKonfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
        detailPanelKonfigView.setExtendedState(JFrame.NORMAL); 
        return true;
    }
    
    @Override
    public void loadPreferences(){
        String [] prefixes = {konfigView.getPanelID().toString(), columnPrefix};
        PreferencesManager.loadOmittedColumns(prefixes, this.getClass(),omittedColumns);
        updateView();
    }

    @Override
    public KonfigView getKonfigView() {
        return konfigView;
    }

    @Override
    public Boolean execute() {
        Boolean result;
        ORMManager oRMManager = ORMManager.getOrmManager();
        result = oRMManager.addToDB(newOrEditedObjectList);
        result = oRMManager.deleteFromDB(objectToDeleteList) && result;
        initObjectList.clear();
        if (result){
            GlobalFun.deepListCopy(objectList, initObjectList);
        }
        newOrEditedObjectList.clear();
        objectToDeleteList.clear();
        return result;
    }

    @Override
    public <E> void unpack(E object) {
      
    }

    @Override
    public void unpack() {
        unpack(ORMManager.getOrmManager().askForObjects(detailEntityClass));
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        this.objectList.clear();
        initObjectList.clear();
        for (E object : objectList){
            this.objectList.add((Serializable) object);
        } 
        GlobalFun.deepListCopy((List<Serializable>) this.objectList, (List<Serializable>) initObjectList);
        
        updateView();
    }

    @Override
    public <E> List<E> getNewOrEditedObjectList() {
        return (List<E>) newOrEditedObjectList;
    }

    @Override
    public void pack() {
        savePreferences();
    }

    @Override
    public void updateView() {
        Integer currentRow = tblObjects.getSelectedRow();
        GlobalFun.updateTable(objectList, tblObjects, omittedColumns);
        if (currentRow < tblObjects.getRowCount()){
            tblObjects.getSelectionModel().setSelectionInterval(currentRow, currentRow);
        } 
        else{
            tblObjects.getSelectionModel().setSelectionInterval(currentRow - 1, currentRow - 1);
        }
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
        objectToDeleteList = new ArrayList<Serializable>();
        omittedColumns = new ArrayList<String>();
        
        omittedColumns.add("serialVersionUID");
        
        sorterModel = new AfterClickSorterModel();
        sorterModel.setAscending(false);
        sorterModel.setList(objectList);
        sorterModel.setManagerPanel(this);
        sorterModel.setTable(tblObjects);
        sorterModel.setObjectType(detailEntityClass);
        tblObjects.getTableHeader().addMouseListener(new AfterClickSorter(sorterModel));
        
        btnEdit.setMnemonic(KeyEvent.VK_E);
        btnAdd.setMnemonic(KeyEvent.VK_D);
        btnDelete.setMnemonic(KeyEvent.VK_U);
        btnWybierzKolumny.setMnemonic(KeyEvent.VK_W);
        btnApply.setMnemonic(KeyEvent.VK_Z);
        btnUndo.setMnemonic(KeyEvent.VK_C);
        
        tblObjects.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me){
                if (me.getClickCount() == 2){
                    editObject(detailPanel, detailPanelTitle);
                }
            }
        });
    }
    
    @Override
    public JComponent getComponentWihtListOfRecords(){
        return tblObjects;
    }
    
    @Override
    public void editObject(MyPanelInterface detailPanel, String title){
        if (tblObjects.getSelectedRow() > -1){
            Serializable object;
            
            MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
            object = objectList.get(tblObjects.getSelectedRow());
            mainWindow.unpackWindow(object);
            
            mainWindow.setVisible(true);
            
            if (mainWindow.getResult()){
                newOrEditedObjectList.add(object);
            }
            
            updateView();
        }
        else{
            JOptionPane.showMessageDialog(this, "Wybierz rekord", "Wybierz rekord", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    @Override
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
        mainWindow.unpackWindow(object);
        
        mainWindow.setVisible(true);
        if (mainWindow.getResult()){
            addObject(object);
        }        
    }
    
    @Override
    public void deleteObject(){
        if (tblObjects.getSelectedRow() > -1){
            Serializable object;
            
            object = objectList.get(tblObjects.getSelectedRow());           
            objectToDeleteList.add(object);
            objectList.remove(tblObjects.getSelectedRow());
            
            updateView();
        } 
    }
    
    @Override
    public <E> void addObject(E object){
        objectList.add((Serializable) object);
        newOrEditedObjectList.add((Serializable) object);
        updateView();        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnWybierzKolumny = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblObjects = new javax.swing.JTable(){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        jPanel5 = new javax.swing.JPanel();
        btnApply = new javax.swing.JButton();
        btnUndo = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 450));
        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(135, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        btnEdit.setText("Edytuj");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel2.add(btnEdit);

        btnAdd.setText("Dodaj");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd);

        btnDelete.setText("Usuń");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btnDelete);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 5, 5));

        btnWybierzKolumny.setText("Wybierz kolumny");
        btnWybierzKolumny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWybierzKolumnyActionPerformed(evt);
            }
        });
        jPanel4.add(btnWybierzKolumny);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel1, java.awt.BorderLayout.EAST);

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

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanel5.setLayout(flowLayout1);

        btnApply.setText("Zastosuj");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        jPanel5.add(btnApply);

        btnUndo.setText("Cofnij wprowadzone zmiany");
        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });
        jPanel5.add(btnUndo);

        add(jPanel5, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editObject(detailPanel, detailPanelTitle);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addObject(detailPanel, detailPanelTitle, detailEntityClass);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteObject();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnWybierzKolumnyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWybierzKolumnyActionPerformed
        List<Object> list = new ArrayList<>();
        list.add(detailEntityClass);
        list.add(omittedColumns);
        MainDialog mainDialog = new MainDialog(null, true, konfigView, "Widoczne kolumny", new ChoosenColumnsPanel());
        mainDialog.unpackWindow(list);
        mainDialog.setVisible(true);
        updateView();
    }//GEN-LAST:event_btnWybierzKolumnyActionPerformed

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        execute();
        updateView();
    }//GEN-LAST:event_btnApplyActionPerformed

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        rollback();
        updateView();
    }//GEN-LAST:event_btnUndoActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnAdd;
    protected javax.swing.JButton btnApply;
    protected javax.swing.JButton btnDelete;
    protected javax.swing.JButton btnEdit;
    protected javax.swing.JButton btnUndo;
    protected javax.swing.JButton btnWybierzKolumny;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel4;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable tblObjects;
    // End of variables declaration//GEN-END:variables
}
