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
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroups;
import org.ism.jsf.hr.StaffGroupsController;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@SessionScoped
@FacesValidator(value = "staffGroupsGroupDefOnStaffValidator")
public class StaffGroupsGroupDefOnStaffValidator implements Validator, Serializable {

    public static final String P_DUPLICATION_CODE_SUMMARY_ID = "StaffGroupsDuplicationField_groupDefSummary";
    public static final String P_DUPLICATION_CODE_DETAIL_ID = "StaffGroupsDuplicationField_groupDefDetail";

    @ManagedProperty(value = "#{staffGroupsController}")
    StaffGroupsController staffGroupsController;

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

        StaffGroupDef stgd = (StaffGroupDef) o;

        // Get Selected Staff
        SelectOneMenu somSt = (SelectOneMenu) JsfUtil.findComponent("stgStaff");
        Object oSt = somSt.getValue();
        if (oSt == null) {
            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("StaffRequiredMsg_stStaff"),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("StaffRequiredMsg_stStaff")
                    + stgd.toString() + " / null");
            throw new ValidatorException(facesMsg);
        }
        Staff st = (Staff) oSt;

        List<StaffGroups> lst = staffGroupsController.getItemsByStaffAndGroupDef(st, stgd, staffAuthController.getCompany());
        if (lst != null && !lst.isEmpty()) {
            if (Objects.equals(lst.get(0).getStgId(), staffGroupsController.getSelected().getStgId())) {
                return;
            } else {
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_DETAIL_ID)
                        + stgd.toString() + " / " + st.toString());
                throw new ValidatorException(facesMsg);
            }
        }
    }

    public void setStaffGroupsController(StaffGroupsController staffGroupsController) {
        this.staffGroupsController = staffGroupsController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
    

}
