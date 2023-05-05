/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Other.ListProductRecord;
import View.BasicView.BasePanel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author komputer1
 */
public class DokumentEditorPanel extends BasePanel {
    ListProductRecord list;

    /**
     * Creates new form DokumentEditorPanel
     */
    public DokumentEditorPanel() {
        super(null);
        initComponents();
    }
    
    @Override
    public <E> void unpack(List<E> objectList) {
        super.unpack(objectList);
        list = (ListProductRecord) objectList;
        list.sortByName();           
        List<String> productStringList = list.positionsToStringList();
        jTextArea1.setText(String.join("\n", productStringList));
    }  

    @Override
    public void pack() {
        list.stringListToPositions(jTextArea1.getText().split("\n"));
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(480, 640));
        setLayout(new java.awt.BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}