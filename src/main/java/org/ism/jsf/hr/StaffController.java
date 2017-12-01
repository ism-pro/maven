package org.ism.jsf.hr;

import org.ism.entities.hr.Staff;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.hr.StaffFacade;

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
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import org.ism.jsf.util.JsfSecurity;
import org.primefaces.component.inputtext.InputText;

@ManagedBean(name = "staffController")
@SessionScoped
public class StaffController implements Serializable {

    @EJB
    private org.ism.sessions.hr.StaffFacade ejbFacade;
    private List<Staff> items = null;
    private Staff selected;
    private Staff edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé
    private Boolean isResetPassword;                //!< Spécifie if password need to be reset

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public StaffController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stId"));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stStaff"));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stFirstname"));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stLastname"));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stMiddlename"));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stBorned"));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stGenre"));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stMaillist"));
        headerTextMap.put(9, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stActivated"));
        headerTextMap.put(10, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stDeleted"));
        headerTextMap.put(11, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stCreated"));
        headerTextMap.put(12, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stChanged"));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stStaff"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stFirstname"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stLastname"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stMiddlename"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stBorned"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stGenre"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stMaillist"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stActivated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stDeleted"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffField_stChanged"), false);

    }

    protected void setEmbeddableKeys() {
    }

    private StaffFacade getFacade() {
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
     * @return staff prepared
     */
    public Staff prepareCreate() {
        selected = new Staff();
        isResetPassword = false;
        return selected;
    }

    public Staff prepareEdit() {
        edited = selected;
        isResetPassword = false;
        return edited;
    }

    public Staff prepareUpdate(String staff) {
        selected = new Staff();
        selected = (Staff) findByStaff(staff).get(0);
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
                        getString("StaffReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * ************************************************************************
     * TABLE OPTIONS
     *
     * ************************************************************************
     */
    /**
     *
     * @param e toogle event
     */
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("Staff : Toggle Column",
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
        JsfUtil.addSuccessMessage("Staff : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleClearPassword() {
        if (selected != null) {
            selected.setStPassword("");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("ClearMsg_yes"));
        } else {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("ClearMsg_no"));
        }
    }

    /**
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    public void create() {
        // Set time on creation action
        selected.setStChanged(new Date());
        selected.setStCreated(new Date());
        if (isResetPassword) {
            selected.setStPassword(JsfSecurity.convert(selected.getStPassword(), JsfSecurity.CODING.SHA256));
        }
        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceCreatedDetail")
                + selected.getStStaff() + " <br > " + selected.getStFirstname() + " - " + selected.getStLastname() + " - " + selected.getStMiddlename());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Staff();

            } else {
                List<Staff> processus = getFacade().findAll();
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
        selected.setStChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceUpdatedDetail")
                + selected.getStStaff() + " <br > " + selected.getStFirstname() + " - " + selected.getStLastname() + " - " + selected.getStMiddlename());
        isResetPassword = false;
    }

    /**
     * @deprecated
     */
    public void updatePassword() {
        selected.setStPassword(JsfSecurity.convert(selected.getStPassword(), JsfSecurity.CODING.SHA256));
        update();
    }

    /**
     * updateWidthPassword is the convenient method to use which allow to update
     * the password by encoding in sha256 before saving all the staff setups.
     * This one replace deprecated method update Password
     */
    public void updateWidthPassword() {
        selected.setStPassword(JsfSecurity.convert(selected.getStPassword(), JsfSecurity.CODING.SHA256));
        update();
    }

    public void updateMaxInactiveInterval() {
        update();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setMaxInactiveInterval(selected.getStMaxInactiveInterval());
    }

    public Boolean destroy() {
        Boolean err = persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("StaffPersistenceDeletedDetail")
                + selected.getStStaff() + " <br > " + selected.getStFirstname() + " - " + selected.getStLastname() + " - " + selected.getStMiddlename());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
        }
        return err;
    }

    private Boolean persist(PersistAction persistAction, String summary, String detail) {
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
                return false;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, summary, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
                return false;
            }
        }
        return true;
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
     * @param id staff id
     * @return staff corresonding to this id
     */
    public Staff getStaff(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Staff> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Staff> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<Staff> getItemsByCode(String code) {
        return getFacade().findByCode(code);
    }

    public List<Staff> getItemsByFirstname(String firstname) {
        return getFacade().findByFirstName(firstname);
    }

    public List<Staff> getItemsByLastname(String lastname) {
        return getFacade().findByFirstName(lastname);
    }

    public List<Staff> getItemsByMiddlename(String middlename) {
        return getFacade().findByFirstName(middlename);
    }

    public List<Staff> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Staff> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Staff> findByStaff(String staff) {
        return getFacade().findByStaff(staff);
    }

    /**
     * ************************************************************************
     * GETTER / SETTER
     *
     * ************************************************************************
     */
    /**
     * staff
     *
     * @return selected staff
     */
    public Staff getSelected() {
        if (selected == null) {
            selected = new Staff();
        }
        return selected;
    }

    public void setSelected(Staff selected) {
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

    public Integer getMaxInactiveIntervalMinute() {
        return selected.getStMaxInactiveInterval() / 60;
    }

    public void setMaxInactiveIntervalMinute(Integer min) {
        selected.setStMaxInactiveInterval(60 * min);
    }

    /**
     * Look after a staff define like firstname lastname middlename [staff] and
     * give the list with a limit of "limit" result
     *
     * @param query is the start of the staff name
     * @param limit the maximum result
     * @return a list of corresponding available staff starting by query with
     * max result 'limit'
     */
    public List<Staff> staffStartingWith(String query, int limit) {
        return getFacade().findStaffStartingWith(query, limit);
    }

    public Boolean getIsResetPassword() {
        return isResetPassword;
    }

    public void setIsResetPassword(Boolean isResetPassword) {
        this.isResetPassword = isResetPassword;
    }

    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    /// Converters
    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    /// Validators
    /// ////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////
    @FacesValidator(value = "staffValidator")
    public static class staffValidator implements Validator {


        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {

            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }

            if (o != null) {
                return;
            }

            FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("DeleteNotRecordForSummury"),
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                            getString("DeleteNotRecordForDetail"));
            throw new ValidatorException(facesMsg);
        }
    }
}
