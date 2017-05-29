/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.ism.jsf.util.JsfUtil;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewCrud")
@ViewScoped
public class ViewCrud implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean toggleBtn = false;

    public ViewCrud() {

    }

    @PostConstruct
    public void init() {
        toggleBtn = false;
    }

    /**
     * This listener allow to make no action
     */
    public void handleFake() {
    }

    public Boolean getToggleBtn() {
        return toggleBtn;
    }

    public void setToggleBtn(Boolean toggleBtn) {
        this.toggleBtn = toggleBtn;
    }

}
