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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.ism.entities.hr.Staff;
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.ProcessAccess;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.hr.StaffGroupsController;
import org.ism.jsf.smq.DocExplorerController;
import org.ism.jsf.smq.ProcessAccessController;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "docExplorerRightsView")
@SessionScoped
public class DocExplorerRightsView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Injection of ProcessAccessController  <br>
     * This controller allow to manage rights on documents from docExplorer.
     */
    @ManagedProperty(value = "#{processAccessController}")
    private ProcessAccessController processAccessController;

    /**
     * Injection of DocExplorerController  <br>
     * This controller contain all document informations
     */
    @ManagedProperty(value = "#{docExplorerController}")
    private DocExplorerController docExplorerController;

    /**
     * Injection of StaffController   <br>
     * This controller allow to get selected definition
     */
    @ManagedProperty(value = "#{staffController}")
    private StaffController staffController;

    /**
     * Injection of StaffAuthController   <br>
     * This controller allow to get definition of staff authentication
     * controller in order to set default company while required.
     */
    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;

    /**
     * Injection of staffGroupsController This controller link company /
     * selected / stgd_group_def which allow to identify selected affected
     * groups. Staff can have multiple group defined <br>
     * In other word, staff are linked to a group by this controller
     */
    @ManagedProperty(value = "#{staffGroupsController}")
    private StaffGroupsController staffGroupsController;

    /**
     * Current staff use for the controller view
     */
    private ProcessAccess selected = null;

    /**
     * displayMode allow to specify which mode is to be set for display users
     * <br>
     * 1 by document affect staff, <br>
     * 2 by staff affect document,<br>
     * <br>
     * Default is 1.
     *
     */
    private Integer displayMode = 1;

    /**
     * Staff Sources contains all the unaffected staff for a document
     */
    List<Staff> staffSource = new ArrayList<>();
    /**
     * Staff Target contains all the affected staff for a document
     */
    List<Staff> staffTarget = new ArrayList<>();
    /**
     * Document Sources contains all the unaffected staff for a document
     */
    List<DocExplorer> documentSource = new ArrayList<>();
    /**
     * Document Target contains all the affected staff for a document
     */
    List<DocExplorer> documentTarget = new ArrayList<>();

    /**
     * StaffModel is
     */
    private DualListModel<Staff> staffModel;
    /**
     * DocumentModel is
     */
    private DualListModel<DocExplorer> documentModel;
    /**
     * Progress value interac in some long process with bean in order to informe
     * user
     */
    private Integer progressValue = 0;

    /* ========================================================================
   
    =========================================================================*/
    /**
     * Convenient action to do fist call for initialisation of component
     */
    @PostConstruct
    public void init() {
        // Initialize sources
        staffSource = staffController.getItems();
        documentSource = docExplorerController.getItems();

        // Initialise model
        staffModel = new DualListModel<>(staffSource, staffTarget);
        documentModel = new DualListModel<>(documentSource, documentTarget);
    }

    /**
     * handleStaffChanged allow to react over a staff selection while
     * displayMode is 1
     *
     * @param event corresponding event when valuage changed occured
     */
    public void handleStaffChanged(ValueChangeEvent event) {
        Staff staff = (Staff) event.getNewValue();
        getSelected().setPaStaff(staff);
        documentSource = docExplorerController.getItems();
        documentTarget = new ArrayList<>();
        if (staff != null) {
            // Recherche des données affectée et non affectée
            List<ProcessAccess> lstPA = processAccessController.getItemsByStaff(staff);

            if (lstPA != null) {
                for (ProcessAccess pa : lstPA) {
                    documentTarget.add(pa.getPaDocexplorer());
                }
                documentSource.removeAll(documentTarget);
            }
        }
        documentModel = new DualListModel<>(documentSource, documentTarget);

    }

    /**
     * handleDocumentChanged allow to react over a document selection while
     * displayMode is 2
     *
     * @param event corresponding event when valuage changed occured
     */
    public void handleDocumentChanged(ValueChangeEvent event) {
        DocExplorer doc = (DocExplorer) event.getNewValue();
        getSelected().setPaDocexplorer(doc);
        staffSource = staffController.getItems();
        staffTarget = new ArrayList<>();
        if (doc != null) {
            // Recherche des données affectée et non affectée
            List<ProcessAccess> lstPA = processAccessController.getItemsByDocument(doc);

            if (lstPA != null) {
                for (ProcessAccess pa : lstPA) {
                    staffTarget.add(pa.getPaStaff());
                }
                staffSource.removeAll(staffTarget);
            }
        }
        staffModel = new DualListModel<>(staffSource, staffTarget);

    }

    public void handleStaffTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Staff) item).toString()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleStaffSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void handleStaffUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void handleStaffReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public void handleDocumentTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((DocExplorer) item).toString()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleDocumentSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void handleDocumentUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void handleDocumentReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    /* ========================================================================
   
    =========================================================================*/
    /**
     * Create
     */
    public void create() {

    }

    /**
     * update
     */
    public void update() {

    }

    /**
     * Destroy the user if not appearring in the list
     */
    public void destroy() {

    }

    /**
     * updateChange is convenient method which check taget data to know what to
     * preserve from rights. <br>
     * 1. Depending on display mode do next operation,<br>
     * 2. Remove all the existing access rights,<br>
     * 3. Recreate all new defined rights
     */
    public void updateChange() {
        progressValue = 0;
        List<ProcessAccess> lstPA = new ArrayList<>();
        switch (displayMode) {
            case 1: // Case by staff
                Staff staff = selected.getPaStaff();
                lstPA = processAccessController.getItemsByStaff(staff);
                // Remove existing content
                if (lstPA != null) {
                    progressValue = 0;
                    for (ProcessAccess pa : lstPA) {
                        processAccessController.setSelected(pa);
                        processAccessController.destroy();
                        progressValue += (50 / lstPA.size());
                    }
                }

                progressValue = 50;
                // Recreate target
                documentSource = documentModel.getSource();
                documentTarget = documentModel.getTarget();

                if (!documentTarget.isEmpty()) {
                    for (DocExplorer doc : documentTarget) {
                        ProcessAccess pa = new ProcessAccess();
                        pa.setPaCompany(staffAuthController.getCompany());
                        pa.setPaDocexplorer(doc);
                        pa.setPaStaff(staff);
                        pa.setPaIsgroup(Boolean.FALSE);
                        processAccessController.setIsOnMultiCreation(Boolean.TRUE);
                        processAccessController.setSelected(pa);
                        processAccessController.create();
                        progressValue += (50 / documentTarget.size());
                    }
                }
                progressValue = 100;
                break;
            case 2: // Case by document
                DocExplorer docExplorer = selected.getPaDocexplorer();
                lstPA = processAccessController.getItemsByDocument(docExplorer);
                // Remove existing content
                if (lstPA != null) {
                    progressValue = 0;
                    for (ProcessAccess pa : lstPA) {
                        processAccessController.setSelected(pa);
                        processAccessController.destroy();
                        progressValue += (50 / lstPA.size());
                    }
                }

                progressValue = 50;
                // Recreate target
                staffSource = staffModel.getSource();
                staffTarget = staffModel.getTarget();

                if (!staffTarget.isEmpty()) {
                    for (Staff staffItr : staffTarget) {
                        ProcessAccess pa = new ProcessAccess();
                        pa.setPaCompany(staffAuthController.getCompany());
                        pa.setPaDocexplorer(docExplorer);
                        pa.setPaStaff(staffItr);
                        pa.setPaIsgroup(Boolean.FALSE);
                        processAccessController.setIsOnMultiCreation(Boolean.TRUE);
                        processAccessController.setSelected(pa);
                        processAccessController.create();
                        progressValue += (50 / documentTarget.size());
                    }
                }
                progressValue = 100;
                break;
            default:
                break;
        }
    }

    /**
     * Get corresponding Document Explorer from a str which is suppose to have
     * double [ ] in which is contain a number corresponding to document.
     *
     * @param str contain the number of the document
     * @return document explorer
     */
    private DocExplorer getDocExplorerFromStr(String str) {
        String sub = str.split("[")[1].split("]")[0].replace("]", "").trim();
        return docExplorerController.getDocExplorer(Integer.valueOf(sub));
    }

    /* ========================================================================
   
    =========================================================================*/
    /**
     * *
     *
     * @return selected
     */
    public ProcessAccess getSelected() {
        if (selected == null) {
            selected = new ProcessAccess();
        }
        return selected;
    }

    public void setSelected(ProcessAccess processAccess) {
        this.getSelected();
        this.selected = processAccess;
    }

    public Integer getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(Integer displayMode) {
        this.displayMode = displayMode;
    }

    public List<Staff> getStaffSource() {
        return staffSource;
    }

    public void setStaffSource(List<Staff> staffSource) {
        this.staffSource = staffSource;
    }

    public List<Staff> getStaffTarget() {
        return staffTarget;
    }

    public void setStaffTarget(List<Staff> staffTarget) {
        this.staffTarget = staffTarget;
    }

    public List<DocExplorer> getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(List<DocExplorer> documentSource) {
        this.documentSource = documentSource;
    }

    public List<DocExplorer> getDocumentTarget() {
        return documentTarget;
    }

    public void setDocumentTarget(List<DocExplorer> documentTarget) {
        this.documentTarget = documentTarget;
    }

    public DualListModel<Staff> getStaffModel() {
        return staffModel;
    }

    public void setStaffModel(DualListModel<Staff> staffModel) {
        this.staffModel = staffModel;
    }

    public DualListModel<DocExplorer> getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DualListModel<DocExplorer> documentModel) {
        this.documentModel = documentModel;
    }

    public Integer getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(Integer progressValue) {
        this.progressValue = progressValue;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
//    public void setCompanyController(CompanyController companyController) {
//        this.companyController = companyController;
//    }
    public void setProcessAccessController(ProcessAccessController processAccessController) {
        this.processAccessController = processAccessController;
    }

    public void setDocExplorerController(DocExplorerController docExplorerController) {
        this.docExplorerController = docExplorerController;
    }

    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }

    public void setStaffGroupsController(StaffGroupsController staffGroupsController) {
        this.staffGroupsController = staffGroupsController;
    }

}
