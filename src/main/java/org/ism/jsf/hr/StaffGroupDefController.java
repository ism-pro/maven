package org.ism.jsf.hr;

import org.ism.entities.hr.StaffGroupDef;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.hr.StaffGroupDefFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.component.UIComponent;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.ism.entities.admin.Company;
import org.ism.entities.hr.Staff;

@ManagedBean(name = "staffGroupDefController")
@SessionScoped
public class StaffGroupDefController implements Serializable {

    @EJB
    private org.ism.sessions.hr.StaffGroupDefFacade ejbFacade;
    private List<StaffGroupDef> items = null;
    private StaffGroupDef selected;
    private StaffGroupDef edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public StaffGroupDefController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdGroupDef"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdDesignation"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdDeleted"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdCreated"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdChanged"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdGroupDef"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdDesignation"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdDeleted"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdChanged"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffGroupDefField_stgdCompany"), false);
    }

    protected void setEmbeddableKeys() {
    }

    private StaffGroupDefFacade getFacade() {
        return ejbFacade;
    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    /**
     *
     * @return a definition of a staff group
     */
    public StaffGroupDef prepareCreate() {
        selected = new StaffGroupDef();
        return selected;
    }

    public StaffGroupDef prepareEdit() {
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
                        getString("StaffGroupDefReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * ************************************************************************
     * TABLE OPTIONS
     *
     * ************************************************************************
     */
    /**
     *
     * @param e toogleEvent e
     */
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("StaffGroupDef : Toggle Column",
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
        JsfUtil.addSuccessMessage("StaffGroupDef : Reorder Column",
                "Columns : <br>" + columns);

    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    public void create() {
        // Set time on creation action
        selected.setStgdChanged(new Date());
        selected.setStgdCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceCreatedDetail")
                + selected.getStgdGroupDef() + " <br > " + selected.getStgdDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new StaffGroupDef();

            } else {
                JsfUtil.out("is not on multicreation");
                List<StaffGroupDef> staffGroupDef = getFacade().findAll();
                selected = staffGroupDef.get(staffGroupDef.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setStgdChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceUpdatedDetail")
                + selected.getStgdGroupDef() + " <br > " + selected.getStgdDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffGroupDefPersistenceDeletedDetail")
                + selected.getStgdGroupDef() + " <br > " + selected.getStgdDesignation());
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
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

    /**
     * ************************************************************************
     * JPA
     *
     * ************************************************************************
     */
    /**
     *
     * @param id of staff group def
     * @return the staffGroupDef corresponding to id
     */
    public StaffGroupDef getStaffGroupDef(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<StaffGroupDef> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<StaffGroupDef> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<StaffGroupDef> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<StaffGroupDef> getItemsByCode(String code, Company company) {
        return getFacade().findByCode(code, company);
    }

    public List<StaffGroupDef> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<StaffGroupDef> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<StaffGroupDef> getItemsByCompany(Company company) {
        return getFacade().findByCompany(company);
    }

    public List<StaffGroupDef> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<StaffGroupDef> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<StaffGroupDef> getItemsGroupByCompany() {
        return getFacade().findGroupByCompany();
    }

    public List<SelectItem> getItemsGrouped() {
        Iterator<StaffGroupDef> itr = getItemsGroupByCompany().iterator();

        List<SelectItem> lstGroup = new ArrayList<>();
        while (itr.hasNext()) {
            StaffGroupDef group = itr.next();
            // Create a groupe
            SelectItemGroup gr = new SelectItemGroup(group.getStgdCompany().getCCompany() + " - " + group.getStgdCompany().getCDesignation());
            SelectItem itm[] = new SelectItem[this.getItemsByCompany(group.getStgdCompany()).size()];

            Iterator<StaffGroupDef> existingGroupItr = this.getItems().iterator();
            int i = 0;
            while (existingGroupItr.hasNext()) {
                StaffGroupDef existingGroup = existingGroupItr.next();
                String C1 = group.getStgdCompany().getCCompany();
                String C2 = existingGroup.getStgdCompany().getCCompany();
                if (C1.matches(C2)) {
                    itm[i] = new SelectItem(existingGroup);
                    itm[i].setLabel(existingGroup.getStgdGroupDef() + " - " + existingGroup.getStgdDesignation());
                    itm[i].setDescription(existingGroup.getStgdDesignation());
                    i++;
                }
            }
            gr.setSelectItems(itm);
            lstGroup.add(gr);
        }

        return lstGroup;
    }

    /**
     * ************************************************************************
     * GETTER / SETTER
     *
     * ************************************************************************
     */
    /**
     *
     * @return selected staff Group definition
     */
    public StaffGroupDef getSelected() {
        if (selected == null) {
            selected = new StaffGroupDef();
        }
        return selected;
    }

    public void setSelected(StaffGroupDef selected) {
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

}
