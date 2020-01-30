/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Global.GlobalFun;
import View.BasicView.KonfigView;
import Interfaces.MyPanelInterface;
import Entities.Produkty;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;
import java.util.List;
import javax.swing.JTextField;
        
/**
 *
 * @author Marek
 */
public class ProduktView extends javax.swing.JPanel implements MyPanelInterface{
    private Produkty produkt = null;
    private FocusListener focusListener = null;
    private KonfigView konfigView = null;

    @Override
    public <E> E getCurrentObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public <E> List<E> getObjectsList() {
        return null;
    }

    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> List<E> getNewOrEditedObjectList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
    public <E> void unpack(E object) {
        produkt = (Produkty) object;
        GlobalFun.bind(produkt.getBialko(), edtBialko);
        GlobalFun.bind(produkt.getBlonnik(), edtBlonnik);
        GlobalFun.bind(produkt.getCukryProste(), edtCukryProste);
        GlobalFun.bind(produkt.getCukrySuma(), edtCukrySuma);
        GlobalFun.bind(produkt.getCukryZlozone(), edtCukryZlozone);
        GlobalFun.bind(produkt.getId(), edtID);
        GlobalFun.bind(produkt.getKcalNa100g(), edtKcalNa100g);
        GlobalFun.bind(produkt.getNazwa(), edtNazwa);
        GlobalFun.bind(produkt.getSol(), edtSol);
        GlobalFun.bind(produkt.getTluszcz(), edtTluszcz);
        GlobalFun.bind(produkt.getWagaJednostki(), edtWagaJednostki);
    }

