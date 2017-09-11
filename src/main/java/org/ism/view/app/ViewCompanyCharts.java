/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.app;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.entities.app.IsmNcrstate;
import org.ism.jsf.app.IsmNcrstateController;
import org.ism.jsf.hr.StaffController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.listener.SessionCounterListener;
import org.ism.charts.model.ChartModel;
import org.ism.charts.model.properties.ChartType;
import org.ism.charts.model.properties.DataLabels;
import org.ism.charts.model.series.Data;
import org.ism.charts.model.series.PlotOptions;
import org.ism.charts.model.series.Series;
import org.ism.charts.model.series.ToolTip;
import org.ism.charts.model.series.type.PieSerie;

/**
 * <h2>ViewCompanyCharts</h2>
 *
 * <h3>Description</h3>
 * ViewCompanyCharts is the managed bean which control the main company charts.
 * It contains all model needeed.
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewCompanyCharts")
@SessionScoped
public class ViewCompanyCharts implements Serializable {

    /**
     * Staff controller contains all required data of existing staffs (users).
     */
    @ManagedProperty(value = "#{staffController}")
    private StaffController staffController;

    /**
     * Non Conformite state controller provide description of non conformite
     * states
     */
    @ManagedProperty(value = "#{ismNcrstateController}")
    private IsmNcrstateController ismNcrstateController;

    /**
     * Non conformite request controller provide all requests
     */
    @ManagedProperty(value = "#{nonConformiteRequestController}")
    private NonConformiteRequestController nonConformiteRequestController;


    
    
    /**
     * sessionModel Session model containt datas to render a pie chart to
     * illustrate number of connected user
     */
    ChartModel sessionModel = null;

    /**
     * nonConformiteModel Session model containt datas to render a pie chart to
     * illustrate number of non conformite state
     */
    ChartModel nonConformiteModel = null;

    ////////////////////////////////////////////////////////////////////////////
    /// Initialisation
    ///
    ////////////////////////////////////////////////////////////////////////////
    @PostConstruct
    public void init() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//
//        // Get Bean
//        nonConformiteRequestController = (org.ism.jsf.smq.nc.NonConformiteRequestController) facesContext.getApplication().getELResolver().
//                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
//        nonConformiteRequestController.prepareCreate();
//
//        ismNcrstateController = (IsmNcrstateController) facesContext.getApplication().getELResolver().
//                getValue(facesContext.getELContext(), null, "ismNcrstateController");
//        ismNcrstateController.prepareCreate();
//
//        staffController = (StaffController) facesContext.getApplication().getELResolver().
//                getValue(facesContext.getELContext(), null, "staffController");
//        staffController.prepareCreate();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Controller
    ///
    ////////////////////////////////////////////////////////////////////////////
    /**
     * createSessionModel Create corresponding chart model for sessions
     */
    private void createSessionModel() {
        float percActiveSession
                = (float) (SessionCounterListener.getTotalActiveSessionsAuthenticated()
                / (1.00 * staffController.getItems().size()));
        /*Double perInactiveSession = (SessionCounterListener.getTotalActiveSession() - SessionCounterListener.getTotalActiveSessionsAuthenticated())
                / (1.00 * SessionCounterListener.getTotalActiveSession());*/
        float perStaffInactive = (float) (1.00 - percActiveSession);

        // Managing Chart
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.PIE);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);

        // Managing Titles
        model.getTitle().setText("Staffs Connectés");

        // Managing plotOptions
        model.setPlotOptions(new PlotOptions());
        model.getPlotOptions().setType(ChartType.PIE);
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().setDataLabels(new DataLabels());
        model.getPlotOptions().getDataLabels().setEnabled(true);
        model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        model.getPlotOptions().getDataLabels().setStyle("{color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'}");

        // Managing Series
        Data connect = new Data("Connectés", (Float) percActiveSession);
        Data disconnect = new Data("Déconnectés", perStaffInactive);

        model.setSeries(new Series());
        model.getSeries().setPieSerie(new PieSerie());
        model.getSeries().getPieSerie().setData(new ArrayList<>());
        model.getSeries().getPieSerie().setColorByPoint(Boolean.TRUE);
        model.getSeries().getPieSerie().getData().add(connect);
        model.getSeries().getPieSerie().getData().add(disconnect);

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
        model.getChart().setPlotShadow(false);

        // Seutp Title
        model.getTitle().setText("Non conformités");

        // Setup Tooltip
        model.setToolTip(new ToolTip());
        model.getToolTip().setPointFormat("{series.name}: <b>{point.percentage:.1f}%</b>");

        // Setup plotOptions
        model.setPlotOptions(new PlotOptions());
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().setDataLabels(new DataLabels());
        model.getPlotOptions().getDataLabels().setEnabled(true);
        model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        model.getPlotOptions().getDataLabels().setStyle("{color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'}");

        // Setup Legend
//        model.setLegend(new Legend());
//        model.getLegend().setLayout(Align.VERTICAL);
//        model.getLegend().setAlign(Align.RIGHT);
//        model.getLegend().setVerticalAlign(Align.TOP);
//        model.getLegend().setX(-40);
//        model.getLegend().setY(80);
//        model.getLegend().setFloating(true);
//        model.getLegend().setBorderWidth(1);
//        model.getLegend().setBackgroundColor("((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF')");
//        model.getLegend().setShadow(true);

        //Setup serie
        model.setSeries(new Series());
        model.getSeries().setPieSerie(new PieSerie());
        model.getSeries().getPieSerie().setName("Non conformités");
        model.getSeries().getPieSerie().setColorByPoint(true);
        model.getSeries().getPieSerie().setData(new ArrayList<>());

        Iterator<IsmNcrstate> iterState = ismNcrstateController.getItems().iterator();
        int i = 0;
        while (iterState.hasNext()) {
            IsmNcrstate ncState = iterState.next();
            Data data = new Data(ncState.getStatename(), nonConformiteRequestController.countByState(ncState.getIstate()));
            if (i == 0) {
                data.setSliced(true);
                data.setSelected(true);
            }
            model.getSeries().getPieSerie().getData().add(data);
        }
        nonConformiteModel = model;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Getter / Setter
    ///
    ////////////////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }

    public void setIsmNcrstateController(IsmNcrstateController ismNcrstateController) {
        this.ismNcrstateController = ismNcrstateController;
    }

    public void setNonConformiteRequestController(NonConformiteRequestController nonConformiteRequestController) {
        this.nonConformiteRequestController = nonConformiteRequestController;
    }

}
