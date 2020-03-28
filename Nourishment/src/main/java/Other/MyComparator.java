/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek
 */
public class MyComparator implements Comparator{
    Field field = null;
    Boolean ascending = null;
    
    public MyComparator(String fieldName, Class objectClass, Boolean ascending){
        try {
            field = objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(MyComparator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MyComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ascending = ascending;        
    }
    
//    public void init(String fieldName, Class objectClass, Boolean ascending) throws NoSuchFieldException{
//        field = objectClass.getDeclaredField(fieldName);
//        this.ascending = ascending;
//    }
    
    @Override
    public int compare(Object o1, Object o2) {
        try {
            if (field != null){
                Boolean oldAccess = field.canAccess(o1);
                Integer result = 0;
                field.setAccessible(true);
                Object value1 = field.get(o1);
                Object value2 = field.get(o2);
                
                if (field.getType().equals(Integer.class)){
                    if (value1 == null){
                        value1 = 0;
                    }
                    if (value2 == null){
                        value2 = 0;
                    }
                    result = ((Integer) value2) - ((Integer) value1);
                }
                else if(field.getType().equals(Double.class)){
                    if (value1 == null){
                        value1 = 0.0;
                    }
                    if (value2 == null){
                        value2 = 0.0;
                    }
                    result = (Integer) Math.round((float) (((Double) value2) - ((Double) value1)));    
                }
                else if(field.getType().equals(String.class)){
                    if (value1 == null){
                        value1 = "";
                    }
                    if (value2 == null){
                        value2 = "";
                    }
                    result = ((String) value2).compareTo((String) value1);
                }
                else if(field.getType().equals(Date.class)){
                    if (value1 == null){
                        value1 = 0;
                    }
                    if (value2 == null){
                        value2 = 0;
                    }
                    result = ((Date) value2).compareTo((Date) value1);
                }
                
                if (ascending){
                    result = -result;
                }
                field.setAccessible(oldAccess);
                
                return result;                
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MyComparator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
