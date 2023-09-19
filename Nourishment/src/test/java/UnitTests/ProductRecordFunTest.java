/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UnitTests;

import Other.ProductRecord;
import Other.ProductRecordFun;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author komputer1
 */
public class ProductRecordFunTest {
    
    public ProductRecordFunTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("Global.ProductRecordFunTest.setUpClass()");
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }    
    
    @Test
    public void testGiveProdutNameWhenProperSeparator(){
        String given = "test:";
        String when = ProductRecordFun.cutProductName(given);
        String then = "test";
        
        assertTrue(when.equals(then));
    }
    
    @Test
    public void testGiveProductNameWhenNoSpearator(){
        String given = "test";
        String when = ProductRecordFun.cutProductName(given);
        String then = "test";
        
        assertTrue(when.equals(then));        
    }
    
    @Test
    public void testGiveProductNameWhenNoSeparatorAndHaveWeight(){
        String given = "test 18.0g";
        String when = ProductRecordFun.cutProductName(given);
        String then = "test";
        
        assertTrue(when.equals(then));        
    }
    
    @Test
    public void testGiveProductWhenProperPattern(){
        String given = "test: 18.0g (12.0 jed.)";
        ProductRecord when = ProductRecordFun.stringToProduct(given);
        ProductRecord then = new ProductRecord();
        then.category = ProductRecordFun.DEFAULT_CATEGORY_NAME;
        then.packages = 12.0;
        then.productName = "test";
        then.weight = 18.0;
        
        assertTrue(when.equals(then));
    }
    
    @Test
    public void testGiveProductWhenProperPatternWithoutSeparator(){
        String given = "test 18.0g (12.0 jed.)";
        ProductRecord when = ProductRecordFun.stringToProduct(given);
        ProductRecord then = new ProductRecord();
        then.category = ProductRecordFun.DEFAULT_CATEGORY_NAME;
        then.packages = 12.0;
        then.productName = "test";
        then.weight = 18.0;
        
        assertTrue(when.equals(then));
    }    
    
    @Test
    public void testGiveProductWhenProperPatternWithoutSeparatorAndSpace(){
        String given = "test18.0g (12.0 jed.)";
        ProductRecord when = ProductRecordFun.stringToProduct(given);
        ProductRecord then = new ProductRecord();
        then.category = ProductRecordFun.DEFAULT_CATEGORY_NAME;
        then.packages = 12.0;
        then.productName = "test";
        then.weight = 18.0;
        
        assertFalse(when.equals(then));
    }        
}
