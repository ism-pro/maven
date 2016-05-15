package org.ism.jsf;

import java.io.IOException;
import org.ism.entities.NonConformiteRequest;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.NonConformiteRequestFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.ism.entities.IsmNcrstate;

@Named("nonConformiteRequestController")
@SessionScoped
public class NonConformiteRequestController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.NonConformiteRequestFacade ejbFacade;
    private List<NonConformiteRequest> items = null;
    private NonConformiteRequest selected;

    private Boolean isEditInfos     = false;
    private Boolean isEditRefuse    = false;
    private Boolean isEditApprouve  = false;
    private Boolean isEditAction    = false;
    private Boolean isEditCloture   = false;

    public NonConformiteRequestController() {
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NonConformiteRequestFacade getFacade() {
        return ejbFacade;
    }

    public NonConformiteRequest prepareCreate() {
        selected = new NonConformiteRequest();
        initializeEmbeddableKey();
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

    /** ************************************************************************
     * GETTERS AND SETTERS
     ************************************************************************ */
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
     */
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
     */
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
     */
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
