/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import com.sun.faces.component.visit.FullVisitContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.ism.listener.SessionCounterListener;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import javax.inject.Inject;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.layout.LayoutUnit;

/**
 *
 * @author r.hendrick
 */
@SessionScoped
@ManagedBean(name = "viewRibbon")
public class ViewRibbon implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer activeIndex = 1;
    private Integer activeIndexSave = 1;
    private final String pathMenu = "/faces/company/hr/staff/MainMenu.xhtml";
    private final String pathAcceuil = "/faces/company/index.xhtml";
    private String ncRequestProcessusFilter = null;
    @RequestScoped
    private Integer reminingTimeSession = 0;                // initial remining time
    private Integer reminingTimeSessionClock = 30;          // refresh all 30s
    private Integer reminingTimeSessionWakeUp_s = 60;       // dialogue open 60before

    
    @Inject
    private ViewLayout viewLayout;
    
    @PostConstruct
    public void init() {
    }

    public UIComponent findComponent(final String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    found[0].setRendered(!found[0].isRendered());
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });
        return found[0];
    }

    public void onChange(TabChangeEvent event) {
        Tab activeTab = event.getTab();
        FacesContext ctx = FacesContext.getCurrentInstance();
        //System.out.println("Click Tab :" + activeTab.getId() + " on active index : " + getActiveIndex() + "Path Menu :" + pathMenu);
              
        /*
        if (getActiveIndex() == 0) {    // Active le menu
            try {
                activeIndexSave = getActiveIndex(); // save active index
                ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + pathMenu);
                return;
            } catch (IOException ex) {
                Logger.getLogger(ViewRibbon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (activeIndexSave == 0) {  // Quitte le menu 
            try {
                activeIndexSave = getActiveIndex(); // save active index
                ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + pathAcceuil);
                return;
            } catch (IOException ex) {
                Logger.getLogger(ViewRibbon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
        activeIndexSave = getActiveIndex(); // save active index
    }

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

    public Integer getSessionCounter() {
        return SessionCounterListener.getTotalActiveSession();
    }

    public Integer getTotalActiveSessionsAuthenticated() {
        return SessionCounterListener.getTotalActiveSessionsAuthenticated();
    }

    public Integer getReminingTimeSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getSession().getMaxInactiveInterval() + reminingTimeSession;
    }

    public void setReminingTimeSession(Integer reminingTimeSession) {
        this.reminingTimeSession = reminingTimeSession;
    }

    public void updateReminingTimeSession() {
        reminingTimeSession-=reminingTimeSessionClock;
        if (getReminingTimeSession() <= reminingTimeSessionWakeUp_s) {
            reminingTimeSessionClock=1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgReminingTimeSessionWakeUp').show();");
        }
    }

    public void resetReminingTimeSession() {
        reminingTimeSession = 0;
        reminingTimeSessionClock=30;
    }

    public Integer advanceSessionMaxInactiveInterval() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        resetReminingTimeSession();
        return request.getSession().getMaxInactiveInterval();
    }

    public Integer getReminingTimeSessionClock() {
        return reminingTimeSessionClock;
    }

    public void setReminingTimeSessionClock(Integer reminingTimeSessionClock) {
        this.reminingTimeSessionClock = reminingTimeSessionClock;
    }

    public Integer getReminingTimeSessionWakeUp_s() {
        return reminingTimeSessionWakeUp_s;
    }

    public void setReminingTimeSessionWakeUp_s(Integer reminingTimeSessionWakeUp_s) {
        this.reminingTimeSessionWakeUp_s = reminingTimeSessionWakeUp_s;
    }
    
    
    
    public String getCurrentPath(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        String[] lst = request.getPathInfo().split("/");
        
        String currentPath = request.getContextPath() + request.getServletPath() + request.getPathInfo() + "?faces-redirect=true";
        return currentPath;
    }
    
    public String reloadCurrentPath(){
        return getCurrentPath();
    }
}
