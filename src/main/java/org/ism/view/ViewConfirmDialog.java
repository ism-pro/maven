/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.confirmdialog.ConfirmDialog;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewConfirmDialog")
@ViewScoped
public class ViewConfirmDialog {

    private ConfirmDialog dlg;            //!< Confirm dialogue

    private String update;         //!< Value for update
    private String escape;         //!< Escape

    /*
    private String          message;        //!< Text to be displayed in body.
    private String          header;         //!< Text for the header
    private String          severity;       //!< Message severity for the displayed icon. (alert or infos)
    private Integer         width;          //!< Width of the dialog in pixels
    private Integer         height;         //!< Width of the dialog in pixels
    private Boolean         closable;       //!< Defines if close icon should be displayed or not
    private String          appendTo;       //!< Appends the dialog to the element defined by the given search expression.
    private Boolean         visible;        //!< Whether to display confirm dialog on load.
    private String          showEffect;     //!< Effect to use on showing dialog. 
                            blind, bounce, clip, drop, explode, fade, fold,
                            highlight, puff, pulsate, scale, shake, size, slide and transfer
    private String          hideEffect;     //!< Effect to use on hiding dialog.
    private Boolean         closeOnEscape;  //!< Defines if dialog should hide on escape key.
    private String          dir;            //!< Defines text direction, valid values are ltr and rtl.
    private Boolean         global;         //!< When enabled, confirmDialog becomes a shared for other components that require confirmation.
     */
    /**
     * Creates a new instance of viewConfirmDialog
     */
    public ViewConfirmDialog() {
    }

    @PostConstruct
    public void init() {
        dlg = new ConfirmDialog();
        dlg.setMessage("Pas de message");
        dlg.setHeader("Title");
        dlg.setSeverity("alert");
        dlg.setWidth(String.valueOf(340));
        dlg.setHeight(String.valueOf(100));
        dlg.setClosable(false);
        dlg.setShowEffect("blind");
        dlg.setHideEffect("blind");
        dlg.setCloseOnEscape(false);
        dlg.setGlobal(true);
        update = "@all";

        escape = "<br >";
    }

    /*
    <p:confirm   header="#{ism.NonConformiteRequestTitleList} - #{ism.ConfirmDeleteTitle}"
             message="#{ism.ConfirmDeleteMessage}" 
             icon="ui-icon-alert"

             />
     */
    public ConfirmDialog getDlg() {
        return dlg;
    }

    public void setDlg(ConfirmDialog dlg) {
        this.dlg = dlg;
    }

    public void setHeader(String header) {
        this.dlg.setHeader(header);
    }

    public String getHeader() {
        return this.dlg.getHeader();
    }

    public void setMessage(String message) {
        this.dlg.setMessage(message);
    }

    public String getMessage() {
        return this.dlg.getMessage();
    }

    public void setSeverity(String severity) {
        this.dlg.setSeverity(severity);
    }

    public String getSeverity() {
        return this.dlg.getSeverity();
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getEscape() {
        return escape;
    }

    public void setEscape(String escape) {
        this.escape = escape;
    }

}
