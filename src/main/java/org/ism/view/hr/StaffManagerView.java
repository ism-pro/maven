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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroups;
import org.ism.jsf.admin.CompanyController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.hr.StaffGroupDefController;
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
//
//    @Inject
//    private StaffController staffController;
//    @Inject
//    private StaffGroupsController staffGroupsCtrl;
//    
//    
    /**
     * Injection of CompanyController  <br>
     * This contrôller allow to get all company
     */
//    @ManagedProperty(value = "#{companyController}")
//    private CompanyController companyController;

    /**
     * Injection of StaffController   <br>
     * This controller allow to get selected definition
     */
    @ManagedProperty(value = "#{staffController}")
    private StaffController staffController;

    /**
     * Injection of StaffGroupDefController   <br>
     * This controller allow to get definition of group
     */
    @ManagedProperty(value = "#{staffGroupDefController}")
    private StaffGroupDefController staffGroupDefController;

    /**
     * Injection of staffGroupsController This controller link company /
     * selected / stgd_group_def which allow to identify selected affected
     * groups. Staff can have multiple group defined <br>
     * In other word, staff are linked to a group by this controller
     */
    @ManagedProperty(value = "#{staffGroupsController}")
    private StaffGroupsController staffGroupsController;

    /**
     * Current staff use for the controller view
     */
    private Staff selected = null;

    /**
     * Specify the current step page in a wizard<br>
     * Default is 0
     */
    private Integer wizardStep = 0;

    private TreeNode access;                    //!< Racine de l'abre complet
    private TreeNode accessStaff;               //!< Racine de l'abre complet
    private TreeNode accessChecked[];           //!< Tableau de élément sélectionné 

    /**
     * displayMode allow to specify which mode is to be set for display users
     * <br>
     * 1 is for list mode, <br>
     * 2 is for tabular mode,<br>
     * 3 is for grid mode
     * <br>
     * Default is 1. This set was introduce in order to display different mode
     * from user perspective
     *
     */
    private Integer displayMode = 1;

    /**
     * Specify the number of item to display in displayMode grid (3)
     */
    private Integer gridRows = 9;
    /**
     * Selector of number of  items to be displayed
     */
    private String rowsPerPageTemplate = "3,6,9,12,15,24,30,60,90,120"; 

    
    
    /**
     * Specify whether or no the selection must stay in memory after création
     */
    private Boolean isReleaseSelected;
    /**
     * Specify if password need to be reset.
     */
    private Boolean isResetPassword;

    /**
     * PreapareCreate allow to setup an empty selected user and tree access
     */
    public void prepareCreate() {
        selected = new Staff();
        wizardStep = 0;
        accessChecked = null;
        access = (new CtrlAccessService()).securityStaff();
    }

    /**
     * PrepareEdit allow to setup company and groups tree for selected user.
     */
    public void prepareEdit() {
        wizardStep = 0;
        isResetPassword = false;

        // Setup company
        FacesContext facesContext = FacesContext.getCurrentInstance();
        CompanyController companyController = (CompanyController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "companyController");
        companyController.prepareCreate();

        // Read back the selected group def associate to this user
        List<StaffGroups> staffGroupsList = staffGroupsController.getItemsByStaff(selected);
        // Create complete tree
        CtrlAccessService ctrlAccessService = new CtrlAccessService();
        if (staffGroupsList == null) {
            access = ctrlAccessService.makeTreeNodeCompanyGroups(companyController.getItems(),
                    staffGroupDefController.getItemsAvailableSelectMany());
        } else {
            access = ctrlAccessService.makeTreeNodeCompanyGroups(companyController.getItems(),
                    staffGroupDefController.getItemsAvailableSelectMany(),
                    staffGroupsList, accessChecked);
        }
    }
    
    /**
     * This method is useful to release actual selected ! That way nothing is
     * selected
     */
    public void releaseSelected() {
        isReleaseSelected = true;
        selected = null;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffReleaseSelectedDetail"));
    }

    /**
     * @deprecated 
     */
    public void handleStaffEdit() {
        List<Staff> lstStaff = staffController.findByStaff(selected.getStStaff());
        if (lstStaff != null) {
            JsfUtil.addErrorMessage("StaffManagerCreateForm:stStaff",
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerExist_stStaff"));
        }
    }

    /**
     * @deprecated 
     */
    public void handleStaffPwdEdit() {
        /*if (!selected.getStPassword().matches(selected.getStPasswordConf())) {
            JsfUtil.addErrorMessage("stPassword", "StaffManagerCreateForm:stStaff",
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerMatch_stPwd"));
        }*/
    }

    /**
     * handleWizardFlow handle the wizard flow by affecting the wizard data.
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

    /**
     * Create allow to create an unexisting staff.<br>
     * This method check if the staff defined by is code was not already defined
     * before creation <br>
     * After reading back the new created staff, we process to groups definition
     */
    public void create() {
        // Check if staff does not already exist
        List<Staff> staffs = staffController.findByStaff(selected.getStStaff());
        if (staffs != null && !staffs.isEmpty()) {
            JsfUtil.addErrorMessage("StaffMangerView : create",
                    "Staff " + this.selected.getStStaff() + " already exist can not be created twice !");
            return;
        }

        // Now create the new staff
        staffController.prepareCreate();
        staffController.setSelected(this.selected);
        staffController.setIsResetPassword(Boolean.TRUE);
        staffController.create();

        // Now read back the new created staff
        staffs = staffController.findByStaff(selected.getStStaff());
        if (staffs != null) {
            selected = staffs.get(0);
        } else {
            JsfUtil.addErrorMessage("StaffMangerView : create",
                    "Staff " + this.selected.getStStaff() + " was not created !");
            return;
        }

        // Create list of group selected
        List<StaffGroupDef> groupDef = new ArrayList<>();
        if (accessChecked != null) {
            for (TreeNode accCheck : accessChecked) {
                CtrlAccess acc = (CtrlAccess) accCheck.getData();
                groupDef.add(acc.getStaffGroupDef());
            }
        }

        // Create all selected access right
        Iterator<StaffGroupDef> itrGroupDef = groupDef.iterator();
        while (itrGroupDef.hasNext()) {
            StaffGroupDef stgd = itrGroupDef.next();
            StaffGroups groups = new StaffGroups();
            groups.setStgStaff(selected); // Définition du selected
            groups.setStgGroupDef(stgd);    // Association à un groupe
            groups.setStgCompany(stgd.getStgdCompany());    // Creation de la société
            groups.setStgActivated(true);
            staffGroupsController.setSelected(groups);
            staffGroupsController.create();
        }

        JsfUtil.addSuccessMessage("Staff Creation Wizard", "Creation " + selected.getStStaff() + " " + selected.getStFirstname() + " succeded");
        selected = new Staff();

    }

    /**
     * update actualise selected staff with new value<br>
     * First step actualise the staff. In this step, depend on the reset
     * password value set this will also encrypt the password<br>
     * Second step actualise the groupe definition<br>
     */
    public void update() {
        staffController.setSelected(selected);
        staffController.setSelected(this.selected);
        staffController.setIsResetPassword(isResetPassword);
        if (isResetPassword) {
            staffController.updateWidthPassword();
        } else {
            staffController.update();
        }
        isResetPassword = false;

        // Get existing access affected to this user
        List<StaffGroups> groups = staffGroupsController.getItemsByStaff(selected);

        // Remove all access
        if (groups != null) {
            Iterator<StaffGroups> groupsItr = groups.iterator();
            while (groupsItr.hasNext()) {
                staffGroupsController.setSelected(groupsItr.next());
                staffGroupsController.destroy();
            }
        }

        // Get newly defined access
        if (accessChecked.length == 0) {
            JsfUtil.addSuccessMessage("StaffManagerView : update >> no access checked !");
            return;
        }
        List<StaffGroupDef> groupDefs = new ArrayList<>();
        for (TreeNode node : accessChecked) {
            CtrlAccess ctrlAccess = (CtrlAccess) node.getData();
            groupDefs.add(ctrlAccess.getStaffGroupDef());
        }

        // Create all new defined
        Iterator<StaffGroupDef> itrGroupDefs = groupDefs.iterator();
        while (itrGroupDefs.hasNext()) {
            StaffGroupDef gd = itrGroupDefs.next();
            staffGroupsController.prepareCreate();
            staffGroupsController.getSelected().setStgCompany(gd.getStgdCompany());
            staffGroupsController.getSelected().setStgStaff(selected);
            staffGroupsController.getSelected().setStgGroupDef(gd);
            staffGroupsController.getSelected().setStgActivated(true);
            staffGroupsController.create();
        }
        JsfUtil.addSuccessMessage("StaffManagerView : update >> succed !");
    }

    
    /**
     * Destroy the user if not appearring in the list
     */
    public void destroy(){
        // Get existing access affected to this user
        List<StaffGroups> groups = staffGroupsController.getItemsByStaff(selected);

        // Remove all access
        if (groups != null) {
            Iterator<StaffGroups> groupsItr = groups.iterator();
            while (groupsItr.hasNext()) {
                staffGroupsController.setSelected(groupsItr.next());
                staffGroupsController.destroy();
            }
        }
        
        // Now try to remove user
        staffController.setSelected(selected);
        if(!staffController.destroy()){
            // reverse access
            Iterator<StaffGroups> groupsItr = groups.iterator();
            while (groupsItr.hasNext()) {
                staffGroupsController.setSelected(groupsItr.next());
                staffGroupsController.create();
            }
        }
        
    }
    /* ========================================================================
   
    =========================================================================*/
    /**
     * *
     *
     * @return selected
     */
    public Staff getSelected() {
        if (selected == null) {
            selected = new Staff();
        }
        return selected;
    }

    public void setSelected(Staff staff) {
        this.getSelected();
        this.selected = staff;
    }

    public Boolean getIsReleaseSelected() {
        return isReleaseSelected;
    }

    public void setIsReleaseSelected(Boolean isReleaseSelected) {
        this.isReleaseSelected = isReleaseSelected;
    }

    public Boolean getIsResetPassword() {
        return isResetPassword;
    }

    public void setIsResetPassword(Boolean isResetPassword) {
        this.isResetPassword = isResetPassword;
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

    public Integer getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(Integer displayMode) {
        this.displayMode = displayMode;
    }

    public Integer getGridRows() {
        return gridRows;
    }

    public void setGridRows(Integer gridRows) {
        this.gridRows = gridRows;
    }

    public String getRowsPerPageTemplate() {
        return rowsPerPageTemplate;
    }

    public void setRowsPerPageTemplate(String rowsPerPageTemplate) {
        this.rowsPerPageTemplate = rowsPerPageTemplate;
    }
    
    

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
//    public void setCompanyController(CompanyController companyController) {
//        this.companyController = companyController;
//    }
    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }

    public void setStaffGroupDefController(StaffGroupDefController staffGroupDefController) {
        this.staffGroupDefController = staffGroupDefController;
    }

    public void setStaffGroupsController(StaffGroupsController staffGroupsController) {
        this.staffGroupsController = staffGroupsController;
    }

    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    /// Validators
    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    public void staffCodeValidator(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String SUMMARY_ID = "StaffDuplicationSummuryField_stStaff";
        String DETAIL_ID_DUPLICATION = "StaffDuplicationDetailField_stStaff";

        
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
