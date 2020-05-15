/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.Produkty;
import Global.ORMManager;
import Interfaces.MyPanelInterface;
import View.BasicView.BaseListPanel;
import View.BasicView.MainDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Marek
 */
public class PotrawyListView extends BaseListPanel{
    List<Produkty> productList = null;
    
    public PotrawyListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        productList = new ArrayList<Produkty>();
        JButton btnMealToProduct = new JButton("Zamień potrawę w produkt");
        btnMealToProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Potrawy potrawa = getCurrentObject();
                    if (potrawa != null){
                        changeMealToProduct(potrawa);
                    }else{
                        JOptionPane.showMessageDialog(null, "Wybierz potrawę", "Wybierz potrawę", JOptionPane.WARNING_MESSAGE);
                    }
                    updateView();
            }
        });
        addButton(btnMealToProduct, KeyEvent.VK_M);       
    }
    
    private void changeMealToProduct(Potrawy meal){
        Produkty product = new Produkty();
        List<Double> weight = new ArrayList<Double>(); //dirty trick to pass double by reference
        product.setNazwa(meal.getNazwa());
        product.setBialko(evaluateProductValuePer100(meal.getSumaBialko(), meal.getWaga()));
        product.setBlonnik(evaluateProductValuePer100(meal.getSumaBlonnik(), meal.getWaga()));
        product.setCukryProste(evaluateProductValuePer100(meal.getSumaCukryProste(), meal.getWaga()));
        product.setCukrySuma(evaluateProductValuePer100(meal.getSumaCukrySuma(), meal.getWaga()));
        product.setCukryZlozone(evaluateProductValuePer100(meal.getSumaCukryZlozone(), meal.getWaga()));
        product.setKcalNa100g(evaluateProductValuePer100(meal.getSumaKcal(), meal.getWaga()));
        product.setSol(evaluateProductValuePer100(meal.getSumaSol(), meal.getWaga()));
        product.setTluszcz(evaluateProductValuePer100(meal.getSumaTluszcz(), meal.getWaga()));
        MainDialog unitWeight = new MainDialog(null, true, konfigView, "Waga jednostki", new WagaJednostkiPanel());
        unitWeight.unpackWindow(weight);
        unitWeight.setVisible(true);
        if (unitWeight.getResult()){
            product.setWagaJednostki(weight.get(0));
            productList.add(product);
            deleteObject();
        }
    }

    private Double evaluateProductValuePer100(Double value, Double weight){
        return (value / weight) * 100; 
    }

    @Override
    public Boolean execute() {
        if (super.execute()){
            ORMManager.getOrmManager().addToDB(productList);
            productList.clear();
            unpack();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void rollback() {
        super.rollback();
        productList.clear();
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
