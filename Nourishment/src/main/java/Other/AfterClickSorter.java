/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author Marek
 */
public class AfterClickSorter extends MouseAdapter{
    private AfterClickSorterModel model = null;

    public AfterClickSorterModel getModel() {
        return model;
    }

    public void setModel(AfterClickSorterModel model) {
        this.model = model;
    }
    
    public <E> AfterClickSorter(AfterClickSorterModel model){
        this.model = model;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Integer column = model.getTable().columnAtPoint(point);
        model.setAscending(!model.getAscending());
        model.getList().sort(new MyComparator((String) model.getTable().getColumnModel().getColumn(column).getHeaderValue(), model.getObjectType(), model.getAscending()));
        model.getManagerPanel().updateView();
    }
    
}
