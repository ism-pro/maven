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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.jsf.process.ctrl.AnalyseAllowedController;
import org.ism.jsf.process.ctrl.AnalyseDataController;
import org.ism.jsf.process.ctrl.AnalysePointController;
import org.ism.jsf.process.ctrl.AnalyseTypeController;

import org.ism.entities.process.Equipement;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.jsf.util.JsfUtil;
import org.ism.model.chart.ChartModel;
import org.ism.model.chart.ChartSerie;
import org.ism.model.chart.ChartSerieData;
import org.ism.model.chart.properties.ChartType;

@ManagedBean(name = "viewAnalyseChart")
@SessionScoped
public class ViewAnalyseChart implements Serializable {

    public class ViewAnalyseChartSelect {

        private Equipement equipement = null;
        private List<AnalyseAllowed> alloweds = new ArrayList<>();
        private List<AnalysePoint> points = null;

        public Equipement getEquipement() {
            return equipement;
        }

        public void setEquipement(Equipement equipement) {
            this.equipement = equipement;
        }

        public List<AnalyseAllowed> getAlloweds() {
            if (alloweds == null) {
                alloweds = new ArrayList<>();
            }
            return alloweds;
        }

        public void setAlloweds(List<AnalyseAllowed> alloweds) {
            getAlloweds();
            this.alloweds = alloweds;
        }

        public List<AnalysePoint> getPoints() {
            return points;
        }

        public void setPoints(List<AnalysePoint> points) {
            this.points = points;
        }

    }

    @Inject
    AnalyseAllowedController analyseAllowedController;
    @Inject
    AnalyseDataController analyseDataController;
    @Inject
    AnalyseTypeController analyseTypeController;
    @Inject
    AnalysePointController analysePointController;

    private ViewAnalyseChartSelect selected = null;
    private List<AnalyseAllowed> analyseAlloweds = new ArrayList<>();
    private List<AnalysePoint> requestPoints;
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

        // Init. Analyse Point Ctrl if not exist
        if (analysePointController == null) {
            analysePointController = (AnalysePointController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "analysePointController");
            analysePointController.prepareCreate();
        }

        if (analyseDataController == null) {
            analyseDataController = (AnalyseDataController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "analyseDataController");
            analyseDataController.prepareCreate();
        }
        // Init selection case
        selected = new ViewAnalyseChartSelect();

        // Init request
        requestPoints = analysePointController.getItems();

        dateFrom = new Date();
        dateFrom = new Date(dateFrom.getYear(), dateFrom.getMonth(), 1);
        dateTo = new Date();
    }

    /**
     * This method is used to react on point selection. It can only be use on a
     * component with specic Id "requestEquipement".
     */
    public void handleEquipementSelect() {
        if (selected.equipement == null) {
            requestPoints = analysePointController.getItems();
        } else {
            requestPoints = analysePointController.getItemsByEquipement(selected.equipement);
        }
    }

    /**
     * This method is used to react on point selection. It can only be use on a
     * component with specic Id "requestPoint".
     */
    public void handlePointSelect() {
        /* UIComponent c = JsfUtil.findComponent("requestPoint");
        org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu som = (org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu) c;
         */
        if (selected.points == null) { // No point selected
            /*som.setRequired(true);
            som.updateModel(FacesContext.getCurrentInstance());*/
            JsfUtil.addErrorMessage("AnalyseAlloweds = null");
            analyseAlloweds = analyseAllowedController.getItems();
        } else if (selected.points.isEmpty()) {
            JsfUtil.addErrorMessage("AnalyseAlloweds = null");
            analyseAlloweds = analyseAllowedController.getItems();
        } else {
            /*som.setRequired(false);
            som.updateModel(FacesContext.getCurrentInstance());*/
            analyseAlloweds = new ArrayList<>(); // Init list of type
            // For each point create a list of allowed analyse type
            selected.points.stream().forEach((point) -> {
                List<AnalyseAllowed> aAlloweds = analyseAllowedController.getItemsByPoint(point);
                if (aAlloweds != null) {
                    if (!aAlloweds.isEmpty()) {
                        analyseAlloweds.addAll(aAlloweds);
                    }
                }
            });
            JsfUtil.addSuccessMessage("AnalyseAlloweds = true");
        }

    }


    private void createModels() {
        // Get Fist Item
        if (selected.alloweds == null) {
            return;
        }
        if (selected.alloweds.isEmpty()) {
            return;
        }

        models = new ArrayList<>();
        Iterator<AnalyseAllowed> iterAnalyseAllowed = selected.alloweds.iterator();
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

    /**
     * Methode de recherche des données requises
     */
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
        return analyseAlloweds;
    }

    public void setAnalyseAlloweds(List<AnalyseAllowed> analyseAlloweds) {
        this.analyseAlloweds = analyseAlloweds;
    }



    public ViewAnalyseChartSelect getSelected() {
        return selected;
    }

    public void setSelected(ViewAnalyseChartSelect selected) {
        this.selected = selected;
    }

    public List<AnalysePoint> getRequestPoints() {
        return requestPoints;
    }

    public void setRequestPoints(List<AnalysePoint> requestPoints) {
        this.requestPoints = requestPoints;
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
