/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicView;

import Global.GlobalConfig;
import Other.PreviewType;
import javax.swing.JFrame;

/**
 *
 * @author Marek
 */
public class KonfigView {
    private PreviewType preview;
    private int defaultOperationOnClose;
    private int extendedState;

    public int getExtendedState() {
        return extendedState;
    }

    public void setExtendedState(int extendedState) {
        this.extendedState = extendedState;
    }

    public int getDefaultOperationOnClose() {
        return defaultOperationOnClose;
    }

    public void setDefaultOperationOnClose(int defaultOperationOnClose) {
        this.defaultOperationOnClose = defaultOperationOnClose;
    }

    public PreviewType getPreview() {
        return preview;
    }

    public void setPreview(PreviewType preview) {
        this.preview = preview;
    }
    
    public KonfigView(){
        preview = GlobalConfig.PREVIEW;
        defaultOperationOnClose = GlobalConfig.DEFAULT_OPERATION_ON_CLOSE;
        extendedState = JFrame.NORMAL;
    } 
    
    public KonfigView(KonfigView konfigView){
        this.preview = konfigView.getPreview();
        this.defaultOperationOnClose = konfigView.getDefaultOperationOnClose();
        this.extendedState = konfigView.getExtendedState();
    }
}
