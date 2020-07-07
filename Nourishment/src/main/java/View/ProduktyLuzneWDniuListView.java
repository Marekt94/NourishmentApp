/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Produkty;
import Entities.ProduktyLuzneWDniu;
import Entities.ProduktyWPotrawie;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
import View.BasicView.MainDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Marek
 */
public class ProduktyLuzneWDniuListView extends BaseListPanel {
    MyListPanelInterface pnlProduktyList;
    
    public ProduktyLuzneWDniuListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        pnlProduktyList = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        ((JPanel) pnlProduktyList).setPreferredSize(new Dimension(500, 450));
        this.add((JPanel) pnlProduktyList, BorderLayout.EAST);
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        if (!objectList.isEmpty() && (objectList.get(0) instanceof Produkty)){
            pnlProduktyList.unpack(objectList);
        }
        else{
            super.unpack(objectList); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType) {
        if (pnlProduktyList.getCurrentObject() == null){
            JOptionPane.showMessageDialog(this, "Wybierz produkt", "Wybierz produkt", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ProduktyLuzneWDniu prodLuzem = new ProduktyLuzneWDniu();
        prodLuzem.setProdukt(pnlProduktyList.getCurrentObject());
            
        MainDialog wagaProduktuDialog = new MainDialog(null, true, konfigView, "Produkt luzem", new WagaProduktuLuzemPanel());
        wagaProduktuDialog.unpackWindow(prodLuzem);
        wagaProduktuDialog.setVisible(true);
            
        if (wagaProduktuDialog.getResult()){
            addObject(prodLuzem);
        }
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}