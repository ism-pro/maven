package org.ism.jsf;

import org.ism.entities.StaffGroups;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.StaffGroupsFacade;

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
import org.ism.entities.Company;
import org.ism.entities.Staff;
import org.ism.entities.StaffGroupDef;


@Named("staffGroupsController")
@SessionScoped
public class StaffGroupsController implements Serializable {
    private static final long serialVersionUID = 1L;


    @EJB private org.ism.sessions.StaffGroupsFacade ejbFacade;
    private List<StaffGroups> items = null;
    private StaffGroups selected;

    public StaffGroupsController() {
    }

    public StaffGroups getSelected() {
        return selected;
    }

    public void setSelected(StaffGroups selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private StaffGroupsFacade getFacade() {
        return ejbFacade;
    }

    public StaffGroups prepareCreate() {
        selected = new StaffGroups();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setStgCreated(new Date());
        selected.setStgChanged(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setStgChanged(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<StaffGroups> getItems() {
        //if (items == null) {
            items = getFacade().findAll();
        //}else if(items.size()==1){
        //    items = getFacade().findAll();
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

    public StaffGroups getStaffGroups(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<StaffGroups> getItems(Staff staff) {
        items = getFacade().findByStaff(staff);
        return items;
    }
    
    public List<StaffGroups> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<StaffGroups> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    StaffGroups getStaffGroups(Staff staff, Company company, StaffGroupDef staffGroupDef) {
        return ejbFacade.findByStaffAndCompanyAndGroupDef(staff, company, staffGroupDef);
    }

    @FacesConverter(forClass=StaffGroups.class)
    public static class StaffGroupsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StaffGroupsController controller = (StaffGroupsController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffGroupsController");
            return controller.getStaffGroups(getKey(value));
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
            if (object instanceof StaffGroups) {
                StaffGroups o = (StaffGroups) object;
                return getStringKey(o.getStgId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), StaffGroups.class.getName()});
                return null;
            }
        }

    }

}
