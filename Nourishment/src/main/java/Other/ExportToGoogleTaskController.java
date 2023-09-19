/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Other;

import Global.GlobalConfig;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author komputer1
 */
public class ExportToGoogleTaskController {
    public boolean logToCloud(){
        return true;
    }
    
    public List<Task> createTasks(ListProductRecord productList){
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
                tasksList.add(task);
            }
        }
        return tasksList;
    }
    
    public boolean exportTasks(List<Task> tasklist){
        return true;
    }
    
    public boolean sendToGoogleTasks(ListProductRecord productRecordList){
        return true;
    }
}
