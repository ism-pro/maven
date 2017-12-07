package org.ism.jsf.smq;

import org.ism.entities.smq.ProcessAccess;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.ProcessAccessFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroups;
import org.ism.entities.smq.DocExplorer;
import org.ism.jsf.hr.StaffAuthController;

@ManagedBean(name = "processAccessController")
@SessionScoped
public class ProcessAccessController implements Serializable {

    @EJB
    private org.ism.sessions.smq.ProcessAccessFacade ejbFacade;
    private List<ProcessAccess> items = null;
    private List<ProcessAccess> itemsDoc = null;
    private ProcessAccess selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    private List<ProcessAccess> itemsByLast;  // Specify current items list
    private List<ProcessAccess> itemsFiltered;// Specify current filtered

    @Inject
    StaffAuthController staffAuthController;
    @Inject
    DocExplorerController docExplorerController;

    public ProcessAccessController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise

        // Init. list
        itemsByLast = getItemsByLastChanged();

        // 
        // STRING PARSE
        String src_01 = "ProcessAccessField_paId";
        String src_02 = "ProcessAccessField_paDocexplorer";
        String src_03 = "ProcessAccessField_paGroupdef";
        String src_04 = "ProcessAccessField_paStaff";
        String src_05 = "ProcessAccessField_paIsgroup";
        String src_06 = "ProcessAccessField_paDeleted";
        String src_07 = "ProcessAccessField_paCreated";
        String src_08 = "ProcessAccessField_paChanged";

        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), false);

    }

    private ProcessAccessFacade getFacade() {
        return ejbFacade;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// SPECIFIC FONCTION
    ///
    ////////////////////////////////////////////////////////////////////////////
    public ProcessAccess prepareCreate() {
        selected = new ProcessAccess();
        return selected;
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
                getString("ProcessAccessReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /////////////////////////////////////////////////////////////////////////////
    /// TABLE OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("ProcessAccess : Toggle Column",
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
        JsfUtil.addSuccessMessage("ProcessAccess : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleTableChanges() {
        itemsByLast = getItemsByLastChanged();
    }

    public void handleDestroy() {
        destroy();
        itemsByLast = getItemsByLastChanged();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CRUD OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void create() {
        // Set time on creation action
        selected.setPaChanged(new Date());
        selected.setPaCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceCreatedDetail")
                + selected.getPaDocexplorer().getDcId());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new ProcessAccess();

            } else {
                JsfUtil.out("is not on multicreation");
                List<ProcessAccess> v_ProcessAccess = getFacade().findAll();
                selected = v_ProcessAccess.get(v_ProcessAccess.size() - 1);
            }
        }
    }

    public void createUnReleased() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setPaChanged(new Date());
        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceUpdatedDetail")
                + selected.getPaDocexplorer().getDcId() + " <br > ");

    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("ProcessAccessPersistenceDeletedDetail")
                + selected.getPaDocexplorer().getDcId() + " <br > ");

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
        }
    }

    private void persist(PersistAction persistAction, String summary, String detail) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(summary, detail);
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

    ////////////////////////////////////////////////////////////////////////////
    /// JPA
    ///
    ////////////////////////////////////////////////////////////////////////////
    public ProcessAccess getProcessAccess(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProcessAccess> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<ProcessAccess> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<ProcessAccess> getItemsByDocLast(DocExplorer document) {
        itemsDoc = getFacade().findAllByDocLast(document);
        return itemsDoc;
    }

    public List<ProcessAccess> getItemsDoc() {
        return itemsDoc;
    }

    public void setItemsDoc(List<ProcessAccess> itemsDoc) {
        this.itemsDoc = itemsDoc;
    }

    /*
    public List<ProcessAccess> getItemsByProcessAccess(String _ProcessAccess) {
        return getFacade().findByCode(_ProcessAccess);
    }

    public List<ProcessAccess> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }
     */
    public List<ProcessAccess> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProcessAccess> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<ProcessAccess> getItemsByStaff(Staff staff){
        return getFacade().findByStaff(staff);
    }
    
    public List<ProcessAccess> getItemsByDocument(DocExplorer docExplorer){
        return getFacade().findByUnGroupDocument(docExplorer);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// GETTER / SETTER
    ///
    ////////////////////////////////////////////////////////////////////////////
    public ProcessAccess getSelected() {
        if (selected == null) {
            selected = new ProcessAccess();
        }
        return selected;
    }

    public void setSelected(ProcessAccess selected) {
        this.selected = selected;
    }

    public void setSelectedExplorer(DocExplorer docExplorer) {
        this.selected.setPaDocexplorer(docExplorer);
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

    public List<ProcessAccess> getItemsByLast() {
        return itemsByLast;
    }

    public void setItemsByLast(List<ProcessAccess> itemsByLast) {
        this.itemsByLast = itemsByLast;
    }

    public List<ProcessAccess> getItemsFiltered() {
        return itemsFiltered;
    }

    public void setItemsFiltered(List<ProcessAccess> itemsFiltered) {
        this.itemsFiltered = itemsFiltered;
    }

    public Boolean isUserAllowed(Staff staff) {
        List<ProcessAccess> lst = getFacade().findByStaff(staff);
        if (lst == null) {
            return false;
        }
        return true;
    }

    public Boolean isGroupAllowed(List<StaffGroups> staffGroups) {
        if (staffGroups == null) {
            return false;
        }
        for (int i = 0; i < staffGroups.size(); i++) {
            List<ProcessAccess> lst = getFacade().findAllByGroup(staffGroups.get(i).getStgGroupDef());
            if (lst != null) {
                return true;
            }
        }
        return false;
    }

    public Boolean isUserDocumentAllowed(DocExplorer docExplorer, Staff staff, List<StaffGroups> staffGroups) {
        if (docExplorer == null) {
            return false;
        }

        if (staff != null) {
            List<ProcessAccess> lst = getFacade().findByDocAndStaff(docExplorer, staff);
            if (lst != null && lst.size() > 0) {
                return true;
            }
        }

        if (staffGroups == null) {
            return false;
        }
        for (int i = 0; i < staffGroups.size(); i++) {
            List<ProcessAccess> lst = getFacade().findByDocAndGroup(docExplorer, staffGroups.get(i).getStgGroupDef());
            if (lst != null) {
                return true;
            }
        }

        return false;
    }

}
