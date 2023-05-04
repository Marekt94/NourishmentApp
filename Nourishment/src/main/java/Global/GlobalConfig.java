/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import Other.PreviewType;
import javax.swing.WindowConstants;

/**
 *
 * @author Marek
 */
public class GlobalConfig {
    // TYLKO STAŁE
    public static final PreviewType PREVIEW = PreviewType.ptPreview;
    public static final int DEFAULT_OPERATION_ON_CLOSE = WindowConstants.EXIT_ON_CLOSE;
    public static final String NULL_SIGN = "-";
    public static final String WEIGHT_UNIT = "g";
    public static final String UNIT_SHORTCUT = "jed.";
    public static final Integer DIGITS = 3;
    public static final Integer BATCH_SIZE = 20;
    public static final String dataFormat = "yyyy-mm-dd"; 
    public static final String PREF_NODE_GLOBAL = "GlobalSettings";
    public static final String PREF_DBPATH = "dbPath";
    
    public static final Byte LOGIN_PANEL_ID = 0;
    public static final Byte MENU_ID = 1;
    public static final Byte PRODUKTY_ID = 2;
    public static final Byte PRODUKTY_W_POTRAWIE_ID = 3;
    public static final Byte POTRAWY_W_DNIU_ID = 4;
    public static final Byte POTRAWY_ID = 5;
    public static final Byte PRODUKTY_LUZEM_ID = 6;
    public static final Byte EDYTOR_TEKSTU = 7;
}
