package org.ism.jsf;

import org.ism.entities.LabSamplePt;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.LabSamplePtFacade;

import java.io.Serializable;
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

@Named("labSamplePtController")
@SessionScoped
public class LabSamplePtController implements Serializable {

    @EJB
    private org.ism.sessions.LabSamplePtFacade ejbFacade;
    private List<LabSamplePt> items = null;
    private LabSamplePt selected;

    public LabSamplePtController() {
    }

    public LabSamplePt getSelected() {
        return selected;
    }

    public void setSelected(LabSamplePt selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LabSamplePtFacade getFacade() {
        return ejbFacade;
    }

    public LabSamplePt prepareCreate() {
        selected = new LabSamplePt();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabSamplePtCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabSamplePtUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabSamplePtDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LabSamplePt> getItems() {
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

    public LabSamplePt getLabSamplePt(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LabSamplePt> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LabSamplePt> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LabSamplePt.class)
    public static class LabSamplePtControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LabSamplePtController controller = (LabSamplePtController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "labSamplePtController");
            return controller.getLabSamplePt(getKey(value));
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
            if (object instanceof LabSamplePt) {
                LabSamplePt o = (LabSamplePt) object;
                return getStringKey(o.getLspId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LabSamplePt.class.getName()});
                return null;
            }
        }

    }

}
