/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.hr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroups;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.hr.StaffGroupsController;
import org.ism.jsf.util.JsfUtil;
import org.ism.services.CtrlAccess;
import org.ism.services.CtrlAccessService;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "staffManagerView")
@SessionScoped
public class StaffManagerView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private StaffController staffCtrl;
    @Inject
    private StaffGroupsController staffGroupsCtrl;

    private Staff staff = null;                         //!< Utilisateur provisoire
    private Integer wizardStep = 0;                     //!< Current step

    private TreeNode access;                    //!< Racine de l'abre complet
    private TreeNode accessStaff;                    //!< Racine de l'abre complet
    private TreeNode accessChecked[];           //!< Tableau de élément sélectionné 

    public StaffManagerView() {
    }

    public void prepareCreate() {
        staff = new Staff();
        wizardStep = 0;
        accessChecked = null;
        access = (new CtrlAccessService()).securityStaff();
    }


    public void handleStaffEdit() {
        List<Staff> lstStaff = staffCtrl.findByStaff(staff.getStStaff());
        if (lstStaff != null) {
            JsfUtil.addErrorMessage("StaffManagerCreateForm:stStaff",
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerExist_stStaff"));
        }
    }

    public void handleStaffPwdEdit() {
        /*if (!staff.getStPassword().matches(staff.getStPasswordConf())) {
            JsfUtil.addErrorMessage("stPassword", "StaffManagerCreateForm:stStaff",
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerMatch_stPwd"));
        }*/
    }

    /**
     * *
     *
     * @param event flow event
     * @return a string page
     */
    public String handleWizardFlow(FlowEvent event) {
        String flowStaff = "flowStaff",
                flowISM = "flowISM",
                flowConfirm = "flowConfirm";

        if (event.getNewStep().matches(flowStaff)) {
            //wizardStep = 0;
        } else if (event.getNewStep().matches(flowISM)) {
            //wizardStep = 1;
        } else if (event.getNewStep().matches(flowConfirm)) {
            //wizardStep = 2;
        } else {
            JsfUtil.addErrorMessage("Unknow flow " + event.getNewStep() + "coming from " + event.getOldStep());
        }
        return event.getNewStep();
    }

    public void handleTreeSelect(NodeSelectEvent event) {
        CheckboxTreeNode node = (CheckboxTreeNode) event.getTreeNode();
        node.setSelected(true, true);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleTreeUnSelect(NodeUnselectEvent event) {
        CheckboxTreeNode node = (CheckboxTreeNode) event.getTreeNode();
        node.setSelected(false, true);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void create() {
        // Create staff
        staffCtrl.prepareCreate();
        staffCtrl.setSelected(this.staff);
        staffCtrl.setIsResetPassword(Boolean.TRUE);
        staffCtrl.create();
        List<Staff> lstStaff = staffCtrl.findByStaff(staff.getStStaff());
        Staff createStaff = null;
        if (lstStaff != null) {
            createStaff = lstStaff.get(0);
        } else {
            JsfUtil.addErrorMessage("StaffMangerVIew : create",
                    "Staff " + this.staff.getStStaff() + " was not created !");
            return;
        }
        if (createStaff == null) {
            return;
        }

        // Create list of group selected
        List<StaffGroupDef> groupDef = new ArrayList<StaffGroupDef>();
        if (accessChecked != null) {
            for (int i = 0; i < accessChecked.length; i++) {
                CtrlAccess access = (CtrlAccess) accessChecked[i].getData();
                groupDef.add(access.getStaffGroupDef());
            }
        }

        // Create all staff access right
        Iterator<StaffGroupDef> itrGroupDef = groupDef.iterator();
        while (itrGroupDef.hasNext()) {
            StaffGroupDef stgd = itrGroupDef.next();
            StaffGroups groups = new StaffGroups();
            groups.setStgStaff(createStaff); // Définition du staff
            groups.setStgGroupDef(stgd);    // Association à un groupe
            groups.setStgCompany(stgd.getStgdCompany());    // Creation de la société
            groups.setStgActivated(true);
            staffGroupsCtrl.setSelected(groups);
            staffGroupsCtrl.create();
        }

        this.staff = new Staff();
        JsfUtil.addSuccessMessage("Staff Creation Wizard", "Createion succed");

    }

    /* ========================================================================
   
    =========================================================================*/
    /**
     * *
     *
     * @return staff
     */
    public Staff getStaff() {
        if (staff == null) {
            staff = new Staff();
        }
        return staff;
    }

    public void setStaff(Staff staff) {
        this.getStaff();
        this.staff = staff;
    }

    /**
     *
     * @return get access
     */
    public TreeNode getAccess() {
        if (access == null) {
            access = (new CtrlAccessService()).securityStaff();
        }
        return access;
    }

    public void setAccess(TreeNode access) {
        this.access = access;
    }

    public TreeNode[] getAccessChecked() {
        return this.accessChecked;
    }

    public void setAccessChecked(TreeNode[] accessChecked) {
        this.accessChecked = accessChecked;
    }

    public TreeNode getAccessStaff() {
        if (accessStaff == null) {
            accessStaff = (new CtrlAccessService()).securityStaff(accessChecked);
        }
        return accessStaff;
    }

    public void setAccessStaff(TreeNode accessStaff) {
        this.accessStaff = accessStaff;
    }

    public Integer getWizardStep() {
        return wizardStep;
    }

    public void setWizardStep(Integer wizardStep) {
        this.wizardStep = wizardStep;
    }

    /**
     * ************************************************************************
     * VALIDATORS
     *
     *
     * ************************************************************************
     */
    @FacesValidator(value = "StaffManagerView_StaffValidator")
    public static class StaffManagerView_StaffValidator implements Validator {

        public static final String SUMMARY_ID = "StaffDuplicationSummuryField_stStaff";
        public static final String DETAIL_ID_DUPLICATION = "StaffDuplicationDetailField_stStaff";

        /*
        @EJB
        private org.ism.sessions.StaffFacade ejbFacade;*/


        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
            String value = o.toString();
            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }
            if (!(uic instanceof InputText)) {
                return;
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            StaffController staffCtrl = (StaffController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffController");

            List<Staff> staffs = staffCtrl.findByStaff(value);
            if (staffs != null) {
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(DETAIL_ID_DUPLICATION)
                        + value);
                throw new ValidatorException(facesMsg);
            } else {
                return;
            }
        }
    }
}
