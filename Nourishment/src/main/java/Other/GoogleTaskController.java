/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import Global.GlobalConfig;
import Interfaces.InterfaceProgressController;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author komputer1
 */
public class GoogleTaskController {
    public final String PROFILE = "https://www.googleapis.com/auth/userinfo.profile";
    public final String EMAIL = "https://www.googleapis.com/auth/userinfo.profile";
    public final String OPEN_ID = "openid";
    
    private Tasks service;    
    private InterfaceProgressController dlg;

    public void setDlg(InterfaceProgressController dlg) {
        this.dlg = dlg;
    }
    
    public GoogleTaskController() throws IOException, GeneralSecurityException{
        List<String> scopes = new ArrayList<>();
        scopes.add(EMAIL);
        scopes.add(PROFILE);
        scopes.add(OPEN_ID);
        scopes.add(TasksScopes.TASKS);
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Tasks.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), GoogleAuthorizator.getCredentials(HTTP_TRANSPORT, scopes))
            .setApplicationName("Nourishment")
            .build();          
    }
    
    public List<Task> createTasks(ListProductRecord productList, Dictionary<String, String> categoriesList){
        List<Task> tasksList = new ArrayList<>();
        for (ProductRecord product : productList) {
            if (!((product.productName == null) || product.productName.isBlank())) {
                Task task = new Task();
                String title;
                if (product.packages.equals(0.0)) {
                    title = product.weight + " " + GlobalConfig.WEIGHT_UNIT + " " + product.productName;                
                }
                else{
                    title = product.weight + " " + GlobalConfig.WEIGHT_UNIT + " (" + product.packages + " " + GlobalConfig.UNIT_SHORTCUT + ")" + " " + product.productName;
                }
                task.setTitle(title);
                String categoryID = categoriesList.get(product.category);
                if (categoryID != null){
                    task.setParent(categoryID);
                }
                tasksList.add(task);
            }
        }
        return tasksList;
    }
    
    public boolean exportTasks(List<Task> tasklist, String listID) throws IOException{
        if (dlg != null){
            dlg.setText("Wysyłanie listy zakupów do Google Task...");
            dlg.setMax(tasklist.size());
        }
        for (int i = 0; i < tasklist.size() - 1; i++) {
            if (dlg != null){
                dlg.setProgress(i);
            }
            Task task = tasklist.get(i);
            try {
                String parent = task.getParent();
                task = service.tasks().insert(listID, task).execute();
                task = service.tasks().move(listID, task.getId()).setParent(parent).execute();
            } catch (IOException ex) {
                Logger.getLogger(GoogleTaskController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (dlg != null){
                if (dlg.getStop()){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean sendToGoogleTasks(ListProductRecord productRecordList, String listTitle){ 
        if (dlg != null){
            dlg.run();
            dlg.setText("Tworzenie listy zakupów...");
        }
        new Thread(() -> {
            try {
                TaskList shoppingList = new TaskList();
                shoppingList.setTitle(listTitle);
                shoppingList = service.tasklists().insert(shoppingList).execute();
                Dictionary<String, String> categoryDictionary = createCategoryTaskList(service.tasks(), shoppingList.getId(), productRecordList);
                List<Task> taskList = createTasks(productRecordList, categoryDictionary);
                exportTasks(taskList, shoppingList.getId());
            } catch (IOException ex) {
                Logger.getLogger(GoogleTaskController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (dlg != null){
                dlg.end();
            }
        }).start();
        return true;
    }
    
    public Dictionary<String, String> createCategoryTaskList(Tasks.TasksOperations service, String taskListID, ListProductRecord productRecordList) throws IOException{
        Dictionary<String, String> categories = new Hashtable<>();
        
        for (ProductRecord productRecord : productRecordList) {
            if (categories.get(productRecord.category) == null){
                Task task = new Task();
                task.setTitle(productRecord.category);
                task = service.insert(taskListID, task).execute();
                categories.put(productRecord.category, task.getId());
            }
        }
        
        return categories;
    };
}
