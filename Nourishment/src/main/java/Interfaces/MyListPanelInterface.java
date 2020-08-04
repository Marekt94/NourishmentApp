/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author Marek
 */
public interface MyListPanelInterface extends MyPanelInterface{
    public void updateView();
    public <E> List<E> getObjectsList();
    public <E> E getCurrentObject();
    public <E> List<E> getNewOrEditedObjectList();
    public <E> List<E> getObjectToDeleteList();
    public void addButton(JButton button, Integer keyboardKey);
    public JComponent getComponentWihtListOfRecords();
    public <E> void addObject(E object);
    public void addObject(MyPanelInterface detailPanel, String title, Class objectType);
    public void deleteObject();
    public void editObject(MyPanelInterface detailPanel, String title);
    public void unpack();
}
