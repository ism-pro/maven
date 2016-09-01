package org.ism.jsf;

import org.ism.entities.LabAnalyseCategory;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.LabAnalyseCategoryFacade;

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

@ManagedBean(name = "labAnalyseCategoryController")
@SessionScoped
public class LabAnalyseCategoryController implements Serializable {

    @EJB
    private org.ism.sessions.LabAnalyseCategoryFacade ejbFacade;
    private List<LabAnalyseCategory> items = null;
    private LabAnalyseCategory selected;

    public LabAnalyseCategoryController() {
    }

    public LabAnalyseCategory getSelected() {
        return selected;
    }

    public void setSelected(LabAnalyseCategory selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LabAnalyseCategoryFacade getFacade() {
        return ejbFacade;
    }

    public LabAnalyseCategory prepareCreate() {
        selected = new LabAnalyseCategory();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseCategoryCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseCategoryUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/META-INF/Lab").getString("LabAnalyseCategoryDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LabAnalyseCategory> getItems() {
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

    public LabAnalyseCategory getLabAnalyseCategory(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LabAnalyseCategory> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LabAnalyseCategory> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LabAnalyseCategory.class)
    public static class LabAnalyseCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LabAnalyseCategoryController controller = (LabAnalyseCategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "labAnalyseCategoryController");
            return controller.getLabAnalyseCategory(getKey(value));
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
            if (object instanceof LabAnalyseCategory) {
                LabAnalyseCategory o = (LabAnalyseCategory) object;
                return getStringKey(o.getLacId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LabAnalyseCategory.class.getName()});
                return null;
            }
        }

    }

}
