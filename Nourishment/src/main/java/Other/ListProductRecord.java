/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<String> positionsToStringList(){
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < this.size() - 1; i++) {
            stringList.add(ProductRecordFun.createShoppingListPosition(this.get(i)));
        }
        return stringList;
    }   
    
    public void stringListToPositions(String[] stringList){
        for (String productString : stringList) {
            ProductRecord newProduct = ProductRecordFun.stringToProduct(productString);
            if (newProduct != null){
                ProductRecord product = this.findByName(productString);
                if (product == null){
                    product = new ProductRecord();
                    this.add(product);
                    product.productName = newProduct.productName;
                    product.category = newProduct.category;
                }
                product.packages = newProduct.packages;
                product.weight = newProduct.weight;
            }
        }
    }    
}
