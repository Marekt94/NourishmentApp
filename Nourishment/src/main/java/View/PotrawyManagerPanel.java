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
import Global.ORMManager;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
import View.BasicView.KonfigView;
import View.BasicView.MainDialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Marek
 */
public class PotrawyManagerPanel extends javax.swing.JPanel implements MyListPanelInterface{
    KonfigView konfigView = null;
    BaseListPanel pnlListaPotraw = null;
    BaseListPanel pnlListaProduktow = null;
    List<ProduktyWPotrawie> prodWPotrList = null;
    Set<Potrawy> newOrEditedList = null;

    @Override
    public <E> E getCurrentObject() {
        if (tblProduktyWPotrawie.getSelectedRow() > -1){
            ProduktyWPotrawie prod = prodWPotrList.get(tblProduktyWPotrawie.getSelectedRow()); 
            return (E) prodWPotrList.get(tblProduktyWPotrawie.getSelectedRow());
        }
        return null;
    }
    
    @Override
    public <E> List<E> getObjectsList() {
        return null;
    }

    @Override
    public void rollback() {
        pnlListaPotraw.rollback();
        pnlListaProduktow.rollback();
    }

    @Override
    public void updateView() {
        String[] omittedColumns = {"idPotrawy"};
        GlobalFun.updateTable(prodWPotrList, tblProduktyWPotrawie, omittedColumns);
    }

    public BaseListPanel getPnlListaPotraw() {
        return pnlListaPotraw;
    }

    public BaseListPanel getPnlListaProduktow() {
        return pnlListaProduktow;
    }

