package org.ism.jsf.smq.nc;

import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.smq.nc.NonConformiteRequestFacade;
import org.ism.entities.app.IsmNcrstate;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import javax.faces.bean.ManagedProperty;
import org.ism.entities.admin.Company;
import org.ism.entities.smq.Processus;
import org.ism.jsf.admin.MailsenderController;
import org.ism.lazy.smq.nc.NonConformiteRequestLazyModel;
import org.ism.services.TableSet;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "nonConformiteRequestController")
@SessionScoped
public class NonConformiteRequestController implements Serializable {

    @EJB
    private org.ism.sessions.smq.nc.NonConformiteRequestFacade ejbFacade;

    @ManagedProperty(value = "#{mailsenderController}")
    private MailsenderController mailsenderController;

    private List<NonConformiteRequest> items = null;
    private NonConformiteRequest selected;
    private NonConformiteRequest edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    /**
     * Multi Sort Meta save table sorting
     */
    private List<SortMeta> multiSortMeta = null;
    /**
     * filters save table filters
     */
    private Map<String, Object> filters = null;

    /**
     * Define table setups include with Analyse data controller
     */
    private TableSet tableSet = new TableSet();

    /**
     * Define lazy model to load progressively data
     */
    private NonConformiteRequestLazyModel lazyModel;

//    private List<SortMeta> sortedValue;
//
//    public List getSortedValue() {
//        JsfUtil.out("Now Return Sorted Value");
//        List<SortMeta> sorted = sortedValue;
//        return sortedValue;
//    }
//
//    public void setSortedValue(List sortedValue) {
//        JsfUtil.out("Now Set Sorted Value");
//        List<SortMeta> sorted = sortedValue;
//        this.sortedValue = sortedValue;
//    }
    public NonConformiteRequestController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise

        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrStaff"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrTitle"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrProcessus"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrState"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrNature"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrGravity"));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrFrequency"));
        headerTextMap.put(9, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrOccured"));
        headerTextMap.put(10, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientname"));
        headerTextMap.put(11, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientaddress"));
        headerTextMap.put(12, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientphone"));
        headerTextMap.put(13, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientemail"));
        headerTextMap.put(14, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClienttype"));
        headerTextMap.put(15, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCreated"));
        headerTextMap.put(16, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrChanged"));
        headerTextMap.put(17, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrEnddingAction"));
        headerTextMap.put(18, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCompany"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrStaff"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrTitle"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrProcessus"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrState"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrNature"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrGravity"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrFrequency"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrOccured"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientname"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientaddress"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientphone"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClientemail"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrClienttype"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrChanged"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrEnddingAction"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCompany"), false);

        // Initialize lazy model
        lazyModel = new NonConformiteRequestLazyModel(ejbFacade);
    }

    private NonConformiteRequestFacade getFacade() {
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
     * @return prepared non conformite request
     */
    public NonConformiteRequest prepareCreate() {
        selected = new NonConformiteRequest();
        return selected;
    }

    public NonConformiteRequest prepareEdit() {
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
                        getString("NonConformiteRequestReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestToggleMultiCreationDetail") + isOnMultiCreation);
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

        JsfUtil.addSuccessMessage("NonConformiteRequest : Toggle Column",
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
        JsfUtil.addSuccessMessage("NonConformiteRequest : Reorder Column",
                "Columns : <br>" + columns);

    }

    /**
     * Handle Filter Date Range allow in a filter to manage a range sélection
     *
     * @param value a value is the corresponding filter
     * @param filter a object filtred
     * @param locale a local
     * @return true if ok
     * @throws ParseException an error
     */
    public boolean handleFilterDateRange(Object value, Object filter, Locale locale) throws ParseException {
        return JsfUtil.dateRangeIn(value, filter, locale);
    }

    public void handleTableChanges() {

    }

    /**
     * Handle filtering is used to save state of filters and restore it to lazy
     * model if it is different from lazy
     *
     * @param event filter event
     */
    public void handleFiltering(FilterEvent event) {
        filters = event.getFilters();
        lazyModel.setFilters(filters);
    }

    /**
     * Handle sorting is used to save state of sort in the lazy model to allow
     * restore while staying in the same stage.
     *
     * @param event while sorting
     */
    public void handleSorting(SortEvent event) {

        SortMeta sortMeta = new SortMeta(event.getSortColumn(),
                event.getSortColumn().getField(),
                event.isAscending() ? SortOrder.ASCENDING : SortOrder.DESCENDING,
                null);
        if (!multiSortMeta.contains(sortMeta)) {
            multiSortMeta.add(sortMeta);
        } else {
            // reorder
            multiSortMeta.remove(sortMeta);
            multiSortMeta.add(sortMeta);
        }
        lazyModel.setMultiSortMeta(multiSortMeta);
    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    public void create() {
        // Set time on creation action
        selected.setNcrChanged(new Date());
        selected.setNcrCreated(new Date());
        selected.setNcrState(new IsmNcrstate(IsmNcrstate.CREATE_ID));

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceCreatedDetail")
                + selected.getNcrTitle());

        if (!JsfUtil.isValidationFailed()) {

//            mailServiceController.setTo("raphaelhendrick@gmail.com");
//            mailServiceController.setSubject("ISM : NC Création d'une nouvelle nc");
//            mailServiceController.setMessage(""
//                    + "<h1>Non conformité n°" + selected.getNcrId() + " </h1>");
//            mailServiceController.sendMail(mailsenderController.getItemsByCompany(selected.getNcrCompany()));
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteRequest();

            } else {
                //JsfUtil.out("is not on multicreation");
                List<NonConformiteRequest> ncRequest = getFacade().findAll();
                selected = ncRequest.get(ncRequest.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setNcrChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceUpdatedDetail")
                + selected.getNcrTitle());
    }

    public void updateOnValidate() {
        selected.setNcrChanged(new Date());
        selected.setNcrapprouvedDate(new Date());
        if (selected.getNcrApprouved()) {
            selected.setNcrState(new IsmNcrstate(IsmNcrstate.WAITFORSOLUTION_ID));
        } else {
            selected.setNcrState(new IsmNcrstate(IsmNcrstate.CANCEL_ID));
        }
        update();
    }

    public void updateOnReview() {
        selected.setNcrChanged(new Date());
        selected.setNcrState(new IsmNcrstate(IsmNcrstate.WAITFORSOLUTION_ID));
        update();
    }

    public void updateOnActionCreate() { // Passe de attente de solution à en cours
        //selected.setNcrapprouvedDate(new Date());
        selected.setNcrChanged(new Date());
        selected.setNcrState(new IsmNcrstate(IsmNcrstate.INPROGRESS_ID));
        update();
    }

    public void updateOnCloture() { // Passe de attente de solution à en cours
        selected.setNcrState(new IsmNcrstate(IsmNcrstate.FINISH_ID));
        selected.setNcrChanged(new Date());
        update();
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("NonConformiteRequestPersistenceDeletedDetail")
                + selected.getNcrTitle());
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
     * @param id non conformite request key
     * @return corresponding non conformite request object
     */
    public NonConformiteRequest getNonConformiteRequest(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteRequest> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<NonConformiteRequest> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<NonConformiteRequest> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<NonConformiteRequest> getItemsByCode(String code, Company company) {
        return getFacade().findByCode(code, company);
    }

    public Integer countByProcessus(String processusCode) {
        return getFacade().countByState(processusCode);
    }

    public Integer countByStaff(String staffCode) {
        return getFacade().countByState(staffCode);
    }

    public Integer countByState(String stateCode) {
        return getFacade().countByState(stateCode);
    }

    public List<NonConformiteRequest> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<NonConformiteRequest> getItemsByDesignation(String designation, Company company) {
        return getFacade().findByDesignation(designation, company);
    }

    public List<NonConformiteRequest> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteRequest> getItemsAvailableSelectOne() {
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
     * @return selected non conformite request
     */
    public NonConformiteRequest getSelected() {
        if (selected == null) {
            selected = new NonConformiteRequest();
        }
        return selected;
    }

    public void setSelected(NonConformiteRequest selected) {
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
    
    
    public TableSet getTableSet() {
        return tableSet;
    }

    public void setTableSet(TableSet tableSet) {
        this.tableSet = tableSet;
    }

    /// ////////////////////////////////////////////////////////////////////////
    ///
    /// Contrôle graphique renderer state
    ///
    /// ////////////////////////////////////////////////////////////////////////
    /**
     * *
     *
     * @param from
     * @param to
     * @return
     */
    public Integer getCountItemsCreateInRange(Date from, Date to) {
        List<NonConformiteRequest> l = getFacade().itemsCreateInRange(from, to);
        if (l == null || l.isEmpty()) {
            return 0;
        }
        return l.size();
    }

    public List<NonConformiteRequest> getItemsCreateInRange(Date from, Date to) {
        return getFacade().itemsCreateInRange(from, to);
    }

    public Integer getCountItemsApprouvedInRange(Date from, Date to) {
        List<NonConformiteRequest> l = getFacade().itemsApprouvedInRange(from, to);
        if (l == null || l.isEmpty()) {
            return 0;
        }
        return l.size();
    }

    public List<NonConformiteRequest> getItemsApprouvedInRange(Date from, Date to) {
        return getFacade().itemsApprouvedInRange(from, to);
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param from
     * @param to
     * @return
     */
    public Integer getCountItemsStateFromTo(String state, Date from, Date to) {
        List<NonConformiteRequest> l = getFacade().itemsStateInRange(state, from, to);
        if (l == null || l.isEmpty()) {
            return 0;
        }
        return l.size();
    }

    public List<NonConformiteRequest> getItemsStateFromTo(String state, Date from, Date to) {
        return getFacade().itemsStateInRange(state, from, to);
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param from
     * @param to
     * @return
     */
    public Integer getCountItemsStateChangeInRange(String state, Date from, Date to) {
        List<NonConformiteRequest> l = getFacade().itemsStateInChangeRange(state, from, to);
        if (l == null || l.isEmpty()) {
            return 0;
        }
        return l.size();
    }

    public List<NonConformiteRequest> getItemsStateChangeInRange(String state, Date from, Date to) {
        return getFacade().itemsStateInChangeRange(state, from, to);
    }

    /**
     * *
     *
     * @param from
     * @param to
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> getItemsCreateInRangeByProcessus(Date from, Date to, Processus processus) {
        return getFacade().itemsCreateInRangeByProcessus(from, to, processus);
    }

    public List<NonConformiteRequest> getItemsApprouvedInRangeByProcessus(Date from, Date to, Processus processus) {
        return getFacade().itemsApprouvedInRangeByProcessus(from, to, processus);
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param from
     * @param to
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> getItemsStateFromToByProcessus(String state, Date from, Date to, Processus processus) {
        return getFacade().itemsStateInRangeByProcessus(state, from, to, processus);
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param from
     * @param to
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> getItemsStateChangeInRangeByProcessus(String state, Date from, Date to, Processus processus) {
        return getFacade().itemsStateInChangeRangeByProcessus(state, from, to, processus);
    }

    public Integer countProcessusInState(Processus processus, String state) {
        return getFacade().countProcessusInState(processus, state);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void setMailsenderController(MailsenderController mailsenderController) {
        this.mailsenderController = mailsenderController;
    }
    
    
    
    /// ////////////////////////////////////////////////////////////////////////
    //
    /// LAZY
    ///
    /// ////////////////////////////////////////////////////////////////////////
    public NonConformiteRequestLazyModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(NonConformiteRequestLazyModel lazyModel) {
        this.lazyModel = lazyModel;
    }



}
