package org.ism.jsf.admin;

import org.ism.entities.admin.Maillist;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.admin.MaillistFacade;

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
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
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

@ManagedBean(name = "maillistController")
@SessionScoped
public class MaillistController implements Serializable {

    @EJB
    private org.ism.sessions.admin.MaillistFacade ejbFacade;
    private List<Maillist> items = null;
    private Maillist selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    private List<Maillist> itemsByLast;  // Specify current items list
    private List<Maillist> itemsFiltered;// Specify current filtered

    /**
     * Multi Sort Meta save table sorting
     */
    private List<SortMeta> multiSortMeta = null;
    /**
     * Filters save table filters
     */
    private Map<String, Object> filters = null;

    /**
     * Define table setups include with controller
     */
    private TableSet tableSet = new TableSet();

    /**
     * Define lazy model to load progressively data
     */
    //private MaillistLazyModel lazyModel;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Constructor
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public MaillistController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise

        // Init. list
        itemsByLast = getItemsByLastChanged();

        // STRING PARSE
        String src_01 = "CtrlShort";
        String src_02 = "CtrlShort";
        String src_03 = "CtrlShort";
        String src_04 = "CtrlShort";
        String src_05 = "CtrlShort";
        String src_06 = "CtrlShort";
        String src_07 = "CtrlShort";
        String src_08 = "CtrlShort";
        String src_09 = "CtrlShort";
        String src_10 = "CtrlShort";
        String src_11 = "CtrlShort";
        String src_12 = "CtrlShort";
        String src_13 = "CtrlShort";
        String src_14 = "CtrlShort";
        String src_15 = "CtrlShort";
        String src_16 = "CtrlShort";
        String src_17 = "CtrlShort";
        String src_18 = "CtrlShort";
        String src_19 = "CtrlShort";
        String src_20 = "CtrlShort";

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

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_11), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_12), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_13), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_14), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_15), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_16), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_17), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_18), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_19), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_20), false);

    }

    private MaillistFacade getFacade() {
        return ejbFacade;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public Maillist prepareCreate() {
        selected = new Maillist();
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
                getString("MaillistReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistToggleMultiCreationDetail") + isOnMultiCreation);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // HANDLE TABLE OPERATIONS
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("Maillist : Toggle Column",
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
        JsfUtil.addSuccessMessage("Maillist : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleTableChanges() {
        itemsByLast = getItemsByLastChanged();
    }

    public void handleDestroy() {
        destroy();
        itemsByLast = getItemsByLastChanged();
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // TABLE CRUD
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public void create() {
        // Set time on creation action
        selected.setMlChanged(new Date());
        selected.setMlCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceCreatedDetail")
                + selected.getMlGroupe() + " <br > " + selected.getMlEvent());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Maillist();

            } else {
                JsfUtil.out("is not on multicreation");
                List<Maillist> v_Maillist = getFacade().findAll();
                selected = v_Maillist.get(v_Maillist.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setMlChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceUpdatedDetail")
                + selected.getMlGroupe() + " <br > " + selected.getMlEvent());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MaillistPersistenceDeletedDetail")
                + selected.getMlGroupe() + " <br > " + selected.getMlEvent());
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

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // JPA CONTAINER
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public Maillist getMaillist(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Maillist> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Maillist> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<Maillist> getItemsByMaillist(String _Maillist) {
        return getFacade().findByCode(_Maillist);
    }

    public List<Maillist> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<Maillist> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Maillist> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public Maillist getSelected() {
        if (selected == null) {
            selected = new Maillist();
        }
        return selected;
    }

    public void setSelected(Maillist selected) {
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

    public List<Maillist> getItemsByLast() {
        return itemsByLast;
    }

    public void setItemsByLast(List<Maillist> itemsByLast) {
        this.itemsByLast = itemsByLast;
    }

    public List<Maillist> getItemsFiltered() {
        return itemsFiltered;
    }

    public void setItemsFiltered(List<Maillist> itemsFiltered) {
        this.itemsFiltered = itemsFiltered;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // CONVERTERS
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
//    @FacesConverter(forClass = Maillist.class)
//    public static class MaillistControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            MaillistController controller = (MaillistController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "maillistController");
//            return controller.getMaillist(getKey(value));
//        }
//
//        java.lang.Integer getKey(String value) {
//            java.lang.Integer key;
//            key = Integer.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Integer value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof Maillist) {
//                Maillist o = (Maillist) object;
//                return getStringKey(o.getMlId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Maillist.class.getName()});
//                return null;
//            }
//        }
//
//    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // VALIDATORS
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
//    @FacesValidator(value = "Maillist_MaillistValidator")
//    public static class Maillist_MaillistValidator implements Validator {
//
//        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "MaillistDuplicationSummary_####";
//        public static final String P_DUPLICATION_CODE_DETAIL_ID = "MaillistDuplicationDetail_###";
//
//        @EJB
//        private org.ism.sessions.admin.MaillistFacade ejbFacade;
//
//        @Override
//        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
//            String value = o.toString();
//            if ((fc == null) || (uic == null)) {
//                throw new NullPointerException();
//            }
//            if (!(uic instanceof InputText)) {
//                return;
//            }
//            InputText input = (InputText) uic;
//            List<Maillist> lst = ejbFacade.findByCode(value);
//            if (lst != null) {
//                if (input.getValue() != null) {
//                    if (value.matches((String) input.getValue())) {
//                        return;
//                    }
//                }
//                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
//                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
//                        getString(P_DUPLICATION_CODE_SUMMARY_ID),
//                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
//                        getString(P_DUPLICATION_CODE_DETAIL_ID)
//                        + value);
//                throw new ValidatorException(facesMsg);
//            }
//        }
//    }

//    @FacesValidator(value = "Maillist_DesignationValidator")
//    public static class MaillistDesignationValidator implements Validator {
//
//        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "MaillistDuplicationSummary_#####";
//        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "MaillistDuplicationDetail_#####";
//
//        @EJB
//        private org.ism.sessions.admin.MaillistFacade ejbFacade;
//
//        @Override
//        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
//            String value = o.toString();
//            if ((fc == null) || (uic == null)) {
//                throw new NullPointerException();
//            }
//            if (!(uic instanceof InputText)) {
//                return;
//            }
//            InputText input = (InputText) uic;
//            List<Maillist> lst = ejbFacade.findByDesignation(value);
//            if (lst != null) {
//                if (input.getValue() != null) {
//                    if (value.matches((String) input.getValue())) {
//                        return;
//                    }
//                }
//                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
//                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
//                        getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
//                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
//                        getString(P_DUPLICATION_DESIGNATION_DETAIL_ID)
//                        + value);
//                throw new ValidatorException(facesMsg);
//            }
//        }
//    }
}
