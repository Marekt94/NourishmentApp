/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

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
    public void unpack(Serializable object);
    public <E> void unpack(List<E> objectList);
    public void pack();
}
