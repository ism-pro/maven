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

}
