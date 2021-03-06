/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.PotrawyWDniu;
import Entities.Produkty;
import Entities.ProduktyLuzneWDniu;
import Global.GlobalConfig;
import Global.GlobalFun;
import Global.ORMManager;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPanelInterface;
import Other.MyComparator;
import View.BasicView.BasePanel;
import View.BasicView.MainDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Marek
 */
public class PotrawyWDniuView extends BasePanel {
    private PotrawyWDniu potrWDniu = null;
    private JDatePickerImpl datePicker = null;
    private List<ProduktyLuzneWDniu> objectToDeleteListLocal = null;
    private List<ProduktyLuzneWDniu> objectToDeleteList = null;

    public List<ProduktyLuzneWDniu> getObjectToDeleteList() {
        return objectToDeleteList;
    }
    
    private void setControlsEnabled(boolean isSelected){
         if (!isSelected){
            cmbLunch.setEnabled(false);
            cmbObiad.setEnabled(true);
            cmbPodwieczorek.setEnabled(true);
            lblLunch.setEnabled(false);
            lblObiad.setEnabled(true);
            lblPodwieczorek.setEnabled(true);            
        }
        else{
            cmbLunch.setEnabled(true);
            cmbObiad.setEnabled(false);
            cmbPodwieczorek.setEnabled(false);   
            lblLunch.setEnabled(true);
            lblObiad.setEnabled(false);
            lblPodwieczorek.setEnabled(false);              
        }       
    }

    @Override
    public <E> void unpack(E object) {
        potrWDniu = (PotrawyWDniu) object;
        GlobalFun.bind(potrWDniu.getCzy5dni(), GlobalFun.dBFalse(), chbCzy5Dni);
        GlobalFun.bind(potrWDniu.getId(),edtID);
        GlobalFun.bind(potrWDniu.getSniadanie(), cmbSniadanie);
        GlobalFun.bind(potrWDniu.getDrugieSniadanie(), cmbDrugieSniadanie);
        GlobalFun.bind(potrWDniu.getObiad(), cmbObiad);
        GlobalFun.bind(potrWDniu.getPodwieczorek(), cmbPodwieczorek);
        GlobalFun.bind(potrWDniu.getKolacja(), cmbKolacja);
        GlobalFun.bind(potrWDniu.getLunch(), cmbLunch);
        GlobalFun.bind(potrWDniu.getNazwa(), edtNazwa);
        setControlsEnabled(chbCzy5Dni.isSelected());
        objectToDeleteListLocal.clear();
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        objectList.sort(new MyComparator("nazwa", Potrawy.class, true));
        GlobalFun.unpackComboBox(cmbSniadanie, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbDrugieSniadanie, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbObiad, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbPodwieczorek, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbKolacja, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbLunch, (List<Serializable>) objectList);
        objectToDeleteListLocal.clear();
    }

    @Override
    public void pack() {
        potrWDniu.setCzy5dni(GlobalFun.bind(chbCzy5Dni, GlobalFun.dBFalse(), GlobalFun.dBTrue()));
        potrWDniu.setSniadanie((Potrawy) GlobalFun.bind(cmbSniadanie));
        potrWDniu.setDrugieSniadanie((Potrawy) GlobalFun.bind(cmbDrugieSniadanie));
        potrWDniu.setObiad((Potrawy) GlobalFun.bind(cmbObiad));
        potrWDniu.setPodwieczorek((Potrawy) GlobalFun.bind(cmbPodwieczorek));
        potrWDniu.setKolacja((Potrawy) GlobalFun.bind(cmbKolacja));
        potrWDniu.setLunch((Potrawy) GlobalFun.bind(cmbLunch));
        potrWDniu.setNazwa((String) GlobalFun.bind(edtNazwa, String.class));
        if (potrWDniu.getProduktyLuzneWDniu() != null){
            for (int i = 0; i < potrWDniu.getProduktyLuzneWDniu().size(); i++) {
                potrWDniu.getProduktyLuzneWDniu().get(i).setDzien(potrWDniu);
            }
        }
        if (objectToDeleteList == null){
            objectToDeleteList = new ArrayList<ProduktyLuzneWDniu>();
        }
        objectToDeleteList.addAll(objectToDeleteListLocal);
        objectToDeleteListLocal.clear();
        ((MyListPanelInterface) extraPanel[0]).getObjectToDeleteList().clear();
        ((MyListPanelInterface) extraPanel[0]).getNewOrEditedObjectList().clear();
    }

