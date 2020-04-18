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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Marek
 */
public class PotrawyListView extends BaseListPanel {
    BaseListPanel pnlPotrawyList = null;
    BaseListPanel pnlProduktyList = null;
    Integer newID = 0;

    /**
     * Creates new form PotrawyListView
     */
    public PotrawyListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        omittedColumns.add("idPotrawy");
        
        pnlPotrawyList = new BaseListPanel(new PotrawyView(), "Potrawa", Potrawy.class);
        pnlPotrawyList.setPreferredSize(new Dimension(500, 450));
        
        pnlProduktyList = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        pnlProduktyList.setPreferredSize(new Dimension(500, 450));
        
        this.add(pnlProduktyList,BorderLayout.EAST);
        this.add(pnlPotrawyList,BorderLayout.WEST);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        
        btnAdd.setText("<<");
        
        pnlPotrawyList.getTblObjects().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
        if (pnlPotrawyList.getCurrentObject() != null){
            ProduktyWPotrawie prodWPotr = new ProduktyWPotrawie();
            prodWPotr.setIdProduktu(pnlProduktyList.getCurrentObject());
            prodWPotr.setIdPotrawy(pnlPotrawyList.getCurrentObject());
            
            MainDialog wagaProduktuDialog = new MainDialog(null, true, konfigView, "Produkt w potrawie", new WagaProduktuPanel());
            wagaProduktuDialog.getMyWindowManager().unpackWindow(prodWPotr);
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
                return super.execute();
            }           
        }
        return false;
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
