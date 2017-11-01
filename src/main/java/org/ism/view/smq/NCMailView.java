/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.smq;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.entities.smq.Processus;
import org.ism.jsf.admin.MailaddressController;
import org.ism.jsf.admin.MaillistController;
import org.ism.jsf.smq.ProcessusController;

/**
 * NonConformitesMailConfig class
 *
 * @author r.hendrick
 */
@ManagedBean(name = "ncMailView")
@SessionScoped
public class NCMailView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Mail list contains all defined groupe with event and processus concern
     */
    @ManagedProperty(value = "maillistController")
    private MaillistController maillistController;

    /**
     * Mail Address contains all possible linked to mail list
     */
    @ManagedProperty(value = "mailaddressController")
    private MailaddressController mailaddressController;

    /**
     * Contains list of available processus
     */
    @ManagedProperty(value = "processusController")
    private ProcessusController processusController;

    /**
     * Specifiy the current selected processus
     */
    private Processus selectedProcessus;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    @PostConstruct
    private void init() {

    }

    public Processus getSelectedProcessus() {
        return selectedProcessus;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    public void setSelectedProcessus(Processus selectedProcessus) {
        this.selectedProcessus = selectedProcessus;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // MANAGED INJECTION
    //
    //
    // /////////////////////////////////////////////////////////////////////////
//    public void setCompanyController(CompanyController companyController) {
//        this.companyController = companyController;
//    }
    public void setProcessusController(ProcessusController processusController) {
        this.processusController = processusController;
    }

    public void setMaillistController(MaillistController maillistController) {
        this.maillistController = maillistController;
    }

    public void setMailaddressController(MailaddressController mailaddressController) {
        this.mailaddressController = mailaddressController;
    }

}
