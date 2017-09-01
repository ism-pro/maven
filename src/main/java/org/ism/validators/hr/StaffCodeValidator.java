/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.hr;

import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.ism.entities.hr.Staff;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.inputtext.InputText;

/**
 * <h1>StaffCodeValidator</h1><br>
 * StaffCodeValidator class
 *
 * @author r.hendrick
 *
 */
@FacesValidator(value = "staffCodeValidator")
@ManagedBean
@RequestScoped
public class StaffCodeValidator implements Validator {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "StaffDuplicationSummary_stStaff";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "StaffDuplicationDetail_stStaff";

    @EJB
    private org.ism.sessions.hr.StaffFacade ejbFacade;

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
        List<Staff> lst = ejbFacade.findByCode(value);
        if (lst != null) {
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
}
