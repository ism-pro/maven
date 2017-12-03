/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.smq.nc;

import java.io.Serializable;
import java.util.List;
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
import org.ism.entities.smq.nc.NonConformiteGravity;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.smq.nc.NonConformiteGravityController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator("nonConformiteGravityCodeValidator")
public class NonConformiteGravityCodeValidator implements Validator, Serializable {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "NonConformiteGravityDuplicationField_codeSummary";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "NonConformiteGravityDuplicationField_codeDetail";

    @ManagedProperty(value = "#{nonConformiteGravityController}")
    NonConformiteGravityController nonConformiteGravityController;

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
        InputText input = (InputText) uic;

        List<NonConformiteGravity> lst = nonConformiteGravityController.getItemsByCode(value, staffAuthController.getCompany());
        if (lst != null && !lst.isEmpty()) {
            if (input.getValue() != null) {
                if (value.matches((String) input.getValue())) {
                    return;
                }
            }
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_CODE_SUMMARY_ID),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString(P_DUPLICATION_CODE_DETAIL_ID)
                    + value);
            throw new ValidatorException(facesMsg);
        }
    }

    public void setNonConformiteGravityController(NonConformiteGravityController nonConformiteGravityController) {
        this.nonConformiteGravityController = nonConformiteGravityController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
