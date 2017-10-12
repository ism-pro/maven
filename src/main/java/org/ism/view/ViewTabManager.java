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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalyseCategory;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.entities.process.ctrl.AnalyseMethod;
import org.ism.entities.process.ctrl.AnalyseNotify;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.DocType;
import org.ism.entities.process.Equipement;
import org.ism.entities.smq.nc.NonConformiteFrequency;
import org.ism.entities.smq.nc.NonConformiteGravity;
import org.ism.entities.smq.nc.NonConformiteNature;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.entities.smq.nc.NonConformiteUnite;
import org.ism.entities.smq.Processus;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroupDefRole;
import org.ism.entities.hr.StaffGroups;
import org.ism.entities.process.Unite;
import org.ism.entities.smq.PointInfos;
import org.ism.jsf.process.ctrl.AnalyseAllowedController;
import org.ism.jsf.process.ctrl.AnalyseCategoryController;
import org.ism.jsf.process.ctrl.AnalyseDataController;
import org.ism.jsf.process.ctrl.AnalyseMethodController;
import org.ism.jsf.process.ctrl.AnalyseNotifyController;
import org.ism.jsf.process.ctrl.AnalysePointController;
import org.ism.jsf.process.ctrl.AnalyseTypeController;
import org.ism.jsf.smq.DocExplorerController;
import org.ism.jsf.smq.DocTypeController;
import org.ism.jsf.process.EquipementController;
import org.ism.jsf.smq.nc.NonConformiteFrequencyController;
import org.ism.jsf.smq.nc.NonConformiteGravityController;
import org.ism.jsf.smq.nc.NonConformiteNatureController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.jsf.smq.nc.NonConformiteUniteController;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.hr.StaffGroupDefController;
import org.ism.jsf.hr.StaffGroupDefRoleController;
import org.ism.jsf.hr.StaffGroupsController;
import org.ism.jsf.process.UniteController;
import org.ism.jsf.smq.PointInfosController;
import org.ism.lazy.process.ctrl.AnalyseDataLazyModel;
import org.primefaces.model.LazyDataModel;

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

    private StaffController staffCtrl;
    private List<Staff> staff;
    private List<Staff> staffFiltered;

    private EquipementController equipementCtrl;
    private List<Equipement> equipement;
    private List<Equipement> equipementFiltered;

    private UniteController uniteCtrl;
    private List<Unite> unite;
    private List<Unite> uniteFiltered;

    private AnalyseAllowedController analyseallowedCtrl;
    private List<AnalyseAllowed> analyseallowed;
    private List<AnalyseAllowed> analyseallowedFiltered;

    private AnalyseCategoryController analysecategoryCtrl;
    private List<AnalyseCategory> analysecategory;
    private List<AnalyseCategory> analysecategoryFiltered;

