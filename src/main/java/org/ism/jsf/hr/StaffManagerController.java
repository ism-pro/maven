/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.jsf.hr;

import org.ism.jsf.admin.CompanyController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.ism.services.CtrlAccess;
import org.ism.services.CtrlAccessService;
import org.ism.entities.admin.Company;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroups;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "staffManagerController")
@SessionScoped
public class StaffManagerController implements Serializable {

    // VARIABLES
    private static final long serialVersionUID = 1L;

    private StaffController staffCtrl = new StaffController();          //!< Staff controller
    private CompanyController companyCtrl = new CompanyController();    //!< Company controller
    private StaffGroupDefController staffgroupDefCtrl
            = new StaffGroupDefController();      //!< Définition des groupes des groupes d'accès                
    private StaffGroupsController staffGroupsCtrl
            = new StaffGroupsController();        //!< Table d'association de staff à un groupe
    private List<Company> selectedCompanies = new ArrayList<Company>();
    private String staffErrorMsg = "";                                  //!< Gestionnaire de message de vérification de doublons de staff
    private String staffErrorPwd = "";                                  //!< Gestionnaire de message de correspondance de mot de passe
    private TreeNode accessTree = null;                                 //!< Racine de l'abre complet
    private TreeNode selectedAccessTree[];                              //!< Tableau de élément sélectionné
    private List<TreeNode> selectedAccesssTreeList = new ArrayList<TreeNode>(); //!< Use to display selected node
    private TreeNode treeNodeFromSelectedAccessTree;                    //!< Racine de l'arbre des éléments sélectionné

    // Constructor
    public StaffManagerController() {

    }

    /**
     * Init StaffManagerController
     */
    @PostConstruct
    public void init() {
    }

