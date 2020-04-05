/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.PotrawyWDniu;
import Entities.Produkty;
import Entities.ProduktyWPotrawie;
import Global.GlobalConfig;
import static Global.GlobalConfig.dataFormat;
import Global.GlobalFun;
import static Global.GlobalFun.returnStringOrEmpty;
import Global.ORMManager;
import Interfaces.MyPDFGeneratorInterface;
import Interfaces.MyPanelInterface;
import Other.MyComparator;
import Other.PDFGenerator;
import Other.ProductRecord;
import View.BasicView.BaseListPanel;
import View.BasicView.FilterPanel;
import View.BasicView.MainDialog;
import com.sun.xml.fastinfoset.util.StringArray;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.hibernate.internal.util.compare.ComparableComparator;

/**
 *
 * @author Marek
 */
public class PotrawyWDniuListView extends BaseListPanel {
    List<Potrawy> potrawyList = null;
    String defaultDirectory = "";
    String fileExtension = "pdf";

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
        ORMManager ormManager = ORMManager.getOrmManager();
        
        JPanel filterPanel = new JPanel();
        FilterPanel filter = new FilterPanel();
        filterPanel.setLayout(new BorderLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Opcje"));
        filterPanel.add(filter, BorderLayout.WEST);
        ActionListener actionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                unpack(ORMManager.getOrmManager().filterByDate(PotrawyWDniu.class, filter.getDataFrom(), filter.getDataTo()));
            }            
        };
        filter.btnReset.addActionListener(actionListener);
        PropertyChangeListener dateChange = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getOldValue() != evt.getNewValue()){
                    unpack(ORMManager.getOrmManager().filterByDate(PotrawyWDniu.class, filter.getDataFrom(), filter.getDataTo()));
                }
            }
        };
        filter.getDatePickerFromTextField().addPropertyChangeListener("value", dateChange);
        filter.getDatePickerToTextField().addPropertyChangeListener("value", dateChange);
        this.add(filterPanel, BorderLayout.NORTH);

        JButton btnPrintMenu = new JButton("Drukuj");
        btnPrintMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMenu(objectList);
            }
        });
        addButton(btnPrintMenu);
        JButton btnPrintShoppingList = new JButton("Drukuj liste zakupów");
        btnPrintShoppingList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateShoppingList(objectList);
            }
        });
        addButton(btnPrintShoppingList);
        JButton btnPrintReceipts = new JButton("Drukuj przpepisy");
        btnPrintReceipts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceipts(objectList);
            }
        });
        addButton(btnPrintReceipts);
        
        potrawyList = (List<Potrawy>) ormManager.askForObjects(Potrawy.class);
    }
    
    public void generateReceipts(List<Serializable> listOfDays){
        String fileName = chooseSavePath();
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle("Potrawy od " + ((PotrawyWDniu) listOfDays.get(0)).getData().toString() + " do " + ((PotrawyWDniu) listOfDays.get(listOfDays.size() - 1)).getData().toString());
            createReceipts(listOfDays, pDFGenerator);
            pDFGenerator.closeDocument();
        }          
    }
    
    private void createReceipts(List<Serializable> listOfDays, MyPDFGeneratorInterface pDFGenerator){
        List<Potrawy> potrawyList = new ArrayList<Potrawy>();
        for (int i = 0; i < listOfDays.size(); i++) {
            PotrawyWDniu pwd = (PotrawyWDniu) listOfDays.get(i);
            addToReceipt(pwd.getSniadanie(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getDrugieSniadanie(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getObiad(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getPodwieczorek(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getLunch(), potrawyList, pDFGenerator);
            addToReceipt(pwd.getKolacja(), potrawyList, pDFGenerator);
        }
    }
    
   private void addToReceipt(Potrawy ptr, List<Potrawy> existingOnReceiptMeals, MyPDFGeneratorInterface pDFGenerator){
        if ((ptr != null) && (!existingOnReceiptMeals.contains(ptr))){
            existingOnReceiptMeals.add(ptr);
            pDFGenerator.addSubtitle(ptr.getNazwa());
            HashSet<String> prodList = new HashSet<String>();
            for (ProduktyWPotrawie prod : ptr.getProduktyWPotrawieCollection()){
                prodList.add(prod.getIdProduktu().getNazwa() + ": " + Double.toString(prod.getIloscWG()));
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
        mainWindow.getMyWindowManager().unpackWindow(potrawyList);
        mainWindow.getMyWindowManager().unpackWindow(object);

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
            mainWindow.getMyWindowManager().unpackWindow(potrawyList);
            mainWindow.getMyWindowManager().unpackWindow(object);

            mainWindow.setVisible(true);

            if (mainWindow.getResult()) {
                newOrEditedObjectList.add(object);
            }

            updateView();
        }
    }

    private void generateMenu(List<Serializable> listOfDays) {
        String fileName = chooseSavePath();
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            objectList.sort(new MyComparator("data", PotrawyWDniu.class, true));
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle("Jadłospis od " + ((PotrawyWDniu) listOfDays.get(0)).getData().toString() + " do " + ((PotrawyWDniu) listOfDays.get(listOfDays.size() - 1)).getData().toString());
            for (int i = 0; i < listOfDays.size(); i++) {
                pDFGenerator.addSubtitle(createTitleString(simpleDateformat.format(((PotrawyWDniu) listOfDays.get(i)).getData()), (PotrawyWDniu) listOfDays.get(i)));
                pDFGenerator.addList(createPotrawyStringList((PotrawyWDniu) listOfDays.get(i)));
            }
            pDFGenerator.closeDocument();
        }
    }

    private String chooseSavePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(fileExtension.toUpperCase(), "*." + fileExtension, fileExtension));
        if (defaultDirectory.equals("")) {
            defaultDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop";
        }
        fileChooser.setCurrentDirectory(new File(defaultDirectory));
        int result = fileChooser.showSaveDialog(this);
        defaultDirectory = fileChooser.getCurrentDirectory().getAbsolutePath();
        if (result == JFileChooser.APPROVE_OPTION) {
            return addExtensionIfNecessery(fileChooser.getSelectedFile().getAbsolutePath());
        } else {
            return "";
        }
    }

    private String addExtensionIfNecessery(String fileName) {
        int dotPosition = fileName.lastIndexOf(".");
        if (dotPosition > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + fileExtension;
        } else {
            fileName = fileName + "." + fileExtension;
        }
        return fileName;
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
            return prefix + ": " + potr.toString()
                    + " b: " + GlobalFun.round(potr.getSumaBialko(), 2).toString()
                    + " w: " + GlobalFun.round(potr.getSumaCukrySuma(), 2).toString()
                    + " t: " + GlobalFun.round(potr.getSumaTluszcz(), 2).toString()
                    + " kcal: " + GlobalFun.round(potr.getSumaKcal(), 2).toString();
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
        String fileName = chooseSavePath();
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // musi być w ten sposób, żeby były nazwy dni tygodnia
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle("Lisa zakupów od " + ((PotrawyWDniu) listOfDays.get(0)).getData().toString() + " do " + ((PotrawyWDniu) listOfDays.get(listOfDays.size() - 1)).getData().toString());
            List<ProductRecord> productList = createShoppingList(listOfDays);
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
    
    private List<ProductRecord> createShoppingList(List<Serializable> listOfDays){
        List<ProductRecord> productsList = new ArrayList<ProductRecord>();
        for (Serializable day : listOfDays){
            addToShoppingList(productsList, ((PotrawyWDniu) day).getSniadanie());
            addToShoppingList(productsList, ((PotrawyWDniu) day).getDrugieSniadanie());
            addToShoppingList(productsList, ((PotrawyWDniu) day).getObiad());
            addToShoppingList(productsList, ((PotrawyWDniu) day).getPodwieczorek());
            addToShoppingList(productsList, ((PotrawyWDniu) day).getLunch());
            addToShoppingList(productsList, ((PotrawyWDniu) day).getKolacja());
        }
        
        return productsList; 
    }
    
    private Boolean addToShoppingList(List<ProductRecord> productsList, Potrawy meal){
            Boolean result = false;
            if (meal != null){
                List<ProduktyWPotrawie> prodWPotr = (List<ProduktyWPotrawie>) meal.getProduktyWPotrawieCollection();
                for (ProduktyWPotrawie product : prodWPotr){
                    Integer indexOfProduct = returnIndexInProductList(productsList, product.getIdProduktu());
                    if (indexOfProduct == -1){
                        ProductRecord productRecord = new ProductRecord();
                        productRecord.productName = ((ProduktyWPotrawie) product).getIdProduktu().getNazwa();
                        productRecord.weight = ((ProduktyWPotrawie) product).getIloscWG();
                        if (!((ProduktyWPotrawie) product).getIdProduktu().getWagaJednostki().equals(0.0)){
                            productRecord.packages = productRecord.weight / ((ProduktyWPotrawie) product).getIdProduktu().getWagaJednostki();
                        }
                        else{
                            productRecord.packages = 0.0;
                        }
                        productsList.add(productRecord);
                        result = true;
                    }
                    else{
                        productsList.get(indexOfProduct).weight = productsList.get(indexOfProduct).weight + product.getIloscWG();
                        if (!((ProduktyWPotrawie) product).getIdProduktu().getWagaJednostki().equals(0.0)){
                            productsList.get(indexOfProduct).packages = productsList.get(indexOfProduct).weight / ((ProduktyWPotrawie) product).getIdProduktu().getWagaJednostki();
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
