/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.ProduktyWPotrawie;
import static Global.GlobalConfig.NULL_SIGN;
import Global.GlobalFun;
import Interfaces.MyPanelInterface;
import View.BasicView.BasePanel;
import View.BasicView.KonfigView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author Marek
 */
public class WagaProduktuPanel extends BasePanel {
    private ProduktyWPotrawie prodWPotr = null;

    /**
     * Creates new form WagaProduktuPanel
     */
    public WagaProduktuPanel() {
        super(null);
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
        lblIlosc = new javax.swing.JLabel();
        edtIlosc = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        lblWagaProduktu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWagaProduktu.setText("Waga/objętość produktu");
        add(lblWagaProduktu);

        edtWagaProdruktu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                edtWagaProdruktuKeyReleased(evt);
            }
        });
        add(edtWagaProdruktu);

        lblIlosc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIlosc.setText("Liczba sztuk/opakowań/jednostek");
        lblIlosc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblIlosc);

        edtIlosc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                edtIloscKeyReleased(evt);
            }
        });
        add(edtIlosc);
    }// </editor-fold>//GEN-END:initComponents

    private void edtWagaProdruktuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtWagaProdruktuKeyReleased
        Double value = (Double) GlobalFun.bind(edtWagaProdruktu, Double.class);
        GlobalFun.bind(evaluateIlosc(value), edtIlosc);
    }//GEN-LAST:event_edtWagaProdruktuKeyReleased

    private void edtIloscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtIloscKeyReleased
        Double ilosc = (Double) GlobalFun.bind(edtIlosc, Double.class);
        GlobalFun.bind(evaluateWaga(ilosc), edtWagaProdruktu);
    }//GEN-LAST:event_edtIloscKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtIlosc;
    private javax.swing.JTextField edtWagaProdruktu;
    private javax.swing.JLabel lblIlosc;
    private javax.swing.JLabel lblWagaProduktu;
    // End of variables declaration//GEN-END:variables

    @Override
    public <E> void unpack(E object) {
        prodWPotr = (ProduktyWPotrawie) object;
        GlobalFun.bind(prodWPotr.getIloscWG(), edtWagaProdruktu);
        if (prodWPotr.getIdProduktu().getWagaJednostki().equals(0.0)){
            edtIlosc.setEnabled(false);
        }
        else{
            edtIlosc.setEnabled(true);
        }
        GlobalFun.bind(evaluateIlosc(prodWPotr.getIloscWG()), edtIlosc);
    }

    @Override
    public void pack() {
        prodWPotr.setIloscWG((Double) GlobalFun.bind(edtWagaProdruktu, Double.class));
    }
    
    private Double evaluateWaga(Double ilosc){
        return ilosc * prodWPotr.getIdProduktu().getWagaJednostki();
    }
    
    private Double evaluateIlosc(Double waga){
        if (prodWPotr.getIdProduktu().getWagaJednostki().equals(0.0)){
            return 0.0;
        } 
        else{
            return waga / prodWPotr.getIdProduktu().getWagaJednostki();   
        }
    }
}
