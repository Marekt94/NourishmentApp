/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Produkty;
import View.BasicView.BasePanel;

/**
 *
 * @author Marek
 */
public class WagaJednostkiPanel extends BasePanel {
    Produkty product;

    /**
     * Creates new form WagaJednostki
     */
    public WagaJednostkiPanel() {
        initComponents();
    }

    @Override
    public void pack() {
         product.setWagaJednostki((Double) Global.GlobalFun.bind(jTextField1, Double.class));
    }

    @Override
    public <E> void unpack(E object) {
        product = (Produkty) object;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Waga jednostki");
        add(jLabel1);
        add(jTextField1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}