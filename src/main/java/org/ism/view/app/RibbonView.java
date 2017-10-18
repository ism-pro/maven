/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.app;

import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.ism.entities.hr.StaffSetup;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.hr.StaffSetupController;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.ism.jsf.util.JsfUtil;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "ribbonView")
@SessionScoped
public class RibbonView implements Serializable {

    private static final long serialVersionUID = 1L;

//    private final String pathMenu = "/faces/company/hr/staff/MainMenu.xhtml";
//    private final String pathAcceuil = "/faces/company/index.xhtml";
    private String ncRequestProcessusFilter = null;

    /**
     * Staff Authentication controller provide current staff informations
     */
    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;

    /**
     * Staff Setup Controller provide all staff setups.<br>
     * This one is used to read back staff information
     */
    @ManagedProperty(value = "#{staffSetupController}")
    private StaffSetupController staffSetupController;

    /**
     * Specify the current active Index
     */
    private Integer activeIndex = 1;
    /**
     * Specify the last active index save. This allow to not update staff setup
     * evry time
     */
    private Integer activeIndexSave = 1;

    /**
     * Remining Time session is used to display remining time before auto
     * disconnect the current staff.
     */
    @RequestScoped
    private Integer reminingTimeSession;
    /**
     * Remining Time session clock correspond to the refresh time while
     * displayed whith poll (p:poll)
     */
    private Integer reminingTimeSessionClock = 30;          // refresh all 30s
    /**
     * Remining time session Wake Up is used to prevent user before this
     * remining time.<br>
     * A dialog is open at this time.
     */
    private Integer reminingTimeSessionWakeUp_s = 60;       // dialogue open 60before

    public RibbonView() {
        this.reminingTimeSession = 0;
    }

    /**
     * Init setup default datas.<br>
     * Staff Setup allow to read last active index tab and set it back to index
     * active and index save
     */
    @PostConstruct
    public void init() {
        StaffSetup staffSetup = staffSetupController.getStaffSetupByStaff(staffAuthController.getStaff());
        activeIndex = staffSetup.getStsmenuIndex();
        activeIndexSave = activeIndex;
        //JsfUtil.out("RibbonView : init ", "Active index : " + activeIndex);
    }

    /**
     * Handle Table Change event allow to manage table event.
     *
     * @param event ribbon table change event
     */
    public void handleTabChange(TabChangeEvent event) {
        Tab activeTab = event.getTab();
        // Use active index which is automatically change in ribbon while active
        // index is changed
        //JsfUtil.out("RibbonView : handleTabChange Before");
        handleTabIndexChange(activeIndex);
        //JsfUtil.out("RibbonView : handleTabChange After");
        
    }

    /**
     * Convenient method to save active index in staff setup while change
     * tab.<br>
     * First this check if index differ from activeIndexSave. if not nothing is
     * done.<br>
     * If index differ from activeIndexSave then save staffsetup before saving
     * the new active index and change active index
     *
     * @param index specify active tab index
     */
    public void handleTabIndexChange(Integer index) {
        //JsfUtil.out("RibbonView : handleTabIndexChange Before == Active index : " + activeIndex + " == Active index save : " + activeIndexSave);
        if (!Objects.equals(index, activeIndexSave)) {
            // Get current staff Setup
            StaffSetup staffSetup = staffSetupController.getStaffSetupByStaff(staffAuthController.getStaff());
            
            // Change active index and update data
            staffSetup.setStsmenuIndex(index);
            staffSetupController.setSelected(staffSetup);
            staffSetupController.update();
            
            // save active and save index
            activeIndex = index;
            activeIndexSave = index;
        }
        //JsfUtil.out("RibbonView : handleTabIndexChange After == Active index : " + activeIndex + " == Active index save : " + activeIndexSave);
    }


    ////////////////////////////////////////////////////////////////////////////
    /// Main Gesture
    ///
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Update remining time session decrement remining time session with
     * reminingTImeSessionClock<br>
     * When remining time session is equal or lower than remining time session
     * wakeup, it shows a window width
     * "PF('dlgReminingTimeSessionWakeUp').show();"
     */
    public void updateReminingTimeSession() {
        reminingTimeSession -= reminingTimeSessionClock;
        if (getReminingTimeSession() <= reminingTimeSessionWakeUp_s) {
            reminingTimeSessionClock = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgReminingTimeSessionWakeUp').show();");
        }
    }

    /**
     * Init default value on starting
     */
    public void resetReminingTimeSession() {
        reminingTimeSession = 0;
        reminingTimeSessionClock = 30;
    }

    /**
     * Allow to retrieved the corresponding component
     *
     * @param id of the component
     * @return the corresponding component of id
     */
    public UIComponent findComponent(final String id) {
        return JsfUtil.findComponent(id);
    }

    public Integer advanceSessionMaxInactiveInterval() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        resetReminingTimeSession();
        return request.getSession().getMaxInactiveInterval();
    }

    public String getCurrentPath() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        String[] lst = request.getPathInfo().split("/");

        String currentPath = request.getContextPath() + request.getServletPath() + request.getPathInfo() + "?faces-redirect=true";
        return currentPath;
    }

    public String reloadCurrentPath() {
        return getCurrentPath();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Getter / Setter
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getNcRequestProcessusFilter() {
        return ncRequestProcessusFilter;
    }

    public void setNcRequestProcessusFilter(String ncRequestProcessusFilter) {
        this.ncRequestProcessusFilter = ncRequestProcessusFilter;
    }

    public Integer getReminingTimeSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getSession().getMaxInactiveInterval() + reminingTimeSession;
    }

    public void setReminingTimeSession(Integer reminingTimeSession) {
        this.reminingTimeSession = reminingTimeSession;
    }

    /**
     * Read remining time session when user is connected
     *
     * @return integer value for remining time session
     */
    public Integer getReminingTimeSessionClock() {
        return reminingTimeSessionClock;
    }

    /**
     * Change remining time sessions
     *
     * @param reminingTimeSessionClock integer value to set ime session
     */
    public void setReminingTimeSessionClock(Integer reminingTimeSessionClock) {
        this.reminingTimeSessionClock = reminingTimeSessionClock;
    }

    public Integer getReminingTimeSessionWakeUp_s() {
        return reminingTimeSessionWakeUp_s;
    }

    public void setReminingTimeSessionWakeUp_s(Integer reminingTimeSessionWakeUp_s) {
        this.reminingTimeSessionWakeUp_s = reminingTimeSessionWakeUp_s;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }

    public void setStaffSetupController(StaffSetupController staffSetupController) {
        this.staffSetupController = staffSetupController;
    }

}
