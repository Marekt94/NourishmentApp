/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import static Global.GlobalConfig.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JTextField;

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
    
    public static Double round(Double number, Integer digits){
        if (digits != null){
            return new BigDecimal(number).setScale(digits, RoundingMode.HALF_UP).doubleValue();
        }
        else{
            return new BigDecimal(number).setScale(DIGITS, RoundingMode.HALF_UP).doubleValue(); 
        }
    }
    
    
}
