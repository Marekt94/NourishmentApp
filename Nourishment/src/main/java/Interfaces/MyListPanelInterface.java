/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import View.BasicView.KonfigView;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author Marek
 */
public interface MyListPanelInterface extends MyPanelInterface{
    public void updateView();//
    public <E> List<E> getObjectsList();//
    public <E> E getCurrentObject();//
    public <E> List<E> getNewOrEditedObjectList();//
    public void addButton(JButton button);  
}