    @Override
    public <E> void unpack(List<E> objectList){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pack() {
        produkt.setBialko((Double) GlobalFun.bind(edtBialko, Double.class));
        produkt.setBlonnik((Double) GlobalFun.bind(edtBlonnik, Double.class));
        produkt.setCukryProste((Double) GlobalFun.bind(edtCukryProste, Double.class));
        produkt.setCukrySuma((Double) GlobalFun.bind(edtCukrySuma, Double.class));
        produkt.setCukryZlozone((Double) GlobalFun.bind(edtCukryZlozone, Double.class));
        produkt.setKcalNa100g((Double) GlobalFun.bind(edtKcalNa100g, Double.class));
        produkt.setNazwa((String) GlobalFun.bind(edtNazwa, String.class));
        produkt.setSol((Double) GlobalFun.bind(edtSol, Double.class));
        produkt.setTluszcz((Double) GlobalFun.bind(edtTluszcz, Double.class));
        produkt.setWagaJednostki((Double) GlobalFun.bind(edtWagaJednostki, Double.class));      
    }

    @Override
    public Boolean execute() {
        pack();
        return true;
    }
    
    @Override
    public KonfigView getKonfigView() {
        return konfigView;
    }

    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView);
        return true;
    }

    /**
     * Creates new form ProduktView
     */
    public ProduktView() {
        initComponents();
        focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = (JTextField)e.getSource();
                field.selectAll();              
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField)e.getSource();
                field.select(0, 0);
            }
        };
        edtBialko.addFocusListener(focusListener);
        edtBlonnik.addFocusListener(focusListener);
        edtCukryProste.addFocusListener(focusListener);
        edtCukrySuma.addFocusListener(focusListener);
        edtCukryZlozone.addFocusListener(focusListener);
        edtID.addFocusListener(focusListener);
        edtKcalNa100g.addFocusListener(focusListener);
        edtNazwa.addFocusListener(focusListener);
        edtSol.addFocusListener(focusListener);
        edtTluszcz.addFocusListener(focusListener);
        edtWagaJednostki.addFocusListener(focusListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblID = new javax.swing.JLabel();
        edtID = new javax.swing.JTextField();
        lblNazwa = new javax.swing.JLabel();
        edtNazwa = new javax.swing.JTextField();
        lblKcalNa100g = new javax.swing.JLabel();
        edtKcalNa100g = new javax.swing.JTextField();
        lblWagaJednostki = new javax.swing.JLabel();
        edtWagaJednostki = new javax.swing.JTextField();
        lblBialko = new javax.swing.JLabel();
        edtBialko = new javax.swing.JTextField();
        lblTluszcz = new javax.swing.JLabel();
        edtTluszcz = new javax.swing.JTextField();
        lblCukryProste = new javax.swing.JLabel();
        edtCukryProste = new javax.swing.JTextField();
        lblCukryZlozone = new javax.swing.JLabel();
        edtCukryZlozone = new javax.swing.JTextField();
        lblCukrySuma = new javax.swing.JLabel();
        edtCukrySuma = new javax.swing.JTextField();
        lblBlonnik = new javax.swing.JLabel();
        edtBlonnik = new javax.swing.JTextField();
        lblSol = new javax.swing.JLabel();
        edtSol = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(174, 344));
        setLayout(new java.awt.GridLayout(11, 2, 5, 10));

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblID.setText("ID");
        lblID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblID);

        edtID.setEnabled(false);
        edtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtIDActionPerformed(evt);
            }
        });
        add(edtID);

        lblNazwa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNazwa.setText("Nazwa");
        lblNazwa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblNazwa);

        edtNazwa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtNazwaActionPerformed(evt);
            }
        });
        add(edtNazwa);

        lblKcalNa100g.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKcalNa100g.setText("Kcal na 100g");
        lblKcalNa100g.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblKcalNa100g);

        edtKcalNa100g.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtKcalNa100gActionPerformed(evt);
            }
        });
        add(edtKcalNa100g);

        lblWagaJednostki.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWagaJednostki.setText("Waga jednostki");
        lblWagaJednostki.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblWagaJednostki);
        add(edtWagaJednostki);

        lblBialko.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBialko.setText("Białko");
        lblBialko.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblBialko);

        edtBialko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtBialkoActionPerformed(evt);
            }
        });
        add(edtBialko);

        lblTluszcz.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTluszcz.setText("Tłuszcz");
        lblTluszcz.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblTluszcz);
        add(edtTluszcz);

        lblCukryProste.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCukryProste.setText("Cukry proste");
        lblCukryProste.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblCukryProste);
        add(edtCukryProste);

        lblCukryZlozone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCukryZlozone.setText("Cukry złożone");
        lblCukryZlozone.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblCukryZlozone);
        add(edtCukryZlozone);

        lblCukrySuma.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCukrySuma.setText("Cukry suma");
        lblCukrySuma.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblCukrySuma);
        add(edtCukrySuma);

        lblBlonnik.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBlonnik.setText("Błonnik");
        lblBlonnik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblBlonnik);
        add(edtBlonnik);

        lblSol.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSol.setText("Sól");
        lblSol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblSol);
        add(edtSol);
    }// </editor-fold>//GEN-END:initComponents

    private void edtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtIDActionPerformed

    private void edtKcalNa100gActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtKcalNa100gActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtKcalNa100gActionPerformed

    private void edtNazwaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtNazwaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtNazwaActionPerformed

    private void edtBialkoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtBialkoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtBialkoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtBialko;
    private javax.swing.JTextField edtBlonnik;
    private javax.swing.JTextField edtCukryProste;
    private javax.swing.JTextField edtCukrySuma;
    private javax.swing.JTextField edtCukryZlozone;
    private javax.swing.JTextField edtID;
    private javax.swing.JTextField edtKcalNa100g;
    private javax.swing.JTextField edtNazwa;
    private javax.swing.JTextField edtSol;
    private javax.swing.JTextField edtTluszcz;
    private javax.swing.JTextField edtWagaJednostki;
    private javax.swing.JLabel lblBialko;
    private javax.swing.JLabel lblBlonnik;
    private javax.swing.JLabel lblCukryProste;
    private javax.swing.JLabel lblCukrySuma;
    private javax.swing.JLabel lblCukryZlozone;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKcalNa100g;
    private javax.swing.JLabel lblNazwa;
    private javax.swing.JLabel lblSol;
    private javax.swing.JLabel lblTluszcz;
    private javax.swing.JLabel lblWagaJednostki;
    // End of variables declaration//GEN-END:variables
}
