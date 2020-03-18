/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Interfaces.MyPanelInterface;
import View.BasicView.BasePanel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JRadioButton;

/**
 *
 * @author Marek
 */
public class ChoosenColumnsPanel extends BasePanel{
    protected List<JRadioButton> radioButtonsList = null;
    protected List<String> ommitedColums = null;
    protected String rbTextPrefix = "lbl";

    @Override
    public void rollback() {
        super.rollback(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pack() {
        ommitedColums.clear();
        for (JRadioButton rb : radioButtonsList){
            if (!rb.isSelected()){
                ommitedColums.add(rb.getText());
            }
        }
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        addRadioButtons(objectList.get(0));
        ommitedColums = (List<String>) objectList.get(1);
        setRadioButtonsSelected(ommitedColums);
    }

    /**
     * Creates new form FilterPanel
     */
    public ChoosenColumnsPanel() {
        initComponents();
        radioButtonsList = new ArrayList<JRadioButton>();
    }
    
    public <E> void addRadioButtons(E object){
        radioButtonsList.clear();
        for (Field field : ((Class) object).getDeclaredFields()) {
            if (!field.getType().equals(Collection.class)){
                JRadioButton rb = new JRadioButton();
                radioButtonsList.add(rb);
                rb.setName(rbTextPrefix + field.getName());
                rb.setText(field.getName());
                this.add(rb);
            }
        }
    }
    
    public void setRadioButtonsSelected(List<String> omitedColumns){      
        for (JRadioButton rb : radioButtonsList){
            rb.setSelected(true);
            for (String columnName : omitedColumns){
                if (rb.getName().equals(rbTextPrefix + columnName)){
                    rb.setSelected(false);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();

        setLayout(new java.awt.GridLayout(0, 1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    // End of variables declaration//GEN-END:variables
}