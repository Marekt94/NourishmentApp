/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.Produkty;
import Entities.ProduktyWPotrawie;
import Global.GlobalConfig;
import Global.GlobalFun;
import Global.ORMManager;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
import View.BasicView.KonfigView;
import View.BasicView.MainDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Marek
 */
public class PotrawyEditorListView extends BaseListPanel {
    MyListPanelInterface pnlPotrawyList = null;
    MyListPanelInterface pnlProduktyList = null;
    Integer newID = 0;
    Boolean isCurrentlyUpdated = false;

    /**
     * Creates new form PotrawyListView
     */
    public PotrawyEditorListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        omittedColumns.add("idPotrawy");
        
        pnlPotrawyList = new PotrawyListView(new PotrawyView(), "Potrawa", Potrawy.class);
        ((JPanel) pnlPotrawyList).setPreferredSize(new Dimension(500, 450));
        
        pnlProduktyList = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        ((JPanel) pnlProduktyList).setPreferredSize(new Dimension(500, 450));
        
        this.add((JPanel) pnlProduktyList,BorderLayout.EAST);
        this.add((JPanel) pnlPotrawyList,BorderLayout.WEST);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        
        btnAdd.setText("<<");
        
        ((JTable) pnlPotrawyList.getComponentWihtListOfRecords()).getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting()){
                    Potrawy potrawa = pnlPotrawyList.getCurrentObject();
                    if (potrawa != null){
                        objectList = (List<Serializable>) GlobalFun.toList(potrawa.getProduktyWPotrawieCollection());
                    }
                    updateView();
                }
            }
        });
    }

    @Override
    public Boolean init(KonfigView konfigView) {
        newID = konfigView.getPanelID() * 10 + GlobalConfig.POTRAWY_ID;
        pnlPotrawyList.getKonfigView().setPanelID(newID.byteValue());
        newID = konfigView.getPanelID() * 10 + GlobalConfig.PRODUKTY_ID;
        pnlProduktyList.getKonfigView().setPanelID(newID.byteValue());
        return super.init(konfigView); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPreferences() {
        pnlPotrawyList.loadPreferences();
        pnlProduktyList.loadPreferences();
        super.loadPreferences(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void savePreferences() { 
        pnlPotrawyList.savePreferences();
        pnlProduktyList.savePreferences();
        super.savePreferences(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType) {
        if (pnlProduktyList.getCurrentObject() == null){
            JOptionPane.showMessageDialog(this, "Wybierz produkt", "Wybierz produkt", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pnlPotrawyList.getCurrentObject() != null){
            ProduktyWPotrawie prodWPotr = new ProduktyWPotrawie();
            prodWPotr.setIdProduktu(pnlProduktyList.getCurrentObject());
            prodWPotr.setIdPotrawy(pnlPotrawyList.getCurrentObject());
            
            MainDialog wagaProduktuDialog = new MainDialog(null, true, konfigView, "Produkt w potrawie", new WagaProduktuPanel());
            wagaProduktuDialog.unpackWindow(prodWPotr);
            wagaProduktuDialog.setVisible(true);
            
            if (wagaProduktuDialog.getResult()){
                if (((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection() == null){
                    ((Potrawy) pnlPotrawyList.getCurrentObject()).setProduktyWPotrawieCollection(new ArrayList<ProduktyWPotrawie>() );
                }
                ((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection().add(prodWPotr);
                objectList = (List<Serializable>) GlobalFun.toList(((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection());
                newOrEditedObjectList.add((Potrawy) pnlPotrawyList.getCurrentObject());
            }
            updateView();
        }
        else{
            JOptionPane.showMessageDialog(this, "Wybierz potrawę", "Wybierz potrawę", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        if (objectList.get(0) instanceof Produkty){
            pnlProduktyList.unpack(objectList);
        }
        if (objectList.get(0) instanceof Potrawy){
            pnlPotrawyList.unpack(objectList);
        }
    }

    @Override
    public Boolean execute() {
        if (pnlProduktyList.execute()){
            if (pnlPotrawyList.execute()){
                if (super.execute()){
                    pnlProduktyList.unpack();
                    return true;
                }
            }           
        }
        return false;
    }

    @Override
    public void rollback() {
        pnlPotrawyList.rollback();
        pnlProduktyList.rollback();
        super.rollback(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateView() {
        if (!isCurrentlyUpdated){
            isCurrentlyUpdated = true;
            pnlProduktyList.updateView();
            pnlPotrawyList.updateView();
            super.updateView();
            isCurrentlyUpdated = false;
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

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
