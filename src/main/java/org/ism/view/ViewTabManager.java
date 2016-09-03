/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.entities.DocExplorer;
import org.ism.entities.DocType;
import org.ism.entities.NonConformiteFrequency;
import org.ism.entities.NonConformiteGravity;
import org.ism.entities.NonConformiteNature;
import org.ism.entities.NonConformiteRequest;
import org.ism.entities.NonConformiteUnite;
import org.ism.entities.Processus;
import org.ism.entities.StaffGroupDef;
import org.ism.entities.StaffGroupDefRole;
import org.ism.entities.StaffGroups;
import org.ism.jsf.DocExplorerController;
import org.ism.jsf.DocTypeController;
import org.ism.jsf.NonConformiteFrequencyController;
import org.ism.jsf.NonConformiteGravityController;
import org.ism.jsf.NonConformiteNatureController;
import org.ism.jsf.NonConformiteRequestController;
import org.ism.jsf.NonConformiteUniteController;
import org.ism.jsf.ProcessusController;
import org.ism.jsf.StaffGroupDefController;
import org.ism.jsf.StaffGroupDefRoleController;
import org.ism.jsf.StaffGroupsController;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewTabManager")
@ViewScoped
public class ViewTabManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProcessusController processusCtrl;
    private List<Processus> processus;
    private List<Processus> processusFiltered;

    private DocExplorerController docExplorerCtrl;
    private List<DocExplorer> docExplorer;
    private List<DocExplorer> docExplorerFiltered;

    private DocTypeController docTypeCtrl;
    private List<DocType> docType;
    private List<DocType> docTypeFiltered;

    private NonConformiteFrequencyController ncFrequencyCtrl;
    private List<NonConformiteFrequency> ncFrequency;
    private List<NonConformiteFrequency> ncFrequencyFiltered;

    private NonConformiteGravityController ncGravityCtrl;
    private List<NonConformiteGravity> ncGravity;
    private List<NonConformiteGravity> ncGravityFiltered;

    private NonConformiteNatureController ncNatureCtrl;
    private List<NonConformiteNature> ncNature;
    private List<NonConformiteNature> ncNatureFiltered;

    private NonConformiteRequestController ncRequestCtrl;
    private List<NonConformiteRequest> ncRequest;
    private List<NonConformiteRequest> ncRequestFiltered;

    private NonConformiteUniteController ncUniteCtrl;
    private List<NonConformiteUnite> ncUnite;
    private List<NonConformiteUnite> ncUniteFiltered;

    private StaffGroupDefController staffGroupDefCtrl;
    private List<StaffGroupDef> staffGroupDef;
    private List<StaffGroupDef> staffGroupDefFiltered;

    private StaffGroupDefRoleController staffGroupDefRoleCtrl;
    private List<StaffGroupDefRole> staffGroupDefRole;
    private List<StaffGroupDefRole> staffGroupDefRoleFiltered;
    
    private StaffGroupsController staffGroupsCtrl;
    private List<StaffGroups> staffGroups;
    private List<StaffGroups> staffGroupsFiltered;

    /**
     * Creates a new instance of FilterNCRequestView
     */
    public ViewTabManager() {
    }

    /**
     * Init. processus controller
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Setup staff controller
        processusCtrl = (ProcessusController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "processusController");
        processus = processusCtrl.getItemsByLastChanged();

        docExplorerCtrl = (DocExplorerController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docExplorerController");
        docExplorer = docExplorerCtrl.getItemsByLastChanged();

        docTypeCtrl = (DocTypeController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docTypeController");
        docType = docTypeCtrl.getItemsByLastChanged();

        ncFrequencyCtrl = (NonConformiteFrequencyController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteFrequencyController");
        ncFrequency = ncFrequencyCtrl.getItemsByLastChanged();

        ncGravityCtrl = (NonConformiteGravityController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteGravityController");
        ncGravity = ncGravityCtrl.getItemsByLastChanged();

        ncNatureCtrl = (NonConformiteNatureController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteNatureController");
        ncNature = ncNatureCtrl.getItemsByLastChanged();

        ncRequestCtrl = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
        ncRequest = ncRequestCtrl.getItemsByLastChanged();

        ncUniteCtrl = (NonConformiteUniteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteUniteController");
        ncUnite = ncUniteCtrl.getItemsByLastChanged();

        staffGroupDefCtrl = (StaffGroupDefController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupDefController");
        staffGroupDef = staffGroupDefCtrl.getItemsByLastChanged();

        staffGroupDefRoleCtrl = (StaffGroupDefRoleController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupDefRoleController");
        staffGroupDefRole = staffGroupDefRoleCtrl.getItemsByLastChanged();
        
        staffGroupsCtrl = (StaffGroupsController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffGroupsController");
        staffGroups = staffGroupsCtrl.getItemsByLastChanged();
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<Processus> getProcessus() {
        return processus;
    }

    public void setProcessus(List<Processus> processus) {
        this.processus = processus;
    }

    public List<Processus> getProcessusFiltered() {
        return processusFiltered;
    }

    public void setProcessusFiltered(List<Processus> processusFiltered) {
        this.processusFiltered = processusFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<DocExplorer> getDocExplorer() {
        return docExplorer;
    }

    public void setDocExplorer(List<DocExplorer> docExplorer) {
        this.docExplorer = docExplorer;
    }

    public List<DocExplorer> getDocExplorerFiltered() {
        return docExplorerFiltered;
    }

    public void setDocExplorerFiltered(List<DocExplorer> docExplorerFiltered) {
        this.docExplorerFiltered = docExplorerFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<DocType> getDocType() {
        return docType;
    }

    public void setDocType(List<DocType> docType) {
        this.docType = docType;
    }

    public List<DocType> getDocTypeFiltered() {
        return docTypeFiltered;
    }

    public void setDocTypeFiltered(List<DocType> docTypeFiltered) {
        this.docTypeFiltered = docTypeFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<NonConformiteFrequency> getNcFrequency() {
        return ncFrequency;
    }

    public void setNcFrequency(List<NonConformiteFrequency> ncFrequency) {
        this.ncFrequency = ncFrequency;
    }

    public List<NonConformiteFrequency> getNcFrequencyFiltered() {
        return ncFrequencyFiltered;
    }

    public void setNcFrequencyFiltered(List<NonConformiteFrequency> ncFrequencyFiltered) {
        this.ncFrequencyFiltered = ncFrequencyFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<NonConformiteGravity> getNcGravity() {
        return ncGravity;
    }

    public void setNcGravity(List<NonConformiteGravity> ncGravity) {
        this.ncGravity = ncGravity;
    }

    public List<NonConformiteGravity> getNcGravityFiltered() {
        return ncGravityFiltered;
    }

    public void setNcGravityFiltered(List<NonConformiteGravity> ncGravityFiltered) {
        this.ncGravityFiltered = ncGravityFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<NonConformiteNature> getNcNature() {
        return ncNature;
    }

    public void setNcNature(List<NonConformiteNature> ncNature) {
        this.ncNature = ncNature;
    }

    public List<NonConformiteNature> getNcNatureFiltered() {
        return ncNatureFiltered;
    }

    public void setNcNatureFiltered(List<NonConformiteNature> ncNatureFiltered) {
        this.ncNatureFiltered = ncNatureFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<NonConformiteRequest> getNcRequest() {
        return ncRequest;
    }

    public void setNcRequest(List<NonConformiteRequest> ncRequest) {
        this.ncRequest = ncRequest;
    }

    public List<NonConformiteRequest> getNcRequestFiltered() {
        return ncRequestFiltered;
    }

    public void setNcRequestFiltered(List<NonConformiteRequest> ncRequestFiltered) {
        this.ncRequestFiltered = ncRequestFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<NonConformiteUnite> getNcUnite() {
        return ncUnite;
    }

    public void setNcUnite(List<NonConformiteUnite> ncUnite) {
        this.ncUnite = ncUnite;
    }

    public List<NonConformiteUnite> getNcUniteFiltered() {
        return ncUniteFiltered;
    }

    public void setNcUniteFiltered(List<NonConformiteUnite> ncUniteFiltered) {
        this.ncUniteFiltered = ncUniteFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<StaffGroupDef> getStaffGroupDef() {
        return staffGroupDef;
    }

    public void setStaffGroupDef(List<StaffGroupDef> staffGroupDef) {
        this.staffGroupDef = staffGroupDef;
    }

    public List<StaffGroupDef> getStaffGroupDefFiltered() {
        return staffGroupDefFiltered;
    }

    public void setStaffGroupDefFiltered(List<StaffGroupDef> staffGroupDefFiltered) {
        this.staffGroupDefFiltered = staffGroupDefFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<StaffGroupDefRole> getStaffGroupDefRole() {
        return staffGroupDefRole;
    }

    public void setStaffGroupDefRole(List<StaffGroupDefRole> staffGroupDefRole) {
        this.staffGroupDefRole = staffGroupDefRole;
    }

    public List<StaffGroupDefRole> getStaffGroupDefRoleFiltered() {
        return staffGroupDefRoleFiltered;
    }

    public void setStaffGroupDefRoleFiltered(List<StaffGroupDefRole> staffGroupDefRoleFiltered) {
        this.staffGroupDefRoleFiltered = staffGroupDefRoleFiltered;
    }
    
    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<StaffGroups> getStaffGroups() {
        return staffGroups;
    }

    public void setStaffGroups(List<StaffGroups> staffGroups) {
        this.staffGroups = staffGroups;
    }

    public List<StaffGroups> getStaffGroupsFiltered() {
        return staffGroupsFiltered;
    }

    public void setStaffGroupsFiltered(List<StaffGroups> staffGroupsFiltered) {
        this.staffGroupsFiltered = staffGroupsFiltered;
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     *
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public boolean handleDateRangeFilters(Object value, Object filter, Locale locale) throws ParseException {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }

        //{"start":"2016-04-18","end":"2016-05-31"}
        if (!filterText.contains("start")) {
            return false;
        }
        String strDate = filterText;
        strDate = strDate.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date begin = format.parse(dates[0]);
        Date end = format.parse(dates[1]);

        //Extend limite in order to make it containt
        Calendar calValue = Calendar.getInstance();
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calValue.setTime((Date) value);
        calBegin.setTime((Date) begin);
        calEnd.setTime((Date) end);
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        calEnd.add(Calendar.DAY_OF_MONTH, +1);
        begin = calBegin.getTime();
        end = calEnd.getTime();

        //Check contain
        if (value instanceof Date) {
            Date date = (Date) value;
            if (date.before(begin) && !date.equals(begin)) {
                return false;
            }
            if (date.after(end) && !date.equals(end)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Cette méthode permet de rafraîchir le résultat en cas de modifaction et
     * ou suppression
     */
    public void handleTableChanges() {
        processus = processusCtrl.getItemsByLastChanged();
        docExplorer = docExplorerCtrl.getItemsByLastChanged();
        docType = docTypeCtrl.getItemsByLastChanged();

        ncFrequency = ncFrequencyCtrl.getItemsByLastChanged();
        ncGravity = ncGravityCtrl.getItemsByLastChanged();
        ncNature = ncNatureCtrl.getItemsByLastChanged();
        ncRequest = ncRequestCtrl.getItemsByLastChanged();
        ncUnite = ncUniteCtrl.getItemsByLastChanged();

        staffGroupDef = staffGroupDefCtrl.getItemsByLastChanged();
        staffGroupDefRole = staffGroupDefRoleCtrl.getItemsByLastChanged();
        staffGroups = staffGroupsCtrl.getItemsByLastChanged();
    }

    /**
     * Use to update datalist when destroy occured
     *
     * @param ctrl
     */
    public void handleDestroy(String ctrl) {
        switch (ctrl) {
            case "processus":
                processusCtrl.destroy();
                processus = processusCtrl.getItemsByLastChanged();
                break;
            case "docExplorer":
                docExplorerCtrl.destroy();
                docExplorer = docExplorerCtrl.getItemsByLastChanged();
                break;
            case "docType":
                docTypeCtrl.destroy();
                docType = docTypeCtrl.getItemsByLastChanged();
                break;
            case "nonConformiteFrequency":
                ncFrequencyCtrl.destroy();
                ncFrequency = ncFrequencyCtrl.getItemsByLastChanged();
                break;
            case "nonConformiteGravity":
                ncGravityCtrl.destroy();
                ncGravity = ncGravityCtrl.getItemsByLastChanged();
                break;
            case "nonConformiteNature":
                ncNatureCtrl.destroy();
                ncNature = ncNatureCtrl.getItemsByLastChanged();
                break;
            case "nonConformiteRequest":
                ncRequestCtrl.destroy();
                ncRequest = ncRequestCtrl.getItemsByLastChanged();
                break;
            case "nonConformiteUnite":
                ncUniteCtrl.destroy();
                ncUnite = ncUniteCtrl.getItemsByLastChanged();
                break;
            case "staffGroupDef":
                staffGroupDefCtrl.destroy();
                staffGroupDef = staffGroupDefCtrl.getItemsByLastChanged();
                break;
            case "staffGroupDefRole":
                staffGroupDefRoleCtrl.destroy();
                staffGroupDefRole = staffGroupDefRoleCtrl.getItemsByLastChanged();
                break;
            case "staffGroups":
                staffGroupsCtrl.destroy();
                staffGroups = staffGroupsCtrl.getItemsByLastChanged();
                break;
            default:
                String allowedCtrl = " Allowed :  processus / docExplorer / docType / nonConformiteFrequency / "
                        + "nonConformiteGravity / nonConformiteNature / nonConformiteRequest / nonConformiteUnite / "
                        + " staffGroupDef / staffGroupDefRole / staffGroups / ";
                throw new IllegalArgumentException("Invalid controller: " + ctrl + allowedCtrl);
        }
    }
}
