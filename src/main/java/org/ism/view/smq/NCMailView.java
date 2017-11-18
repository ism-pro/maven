/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.smq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.ism.entities.hr.Staff;
import org.ism.entities.smq.Processus;
import org.ism.jsf.admin.MailaddressController;
import org.ism.jsf.admin.MaillistController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.selectonemenu.SelectOneMenu;

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
    @ManagedProperty(value = "#{maillistController}")
    private MaillistController maillistController;

    /**
     * Mail Address contains all possible linked to mail list
     */
    @ManagedProperty(value = "#{mailaddressController}")
    private MailaddressController mailaddressController;

    /**
     * Contains list of available processus
     */
    @ManagedProperty(value = "#{processusController}")
    private ProcessusController processusController;
    
    /**
     * Contains list of available staff
     */
    @ManagedProperty(value = "#{staffController}")
    private StaffController staffController;

    /**
     * Specifiy the current selected processus
     */
    private Processus selectedProcessus;

    private Staff selectedStaffSearchTo = null;
    private Staff selectedStaffSearchCc = null;
    private Staff selectedStaffSearchCci = null;
    private Staff selectedStaffSearchReclamTo = null;
    private Staff selectedStaffSearchReclamCc = null;
    private Staff selectedStaffSearchReclamCci = null;

    /**
     * Represent a list of staff
     */
    List<Staff> mailTos = new ArrayList<>();
    List<Staff> mailCcs = new ArrayList<>();
    List<Staff> mailCcis = new ArrayList<>();
    List<Staff> mailReclamTos = new ArrayList<>();
    List<Staff> mailReclamCcs = new ArrayList<>();
    List<Staff> mailReclamCcis = new ArrayList<>();

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

    /**
     * Complete staff allow to help user on completion by providing list of
     * available data
     *
     * @param query starting of seaching staff begining by firstname lastname middlename [staff]
     * @return list of available staff starting with query with a result of maximum 10 staff
     */
    public List<Staff> completeStaff(String query) {
        return staffController.staffStartingWith(query, 10);
    }

    public void addStaffTo() {
        if (selectedStaffSearchTo == null) {
            return;
        }
        if (!mailTos.contains(selectedStaffSearchTo)) {
            mailTos.add(selectedStaffSearchTo);
        } else {
            JsfUtil.addErrorMessage(selectedStaffSearchTo.toString() + " est déjà définit !");
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    public Processus getSelectedProcessus() {
        return selectedProcessus;
    }

    public void setSelectedProcessus(Processus selectedProcessus) {
        this.selectedProcessus = selectedProcessus;
    }

    public Staff getSelectedStaffSearchTo() {
        return selectedStaffSearchTo;
    }

    public void setSelectedStaffSearchTo(Staff selectedStaffSearchTo) {
        this.selectedStaffSearchTo = selectedStaffSearchTo;
    }

    public Staff getSelectedStaffSearchCc() {
        return selectedStaffSearchCc;
    }

    public void setSelectedStaffSearchCc(Staff selectedStaffSearchCc) {
        this.selectedStaffSearchCc = selectedStaffSearchCc;
    }

    public Staff getSelectedStaffSearchCci() {
        return selectedStaffSearchCci;
    }

    public void setSelectedStaffSearchCci(Staff selectedStaffSearchCci) {
        this.selectedStaffSearchCci = selectedStaffSearchCci;
    }

    public Staff getSelectedStaffSearchReclamTo() {
        return selectedStaffSearchReclamTo;
    }

    public void setSelectedStaffSearchReclamTo(Staff selectedStaffSearchReclamTo) {
        this.selectedStaffSearchReclamTo = selectedStaffSearchReclamTo;
    }

    public Staff getSelectedStaffSearchReclamCc() {
        return selectedStaffSearchReclamCc;
    }

    public void setSelectedStaffSearchReclamCc(Staff selectedStaffSearchReclamCc) {
        this.selectedStaffSearchReclamCc = selectedStaffSearchReclamCc;
    }

    public Staff getSelectedStaffSearchReclamCci() {
        return selectedStaffSearchReclamCci;
    }

    public void setSelectedStaffSearchReclamCci(Staff selectedStaffSearchReclamCci) {
        this.selectedStaffSearchReclamCci = selectedStaffSearchReclamCci;
    }

    public List<Staff> getMailTos() {
        return mailTos;
    }

    public void setMailTos(List<Staff> mailTos) {
        this.mailTos = mailTos;
    }

    public List<Staff> getMailCcs() {
        return mailCcs;
    }

    public void setMailCcs(List<Staff> mailCcs) {
        this.mailCcs = mailCcs;
    }

    public List<Staff> getMailCcis() {
        return mailCcis;
    }

    public void setMailCcis(List<Staff> mailCcis) {
        this.mailCcis = mailCcis;
    }

    public List<Staff> getMailReclamTos() {
        return mailReclamTos;
    }

    public void setMailReclamTos(List<Staff> mailReclamTos) {
        this.mailReclamTos = mailReclamTos;
    }

    public List<Staff> getMailReclamCcs() {
        return mailReclamCcs;
    }

    public void setMailReclamCcs(List<Staff> mailReclamCcs) {
        this.mailReclamCcs = mailReclamCcs;
    }

    public List<Staff> getMailReclamCcis() {
        return mailReclamCcis;
    }

    public void setMailReclamCcis(List<Staff> mailReclamCcis) {
        this.mailReclamCcis = mailReclamCcis;
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
    public void setMaillistController(MaillistController maillistController) {
        this.maillistController = maillistController;
    }

    public void setProcessusController(ProcessusController processusController) {
        this.processusController = processusController;
    }

    public void setMailaddressController(MailaddressController mailaddressController) {
        this.mailaddressController = mailaddressController;
    }
    
    public void setStaffController(StaffController staffController){
        this.staffController = staffController;
    }

}