    @Override
    public void rollback() {
        super.rollback();
        objectToDeleteListLocal.clear();
    }
    

    
    public PotrawyWDniuView(MyPanelInterface extraPanel) {
        super(extraPanel);
        initComponents();
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        objectToDeleteList = new ArrayList<ProduktyLuzneWDniu>();
        objectToDeleteListLocal = new ArrayList<ProduktyLuzneWDniu>();
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
        lblSniadanie = new javax.swing.JLabel();
        cmbSniadanie = new javax.swing.JComboBox<>();
        lblDrugieSniadanie = new javax.swing.JLabel();
        cmbDrugieSniadanie = new javax.swing.JComboBox<>();
        lblObiad = new javax.swing.JLabel();
        cmbObiad = new javax.swing.JComboBox<>();
        lblLunch = new javax.swing.JLabel();
        cmbLunch = new javax.swing.JComboBox<>();
        lblPodwieczorek = new javax.swing.JLabel();
        cmbPodwieczorek = new javax.swing.JComboBox<>();
        lblKolacja = new javax.swing.JLabel();
        cmbKolacja = new javax.swing.JComboBox<>();
        lblProdukty = new javax.swing.JLabel();
        btnDodajProdukty = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chbCzy5Dni = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridLayout(10, 2, 10, 10));

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblID.setText("ID");
        lblID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblID);

        edtID.setEnabled(false);
        add(edtID);

        lblNazwa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNazwa.setText("Nazwa");
        add(lblNazwa);
        add(edtNazwa);

        lblSniadanie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSniadanie.setText("Śniadanie");
        lblSniadanie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblSniadanie);

        add(cmbSniadanie);

        lblDrugieSniadanie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDrugieSniadanie.setText("Drugie śniadanie");
        lblDrugieSniadanie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblDrugieSniadanie);

        add(cmbDrugieSniadanie);

        lblObiad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblObiad.setText("Obiad");
        lblObiad.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblObiad);

        add(cmbObiad);

        lblLunch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLunch.setText("Lunch");
        lblLunch.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblLunch);

        add(cmbLunch);

        lblPodwieczorek.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPodwieczorek.setText("Podwieczorek");
        lblPodwieczorek.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblPodwieczorek);

        add(cmbPodwieczorek);

        lblKolacja.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKolacja.setText("Kolacja");
        lblKolacja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblKolacja);

        add(cmbKolacja);

        lblProdukty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProdukty.setText("Produkty luzem");
        add(lblProdukty);

        btnDodajProdukty.setText("Dodaj produkty");
        btnDodajProdukty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajProduktyActionPerformed(evt);
            }
        });
        add(btnDodajProdukty);
        add(jPanel1);

        chbCzy5Dni.setText("Dieta 4-dniowa");
        chbCzy5Dni.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chbCzy5Dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCzy5DniActionPerformed(evt);
            }
        });
        add(chbCzy5Dni);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajProduktyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajProduktyActionPerformed
        MainDialog dlgProduktyLuzem = new MainDialog(null, true, extraPanel[0].getKonfigView().withPanelID(GlobalConfig.PRODUKTY_LUZEM_ID), "Produkty luzem", extraPanel[0]);
        List<ProduktyLuzneWDniu> prodLuzneWDniuList = potrWDniu.getProduktyLuzneWDniu(); 
        if (prodLuzneWDniuList == null){
            prodLuzneWDniuList = new ArrayList<ProduktyLuzneWDniu>();
            potrWDniu.setProduktyLuzneWDniu(prodLuzneWDniuList);
        }
        dlgProduktyLuzem.unpackWindow(potrWDniu.getProduktyLuzneWDniu());
        dlgProduktyLuzem.unpackWindow(ORMManager.getOrmManager().askForObjects(Produkty.class));
        dlgProduktyLuzem.setVisible(true);
        if (dlgProduktyLuzem.getResult()) {          
            objectToDeleteListLocal.addAll(((MyListPanelInterface) extraPanel[0]).getObjectToDeleteList());
        }
        else{
            ((MyListPanelInterface) extraPanel[0]).getObjectToDeleteList().clear();
        }
    }//GEN-LAST:event_btnDodajProduktyActionPerformed

    private void chbCzy5DniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCzy5DniActionPerformed
        setControlsEnabled(chbCzy5Dni.isSelected());
    }//GEN-LAST:event_chbCzy5DniActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodajProdukty;
    private javax.swing.JCheckBox chbCzy5Dni;
    private javax.swing.JComboBox<String> cmbDrugieSniadanie;
    private javax.swing.JComboBox<String> cmbKolacja;
    private javax.swing.JComboBox<String> cmbLunch;
    private javax.swing.JComboBox<String> cmbObiad;
    private javax.swing.JComboBox<String> cmbPodwieczorek;
    private javax.swing.JComboBox<String> cmbSniadanie;
    private javax.swing.JTextField edtID;
    private javax.swing.JTextField edtNazwa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDrugieSniadanie;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKolacja;
    private javax.swing.JLabel lblLunch;
    private javax.swing.JLabel lblNazwa;
    private javax.swing.JLabel lblObiad;
    private javax.swing.JLabel lblPodwieczorek;
    private javax.swing.JLabel lblProdukty;
    private javax.swing.JLabel lblSniadanie;
    // End of variables declaration//GEN-END:variables
}
