/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.Objects;

/**
 *
 * @author Marek
 */
public class ProductRecord {
      public String productName;
      public Double weight;
      public Double packages;
      public String category;
      
      public boolean equals(ProductRecord o){
          return    this.productName.equalsIgnoreCase(o.productName)
                 && this.category.equalsIgnoreCase(o.category)
                 && (Objects.equals(this.packages, o.packages))
                 && (Objects.equals(this.weight, o.weight));
      }
}
