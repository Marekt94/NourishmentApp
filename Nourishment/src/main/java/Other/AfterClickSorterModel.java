/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Interfaces.MyListPanelInterface;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Marek
 */
public class AfterClickSorterModel {
    private JTable table = null;
    private List list = null;
    private Class objectType = null;
    private Boolean ascending = false;
    private MyListPanelInterface managerPanel = null;

    public AfterClickSorterModel(){
        
    }
    
    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Class getObjectType() {
        return objectType;
    }

    public void setObjectType(Class objectType) {
        this.objectType = objectType;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }

    public MyListPanelInterface getManagerPanel() {
        return managerPanel;
    }

    public void setManagerPanel(MyListPanelInterface managerPanel) {
        this.managerPanel = managerPanel;
    }
    
    
}
