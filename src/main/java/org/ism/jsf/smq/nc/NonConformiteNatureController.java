package org.ism.jsf.smq.nc;

import org.ism.entities.smq.nc.NonConformiteNature;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.nc.NonConformiteNatureFacade;

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

@ManagedBean(name = "nonConformiteNatureController")
@SessionScoped
public class NonConformiteNatureController implements Serializable {

    @EJB
    private org.ism.sessions.smq.nc.NonConformiteNatureFacade ejbFacade;
    private List<NonConformiteNature> items = null;
    private NonConformiteNature selected;
    private NonConformiteNature edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public NonConformiteNatureController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        /*
        NonConformiteNatureFieldInfo_ncnId=N°
        NonConformiteNatureFieldInfo_ncnCompany=Société
        NonConformiteNatureFieldInfo_ncnNature=Nature
        NonConformiteNatureFieldInfo_ncnDesignation=Désignation
        NonConformiteNatureFieldInfo_ncnDeleted=Suppression
        NonConformiteNatureFieldInfo_ncnCreated=Création
        NonConformiteNatureFieldInfo_ncnChanged=Modif.
         */
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnNature"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnDesignation"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnDeleted"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnCreated"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnChanged"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnNature"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnDesignation"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnDeleted"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnChanged"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteNatureField_ncnCompany"), false);

    }

    protected void setEmbeddableKeys() {
    }

    private NonConformiteNatureFacade getFacade() {
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
     * @return prepare selected non conformite nature
     */
    public NonConformiteNature prepareCreate() {
        selected = new NonConformiteNature();
        return selected;
    }

    public NonConformiteNature prepareEdit() {
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
                        getString("NonConformiteNatureReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNatureReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNatureToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNatureToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNatureToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNatureToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("NonConformiteNature : Toggle Column",
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
        JsfUtil.addSuccessMessage("NonConformiteNature : Reorder Column",
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
        selected.setNcnChanged(new Date());
        selected.setNcnCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceCreatedDetail")
                + selected.getNcnNature() + " <br > " + selected.getNcnDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteNature();

            } else {
                JsfUtil.out("is not on multicreation");
                List<NonConformiteNature> processus = getFacade().findAll();
                selected = processus.get(processus.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setNcnChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceUpdatedDetail")
                + selected.getNcnNature() + " <br > " + selected.getNcnDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteNaturePersistenceDeletedDetail")
                + selected.getNcnNature() + " <br > " + selected.getNcnDesignation());
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
     * @param id non conformite nature key
     * @return non conformite nature object
     */
    public NonConformiteNature getNonConformiteNature(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteNature> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<NonConformiteNature> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<NonConformiteNature> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<NonConformiteNature> getItemsByCode(String code, Company company) {
        return getFacade().findByCode(code, company);
    }

    public List<NonConformiteNature> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<NonConformiteNature> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<NonConformiteNature> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteNature> getItemsAvailableSelectOne() {
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
     * @return selected non conformite nature
     */
    public NonConformiteNature getSelected() {
        if (selected == null) {
            selected = new NonConformiteNature();
        }
        return selected;
    }

    public void setSelected(NonConformiteNature selected) {
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
