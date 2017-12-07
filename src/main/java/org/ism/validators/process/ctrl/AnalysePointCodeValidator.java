/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.process.ctrl;

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
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.process.ctrl.AnalysePointController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator(value = "analysePointCodeValidator")
public class AnalysePointCodeValidator implements Validator , Serializable {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "AnalysePointDuplicationSummary_apPoint";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "AnalysePointDuplicationDetail_apPoint";


    @ManagedProperty(value = "#{analysePointController}")
    AnalysePointController analysePointController;
    
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
        List<AnalysePoint> lst = analysePointController.getItemsByCode(value, staffAuthController.getCompany());
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
    public void setAnalysePointController(AnalysePointController analysePointController) {
        this.analysePointController = analysePointController;
    }
    
    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
