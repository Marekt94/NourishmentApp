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
    private Boolean isBtnVisible;
    private Byte panelID;
    private Boolean updateDB;

    public Boolean getUpdateDB() {
        return updateDB;
    }

    public void setUpdateDB(Boolean updateDB) {
        this.updateDB = updateDB;
    }
    
    public Byte getPanelID() {
        return panelID;
    }

    public void setPanelID(Byte panelID) {
        this.panelID = panelID;
    }
    
    public KonfigView withPanelID(Byte panelID) {
        this.panelID = panelID;
        return this;
    }    
    
    public KonfigView withIsBtnVisible(Boolean isBtnVisible) {
        this.isBtnVisible = isBtnVisible;
        return this;
    }
    
    public KonfigView withUpdateDB(Boolean updateDB){
        this.updateDB = updateDB;
        return this;
    }
    
    public Boolean getIsBtnVisible() {
        return isBtnVisible;
    }

    public void setIsBtnVisible(Boolean isBtnVisible) {
        this.isBtnVisible = isBtnVisible;
    }

    public KonfigView withExtendedState(int extendedState) {
        this.extendedState = extendedState;
        return this;
    }

    public int getExtendedState() {
        return extendedState;
    }

    public void setExtendedState(int extendedState) {
        this.extendedState = extendedState;
    }
    
    public KonfigView withDefaultOperationOnClose(int defaultOperationOnClose) {
        this.defaultOperationOnClose = defaultOperationOnClose;
        return this;
    }
    
    public int getDefaultOperationOnClose() {
        return defaultOperationOnClose;
    }

    public void setDefaultOperationOnClose(int defaultOperationOnClose) {
        this.defaultOperationOnClose = defaultOperationOnClose;
    }
 
    public KonfigView withPreview(PreviewType preview) {
        this.preview = preview;
        return this;
    }

    public PreviewType getPreview() {
        return preview;
    }

    public void setPreview(PreviewType preview) {
        this.preview = preview;
    }
    
    public KonfigView(Byte panelID){
        preview = GlobalConfig.PREVIEW;
        defaultOperationOnClose = GlobalConfig.DEFAULT_OPERATION_ON_CLOSE;
        extendedState = JFrame.NORMAL;
        isBtnVisible = true;
        updateDB = true;
        this.panelID = panelID;
    } 
    
    public KonfigView(KonfigView konfigView, Byte panelID){
        this.preview = konfigView.getPreview();
        this.defaultOperationOnClose = konfigView.getDefaultOperationOnClose();
        this.extendedState = konfigView.getExtendedState();
        this.isBtnVisible = konfigView.getIsBtnVisible();
        this.panelID = panelID;
        this.updateDB = konfigView.getUpdateDB();
    }
}
