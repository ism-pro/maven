package org.ism.jsf;

import org.ism.entities.LabAnalyseType;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.LabAnalyseTypeFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "labAnalyseTypeController")
@SessionScoped
public class LabAnalyseTypeController implements Serializable {

    @EJB
    private org.ism.sessions.LabAnalyseTypeFacade ejbFacade;
    private List<LabAnalyseType> items = null;
    private LabAnalyseType selected;

    public LabAnalyseTypeController() {
    }

    public LabAnalyseType getSelected() {
        return selected;
    }

    public void setSelected(LabAnalyseType selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LabAnalyseTypeFacade getFacade() {
        return ejbFacade;
    }

    public LabAnalyseType prepareCreate() {
        selected = new LabAnalyseType();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseTypeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseTypeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseTypeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LabAnalyseType> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/Lab").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/Lab").getString("PersistenceErrorOccured"));
            }
        }
    }

    public LabAnalyseType getLabAnalyseType(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LabAnalyseType> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LabAnalyseType> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LabAnalyseType.class)
    public static class LabAnalyseTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LabAnalyseTypeController controller = (LabAnalyseTypeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "labAnalyseTypeController");
            return controller.getLabAnalyseType(getKey(value));
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
            if (object instanceof LabAnalyseType) {
                LabAnalyseType o = (LabAnalyseType) object;
                return getStringKey(o.getLatId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LabAnalyseType.class.getName()});
                return null;
            }
        }

    }

}
