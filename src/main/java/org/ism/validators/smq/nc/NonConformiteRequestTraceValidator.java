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
import org.primefaces.component.inputtext.InputText;

/**
 * Ce validateur permet de valider soit qu'une trace ou qu'un produit aie été
 * définit dans les champs de création
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator("nonConformiteRequestTraceValidator")
public class NonConformiteRequestTraceValidator implements Validator, Serializable {

    public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "NonConformiteRequestTraceFields_Summary";
    public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "NonConformiteRequestTraceFields_Detail";

    @ManagedProperty(value = "#{nonConformiteRequestController}")
    NonConformiteRequestController nonConformiteRequestController;

    @ManagedProperty(value = "#{staffAuthController}")
    StaffAuthController staffAuthController;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String value = o.toString();
        if ((fc == null) || (uic == null)) {
            throw new NullPointerException();
        }
        if (!(uic instanceof InputText)) {
            return;
        }

        UIComponent uicProduct = null;
        UIComponent uicTrace = null;
        uicProduct = JsfUtil.findComponent("ncrProduct");
        uicTrace = JsfUtil.findComponent("ncrTrace");

        if (uicProduct == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                    "Component ncrProduct does not exist !");
            throw new ValidatorException(facesMsg);
        }

        if (uicTrace == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                    "Component ncrTrace does not exist !");
            throw new ValidatorException(facesMsg);
        }

        InputText inputProduct = (InputText) uicProduct;
        InputText inputTrace = (InputText) uicTrace;

        if (inputProduct.getSubmittedValue() == null
                && inputTrace.getSubmittedValue() == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_DETAIL_ID)
                    + value);
            throw new ValidatorException(facesMsg);
        }
        int i = 0;
        if (inputProduct.getSubmittedValue() != null) {
            if (inputProduct.getSubmittedValue().toString().trim().isEmpty()) {
                i++;
            }
        }
        if (inputTrace.getSubmittedValue() != null) {
            if (inputTrace.getSubmittedValue().toString().trim().isEmpty()) {
                i++;
            }
        }

        if (i == 2) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_DESIGNATION_DETAIL_ID)
                    + value);
            throw new ValidatorException(facesMsg);
        }

    }

    public void setNonConformiteRequestController(NonConformiteRequestController nonConformiteRequestController) {
        this.nonConformiteRequestController = nonConformiteRequestController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
