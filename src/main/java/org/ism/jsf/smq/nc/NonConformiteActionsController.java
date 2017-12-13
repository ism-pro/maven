package org.ism.jsf.smq.nc;

import org.ism.entities.smq.nc.NonConformiteActions;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.nc.NonConformiteActionsFacade;

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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.entities.app.IsmNcastate;
import org.ism.entities.smq.Processus;
import org.ism.entities.smq.nc.NonConformiteRequest;

@ManagedBean(name = "nonConformiteActionsController")
@SessionScoped
public class NonConformiteActionsController implements Serializable {

    @EJB
    private org.ism.sessions.smq.nc.NonConformiteActionsFacade ejbFacade;

    @ManagedProperty(value = "#{nonConformiteRequestController}")
    private NonConformiteRequestController ncRequestCtrl;

    private List<NonConformiteActions> items = null;
    private List<NonConformiteActions> itemsNC = null;  //!< This is items when a request with nc is apply
    private NonConformiteActions selected;
    private NonConformiteActions edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public NonConformiteActionsController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        /*
        NonConformiteActionsField_ncaId=N°
        NonConformiteActionsField_ncaDescription=Description
        NonConformiteActionsField_ncaDeadline=Echéance
        NonConformiteActionsField_ncaStaffRefuse
        NonConformiteActionsField_ncaDescRefuse
        NonConformiteActionsField_ncaCreated=Création
        NonConformiteActionsField_ncaChanged=Modif.
        NonConformiteActionsField_ncaNc=NC
        NonConformiteActionsField_ncaStaff=Staff
        NonConformiteActionsField_ncaState=Etat
         */

        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaStaff"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDescription"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDeadline"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaStaffApprouver"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDescApprouver"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaCreated"));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaChanged"));
        headerTextMap.put(9, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaNc"));
        headerTextMap.put(10, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaState"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaStaff"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDescription"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDeadline"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaStaffApprouver"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaDescApprouver"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaChanged"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaNc"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteActionsField_ncaState"), true);

    }

    protected void setEmbeddableKeys() {
    }

    private NonConformiteActionsFacade getFacade() {
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
     * @return prepared non conformite actions
     */
    public NonConformiteActions prepareCreate() {
        selected = new NonConformiteActions();
        return selected;
    }

    public NonConformiteActions prepareEdit() {
        edited = selected;
        return edited;
    }

    public NonConformiteActions prepareAction(NonConformiteRequest ncr) {
        selected = new NonConformiteActions();
        selected.setNcaNc(ncr);
        itemsNC = this.getItemsDesc(ncr);
        return selected;
    }

    public NonConformiteActions prepareReview(NonConformiteRequest ncr) {
        itemsNC = this.getItemsDesc(ncr);
        selected = new NonConformiteActions();
        if (itemsNC != null) {
            selected = itemsNC.get(0);
        }
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
                getString("NonConformiteActionsReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("NonConformiteActions : Toggle Column",
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
        JsfUtil.addSuccessMessage("NonConformiteActions : Reorder Column",
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
        selected.setNcaChanged(new Date());
        selected.setNcaCreated(new Date());
        selected.setNcaState(new IsmNcastate(IsmNcastate.INPROGRESS_ID));

        // Save preview actions list
        itemsNC = getItemsDesc(selected.getNcaNc());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceCreatedDetail")
                + selected.getNcaId());

        if (!JsfUtil.isValidationFailed()) {
            // Gère l'action qui précède
            if (itemsNC != null) {// Il existait une nc
                NonConformiteActions saveSelected = selected;
                selected = itemsNC.get(0);
                selected.setNcaState(new IsmNcastate(IsmNcastate.CANCEL_ID));
                update();
                selected = saveSelected;
            }
            // Now update request state
            ncRequestCtrl.getSelected().setNcrenddingAction(selected.getNcaDeadline());
            ncRequestCtrl.updateOnActionCreate();
            

            items = null;    // Invalidate list of items to trigger re-query.
            itemsNC = null;
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteActions();

            } else {
                JsfUtil.out("is not on multicreation");
                List<NonConformiteActions> nonConformiteActions = getFacade().findAll();
                selected = nonConformiteActions.get(nonConformiteActions.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setNcaChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceUpdatedDetail")
                + selected.getNcaId());
    }

    public void updateOnReview() {
        selected.setNcaState(new IsmNcastate(IsmNcastate.REVIEW_ID));
        update();
        ncRequestCtrl.updateOnReview();
    }

    public void updateOnCloture(NonConformiteRequest nc) {
        selected = getItemsDesc(nc).get(0);
        selected.setNcaState(new IsmNcastate(IsmNcastate.FINISH_ID));
        update();
        ncRequestCtrl.updateOnCloture();
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteActionsPersistenceDeletedDetail")
                + selected.getNcaId());
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            itemsNC = null;
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
     * @param id non conformite actions key
     * @return corresponding non conformite actions object
     */
    public NonConformiteActions getNonConformiteActions(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteActions> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<NonConformiteActions> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<NonConformiteActions> getItemsNC() {
        return itemsNC;
    }

    public void setItemsNC(List<NonConformiteActions> itemsNC) {
        this.itemsNC = itemsNC;
    }

    public String getNCActionState(NonConformiteRequest nonConformite) {
        List<NonConformiteActions> actions = getFacade().findAllByNCLast(nonConformite);
        if (actions == null) {
            return "";
        }

        NonConformiteActions action = actions.get(0);
        if (action == null) {
            return "";
        }

        return action.getNcaState().getIstate() + " - " + action.getNcaState().getStatename();
    }

    public List<NonConformiteActions> getItemsByNCLast(NonConformiteRequest nc) {
        itemsNC = getFacade().findAllByNCLast(nc);
        return itemsNC;
    }

    /**
     * This method return null if non conformite is null or if no action found
     *
     * @param nc on which to found corresponding action
     * @return return null if no action associated from nc or if null nc
     */
    public NonConformiteActions getLastActionFromNC(NonConformiteRequest nc) {
        if (nc == null) {
            return null;
        }

        itemsNC = getFacade().findAllByNCLastID(nc);
        if (itemsNC == null) {
            return null;
        }

        return itemsNC.get(0);
    }

    public List<NonConformiteActions> getItemsDesc(NonConformiteRequest nc) {
        itemsNC = getFacade().findDescByNC(nc);
        return itemsNC;
    }

    public NonConformiteActions getItemsByNCFirst() {
        if (itemsNC == null) {
            if (selected == null) {
                return null;
            }
            if (selected.getNcaNc() == null) {
                return null;
            }
            if (getItemsByNCLast(selected.getNcaNc()) == null) {
                return null;
            }
        }
        return itemsNC.get(0);
    }

    public NonConformiteActions getItemsByNCLast() {
        if (itemsNC == null) {
            if (selected == null) {
                return null;
            }
            if (selected.getNcaNc() == null) {
                return null;
            }
            if (getItemsByNCLast(selected.getNcaNc()) == null) {
                return null;
            }
        }
        return itemsNC.get(itemsNC.size());
    }

    public List<NonConformiteActions> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<NonConformiteActions> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<NonConformiteActions> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteActions> getItemsAvailableSelectOne() {
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
     * @return selected non conformite actions
     */
    public NonConformiteActions getSelected() {
        /*if (selected == null) {
            selected = new NonConformiteActions();
        }*/
        return selected;
    }

    public void setSelected(NonConformiteActions selected) {
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

    public NonConformiteRequestController getNcRequestCtrl() {
        return ncRequestCtrl;
    }

    public void setNcRequestCtrl(NonConformiteRequestController ncRequestCtrl) {
        this.ncRequestCtrl = ncRequestCtrl;
    }

    /**
     * =========================================================================
     *
     * =========================================================================
     */
    /**
     *
     * @param from
     * @param to
     * @return
     */

    public Integer getCountItemsCreateInRange(Date from, Date to) {
        List<NonConformiteActions> l = getFacade().itemsCreateInRange(from, to);
        if (l == null || l.isEmpty()) {
            return 0;
        }
        return l.size();
    }

    public List<NonConformiteActions> getItemsCreateInRange(Date from, Date to) {
        return getFacade().itemsCreateInRange(from, to);
    }

    /**
     * *
     *
     */
    /**
     *
     * @param from
     * @param to
     * @param processus
     * @return
     */
    public List<NonConformiteActions> getItemsCreateInRangeByProcessus(Date from, Date to, Processus processus) {
        return getFacade().itemsCreateInRange(from, to, processus);
    }


}
