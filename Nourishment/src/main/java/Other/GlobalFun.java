/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import static Other.GlobalConfig.*;
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
    
    public static Double bind(JTextField edt, Double number){
        if (edt.getText().equals(NULL_SIGN) || edt.getText().equals("")){
            number = 0.0;
        }
        else{
            number = new Double(edt.getText());
        }
        return number;
    }

    public static Integer bind(JTextField edt, Integer number){
        if (edt.getText().equals(NULL_SIGN) || edt.getText().equals("")){
            number = 0;
        }
        else{
            number = new Integer(edt.getText());
        }
        return number;
    } 
    
    public static String bind(JTextField edt, String text){
        if (edt.getText().equals(NULL_SIGN) || edt.getText().equals("")){
            text = "";
        }
        else{
            text = edt.getText();
        }
        return text;
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
