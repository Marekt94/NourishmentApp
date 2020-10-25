/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JTable;

/**
 *
 * @author Marek
 */
public class RowSearcher {
    private JTable table;
    private String searchedString = "";
    private Integer visibleRowsAfterSelectedRow;
    private long lastSignEnteredTime = 0;
    private long maxTimeToRemeberSigns; //in millis

    public RowSearcher(JTable table) {
        this.table = table;
        visibleRowsAfterSelectedRow = 0;
        maxTimeToRemeberSigns = 2000;
    }
    
    public void searchRowAndScroll(KeyEvent keyEvt){
        if (keyEvt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            searchedString = "";
            return;
        }
        if ((keyEvt.getKeyCode() == KeyEvent.VK_ALT)
                || (keyEvt.getKeyCode() == KeyEvent.VK_ALT_GRAPH)
                || (keyEvt.getKeyCode() == KeyEvent.VK_CONTROL)
                || (keyEvt.getKeyCode() == KeyEvent.VK_LEFT)){
            return;
        }
        else{
            if ((System.currentTimeMillis() - lastSignEnteredTime) > maxTimeToRemeberSigns){
                searchedString = "";
                lastSignEnteredTime = System.currentTimeMillis();
            }
            scrollToFoundRow(searchRow(keyEvt.getKeyChar()));
        }
    }

    private Integer searchRow(char nextSign) {
        searchedString = searchedString + nextSign;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                if (table.getModel().getValueAt(i, j).toString().contains(searchedString)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void scrollToFoundRow(Integer row) {
        if (row > -1) {
            table.getSelectionModel().setSelectionInterval(row, row);
            table.scrollRectToVisible(new Rectangle(table.getCellRect(row + visibleRowsAfterSelectedRow, 0, true)));            
        } else {
            table.getSelectionModel().clearSelection();
        }
    }
}
