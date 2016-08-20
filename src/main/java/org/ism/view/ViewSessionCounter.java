/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.ism.entities.Staff;
import org.ism.listener.SessionCounterListener;

@Named
@ManagedBean(name = "viewSessionCounter")
@SessionScoped
public class ViewSessionCounter {

    private static final long serialVersionUID = 20120925L;

    @PostConstruct
    protected void initialize() {
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    public Integer getSessionCounter() {
        return SessionCounterListener.getTotalActiveSession();
    }

    public Integer getTotalActiveSessionsAuthenticated() {
        return SessionCounterListener.getTotalActiveSessionsAuthenticated();
    }

    public List<Staff> getActiveStaff() {
        return SessionCounterListener.getActiveStaff();
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
}
