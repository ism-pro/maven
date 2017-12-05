package org.ism.jsf.hr;

import org.ism.jsf.admin.CompanyController;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.services.CtrlAccessService;
import org.ism.entities.admin.Company;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroups;
import org.ism.entities.hr.StaffSetup;
import org.ism.jsf.util.JsfUtil;
import org.ism.sessions.hr.StaffAuthFacade;
import org.primefaces.model.TreeNode;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "staffAuthController")
@SessionScoped
public class StaffAuthController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private StaffAuthFacade staffAuthFacade;
    @ManagedProperty(value = "#{staffSetupController}")
    private StaffSetupController staffSetupController;

    /**
     * Staff used for connexion on login page
     */
    private Staff staff = new Staff();
    /**
     * Company used for connexion on login page
     */
    private Company company;

    /**
     * Default link in case of login success
     */
    private final String ON_LOGIN_SUCCESS = "/company/index.xhtml?faces-redirect=true";
    /**
     * Defualt link in case of login error
     */
    private final String ON_LOGIN_ERROR = "/index.xhtml?faces-redirect=true";

    /**
     * Tree node for selected access
     */
    private TreeNode selectedAccessTree[];

    /**
     * Inititalize staff and company before login
     */
    @PostConstruct
    public void init() {
        getStaff();
        getCompany();
    }

    /**
     * Manage staff Login
     * <br > Usefull method for request a login connexion of a specified staff,
     * company and password.
     *
     * @return the connection success if pass login.
     */
    public String login() {
        staffAuthFacade.setStaff(getStaff());
        if (staffAuthFacade.login()) {
            this.setStaff(staffAuthFacade.getStaff());
            setupStaff();
            // Manage ExceptionHandler for Invalidate session
            String errorPageLocation = "/WEB-INF/errorpages/logout.xhtml";
            FacesContext context = FacesContext.getCurrentInstance();
            context.setViewRoot(context.getApplication().getViewHandler().createView(context, errorPageLocation));
            context.getPartialViewContext().setRenderAll(true);
            context.renderResponse();

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

    /**
     * Convenient method to logout session
     * <br >
     * release façade and staff and company
     *
     * @return Logout link page
     */
    public String logout() {
        staffAuthFacade.logout();
        staff = null;
        company = null;
        return ON_LOGIN_ERROR;
    }

    /**
     * Manage staff setup
     * <br >
     * If user exist it take value from database and set it to the staff.
     * Otherwise it set default value.
     */
    private void setupStaff() {
        //if (staffSetupController == null) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Setup association staff à une companie
        staffSetupController = (StaffSetupController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffSetupController");
        //} 
        staffSetupController.prepareCreate();
        StaffSetup staffSetup = staffSetupController.getStaffSetupByStaff(getStaff());
        if (staffSetup != null) { // Get existing setup
            staffSetupController.setSelected(staffSetup);
        } else { // Set default staff setup
            staffSetupController.prepareCreate();
            staffSetupController.getSelected().setStsStaff(staff);
            staffSetupController.getSelected().setStsmenuIndex(1);
            staffSetupController.getSelected().setStstimeOutSession(0);
            staffSetupController.getSelected().setStsTheme(JsfUtil.defaultThemeName);
            staffSetupController.create();
        }
    }

    /**
     * Manage staff access.
     * <br >Look on database to read corresponding access of the staff set in
     * database. In particular check staff groups, staff companies, staff groups
     * definition.
     *
     * @return access tree node
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

    public TreeNode[] getSelectedAccessTree() {
        return selectedAccessTree;
    }

    public void setSelectedAccessTree(TreeNode[] selectedAccessTree) {
        this.selectedAccessTree = selectedAccessTree;
    }

    public void setStaffSetupController(StaffSetupController staffSetupController) {
        this.staffSetupController = staffSetupController;
    }

}
