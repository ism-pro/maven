/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.smq;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.domain.mail.MailDefiner;
import org.ism.entities.admin.Maillist;
import org.ism.entities.app.IsmMailtype;
import org.ism.entities.hr.Staff;
import org.ism.entities.smq.Processus;
import org.ism.jsf.admin.MailaddressController;
import org.ism.jsf.admin.MaillistController;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

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
     * Contains current user information
     */
    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;
    
    
    /**
     * Specifiy the current selected processus
     */
    private Processus selectedProcessus;

    /**
     * Represent a list of staff
     */
    MailDefiner mailCreate;
    MailDefiner mailWaiting;
    MailDefiner mailProcessing;
    MailDefiner mailFinished;
    MailDefiner mailCanceled;
    MailDefiner mailReclamCcis;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    @PostConstruct
    private void init() {
        mailCreate = new MailDefiner();
        mailWaiting = new MailDefiner();
        mailProcessing = new MailDefiner();
        mailFinished = new MailDefiner();
        mailCanceled = new MailDefiner();
        mailReclamCcis = new MailDefiner();
    }

    /**
     * Reset content of mail actualy set
     */
    public void prepareMail() {
        mailCreate = new MailDefiner();
        mailWaiting = new MailDefiner();
        mailProcessing = new MailDefiner();
        mailFinished = new MailDefiner();
        mailCanceled = new MailDefiner();
        mailReclamCcis = new MailDefiner();
    }
    
    
    /**
     * Process change while processus is change form one to an other
     * @param event current item select event
     */
    public void handleProcessusChanged(SelectEvent event){
        SelectOneMenu som = (SelectOneMenu) event.getSource();
        
        if(som.getValue()==null){
            JsfUtil.addErrorMessage("Aucun processus définit ! Aucune action...");
            return;
        }
        
        // Initialisaze all mail
        prepareMail();
        
        // Read processus
        Processus processus = (Processus) som.getValue();
        
        
        
    }
    

    /**
     * Complete staff allow to help user on completion by providing list of
     * available data
     *
     * @param query starting of seaching staff begining by firstname lastname
     * middlename [staff]
     * @return list of available staff starting with query with a result of
     * maximum 10 staff
     */
    public List<Staff> completeStaff(String query) {
        return staffController.staffStartingWith(query, 10);
    }

    
    
    /**
     * 
     */
    public void save(){
        // First delete all existing setup of this processus
        maillistController.destroyAllByProcessus(selectedProcessus);
        
        // Create new definition of this processus
        
        // Event
        String onCreated = "A", onWaitingSolution = "B", onProgressed = "C", onFinished = "D", onCanceled = "E";
        
        // Create mail list
        Maillist mlist = new Maillist();
        mlist.setMlCompany(staffAuthController.getCompany());
        mlist.setMlProcessus(selectedProcessus);
        mlist.setMlGroupe("NC");
        
        // Create
        mlist.setMlEvent(onCreated);
        mlist.setMlType(new IsmMailtype(1)); // ism mail type 1: standard / 2 : réclamation
        mlist.setMlTos(mailCreate.tosAsString());
        mlist.setMlCcs(mailCreate.ccsAsString());
        mlist.setMlCcis(mailCreate.ccisAsString());
        maillistController.setSelected(mlist);
        maillistController.create();
        mlist.setMlType(new IsmMailtype(2)); // ism mail type 1: standard / 2 : réclamation
        mlist.setMlTos(mailCreate.tosReclamAsString());
        mlist.setMlCcs(mailCreate.ccsReclamAsString());
        mlist.setMlCcis(mailCreate.ccisReclamAsString());
        maillistController.setSelected(mlist);
        maillistController.create();
        
        // En attente de solution
        mlist.setMlEvent(onWaitingSolution);
        mlist.setMlType(new IsmMailtype(1)); // ism mail type 1: standard / 2 : réclamation
        mlist.setMlTos(mailWaiting.tosAsString());
        mlist.setMlCcs(mailWaiting.ccsAsString());
        mlist.setMlCcis(mailWaiting.ccisAsString());
        maillistController.setSelected(mlist);
        maillistController.create();
        mlist.setMlType(new IsmMailtype(2)); // ism mail type 1: standard / 2 : réclamation
        mlist.setMlTos(mailWaiting.tosReclamAsString());
        mlist.setMlCcs(mailWaiting.ccsReclamAsString());
        mlist.setMlCcis(mailWaiting.ccisReclamAsString());
        maillistController.setSelected(mlist);
        maillistController.create();
        
        
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

    public MailDefiner getMailCreate() {
        return mailCreate;
    }

    public void setMailCreate(MailDefiner mailCreate) {
        this.mailCreate = mailCreate;
    }

    public MailDefiner getMailWaiting() {
        return mailWaiting;
    }

    public void setMailWaiting(MailDefiner mailWaiting) {
        this.mailWaiting = mailWaiting;
    }

    public MailDefiner getMailProcessing() {
        return mailProcessing;
    }

    public void setMailProcessing(MailDefiner mailProcessing) {
        this.mailProcessing = mailProcessing;
    }

    public MailDefiner getMailFinished() {
        return mailFinished;
    }

    public void setMailFinished(MailDefiner mailFinished) {
        this.mailFinished = mailFinished;
    }

    public MailDefiner getMailCanceled() {
        return mailCanceled;
    }

    public void setMailCanceled(MailDefiner mailCanceled) {
        this.mailCanceled = mailCanceled;
    }

    public MailDefiner getMailReclamCcis() {
        return mailReclamCcis;
    }

    public void setMailReclamCcis(MailDefiner mailReclamCcis) {
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

    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }
    
    
    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }

}
