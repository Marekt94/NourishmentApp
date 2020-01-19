/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Other.KonfigView;
import Other.MyPanelInterface;
import Other.ORMManager;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marek
 */
public class LoginPanel extends javax.swing.JPanel implements MyPanelInterface{

    @Override
    public <E> void unpack(E object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> void unpack(List<E> objectList){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private KonfigView konfigView;
    private ORMManager ormManager;
    
    @Override
    public Boolean execute() {
        ormManager = ORMManager.getOrmManager();
        return ormManager.connect(edtUsername.getText(), new String(edtPassword.getPassword()));
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
     * Creates new form LoginPanel
     */
    public LoginPanel() {
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

        lblUsername = new javax.swing.JLabel();
        edtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        edtPassword = new javax.swing.JPasswordField();

        setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("Użytkownik");
        add(lblUsername);

        edtUsername.setPreferredSize(new java.awt.Dimension(100, 20));
        add(edtUsername);

        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setText("Hasło");
        lblPassword.setRequestFocusEnabled(false);
        add(lblPassword);

        edtPassword.setPreferredSize(new java.awt.Dimension(100, 20));
        add(edtPassword);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField edtPassword;
    private javax.swing.JTextField edtUsername;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    // End of variables declaration//GEN-END:variables
}
