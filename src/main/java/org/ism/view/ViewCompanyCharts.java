/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.ism.entities.app.IsmNcrstate;
import org.ism.jsf.app.IsmNcrstateController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.listener.SessionCounterListener;
import org.ism.charts.model.ChartModel;
import org.ism.charts.model.ChartSerie;
import org.ism.charts.model.ChartSerieData;
import org.ism.charts.model.properties.ChartType;

/**
 * <h2>ViewCompanyCharts</h2>
 *
 * <h3>Description</h3>
 * ViewCompanyCharts is the managed bean which control the main company charts. It
 * contains all model needeed.
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewCompanyCharts")
public class ViewCompanyCharts implements Serializable {

    private NonConformiteRequestController ncRequestCtrl = new NonConformiteRequestController();
    private IsmNcrstateController ncStateCtrl = new IsmNcrstateController();
    private StaffController stStaffCtrl = new StaffController();

    /**
     * <h1>sessionModel</h1>
     * Session model containt datas to render a pie chart to illustrate number
     * of connected user
     */
    ChartModel sessionModel = null;

    /**
     * <h1>nonConformiteModel</h1>
     * Session model containt datas to render a pie chart to illustrate number
     * of non conformite state
     */
    ChartModel nonConformiteModel = null;

    /**
     * Preapare bean with injected managedbean check if all are load properly
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Get Bean
        ncRequestCtrl = (org.ism.jsf.smq.nc.NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
        ncRequestCtrl.prepareCreate();

        ncStateCtrl = (IsmNcrstateController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ismNcrstateController");
        ncStateCtrl.prepareCreate();
        
        stStaffCtrl = (StaffController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "staffController");
        stStaffCtrl.prepareCreate();
    }

    /**
     * <h2>createSessionModel</h2>
     *
     * <h3>Description</h3>
     * This method create the new method
     */
    private void createSessionModel() {
        Double percActiveSession
                = SessionCounterListener.getTotalActiveSessionsAuthenticated()
                / (1.00 * stStaffCtrl.getItems().size());
        /*Double perInactiveSession = (SessionCounterListener.getTotalActiveSession() - SessionCounterListener.getTotalActiveSessionsAuthenticated())
                / (1.00 * SessionCounterListener.getTotalActiveSession());*/
        Double perStaffInactive = 1.00 - percActiveSession;

        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.PIE);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);
        // Seutp Title
        model.getTitle().setText("Sessions actives");
        // Setup Tooltip
        model.getTooltip().setPointFormat("{series.name}: <b>{point.percentage:.1f}%</b>");
        // Setup plotOptions
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().getDataLabels().setEnabled(true);
        model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        model.getPlotOptions().getDataLabels().setStyle("color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'");
        // Setup serie
        ChartSerie sessionSerie = new ChartSerie("Sessions");
        sessionSerie.setColorByPoint(Boolean.TRUE);
        sessionSerie.add(new ChartSerieData("Connectée(s)", percActiveSession, true, true));
        sessionSerie.add(new ChartSerieData("Déconectée(s)", perStaffInactive));
        model.addSerie(sessionSerie);

        sessionModel = model;
    }

    /**
     * <h2>createSessionModel</h2>
     *
     * <h3>Description</h3>
     * This method create the new method
     */
    private void createNonConformiteModels() {
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.PIE);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);
        // Seutp Title
        model.getTitle().setText("Non conformités");
        // Setup Tooltip
        model.getTooltip().setPointFormat("<b>{point.percentage:.1f}%</b>");
        // Setup plotOptions
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().getDataLabels().setEnabled(true);
        model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        model.getPlotOptions().getDataLabels().setStyle("color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'");
        // Setup serie
        ChartSerie nonConformiteSerie = new ChartSerie("Non Conformité");
        nonConformiteSerie.setColorByPoint(Boolean.TRUE);
        Iterator<IsmNcrstate> iterState = ncStateCtrl.getItems().iterator();
        while (iterState.hasNext()) {
            IsmNcrstate ncState = iterState.next();
            Double cntNCState = 1.00 * ncRequestCtrl.countByState(ncState.getIstate());
            nonConformiteSerie.add(new ChartSerieData(ncState.getStatename(), cntNCState));
        }

        model.addSerie(nonConformiteSerie);

        nonConformiteModel = model;
    }

    /**
     * <h3>getSessionModel</h3>
     * First refresh session model to obtain new values using method
     * "createSessionModel".
     *
     * @see createSessionModel
     * @return the new session model refreshed
     */
    public ChartModel getSessionModel() {
        createSessionModel();
        return sessionModel;
    }

    public void setSessionModel(ChartModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    /**
     * <h3>getNonConformiteModel</h3>
     * First refresh Non Conformite model to obtain new values using method
     * "createNonConformiteModel"
     *
     * @see ViewCompanyCharts#createNonConformiteModels()
     * @return the new non conformite model state
     */
    public ChartModel getNonConformiteModel() {
        createNonConformiteModels();
        return nonConformiteModel;
    }

    public void setNonConformiteModel(ChartModel nonConformiteModel) {
        this.nonConformiteModel = nonConformiteModel;
    }

}
