<#if comment>

TEMPLATE DESCRIPTION:

This is Java template for 'JSF Pages From Entity Beans' controller class. Templating
is performed using FreeMaker (http://freemarker.org/) - see its documentation
for full syntax. Variables available for templating are:

controllerClassName - controller class name (type: String)
controllerPackageName - controller package name (type: String)
entityClassName - entity class name without package (type: String)
importEntityFullClassName - whether to import entityFullClassName or not
entityFullClassName - fully qualified entity class name (type: String)
ejbClassName - EJB class name (type: String)
importEjbFullClassName - whether to import ejbFullClassName or not
ejbFullClassName - fully qualified EJB class name (type: String)
managedBeanName - name of managed bean (type: String)
keyEmbedded - is entity primary key is an embeddable class (type: Boolean)
keyType - fully qualified class name of entity primary key
keyBody - body of Controller.Converter.getKey() method
keyStringBody - body of Controller.Converter.getStringKey() method
keyGetter - entity getter method returning primaty key instance
keySetter - entity setter method to set primary key instance
embeddedIdFields - contains information about embedded primary IDs
cdiEnabled - project contains beans.xml, so Named beans can be used
bundle - name of the variable defined in the JSF config file for the resource bundle (type: String)

This template is accessible via top level menu Tools->Templates and can
be found in category JavaServer Faces->JSF from Entity.

</#if>
package ${controllerPackageName};

<#if importEntityFullClassName?? && importEntityFullClassName == true>
import ${entityFullClassName};
</#if>
import ${controllerPackageName}.util.JsfUtil;
import ${controllerPackageName}.util.JsfUtil.PersistAction;
<#if importEjbFullClassName?? && importEjbFullClassName == true>
    <#if ejbClassName??>
import ${ejbFullClassName};
    <#elseif jpaControllerClassName??>
import ${jpaControllerFullClassName};
    </#if>
</#if>

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



@ManagedBean(name="${managedBeanName}")
@SessionScoped
public class ${controllerClassName} implements Serializable {

@EJB private ${ejbFullClassName} ejbFacade;
private List<${entityClassName}> items = null;
private ${entityClassName} selected;
private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

        public ${controllerClassName}() {
        }

        @PostConstruct
        protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // STRING PARSE
        String src_01 = "src_xx";
        String src_02 = "src_xx";
        String src_03 = "src_xx";
        String src_04 = "src_xx";
        String src_05 = "src_xx";
        String src_06 = "src_xx";
        String src_07 = "src_xx";
        String src_08 = "src_xx";
        String src_09 = "src_xx";
        String src_10 = "src_xx";
        String src_11 = "src_xx";
        String src_12 = "src_xx";
        String src_13 = "src_xx";
        String src_14 = "src_xx";
        String src_15 = "src_xx";
        String src_16 = "src_xx";
        String src_17 = "src_xx";
        String src_18 = "src_xx";
        String src_19 = "src_xx";
        String src_20 = "src_xx";


        // Setup initial visibility
        headerTextMap = new HashMap<Integer, String>();
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

            visibleColMap = new HashMap<String, Boolean>();
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

<#if ejbClassName??>
                private ${ejbClassName} getFacade() {
                return ejbFacade;
                }
<#elseif jpaControllerClassName??>
                private ${jpaControllerClassName} getJpaController() {
                if (jpaController == null) {
<#if isInjected?? && isInjected==true>
                jpaController = new ${jpaControllerClassName}(utx, emf);
<#else>
                jpaController = new ${jpaControllerClassName}(Persistence.createEntityManagerFactory(<#if persistenceUnitName??>"${persistenceUnitName}"</#if>));
</#if>
                }
                return jpaController;
                }
</#if>

                /**
                * ************************************************************************
                * CRUD OPTIONS
                *
                * ************************************************************************
                */
                public ${entityClassName} prepareCreate() {
                selected = new ${entityClassName}();
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
                getString("${entityClassName}ReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}ReleaseSelectedDetail"));
                }

                /**
                * Allow to toggle from on creation mode to multicreation mode
                */
                public void toggleMultiCreation() {
                isOnMultiCreation = !isOnMultiCreation;
                JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}ToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}ToggleMultiCreationDetail") + isOnMultiCreation);
                }

                /**
                * Facke togle event : this is useful to use with
                */
                public void toggleMultiCreationFake() {
                /*isOnMultiCreation = !isOnMultiCreation;*/
                JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}ToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}ToggleMultiCreationDetail") + isOnMultiCreation);
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

                JsfUtil.addSuccessMessage("${entityClassName} : Toggle Column",
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
                JsfUtil.addSuccessMessage("${entityClassName} : Reorder Column",
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
                selected.set${entityClassName?substring(0,3)}Changed(new Date());
                selected.set${entityClassName?substring(0,3)}Created(new Date());

                persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceCreatedDetail")
                + selected.get${entityClassName?substring(0,3)}${entityClassName}() + " <br > " + selected.get${entityClassName?substring(0,3)}Designation());

                if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
                if (isReleaseSelected) {
                selected = null;
                }
                if (isOnMultiCreation) {
                selected = new ${entityClassName}();

                } else {
                JsfUtil.out("is not on multicreation");
                List<${entityClassName}> v_${entityClassName} = getFacade().findAll();
                selected = v_${entityClassName}.get(v_${entityClassName}.size() - 1);
                }
                }
                }



                public void createUnReleasded() {
                isReleaseSelected = false;
                create();
                }

                public void update() {
                // Set time on creation action
                selected.set${entityClassName?substring(0,3)}Changed(new Date());

                persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceUpdatedDetail")
                + selected.get${entityClassName?substring(0,3)}${entityClassName}() + " <br > " + selected.get${entityClassName?substring(0,3)}Designation());
                }

                public void destroy() {
                persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("${entityClassName}PersistenceDeletedDetail")
                + selected.get${entityClassName?substring(0,3)}${entityClassName}() + " <br > " + selected.get${entityClassName?substring(0,3)}Designation());
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
                * @param id
                * @return
                */
                public ${entityClassName} get${entityClassName}(java.lang.Integer id) {
                return getFacade().find(id);
                }

                public List<${entityClassName}> getItems() {
                items = getFacade().findAll();
                return items;
                }

                public List<${entityClassName}> getItemsByLastChanged() {
                items = getFacade().findAllByLastChanged();
                return items;
                }

                public List<${entityClassName}> getItemsBy${entityClassName}(String _${entityClassName}) {
                return getFacade().findByCode(_${entityClassName});
                }

                public List<${entityClassName}> getItemsByDesignation(String designation) {
                return getFacade().findByDesignation(designation);
                }

                public List<${entityClassName}> getItemsAvailableSelectMany() {
                return getFacade().findAll();
                }

                public List<${entityClassName}> getItemsAvailableSelectOne() {
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
                public ${entityClassName} getSelected() {
                if (selected == null) {
                selected = new ${entityClassName}();
                }
                return selected;
                }

                public void setSelected(${entityClassName} selected) {
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
                        @FacesConverter(forClass=${entityClassName}.class)
                        public static class ${controllerClassName}Converter implements Converter {
<#if keyEmbedded>

                        private static final String SEPARATOR = "#";
                        private static final String SEPARATOR_ESCAPED = "\\#";
</#if>

                        @Override
                        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
                        if (value == null || value.length() == 0) {
                        return null;
                        }
            ${controllerClassName} controller = (${controllerClassName})facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "${managedBeanName}");
<#if ejbClassName??>
<#if cdiEnabled?? && cdiEnabled>
                        return controller.get${entityClassName}(getKey(value));
<#else>
                        return controller.getFacade().find(getKey(value));
</#if>
<#elseif jpaControllerClassName??>
                        return controller.getJpaController().find${entityClassName}(getKey(value));
</#if>
                        }

        ${keyType} getKey(String value) {
            ${keyType} key;
${keyBody}
                        return key;
                        }

                        String getStringKey(${keyType} value) {
                        StringBuilder sb = new StringBuilder();
${keyStringBody}
                        return sb.toString();
                        }

                        @Override
                        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
                        if (object == null) {
                        return null;
                        }
                        if (object instanceof ${entityClassName}) {
                ${entityClassName} o = (${entityClassName}) object;
                        return getStringKey(o.${keyGetter}());
                        } else {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ${entityClassName}.class.getName()});
                        return null;
                        }
                        }

                        }




                        /**
                        * ************************************************************************
                        * VALIDATOR
                        *
                        *
                        * ************************************************************************
                        */
                        @FacesValidator(value = "${entityClassName}_${entityClassName}Validator")
                        public static class ${entityClassName}_${entityClassName}Validator implements Validator {

                        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "${entityClassName}DuplicationSummary_####";
                        public static final String P_DUPLICATION_CODE_DETAIL_ID = "${entityClassName}DuplicationDetail_###";

                        @EJB
                        private org.ism.sessions.${entityClassName}Facade ejbFacade;

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
                        List<${entityClassName}> lst = ejbFacade.findByCode(value);
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

                        @FacesValidator(value = "${entityClassName}_DesignationValidator")
                        public static class ${entityClassName}DesignationValidator implements Validator {

                        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "${entityClassName}DuplicationSummary_#####";
                        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "${entityClassName}DuplicationDetail_#####";

                        @EJB
                        private org.ism.sessions.${entityClassName}Facade ejbFacade;

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
                        List<${entityClassName}> lst = ejbFacade.findByDesignation(value);
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
