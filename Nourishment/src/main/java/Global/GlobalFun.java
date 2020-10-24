/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import static Global.GlobalConfig.*;
import Other.FileDialogFunctionType;
import Other.TableUpdater;
import java.awt.Component;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author Marek
 */
public class GlobalFun {
    public static void bind(String text, JTextComponent edt){
        if (text == null || text.equals("")){
            edt.setText(NULL_SIGN);
        }
        else{
            edt.setText(text);
        }
    }
    
    public static void bind(Integer number, JTextComponent edt){
        if (number != null){
            edt.setText(number.toString());
        }
        else{
            edt.setText(NULL_SIGN);
        }        
    }
    
    public static void bind(Character value, char valueForSelected, JCheckBox chb){
        if ((value != null) && (value == valueForSelected)){
            chb.setSelected(true);
        }
        else{
            chb.setSelected(false);
        }
    }
    
    public static void bind(Double number, JTextComponent edt){
        if (number != null){
            number = round(number, null);
            edt.setText(number.toString());
        }
        else{
            edt.setText(NULL_SIGN);
        }         
    }
    
    public static char bind(JCheckBox chb, char valueForSelected, char valueForUnselected){
        if (chb.isSelected()){
            return valueForSelected;
        }
        else{
            return valueForUnselected;
        }
    }
    
    public static void bind(Serializable obj, JComboBox cmb, Serializable defaultObject){
        if (obj != null){
            cmb.setSelectedItem(obj);
        }
        else{
            cmb.setSelectedItem(defaultObject);
        }         
    }
    
    public static void bind(Serializable obj, JComboBox cmb){
        bind(obj, cmb, null);
    }
    
    public static Object bind(JTextComponent edt, Class type){
        Boolean notEmpty;
 
        notEmpty = !(edt.getText().equals(NULL_SIGN) || edt.getText().trim().equals(""));
        
        if (type == Double.class){
            if (notEmpty){
                    DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
                    DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
                    String valueStr = edt.getText().replaceAll(Character.toString(symbols.getDecimalSeparator()),".");
                    Double valueDoub = new Double(valueStr); 
                    return valueDoub;
            }
            else{
                return 0.0;
            }
        }
        
        if (type == Integer.class){
            if (notEmpty){
                return new Integer(edt.getText());
            }
            else{
                return 0;
            }
        }
        
        if (type == String.class){
            if (notEmpty){
                return edt.getText().trim();
            }
            else{
                return "";
            }
        }
        
        return NULL_SIGN;
    }    
    
    public static Serializable bind(JComboBox cmb){
        Serializable item = (Serializable) cmb.getSelectedItem();         
        return item;
    }
    
    public static Double round(Double number, Integer digits){
        if (number == null){
            return 0.0;
        }
        if (digits != null){
            return new BigDecimal(number).setScale(digits, RoundingMode.HALF_UP).doubleValue();
        }
        else{
            return new BigDecimal(number).setScale(DIGITS, RoundingMode.HALF_UP).doubleValue(); 
        }
    }
    
    public static <E> void updateTable(Collection<E> list, JTable table){
        updateTable(toList((Collection<? extends Serializable>) list), table);
    }
    
    public static <E> void updateTable(List<E> list, JTable table){
        updateTable(list, table, new ArrayList<String>());
    }
    
    public static <E> void updateTable(List<E> list, JTable table, List<String> omittedColumns){
        TableUpdater tableUpdater = new TableUpdater();
        try {     
            tableUpdater.updateTable(list, table, omittedColumns);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GlobalFun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static <E extends Serializable> void deepListCopy(List<E> referenceList, List<E> newList){
        for (E object : referenceList){
            newList.add(SerializationUtils.clone(object));
        }
    }
    
    public static List<? extends Serializable> toList(Collection<? extends Serializable> collection){
        if (collection != null){
            return collection.stream().collect(Collectors.toList());
        }
        return null;
    }
    
    public static void unpackComboBox(JComboBox combobox, List<Serializable> list){
        combobox.removeAllItems();
        combobox.addItem(null);
        for (Serializable obj : list){
            combobox.addItem(obj);
        }
    }
   
    public static String choosePath(Component parent, String fileExtension, FileDialogFunctionType type, StringBuilder defaultDirectory) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.getActionMap().get("viewTypeDetails").actionPerformed(null);
        fileChooser.setFileFilter(new FileNameExtensionFilter(fileExtension.toUpperCase(), "*." + fileExtension, fileExtension));
        if (defaultDirectory.toString().equals("")) {
            defaultDirectory.append(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
        }
        int result = 0;
        fileChooser.setCurrentDirectory(new File(defaultDirectory.toString()));
        switch (type){
            case fdftOpen:{
                result = fileChooser.showOpenDialog(parent);
                break;
            }
            case fdftSave:{
                result = fileChooser.showSaveDialog(parent);   
                break;
            }
            default:{
                result = fileChooser.showDialog(parent, "Wybierz"); 
                break;
            }
        }
        defaultDirectory.setLength(0);
        defaultDirectory.append(fileChooser.getCurrentDirectory().getAbsolutePath());
        if (result == JFileChooser.APPROVE_OPTION) {
            return addExtensionIfNecessery(fileChooser.getSelectedFile().getAbsolutePath(), fileExtension);
        } else {
            return "";
        }
    }
    
     private static String addExtensionIfNecessery(String fileName, String fileExtension) {
        int dotPosition = fileName.lastIndexOf(".");
        if (dotPosition > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + fileExtension;
        } else {
            fileName = fileName + "." + fileExtension;
        }
        return fileName;
    }
     
    public static char dBTrue(){
        return '1';
    }
    
    public static char dBFalse(){
        return '0';
    }
    
    public static Method findGetter (Field field){
        for (Method method : field.getDeclaringClass().getMethods()){
            Boolean res = false;
            String methodName = method.getName().toLowerCase();
            String getterName = ("get" + field.getName()).toLowerCase();
            if (methodName.equals(getterName)){
                if(method.getParameterTypes().length == 0){
                    if(!void.class.equals(method.getReturnType())){
                        res = true;
                    }
                }
            }
            if (res){
                return method;
            }
        }
        return null;
    }    
    
}
