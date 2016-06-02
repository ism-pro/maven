package org.ism.jsf;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.ism.services.CtrlAccessService;
import org.ism.entities.Company;
import org.ism.entities.Staff;
import org.ism.entities.StaffCompanies;
import org.ism.entities.StaffGroups;
import org.ism.entities.StaffSetup;
import org.ism.jsf.util.JsfUtil;
import org.ism.sessions.StaffAuthFacade;
import org.primefaces.model.TreeNode;

/**
 *
 * @author juneau
 */
@SessionScoped
@Named("staffAuthController")
public class StaffAuthController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private StaffAuthFacade staffAuthFacade;

    private Staff staff = new Staff();      //!< Staff pour la connexion
    private Company company;                //!< Company pour la connexion

    private final String ON_LOGIN_SUCCESS = "/company/index.xhtml?faces-redirect=true";
    private final String ON_LOGIN_ERROR = "/login.xhtml?faces-redirect=true";

    private TreeNode selectedAccessTree[];                              //!< Tableau de élément sélectionné

    /**
     * Creates a new instance of AuthenticationController
     */
    public StaffAuthController() {
    }

    @PostConstruct
    public void init() {
        getStaff();
        getCompany();
    }

    public Staff getStaff() {
        if (staff == null) {
            staff = new Staff();
        }
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Company getCompany() {
        if (company == null) {
            company = new Company();
        }
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getMaxInactiveIntervalMinute() {
        getStaff();
        return staff.getStMaxInactiveInterval() / 60;
    }

    public void setMaxInactiveIntervalMinute(Integer min) {
        getStaff();
        staff.setStMaxInactiveInterval(60 * min);
    }

    public String login() {
        staffAuthFacade.setStaff(getStaff());
        if (staffAuthFacade.login()) {
            this.setStaff(staffAuthFacade.getStaff());
            setupStaff();
            return ON_LOGIN_SUCCESS;
        } else {
            Iterator<String> msgIterator = staffAuthFacade.getMsgList().iterator();
            while (msgIterator.hasNext()) {
                JsfUtil.addErrorMessage(msgIterator.next());
            }
            setStaff(null);
            return "";
        }
    }

    public String logout() {
        staffAuthFacade.logout();
        staff = null;
        return ON_LOGIN_ERROR;
    }

    private void setupStaff() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Setup association staff à une companie
        StaffSetupController staffSetupCtrl = (StaffSetupController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffSetupController");
        staffSetupCtrl.prepareCreate();
        StaffSetup staffSetup = staffSetupCtrl.getStaffSetupByStaff(getStaff());
        if (staffSetup != null) { // Get existing setup
            staffSetupCtrl.setSelected(staffSetup);
        } else { // Set default staff setup
            staffSetupCtrl.prepareCreate();
            staffSetupCtrl.getSelected().setStsStaff(staff);
            staffSetupCtrl.getSelected().setStsmenuIndex(1);
            staffSetupCtrl.getSelected().setStstimeOutSession(0);
            staffSetupCtrl.getSelected().setStsTheme(JsfUtil.defaultThemeName);
            staffSetupCtrl.create();
        }
    }

    /**
     * Connection application
     *
     * @return
     */
    /*
    public String login() {
        staffAuthFacade.setStaff(getStaff());
        staffAuthFacade.setCompany(getCompany());
        boolean authResult = staffAuthFacade.login();
        if (authResult) {
            this.authenticated = true;
            setStaff(staffAuthFacade.getStaff());
            setCompany(staffAuthFacade.getCompany());
            setRoles(staffAuthFacade.getAroles());

            // Setup default data
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // Setup association staff à une companie
            staffSetupCtrl = (StaffSetupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffSetupController");
            staffSetupCtrl.prepareCreate();
            StaffSetup stsId = staffSetupCtrl.getStaffSetup(this.getStaff());
            // RibbonView
            RibbonView ribbonView = (RibbonView) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ribbonView");

            // Manage Staff setup
            if (stsId != null) {
                setSessionTimeOut((int) stsId.getStstimeOutSession());
                ribbonView.setActiveIndex(stsId.getStsmenuIndex());
                staffSetupCtrl.setSelected(stsId);
            } else {
                setSessionTimeOut(sessionTimeOut);
                ribbonView.setActiveIndex(1);
                // and create staff with default parameter
                staffSetupCtrl.prepareCreate();
                staffSetupCtrl.getSelected().setStsStaff(staff);
                staffSetupCtrl.getSelected().setStsmenuIndex(1);
                staffSetupCtrl.getSelected().setStstimeOutSession(sessionTimeOut / 60);
                staffSetupCtrl.getSelected().setStsTheme(JsfUtil.defaultThemeName);
                staffSetupCtrl.create();
            }

            username = "";
            loginMsg = null;
            //System.out.println("Connection with " + getStaff().getStStaff() + " is established  go to public index");
            return ON_LOGIN_SUCCESS;
        } else {
            this.authenticated = false;
            String staffCode = getStaff().getStStaff();
            setStaff(null);
            getStaff().setStStaff(staffCode);
            loginMsg = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationWrongValue");

            String msg = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("AuthenticationDecline");
            JsfUtil.addErrorMessage(msg);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return ON_LOGIN_ERROR;
        }
    }

    */
    
    /**
     * Renvoie les access utilisateur
     *
     * @return
     */
    public TreeNode getAccessTree() {
        //prepareCreate();
        TreeNode accessTree = null;
        selectedAccessTree = null;

        // Get 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Setup association staff à une companie
        StaffCompaniesController staffCompaniesCtrl = (StaffCompaniesController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffCompaniesController");
        staffCompaniesCtrl.prepareCreate();

        // Setup association staff à un groupe
        StaffGroupsController staffGroupsCtrl = (StaffGroupsController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupsController");
        staffGroupsCtrl.prepareCreate();

        // Setup company
        CompanyController companyCtrl = (CompanyController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "companyController");
        companyCtrl.prepareCreate();

        // Setup Définition des groupe
        StaffGroupDefController staffgroupDefCtrl = (StaffGroupDefController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupDefController");
        staffgroupDefCtrl.prepareCreate();

        // Get company rely on staff
        List<StaffCompanies> staffCompanies = staffCompaniesCtrl.getItems(staff);

        // Get Groups associate to companies
        List<StaffGroups> staffGroupsList = staffGroupsCtrl.getItems(staff);

        // Create complete tree
        CtrlAccessService ctrlAccessService = new CtrlAccessService();
        if (staffCompanies == null || staffGroupsList == null) {
            accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(companyCtrl.getItems(),
                    staffgroupDefCtrl.getItemsAvailableSelectMany());
        } else {
            accessTree = ctrlAccessService.makeTreeNodeCompanyGroups(companyCtrl.getItems(),
                    staffgroupDefCtrl.getItemsAvailableSelectMany(),
                    staffGroupsList, selectedAccessTree);
        }

        return accessTree;
    }

    public TreeNode[] getSelectedAccessTree() {
        return selectedAccessTree;
    }

    public void setSelectedAccessTree(TreeNode[] selectedAccessTree) {
        this.selectedAccessTree = selectedAccessTree;
    }

   
}
