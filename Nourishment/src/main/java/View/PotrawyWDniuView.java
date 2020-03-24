/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.PotrawyWDniu;
import Global.GlobalFun;
import Other.DateLabelFormatter;
import View.BasicView.BasePanel;
import java.awt.BorderLayout;
import java.io.Serializable;
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
    JDatePickerImpl datePicker = null;

    @Override
    public <E> void unpack(E object) {
        potrWDniu = (PotrawyWDniu) object;
        GlobalFun.bind(potrWDniu.getId(),edtID);
        GlobalFun.bind(potrWDniu.getSniadanie(), cmbSniadanie);
        GlobalFun.bind(potrWDniu.getDrugieSniadanie(), cmbDrugieSniadanie);
        GlobalFun.bind(potrWDniu.getObiad(), cmbObiad);
        GlobalFun.bind(potrWDniu.getPodwieczorek(), cmbPodwieczorek);
        GlobalFun.bind(potrWDniu.getKolacja(), cmbKolacja);
        GlobalFun.bind(potrWDniu.getLunch(), cmbLunch);
        GlobalFun.bind(potrWDniu.getData(), datePicker);
    }

    @Override
    public <E> void unpack(List<E> objectList) {
        GlobalFun.unpackComboBox(cmbSniadanie, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbDrugieSniadanie, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbObiad, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbPodwieczorek, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbKolacja, (List<Serializable>) objectList);
        GlobalFun.unpackComboBox(cmbLunch, (List<Serializable>) objectList);
    }

    @Override
    public void pack() {
        potrWDniu.setSniadanie((Potrawy) GlobalFun.bind(cmbSniadanie));
        potrWDniu.setDrugieSniadanie((Potrawy) GlobalFun.bind(cmbDrugieSniadanie));
        potrWDniu.setObiad((Potrawy) GlobalFun.bind(cmbObiad));
        potrWDniu.setPodwieczorek((Potrawy) GlobalFun.bind(cmbPodwieczorek));
        potrWDniu.setKolacja((Potrawy) GlobalFun.bind(cmbKolacja));
        potrWDniu.setLunch((Potrawy) GlobalFun.bind(cmbLunch));
        potrWDniu.setData(GlobalFun.bind(datePicker));
        potrWDniu.setCzy5dni('1');
    }
    

    
    public PotrawyWDniuView() {
        super();
        initComponents();
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        jPanel1.add(datePicker,BorderLayout.CENTER);       
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
        lblData = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblSniadanie = new javax.swing.JLabel();
        cmbSniadanie = new javax.swing.JComboBox<>();
        lblDrugieSnianiadnie = new javax.swing.JLabel();
        cmbDrugieSniadanie = new javax.swing.JComboBox<>();
        lblObiad = new javax.swing.JLabel();
        cmbObiad = new javax.swing.JComboBox<>();
        lblLunch = new javax.swing.JLabel();
        cmbLunch = new javax.swing.JComboBox<>();
        lblPodwieczorek = new javax.swing.JLabel();
        cmbPodwieczorek = new javax.swing.JComboBox<>();
        lblKolacja = new javax.swing.JLabel();
        cmbKolacja = new javax.swing.JComboBox<>();

        setLayout(new java.awt.GridLayout(8, 2, 10, 10));

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblID.setText("ID");
        lblID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblID);
        add(edtID);

        lblData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblData.setText("Data");
        lblData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblData);

        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1);

        lblSniadanie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSniadanie.setText("Śniadanie");
        lblSniadanie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblSniadanie);

        add(cmbSniadanie);

        lblDrugieSnianiadnie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDrugieSnianiadnie.setText("Drugie śniadanie");
        lblDrugieSnianiadnie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(lblDrugieSnianiadnie);

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
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbDrugieSniadanie;
    private javax.swing.JComboBox<String> cmbKolacja;
    private javax.swing.JComboBox<String> cmbLunch;
    private javax.swing.JComboBox<String> cmbObiad;
    private javax.swing.JComboBox<String> cmbPodwieczorek;
    private javax.swing.JComboBox<String> cmbSniadanie;
    private javax.swing.JTextField edtID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDrugieSnianiadnie;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKolacja;
    private javax.swing.JLabel lblLunch;
    private javax.swing.JLabel lblObiad;
    private javax.swing.JLabel lblPodwieczorek;
    private javax.swing.JLabel lblSniadanie;
    // End of variables declaration//GEN-END:variables
}
