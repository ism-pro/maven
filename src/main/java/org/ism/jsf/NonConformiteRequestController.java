package org.ism.jsf;

import java.io.IOException;
import org.ism.entities.NonConformiteRequest;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.NonConformiteRequestFacade;
import org.ism.entities.IsmNcrstate;

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

@ManagedBean(name = "nonConformiteRequestController")
@SessionScoped
public class NonConformiteRequestController implements Serializable {

    @EJB
    private org.ism.sessions.NonConformiteRequestFacade ejbFacade;
    private List<NonConformiteRequest> items = null;
    private NonConformiteRequest selected;
    private NonConformiteRequest edited;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    public NonConformiteRequestController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // Setup initial visibility
        /*
        NonConformiteRequestField_ncrId=N°
        NonConformiteRequestField_ncrStaff=Emetteur
        NonConformiteRequestField_ncrTitle=Intitulé
        NonConformiteRequestField_ncrProcessus=Processus
        NonConformiteRequestField_ncrState=Etat
        NonConformiteRequestField_ncrNature=Nature
        NonConformiteRequestField_ncrGravity=Gravité
        NonConformiteRequestField_ncrFrequency=Fréquence
        NonConformiteRequestField_ncrOccured=Apparition
        NonConformiteRequestField_ncrCreated=Création
        NonConformiteRequestField_ncrChanged=Modif.
        NonConformiteRequestField_ncrEnddingAction=Date résolution
        NonConformiteRequestField_ncrCompany=Société
        
        // # ELEMENT CI-DESSOUS NON AFFICHE DANS LE TABLEAU
        NonConformiteRequestField_ncrDescription=Description
        NonConformiteRequestField_ncrStaffOnValidate=Approbateur
        NonConformiteRequestField_ncrOccuredValidate=Date validation
        NonConformiteRequestField_ncrStaffOnRefuse=Approbateur
        NonConformiteRequestField_ncrOccuredRefuse=Date du refus
        NonConformiteRequestField_ncrStaffOnAction=Responsable
        NonConformiteRequestField_ncrOccuredAction=Date des actions
        NonConformiteRequestField_ncrStatePrevious=Etat Actuel
        NonConformiteRequestField_ncrStateNew=Etat à venir
        NonConformiteRequestField_ncrProduct=Produit
        NonConformiteRequestField_ncrTrace=Traçabilité
        NonConformiteRequestField_ncrQuantity=Quantité
        NonConformiteRequestField_ncrUnite=Unité
        NonConformiteRequestField_ncrDescOnValidate=Description validée
        NonConformiteRequestField_ncrDescOnAction=Action(s)
        NonConformiteRequestField_ncrDescOnRefuse=Avenant(s)
        NonConformiteRequestField_ncrLink=Lien/Image
         */
        headerTextMap = new HashMap<Integer, String>();
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
        headerTextMap.put(10, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCreated"));
        headerTextMap.put(11, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrChanged"));
        headerTextMap.put(12, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrEnddingAction"));
        headerTextMap.put(13, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCompany"));

        visibleColMap = new HashMap<String, Boolean>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrId"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrStaff"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrTitle"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrProcessus"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrState"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrNature"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrGravity"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrFrequency"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrOccured"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCreated"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrChanged"), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrEnddingAction"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("NonConformiteRequestField_ncrCompany"), false);

    }

    protected void setEmbeddableKeys() {
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
     * @param e
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
     * ************************************************************************
     * CRUD OPTIONS
     *
     * ************************************************************************
     */
    public void create() {
        // Set time on creation action
        selected.setNcrChanged(new Date());
        selected.setNcrCreated(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(1);
        state.setIstate("A");
        selected.setNcrState(state);

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteRequestPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("NonConformiteRequestPersistenceCreatedDetail")
                + selected.getNcrTitle());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new NonConformiteRequest();

            } else {
                JsfUtil.out("is not on multicreation");
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
     * @param id
     * @return
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

    public List<NonConformiteRequest> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
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
     * @return
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

    /**
     * ************************************************************************
     * CONVERTER
     *
     *
     * ************************************************************************
     */
    @FacesConverter(forClass = NonConformiteRequest.class)
    public static class NonConformiteRequestControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NonConformiteRequestController controller = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
            return controller.getNonConformiteRequest(getKey(value));
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
            if (object instanceof NonConformiteRequest) {
                NonConformiteRequest o = (NonConformiteRequest) object;
                return getStringKey(o.getNcrId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NonConformiteRequest.class.getName()});
                return null;
            }
        }

    }

    @FacesValidator(value = "NonConformiteRequestCodeValidator")
    public static class NonConformiteRequestCodeValidator implements Validator {

        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "NonConformiteRequestDuplicationField_codeSummary";
        public static final String P_DUPLICATION_CODE_DETAIL_ID = "NonConformiteRequestDuplicationField_codeDetail";

        @EJB
        private org.ism.sessions.NonConformiteRequestFacade ejbFacade;

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
            List<NonConformiteRequest> lst = ejbFacade.findByCode(value);
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

    @FacesValidator(value = "NonConformiteRequestDesignationValidator")
    public static class NonConformiteRequestDesignationValidator implements Validator {

        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "NonConformiteRequestDuplicationField_designationSummary";
        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "NonConformiteRequestDuplicationField_designationDetail";

        @EJB
        private org.ism.sessions.NonConformiteRequestFacade ejbFacade;

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
            List<NonConformiteRequest> lst = ejbFacade.findByDesignation(value);
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
/*
    private Boolean isEditInfos = false;
    private Boolean isEditRefuse = false;
    private Boolean isEditApprouve = false;
    private Boolean isEditAction = false;
    private Boolean isEditCloture = false;

    public NonConformiteRequest prepareCreate() {
        selected = new NonConformiteRequest();
        return selected;
    }

    public void prepareEdit(NonConformiteRequest nc) {
        selected = nc;
    }

    public void prepareCRUD() {
        isEditInfos = false;
        getSelected();          // si non initialisé, il le sera
    }

    public void create() {
        selected.setNcrChanged(new Date());
        selected.setNcrCreated(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(1);
        state.setIstate("A");
        selected.setNcrState(state);
        persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
    }

    public void prepareValidate(NonConformiteRequest nc) {
        selected = nc;
        selected.setNcrdescOnValidate(selected.getNcrDescription());
    }

    public NonConformiteRequest prepareValidate() {
        selected.setNcrdescOnValidate(selected.getNcrDescription());
        return selected;
    }

    public void validate() {
        selected.setNcroccuredValidate(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(3);
        state.setIstate("C");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void cloture(NonConformiteRequest nc) {
        selected = nc;
        IsmNcrstate state = new IsmNcrstate();
        state.setId(6);
        state.setIstate("F");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void cloture() {
        IsmNcrstate state = new IsmNcrstate();
        state.setId(6);
        state.setIstate("F");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void action(NonConformiteRequest nc) {
        if (nc == null) {
            return;
        }
        if (nc.getNcrdescOnAction().isEmpty()) {
            return;
        }
        selected = nc;
        selected.setNcroccuredAction(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(4);
        state.setIstate("D");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    //Main Method for managing action when user 
    public void action() {
        if (selected.getNcrdescOnAction().isEmpty()) {
            return;
        }
        selected.setNcroccuredAction(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(4);
        state.setIstate("D");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void refuse(NonConformiteRequest nc) {
        selected = nc;
        selected.setNcroccuredRefuse(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(7);
        state.setIstate("G");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void refuse() {
        selected.setNcroccuredRefuse(new Date());
        IsmNcrstate state = new IsmNcrstate();
        state.setId(7);
        state.setIstate("G");
        selected.setNcrState(state);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void manageState(NonConformiteRequest nc) {
        if (nc == null) {
            return;
        }
        selected = nc;
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    //Main Method for managing action when user 
    public void manageState() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Date now() {
        return new Date();
    }

    public void destroy(NonConformiteRequest nc) {
        selected = nc;
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NonConformiteRequest> getItems() {
        //if (items == null) {
        items = getFacade().findAll();
        //}
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
            }
        }
    }

    public NonConformiteRequest getNonConformiteRequest(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteRequest> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteRequest> getItemsAvailableSelectOne() {
        return getFacade().findAll();
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

    /**
     * ************************************************************************
     * GETTERS AND SETTERS
     * ***********************************************************************
 */
 /*
    public NonConformiteRequest getSelected() {
        return selected;
    }

    public void setSelected(NonConformiteRequest selected) {
        this.selected = selected;
    }

    public Boolean getIsEditInfos() {
        return isEditInfos;
    }

    public void setIsEditInfos(Boolean isEditInfos) {
        this.isEditInfos = isEditInfos;
    }

    public Boolean getIsEditRefuse() {
        return isEditRefuse;
    }

    public void setIsEditRefuse(Boolean isEditRefuse) {
        this.isEditRefuse = isEditRefuse;
    }

    public Boolean getIsEditApprouve() {
        return isEditApprouve;
    }

    public void setIsEditApprouve(Boolean isEditApprouve) {
        this.isEditApprouve = isEditApprouve;
    }

    public Boolean getIsEditAction() {
        return isEditAction;
    }

    public void setIsEditAction(Boolean isEditAction) {
        this.isEditAction = isEditAction;
    }

    public Boolean getIsEditCloture() {
        return isEditCloture;
    }

    public void setIsEditCloture(Boolean isEditCloture) {
        this.isEditCloture = isEditCloture;
    }

    /**
     * ************************************************************************
     * Evènement double click sur une ligne du tableau permet d'ouvrir l'édition
     * de la non conformité pour visualisation. Une initialisation des
     * paramètres par défaut vérification de selected et des booléenes
     *
     * @throws IOException
     * ***********************************************************************
 *//*
    public void onDBClickRow() throws IOException {
        // CONFIG PAR DEFAUT
        prepareCRUD();

        // PREPARATION DE LA PAGE DE CHARGEMENT
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String dir = ec.getRequestPathInfo();
        String[] dirs = dir.split("/");
        dir = "/";
        for (int i = 0; i < dirs.length - 1; i++) {
            dir += dirs[i] + "/";
        }
        dir += "View.xhtml?faces-redirect=true";
        ec.redirect(ec.getRequestContextPath() + ec.getRequestServletPath() + dir);
    }

    /**
     * ************************************************************************
     * Annule les informtations saisie et remet les informations par défaut.
     *
     ************************************************************************
 *//*
    public void cancelEdit() {
        selected = this.getNonConformiteRequest(selected.getNcrId());
        JsfUtil.addSuccessMessage("Opération Annulée");

    }

    /**
     * ************************************************************************
     * Effectue une mise à jour des données en utilisant une description d'une
     * non conformité nc.
     *
     * ***********************************************************************
 *//*
    public void updateOnEdit() {
        selected.setNcrChanged(this.now());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        JsfUtil.addSuccessMessage("Mise à jour réussie !");
    }

    @FacesConverter(forClass = NonConformiteRequest.class)
    public static class NonConformiteRequestControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NonConformiteRequestController controller = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
            return controller.getNonConformiteRequest(getKey(value));
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
            if (object instanceof NonConformiteRequest) {
                NonConformiteRequest o = (NonConformiteRequest) object;
                return getStringKey(o.getNcrId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NonConformiteRequest.class.getName()});
                return null;
            }
        }

    }

}
 */
