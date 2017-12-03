package org.ism.jsf.smq.nc;

import org.ism.entities.smq.nc.NonConformiteUnite;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.nc.NonConformiteUniteFacade;

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
import org.ism.entities.smq.Processus;

@ManagedBean(name = "nonConformiteUniteController")
@SessionScoped
public class NonConformiteUniteController implements Serializable {

    @EJB
    private org.ism.sessions.smq.nc.NonConformiteUniteFacade ejbFacade;
    private List<NonConformiteUnite> items = null;
    private NonConformiteUnite selected;
    private NonConformiteUnite edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public NonConformiteUniteController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        /*
        NonConformiteUniteField_ncuId=N°
        NonConformiteUniteField_ncuCompany=Société
        NonConformiteUniteField_ncuUnite=Fréquence
        NonConformiteUniteField_ncuDesignation=Désignation
        NonConformiteUniteField_ncuDeleted=Suppression
        NonConformiteUniteField_ncuCreated=Création
        NonConformiteUniteField_ncuChanged=Modif.
         */
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuUnite"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuDesignation"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuDeleted"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuCreated"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuChanged"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuUnite"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuDesignation"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuDeleted"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuChanged"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteUniteField_ncuCompany"), false);

    }

    protected void setEmbeddableKeys() {
    }

    private NonConformiteUniteFacade getFacade() {
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
     * @return prepared non conformite unite
     */
    public NonConformiteUnite prepareCreate() {
        selected = new NonConformiteUnite();
        return selected;
    }

    public NonConformiteUnite prepareEdit() {
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
                        getString("NonConformiteUniteReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUniteReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUniteToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUniteToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUniteToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUniteToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("NonConformiteUnite : Toggle Column",
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
        JsfUtil.addSuccessMessage("NonConformiteUnite : Reorder Column",
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
        selected.setNcuChanged(new Date());
        selected.setNcuCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceCreatedDetail")
                + selected.getNcuUnite() + " <br > " + selected.getNcuDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteUnite();

            } else {
                JsfUtil.out("is not on multicreation");
                List<NonConformiteUnite> processus = getFacade().findAll();
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
        selected.setNcuChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceUpdatedDetail")
                + selected.getNcuUnite() + " <br > " + selected.getNcuDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteUnitePersistenceDeletedDetail")
                + selected.getNcuUnite() + " <br > " + selected.getNcuDesignation());
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
     * @param id non conformite unite key
     * @return corresponding non conformite unite object
     */
    public NonConformiteUnite getNonConformiteUnite(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteUnite> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<NonConformiteUnite> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<NonConformiteUnite> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }
    
    
    public List<NonConformiteUnite> getItemsByCode(String code, Company company) {
        return getFacade().findByCode(code, company);
    }

    public List<NonConformiteUnite> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }
    
    
    public List<NonConformiteUnite> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }


    public List<NonConformiteUnite> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteUnite> getItemsAvailableSelectOne() {
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
     * @return selected non conformite unite
     */
    public NonConformiteUnite getSelected() {
        if (selected == null) {
            selected = new NonConformiteUnite();
        }
        return selected;
    }

    public void setSelected(NonConformiteUnite selected) {
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
