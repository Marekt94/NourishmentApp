/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UnitTests;

import Global.GlobalConfig;
import Other.ExportToGoogleTaskController;
import Other.ListProductRecord;
import Other.ProductRecord;
import com.google.api.services.tasks.model.Task;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author komputer1
 */
public class ExportToGoogleTasksControllerTest {
    
    private ExportToGoogleTaskController Controller = null;
    
    public ExportToGoogleTasksControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        Controller = new ExportToGoogleTaskController();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void createTaskWithPackages(){
        String cCategory = "owoce";
        Double cPackages = 1.0;
        String cProductName = "produkt testowy";
        Double cWeight = 2.0;
        
        ProductRecord product = new ProductRecord();
        product.category = cCategory;
        product.packages = cPackages;
        product.productName = cProductName;
        product.weight = cWeight;
        
        ListProductRecord productRecordList = new ListProductRecord();
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList);
        
        String res = taskList.get(0).getTitle();
        assertEquals(product.weight + " " + GlobalConfig.WEIGHT_UNIT + " (" + product.packages + " " + GlobalConfig.UNIT_SHORTCUT + ")" + " " + product.productName, res);
    }
    
    @Test
    public void createTaskWithoutPackages(){
        String cCategory = "owoce";
        Double cPackages = 0.0;
        String cProductName = "produkt testowy";
        Double cWeight = 2.0;
        
        ProductRecord product = new ProductRecord();
        product.category = cCategory;
        product.packages = cPackages;
        product.productName = cProductName;
        product.weight = cWeight;
        
        ListProductRecord productRecordList = new ListProductRecord();
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList);
        
        String res = taskList.get(0).getTitle();
        assertEquals(product.weight + " " + GlobalConfig.WEIGHT_UNIT + " " + product.productName, res);
    }  
    
    @Test
    public void ommitWhenProductNameIsNull(){
        String cCategory = "owoce";
        Double cPackages = 0.0;
        Double cWeight = 2.0;
        
        ProductRecord product = new ProductRecord();
        product.category = cCategory;
        product.packages = cPackages;
        product.weight = cWeight;
        
        ListProductRecord productRecordList = new ListProductRecord();
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList);
        
        assertEquals(taskList.size(), 0);
    }  
    
    @Test
    public void ommitWhenProductNameIsEmpty(){
        String cCategory = "owoce";
        Double cPackages = 0.0;
        String cProductName = " ";
        Double cWeight = 2.0;
        
        ProductRecord product = new ProductRecord();
        product.category = cCategory;
        product.packages = cPackages;
        product.productName = cProductName;
        product.weight = cWeight;
        
        ListProductRecord productRecordList = new ListProductRecord();
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList);
        
        assertEquals(taskList.size(), 0);
    }    
    
}
