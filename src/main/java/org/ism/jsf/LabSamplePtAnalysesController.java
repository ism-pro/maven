package org.ism.jsf;

import org.ism.entities.LabSamplePtAnalyses;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.LabSamplePtAnalysesFacade;

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

@Named("labSamplePtAnalysesController")
@SessionScoped
public class LabSamplePtAnalysesController implements Serializable {

    @EJB
    private org.ism.sessions.LabSamplePtAnalysesFacade ejbFacade;
    private List<LabSamplePtAnalyses> items = null;
    private LabSamplePtAnalyses selected;

    public LabSamplePtAnalysesController() {
    }

    public LabSamplePtAnalyses getSelected() {
        return selected;
    }

    public void setSelected(LabSamplePtAnalyses selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LabSamplePtAnalysesFacade getFacade() {
        return ejbFacade;
    }

    public LabSamplePtAnalyses prepareCreate() {
        selected = new LabSamplePtAnalyses();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/META-INF/LABB").getString("LabSamplePtAnalysesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/META-INF/LABB").getString("LabSamplePtAnalysesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/META-INF/LABB").getString("LabSamplePtAnalysesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LabSamplePtAnalyses> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/LABB").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/LABB").getString("PersistenceErrorOccured"));
            }
        }
    }

    public LabSamplePtAnalyses getLabSamplePtAnalyses(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LabSamplePtAnalyses> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LabSamplePtAnalyses> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LabSamplePtAnalyses.class)
    public static class LabSamplePtAnalysesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LabSamplePtAnalysesController controller = (LabSamplePtAnalysesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "labSamplePtAnalysesController");
            return controller.getLabSamplePtAnalyses(getKey(value));
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
            if (object instanceof LabSamplePtAnalyses) {
                LabSamplePtAnalyses o = (LabSamplePtAnalyses) object;
                return getStringKey(o.getLspaId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LabSamplePtAnalyses.class.getName()});
                return null;
            }
        }

    }

}
