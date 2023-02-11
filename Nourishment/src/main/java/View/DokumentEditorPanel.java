/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import View.BasicView.BasePanel;

/**
 *
 * @author komputer1
 */
public class DokumentEditorPanel extends BasePanel {
    StringBuilder text;

    /**
     * Creates new form DokumentEditorPanel
     */
    public DokumentEditorPanel() {
        super(null);
        initComponents();
    }
    
    @Override
    public <E> void unpack(E object) {
        super.unpack(object);
        text = (StringBuilder) object;
        jTextArea1.setText(text.toString());
    }

    @Override
    public void pack() {
      text.delete(0, text.length());
      text.append(jTextArea1.getText());
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
