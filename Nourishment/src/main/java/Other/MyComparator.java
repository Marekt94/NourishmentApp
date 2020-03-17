/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek
 */
public class MyComparator implements Comparator{
    Field field = null;
    Boolean ascending = null;
    
    public MyComparator(){
        
    }
    
    public void init(String fieldName, Class objectClass, Boolean ascending) throws NoSuchFieldException{
        field = objectClass.getDeclaredField(fieldName);
        this.ascending = ascending;
    }
    
    @Override
    public int compare(Object o1, Object o2) {
        try {
            if (field != null){
                Boolean oldAccess = field.canAccess(o1);
                Integer result = null;
                
                field.setAccessible(true);
                if (field.getType().equals(Integer.class)){
                    result = ((Integer) field.get(o2)) - ((Integer) field.get(o1));
                }
                else if(field.getType().equals(Double.class)){
                    result = (Integer) Math.round((float) (((Double) field.get(o1)) - ((Double) field.get(o2))));    
                }
                else if(field.getType().equals(String.class)){
                    result = ((String)field.get(o2)).compareTo((String) field.get(o1));
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
