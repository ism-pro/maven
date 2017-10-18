/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.crystal.view;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.ism.crystal.jsf.CrystalController;
import org.ism.services.Util;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 * CSDialogView class is used for report button in order to standardize show of
 * dialog for config report
 *
 * @author r.hendrick
 */
@ManagedBean(name = "csDialogView")
@SessionScoped
public class CSDialogView {

    private String itemLabel = "";
    private String itemValue = "";
    private List<?> values = new ArrayList<>();

    @ManagedProperty(value = "#{crystalController}")
    private CrystalController crystalController;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public void prepareReport(String itemLabel, String itemValue, List<?> values) {
        crystalController.setReloadFile(false);
        this.itemLabel = itemLabel;
        this.itemValue = itemValue;
        this.values = values;
    }

    public void handleChangeReport(ActionEvent event){
        SelectOneMenu som = (SelectOneMenu) event.getSource();
        Util.out("New value " + som.getValue().toString());
        crystalController.setFileReport(som.getValue().toString());
    }
    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public List<?> getValues() {
        return values;
    }

    public void setValues(List<?> values) {
        this.values = values;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Injection
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public void setCrystalController(CrystalController cyrstalController){
        this.crystalController = cyrstalController;
    }
            

}
