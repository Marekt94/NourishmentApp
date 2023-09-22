/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package IntegrationTests;

import Other.GoogleTaskController;
import Other.ListProductRecord;
import Other.ProductRecord;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author komputer1
 */
public class ExportToGooleTasksControllerIntegrationTest {
    private GoogleTaskController Controller = null;
    
    public ExportToGooleTasksControllerIntegrationTest() {
    }
    
    //@Test
    public void createTasks() throws IOException, GeneralSecurityException{
        Controller = new GoogleTaskController();
        
        ListProductRecord list = new ListProductRecord();
        ProductRecord product = new ProductRecord();
        product.category = "owoce";
        product.packages = 1.0;
        product.productName = "testowy 1";
        product.weight = 1.0;
        list.add(product);
        
        product = new ProductRecord();
        product.category = "nabiał";
        product.packages = 2.0;
        product.productName = "testowy 2";
        product.weight = 2.0;
        list.add(product);        
        
        Controller.sendToGoogleTasks(list, "lista testowa");
    }
    
}
