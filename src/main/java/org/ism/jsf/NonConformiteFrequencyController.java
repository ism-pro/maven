package org.ism.jsf;

import org.ism.entities.NonConformiteFrequency;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.NonConformiteFrequencyFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("nonConformiteFrequencyController")
@SessionScoped
public class NonConformiteFrequencyController implements Serializable {

    @EJB
    private org.ism.sessions.NonConformiteFrequencyFacade ejbFacade;
    private List<NonConformiteFrequency> items = null;
    private NonConformiteFrequency selected;

    public NonConformiteFrequencyController() {
    }

    public NonConformiteFrequency getSelected() {
        return selected;
    }

    public void setSelected(NonConformiteFrequency selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NonConformiteFrequencyFacade getFacade() {
        return ejbFacade;
    }

    public NonConformiteFrequency prepareCreate() {
        selected = new NonConformiteFrequency();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setNcfChanged(new Date());
        selected.setNcfCreated(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NonConformiteFrequency> getItems() {
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

    public NonConformiteFrequency getNonConformiteFrequency(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NonConformiteFrequency> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NonConformiteFrequency> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = NonConformiteFrequency.class)
    public static class NonConformiteFrequencyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NonConformiteFrequencyController controller = (NonConformiteFrequencyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nonConformiteFrequencyController");
            return controller.getNonConformiteFrequency(getKey(value));
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
            if (object instanceof NonConformiteFrequency) {
                NonConformiteFrequency o = (NonConformiteFrequency) object;
                return getStringKey(o.getNcfId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NonConformiteFrequency.class.getName()});
                return null;
            }
        }

    }

}