    @Override
    public <E> List<E> getNewOrEditedObjectList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView);
        return true;
    }

    @Override
    public KonfigView getKonfigView() {
        return null;
    }

    @Override
    public Boolean execute() {
        pnlListaPotraw.execute();
        pnlListaProduktow.execute();
        ORMManager ormManager = ORMManager.getOrmManager();
        ormManager.addToDB(new ArrayList<Potrawy>(newOrEditedList));
        return true;
    }

    @Override
    public <E> void unpack(E object) {
        
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        if (objectList.get(0) instanceof Produkty){
            pnlListaProduktow.unpack(objectList);
        }
        if (objectList.get(0) instanceof Potrawy){
            pnlListaPotraw.unpack(objectList);
        }
    }

    @Override
    public void pack() {
        
    }

    /**
     * Creates new form PotrawyManagerPanel
     */
    public PotrawyManagerPanel() {
        initComponents();
        pnlListaPotraw = new BaseListPanel(new PotrawyView(), "Potrawa", Potrawy.class);
        pnlListaProduktow = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        prodWPotrList = new ArrayList<ProduktyWPotrawie>();
        newOrEditedList = new HashSet<Potrawy>();
        
        
        pnlPotrawy.add(pnlListaPotraw);
        pnlProdukty.add(pnlListaProduktow);
        
        pnlListaPotraw.getTblObjects().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting()){
                    Potrawy potrawa = pnlListaPotraw.getCurrentObject();
                    if (potrawa != null){
                        prodWPotrList = GlobalFun.toList(potrawa.getProduktyWPotrawieCollection());
                    }
                    updateView();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPotrawy = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnPotrawyOK = new javax.swing.JButton();
        btnPotrawyAnuluj = new javax.swing.JButton();
        pnlProduktyWPotrawie = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduktyWPotrawie = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnAddProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        pnlProdukty = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnProduktyOK = new javax.swing.JButton();
        btnProduktyAnuluj = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0, 10, 5));

        pnlPotrawy.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnPotrawyOK.setText("OK");
        btnPotrawyOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotrawyOKActionPerformed(evt);
            }
        });
        jPanel3.add(btnPotrawyOK);

        btnPotrawyAnuluj.setText("Anuluj");
        btnPotrawyAnuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotrawyAnulujActionPerformed(evt);
            }
        });
        jPanel3.add(btnPotrawyAnuluj);

        pnlPotrawy.add(jPanel3, java.awt.BorderLayout.SOUTH);

        add(pnlPotrawy);

        pnlProduktyWPotrawie.setLayout(new java.awt.BorderLayout(5, 0));

        tblProduktyWPotrawie.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblProduktyWPotrawie);

        pnlProduktyWPotrawie.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(100, 300));
        jPanel4.setLayout(new java.awt.GridLayout(20, 0, 5, 5));

        btnAddProduct.setText("<<");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });
        jPanel4.add(btnAddProduct);

        btnDeleteProduct.setText(">>");
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });
        jPanel4.add(btnDeleteProduct);

        pnlProduktyWPotrawie.add(jPanel4, java.awt.BorderLayout.LINE_END);

        add(pnlProduktyWPotrawie);

        pnlProdukty.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnProduktyOK.setText("OK");
        btnProduktyOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduktyOKActionPerformed(evt);
            }
        });
        jPanel6.add(btnProduktyOK);

        btnProduktyAnuluj.setText("Anuluj");
        btnProduktyAnuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduktyAnulujActionPerformed(evt);
            }
        });
        jPanel6.add(btnProduktyAnuluj);

        pnlProdukty.add(jPanel6, java.awt.BorderLayout.SOUTH);

        add(pnlProdukty);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPotrawyOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotrawyOKActionPerformed
        pnlListaPotraw.execute();
        pnlListaPotraw.updateView();
    }//GEN-LAST:event_btnPotrawyOKActionPerformed

    private void btnProduktyOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduktyOKActionPerformed
        pnlListaProduktow.execute();
        pnlListaProduktow.updateView();
    }//GEN-LAST:event_btnProduktyOKActionPerformed

    private void btnPotrawyAnulujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotrawyAnulujActionPerformed
        pnlListaPotraw.rollback();
        pnlListaPotraw.updateView();
    }//GEN-LAST:event_btnPotrawyAnulujActionPerformed

    private void btnProduktyAnulujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduktyAnulujActionPerformed
        pnlListaProduktow.rollback();
        pnlListaProduktow.updateView(); 
    }//GEN-LAST:event_btnProduktyAnulujActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        if (pnlListaPotraw.getCurrentObject() != null){
            ProduktyWPotrawie prodWPotr = new ProduktyWPotrawie();
            prodWPotr.setIdProduktu(pnlListaProduktow.getCurrentObject());
            prodWPotr.setIdPotrawy(pnlListaPotraw.getCurrentObject());
            MainDialog wagaProduktuDialog = new MainDialog(null, true, konfigView, "Produkt w potrawie", new WagaProduktuPanel());
            wagaProduktuDialog.getMyWindowManager().unpackWindow(prodWPotr);
            wagaProduktuDialog.setVisible(true);
            if (wagaProduktuDialog.getResult()){
                if (((Potrawy) pnlListaPotraw.getCurrentObject()).getProduktyWPotrawieCollection() == null){
                    ((Potrawy) pnlListaPotraw.getCurrentObject()).setProduktyWPotrawieCollection(new ArrayList<ProduktyWPotrawie>() );
                }
                ((Potrawy) pnlListaPotraw.getCurrentObject()).getProduktyWPotrawieCollection().add(prodWPotr);
                prodWPotrList = GlobalFun.toList(((Potrawy) pnlListaPotraw.getCurrentObject()).getProduktyWPotrawieCollection());
                newOrEditedList.add((Potrawy) pnlListaPotraw.getCurrentObject());
            }
            updateView();
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        if (tblProduktyWPotrawie.getSelectedRow() > -1){
                if (pnlListaPotraw.getCurrentObject() != null){
                    ProduktyWPotrawie prod = getCurrentObject();
                    ((Potrawy) pnlListaPotraw.getCurrentObject()).getProduktyWPotrawieCollection().remove(prod);
                    prodWPotrList.remove(prod);
                    updateView();
                }
        }
    }//GEN-LAST:event_btnDeleteProductActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnPotrawyAnuluj;
    private javax.swing.JButton btnPotrawyOK;
    private javax.swing.JButton btnProduktyAnuluj;
    private javax.swing.JButton btnProduktyOK;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlPotrawy;
    private javax.swing.JPanel pnlProdukty;
    private javax.swing.JPanel pnlProduktyWPotrawie;
    private javax.swing.JTable tblProduktyWPotrawie;
    // End of variables declaration//GEN-END:variables

}
