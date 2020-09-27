/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.Produkty;
import Entities.ProduktyWPotrawie;
import Global.GlobalConfig;
import Global.GlobalFun;
import Interfaces.MyListPanelInterface;
import Interfaces.MyPDFGeneratorInterface;
import Interfaces.MyPanelInterface;
import static Other.FileDialogFunctionType.fdftSave;
import Other.PDFGenerator;
import View.BasicView.BaseListPanel;
import View.BasicView.KonfigView;
import View.BasicView.MainDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Marek
 */
public class PotrawyEditorListView extends BaseListPanel {
    MyListPanelInterface pnlPotrawyList = null;
    MyListPanelInterface pnlProduktyList = null;
    Integer newID = 0;
    Boolean isCurrentlyUpdated = false;
    StringBuilder defaultDirectory = new StringBuilder();
    String fileExtension = "pdf";

    /**
     * Creates new form PotrawyListView
     */
    public PotrawyEditorListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        omittedColumns.add("idPotrawy");
        
        pnlPotrawyList = new PotrawyListView(new PotrawyView(), "Potrawa", Potrawy.class);
        ((JPanel) pnlPotrawyList).setPreferredSize(new Dimension(500, 450));
        
        pnlProduktyList = new BaseListPanel(new ProduktView(), "Produkt", Produkty.class);
        ((JPanel) pnlProduktyList).setPreferredSize(new Dimension(500, 450));
        
        this.add((JPanel) pnlProduktyList,BorderLayout.EAST);
        this.add((JPanel) pnlPotrawyList,BorderLayout.WEST);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        
        btnAdd.setText("<<");
        
        ((JTable) pnlPotrawyList.getComponentWihtListOfRecords()).getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {                
                if ( !e.getValueIsAdjusting()){
                    Potrawy potrawa = pnlPotrawyList.getCurrentObject();
                    if (potrawa != null){
                        objectList = (List<Serializable>) GlobalFun.toList(potrawa.getProduktyWPotrawieCollection());
                    }  
                    updateView();
                }
            }
        });
        
        JButton btnPrint = new JButton();
        btnPrint.setText("Drukuj");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printMeals();
            }
        });
        pnlPotrawyList.addButton(btnPrint, KeyEvent.VK_D);
    }
    
    private void printMeals(){
        String fileName = GlobalFun.choosePath(this, fileExtension, fdftSave, defaultDirectory);
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle("Potrawy");
            createMeals(pDFGenerator);
            pDFGenerator.closeDocument();
        }         
    }
    
    private void createMeals(MyPDFGeneratorInterface pDFGenerator){
        List<Potrawy> potrList = pnlPotrawyList.getObjectsList();
        HashSet<String> list = new HashSet<>();
        for (int i = 0; i < pnlPotrawyList.getObjectsList().size(); i++) {
            Potrawy potr = potrList.get(i);
            pDFGenerator.addSubtitle(potr.getNazwa() + ":\n"
                                     + "b: " + GlobalFun.round(potr.getSumaBialko(), 2).toString()
                                     + " w: " + GlobalFun.round(potr.getSumaCukrySuma(), 2).toString()
                                     + " t: " + GlobalFun.round(potr.getSumaTluszcz(), 2).toString()
                                     + " kcal: " + GlobalFun.round(potr.getSumaKcal(), 2).toString());
            if (potr.getPrzepis() != null){
                pDFGenerator.addParagraph(potr.getPrzepis().trim());
            }
            list.clear();
            for (int j = 0; j < potrList.get(i).getProduktyWPotrawieCollection().size(); j++) {
                list.add(potrList.get(i).getProduktyWPotrawieCollection().get(j).getIdProduktu().getNazwa() + " "
                         + potrList.get(i).getProduktyWPotrawieCollection().get(j).getIloscWG() + " g");
            }
            pDFGenerator.addList(list.toArray(new String[0]));
        }
    }

    @Override
    public Boolean init(KonfigView konfigView) {
        newID = konfigView.getPanelID() * 10 + GlobalConfig.POTRAWY_ID;
        pnlPotrawyList.getKonfigView().setPanelID(newID.byteValue());
        newID = konfigView.getPanelID() * 10 + GlobalConfig.PRODUKTY_ID;
        pnlProduktyList.getKonfigView().setPanelID(newID.byteValue());
        return super.init(konfigView); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPreferences() {
        pnlPotrawyList.loadPreferences();
        pnlProduktyList.loadPreferences();
        super.loadPreferences(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void savePreferences() { 
        pnlPotrawyList.savePreferences();
        pnlProduktyList.savePreferences();
        super.savePreferences(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType) {
        if (pnlProduktyList.getCurrentObject() == null){
            JOptionPane.showMessageDialog(this, "Wybierz produkt", "Wybierz produkt", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pnlPotrawyList.getCurrentObject() != null){
            ProduktyWPotrawie prodWPotr = new ProduktyWPotrawie();
            prodWPotr.setIdProduktu(pnlProduktyList.getCurrentObject());
            prodWPotr.setIdPotrawy(pnlPotrawyList.getCurrentObject());
            
            MainDialog wagaProduktuDialog = new MainDialog(null, true, konfigView, "Produkt w potrawie", new WagaProduktuWPotrawiePanel());
            wagaProduktuDialog.unpackWindow(prodWPotr);
            wagaProduktuDialog.setVisible(true);
            
            if (wagaProduktuDialog.getResult()){
                if (((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection() == null){
                    ((Potrawy) pnlPotrawyList.getCurrentObject()).setProduktyWPotrawieCollection(new ArrayList<ProduktyWPotrawie>() );
                }
                ((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection().add(prodWPotr);
                objectList = (List<Serializable>) GlobalFun.toList(((Potrawy) pnlPotrawyList.getCurrentObject()).getProduktyWPotrawieCollection());
                newOrEditedObjectList.add((Potrawy) pnlPotrawyList.getCurrentObject());
            }
            updateView();
        }
        else{
            JOptionPane.showMessageDialog(this, "Wybierz potrawę", "Wybierz potrawę", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void deleteObject() {
        if (pnlPotrawyList.getCurrentObject() != null){
            Potrawy potrawa = pnlPotrawyList.getCurrentObject();
            potrawa.getProduktyWPotrawieCollection().remove(getCurrentObject());
        }
        super.deleteObject(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public <E> void unpack(List<E> objectList) {
        if (objectList.get(0) instanceof Produkty){
            pnlProduktyList.unpack(objectList);
        }
        if (objectList.get(0) instanceof Potrawy){
            pnlPotrawyList.unpack(objectList);
        }
    }

    @Override
    public Boolean execute() {
        if (pnlProduktyList.execute()){
            if (pnlPotrawyList.execute()){
                if (super.execute()){
                    pnlProduktyList.unpack();
                    pnlPotrawyList.unpack();
                    return true;
                }
            }           
        }
        return false;
    }

    @Override
    public void rollback() {
        pnlPotrawyList.rollback();
        pnlProduktyList.rollback();
        super.rollback(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateView() {
        if (!isCurrentlyUpdated){
            isCurrentlyUpdated = true;
            pnlProduktyList.updateView();
            pnlPotrawyList.updateView();
            super.updateView();
            isCurrentlyUpdated = false;
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

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
