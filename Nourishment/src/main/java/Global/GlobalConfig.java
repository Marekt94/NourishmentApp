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
    public static final Integer DIGITS = 3;
    public static final Integer BATCH_SIZE = 20;
    public static final String dataFormat = "yyyy-MM-dd"; 
}
