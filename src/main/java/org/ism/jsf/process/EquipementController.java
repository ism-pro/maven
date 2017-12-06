package org.ism.jsf.process;

import org.ism.entities.process.Equipement;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.process.EquipementFacade;

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

@ManagedBean(name = "equipementController")
@SessionScoped
public class EquipementController implements Serializable {

    @EJB
    private org.ism.sessions.process.EquipementFacade ejbFacade;
    private List<Equipement> items = null;
    private Equipement selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public EquipementController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // STRING PARSE
        String src_01 = "EquipementField_EId";
        String src_02 = "EquipementField_EEquipement";
        String src_03 = "EquipementField_EDesignation";
        String src_04 = "EquipementField_ECf";
        String src_05 = "EquipementField_EFamille";
        String src_06 = "EquipementField_ESsFamille";
        String src_07 = "EquipementField_EGroupe";
        String src_08 = "EquipementField_EResponsable";
        String src_09 = "EquipementField_EDescription";
        String src_10 = "EquipementField_EDeleted";
        String src_11 = "EquipementField_ECreated";
        String src_12 = "EquipementField_EChanged";
        String src_13 = "EquipementField_ECompany";

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
        headerTextMap.put(9, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09));
        headerTextMap.put(10, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10));
        headerTextMap.put(11, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_11));
        headerTextMap.put(12, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_12));
        headerTextMap.put(13, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_13));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_11), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_12), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_13), false);

    }

    private EquipementFacade getFacade() {
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
     * @return prepared equipement
     */
    public Equipement prepareCreate() {
        selected = new Equipement();
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
                        getString("EquipementReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("Equipement : Toggle Column",
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
        JsfUtil.addSuccessMessage("Equipement : Reorder Column",
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
        selected.setEChanged(new Date());
        selected.setECreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceCreatedDetail")
                + selected.getEEquipement() + " <br > " + selected.getEDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Equipement();

            } else {
                JsfUtil.out("is not on multicreation");
                List<Equipement> v_Equipement = getFacade().findAll();
                selected = v_Equipement.get(v_Equipement.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setEChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceUpdatedDetail")
                + selected.getEEquipement() + " <br > " + selected.getEDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EquipementPersistenceDeletedDetail")
                + selected.getEEquipement() + " <br > " + selected.getEDesignation());
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
     * @param id equipement key
     * @return corresponding equipement object
     */
    public Equipement getEquipement(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Equipement> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Equipement> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<Equipement> getItemsByEquipement(String _Equipement) {
        return getFacade().findByCode(_Equipement);
    }

    public List<Equipement> getItemsByCode(String equipement, Company company) {
        return getFacade().findByCode(equipement, company);
    }

    public List<Equipement> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<Equipement> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<Equipement> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Equipement> getItemsAvailableSelectOne() {
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
     * @return selected equipement
     */
    public Equipement getSelected() {
        if (selected == null) {
            selected = new Equipement();
        }
        return selected;
    }

    public void setSelected(Equipement selected) {
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
