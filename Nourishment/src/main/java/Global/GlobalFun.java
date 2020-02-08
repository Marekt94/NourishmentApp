/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import Entities.Potrawy;
import Entities.Produkty;
import static Global.GlobalConfig.*;
import java.io.Serializable;
import static java.lang.System.exit;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author Marek
 */
public class GlobalFun {
    public static void bind(String text, JTextField edt){
        if (text != null){
            edt.setText(text);
        }
        else{
            edt.setText(NULL_SIGN);
        }
    }
    
    public static void bind(Integer number, JTextField edt){
        if (number != null){
            edt.setText(number.toString());
        }
        else{
            edt.setText(NULL_SIGN);
        }        
    }
    
    public static void bind(Double number, JTextField edt){
        if (number != null){
            number = round(number, null);
            edt.setText(number.toString());
        }
        else{
            edt.setText(NULL_SIGN);
        }         
    }
    
    public static void bind(Serializable obj, JComboBox cmb){
        if (obj != null){
            cmb.setSelectedItem(obj);
        }
        else{
            cmb.setSelectedItem(null);
        }         
    }

    public static Object bind(JTextField edt, Class type){
        Boolean notEmpty;
 
        if (edt.getText().equals(NULL_SIGN) || edt.getText().equals("")){
            notEmpty = false;
        }
        else{
            notEmpty = true;
        }
        
        if (type == Double.class){
            if (notEmpty){
                return new Double(edt.getText());
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
                return edt.getText();
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
        if (digits != null){
            return new BigDecimal(number).setScale(digits, RoundingMode.HALF_UP).doubleValue();
        }
        else{
            return new BigDecimal(number).setScale(DIGITS, RoundingMode.HALF_UP).doubleValue(); 
        }
    }
    
    public static <E> void updateTable(Collection<E> list, JTable table){
        updateTable(toList(list), table);
    }
    
    private static Boolean isInOmmited(String columnName, String[] ommitedColumns){
        if (ommitedColumns.length == 0){
            return false;
        }
        for (String columnNameTemp : ommitedColumns){
            if (columnName == columnNameTemp){
                return true;
            }
        }
        return false;
    }
    
    public static <E> void updateTable(List<E> list, JTable table){
        updateTable(list, table, new String[0]);
    }
    
    public static <E> void updateTable(List<E> list, JTable table, String[] ommitedColumns){
        if (list.size() < 1){
            ((DefaultTableModel) table.getModel()).setRowCount(0);
            return;
        }
        List<Field> fieldsList = new ArrayList<>();
        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            if ((field.getName() != "serialVersionUID") && (!field.getType().equals(Collection.class))){
                if (!isInOmmited(field.getName(), ommitedColumns)){
                    fieldsList.add(field);
                }
            }

        }
        Field[] fields = fieldsList.toArray(new Field[fieldsList.size()]);
        
        ((DefaultTableModel) table.getModel()).setColumnCount(0);
        while(table.getColumnCount() < fields.length){
            TableColumn tblcol = new TableColumn();
            ((DefaultTableModel) table.getModel()).addColumn("");
        }
        
        for (int i = 0; i < fields.length; i++) {
            table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(fields[i].getName());
        }
        
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (int j = 0; j < list.size(); j++) {
            String[] row = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    Boolean oldAccess;
                    oldAccess =  fields[i].canAccess(list.get(j));
                    fields[i].setAccessible(true);
                    if (fields[i].get(list.get(j)) != null){
                        if (fields[i].get(list.get(j)).getClass().equals(Double.class)){
                            row[i] = GlobalFun.round((Double) fields[i].get(list.get(j)), null).toString();
                        }
                        else{
                            row[i] = fields[i].get(list.get(j)).toString();
                        }
                    }
                    fields[i].setAccessible(oldAccess);
                } catch (IllegalArgumentException ex) {
                    System.out.print("Error in updateTable\n");
                } catch (IllegalAccessException ex) {
                    System.out.print("Error in updateTable\n");
                }

            }
            ((DefaultTableModel) table.getModel()).addRow(row);
        }        
    }
    
    public static <E extends Serializable> void deepListCopy(List<E> referenceList, List<E> newList){
        for (E object : referenceList){
            newList.add(SerializationUtils.clone(object));
        }
    }
    
    public static <E> List<E> toList(Collection<E> collection){
        return collection.stream().collect(Collectors.toList());
    }
    
    public static void unpackComboBox(JComboBox combobox, List<Serializable> list){
        combobox.addItem(null);
        for (Serializable obj : list){
            combobox.addItem(obj);
        }
    }
    
    
}
