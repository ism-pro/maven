package org.ism.jsf.app;

import org.ism.entities.app.IsmGenre;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.app.IsmGenreFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "ismGenreController")
@SessionScoped
public class IsmGenreController implements Serializable {

    @EJB
    private org.ism.sessions.app.IsmGenreFacade ejbFacade;
    private List<IsmGenre> items = null;
    private IsmGenre selected;

    public IsmGenreController() {
    }

    public IsmGenre getSelected() {
        return selected;
    }

    public void setSelected(IsmGenre selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IsmGenreFacade getFacade() {
        return ejbFacade;
    }

    public IsmGenre prepareCreate() {
        selected = new IsmGenre();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/toDel").getString("IsmGenreCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/toDel").getString("IsmGenreUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/toDel").getString("IsmGenreDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<IsmGenre> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/toDel").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/toDel").getString("PersistenceErrorOccured"));
            }
        }
    }

    public IsmGenre getIsmGenre(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<IsmGenre> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<IsmGenre> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = IsmGenre.class, value = "ismGenreConverter")
    public static class IsmGenreControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IsmGenreController controller = (IsmGenreController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ismGenreController");
            return controller.getIsmGenre(getKey(value));
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
            if (object instanceof IsmGenre) {
                IsmGenre o = (IsmGenre) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IsmGenre.class.getName()});
                return null;
            }
        }

    }

}
