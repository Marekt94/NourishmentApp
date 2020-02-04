/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.ProduktyWPotrawie;
import Global.GlobalFun;
import Interfaces.MyPanelInterface;
import View.BasicView.KonfigView;
import java.util.List;

/**
 *
 * @author Marek
 */
public class WagaProduktuPanel extends javax.swing.JPanel implements MyPanelInterface {
    private KonfigView konfigView = null;
    private ProduktyWPotrawie prodWPotr = null;
    
    @Override
    public <E> void unpack(List<E> objectList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Creates new form WagaProduktuPanel
     */
    public WagaProduktuPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWagaProduktu = new javax.swing.JLabel();
        edtWagaProdruktu = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(233, 22));
        setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        lblWagaProduktu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWagaProduktu.setText("Waga produktu");
        add(lblWagaProduktu);
        add(edtWagaProdruktu);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtWagaProdruktu;
    private javax.swing.JLabel lblWagaProduktu;
    // End of variables declaration//GEN-END:variables

    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView);
        return true;
    }

    @Override
    public KonfigView getKonfigView() {
        return this.konfigView;
    }

    @Override
    public Boolean execute() {
        pack();
        return true;
    }

    @Override
    public <E> void unpack(E object) {
        prodWPotr = (ProduktyWPotrawie) object;
    }

    @Override
    public void pack() {
        prodWPotr.setIloscWG((Double) GlobalFun.bind(edtWagaProdruktu, Double.class));
    }

    @Override
    public void rollback() {
        
    }
}
