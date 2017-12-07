package org.ism.jsf.app;

import org.ism.entities.app.IsmRole;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.app.IsmRoleFacade;

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

@ManagedBean(name = "ismRoleController")
@SessionScoped
public class IsmRoleController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.app.IsmRoleFacade ejbFacade;
    private List<IsmRole> items = null;
    private IsmRole selected;

    public IsmRoleController() {
    }

    public IsmRole getSelected() {
        return selected;
    }

    public void setSelected(IsmRole selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IsmRoleFacade getFacade() {
        return ejbFacade;
    }

    public IsmRole prepareCreate() {
        selected = new IsmRole();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/META-INF/Bundle").getString("IsmRoleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/META-INF/Bundle").getString("IsmRoleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/META-INF/Bundle").getString("IsmRoleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<IsmRole> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/META-INF/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public IsmRole getIsmRole(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<IsmRole> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<IsmRole> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public IsmRole getItemBy(String role) {
        return getFacade().findByRole(role);
    }

}
