/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Interfaces.MyPanelInterface;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Marek
 */
public class BasePanel extends javax.swing.JPanel implements MyPanelInterface{
    protected FocusListener focusListener = null;
    protected KonfigView konfigView = null;
    protected MyPanelInterface[] extraPanel = null;

    public MyPanelInterface[] getExtraPanel() {
        return extraPanel;
    }
            
    @Override
    public void loadPreferences() {
        
    }

    @Override
    public void savePreferences() {
        
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        //do eventualnego pokrycia w potomnych
    }
    
    public BasePanel(MyPanelInterface... extraPanel) {
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
        this.extraPanel = extraPanel;
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

    @Override
    public KonfigView getKonfigView() {
        return this.konfigView;
    }

    @Override
    public Boolean init(KonfigView konfigView) {
        this.konfigView = new KonfigView(konfigView, konfigView.getPanelID());
        setFocusListenerToAll(this);
        return true;
    }
    
    private void setFocusListenerToAll(JPanel panel){
        for (Component component : panel.getComponents()){
            if (component.getClass().equals(JPanel.class)){
                setFocusListenerToAll((JPanel) component);
            }
            if (component instanceof JTextField){
                component.addFocusListener(focusListener);
            }
        }        
    }

    @Override
    public <E> void unpack(E object) {
        //do pokrycia w potomnych
    }

    @Override
    public void pack() {
        //do pokrycia w potomnych
    }

    @Override
    public Boolean execute() {
        pack();
        return true;
    }

    @Override
    public void rollback() {
        if (extraPanel != null){
            for (MyPanelInterface pnl : extraPanel){
                pnl.rollback();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
