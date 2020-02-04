/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import View.BasicView.MainDialog;
import View.BasicView.KonfigView;
import Interfaces.MyPanelInterface;
import Global.ORMManager;
import Entities.Produkty;
import View.BasicView.BaseListPanel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Marek
 */
public class MainMenuPanel extends javax.swing.JPanel implements MyPanelInterface{
    private KonfigView konfigView;
    private ORMManager ormManager;
    
    @Override
    public <E> void unpack(List<E> objectList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public <E> void unpack(E object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * Creates new form MainMenuPanel
     */
    public MainMenuPanel() {
        initComponents();
        ormManager = ORMManager.getOrmManager();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnProdukty = new javax.swing.JButton();
        btnPotrawy = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(132, 58));

        btnProdukty.setText("Produkty");
        btnProdukty.setMaximumSize(new java.awt.Dimension(100, 21));
        btnProdukty.setMinimumSize(new java.awt.Dimension(100, 21));
        btnProdukty.setPreferredSize(new java.awt.Dimension(100, 21));
        btnProdukty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduktyActionPerformed(evt);
            }
        });
        add(btnProdukty);

        btnPotrawy.setText("Potrawy");
        btnPotrawy.setMaximumSize(new java.awt.Dimension(100, 21));
        btnPotrawy.setMinimumSize(new java.awt.Dimension(100, 21));
        btnPotrawy.setPreferredSize(new java.awt.Dimension(100, 21));
        btnPotrawy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotrawyActionPerformed(evt);
            }
        });
        add(btnPotrawy);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProduktyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduktyActionPerformed
        konfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
        konfigView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BaseListPanel listaProduktowPanel = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        MainDialog mainWindow = new MainDialog(null, true, konfigView, "Produkty", listaProduktowPanel);        
        mainWindow.getMyWindowManager().unpackWindow(ormManager.askForProducts());
        
        mainWindow.setVisible(true);
    }//GEN-LAST:event_btnProduktyActionPerformed

    private void btnPotrawyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotrawyActionPerformed
        konfigView.setDefaultOperationOnClose(WindowConstants.HIDE_ON_CLOSE);
        konfigView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        MainDialog mainDialog = new MainDialog(null, true, konfigView, "Zarządzanie potrawami", new PotrawyManagerPanel());
        mainDialog.getMyWindowManager().unpackWindow(ormManager.askForProducts());
        mainDialog.getMyWindowManager().unpackWindow(ormManager.askForPotrawy());
        
        mainDialog.setVisible(true);
    }//GEN-LAST:event_btnPotrawyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPotrawy;
    private javax.swing.JButton btnProdukty;
    // End of variables declaration//GEN-END:variables
}
