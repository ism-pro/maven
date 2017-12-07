package org.ism.jsf.admin;

import org.ism.entities.admin.Mailsender;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.admin.MailsenderFacade;

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
import org.ism.entities.admin.Company;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.services.admin.Mail;
import org.ism.services.admin.MailFacade;

@ManagedBean(name = "mailsenderController")
@SessionScoped
public class MailsenderController implements Serializable {

    @EJB
    private org.ism.sessions.admin.MailsenderFacade ejbFacade;

    @EJB
    private MailFacade ejbMailFacade;

    @ManagedProperty(value = "#{staffAuthController}")
    StaffAuthController staffAuthController;

    private List<Mailsender> items = null;
    private Mailsender selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    private List<Mailsender> itemsByLast;
    private List<Mailsender> itemsFiltered;
    /**
     * Email Test is used to define receiver of email in order to test
     * configuration
     */
    private String emailTest = "";

    public MailsenderController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise

        // Initialize list of senders
        itemsByLast = getItemsByLastChanged();

        // STRING PARSE
        String src_01 = "MailsenderField_msId";
        String src_02 = "MailsenderField_msAddress";
        String src_03 = "MailsenderField_msSmtpsrv";
        String src_04 = "MailsenderField_msPort";
        String src_05 = "MailsenderField_msSsl";
        String src_06 = "MailsenderField_msrequiresAuth";
        String src_07 = "MailsenderField_msUsername";
        String src_08 = "MailsenderField_msPassword";
        String src_09 = "MailsenderField_amEnabled";
        String src_10 = "MailsenderField_msDeleted";
        String src_11 = "MailsenderField_msCreated";
        String src_12 = "MailsenderField_msChanged";

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
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_11), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_12), false);

        // Selected Default SMTP Mail config
        selected = ejbFacade.findByCompany(staffAuthController.getCompany());
    }

    private MailsenderFacade getFacade() {
        return ejbFacade;
    }

    private MailFacade getMailFacade() {
        return ejbMailFacade;
    }

    public StaffAuthController getStaffAuthController() {
        return staffAuthController;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// SPECIFIC FONCTION
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Mailsender prepareCreate() {
        if (selected == null) {
            selected = new Mailsender();
        }
        if (getItemsByCompany(getStaffAuthController().getCompany()) != null) {
            selected = getItemsByCompany(getStaffAuthController().getCompany());
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
                getString("MailsenderReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Send Test Mail allow to test configuration by sending an email to a
     * specified email address "emailTest"<br>
     * If there is no selected configuration this one stopped.
     */
    public void sendTestMail() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Test mail send : SMTP Server no defined");
            return;
        }
        JsfUtil.out(getClass().getName() + " : sendTestMail >> receiver is " + emailTest);
        Mail mail = new Mail(emailTest,
                "MAIL TEST CONFIG.",
                Mail.msgTest()
        );
        ejbMailFacade.send(selected, mail);
        JsfUtil.addSuccessMessage("L'envoi d'un emssage de test à " + emailTest + " est en cours...");
    }

    /////////////////////////////////////////////////////////////////////////////
    /// TABLE OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("Mailsender : Toggle Column",
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
        JsfUtil.addSuccessMessage("Mailsender : Reorder Column",
                "Columns : <br>" + columns);

    }

    public void handleTableChanges() {
        itemsByLast = getItemsByLastChanged();
    }

    public void handleDestroy(String ctrl) {
        destroy();
        itemsByLast = getItemsByLastChanged();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CRUD OPTIONS
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void create() {
        if (selected.getMsCreated() == null) {
            selected.setMsCreated(new Date());
        } else {
            update();
        }
        // Set time on creation action
        selected.setMsChanged(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceCreatedDetail")
                + selected.getMsAddress() + " <br > " + selected.getMsSmtpsrv());

        /*
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Mailsender();

            } else {
                JsfUtil.out("is not on multicreation");
                List<Mailsender> v_Mailsender = getFacade().findAll();
                selected = v_Mailsender.get(v_Mailsender.size() - 1);
            }
        }
         */
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setMsChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceUpdatedDetail")
                + selected.getMsAddress() + " <br > " + selected.getMsSmtpsrv());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MailsenderPersistenceDeletedDetail")
                + selected.getMsAddress() + " <br > " + selected.getMsSmtpsrv());
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
    public Mailsender getMailsender(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Mailsender> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Mailsender> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<Mailsender> getItemsByMailsender(String _Mailsender) {
        return getFacade().findByCode(_Mailsender);
    }

    public List<Mailsender> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<Mailsender> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Mailsender> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Mailsender getItemsByCompany(Company company) {
        return getFacade().findByCompany(company);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// GETTER / SETTER
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Mailsender getSelected() {
        if (selected == null) {
            selected = new Mailsender();
        }
        return selected;
    }

    public void setSelected(Mailsender selected) {
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

    public List<Mailsender> getItemsByLast() {
        itemsByLast = getItemsByLastChanged();
        return itemsByLast;
    }

    public void setItemsByLast(List<Mailsender> itemsByLast) {
        this.itemsByLast = itemsByLast;
    }

    public List<Mailsender> getItemsFiltered() {
        return itemsFiltered;
    }

    public void setItemsFiltered(List<Mailsender> itemsFiltered) {
        this.itemsFiltered = itemsFiltered;
    }

    public String getEmailTest() {
        return emailTest;
    }

    public void setEmailTest(String emailTest) {
        this.emailTest = emailTest;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }
}
