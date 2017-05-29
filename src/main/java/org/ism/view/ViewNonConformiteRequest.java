/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.jsf.smq.nc.NonConformiteRequestController;

/**
 *
 * @author r.hendrick
 */
@ViewScoped
@ManagedBean(name = "viewNonConformiteRequest")
public class ViewNonConformiteRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private NonConformiteRequestController ncCtrl;

    public ViewNonConformiteRequest() {
    }

    @PostConstruct
    public void init() {

    }

    private NonConformiteRequestController getNCRequestCtrl() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ncCtrl = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
        return ncCtrl;
    }

    public void doCancel() {
        NonConformiteRequestController ncRequestCtrl = getNCRequestCtrl();
        //ncRequestCtrl.setIsEditInfos(false);
    }
}
