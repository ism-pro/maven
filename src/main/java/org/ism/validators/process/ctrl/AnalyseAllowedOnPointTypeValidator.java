/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.process.ctrl;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.process.ctrl.AnalyseAllowedController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator(value = "analyseAllowedOnPointTypeValidator")
public class AnalyseAllowedOnPointTypeValidator implements Validator, Serializable {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "AnalyseAllowedDuplicationSummary_aaType";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "AnalyseAllowedDuplicationDetail_aaType";

    @ManagedProperty(value = "#{analyseAllowedController}")
    AnalyseAllowedController analyseAllowedController;

    @ManagedProperty(value = "#{staffAuthController}")
    StaffAuthController staffAuthController;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String value = o.toString();
        if ((fc == null) || (uic == null)) {
            throw new NullPointerException();
        }

        if (!(uic instanceof SelectOneMenu)) {
            return;
        }

        AnalyseType aaType = (AnalyseType) o;

        // Get Selected Point
        SelectOneMenu somPoint = (SelectOneMenu) JsfUtil.findComponent("aaPoint");
        Object oPoint = somPoint.getValue();
        if (oPoint == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("AnalysePointRequiredMsg_apPoint"),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("AnalysePointRequiredMsg_apPoint")
                    + aaType.toString() + " / null");
            throw new ValidatorException(facesMsg);
        }
        AnalysePoint aaPoint = (AnalysePoint) oPoint;

        List<AnalyseAllowed> lst = analyseAllowedController.getItemsByPointType(aaPoint, aaType, staffAuthController.getCompany());
        if (lst != null && !lst.isEmpty()) {
            if (Objects.equals(lst.get(0).getAaId(), analyseAllowedController.getSelected().getAaId())) {
                return;
            } else {
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_DETAIL_ID)
                        + aaType.toString() + " / " + aaPoint.toString());
                throw new ValidatorException(facesMsg);
            }
        }
    }

    public void setAnalyseAllowedController(AnalyseAllowedController analyseAllowedController) {
        this.analyseAllowedController = analyseAllowedController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
