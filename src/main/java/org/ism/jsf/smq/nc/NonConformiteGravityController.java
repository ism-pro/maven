package org.ism.jsf.smq.nc;

import org.ism.entities.smq.nc.NonConformiteGravity;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.nc.NonConformiteGravityFacade;

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

@ManagedBean(name = "nonConformiteGravityController")
@SessionScoped
public class NonConformiteGravityController implements Serializable {

    @EJB
    private org.ism.sessions.smq.nc.NonConformiteGravityFacade ejbFacade;
    private List<NonConformiteGravity> items = null;
    private NonConformiteGravity selected;
    private NonConformiteGravity edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public NonConformiteGravityController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        /*
        NonConformiteGravityField_ncgId=N°
        NonConformiteGravityField_ncgCompany=Société
        NonConformiteGravityField_ncgGravity=Fréquence
        NonConformiteGravityField_ncgDesignation=Désignation
        NonConformiteGravityField_ncgDeleted=Suppression
        NonConformiteGravityField_ncgCreated=Création
        NonConformiteGravityField_ncgChanged=Modif.
         */
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgGravity"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgDesignation"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgDeleted"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgCreated"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgChanged"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgGravity"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgDesignation"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgDeleted"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgChanged"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteGravityField_ncgCompany"), false);

    }

    protected void setEmbeddableKeys() {
    }

    private NonConformiteGravityFacade getFacade() {
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
     * @return prepared non conformite gravity
     */
    public NonConformiteGravity prepareCreate() {
        selected = new NonConformiteGravity();
        return selected;
    }

    public NonConformiteGravity prepareEdit() {
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
                        getString("NonConformiteGravityReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("NonConformiteGravity : Toggle Column",
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
        JsfUtil.addSuccessMessage("NonConformiteGravity : Reorder Column",
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
        selected.setNcgChanged(new Date());
        selected.setNcgCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceCreatedDetail")
                + selected.getNcgGravity() + " <br > " + selected.getNcgDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteGravity();

            } else {
                JsfUtil.out("is not on multicreation");
                List<NonConformiteGravity> processus = getFacade().findAll();
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
        selected.setNcgChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceUpdatedDetail")
                + selected.getNcgGravity() + " <br > " + selected.getNcgDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteGravityPersistenceDeletedDetail")
                + selected.getNcgGravity() + " <br > " + selected.getNcgDesignation());
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
     * @param id non conformite gravity key
     * @return corresponding non conformite gravity object
     */
    public NonConformiteGravity getNonConformiteGravity(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteGravity> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<NonConformiteGravity> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<NonConformiteGravity> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<NonConformiteGravity> getItemsByCode(String code, Company company) {
        return getFacade().findByCode(code, company); 
    }

    public List<NonConformiteGravity> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<NonConformiteGravity> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<NonConformiteGravity> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteGravity> getItemsAvailableSelectOne() {
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
     * @return selected non conformite gravity.
     */
    public NonConformiteGravity getSelected() {
        if (selected == null) {
            selected = new NonConformiteGravity();
        }
        return selected;
    }

    public void setSelected(NonConformiteGravity selected) {
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
