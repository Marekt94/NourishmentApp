/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UnitTests;

import Global.GlobalConfig;
import Other.GoogleTaskController;
import Other.ListProductRecord;
import Other.ProductRecord;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author komputer1
 */
public class ExportToGoogleTasksControllerTest {
    
    private GoogleTaskController Controller = null;
    
    public ExportToGoogleTasksControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException, GeneralSecurityException {
        Controller = new GoogleTaskController();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void createTaskWithPackages(){
        Dictionary<String, String> categoriesList = mock(Hashtable.class);
        when(categoriesList.get(anyString())).thenReturn(null);
        
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
        
        List<Task> taskList = Controller.createTasks(productRecordList, categoriesList);
        
        String res = taskList.get(0).getTitle();
        assertEquals(product.weight + " " + GlobalConfig.WEIGHT_UNIT + " (" + product.packages + " " + GlobalConfig.UNIT_SHORTCUT + ")" + " " + product.productName, res);
    }
    
    @Test
    public void createTaskWithoutPackages(){
        Dictionary<String, String> categoriesList = mock(Hashtable.class);
        when(categoriesList.get(anyString())).thenReturn(null);
        
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
        
        List<Task> taskList = Controller.createTasks(productRecordList, categoriesList);
        
        String res = taskList.get(0).getTitle();
        assertEquals(product.weight + " " + GlobalConfig.WEIGHT_UNIT + " " + product.productName, res);
    }  
    
    @Test
    public void ommitWhenProductNameIsNull(){
        Dictionary<String, String> categoriesList = mock(Hashtable.class);
        when(categoriesList.get(anyString())).thenReturn(null);
        
        String cCategory = "owoce";
        Double cPackages = 0.0;
        Double cWeight = 2.0;
        
        ProductRecord product = new ProductRecord();
        product.category = cCategory;
        product.packages = cPackages;
        product.weight = cWeight;
        
        ListProductRecord productRecordList = new ListProductRecord();
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList, categoriesList);
        
        assertEquals(taskList.size(), 0);
    }  
    
    @Test
    public void ommitWhenProductNameIsEmpty(){
        Dictionary<String, String> categoriesList = mock(Hashtable.class);
        when(categoriesList.get(anyString())).thenReturn(null);
        
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
        
        List<Task> taskList = Controller.createTasks(productRecordList, categoriesList);
        
        assertEquals(taskList.size(), 0);
    }

    @Test
    public void createCategoryTaskList() throws IOException{
        Tasks.TasksOperations categoryList = mock(Tasks.TasksOperations.class);
        Tasks.TasksOperations.Insert insertMock = mock(Tasks.TasksOperations.Insert.class);
        Task mockTask = new Task();
        mockTask.setId("test");
        when(categoryList.insert(anyString(), any(Task.class))).thenReturn(insertMock);
        when(insertMock.execute()).thenReturn(mockTask);
        
        ListProductRecord list = new ListProductRecord();
        ProductRecord product = new ProductRecord();
        product.category = "owoce";
        list.add(product);

        product = new ProductRecord();
        product.category = "owoce";
        list.add(product);        
        
        product = new ProductRecord();
        product.category = "warzywa";
        list.add(product);        
        
        product = new ProductRecord();
        product.category = "nabiał";
        list.add(product);        
        
        Dictionary<String, String> categories = Controller.createCategoryTaskList(categoryList, "", list);
        assertEquals(categories.get("owoce"), "test");
        assertEquals(categories.get("warzywa"), "test");
        assertEquals(categories.get("warzywa"), "test");
        assertEquals(categories.size(), 3);
    }
    
    @Test
    public void createTaskWithParentTask(){
        Dictionary<String, String> categoriesList = new Hashtable<>();
        
        String cCategory1 = "owoce";
        String cCategory2 = "nabiał";
        String cCategory1ID = "owoceID";
        String cCategory2ID = "nabiałID";        
        Double cPackages = 0.0;
        String cProductName = "test";
        Double cWeight = 2.0;
        
        categoriesList.put(cCategory1, cCategory1ID);
        categoriesList.put(cCategory2, cCategory2ID);        
        
        ListProductRecord productRecordList = new ListProductRecord();
        ProductRecord product = new ProductRecord();
        product.category = cCategory1;
        product.packages = cPackages;
        product.productName = cProductName;
        product.weight = cWeight;
        productRecordList.add(product);
        
        product = new ProductRecord();
        product.category = cCategory2;
        product.packages = cPackages;
        product.productName = cProductName;
        product.weight = cWeight;                
        productRecordList.add(product);
        
        List<Task> taskList = Controller.createTasks(productRecordList, categoriesList);
        
        assertEquals(taskList.get(0).getParent(), cCategory1ID);       
        assertEquals(taskList.get(1).getParent(), cCategory2ID);
    }
    
}
