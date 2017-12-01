package org.ism.jsf.admin;

import org.ism.entities.admin.Company;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.admin.CompanyFacade;

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

@ManagedBean(name = "companyController")
@SessionScoped
public class CompanyController implements Serializable {

    @EJB
    private org.ism.sessions.admin.CompanyFacade ejbFacade;
    private List<Company> items = null;
    private Company selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    private List<Company> itemsByLast;  // Specify current items list
    private List<Company> itemsFiltered;// Specify current filtered

    public CompanyController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise

        // Init. list
        itemsByLast = getItemsByLastChanged();

        // STRING PARSE
        String src_01 = "CompanyField_CId";
        String src_02 = "CompanyField_CCompany";
        String src_03 = "CompanyField_CDesignation";
        String src_04 = "CompanyField_CBuilded";
        String src_05 = "CompanyField_CMain";
        String src_06 = "CompanyField_CEntreprise";
        String src_07 = "CompanyField_CActivated";
        String src_08 = "CompanyField_CCreated";
        String src_09 = "CompanyField_CChanged";

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

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), false);

    }

    private CompanyFacade getFacade() {
        return ejbFacade;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// SPECIFIC FONCTION
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Company prepareCreate() {
        selected = new Company();
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
                getString("CompanyReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /////////////////////////////////////////////////////////////////////////////
    /// TABLE OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("Company : Toggle Column",
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
        JsfUtil.addSuccessMessage("Company : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleTableChanges() {
        itemsByLast = getItemsByLastChanged();
    }

    public void handleDestroy() {
        destroy();
        itemsByLast = getItemsByLastChanged();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CRUD OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void create() {
        // Set time on creation action
        selected.setCChanged(new Date());
        selected.setCCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceCreatedDetail")
                + selected.getCCompany() + " <br > " + selected.getCDesignation());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Company();

            } else {
                JsfUtil.out("is not on multicreation");
                List<Company> v_Company = getFacade().findAll();
                selected = v_Company.get(v_Company.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setCChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceUpdatedDetail")
                + selected.getCCompany() + " <br > " + selected.getCDesignation());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("CompanyPersistenceDeletedDetail")
                + selected.getCCompany() + " <br > " + selected.getCDesignation());
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

    ////////////////////////////////////////////////////////////////////////////
    /// JPA
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Company getCompany(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Company> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Company> getItemsByLastChanged() {
        items = getFacade().findAllByFieldDescending("cChanged");
        return items;
    }

    public List<Company> getItemsByCompany(String company) {
        return getFacade().findByCode(company);
    }

    public List<Company> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<Company> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Company> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// GETTER / SETTER
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Company getSelected() {
        if (selected == null) {
            selected = new Company();
        }
        return selected;
    }

    public void setSelected(Company selected) {
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

    public List<Company> getItemsByLast() {
        return itemsByLast;
    }

    public void setItemsByLast(List<Company> itemsByLast) {
        this.itemsByLast = itemsByLast;
    }

    public List<Company> getItemsFiltered() {
        return itemsFiltered;
    }

    public void setItemsFiltered(List<Company> itemsFiltered) {
        this.itemsFiltered = itemsFiltered;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CONVERTER
    ///
    ////////////////////////////////////////////////////////////////////////////
    @FacesConverter(forClass = Company.class, value = "companyConverter")
    public static class CompanyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompanyController controller = (CompanyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "companyController");
            return controller.getCompany(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Company) {
                Company o = (Company) object;
                return getStringKey(o.getCId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Company.class.getName()});
                return null;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    /// VALIDATOR
    ///
    ////////////////////////////////////////////////////////////////////////////
    @FacesValidator(value = "Company_CompanyValidator")
    public static class Company_CompanyValidator implements Validator {

        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "CompanyDuplicationSummary_####";
        public static final String P_DUPLICATION_CODE_DETAIL_ID = "CompanyDuplicationDetail_###";

        @EJB
        private org.ism.sessions.admin.CompanyFacade ejbFacade;

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
            List<Company> lst = ejbFacade.findByCode(value);
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

    @FacesValidator(value = "Company_DesignationValidator")
    public static class CompanyDesignationValidator implements Validator {

        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "CompanyDuplicationSummary_#####";
        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "CompanyDuplicationDetail_#####";

        @EJB
        private org.ism.sessions.admin.CompanyFacade ejbFacade;

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
            List<Company> lst = ejbFacade.findByDesignation(value);
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
