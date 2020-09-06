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
import Entities.ProduktyWPotrawie;
import Global.GlobalConfig;
import static Global.GlobalConfig.dataFormat;
import Global.GlobalFun;
import static Global.GlobalFun.returnStringOrEmpty;
import Global.ORMManager;
import Interfaces.MyPDFGeneratorInterface;
import Interfaces.MyPanelInterface;
import static Other.FileDialogFunctionType.fdftSave;
import Other.MyComparator;
import Other.PDFGenerator;
import Other.ProductRecord;
import View.BasicView.BaseListPanel;
import View.BasicView.KonfigView;
import View.BasicView.MainDialog;
import com.sun.xml.fastinfoset.util.StringArray;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.EventHandler;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.hibernate.internal.util.compare.ComparableComparator;

/**
 *
 * @author Marek
 */
public class PotrawyWDniuListView extends BaseListPanel {
    private List<Potrawy> potrawyList = null;
    private StringBuilder defaultDirectory = new StringBuilder();
    private String fileExtension = "pdf";

    /**
     * Creates new form ProduktyWDniuListView
     */
    public PotrawyWDniuListView(MyPanelInterface detailPanel, String detailPanelTitle, Class detailEntityClass) {
        super(detailPanel, detailPanelTitle, detailEntityClass);
        omittedColumns.add("mnoznikSniadanie");
        omittedColumns.add("mnoznikDrugieSniadanie");
        omittedColumns.add("mnoznikObiad");
        omittedColumns.add("mnoznikKolacja");
        omittedColumns.add("mnoznikPodwieczorek");
        omittedColumns.add("mnoznikLunch");
        omittedColumns.add("czy5dni");

        JButton btnPrintMenu = new JButton("Drukuj");
        btnPrintMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMenu(getSelectedRecords());
            }
        });
        addButton(btnPrintMenu, KeyEvent.VK_R);
        JButton btnPrintShoppingList = new JButton("Drukuj liste zakupów");
        btnPrintShoppingList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateShoppingList(getSelectedRecords());
            }
        });
        addButton(btnPrintShoppingList, KeyEvent.VK_L);
        JButton btnPrintReceipts = new JButton("Drukuj przpepisy");
        btnPrintReceipts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceipts(getSelectedRecords());
            }
        });
        addButton(btnPrintReceipts, KeyEvent.VK_P);
    }
    
    private List<Serializable> getSelectedRecords(){
        List<Serializable> selectedRecords = new ArrayList<>();
        for (int record : tblObjects.getSelectedRows()){
            selectedRecords.add(objectList.get(record));
        }
        return selectedRecords;
    }

    @Override
    public Boolean init(KonfigView konfigView) {
        Boolean res;
        res = super.init(konfigView);
        potrawyList = (List<Potrawy>) ORMManager.getOrmManager().askForObjects(Potrawy.class);
        return res;
    }

    @Override
    public Boolean execute() {
        Boolean result = super.execute();
        if (result){
            ORMManager oRMManager = ORMManager.getOrmManager(); 
            result = oRMManager.deleteFromDB(((PotrawyWDniuView) detailPanel).getObjectToDeleteList()) && result;
            ((PotrawyWDniuView) detailPanel).getObjectToDeleteList().clear();
        }
        return result;
    }

    @Override
    public void rollback() {
        super.rollback();
        ((PotrawyWDniuView) detailPanel).getObjectToDeleteList().clear();        
    }
    
    public void generateReceipts(List<Serializable> listOfDays){
        String fileName = GlobalFun.choosePath(this, fileExtension, fdftSave, defaultDirectory);
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle(((PotrawyWDniu) listOfDays.get(0)).getNazwa());
            createReceipts(listOfDays, pDFGenerator);
            pDFGenerator.closeDocument();
        }          
    }
    
    private void createReceipts(List<Serializable> listOfDays, MyPDFGeneratorInterface pDFGenerator){
        List<Potrawy> potrawyList = new ArrayList<Potrawy>();
        for (int i = 0; i < listOfDays.size(); i++) {
            PotrawyWDniu pwd = (PotrawyWDniu) listOfDays.get(i);
            addToReceipt(pwd.getSniadanie(),       potrawyList, pDFGenerator);
            addToReceipt(pwd.getDrugieSniadanie(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getObiad(),           potrawyList, pDFGenerator);
            addToReceipt(pwd.getPodwieczorek(),    potrawyList, pDFGenerator);
            addToReceipt(pwd.getLunch(),           potrawyList, pDFGenerator);
            addToReceipt(pwd.getKolacja(),         potrawyList, pDFGenerator);
        }
    }
    
    private String [] addFreeProducts(PotrawyWDniu pwd){
        HashSet list = new HashSet<String>();
        for (int i = 0; i < pwd.getProduktyLuzneWDniu().size(); i++) {
            list.add(addFreeProduct(pwd.getProduktyLuzneWDniu().get(i)));
        }
        return (String[]) list.toArray(new String [0]);
    }

    private String addFreeProduct(ProduktyLuzneWDniu prod){
        if (prod != null) {
            return prod.getProdukt().getNazwa() + ": "
                    + " b: "    + GlobalFun.round(prod.getBialko(),    2).toString()
                    + " w: "    + GlobalFun.round(prod.getCukrySuma(), 2).toString()
                    + " t: "    + GlobalFun.round(prod.getTluszcz(),   2).toString()
                    + " kcal: " + GlobalFun.round(prod.getKcal(),      2).toString()
                    + " waga: " + GlobalFun.round(prod.getIloscWG(),   2).toString() + " g";
        } else {
            return GlobalConfig.NULL_SIGN;
        }        
    }
                
    
   private void addToReceipt(Potrawy ptr, List<Potrawy> existingOnReceiptMeals, MyPDFGeneratorInterface pDFGenerator){
        if ((ptr != null) && (!existingOnReceiptMeals.contains(ptr))){
            existingOnReceiptMeals.add(ptr);
            pDFGenerator.addSubtitle(ptr.getNazwa());
            HashSet<String> prodList = new HashSet<String>();
            for (ProduktyWPotrawie prod : ptr.getProduktyWPotrawieCollection()){
                prodList.add(prod.getIdProduktu().getNazwa() + ": " + Double.toString(prod.getIloscWG()) + " " + prod.getIdProduktu().getJednostka());
            }
            String [] prodListArray = prodList.toArray(new String[0]); 
            pDFGenerator.addList(prodListArray);
        }       
   } 
    
    @Override
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType) {
        MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
        Serializable object = null;
        try {
            try {
                object = (Serializable) objectType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BaseListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainWindow.unpackWindow(potrawyList);
        mainWindow.unpackWindow(object);

        mainWindow.setVisible(true);
        if (mainWindow.getResult()) {
            objectList.add(object);
            newOrEditedObjectList.add(object);
            updateView();
        }
    }

    @Override
    public void editObject(MyPanelInterface detailPanel, String title) {
        if (tblObjects.getSelectedRow() > -1) {
            Serializable object;

            MainDialog mainWindow = new MainDialog(null, true, konfigView, title, detailPanel);
            object = objectList.get(tblObjects.getSelectedRow());
            mainWindow.unpackWindow(potrawyList);
            mainWindow.unpackWindow(object);

            mainWindow.setVisible(true);

            if (mainWindow.getResult()) {
                newOrEditedObjectList.add(object);
            }

            updateView();
        }
        else{
            JOptionPane.showMessageDialog(this, "Wybierz dzień", "Wybierz dzień", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateMenu(List<Serializable> listOfDays) {
        String fileName = GlobalFun.choosePath(this, fileExtension, fdftSave, defaultDirectory);
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            listOfDays.sort(new MyComparator("data", PotrawyWDniu.class, true));
            pDFGenerator.openDocument(fileName);
            for (int i = 0; i < listOfDays.size(); i++) {
                PotrawyWDniu potr = (PotrawyWDniu) listOfDays.get(i);
                if (potr.getNazwa() != null && !potr.getNazwa().isEmpty()){
                    pDFGenerator.addTitle(potr.getNazwa());                 
                }                
                pDFGenerator.addSubtitle(createTitleString("makro", (PotrawyWDniu) listOfDays.get(i)));
                pDFGenerator.addList(createPotrawyStringList(potr));
                if ((potr.getProduktyLuzneWDniu()!= null) && potr.getProduktyLuzneWDniu().size() > 0){
                    pDFGenerator.addSubtitle(" Produkty luzem");
                    pDFGenerator.addList(addFreeProducts((PotrawyWDniu) listOfDays.get(i)));
                }
                pDFGenerator.newPage();
            }
            pDFGenerator.closeDocument();
        }
    }

    private String[] createPotrawyStringList(PotrawyWDniu potr) {
        String[] stringArray = null;
        if ((potr.getCzy5dni() != null) && (potr.getCzy5dni() == '1')) {
            stringArray = new String[5];
            stringArray[0] = createMealString("śniadanie", potr.getSniadanie());
            stringArray[1] = createMealString("drugie śniadanie", potr.getDrugieSniadanie());
            stringArray[2] = createMealString("obiad", potr.getObiad());
            stringArray[3] = createMealString("podwieczorek", potr.getPodwieczorek());
            stringArray[4] = createMealString("kolacja", potr.getKolacja());
        } else {
            stringArray = new String[4];
            stringArray[0] = createMealString("śniadanie", potr.getSniadanie());
            stringArray[1] = createMealString("drugie śniadanie", potr.getDrugieSniadanie());
            stringArray[2] = createMealString("lunch", potr.getLunch());
            stringArray[3] = createMealString("kolacja", potr.getKolacja());
        }
        return stringArray;
    }

    private String createMealString(String prefix, Potrawy potr) {        
        if (potr != null) {           
            HashSet<String> productList = new HashSet<String>();
            String text = "";   
            text = prefix + ": " + potr.toString() + "\n"
                   + " b: " + GlobalFun.round(potr.getSumaBialko(), 2).toString()
                   + " w: " + GlobalFun.round(potr.getSumaCukrySuma(), 2).toString()
                   + " t: " + GlobalFun.round(potr.getSumaTluszcz(), 2).toString()
                   + " kcal: " + GlobalFun.round(potr.getSumaKcal(), 2).toString();
            for (ProduktyWPotrawie prod : potr.getProduktyWPotrawieCollection()){
                productList.add("  * " + prod.getIdProduktu().getNazwa()
                                + ": " + Double.toString(prod.getIloscWG())
                                + " " + prod.getIdProduktu().getJednostka() + "\n");
            } 
            return text + "\n" + productList.toString().replace("[", "").replace("]","").replace(", ", "");
        } else {
            return prefix + ": ";
        }
    }

    private String createTitleString(String prefix, PotrawyWDniu potr) {
        if (potr != null) {
            return prefix + ": " + " b: " + GlobalFun.round(potr.getSumaBialko(), 2).toString()
                    + " w: " + GlobalFun.round(potr.getSumaCukrySuma(), 2).toString()
                    + " t: " + GlobalFun.round(potr.getSumaTluszcz(), 2).toString()
                    + " kcal: " + GlobalFun.round(potr.getSumaKcal(), 2).toString();
        } else {
            return prefix + ": ";
        }
    }
    
    private void generateShoppingList(List<Serializable> listOfDays){
        String fileName = GlobalFun.choosePath(this, fileExtension, fdftSave, defaultDirectory);
        List<List<?>> list = new ArrayList<>();
        List<Integer> forHowManyDaysList = new ArrayList<>();
        MainDialog dlg = new MainDialog(null, true, konfigView, "Jadłospis na ile dni?", new DaysPanel());
        list.add(listOfDays);
        list.add(forHowManyDaysList);
        dlg.unpackWindow(list);
        dlg.setVisible(true);
        if (!fileName.equals("") && dlg.getResult()) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle(((PotrawyWDniu) listOfDays.get(0)).getNazwa());
            List<ProductRecord> productList = createShoppingList(listOfDays, forHowManyDaysList);
            productList.sort(new Comparator<ProductRecord>() {
                @Override
                public int compare(ProductRecord o1, ProductRecord o2) {
                    return o1.productName.compareTo(o2.productName);
                }
            });
            pDFGenerator.addList(createShoppingStringList(productList));
            pDFGenerator.closeDocument();
        }        
    }
    
    private List<ProductRecord> createShoppingList(List<Serializable> listOfDays, List<Integer> forHowManyDaysList){
        Integer forHowManyDays = 1;
        List<ProductRecord> productsList = new ArrayList<ProductRecord>();
        for (int i = 0; i < listOfDays.size(); ++i){
            forHowManyDays = forHowManyDaysList.get(i);
            PotrawyWDniu day = (PotrawyWDniu) listOfDays.get(i);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getSniadanie(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getDrugieSniadanie(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getObiad(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getPodwieczorek(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getLunch(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getKolacja(), forHowManyDays);
            addToShoppingList(productsList, ((PotrawyWDniu) day).getProduktyLuzneWDniu(), forHowManyDays);
        }        
        return productsList; 
    }
    
    private Boolean addToShoppingList(List<ProductRecord> productsList, Potrawy meal, Integer forHowManyDays){
            Boolean result = false;
            if (meal != null){
                List<ProduktyWPotrawie> prodWPotr = (List<ProduktyWPotrawie>) meal.getProduktyWPotrawieCollection();
                for (ProduktyWPotrawie produktWPotr : prodWPotr){
                    Integer indexOfProduct = returnIndexInProductList(productsList, produktWPotr.getIdProduktu());
                    Produkty prod = ((ProduktyWPotrawie) produktWPotr).getIdProduktu();
                    if (indexOfProduct == -1){
                        ProductRecord productRecord = new ProductRecord();
                        productRecord.productName = prod.getNazwa();
                        productRecord.weight = forHowManyDays * produktWPotr.getIloscWG();
                        if (!prod.getWagaJednostki().equals(0.0)){
                            productRecord.packages = productRecord.weight / prod.getWagaJednostki();
                        }
                        else{
                            productRecord.packages = 0.0;
                        }
                        productsList.add(productRecord);
                        result = true;
                    }
                    else{
                        productsList.get(indexOfProduct).weight = productsList.get(indexOfProduct).weight + forHowManyDays * produktWPotr.getIloscWG();
                        if (!prod.getWagaJednostki().equals(0.0)){
                            productsList.get(indexOfProduct).packages = productsList.get(indexOfProduct).weight / prod.getWagaJednostki();
                        }
                        else{
                            productsList.get(indexOfProduct).packages = 0.0;
                        }                        
                        result = true;
                    }
                }
            }
            return result;
    }
    
    private Boolean addToShoppingList(List<ProductRecord> productsList, List<ProduktyLuzneWDniu> prodLuzneWdniu, Integer forHowManyDays){
            Boolean result = false;
            if (prodLuzneWdniu != null){
                for (ProduktyLuzneWDniu prodLuzem : prodLuzneWdniu){
                    Integer indexOfProduct = returnIndexInProductList(productsList, prodLuzem.getProdukt());
                    Produkty prod = prodLuzem.getProdukt();
                    if (indexOfProduct == -1){
                        ProductRecord productRecord = new ProductRecord();
                        productRecord.productName = prod.getNazwa();
                        productRecord.weight = forHowManyDays * prodLuzem.getIloscWG();
                        if (!prod.getWagaJednostki().equals(0.0)){
                            productRecord.packages = productRecord.weight / prod.getWagaJednostki();
                        }
                        else{
                            productRecord.packages = 0.0;
                        }
                        productsList.add(productRecord);
                        result = true;
                    }
                    else{
                        productsList.get(indexOfProduct).weight = productsList.get(indexOfProduct).weight + forHowManyDays * prodLuzem.getIloscWG();
                        if (!prod.getWagaJednostki().equals(0.0)){
                            productsList.get(indexOfProduct).packages = productsList.get(indexOfProduct).weight / prod.getWagaJednostki();
                        }
                        else{
                            productsList.get(indexOfProduct).packages = 0.0;
                        }                        
                        result = true;
                    }
                }
            }
            return result;
    }
    
    private String[] createShoppingStringList(List<ProductRecord> productList){
        Integer size = productList.size();
        String [] list = new String[size];
        for (int i = 0; i < size; i++) {
            list[i] = productList.get(i).productName + ": "
                      + GlobalFun.round(productList.get(i).weight,1) + "g";
            if (!productList.get(i).packages.equals(0.0)){
                list[i] = list[i] + " (" +  GlobalFun.round(productList.get(i).packages,1) + " jed.)";
            }
        }
        return list;
    }
    
    private Integer returnIndexInProductList (List<ProductRecord> productsList, Produkty product){
        for (ProductRecord productRecord : productsList){
            if (productRecord.productName.equals(product.getNazwa())){
                return productsList.indexOf(productRecord);
            }
        }
        return -1;
    }

    //TODO zrobić, żeby wybierało sie ścieżkę gdzie zapisać plik z jadłospisem
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
