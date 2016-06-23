/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewUtil")
@ViewScoped
public class ViewUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date maintenant;
    private Date aujourdhui;
    
    
    
    /*
    @ManagedProperty("#{processusController}")
    private ProcessusController     processusCtrl;
     */
    /**
     * Creates a new instance of FilterNCRequestView
     */
    public ViewUtil() {
    }

    /**
     * Init. processus controller
     */
    @PostConstruct
    public void init() {
        maintenant = new Date();
        aujourdhui = new Date();
        aujourdhui.setHours(23);
        aujourdhui.setMinutes(59);
        aujourdhui.setSeconds(59);
    }

    public Date getMaintenant() {
        return maintenant;
    }

    public void setMaintenant(Date maintenant) {
        this.maintenant = maintenant;
    }

    public Date getAujourdhui() {
        return aujourdhui;
    }

    public void setAujourdhui(Date aujourdhui) {
        this.aujourdhui = aujourdhui;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    
}
