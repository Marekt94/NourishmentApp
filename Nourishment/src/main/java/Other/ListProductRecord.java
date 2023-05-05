/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import Global.GlobalConfig;
import Global.GlobalFun;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.bytebuddy.agent.builder.AgentBuilder;

/**
 *
 * @author komputer1
 */
public class ListProductRecord extends ArrayList<ProductRecord>{
    static final String SEPARATOR = ":";
    
    public ProductRecord findByName(String name){
        return this.stream().filter(object -> name.equals(object.productName)).findFirst().orElse(null);
    }
    
    public void sortByCategory(){
        this.sort(new Comparator<ProductRecord>() {
            @Override
            public int compare(ProductRecord o1, ProductRecord o2) {
                int res = o1.category.compareTo(o2.category); 
                if (res == 0){
                    res = o1.productName.compareTo(o2.productName);
                }
                return res;
            }
        });        
    }
    
    public void sortByName(){
        this.sort(new Comparator<ProductRecord>() {
            @Override
            public int compare(ProductRecord o1, ProductRecord o2) {
                return o1.productName.compareTo(o2.productName); 
            }
        });        
    }

    public List<String> positionsToStringList(){
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < this.size() - 1; i++) {
            stringList.add(createShoppingListPosition(this.get(i)));
        }
        return stringList;
    }   
    
    public static String createShoppingListPosition(ProductRecord product){
        String position = product.productName + SEPARATOR + " "
                          + GlobalFun.round(product.weight,1) + GlobalConfig.WEIGHT_UNIT;
        if (!product.packages.equals(0.0)){
            position = position + " (" +  GlobalFun.round(product.packages,1) + " " +  GlobalConfig.UNIT_SHORTCUT + ")";
        }        
        return position;
    } 
    
    private Integer getSepearatorIndex(String productString){
      return productString.lastIndexOf(SEPARATOR);    
    }
    
    private String cutProductName(String productString){
      Integer separatorIndex = getSepearatorIndex(productString);
      if (separatorIndex > -1){
          return productString.substring(0, separatorIndex).trim();
      }
      else{
          return productString;
      }
    }
    
    private Double cutWeight(String productString){
      Integer separatorIndex = productString.lastIndexOf(SEPARATOR);
      if (separatorIndex > -1){
          return Double.valueOf(productString.substring(separatorIndex + 1, productString.lastIndexOf(GlobalConfig.WEIGHT_UNIT)).trim());
      }
      else{
          return 0.0;
      }        
    }
    
    private Double cutPackages(String productString){
        Integer packageStartIndex = productString.lastIndexOf("(");
        Integer packageEndIndex = productString.indexOf(GlobalConfig.UNIT_SHORTCUT, packageStartIndex);
        if (packageStartIndex > -1){
          return Double.valueOf(productString.substring(packageStartIndex + 1, packageEndIndex).trim());
        }       
        else{
            return 0.0;
        }
    }
    
    public void stringListToPositions(String[] stringList){
        for (String productString : stringList) {
            if (productString.trim().isEmpty()){continue;}
            
            String productName = cutProductName(productString);
            Double weight = cutWeight(productString);
            Double packages = cutPackages(productString);
            ProductRecord product = this.findByName(productName);
            if (product == null){
                product = new ProductRecord();
                product.productName = productName;
                product.category = "inne";
                this.add(product);
            }
            product.packages = packages;
            product.weight = weight;            
        }
    }    
}
