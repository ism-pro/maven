package org.ism.jsf.process.ctrl;

import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.process.ctrl.AnalyseDataFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.lazy.process.ctrl.AnalyseDataLazyModel;
import org.ism.services.TableSet;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "analyseDataController")
@SessionScoped
public class AnalyseDataController implements Serializable {

    @EJB
    private org.ism.sessions.process.ctrl.AnalyseDataFacade ejbFacade;
    private List<AnalyseData> items = null;
    private AnalyseData selected;
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
    private AnalyseDataLazyModel lazyModel;

    /**
     * Injection of analyse allowed controller
     */
    @ManagedProperty(value = "#{analyseAllowedController}")
    AnalyseAllowedController analyseAllowedController;
    /**
     * Injection of analyse type controller
     */
    @ManagedProperty(value = "#{analyseTypeController}")
    AnalyseTypeController analyseTypeController;

    private List<AnalyseAllowed> typesAllowedByPoint;

    public AnalyseDataController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // STRING PARSE
        String src_01 = "AnalyseDataField_adId";
        String src_02 = "AnalyseDataField_adPoint";
        String src_03 = "AnalyseDataField_adType";
        String src_04 = "AnalyseDataField_adValue";
        String src_05 = "AnalyseDataField_adValueT";
        String src_06 = "AnalyseDataField_adSampler";
        String src_07 = "AnalyseDataField_adValidate";
        String src_08 = "AnalyseDataField_adValidator";
        String src_09 = "AnalyseDataField_adenlimitHH";
        String src_10 = "AnalyseDataField_adlimitHH";
        String src_11 = "AnalyseDataField_adenlimitH";
        String src_12 = "AnalyseDataField_adlimitH";
        String src_13 = "AnalyseDataField_adenlimitB";
        String src_14 = "AnalyseDataField_adlimitB";
        String src_15 = "AnalyseDataField_adenlimitBB";
        String src_16 = "AnalyseDataField_adlimitBB";
        String src_17 = "AnalyseDataField_adBatch";
        String src_18 = "AnalyseDataField_adsampleTime";
        String src_19 = "AnalyseDataField_adObservation";
        String src_20 = "AnalyseDataField_adDeleted";
        String src_21 = "AnalyseDataField_adCreated";
        String src_22 = "AnalyseDataField_adChanged";
        String src_23 = "AnalyseDataField_adCompany";

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
        headerTextMap.put(14, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_14));
        headerTextMap.put(15, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_15));
        headerTextMap.put(16, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_16));
        headerTextMap.put(17, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_17));
        headerTextMap.put(18, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_18));
        headerTextMap.put(19, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_19));
        headerTextMap.put(20, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_20));
        headerTextMap.put(21, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_21));
        headerTextMap.put(22, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_22));
        headerTextMap.put(23, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_23));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_11), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_12), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_13), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_14), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_15), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_16), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_17), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_18), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_19), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_20), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_21), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_22), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_23), false);

        // Initialise model
        lazyModel = new AnalyseDataLazyModel(ejbFacade);

    }

    private AnalyseDataFacade getFacade() {
        return ejbFacade;
    }

    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// BUTTONS OPTIONS
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    /**
     * Preparation de la création
     *
     * @return Data analysis
     */
    public AnalyseData prepareCreate() {
        selected = new AnalyseData();
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
                        getString("AnalyseDataReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// TABLE OPTIONS
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    /**
     * Handle column Toggle manage column visibilit by changing state and saving
     * it in the scoe.
     *
     * @param e toogle event
     */
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("AnalyseData : Toggle Column",
                "Column n° " + e.getData() + " is now " + e.getVisibility());
    }

    /**
     * Handle column Reorder save the table ordering
     *
     * @param e event column reorder
     */
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
        JsfUtil.addSuccessMessage("AnalyseData : Reorder Column",
                "Columns : <br>" + columns);

    }

    /**
     * This method is used to react on point selection. It can only be use on a
     * component with specic Id "adPoint".
     */
    public void handlePointSelect() {
        typesAllowedByPoint = analyseAllowedController.getItemsByPoint(selected.getAdPoint());

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

    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// CRUD OPTIONS
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    public void create() {
        // Set time on creation action
        selected.setAdChanged(new Date());
        selected.setAdCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceCreatedDetail")
                + selected.getAdPoint().getApPoint() + " - " + selected.getAdPoint().getApDesignation()
                + " <br > " + selected.getAdType().getAtType() + " - " + selected.getAdType().getAtDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new AnalyseData();

            } else {
//                List<AnalyseData> v_AnalyseData = getFacade().findAll();
//                selected = v_AnalyseData.get(v_AnalyseData.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setAdChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceUpdatedDetail")
                + selected.getAdPoint().getApPoint() + " - " + selected.getAdPoint().getApDesignation()
                + " <br > " + selected.getAdType().getAtType() + " - " + selected.getAdType().getAtDesignation());
    }

    public void destroy() {
        if (selected == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("DeleteNotRecordForSummury"),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("DeleteNotRecordForDetail"));
            return;
        }
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("AnalyseDataPersistenceDeletedDetail")
                + selected.getAdPoint().getApPoint() + " - " + selected.getAdPoint().getApDesignation()
                + " <br > " + selected.getAdType().getAtType() + " - " + selected.getAdType().getAtDesignation());
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

    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// JPA
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    /**
     * Read Analyse Data define from an id
     *
     * @param id of analyse data
     * @return corresponding analyse data of the object
     */
    public AnalyseData getAnalyseData(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AnalyseData> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public AnalyseDataLazyModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(AnalyseDataLazyModel lazyModel) {
        this.lazyModel = lazyModel;
    }

    public TableSet getTableSet() {
        return tableSet;
    }

    public void setTableSet(TableSet tableSet) {
        this.tableSet = tableSet;
    }

    public List<AnalyseData> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<AnalyseData> getItemsByAnalyseData(String _AnalyseData) {
        return getFacade().findByCode(_AnalyseData);
    }

    public List<AnalyseData> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<AnalyseData> getItemsByPointType(AnalysePoint point, AnalyseType type) {
        return getFacade().findByPointType(point, type);
    }

    public List<AnalyseData> getItemsByPointTypeSampleTimeRange(AnalysePoint point, AnalyseType type, Date from, Date to) {
        return getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeD</h3>
     * Surcharge method to have a double collection list that take following
     * parameters. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRange
     *
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @return List of all String value
     */
    public List<Double> getItemsByPointTypeSampleTimeRangeD(AnalysePoint point, AnalyseType type, Date from, Date to) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<Double> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((ad) -> {
            values.add(ad.getAdValue());
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRange
     *
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @return List of all String value
     */
    public HashMap<String, Double> getItemsByPointTypeSampleTimeRangeMap(AnalysePoint point, AnalyseType type, Date from, Date to) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        HashMap<String, Double> values = new HashMap<>();
        // Loop on iterator
        datas.stream().forEach((ad) -> {
            values.put(ad.getAdsampleTime().toString(), ad.getAdValue());
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRangeLimite
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @param limit is one of HH, H, B, BB
     * @return all value in a list
     */
    public List<Double> getItemsByPointTypeSampleTimeRangeLimite(AnalysePoint point, AnalyseType type, Date from, Date to, String limit) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<Double> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((AnalyseData ad) -> {
            switch (limit) {
                case "HH":
                    values.add(ad.getAdlimitHH());
                    break;
                case "H":
                    values.add(ad.getAdlimitH());
                    break;
                case "B":
                    values.add(ad.getAdlimitB());
                    break;
                case "BB":
                    values.add(ad.getAdlimitBB());
                    break;
                default:
                    break;
            }
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRangeLimite
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @param pattern the partern
     * @return list of allow date
     */
    public List<String> getItemsByPointTypeSampleTimeRangeSampleDate(AnalysePoint point, AnalyseType type, Date from, Date to, String pattern) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<String> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((AnalyseData ad) -> {
            Date date = ad.getAdsampleTime();
            values.add(DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault()).format(date));
        });
        return values;
    }

    public List<AnalyseData> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AnalyseData> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<AnalyseAllowed> getTypesAllowedByPoint() {
        if (selected.getAdPoint() == null) {
            return typesAllowedByPoint;
        }
        typesAllowedByPoint = analyseAllowedController.getItemsByPoint(selected.getAdPoint());
        return typesAllowedByPoint;
    }

    public AnalyseData getSelected() {
        if (selected == null) {
            selected = new AnalyseData();
        }
        return selected;
    }

    public void setSelected(AnalyseData selected) {
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

    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// MANAGED INJECTION
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    public void setAnalyseAllowedController(AnalyseAllowedController analyseAllowedController) {
        this.analyseAllowedController = analyseAllowedController;
    }

    public void setAnalyseTypeController(AnalyseTypeController analyseTypeController) {
        this.analyseTypeController = analyseTypeController;
    }



    /**
     * ************************************************************************
     * VALIDATOR
     *
     *
     * ************************************************************************
     */
    @FacesValidator(value = "AnalyseData_AnalyseDataValidator")
    public static class AnalyseData_AnalyseDataValidator implements Validator {

        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "AnalyseDataDuplicationSummary_####";
        public static final String P_DUPLICATION_CODE_DETAIL_ID = "AnalyseDataDuplicationDetail_###";

        @EJB
        private org.ism.sessions.process.ctrl.AnalyseDataFacade ejbFacade;

        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
            String value = o.toString();
            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }
            if (!(uic instanceof InputText)) {
                return;
            }
            InputText input = (InputText) uic;
            List<AnalyseData> lst = ejbFacade.findByCode(value);
            if (lst != null) {
                if (input.getValue() != null) {
                    if (value.matches((String) input.getValue())) {
                        return;
                    }
                }
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_CODE_DETAIL_ID)
                        + value);
                throw new ValidatorException(facesMsg);
            }
        }
    }

    @FacesValidator(value = "AnalyseData_DesignationValidator")
    public static class AnalyseDataDesignationValidator implements Validator {

        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "AnalyseDataDuplicationSummary_#####";
        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "AnalyseDataDuplicationDetail_#####";

        @EJB
        private org.ism.sessions.process.ctrl.AnalyseDataFacade ejbFacade;

        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
            String value = o.toString();
            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }
            if (!(uic instanceof InputText)) {
                return;
            }
            InputText input = (InputText) uic;
            List<AnalyseData> lst = ejbFacade.findByDesignation(value);
            if (lst != null) {
                if (input.getValue() != null) {
                    if (value.matches((String) input.getValue())) {
                        return;
                    }
                }
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                                getString(P_DUPLICATION_DESIGNATION_DETAIL_ID)
                        + value);
                throw new ValidatorException(facesMsg);
            }
        }
    }
}
