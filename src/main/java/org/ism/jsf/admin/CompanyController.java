package org.ism.jsf.admin;

import org.ism.entities.admin.Company;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.admin.CompanyFacade;

import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "companyController")
@SessionScoped
public class CompanyController implements Serializable {

    //private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.admin.CompanyFacade ejbFacade;
    private List<Company> items = null;
    private Company selected;

    public CompanyController() {
    }

    public Company getSelected() {
        return selected;
    }

    public void setSelected(Company selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompanyFacade getFacade() {
        return ejbFacade;
    }

    public Company prepareCreate() {
        selected = new Company();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setCChanged(new Date());
        selected.setCCreated(new Date());
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

    public List<Company> getItems() {
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

    public Company getCompany(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Company> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Company> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Company.class)
    public static class CompanyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompanyController controller = (CompanyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "companyController");
            return controller.getCompany(getKey(value));
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
            if (object instanceof Company) {
                Company o = (Company) object;
                return getStringKey(o.getCId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Company.class.getName()});
                return null;
            }
        }

    }

}
