/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Global.GlobalFun;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Marek
 */
public class TableUpdater {
    List<String> columnOrder = null;
    
    public TableUpdater(){
        columnOrder = new ArrayList<String>();
    }
            
    public <E> void updateTable(List<E> list, JTable table, List<String> omittedColumns) throws InvocationTargetException{
        if (!checkRequirements(list, table)){return;}
        List<String> choosenColumns = createChosenColumsList(list.get(0).getClass(), omittedColumns);
        Field[] fields = setFieldsToShow(table, list, choosenColumns);
        createColumns(fields, table);
        fillTable(table, list, fields);
    }
    
    private List<String> createChosenColumsList(Class objectClass, List<String> omittedColumns){
        List<String> choosenColumns = new ArrayList<String>();
        for (Field field : objectClass.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!isInOmitted(fieldName, omittedColumns) && (!field.getType().equals(Collection.class))){
                choosenColumns.add(fieldName);
            }
        }
        return choosenColumns;
    }
    
    private Boolean isInOmitted(String columnName, List<String> omittedColumns){
        if (omittedColumns == null || omittedColumns.size() == 0) {
            return false;
        }
        for (String name : omittedColumns){
            if (name.equals(columnName)){
                return true;
            }
        }
        return false;
    }
    
    private <E> Boolean checkRequirements(List<E> list, JTable table){
        if ((list == null) || (list.size() < 1)){
            ((DefaultTableModel) table.getModel()).setRowCount(0);
            return false;
        }
        else {
            return true;
        }
    }
    
    private <E> Field[] setFieldsToShow(JTable table, List<E> list, List<String> choosenColumns){
        List<Field> fieldsList = new ArrayList<>();
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnModel().getColumn(i).getHeaderValue().toString(); 
            if (isInChoosen(columnName, choosenColumns)) {
                try {
                    fieldsList.add(list.get(0).getClass().getDeclaredField(columnName));
                    choosenColumns.remove(columnName);
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(TableUpdater.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(TableUpdater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (String choosenColumn : choosenColumns) {
            try {
                fieldsList.add(list.get(0).getClass().getDeclaredField(choosenColumn));
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(TableUpdater.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(TableUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fieldsList.toArray(new Field[fieldsList.size()]);
    }
    
    private Boolean isInChoosen(String columnName, List<String> choosenColumns){
        if ((choosenColumns == null) || (choosenColumns.size() == 0)){
            return false;
        }
        for (String columnNameTemp : choosenColumns){
            if (columnName.equals(columnNameTemp)){
                return true;
            }
        }
        return false;
    }
    
    private void createColumns(Field[] fields, JTable table){
        ((DefaultTableModel) table.getModel()).setColumnCount(0);
        while(table.getColumnCount() < fields.length){
            TableColumn tblcol = new TableColumn();
            ((DefaultTableModel) table.getModel()).addColumn("");
        }
        
        for (int i = 0; i < fields.length; i++) {
            table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(fields[i].getName());
        }        
    }
    
    private <E> void fillTable(JTable table, List<E> list, Field[] fields) throws InvocationTargetException{
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        Object value;
        for (int j = 0; j < list.size(); j++) {
            String[] row = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    Boolean oldAccess;
                    Method method = null;
                    value = null;
                    
                    oldAccess = fields[i].canAccess(list.get(j));
                    if (oldAccess == false){
                        fields[i].setAccessible(true);
                        method = findGetter(fields[i]);
                        if (method != null){
                            value = method.invoke(list.get(j));
                        }
                    }
                    else{
                        if (fields[i].get(list.get(j)) != null){
                            value = fields[i].get(list.get(j)); 
                        }
                    }
                    if (value != null){
                        if (fields[i].get(list.get(j)).getClass().equals(Double.class)){
                            row[i] = GlobalFun.round((Double) value, null).toString();
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
    
    private Method findGetter (Field field){
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
    
    private void getColumnOrder(JTable table){
        columnOrder.clear();
        for (int i = 0; i < table.getColumnCount(); i++) {
            columnOrder.add(table.getColumnModel().getColumn(i).getHeaderValue().toString());
        }
    }
    
    private void setColumnOrder(JTable table){
        List<Integer> movedColumns = new ArrayList<Integer>();
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn tblColumn = table.getColumnModel().getColumn(i);
            if ((i < columnOrder.size()) && (!tblColumn.getIdentifier().equals(columnOrder.get(i)))){
                if (!movedColumns.contains(i)){
                    if (columnOrder.contains(tblColumn.getIdentifier())){
                        int newIndex = columnOrder.indexOf(tblColumn.getIdentifier());
                        movedColumns.add(newIndex);
                        if (newIndex <= (table.getColumnCount() - 1)){
                            table.moveColumn(i, newIndex);
                        }
                    }
                }
            }
        }
    }
}
