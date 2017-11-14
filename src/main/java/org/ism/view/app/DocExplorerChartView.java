/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.ism.charts.model.ChartModel;
import org.ism.charts.model.axis.AxisTitle;
import org.ism.charts.model.properties.Align;
import org.ism.charts.model.properties.ChartType;
import org.ism.charts.model.properties.DataLabels;
import org.ism.charts.model.series.Data;
import org.ism.charts.model.series.Legend;
import org.ism.charts.model.series.PlotOptions;
import org.ism.charts.model.series.Series;
import org.ism.charts.model.series.ToolTip;
import org.ism.charts.model.series.type.BarSerie;
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.DocType;
import org.ism.entities.smq.Processus;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.smq.DocExplorerController;
import org.ism.jsf.smq.DocTypeController;
import org.ism.jsf.smq.ProcessusController;

/**
 * <h1>DocExplorerChartView</h1><br>
 * DocExplorerChartView class
 *
 * @author r.hendrick
 *
 */
@ManagedBean(name = "docExplorerChartView")
@SessionScoped
public class DocExplorerChartView implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{processusController}")
    private ProcessusController processusController;

    @ManagedProperty(value = "#{docTypeController}")
    private DocTypeController docTypeController;

    @ManagedProperty(value = "#{docExplorerController}")
    private DocExplorerController docExplorerController;

    @ManagedProperty(value = "#{staffAuthController}")
    private StaffAuthController staffAuthController;
    /**
     * Bar chart document model define the chart representing horizontaly the
     * number of document created
     */
    private ChartModel barChartDocModel = null;

    /**
     * It is a model of document for the user processus only
     */
    private ChartModel barChartDocStaffModel = null;

    /**
     *
     */
    @PostConstruct
    public void init() {
        createBarChartDocumentModel();
        createBarChartUserDocumentModel();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Specific
    ///
    ////////////////////////////////////////////////////////////////////////////
    private void createBarChartDocumentModel() {
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.COLUMN);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);

        // Setup Titles
        model.getTitle().setText("Documentation");
        model.getSubTitle().setText("Répartition par processus");

        // Managing XAxis
        List<String> xAxis = processusController.getApprouvedItemsAsString();
        model.getxAxis().setCategories(xAxis);
        AxisTitle xtitle = new AxisTitle();
        xtitle.setText("Processus");
        model.getxAxis().setTitle(xtitle);

        // Managing YAxis
        model.getyAxis().setMin(0);
        model.getyAxis().getTitle().setAlign(AxisTitle.HIGH);

        // Managing ToolTip
        model.setToolTip(new ToolTip());
        //model.getToolTip().setHeaderFormat("{point.name}{series.className}");
        model.getToolTip().setFollowPointer(true);
        //model.getToolTip().setValueSuffix(" {point.name}");

        // Setup plotOptions
        model.setPlotOptions(new PlotOptions());
        model.getPlotOptions().setType(ChartType.COLUMN);
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().setDataLabels(new DataLabels());
        model.getPlotOptions().getDataLabels().setEnabled(true);
        //model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.y:.1f}");
        model.getPlotOptions().getDataLabels().setStyle("{color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'}");

        // Setup Legend
        model.setLegend(new Legend());
        model.getLegend().setLayout(Align.VERTICAL);
        model.getLegend().setAlign(Align.RIGHT);
        model.getLegend().setVerticalAlign(Align.TOP);
//        model.getLegend().setX(-40);
//        model.getLegend().setY(80);
        model.getLegend().setFloating(true);
        model.getLegend().setBorderWidth(1);
        model.getLegend().setBackgroundColor("((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF')");
        model.getLegend().setShadow(true);

        // Managing Series
        model.setSeries(new Series());
        model.getSeries().setBarSerie(new BarSerie());
        model.getSeries().getBarSerie().setData(new ArrayList<>());

        for (DocType docType : docTypeController.getItems()) { // For each document type
            List<Object> serie = new ArrayList<>();
            for (Processus processus : processusController.getApprouvedItems()) { // For each processus
                List<DocExplorer> docs = docExplorerController.getItemsByProcessusAndType(processus, docType);
                if (docs == null || docs.isEmpty()) {
                    serie.add(0);
                } else {
                    serie.add(docs.size());
                }
            }
            model.getSeries().getBarSerie().getData().add(new Data(docType.getDctDesignation(), serie));
        }

        barChartDocModel = model;
    }

    private void createBarChartUserDocumentModel() {
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.COLUMN);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);

        // Setup Titles
        model.getTitle().setText(staffAuthController.getStaff().getStProcessus().getPDesignation());
        //model.getSubTitle().setText("Répartition par processus");

        // Managing XAxis
        List<String> xAxis = new ArrayList<>();
        for (DocType type : docTypeController.getItems()) {
            xAxis.add(type.getDctType());// + " - " + type.getDctDesignation());
        }
        model.getxAxis().setCategories(xAxis);
        AxisTitle xtitle = new AxisTitle();
        xtitle.setText("Type");
        model.getxAxis().setTitle(xtitle);

        // Managing YAxis
//        model.getyAxis().setMin(0);
//        model.getyAxis().getTitle().setAlign(AxisTitle.HIGH);

        // Managing ToolTip
        model.setToolTip(new ToolTip());
        //model.getToolTip().setHeaderFormat("{point.name}{series.className}");
        model.getToolTip().setFollowPointer(true);
        //model.getToolTip().setValueSuffix(" {point.name}");

        // Setup plotOptions
        model.setPlotOptions(new PlotOptions());
        model.getPlotOptions().setType(ChartType.COLUMN);
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().setDataLabels(new DataLabels());
        model.getPlotOptions().getDataLabels().setEnabled(true);
        //model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.y:.1f}");
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

        // Managing Series
        model.setSeries(new Series());
        model.getSeries().setBarSerie(new BarSerie());
        model.getSeries().getBarSerie().setData(new ArrayList<>());

        List<Object> serie = new ArrayList<>();
        for (DocType docType : docTypeController.getItems()) { // For each document type
            
            List<DocExplorer> docs = docExplorerController.getItemsByProcessusAndType(staffAuthController.getStaff().getStProcessus(), docType);
            if (docs == null || docs.isEmpty()) {
                serie.add(0);
            } else {
                serie.add(docs.size());
            }
            
        }
        model.getSeries().getBarSerie().getData().add(new Data(staffAuthController.getStaff().getStProcessus().getPProcessus(), serie));

        barChartDocStaffModel = model;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// Getter/Setter
    ///
    ////////////////////////////////////////////////////////////////////////////
    public ChartModel getBarChartDocModel() {
        return barChartDocModel;
    }

    public void setBarChartDocModel(ChartModel barChartDocModel) {
        this.barChartDocModel = barChartDocModel;
    }

    public ChartModel getBarChartDocStaffModel() {
        return barChartDocStaffModel;
    }

    public void setBarChartDocStaffModel(ChartModel barChartDocStaffModel) {
        this.barChartDocStaffModel = barChartDocStaffModel;
    }
    
    

    ////////////////////////////////////////////////////////////////////////////
    /// Manage Injection
    ///
    ////////////////////////////////////////////////////////////////////////////
    public void setProcessusController(ProcessusController processusController) {
        this.processusController = processusController;
    }

    public void setDocTypeController(DocTypeController docTypeController) {
        this.docTypeController = docTypeController;
    }

    public void setDocExplorerController(DocExplorerController docExplorerController) {
        this.docExplorerController = docExplorerController;
    }

    public void setStaffAuthController(StaffAuthController staffAuthController) {
        this.staffAuthController = staffAuthController;
    }

}
