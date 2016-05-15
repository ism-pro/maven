package org.ism.jsf;

import org.ism.entities.Staff;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.StaffFacade;

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
import javax.servlet.http.HttpServletRequest;
import org.ism.jsf.util.JsfSecurity;

@Named("staffController")
@SessionScoped
public class StaffController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private org.ism.sessions.StaffFacade ejbFacade;
    private List<Staff> items = null;
    private Staff selected;

    public StaffController() {
    }

    public Staff getSelected() {
        return selected;
    }

    public void setSelected(Staff selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private StaffFacade getFacade() {
        return ejbFacade;
    }

    public Staff prepareCreate() {
        selected = new Staff();
        initializeEmbeddableKey();
        return selected;
    }

    public Staff prepareUpdate(String staff) {
        selected = new Staff();
        selected = (Staff) findByStaff(staff).get(0);
        selected.setStPasswordConf(selected.getStPassword());
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        // Controle de mot de passe
        if (selected != null) {
            if (selected.getStPassword().matches(selected.getStPasswordConf())) {
                selected.setStPassword(JsfSecurity.convert(selected.getStPassword(), JsfSecurity.CODING.SHA256));
                selected.setStCreated(new Date());
                selected.setStChanged(new Date());
                persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
                if (!JsfUtil.isValidationFailed()) {
                    items = null;    // Invalidate list of items to trigger re-query.
                    selected = null;
                } else {

                }
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffPersistencePasswordConfError"));
            }
        }
    }

    public void update() {
        // Si le mot de passe n'est pas vide, c'est qu'il a été modifié
        if (!selected.getStPassword().isEmpty()) {
            if (selected.getStPassword().matches(selected.getStPasswordConf())) {   // Vérifie que les deux mot de passe sont identique
                // Si le mot de passe est différent de celui en base de donnée, on l'encrypte
                if (!selected.getStPassword().matches(getPasswordByStaffId(selected.getStId()))) {
                    selected.setStPassword(JsfSecurity.convert(selected.getStPassword(), JsfSecurity.CODING.SHA256));
                    selected.setStPasswordConf(selected.getStPassword());
                    JsfUtil.out(selected.getStPassword());
                }
                selected.setStChanged(new Date());
                persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("StaffPersistencePasswordConfError"));
            }
        } else {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
        }
    }
    
    public void updateMaxInactiveInterval(){
        update();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setMaxInactiveInterval(selected.getStMaxInactiveInterval());
    }

   
    public Integer getMaxInactiveIntervalMinute(){
        return selected.getStMaxInactiveInterval()/60;
    }
    
    public void setMaxInactiveIntervalMinute(Integer min){
        selected.setStMaxInactiveInterval(60*min);
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Staff> getItems() {
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

    public String getPasswordByStaffId(java.lang.Integer id) {
        return getFacade().find(id).getStPassword();
    }

    public void clearCurrentPassword() {
        if (selected != null) {
            selected.setStPassword("");
            selected.setStPasswordConf("");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("ClearMsg_yes"));
        }else{
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("ClearMsg_no"));
        }
    }

    public Staff getStaff(java.lang.Integer id) {
        Staff staff = getFacade().find(id);
        staff.setStPasswordConf(staff.getStPassword());
        selected = staff;
        return staff;
    }

    public List<Staff> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Staff> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Staff> findByStaff(String staff) {
        return getFacade().findByStaff(staff);
    }

    @FacesConverter(forClass = Staff.class)
    public static class StaffControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StaffController controller = (StaffController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "staffController");
            return controller.getStaff(getKey(value));
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
            if (object instanceof Staff) {
                Staff o = (Staff) object;
                return getStringKey(o.getStId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Staff.class.getName()});
                return null;
            }
        }

    }

}
