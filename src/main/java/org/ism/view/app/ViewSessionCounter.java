/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.app;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.entities.hr.Staff;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.listener.SessionCounterListener;


@ManagedBean(name = "viewSessionCounter")
@SessionScoped
public class ViewSessionCounter {

    private static final long serialVersionUID = 20120925L;
    
    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;

    public Integer getSessionCounter() {
        return SessionCounterListener.getTotalActiveSession();
    }

    public Integer getTotalActiveSessionsAuthenticated() {
        return SessionCounterListener.getTotalActiveSessionsAuthenticated();
    }

    public List<Staff> getActiveStaff() {
        return SessionCounterListener.getActiveStaff();
    }
    
    public long currentSessionDuration(){
        Staff staff = staffAuthController.getStaff();
        return SessionCounterListener.sessionDuration(staff);
    }
    
    public long staffSessionDuration(Staff staff){
        return SessionCounterListener.sessionDuration(staff);
    }
    
    public Boolean removeSessionActive(Staff staff){
        return SessionCounterListener.removeActiveStaff(staff);
    }
    
    
    /// ////////////////////////////////////////////////////////////////////////
    ///
    ///
    /// GETTER / SETTER
    ///
    ///
    /// ////////////////////////////////////////////////////////////////////////
    public void setStaffAuthController(StaffAuthController staffAuthController){
        this.staffAuthController = staffAuthController;
    }

}