    /**
     * Prepare before creation
     */
    public void prepareCreate() {
        // Local data
        selectedAccessTree = null;
        treeNodeFromSelectedAccessTree = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Setup staff controller
        staffCtrl = (StaffController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffController");
        staffCtrl.prepareCreate();
        staffCtrl.getSelected().setStActivated(true);   //Default activate

        // Setup company
        companyCtrl = (CompanyController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "companyController");
        companyCtrl.prepareCreate();

        // Setup Définition des groupe
        staffgroupDefCtrl = (StaffGroupDefController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupDefController");
        staffgroupDefCtrl.prepareCreate();

        // Setup association staff à un groupe
        staffGroupsCtrl = (StaffGroupsController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupsController");
        staffGroupsCtrl.prepareCreate();

        // Create complete tree
        CtrlAccessService ctrlAccessService = new CtrlAccessService();
        accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(companyCtrl.getItems(),
                staffgroupDefCtrl.getItemsAvailableSelectMany());

        /*
        // Sauvegarde la vue RH.
        RibbonView ribbonView = (RibbonView) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ribbonView");
        ribbonView.setActiveIndex(2);// 2 - RH
         */
    }

    /**
     * Create Staff associate to group
     */
    public void create() {

        // Controle de mot de passe
        staffCtrl.create();
        staffCtrl.setSelected(staffCtrl.findByStaff(staffCtrl.getSelected().getStStaff()).get(0));
        // Structure staffCompaniesCreation
        if (treeNodeFromSelectedAccessTree != null) {
            //Loop for creating parent node
            //List<TreeNode> parentNode = new ArrayList<TreeNode>();
            List<Company> lstCompapny = new ArrayList<>();
            for (int i = 0; i < selectedAccessTree.length; i++) {
                TreeNode currentNode = selectedAccessTree[i];
                CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
                //if (ctrlAccess.getType() == CtrlAccess.TypeCtrl.A_FILE) {
                CtrlAccess ct = (CtrlAccess) currentNode.getParent().getData();

                if (lstCompapny.isEmpty()) { // On ajoute à la liste et on crée le lien staff companie

                } else if (!lstCompapny.contains(ct.getCompany())) {

                }
                //}
            }

            // Loop for adding child
            staffGroupsCtrl.getSelected().setStgActivated(true);
            staffGroupsCtrl.getSelected().setStgStaff(staffCtrl.getSelected());
            for (int i = 0; i < selectedAccessTree.length; i++) {
                TreeNode currentNode = selectedAccessTree[i];
                CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
                //if (ctrlAccess.getType() == CtrlAccess.TypeCtrl.A_FILE) {
                CtrlAccess ct = (CtrlAccess) currentNode.getParent().getData();
                staffGroupsCtrl.getSelected().setStgGroupDef(ctrlAccess.getStaffGroupDef());
                staffGroupsCtrl.getSelected().setStgCompany(ct.getCompany());
                staffGroupsCtrl.create();
                //}
            }
        }
    }

    /**
     * Prepare data to show while editing.
     *
     * @param staff staff prepered for view
     */
    public void prepareView(Staff staff) {
        //prepareCreate();
        accessTree = null;
        selectedAccessTree = null;
        // Affected staff
        //staff.setStPasswordConf(staff.getStPassword());
        staffCtrl.setSelected(staff);

        // Get Groups associate to companies
        List<StaffGroups> staffGroupsList = staffGroupsCtrl.getItemsByStaff(staff);

        // Create complete tree
        CtrlAccessService ctrlAccessService = new CtrlAccessService();
        if (staffGroupsList == null) {
            accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(companyCtrl.getItems(),
                    staffgroupDefCtrl.getItemsAvailableSelectMany());
        } else {
            accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(companyCtrl.getItems(),
                    staffgroupDefCtrl.getItemsAvailableSelectMany(),
                    staffGroupsList, selectedAccessTree);
        }

    }

    /**
     * Modification du staff
     */
    public void update() {

        // Controle de mot de passe
        staffCtrl.update();
        //staffCtrl.setSelected(staffCtrl.findByStaff(staffCtrl.getSelected().getStStaff()).get(0));

        // Structure staffCompaniesCreation
        if (treeNodeFromSelectedAccessTree != null) {
            //Loop for creating parent node
            //List<Company> lstCompapny = new ArrayList<Company>();

            for (int i = 0; i < selectedAccessTree.length; i++) {
                TreeNode currentNode = selectedAccessTree[i];
                CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
                //if (ctrlAccess.getType() == CtrlAccess.TypeCtrl.A_FILE) {
                CtrlAccess ct = (CtrlAccess) currentNode.getParent().getData();

                //}
            }

            // Récupération de l'ensemble des liens staff company group
            List<StaffGroups> beforeStaffGroups = staffGroupsCtrl.getItemsByStaff(staffCtrl.getSelected());

            // Loop for adding child
            for (int i = 0; i < selectedAccessTree.length; i++) {
                TreeNode currentNode = selectedAccessTree[i];
                CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
                //if (ctrlAccess.getType() == CtrlAccess.TypeCtrl.A_FILE) {
                CtrlAccess ct = (CtrlAccess) currentNode.getParent().getData();
                StaffGroups qStaffGroups = staffGroupsCtrl.getItemsByStaffGroups(staffCtrl.getSelected(),
                        ctrlAccess.getStaffGroupDef());
                if (qStaffGroups != null) { // Modification
                    staffGroupsCtrl.setSelected(qStaffGroups);
                    staffGroupsCtrl.getSelected().setStgActivated(true);
                    staffGroupsCtrl.getSelected().setStgStaff(staffCtrl.getSelected());
                    beforeStaffGroups.remove(staffGroupsCtrl.getSelected()); // remove from liste to destroy
                    staffGroupsCtrl.update();
                } else { // Creation
                    staffGroupsCtrl.prepareCreate();
                    staffGroupsCtrl.getSelected().setStgStaff(staffCtrl.getSelected());
                    staffGroupsCtrl.getSelected().setStgGroupDef(ctrlAccess.getStaffGroupDef());
                    staffGroupsCtrl.getSelected().setStgCompany(ct.getCompany());
                    staffGroupsCtrl.getSelected().setStgActivated(true);
                    staffGroupsCtrl.create();
                }
                //}
            }

            // Remove remining an remove staffGroups
            for (int i = 0; i < beforeStaffGroups.size(); i++) {
                staffGroupsCtrl.setSelected(beforeStaffGroups.get(i));
                staffGroupsCtrl.destroy();
            }

        }
    }

    /**
     * Remove staff and associate company and groups of access
     *
     * @param staff the staff to destroy
     */
    public void destroy(Staff staff) {
        // Groups
        Iterator<StaffGroups> iStaffGroups = staffGroupsCtrl.getItems().iterator();
        while (iStaffGroups.hasNext()) {
            StaffGroups groups = iStaffGroups.next();
            if (groups.getStgStaff() == staff) {
                staffGroupsCtrl.setSelected(groups);
                staffGroupsCtrl.destroy();
            }
        }

        // Staff
        staffCtrl.setSelected(staff);
        staffCtrl.destroy();

        JsfUtil.addSuccessMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));

    }

    /**
     * Event happened between change of page
     *
     * @param event flowevent
     * @return next string
     */
    public String listenerFlowEvent(FlowEvent event) {
        // FlowDescription
        String flowStaff = "flowStaff";
        String flowISM = "flowISM";
        String flowConfirm = "flowConfirm";

        if (event.getNewStep().matches(flowStaff)) {
            JsfUtil.out("New flow is : " + flowStaff + " coming from " + event.getOldStep());
        } else if (event.getNewStep().matches(flowISM)) {
            JsfUtil.out("New flow is : " + flowISM + " coming from " + event.getOldStep());
            // Check if password match
            /*if (!staffCtrl.getSelected().getStPassword().matches(staffCtrl.getSelected().getStPasswordConf())) {
                this.staffErrorPwd = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerMatch_stPwd");
                return event.getOldStep();
            }else{
                this.staffErrorPwd = "";
            }*/
        } else if (event.getNewStep().matches(flowConfirm)) {
            JsfUtil.out("New flow is : " + flowConfirm + " coming from " + event.getOldStep());
        } else {
            JsfUtil.out("Unknow flow " + event.getNewStep() + "coming from " + event.getOldStep());
        }
        return event.getNewStep();
    }

    /**
     * Listener hollowing to check if staff is already existing
     */
    public void listenerStaffChangedEvent() {
        if (staffCtrl.getSelected() != null) {
            List<Staff> lstStaff = staffCtrl.findByStaff(staffCtrl.getSelected().getStStaff());
            if (lstStaff != null) {
                JsfUtil.addErrorMessage("StaffManagerCreateForm:stStaff",
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerExist_stStaff"));
                this.staffErrorMsg = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerExist_stStaff");
            } else {
                this.staffErrorMsg = "";
            }
        }
    }

    /**
     * Listener hollowing to check if passwords matches
     */
    public void listenerStaffPwdChangedEvent() {
        if (staffCtrl.getSelected() != null) {
            /*if (!staffCtrl.getSelected().getStPassword().matches(staffCtrl.getSelected().getStPasswordConf())) {
                this.staffErrorPwd = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffManagerMatch_stPwd");
            } else {
                this.staffErrorPwd = "";
            }*/
        }
    }

    /**
     * Listener hollowing to create access node and display corresponding tree
     */
    public void listenerCreateAccessTree() {
        CtrlAccessService ctrlAccessService = new CtrlAccessService();
        accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(selectedCompanies,
                staffgroupDefCtrl.getItemsAvailableSelectMany());
    }

    public void listenerSelect(Staff staff) {
        staffCtrl.setSelected(staff);
        JsfUtil.out(staff.getStStaff());
    }

    /**
     * Represent staffController
     *
     * @return staff controller
     */
    public StaffController getStaffCtrl() {
        return staffCtrl;
    }

    public void setStaffCtrl(StaffController staffCtrl) {
        this.staffCtrl = staffCtrl;
    }

    /**
     * Represent company controller
     *
     * @return campany controller
     */
    public CompanyController getCompanyCtrl() {
        return companyCtrl;
    }

    public void setCompanyCtrl(CompanyController companyCtrl) {
        this.companyCtrl = companyCtrl;
    }

    /**
     * Représent staff Groups
     *
     * @return staff group controller
     */
    public StaffGroupsController getStaffGroupsCtrl() {
        return staffGroupsCtrl;
    }

    public void setStaffGroupsCtrl(StaffGroupsController staffGroupsCtrl) {
        this.staffGroupsCtrl = staffGroupsCtrl;
    }

    /**
     * Represent staff group def
     *
     * @return staff groupdef ctrl
     */
    public StaffGroupDefController getStaffgroupDefCtrl() {
        return staffgroupDefCtrl;
    }

    public void setStaffgroupDefCtrl(StaffGroupDefController staffgroupDefCtrl) {
        this.staffgroupDefCtrl = staffgroupDefCtrl;
    }

    /**
     * Represent selected company
     *
     * @return selected companies
     */
    public List<Company> getSelectedCompanies() {
        return selectedCompanies;
    }

    public void setSelectedCompanies(List<Company> selectedCompanies) {
        this.selectedCompanies = selectedCompanies;
    }

    public String getStaffErrorMsg() {
        return staffErrorMsg;
    }

    public void setStaffErrorMsg(String staffErrorMsg) {
        this.staffErrorMsg = staffErrorMsg;
    }

    public String getStaffErrorPwd() {
        return staffErrorPwd;
    }

    public void setStaffErrorPwd(String staffErrorPwd) {
        this.staffErrorPwd = staffErrorPwd;
    }

    public TreeNode getAccessTree() {
        return accessTree;
    }

    public void setAccessTree(TreeNode accessTree) {
        this.accessTree = accessTree;
    }

    public TreeNode[] getSelectedAccessTree() {
        return this.selectedAccessTree;
    }

    public void setSelectedAccessTree(TreeNode[] selectedAccessTree) {
        this.selectedAccessTree = selectedAccessTree;
    }

    public List<TreeNode> getSelectedAccesssTreeList() {
        return selectedAccesssTreeList;
    }

    public void setSelectedAccesssTreeList(List<TreeNode> selectedAccesssTreeList) {
        this.selectedAccesssTreeList = selectedAccesssTreeList;
    }

    /**
     * Use to show selected node of access company.
     *
     * @return a tree node
     */
    public TreeNode getTreeNodeFromSelectedAccessTree() {

        //RACINE
        Company entrep = new Company();
        entrep.setCCompany("E");
        entrep.setCDesignation("Entreprise");
        treeNodeFromSelectedAccessTree = new CheckboxTreeNode(new CtrlAccess(entrep), null);

        if (selectedAccessTree == null) {
            return treeNodeFromSelectedAccessTree;
        }

        //Loop for creating parent node
        List<TreeNode> parentNode = new ArrayList<>();
        for (int i = 0; i < selectedAccessTree.length; i++) {
            TreeNode currentNode = selectedAccessTree[i];
            // Check if parent is not already create
            if (!parentNode.contains(currentNode.getParent())) {
                CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
                if (ctrlAccess.getCompany() == null) {
                    parentNode.add(new CheckboxTreeNode(
                            (CtrlAccess) currentNode.getParent().getData(), treeNodeFromSelectedAccessTree));
                }
            }
        }

        // Loop for adding child
        for (int i = 0; i < selectedAccessTree.length; i++) {
            TreeNode currentNode = selectedAccessTree[i];
            CtrlAccess ctrlAccess = (CtrlAccess) currentNode.getData();
            if (ctrlAccess.getCompany() == null) {
                CheckboxTreeNode a = new CheckboxTreeNode(
                        ctrlAccess,
                        parentNode.get(parentNode.indexOf(currentNode.getParent())));
            }
        }
        return treeNodeFromSelectedAccessTree;
    }

}
