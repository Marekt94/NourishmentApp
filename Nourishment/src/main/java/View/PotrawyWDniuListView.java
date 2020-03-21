/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Potrawy;
import Entities.PotrawyWDniu;
import Global.GlobalFun;
import static Global.GlobalFun.returnStringOrEmpty;
import Global.ORMManager;
import Interfaces.MyPDFGeneratorInterface;
import Interfaces.MyPanelInterface;
import Other.MyComparator;
import Other.PDFGenerator;
import View.BasicView.BaseListPanel;
import View.BasicView.FilterPanel;
import View.BasicView.MainDialog;
import com.sun.xml.fastinfoset.util.StringArray;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
    MyComparator comparatorByDate = null;
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
        filter.btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unpack(ORMManager.getOrmManager().filterByDate(PotrawyWDniu.class, filter.getDataFrom(), filter.getDataTo()));
            }
        });

        this.add(filterPanel, BorderLayout.NORTH);

        JButton printButton = new JButton("Drukuj");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMenu(objectList);
            }
        });
        addButton(printButton);
        potrawyList = (List<Potrawy>) ormManager.askForObjects(Potrawy.class);
        comparatorByDate = new MyComparator();
        try {
            comparatorByDate.init("data", PotrawyWDniu.class, true);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(PotrawyWDniuListView.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public void updateView() {
        super.updateView();
    }

    private void generateMenu(List<Serializable> list) {
        String fileName = chooseSavePath();
        if (!fileName.equals("")) {
            MyPDFGeneratorInterface pDFGenerator = new PDFGenerator();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", new Locale("pl", "PL")); // the day of the week spelled out completely
            objectList.sort(comparatorByDate);
            pDFGenerator.openDocument(fileName);
            pDFGenerator.addTitle("Jadłospis od " + ((PotrawyWDniu) list.get(0)).getData().toString() + " do " + ((PotrawyWDniu) list.get(list.size() - 1)).getData().toString());
            for (int i = 0; i < list.size(); i++) {
                pDFGenerator.addSubtitle(createTitleString(simpleDateformat.format(((PotrawyWDniu) list.get(i)).getData()), (PotrawyWDniu) list.get(i)));
                pDFGenerator.addList(createPotrawyStringList((PotrawyWDniu) list.get(i)));
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
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
        } else {
            fileName = fileName + ".pdf";
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
