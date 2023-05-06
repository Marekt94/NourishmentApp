/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Marek
 */
public class GlobalFunTest {
    
    public GlobalFunTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
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
    public void testRoundUp1Digit() {
        System.out.println("round");
        Double number = 2.355;
        Integer digits = 1;
        Double expResult = 2.4;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRoundDown1Digit() {
        System.out.println("round");
        Double number = 2.345;
        Integer digits = 1;
        Double expResult = 2.3;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testRoundUp2Digit() {
        System.out.println("round");
        Double number = 2.365;
        Integer digits = 2;
        Double expResult = 2.37;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRoundDown2Digit() {
        System.out.println("round");
        Double number = 2.364;
        Integer digits = 2;
        Double expResult = 2.36;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }  
    
    @Test
    public void testRoundDown0Digit() {
        System.out.println("round");
        Double number = 2.366;
        Integer digits = 0;
        Double expResult = 2.0;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRoundUp0Digit() {
        System.out.println("round");
        Double number = 2.5;
        Integer digits = 0;
        Double expResult = 3.0;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testRoundNullZeroDigit() {
        System.out.println("round");
        Double number = null;
        Integer digits = 0;
        Double expResult = 0.0;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRoundNullOneDigit() {
        System.out.println("round");
        Double number = null;
        Integer digits = 1;
        Double expResult = 0.0;
        Double result = GlobalFun.round(number, digits);
        assertEquals(expResult, result);
    }     
}
