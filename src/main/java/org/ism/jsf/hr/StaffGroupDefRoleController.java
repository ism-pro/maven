package org.ism.jsf.hr;

import org.ism.jsf.app.IsmRoleController;
import org.ism.entities.hr.StaffGroupDefRole;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.hr.StaffGroupDefRoleFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.ism.entities.app.IsmRole;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.services.CtrlAccess;
import org.ism.services.CtrlAccessService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.ism.entities.admin.Company;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "staffGroupDefRoleController")
@SessionScoped
public class StaffGroupDefRoleController implements Serializable {

    @EJB
    private org.ism.sessions.hr.StaffGroupDefRoleFacade ejbFacade;
    private List<StaffGroupDefRole> items = null;
    private StaffGroupDefRole selected;
    private StaffGroupDefRole edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    @Inject
    private IsmRoleController roleCtrl;             //!< Inject role

    private CheckboxTreeNode nodeAccessTree = null;
    private int createTreeProgress = 0;

    public StaffGroupDefRoleController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrGroupDef"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrRole"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrActivated"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrCreated"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrChanged"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrGroupDef"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrRole"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrActivated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrCreated"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrChanged"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefRoleField_stgdrCompany"), false);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        roleCtrl = (IsmRoleController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ismRoleController");

    }

    protected void setEmbeddableKeys() {
    }

    private StaffGroupDefRoleFacade getFacade() {
        return ejbFacade;
    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    /**
     * Preapare
     *
     * @return staff group def prepare
     */
    public StaffGroupDefRole prepareCreate() {
        selected = new StaffGroupDefRole();
        return selected;
    }

    public StaffGroupDefRole prepareEdit() {
        edited = selected;
        return edited;
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
                getString("StaffGroupDefRoleReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRoleReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRoleToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRoleToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRoleToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRoleToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * ************************************************************************
     * TABLE OPTIONS
     *
     * ************************************************************************
     */
    /**
     *
     * @param e toogle Event
     */
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("StaffGroupDefRole : Toggle Column",
                "Column n° " + e.getData() + " is now " + e.getVisibility());

    }

    public void handleColumnReorder(javax.faces.event.AjaxBehaviorEvent e) {
        DataTable table = (DataTable) e.getSource();
        String columns = "";
        int i = 0;
        for (UIColumn column : table.getColumns()) {
            UIComponent uic = (UIComponent) column;
            String headerText = (String) uic.getAttributes().get((Object) "headerText");
            Boolean visible = (Boolean) uic.getAttributes().get((Object) "visible");
            headerTextMap.replace(i, headerText);
            visibleColMap.replace(headerText, visible);
            columns += headerText + "(" + visible + ") <br >";
            i++;
        }
        JsfUtil.addSuccessMessage("StaffGroupDefRole : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleGroupChange(ValueChangeEvent event) {
        StaffGroupDef stgd = (StaffGroupDef) event.getNewValue();
        if (stgd != null) {
            // Reset node access tree
            CtrlAccessService cas = new CtrlAccessService();
            nodeAccessTree = cas.securityAccess();
            // Set node tree with existing property 
            cas.securitySetPropertyByGroup(nodeAccessTree, this.getItems(stgd));
            //JsfUtil.addSuccessMessage("HandleGroupChanged", "Groupe change with success");
        } else {
            nodeAccessTree = null;
        }

    }

    public void handleTreeTableSelect(NodeSelectEvent event) {
        CheckboxTreeNode node = (CheckboxTreeNode) event.getTreeNode();
        node.setSelected(true, true);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleTreeTableUnSelect(NodeUnselectEvent event) {
        CheckboxTreeNode node = (CheckboxTreeNode) event.getTreeNode();
        node.setSelected(false, true);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    public void create() {
        // Set time on creation action
        selected.setStgdrChanged(new Date());
        selected.setStgdrCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceCreatedDetail")
                + selected.getStgdrGroupDef() + " <br > " + selected.getStgdrRole());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new StaffGroupDefRole();

            } else {
                /*JsfUtil.out("is not on multicreation");*/
                List<StaffGroupDefRole> staffGroupDefRoleList = getFacade().findAll();
                selected = staffGroupDefRoleList.get(staffGroupDefRoleList.size() - 1);
            }
        }
    }

    public void createTree() {
        createTreeProgress = 0;
        // La selection ne peut pas être vide
        if (selected == null) {
            JsfUtil.addErrorMessage("StaffGroupDefRole", "CreateTree : selected is null !");
            return;
        }

        // Le groupe ne peut pas être null
        StaffGroupDef groupDef = selected.getStgdrGroupDef();
        if (groupDef == null) {
            JsfUtil.addErrorMessage("StaffGroupDefRole", "CreateTree : none of the group is chose !");
            return;
        }

        // Remove all about the existing groupe 
        selected.setStgdrActivated(true);
        StaffGroupDefRole save = selected;
        destroyList(this.getItems(groupDef));
        createTreeProgress = 50;
        if (nodeAccessTree != null) {
            processCreateTree(nodeAccessTree, save);
        }
        createTreeProgress = 100;
        JsfUtil.addSuccessMessage("Apply finished !");
    }

    private void processCreateTree(TreeNode childNode, StaffGroupDefRole saveSelected) {
        selected = new StaffGroupDefRole();
        selected = saveSelected;
        // Create a new list
        CheckboxTreeNode node = (CheckboxTreeNode) childNode;
        if (node.isSelected() || node.isPartialSelected()) {
            CtrlAccess ctrl = (CtrlAccess) node.getData();
            IsmRole role = roleCtrl.getItemBy(ctrl.getCode()); // Check role as if parent
            selected.setStgdrRole(role);
            if (role != null) {
                this.create();
            } else {
                JsfUtil.out("Create Tree", "Role " + ctrl.getCode() + " was not found not created is null");
                JsfUtil.addErrorMessage("Create Tree",
                        "Role " + ctrl.getCode() + " was not found not created is null");
            }
        }
        // Reccursive if has child
        int nbRole = roleCtrl.getItems().size();
        createTreeProgress++;
        node.getChildren().stream().forEach((_childNode) -> {
            processCreateTree(_childNode, saveSelected);
        });

    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setStgdrChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceUpdatedDetail")
                + selected.getStgdrGroupDef() + " <br > " + selected.getStgdrRole());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("StaffGroupDefRolePersistenceDeletedDetail")
                + selected.getStgdrGroupDef() + " <br > " + selected.getStgdrRole());
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
        }
    }

    public void destroyList(List<StaffGroupDefRole> stgdrs) {
        int nbRole = roleCtrl.getItems().size();
        if (stgdrs != null) {
            Iterator<StaffGroupDefRole> itr = stgdrs.iterator();
            int i = 0;
            while (itr.hasNext()) {
                selected = itr.next();
                destroy();
                i++;
                createTreeProgress = 50 * i / stgdrs.size();
            }
        }
    }

    private void persist(PersistAction persistAction, String summary, String detail) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                //JsfUtil.addSuccessMessage(summary, detail);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(summary, msg);
                } else {
                    JsfUtil.addErrorMessage(ex, summary,
                            ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, summary, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
            }
        }
    }

    private void persist(PersistAction persistAction, String detail) {
        persist(persistAction, detail, detail);
    }

    /**
     * ************************************************************************
     * JPA
     *
     * ************************************************************************
     */
    /**
     *
     * @param id staff group def id
     * @return corresponding staff group def role of the id
     */
    public StaffGroupDefRole getStaffGroupDefRole(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<StaffGroupDefRole> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<StaffGroupDefRole> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<StaffGroupDefRole> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<StaffGroupDefRole> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * ************************************************************************
     * GETTER / SETTER
     *
     * ************************************************************************
     */
    /**
     *
     * @return staff group def role
     */
    public StaffGroupDefRole getSelected() {
        if (selected == null) {
            selected = new StaffGroupDefRole();
        }
        return selected;
    }

    public void setSelected(StaffGroupDefRole selected) {
        this.selected = selected;
    }

    public Boolean getIsReleaseSelected() {
        return isReleaseSelected;
    }

    public void setIsReleaseSelected(Boolean isReleaseSelected) {
        this.isReleaseSelected = isReleaseSelected;
    }

    public Boolean getIsOnMultiCreation() {
        return isOnMultiCreation;
    }

    public void setIsOnMultiCreation(Boolean isOnMultiCreation) {
        this.isOnMultiCreation = isOnMultiCreation;
    }

    public Map<String, Boolean> getVisibleColMap() {
        return visibleColMap;
    }

    public void setVisibleColMap(Map<String, Boolean> visibleColMap) {
        this.visibleColMap = visibleColMap;
    }

    public Boolean getIsVisibleColKey(String key) {
        return this.visibleColMap.get(key);
    }

    public CheckboxTreeNode getNodeAccessTree() {
        return nodeAccessTree;
    }

    public void setNodeAccessTree(CheckboxTreeNode nodeAccessTree) {
        this.nodeAccessTree = nodeAccessTree;
    }

    public int getCreateTreeProgress() {
        return createTreeProgress;
    }

    public void setCreateTreeProgress(int createTreeProgress) {
        this.createTreeProgress = createTreeProgress;
    }

    public List<StaffGroupDefRole> getItems(StaffGroupDef groupDef) {
        return getFacade().findByStaffGroups(groupDef);
    }

    public StaffGroupDefRole getItemsBy(StaffGroupDef stgdrGroupDef, IsmRole role) {
        return getFacade().findBy(stgdrGroupDef, role);
    }
    
    public List<StaffGroupDefRole> getItemsByStaffGroupDefRole(StaffGroupDef stgdrGroupDef, IsmRole role, Company company) {
        return getFacade().findByGroupDefRole(stgdrGroupDef, role, company);
    }


}
