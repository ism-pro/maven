package org.ism.jsf.hr;

import org.ism.entities.hr.StaffSetup;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.JsfUtil.PersistAction;
import org.ism.sessions.hr.StaffSetupFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import org.ism.domain.Theme;
import org.ism.services.ThemeService;
import org.ism.entities.hr.Staff;

@ManagedBean(name = "staffSetupController")
@SessionScoped
public class StaffSetupController implements Serializable {

    @EJB
    private org.ism.sessions.hr.StaffSetupFacade ejbFacade;
    private List<StaffSetup> items = null;
    private StaffSetup selected;

    private String option;
    private Theme theme;
    private List<Theme> themes;

    @ManagedProperty("#{themeService}")
    private ThemeService service;

    public StaffSetupController() {
    }

    @PostConstruct
    public void init() {
        if (service == null) {
            service = new ThemeService();
        }
        themes = service.getThemes();
    }

    protected void setEmbeddableKeys() {
    }

    private StaffSetupFacade getFacade() {
        return ejbFacade;
    }

    private void initDefaultSelected() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        selected = this.getStaffSetupByStaff(request.getRemoteUser());
    }

    public StaffSetup prepareCreate() {
        selected = new StaffSetup();
        selected.setStsmenuIndex(0);
        selected.setStstimeOutSession(5);
        return selected;
    }

    public StaffSetup prepareUpdate(Integer staffId) {
        selected = new StaffSetup();
        selected = (StaffSetup) getStaffSetup(staffId);
        return selected;
    }

    public StaffSetup prepareCreate(Staff staff) {
        selected = new StaffSetup();
        selected.setStsTheme(JsfUtil.defaultThemeName);
        selected.setStsmenuIndex(0);
        selected.setStstimeOutSession(5);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        /* RibbonView rv = (RibbonView) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ribbonView");
        rv.setActiveIndex(0); */
        if (staff != null) {
            // Check if staff already have some setup
            StaffSetup staffSetup = getStaffSetupByStaff(staff);
            // Depending on what staff has or not has setup
            if (staffSetup != null) { // staff exist so update
                selected = staffSetup;
            }
            // Setup timeout
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession(false).setMaxInactiveInterval(selected.getStstimeOutSession() * 60);
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
        }
        return selected;
    }

    public void create() {
        selected.setStsCreated(new Date());
        selected.setStsChanged(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setStsChanged(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceUpdated"));
    }

    /**
     * This méthod is only used when change from the actual session
     *
     * @param staff the staff to be update
     */
    public void updateStaff(Staff staff) {
        // Vérifie que le staff a été spécifié
        if (staff != null) {
            // Check if staff already have some setup
            StaffSetup staffSetup = getStaffSetupByStaff(staff);

            // Depending on what staff has or not has setup
            if (staffSetup != null) { // staff exist so update
                selected.setStsId(staffSetup.getStsId());
                selected.setStsStaff(staffSetup.getStsStaff());
                update();
                if (JsfUtil.isValidationFailed()) {
                    JsfUtil.addErrorMessage("UPDATE setup Failed !");
                } else {
                    JsfUtil.addSuccessMessage("UPDATE succed !");
                }
            } else { // create
                selected.setStsStaff(staff);
                create();
                if (JsfUtil.isValidationFailed()) {
                    JsfUtil.addErrorMessage("CREATE setup Failed !");
                } else {
                    JsfUtil.addSuccessMessage("CREATE succed !");
                }
            }
            // Setup timeout
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession(false).setMaxInactiveInterval(selected.getStstimeOutSession() * 60);
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<StaffSetup> getItems() {
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

    public StaffSetup getSelected() {
        if (selected == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            selected = this.getStaffSetupByStaff(request.getRemoteUser());
        }
        return selected;
    }

    public void setSelected(StaffSetup selected) {
        this.selected = selected;
    }

    public StaffSetup getStaffSetup(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public StaffSetup getStaffSetupByStaff(Staff staff) {
        return getFacade().findByStaff(staff);
    }

    public StaffSetup getStaffSetupByStaff(String staff) {
        return getFacade().findByStaff(staff);
    }

    public List<StaffSetup> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<StaffSetup> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /* =========================================================================
     * THEME SELECT OPTION
     * 
     * ========================================================================*/
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getThemeName() {
        if (selected == null) {
            return JsfUtil.defaultThemeName;
        }
        if (selected.getStsTheme() == null) {
            return JsfUtil.defaultThemeName;
        }
        if (selected.getStsTheme().isEmpty()) {
            return JsfUtil.defaultThemeName;
        }
        return selected.getStsTheme();
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        getSelected().setStsTheme(theme.getName());
    }

    public List<Theme> getThemes() {
        if (themes == null) {
            themes = service.getThemes();
        }
        return themes;
    }

    public void setService(ThemeService service) {
        this.service = service;
    }

    public void handleThemeChanged(ValueChangeEvent event) {
        Theme newTheme = (Theme) event.getNewValue();
        Theme oldTheme = (Theme) event.getOldValue();

        if (oldTheme == null) {
            oldTheme = new Theme();
            oldTheme.setName(JsfUtil.defaultThemeName);
        }
        // Setup default data
        if (!newTheme.getName().matches(oldTheme.getName())) {
            if (selected == null) {
                initDefaultSelected();
            }
            this.theme = newTheme;
            selected.setStsTheme(newTheme.getName());
            update();
        }
    }

}