//    private AnalyseDataController analysedataCtrl;
//    private List<AnalyseData> analysedata;
//    private List<AnalyseData> analysedataFiltered;

    private AnalyseMethodController analysemethodCtrl;
    private List<AnalyseMethod> analysemethod;
    private List<AnalyseMethod> analysemethodFiltered;

    private AnalyseNotifyController analysenotifyCtrl;
    private List<AnalyseNotify> analysenotify;
    private List<AnalyseNotify> analysenotifyFiltered;

    private AnalysePointController analysepointCtrl;
    private List<AnalysePoint> analysepoint;
    private List<AnalysePoint> analysepointFiltered;

    private AnalyseTypeController analysetypeCtrl;
    private List<AnalyseType> analysetype;
    private List<AnalyseType> analysetypeFiltered;

    private PointInfosController pointInfosController;
    private List<PointInfos> pointInfos;
    private List<PointInfos> pointInfosFiltered;

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

        staffCtrl = (StaffController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffController");
        staff = staffCtrl.getItemsByLastChanged();

        equipementCtrl = (EquipementController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "equipementController");
        equipement = equipementCtrl.getItemsByLastChanged();

        uniteCtrl = (UniteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "uniteController");
        unite = uniteCtrl.getItemsByLastChanged();

        analyseallowedCtrl = (AnalyseAllowedController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analyseAllowedController");
        analyseallowed = analyseallowedCtrl.getItemsByLastChanged();

        analysecategoryCtrl = (AnalyseCategoryController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analyseCategoryController");
        analysecategory = analysecategoryCtrl.getItemsByLastChanged();

//        analysedataCtrl = (AnalyseDataController) facesContext.getApplication().getELResolver().
//                getValue(facesContext.getELContext(), null, "analyseDataController");
        //analysedata = analysedataCtrl.getItemsByLastChanged();

        analysemethodCtrl = (AnalyseMethodController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analyseMethodController");
        analysemethod = analysemethodCtrl.getItemsByLastChanged();

        analysenotifyCtrl = (AnalyseNotifyController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analyseNotifyController");
        analysenotify = analysenotifyCtrl.getItemsByLastChanged();

        analysepointCtrl = (AnalysePointController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analysePointController");
        analysepoint = analysepointCtrl.getItemsByLastChanged();

        analysetypeCtrl = (AnalyseTypeController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "analyseTypeController");
        analysetype = analysetypeCtrl.getItemsByLastChanged();

        pointInfosController = (PointInfosController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "pointInfosController");
        pointInfos = pointInfosController.getItemsByLastChanged();

    }

    /**
     * *************************************************************************
     * @return processus
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
     * @return doc explorer
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
     * @return doc type
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
     * @return non conformite frequency
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
     * @return non conformite gravity
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
     * @return non conformite nature
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
     * @return non conformite request
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
     * @return non conformite unite
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
     * @return staff group def
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
     * @return staff group def role
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
     * @return staff griyos
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
     * *************************************************************************
     * @return staff
     * *************************************************************************
     */
    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Staff> getStaffFiltered() {
        return staffFiltered;
    }

    public void setStaffFiltered(List<Staff> staffFiltered) {
        this.staffFiltered = staff;
    }

    /**
     * *************************************************************************
     * @return equipement
     * *************************************************************************
     */
    public List<Equipement> getEquipement() {
        return equipement;
    }

    public void setEquipement(List<Equipement> equipement) {
        this.equipement = equipement;
    }

    public List<Equipement> getEquipementFiltered() {
        return equipementFiltered;
    }

    public void setEquipementFiltered(List<Equipement> equipementFiltered) {
        this.equipementFiltered = equipementFiltered;
    }

    /**
     * *************************************************************************
     * @return unite
     * *************************************************************************
     */
    public List<Unite> getUnite() {
        return unite;
    }

    public void setUnite(List<Unite> unite) {
        this.unite = unite;
    }

    public List<Unite> getUniteFiltered() {
        return uniteFiltered;
    }

    public void setUniteFiltered(List<Unite> uniteFiltered) {
        this.uniteFiltered = uniteFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse allowed
     * *************************************************************************
     */
    public List<AnalyseAllowed> getAnalyseallowed() {
        return analyseallowed;
    }

    public void setAnalyseallowed(List<AnalyseAllowed> analyseallowed) {
        this.analyseallowed = analyseallowed;
    }

    public List<AnalyseAllowed> getAnalyseallowedFiltered() {
        return analyseallowedFiltered;
    }

    public void setAnalyseallowedFiltered(List<AnalyseAllowed> analyseallowedFiltered) {
        this.analyseallowedFiltered = analyseallowedFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse category
     * *************************************************************************
     */
    public List<AnalyseCategory> getAnalysecategory() {
        return analysecategory;
    }

    public void setAnalysecategory(List<AnalyseCategory> analysecategory) {
        this.analysecategory = analysecategory;
    }

    public List<AnalyseCategory> getAnalysecategoryFiltered() {
        return analysecategoryFiltered;
    }

    public void setAnalysecategoryFiltered(List<AnalyseCategory> analysecategoryFiltered) {
        this.analysecategoryFiltered = analysecategoryFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse data
     * *************************************************************************
     */
//    public LazyDataModel<AnalyseData> getAnalyseDataModel() {
//        return new AnalyseDataLazyModel(analysedata);
//    }
//
//    public List<AnalyseData> getAnalysedata() {
//        return analysedata;
//    }
//
//    public void setAnalysedata(List<AnalyseData> analysedata) {
//        this.analysedata = analysedata;
//    }
//
//    public List<AnalyseData> getAnalysedataFiltered() {
//        return analysedataFiltered;
//    }
//
//    public void setAnalysedataFiltered(List<AnalyseData> analysedataFiltered) {
//        this.analysedataFiltered = analysedataFiltered;
//    }

    /**
     * *************************************************************************
     * @return analyse method
     * *************************************************************************
     */
    public List<AnalyseMethod> getAnalysemethod() {
        return analysemethod;
    }

    public void setAnalysemethod(List<AnalyseMethod> analysemethod) {
        this.analysemethod = analysemethod;
    }

    public List<AnalyseMethod> getAnalysemethodFiltered() {
        return analysemethodFiltered;
    }

    public void setAnalysemethodFiltered(List<AnalyseMethod> analysemethodFiltered) {
        this.analysemethodFiltered = analysemethodFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse notify
     * *************************************************************************
     */
    public List<AnalyseNotify> getAnalysenotify() {
        return analysenotify;
    }

    public void setAnalysenotify(List<AnalyseNotify> analysenotify) {
        this.analysenotify = analysenotify;
    }

    public List<AnalyseNotify> getAnalysenotifyFiltered() {
        return analysenotifyFiltered;
    }

    public void setAnalysenotifyFiltered(List<AnalyseNotify> analysenotifyFiltered) {
        this.analysenotifyFiltered = analysenotifyFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse point
     * *************************************************************************
     */
    public List<AnalysePoint> getAnalysepoint() {
        return analysepoint;
    }

    public void setAnalysepoint(List<AnalysePoint> analysepoint) {
        this.analysepoint = analysepoint;
    }

    public List<AnalysePoint> getAnalysepointFiltered() {
        return analysepointFiltered;
    }

    public void setAnalysepointFiltered(List<AnalysePoint> analysepointFiltered) {
        this.analysepointFiltered = analysepointFiltered;
    }

    /**
     * *************************************************************************
     * @return analyse type
     * *************************************************************************
     */
    public List<AnalyseType> getAnalysetype() {
        return analysetype;
    }

    public void setAnalysetype(List<AnalyseType> analysetype) {
        this.analysetype = analysetype;
    }

    public List<AnalyseType> getAnalysetypeFiltered() {
        return analysetypeFiltered;
    }

    public void setAnalysetypeFiltered(List<AnalyseType> analysetypeFiltered) {
        this.analysetypeFiltered = analysetypeFiltered;
    }

    /**
     * *************************************************************************
     * @return point infos
     * *************************************************************************
     */
    public List<PointInfos> getPointInfos() {
        return pointInfos;
    }

    public void setPointInfos(List<PointInfos> pointInfos) {
        this.pointInfos = pointInfos;
    }

    public List<PointInfos> getPointInfosFiltered() {
        return pointInfosFiltered;
    }

    public void setPointInfosFiltered(List<PointInfos> pointInfosFiltered) {
        this.pointInfosFiltered = pointInfosFiltered;
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     *
     * @param value a value
     * @param filter a object filtred
     * @param locale a local
     * @return true if ok
     * @throws ParseException an error
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
        staff = staffCtrl.getItemsByLastChanged();

        equipement = equipementCtrl.getItemsByLastChanged();
        unite = uniteCtrl.getItemsByLastChanged();
        analyseallowed = analyseallowedCtrl.getItemsByLastChanged();
        analysecategory = analysecategoryCtrl.getItemsByLastChanged();
//        analysedata = analysedataCtrl.getItemsByLastChanged();
        analysemethod = analysemethodCtrl.getItemsByLastChanged();
        analysenotify = analysenotifyCtrl.getItemsByLastChanged();
        analysepoint = analysepointCtrl.getItemsByLastChanged();
        analysetype = analysetypeCtrl.getItemsByLastChanged();

        pointInfos = pointInfosController.getItemsByLastChanged();
    }

    /**
     * Use to update datalist when destroy occured
     *
     * @param ctrl a controller
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
            case "staff":
                staffCtrl.destroy();
                staff = staffCtrl.getItemsByLastChanged();
                break;
            case "equipement":
                equipementCtrl.destroy();
                equipement = equipementCtrl.getItemsByLastChanged();
                break;
            case "unite":
                uniteCtrl.destroy();
                unite = uniteCtrl.getItemsByLastChanged();
                break;
            case "analyseallowed":
                analyseallowedCtrl.destroy();
                analyseallowed = analyseallowedCtrl.getItemsByLastChanged();
                break;
            case "analysecategory":
                analysecategoryCtrl.destroy();
                analysecategory = analysecategoryCtrl.getItemsByLastChanged();
                break;
//            case "analysedata":
//                analysedataCtrl.destroy();
//                analysedata = analysedataCtrl.getItemsByLastChanged();
//                break;
            case "analysemethod":
                analysemethodCtrl.destroy();
                analysemethod = analysemethodCtrl.getItemsByLastChanged();
                break;
            case "analysenotify":
                analysenotifyCtrl.destroy();
                analysenotify = analysenotifyCtrl.getItemsByLastChanged();
                break;
            case "analysepoint":
                analysepointCtrl.destroy();
                analysepoint = analysepointCtrl.getItemsByLastChanged();
                break;
            case "analysetype":
                analysetypeCtrl.destroy();
                analysetype = analysetypeCtrl.getItemsByLastChanged();
                break;
            case "pointInfos":
                pointInfosController.destroy();
                pointInfos = pointInfosController.getItemsByLastChanged();
                break;
            default:
                String allowedCtrl = " Allowed :  processus / docExplorer / docType / nonConformiteFrequency / "
                        + "nonConformiteGravity / nonConformiteNature / nonConformiteRequest / nonConformiteUnite / "
                        + " staffGroupDef / staffGroupDefRole / staffGroups / staff / equipement /"
                        + "unite / analyseallowed / analysecategory / analysedata / analysemethod / analysenotify / analysetype /"
                        + " pointInfos";
                throw new IllegalArgumentException("Invalid controller: " + ctrl + allowedCtrl);
        }
    }
}
