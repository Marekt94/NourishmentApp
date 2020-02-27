/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.Produkty;
import Entities.ProduktyWPotrawie;
import Global.GlobalFun;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
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

    /**
     * Creates new form PotrawyListView
     */
    public PotrawyListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        JPanel pnlPotrawy = new JPanel();
        JPanel pnlPotrawyButtons = new JPanel();
        JButton btnPotrawyOk = new JButton();
        JButton btnPotrawyCancel = new JButton();
        
        btnPotrawyOk.setText("OK");
        btnPotrawyCancel.setText("Anuluj");
        
        pnlPotrawy.setLayout(new BorderLayout());
        pnlPotrawyButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        pnlPotrawy.setBorder(BorderFactory.createRaisedBevelBorder());
        pnlPotrawyButtons.add(btnPotrawyOk);
        pnlPotrawyButtons.add(btnPotrawyCancel);
        pnlPotrawy.add(pnlPotrawyButtons, BorderLayout.SOUTH);
        pnlPotrawyList = new BaseListPanel(new PotrawyView(), "Potrawa", Potrawy.class);
        pnlPotrawy.add(pnlPotrawyList, BorderLayout.CENTER);
        pnlPotrawy.setPreferredSize(new Dimension(450, 450));
        
        JPanel pnlProdukty = new JPanel();
        JPanel pnlProduktyButtons = new JPanel();
        JButton btnProduktyOk = new JButton();
        JButton btnProduktyCancel = new JButton();
        
        btnProduktyOk.setText("OK");
        btnProduktyCancel.setText("Anuluj");
        
        pnlProdukty.setLayout(new BorderLayout());
        pnlProduktyButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        pnlProdukty.setBorder(BorderFactory.createRaisedBevelBorder());
        pnlProduktyButtons.add(btnProduktyOk);
        pnlProduktyButtons.add(btnProduktyCancel);
        pnlProdukty.add(pnlProduktyButtons, BorderLayout.SOUTH);
        pnlProduktyList = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        pnlProdukty.add(pnlProduktyList, BorderLayout.CENTER);
        pnlProdukty.setPreferredSize(new Dimension(450, 450));
        
        this.add(pnlProdukty,BorderLayout.EAST);
        this.add(pnlPotrawy,BorderLayout.WEST);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        pack();
        
        btnAdd.setText("<<");
        btnEdit.setText(">>");
        
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
        
        btnPotrawyOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlPotrawyList.execute();
                updateView();
            }
        });
        
        btnProduktyOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlProduktyList.execute();
                updateView();
            }
        });
        
        btnPotrawyCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlPotrawyList.rollback();
                updateView();
            }
        });
        
        btnProduktyCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlProduktyList.rollback();
                updateView();
            }
        });
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
                //prodWPotrList = GlobalFun.toList(((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection());
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
    public void updateView() {
        String[] omittedColumns = {"idPotrawy"};
        GlobalFun.updateTable(objectList, tblObjects, omittedColumns);
        pnlPotrawyList.updateView();
        pnlProduktyList.updateView();
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
