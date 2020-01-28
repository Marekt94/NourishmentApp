/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import View.BasicView.KonfigView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marek
 */
public interface MyPanelInterface {
    public Boolean init(KonfigView konfigView);
    public KonfigView getKonfigView();
    public Boolean execute();
    public <E> void unpack(E object);
    public <E> void unpack(List<E> objectList);
    public <E> List<E> getNewOrEditedObjectList();
    public void pack();
    public void updateView();
    public void rollback();
    public <E> List<E> getObjectsList();
}
