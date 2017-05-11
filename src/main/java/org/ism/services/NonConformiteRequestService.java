/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.ism.charts.model.ChartModel;
import org.ism.charts.model.ChartSerie;
import org.ism.charts.model.ChartSerieData;
import org.ism.charts.model.properties.Align;
import org.ism.charts.model.properties.ChartType;
import org.ism.domain.NonConformiteRequestDomain;
import org.ism.entities.app.IsmNcrstate;
import org.ism.entities.smq.nc.NonConformiteActions;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.jsf.smq.nc.NonConformiteActionsController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.jsf.util.JsfUtil;
import org.ism.view.ViewUtil;

/**
 * <h1>NonConformiteRequestService</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "nonConformiteRequestService")
@RequestScoped
public class NonConformiteRequestService {

    private NonConformiteRequestDomain nonConformiteRequestDomain;

    @Inject
    NonConformiteRequestController nonConformiteRequestController;
    @Inject
    NonConformiteActionsController nonConformiteActionsController;


    public void setYear(Integer year) {
        nonConformiteRequestDomain.setYear(year);
    }

    public Integer getYear() {
        return nonConformiteRequestDomain.getYear();
    }
    
    

    public NonConformiteRequestDomain getNonConformiteRequestDomain() {
        return nonConformiteRequestDomain;
    }

    public void setNonConformiteRequestDomain(NonConformiteRequestDomain nonConformiteRequestDomain) {
        this.nonConformiteRequestDomain = nonConformiteRequestDomain;
    }

    @PostConstruct
    public void init() {
        nonConformiteRequestDomain = new NonConformiteRequestDomain();
        prepare();
    }

    public void prepare() {
        nonConformiteRequestDomain.setItemsCounterCreated(counterCreate(getYear()));
        nonConformiteRequestDomain.setItemsCounterRequest(counterApprouved(getYear()));
        nonConformiteRequestDomain.setItemsCounterProcessing(counterProcessing(getYear()));
        nonConformiteRequestDomain.setItemsCounterFinished(counterStateChange("D", getYear()));
        nonConformiteRequestDomain.setItemsCounterCanceled(counterStateChange("E", getYear()));
        createNcLineModel();
    }

    public List<Integer> counterCreate(Integer year) {
        List<Integer> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            //JsfUtil.out("From : " + from.toString() + "  To : " + to.toString());
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsCreateInRange(from, to);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterApprouved(Integer year) {
        List<Integer> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            //JsfUtil.out("From : " + from.toString() + "  To : " + to.toString());
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsApprouvedInRange(from, to);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterProcessing(Integer year) {
        List<Integer> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            //JsfUtil.out("From : " + from.toString() + "  To : " + to.toString());
            List<NonConformiteActions> mlst = nonConformiteActionsController.getItemsCreateInRange(from, to);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterState(String state, Integer year) {
        List<Integer> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            JsfUtil.out("From : " + from.toString() + "  To : " + to.toString());
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsStateFromTo(state, from, to);
            if (mlst != null) {
                lst.add(mlst.size());
                JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    /**
     *
     * @param state one of D or E
     * @param year
     * @return
     */
    public List<Integer> counterStateChange(String state, Integer year) {
        List<Integer> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            //JsfUtil.out("From : " + from.toString() + "  To : " + to.toString());
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsStateChangeInRange(state, from, to);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    
    public List<Double> IntToDoubleList(List<Integer> lst){
        List<Double> l = new ArrayList<>();
        Iterator<Integer> iter = lst.iterator();
        while(iter.hasNext()){
            l.add(1.00*iter.next());
        }
        return l;
    }
    
    
    /**
     * <h2>createNcStateLineModel</h2>
     *
     * <h3>Description</h3>
     * This method create the new method
     */
    private void createNcLineModel() {
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.LINE);
        model.getChart().setPlotBackgroundCorlor(null);
        model.getChart().setPlotBorderWidth(null);
        model.getChart().setPlotShadow(false);
        // Seutp Title
        model.getTitle().setText("Evolution des non conformités " + getYear());
        model.getTitle().setX(-20);
        // Setup xAxis
        List<String> xAxis = new ArrayList<String>();
        Collections.addAll(xAxis, "Jan", "Fev", "Mar", "Avr", "Mai", "Jui", "Juil", "Auo", "Sep", "Oct", "Nov", "Dec");
        model.getxAxis().setCategories(xAxis);
        // Setup yAxis
        model.getyAxis().getTitle().setText("Nombre");
        model.getyAxis().getPlotLines().setValue(0);
        model.getyAxis().getPlotLines().setWidth(1);
        model.getyAxis().getPlotLines().setColor("#808080");
        // Setup Tooltip
        //model.getTooltip().setPointFormat("<b>{point.percentage:.1f}%</b>");
        // Setup plotOptions
        model.getPlotOptions().setAllowPointSelect(true);
        model.getPlotOptions().setCursor("pointer");
        model.getPlotOptions().getDataLabels().setEnabled(true);
        //model.getPlotOptions().getDataLabels().setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        model.getPlotOptions().getDataLabels().setStyle("color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'");
        
        // Setup Legend
        model.getLegend().setLayout(Align.VERTICAL);
        model.getLegend().setAlign(Align.RIGHT);
        model.getLegend().setVerticalAlign(Align.MIDDLE);
        model.getLegend().setBorderWidth(0);
        
        // Serie Create
        ChartSerie serieCreate = new ChartSerie("Créée");
        serieCreate.add(new ChartSerieData(IntToDoubleList(nonConformiteRequestDomain.getItemsCounterCreated())));
        model.addSerie(serieCreate);

        // Serie Approuved
        ChartSerie serieApprouved = new ChartSerie("En attente de solution");
        serieApprouved.add(new ChartSerieData(IntToDoubleList(nonConformiteRequestDomain.getItemsCounterRequest())));
        model.addSerie(serieApprouved);

        // Serie Processing
        ChartSerie serieProcessing = new ChartSerie("En cours");
        serieProcessing.add(new ChartSerieData(IntToDoubleList(nonConformiteRequestDomain.getItemsCounterProcessing())));
        model.addSerie(serieProcessing);

        // Serie Finished
        ChartSerie serieFinished = new ChartSerie("Terminée");
        serieFinished.add(new ChartSerieData(IntToDoubleList(nonConformiteRequestDomain.getItemsCounterFinished())));
        model.addSerie(serieFinished);

        // Serie Cancel
        ChartSerie serieCanceled = new ChartSerie("Annulée");
        serieCanceled.add(new ChartSerieData(IntToDoubleList(nonConformiteRequestDomain.getItemsCounterCanceled())));
        model.addSerie(serieCanceled);
        
        
        nonConformiteRequestDomain.setNcStateLineModel(model);

    }
}
