/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.smq.nc;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 * Ce validateur permet de valider qu'en présence d'une quantité, une unité soit
 * définie.
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator("nonConformiteRequestQuantityValidator")
public class NonConformiteRequestQuantityValidator implements Validator, Serializable {

    public static final String P_Q_SUMMARY_ID = "NonConformiteRequestQuantityFields_Summary";
    public static final String P_Q_DETAIL_ID = "NonConformiteRequestQuantityFields_Detail";
    public static final String P_QU_SUMMARY_ID = "NonConformiteRequestQuantityUniteFields_Summary";
    public static final String P_QU_DETAIL_ID = "NonConformiteRequestQuantityUniteFields_Detail";

    @ManagedProperty(value = "#{nonConformiteRequestController}")
    NonConformiteRequestController nonConformiteRequestController;

    @ManagedProperty(value = "#{staffAuthController}")
    StaffAuthController staffAuthController;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if ((fc == null) || (uic == null)) {
            throw new NullPointerException();
        }
        if (!(uic instanceof InputText)
                && !(uic instanceof InputNumber)
                && !(uic instanceof SelectOneMenu)) {
            return;
        }

        UIComponent uicQuantity = JsfUtil.findComponent("ncrQuantity");
        UIComponent uicUnite = JsfUtil.findComponent("ncrUnite");

        if (uicQuantity == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_Q_SUMMARY_ID),
                    "Component ncrQuantity does not exist !");
            throw new ValidatorException(facesMsg);
        }

        if (uicUnite == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_Q_SUMMARY_ID),
                    "Component ncrUnite does not exist !");
            throw new ValidatorException(facesMsg);
        }

        InputNumber inputQuantity = (InputNumber) uicQuantity;
        SelectOneMenu inputUnite = (SelectOneMenu) uicUnite;

        int i = 0;
        // Manage unite when quantity is specify
        if (inputQuantity.getSubmittedValue() != null) {
            if (!inputQuantity.getSubmittedValue().toString().trim().isEmpty()) {
                // Wait for unit when quantity is specify
                if (inputUnite.getSubmittedValue() != null) {
                    if (inputUnite.getSubmittedValue().toString().trim().isEmpty()) {
                        FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                        getString(P_Q_SUMMARY_ID),
                                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                        getString(P_Q_DETAIL_ID));
                        throw new ValidatorException(facesMsg);
                    }
                }
            } // Manage unite when quantity is not specify but unit is
            else if (inputUnite.getSubmittedValue() != null) {
                /*if (!inputUnite.getSubmittedValue().toString().trim().isEmpty()) {
                        FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_QU_SUMMARY_ID),
                                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_QU_DETAIL_ID));
                        throw new ValidatorException(facesMsg);
                    }*/
            }
        }
    }

    public void setNonConformiteRequestController(NonConformiteRequestController nonConformiteRequestController) {
        this.nonConformiteRequestController = nonConformiteRequestController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
