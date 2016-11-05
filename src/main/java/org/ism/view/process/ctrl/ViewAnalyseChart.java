/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.process.ctrl;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.jsf.process.ctrl.AnalyseAllowedController;
import org.ism.jsf.process.ctrl.AnalyseDataController;
import org.ism.jsf.process.ctrl.AnalysePointController;
import org.ism.jsf.process.ctrl.AnalyseTypeController;

import org.ism.entities.process.Equipement;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.jsf.util.JsfUtil;
import org.ism.model.chart.ChartModel;
import org.ism.model.chart.ChartSerie;
import org.ism.model.chart.ChartSerieData;
import org.ism.model.chart.properties.ChartType;

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
    private List<AnalyseAllowed> analyseAlloweds = new ArrayList<>();
    private Date dateFrom;
    private Date dateTo;

    List<ChartModel> models = new ArrayList<>();

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
        // Get Fist Item
        if (analyseAlloweds == null) {
            return;
        }
        if (analyseAlloweds.isEmpty()) {
            return;
        }

        models = new ArrayList<>();
        Iterator<AnalyseAllowed> iterAnalyseAllowed = analyseAlloweds.iterator();
        while (iterAnalyseAllowed.hasNext()) {
            AnalyseAllowed currentAnalyse = ((AnalyseAllowed) iterAnalyseAllowed.next());

            ChartModel chartModel = new ChartModel();
            chartModel.getChart().setType(ChartType.LINE);
            chartModel.getChart().setZommType("x");
            // Seutp Title
            chartModel.getTitle().setText(currentAnalyse.getAaPoint().getApPoint() + " - "
                    + currentAnalyse.getAaPoint().getApDesignation());
            chartModel.getTitle().setX(-20);
            // Setup SubTitle
            chartModel.getSubTitle().setText("["
                    + currentAnalyse.getAaType().getAtType()
                    + " - "
                    + currentAnalyse.getAaType().getAtDesignation() + "]");
            chartModel.getSubTitle().setX(-20);
            // Setup XAxis
            chartModel.getxAxis().getTitle().setText(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                    getString("AnalyseDataField_adsampleTime"));
            chartModel.getxAxis().setCategories(analyseDataController.getItemsByPointTypeSampleTimeRangeSampleDate(
                    currentAnalyse.getAaPoint(), currentAnalyse.getAaType(),
                    dateFrom, dateTo, ""));
            // Setup YAxis
            chartModel.getyAxis().getTitle().setText(currentAnalyse.getAaType().getAtUnite().getUDesignation());
            chartModel.getyAxis().getPlotLines().setValue(0);
            chartModel.getyAxis().getPlotLines().setWidth(1);
            chartModel.getyAxis().getPlotLines().setColor("#808080");
            // Setup Tooltip
            chartModel.getTooltip().setValueSuffix(currentAnalyse.getAaType().getAtUnite().getUUnite());

            // Manage serie title
            ChartSerie sMain = new ChartSerie(currentAnalyse.getAaType().getAtType()
                    + " - "
                    + currentAnalyse.getAaType().getAtDesignation());
            ChartSerie sLimitHH = new ChartSerie(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                    getString("AnalyseAllowedField_aalimitHH"));
            ChartSerie sLimitH = new ChartSerie(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                    getString("AnalyseAllowedField_aalimitH"));
            ChartSerie sLimitB = new ChartSerie(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                    getString("AnalyseAllowedField_aalimitB"));
            ChartSerie sLimitBB = new ChartSerie(ResourceBundle.getBundle(JsfUtil.BUNDLE).
                    getString("AnalyseAllowedField_aalimitBB"));

            try {
                List<Double> datas = analyseDataController
                        .getItemsByPointTypeSampleTimeRangeD(currentAnalyse.getAaPoint(), currentAnalyse.getAaType(),
                                dateFrom, dateTo);
                if (datas != null) {
                    sMain.add(new ChartSerieData(datas));
                    chartModel.addSerie(sMain);
                }
                List<Double> adLimitHH = analyseDataController.getItemsByPointTypeSampleTimeRangeLimite(
                        currentAnalyse.getAaPoint(), currentAnalyse.getAaType(),
                        dateFrom, dateTo, "HH");
                if (adLimitHH != null) {
                    sLimitHH.add(new ChartSerieData(adLimitHH));
                    chartModel.addSerie(sLimitHH);
                }
                List<Double> adLimitH = analyseDataController.getItemsByPointTypeSampleTimeRangeLimite(
                        currentAnalyse.getAaPoint(), currentAnalyse.getAaType(),
                        dateFrom, dateTo, "H");
                if (adLimitH != null) {
                    sLimitH.add(new ChartSerieData(adLimitH));
                    chartModel.addSerie(sLimitH);
                }
                List<Double> adLimitB = analyseDataController.getItemsByPointTypeSampleTimeRangeLimite(
                        currentAnalyse.getAaPoint(), currentAnalyse.getAaType(),
                        dateFrom, dateTo, "B");
                if (adLimitB != null) {
                    sLimitB.add(new ChartSerieData(adLimitB));
                    chartModel.addSerie(sLimitB);
                }
                List<Double> adLimitBB = analyseDataController.getItemsByPointTypeSampleTimeRangeLimite(
                        currentAnalyse.getAaPoint(),
                        currentAnalyse.getAaType(),
                        dateFrom, dateTo, "BB");
                if (adLimitBB != null) {
                    sLimitBB.add(new ChartSerieData(adLimitBB));
                    chartModel.addSerie(sLimitBB);
                }
                models.add(chartModel);
                activeIndex = 1;
            } catch (Exception e) {
                JsfUtil.addErrorMessage("ViewAnalyseChart",
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString("EmptyList"));
            }
        }

    }

    public void handleAnalyseSearch() {
        createModels();

    }


    public List<ChartModel> getModels() {
        createModels();
        return models;
    }

    public void setModels(List<ChartModel> models) {
        this.models = models;
    }

    public List<AnalyseAllowed> getAnalyseAlloweds() {
        if (analyseAlloweds == null) {
            analyseAlloweds = new ArrayList<>();
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
