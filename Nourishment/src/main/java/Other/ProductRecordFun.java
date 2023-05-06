/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import Global.GlobalConfig;
import Global.GlobalFun;

/**
 *
 * @author komputer1
 */
public class ProductRecordFun {
    static final String SEPARATOR = ":";

    public static String createShoppingListPosition(ProductRecord product){
        String position = product.productName + SEPARATOR + " "
                          + GlobalFun.round(product.weight,1) + GlobalConfig.WEIGHT_UNIT;
        if (!product.packages.equals(0.0)){
            position = position + " (" +  GlobalFun.round(product.packages,1) + " " +  GlobalConfig.UNIT_SHORTCUT + ")";
        }        
        return position;
    }     
    
    private static Integer getSepearatorIndex(String productString){
      Integer separatorIndex = productString.lastIndexOf(SEPARATOR);    
      if (separatorIndex == -1){
          for (int i = 0; i < productString.length() - 1; i++) {
              if (Character.isDigit(productString.charAt(i))){
                  if (Character.isSpaceChar(productString.charAt(i - 1))){
                  return i - 1;
                  }
              }
              
          }
      }
      return separatorIndex;
    }
        
    public static String cutProductName(String productString){
      Integer separatorIndex = getSepearatorIndex(productString);
      if (separatorIndex > -1){
          return productString.substring(0, separatorIndex).trim();
      }
      else{
          return productString;
      }
    }
    
    public static Double cutWeight(String productString){
      Integer separatorIndex = getSepearatorIndex(productString);
      if (separatorIndex > -1){
          return Double.valueOf(productString.substring(separatorIndex + 1, productString.lastIndexOf(GlobalConfig.WEIGHT_UNIT)).trim());
      }
      else{
          return 0.0;
      }        
    }
    
    public static Double cutPackages(String productString){
        Integer packageStartIndex = productString.lastIndexOf("(");
        Integer packageEndIndex = productString.indexOf(GlobalConfig.UNIT_SHORTCUT, packageStartIndex);
        if (packageStartIndex > -1){
          return Double.valueOf(productString.substring(packageStartIndex + 1, packageEndIndex).trim());
        }       
        else{
            return 0.0;
        }
    }  
    
    public static ProductRecord stringToProduct(String productString){
        if (productString.trim().isEmpty()){return null;}

        ProductRecord product = new ProductRecord();
        
        String productName = ProductRecordFun.cutProductName(productString);
        Double weight = ProductRecordFun.cutWeight(productString);
        Double packages = ProductRecordFun.cutPackages(productString);
        
        product.productName = productName;
        product.category = "inne";            
        product.packages = packages;
        product.weight = weight;          
        
        return product;
    }
}
