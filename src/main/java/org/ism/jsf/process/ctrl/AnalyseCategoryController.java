package org.ism.jsf.process.ctrl;

import org.ism.entities.process.ctrl.AnalyseCategory;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.process.ctrl.AnalyseCategoryFacade;

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
import org.ism.entities.admin.Company;

@ManagedBean(name = "analyseCategoryController")
@SessionScoped
public class AnalyseCategoryController implements Serializable {

    @EJB
    private org.ism.sessions.process.ctrl.AnalyseCategoryFacade ejbFacade;
    private List<AnalyseCategory> items = null;
    private AnalyseCategory selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public AnalyseCategoryController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // STRING PARSE
        String src_01 = "AnalyseCategoryField_acId";
        String src_02 = "AnalyseCategoryField_acCategory";
        String src_03 = "AnalyseCategoryField_acDesignation";
        String src_04 = "AnalyseCategoryField_acDescription";
        String src_05 = "AnalyseCategoryField_acDeleted";
        String src_06 = "AnalyseCategoryField_acCreated";
        String src_07 = "AnalyseCategoryField_acChanged";
        String src_08 = "AnalyseCategoryField_acCompany";

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
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), false);

    }

    private AnalyseCategoryFacade getFacade() {
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
     * @return prepared analyse category
     */
    public AnalyseCategory prepareCreate() {
        selected = new AnalyseCategory();
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
                        getString("AnalyseCategoryReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * ************************************************************************
     * TABLE OPTIONS
     *
     * ************************************************************************
     */
    /**
     *
     * @param e toggle event
     */
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("AnalyseCategory : Toggle Column",
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
        JsfUtil.addSuccessMessage("AnalyseCategory : Reorder Column",
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
        selected.setAcChanged(new Date());
        selected.setAcCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceCreatedDetail")
                + selected.getAcCategory() + " <br > " + selected.getAcDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new AnalyseCategory();

            } else {
                JsfUtil.out("is not on multicreation");
                List<AnalyseCategory> v_AnalyseCategory = getFacade().findAll();
                selected = v_AnalyseCategory.get(v_AnalyseCategory.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setAcChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceUpdatedDetail")
                + selected.getAcCategory() + " <br > " + selected.getAcDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseCategoryPersistenceDeletedDetail")
                + selected.getAcCategory() + " <br > " + selected.getAcDesignation());
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

    /**
     * ************************************************************************
     * JPA
     *
     * ************************************************************************
     */
    /**
     *
     * @param id integer id of the analyse category
     * @return corresponding analyse category
     */
    public AnalyseCategory getAnalyseCategory(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AnalyseCategory> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<AnalyseCategory> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<AnalyseCategory> getItemsByAnalyseCategory(String _AnalyseCategory) {
        return getFacade().findByCode(_AnalyseCategory);
    }

    public List<AnalyseCategory> getItemsByCode(String category, Company company) {
        return getFacade().findByCode(category, company);
    }

    public List<AnalyseCategory> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<AnalyseCategory> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<AnalyseCategory> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AnalyseCategory> getItemsAvailableSelectOne() {
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
     * @return selected analyse category
     */
    public AnalyseCategory getSelected() {
        if (selected == null) {
            selected = new AnalyseCategory();
        }
        return selected;
    }

    public void setSelected(AnalyseCategory selected) {
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
