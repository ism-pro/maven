package org.ism.jsf;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.services.CtrlAccessService;
import org.ism.entities.Company;
import org.ism.entities.Staff;
import org.ism.entities.StaffGroups;
import org.ism.entities.StaffSetup;
import org.ism.jsf.util.JsfUtil;
import org.ism.sessions.StaffAuthFacade;
import org.primefaces.model.TreeNode;

/**
 *
 * @author juneau
 */
@ManagedBean(name = "staffAuthController")
@SessionScoped
public class StaffAuthController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private StaffAuthFacade staffAuthFacade;
    @Inject
    StaffSetupController staffSetupCtrl;

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
        if (staffSetupCtrl == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            // Setup association staff à une companie
            staffSetupCtrl = (StaffSetupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffSetupController");
        }
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

        return accessTree;
    }

    public TreeNode[] getSelectedAccessTree() {
        return selectedAccessTree;
    }

    public void setSelectedAccessTree(TreeNode[] selectedAccessTree) {
        this.selectedAccessTree = selectedAccessTree;
    }

}
