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
    
    public void stringListToPositions(String[] stringList){
        for (String productString : stringList) {
            String productName;
            Integer weightIndex = productString.indexOf(SEPARATOR);
            if (weightIndex > -1){
                productName = productString.substring(0, weightIndex);
            }
            else{
                productName = productString;
            }
            ProductRecord product = this.findByName(productName);
            if (product == null){
                product = new ProductRecord();
                product.productName = productName;
                product.category = "inne";
                product.packages = 0.0;
                product.weight = 0.0;
                this.add(product);
            }
        }
    }    
}
