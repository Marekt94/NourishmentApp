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
    public KonfigView getKonfigView();
    public Boolean init(KonfigView konfigView);
    public void savePreferences();
    public void loadPreferences();
    public <E> void unpack(E object);
    public <E> void unpack(List<E> objectList);
    public void pack();
    public Boolean execute();
    public void rollback();
}
