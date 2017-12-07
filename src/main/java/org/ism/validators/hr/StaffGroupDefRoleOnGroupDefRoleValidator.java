/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.validators.hr;

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
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.ism.entities.app.IsmRole;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroupDefRole;
import org.ism.jsf.hr.StaffGroupDefRoleController;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator(value = "staffGroupDefRoleOnGroupDefRoleValidator")
public class StaffGroupDefRoleOnGroupDefRoleValidator implements Validator, Serializable {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "StaffGroupDefRoleDuplicationCodeSummary";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "StaffGroupDefRoleDuplicationCodeDetail";

    @ManagedProperty(value = "#{staffGroupDefRoleController}")
    StaffGroupDefRoleController staffGroupDefRoleController;

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

        IsmRole role = (IsmRole) o;

        // Get Selected Point
        SelectOneMenu sompStgd = (SelectOneMenu) JsfUtil.findComponent("stgdrGroupDef");
        Object oStgd = sompStgd.getValue();
        if (oStgd == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("StaffGroupDefRequiredMsg_stgdGroupDef"),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("StaffGroupDefRequiredMsg_stgdGroupDef")
                    + role.toString() + " / null");
            throw new ValidatorException(facesMsg);
        }
        StaffGroupDef stgd = (StaffGroupDef) oStgd;

        List<StaffGroupDefRole> lst = staffGroupDefRoleController.getItemsByStaffGroupDefRole(stgd, role, staffAuthController.getCompany());
        if (lst != null && !lst.isEmpty()) {
            if (Objects.equals(lst.get(0).getStgdrId(), staffGroupDefRoleController.getSelected().getStgdrId())) {
                return;
            } else {
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_DETAIL_ID)
                        + role.toString() + " / " + stgd.toString());
                throw new ValidatorException(facesMsg);
            }
        }
    }

    public void setStaffGroupDefRoleController(StaffGroupDefRoleController staffGroupDefRoleController) {
        this.staffGroupDefRoleController = staffGroupDefRoleController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
