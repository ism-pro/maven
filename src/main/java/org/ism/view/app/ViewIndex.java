/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.app;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.entities.smq.Processus;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.services.Util;

/**
 * ViewIndex class
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewIndex")
@SessionScoped
public class ViewIndex {

    /**
     * Get Information of the current connected user
     */
    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;

    /**
     * Get Informations about the processus
     */
    @ManagedProperty(value = "#{processusController}")
    private ProcessusController processusController;

    /**
     * Get Information of non conformite
     */
    @ManagedProperty(value = "#{nonConformiteRequestController}")
    private NonConformiteRequestController nonConformiteRequestController;

    @PostConstruct
    public void init() {

    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }

    public void setProcessusController(ProcessusController processusController) {
        this.processusController = processusController;
    }

    public void setNonConformiteRequestController(NonConformiteRequestController nonConformiteRequestController) {
        this.nonConformiteRequestController = nonConformiteRequestController;
    }

    public Integer countStaffNC(String state) {
        return nonConformiteRequestController.countProcessusInState(staffAuthController.getStaff().getStProcessus(), state);
    }
    
    public Integer indicOpenStaffNC(String state){
        Integer divider = (countStaffNC("A")+countStaffNC("B")+countStaffNC("C"));
        if(divider<=0)return 0;
        return (Integer)(100*countStaffNC(state)/divider);
    }
    
    public Integer indicClosedStaffNC(String state){
        Integer divider = (countStaffNC("D")+countStaffNC("E"));
        if(divider<=0)return 0;
        return (Integer)(100*countStaffNC(state)/divider);
    }

}
