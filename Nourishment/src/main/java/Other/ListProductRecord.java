/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author komputer1
 */
public class ListProductRecord extends ArrayList<ProductRecord>{
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
}
