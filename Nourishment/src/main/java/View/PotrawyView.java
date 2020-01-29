/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
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
public class PotrawyView extends javax.swing.JPanel implements MyPanelInterface{
    private Potrawy meal = null;
    private FocusListener focusListener = null;
    private KonfigView konfigView = null;
    
    @Override
    public <E> List<E> getObjectsList() {
        return null;
    }

    @Override
    public void rollback() {

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
        meal = (Potrawy) object;
        GlobalFun.bind(meal.getId(), edtID);
        GlobalFun.bind(meal.getNazwa(), edtNazwa);
    }

    @Override
    public <E> void unpack(List<E> objectList){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pack() {
        meal.setNazwa((String) GlobalFun.bind(edtNazwa, String.class));
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
    public PotrawyView() {
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
        edtID.addFocusListener(focusListener);
        edtNazwa.addFocusListener(focusListener);
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

        setPreferredSize(new java.awt.Dimension(174, 51));
        setLayout(new java.awt.GridLayout(2, 2, 5, 10));

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
    }// </editor-fold>//GEN-END:initComponents

    private void edtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtIDActionPerformed

    private void edtNazwaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtNazwaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtNazwaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtID;
    private javax.swing.JTextField edtNazwa;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNazwa;
    // End of variables declaration//GEN-END:variables
}
