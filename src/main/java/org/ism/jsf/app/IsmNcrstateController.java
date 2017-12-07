package org.ism.jsf.app;

import org.ism.entities.app.IsmNcrstate;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.app.IsmNcrstateFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "ismNcrstateController")
@SessionScoped
public class IsmNcrstateController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.app.IsmNcrstateFacade ejbFacade;
    private List<IsmNcrstate> items = null;
    private IsmNcrstate selected;

    public IsmNcrstateController() {
    }

    public IsmNcrstate getSelected() {
        return selected;
    }

    public void setSelected(IsmNcrstate selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IsmNcrstateFacade getFacade() {
        return ejbFacade;
    }

    public IsmNcrstate prepareCreate() {
        selected = new IsmNcrstate();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
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

    public List<IsmNcrstate> getItems() {
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

    public IsmNcrstate getIsmNcrstate(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<IsmNcrstate> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<IsmNcrstate> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }


}
