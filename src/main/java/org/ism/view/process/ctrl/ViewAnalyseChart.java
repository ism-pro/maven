/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.process.ctrl;

import org.ism.view.*;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.ism.entities.app.IsmNcrstate;
import org.ism.jsf.app.IsmNcrstateController;
import org.ism.jsf.process.ctrl.AnalyseAllowedController;
import org.ism.jsf.process.ctrl.AnalyseDataController;
import org.ism.jsf.process.ctrl.AnalysePointController;
import org.ism.jsf.process.ctrl.AnalyseTypeController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.listener.SessionCounterListener;
import org.primefaces.event.ItemSelectEvent;

import org.ism.domain.chart.ChartModel;
import org.ism.entities.process.Equipement;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean(name = "viewAnalyseChart")
public class ViewAnalyseChart implements Serializable {

    @Inject
    AnalyseAllowedController analyseAllowedController;
    @Inject
    AnalyseDataController analyseDataController;
    @Inject
    AnalyseTypeController analyseTypeController;
    @Inject
    AnalysePointController analysePointController;

    private Equipement selectedEquipement;
    private List<AnalyseAllowed> analyseAlloweds = new ArrayList<AnalyseAllowed>();
    private Date dateFrom;
    private Date dateTo;

    private ChartModel mainChartModel;
    private ChartModel mainChartLimitHH;
    private ChartModel mainChartLimitH;
    private ChartModel mainChartLimitB;
    private ChartModel mainChartLimitBB;

    private Integer activeIndex = 0;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (analyseAllowedController == null) {
            analyseAllowedController = (AnalyseAllowedController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "analyseAllowedController");
            analyseAllowedController.prepareCreate();
        }

        if (analyseDataController == null) {
            analyseDataController = (AnalyseDataController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "analyseDataController");
            analyseDataController.prepareCreate();
        }
        dateFrom = new Date();
        dateFrom = new Date(dateFrom.getYear(), dateFrom.getMonth(), 1);
        dateTo = new Date();

    }

    private void createModels() {
        mainChartModel = new ChartModel();
        mainChartModel.setType(ChartModel.ChartType.TYPE_LINE);
        AnalyseAllowed firstItem = ((AnalyseAllowed) analyseAlloweds.get(0));
        mainChartModel.setTitle(firstItem.getAaPoint().getApPoint() + " - "
                + firstItem.getAaPoint().getApDesignation() + " ["
                + firstItem.getAaType().getAtType()
                + " - "
                + firstItem.getAaType().getAtDesignation() + "]");
        mainChartModel.getAxes().setxLabel(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("AnalyseDataField_adsampleTime"));
        mainChartModel.getAxes().setyLabel(firstItem.getAaType().getAtUnite().getUUnite());
        mainChartModel.setCurveLabel(firstItem.getAaType().getAtType()
                + " - "
                + firstItem.getAaType().getAtDesignation());
        mainChartLimitHH = new ChartModel();
        mainChartLimitH = new ChartModel();
        mainChartLimitB = new ChartModel();
        mainChartLimitBB = new ChartModel();

        // 
        try {
            Iterator<AnalyseData> iterData = analyseDataController.getItemsByPointTypeSampleTimeRange(
                    firstItem.getAaPoint(),
                    firstItem.getAaType(),
                    dateFrom, dateTo).iterator();
            while (iterData.hasNext()) {
                AnalyseData data = iterData.next();
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                String date = df.format(data.getAdsampleTime());

                mainChartModel.add(date, data.getAdValue());
                mainChartLimitHH.add(date, data.getAdlimitHH());
                mainChartLimitH.add(date, data.getAdlimitH());
                mainChartLimitB.add(date, data.getAdlimitB());
                mainChartLimitBB.add(date, data.getAdlimitBB());
            }
            activeIndex = 1;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("ViewAnalyseChart", 
                    ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("EmptyList"));
        }

    }

    /**
     * ************************************************************************
     *
     * ************************************************************************
     */
    public void handleAnalyseSearch() {
        createModels();
        
    }

    /**
     * ************************************************************************
     *
     * ************************************************************************
     */
    public ChartModel getMainChartModel() {
        return mainChartModel;
    }

    public void setMainChartModel(ChartModel mainChartModel) {
        this.mainChartModel = mainChartModel;
    }

    public ChartModel getMainChartLimitHH() {
        return mainChartLimitHH;
    }

    public void setMainChartLimitHH(ChartModel mainChartLimitHH) {
        this.mainChartLimitHH = mainChartLimitHH;
    }

    public ChartModel getMainChartLimitH() {
        return mainChartLimitH;
    }

    public void setMainChartLimitH(ChartModel mainChartLimitH) {
        this.mainChartLimitH = mainChartLimitH;
    }

    public ChartModel getMainChartLimitB() {
        return mainChartLimitB;
    }

    public void setMainChartLimitB(ChartModel mainChartLimitB) {
        this.mainChartLimitB = mainChartLimitB;
    }

    public ChartModel getMainChartLimitBB() {
        return mainChartLimitBB;
    }

    public void setMainChartLimitBB(ChartModel mainChartLimitBB) {
        this.mainChartLimitBB = mainChartLimitBB;
    }

    public List<AnalyseAllowed> getAnalyseAlloweds() {
        if (analyseAlloweds == null) {
            analyseAlloweds = new ArrayList<AnalyseAllowed>();
        }
        return analyseAlloweds;
    }

    public void setAnalyseAlloweds(List<AnalyseAllowed> analyseAlloweds) {
        getAnalyseAlloweds();
        this.analyseAlloweds = analyseAlloweds;
    }

    public Equipement getSelectedEquipement() {
        return selectedEquipement;
    }

    public void setSelectedEquipement(Equipement selectedEquipement) {
        this.selectedEquipement = selectedEquipement;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

}
