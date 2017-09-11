/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import org.ism.charts.model.ChartModel;
import org.ism.charts.model.axis.Crosshair;
import org.ism.charts.model.axis.PlotLines;
import org.ism.charts.model.properties.Align;
import org.ism.charts.model.properties.ChartType;
import org.ism.charts.model.properties.DataLabels;
import org.ism.charts.model.series.Data;
import org.ism.charts.model.series.Legend;
import org.ism.charts.model.series.PlotOptions;
import org.ism.charts.model.series.Series;
import org.ism.charts.model.series.type.LineSerie;
import org.ism.domain.NonConformiteRequestDomain;
import org.ism.entities.smq.Processus;
import org.ism.entities.smq.nc.NonConformiteActions;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.jsf.smq.ProcessusController;
import org.ism.jsf.smq.nc.NonConformiteActionsController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.jsf.util.JsfUtil;
import org.ism.view.util.ViewUtil;

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
    @Inject
    ProcessusController processusController;

    private List<Integer> itemsCreatedOnMonth = new ArrayList<>();
    private List<Integer> itemsApprouvedOnMonth = new ArrayList<>();
    private List<Integer> itemsProcessingOnMonth = new ArrayList<>();
    private List<Integer> itemsFinishedOnMonth = new ArrayList<>();
    private List<Integer> itemsCanceledOnMonth = new ArrayList<>();

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

        List<Processus> processus = processusController.getApprouvedItems();
        for (int i = 0; i < processus.size(); i++) {
            nonConformiteRequestDomain.addItemsCounterCreatedByProcessus(counterCreateByProcessus(getYear(), processus.get(i)));
            nonConformiteRequestDomain.addItemsCounterRequestByProcessus(counterApprouvedByProcessus(getYear(), processus.get(i)));
            nonConformiteRequestDomain.addItemsCounterProcessingByProcessus(counterProcessingByProcessus(getYear(), processus.get(i)));
            nonConformiteRequestDomain.addItemsCounterFinishedByProcessus(counterStateChangeByProcessus("D", getYear(), processus.get(i)));
            nonConformiteRequestDomain.addItemsCounterCanceledByProcessus(counterStateChangeByProcessus("E", getYear(), processus.get(i)));
        }
        //
        itemsCreatedOnMonth = new ArrayList<>();
        itemsApprouvedOnMonth = new ArrayList<>();
        itemsProcessingOnMonth = new ArrayList<>();
        itemsFinishedOnMonth = new ArrayList<>();
        itemsCanceledOnMonth = new ArrayList<>();
        generateMonthStateValueByProcessus(4);
    }

    public List<Object> counterCreate(Integer year) {
        List<Object> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            Integer count = nonConformiteRequestController.getCountItemsCreateInRange(from, to);
            if (count == null) {
                lst.add(0);
            } else {
                lst.add(count);
            }
        }
        return lst;
    }

    public List<Object> counterApprouved(Integer year) {
        List<Object> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            Integer count = nonConformiteRequestController.getCountItemsApprouvedInRange(from, to);
            if (count == null) {
                lst.add(0);
            } else {
                lst.add(count);
            }
        }
        return lst;
    }

    public List<Object> counterProcessing(Integer year) {
        List<Object> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            Integer count = nonConformiteActionsController.getCountItemsCreateInRange(from, to);
            if (count == null) {
                lst.add(0);
            } else {
                lst.add(count);
            }
        }
        return lst;
    }

    public List<Object> counterState(String state, Integer year) {
        List<Object> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            Integer count = nonConformiteRequestController.getCountItemsStateFromTo(state, from, to);
            if (count == null) {
                lst.add(0);
            } else {
                lst.add(count);
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
    public List<Object> counterStateChange(String state, Integer year) {
        List<Object> lst = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Date from = ViewUtil.toDate(year, month, 1, 0, 0);
            Date to;

            if (month == 12) {
                to = ViewUtil.toDate(year + 1, 1, 1, 0, 0);
            } else {
                to = ViewUtil.toDate(year, month + 1, 1, 0, 0);
            }
            Integer count = nonConformiteRequestController.getCountItemsStateChangeInRange(state, from, to);
            if (count == null) {
                lst.add(0);
            } else {
                lst.add(count);
            }
        }
        return lst;
    }

    /**
     * =========================================================================
     *
     *
     * =========================================================================
     */
    /**
     *
     * @param year
     * @param processus
     * @return
     */
    public List<Integer> counterCreateByProcessus(Integer year, Processus processus) {
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
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsCreateInRangeByProcessus(from, to, processus);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterApprouvedByProcessus(Integer year, Processus processus) {
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
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsApprouvedInRangeByProcessus(from, to, processus);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterProcessingByProcessus(Integer year, Processus processus) {
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
            List<NonConformiteActions> mlst = nonConformiteActionsController.getItemsCreateInRangeByProcessus(from, to, processus);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    public List<Integer> counterStateByProcessus(String state, Integer year, Processus processus) {
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
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsStateFromToByProcessus(state, from, to, processus);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
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
    public List<Integer> counterStateChangeByProcessus(String state, Integer year, Processus processus) {
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
            List<NonConformiteRequest> mlst = nonConformiteRequestController.getItemsStateChangeInRangeByProcessus(state, from, to, processus);
            if (mlst != null) {
                lst.add(mlst.size());
                //JsfUtil.out("Month " + month + " is not empty");
            } else {
                lst.add(0);
            }
        }
        return lst;
    }

    /**
     * Methodd permettand de convertir une list en entier en double method utile
     * pour utilisation des graphique
     *
     * @param integerList list d'entier à convertir en double
     * @return liste des entiers ayant été converti en double
     */
    public List<Double> IntToDoubleList(List<Integer> integerList) {
        List<Double> l = new ArrayList<>();
        Iterator<Integer> iter = integerList.iterator();
        while (iter.hasNext()) {
            l.add(1.00 * iter.next());
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
        // Managing Chart
        ChartModel model = new ChartModel();
        model.getChart().setType(ChartType.LINE);
//        model.getChart().setPlotBackgroundCorlor(null);
//        model.getChart().setPlotBorderWidth(null);
//        model.getChart().setPlotShadow(false);

        // Setup Title
        model.getTitle().setText("Evolution des non conformités " + getYear());
        //model.getTitle().setX(-20);

        // Setup xAxis
        List<String> xAxis = new ArrayList<>();
        Collections.addAll(xAxis, "Jan", "Fev", "Mar", "Avr", "Mai", "Jui", "Juil", "Auo", "Sep", "Oct", "Nov", "Dec");
        model.getxAxis().setCategories(xAxis);

        // Setup yAxis
        model.getyAxis().getTitle().setText("Nombre");
        model.getyAxis().setPlotLines(new PlotLines());
        model.getyAxis().getPlotLines().setValue(0);
        model.getyAxis().getPlotLines().setWidth(1);
        model.getyAxis().getPlotLines().setColor("#808080");
        model.getyAxis().setCrosshair(new Crosshair());
        model.getyAxis().getCrosshair().setSnap(Boolean.TRUE);

        // Setup plotOptions
//        model.setPlotOptions(new PlotOptions());
//        model.getPlotOptions().setAllowPointSelect(true);
//        model.getPlotOptions().setCursor("pointer");
//        model.getPlotOptions().setDataLabels(new DataLabels());
//        model.getPlotOptions().getDataLabels().setEnabled(true);
//        model.getPlotOptions().getDataLabels().setStyle("color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'");
        // Setup Legend
        model.setLegend(new Legend());
        model.getLegend().setLayout(Align.HORIZONTAL);
        model.getLegend().setAlign(Align.CENTER);
        model.getLegend().setVerticalAlign(Align.BOTTOM);
        model.getLegend().setBorderWidth(0);

        // Serie Create
        model.setSeries(new Series());
        model.getSeries().setLineSerie(new LineSerie());
        model.getSeries().getLineSerie().setData(new ArrayList<>());
        model.getSeries().getLineSerie().getData().add(new Data("Créée", nonConformiteRequestDomain.getItemsCounterCreated()));
        model.getSeries().getLineSerie().getData().add(new Data("En attente de solution", nonConformiteRequestDomain.getItemsCounterRequest()));
        model.getSeries().getLineSerie().getData().add(new Data("En cours", nonConformiteRequestDomain.getItemsCounterProcessing()));
        model.getSeries().getLineSerie().getData().add(new Data("Terminée", nonConformiteRequestDomain.getItemsCounterFinished()));
        model.getSeries().getLineSerie().getData().add(new Data("Annulée", nonConformiteRequestDomain.getItemsCounterCanceled()));

        nonConformiteRequestDomain.setNcStateLineModel(model);

    }

    private void generateMonthStateValueByProcessus(Integer month) {
        for (int i = 0; i < processusController.getApprouvedItems().size(); i++) {
            itemsCreatedOnMonth.add(nonConformiteRequestDomain.getItemsCounterCreatedByProcessus().get(i).get(month - 1));
            itemsApprouvedOnMonth.add(nonConformiteRequestDomain.getItemsCounterRequestByProcessus().get(i).get(month - 1));
            itemsProcessingOnMonth.add(nonConformiteRequestDomain.getItemsCounterProcessingByProcessus().get(i).get(month - 1));
            itemsFinishedOnMonth.add(nonConformiteRequestDomain.getItemsCounterCreatedByProcessus().get(i).get(month - 1));
            itemsCanceledOnMonth.add(nonConformiteRequestDomain.getItemsCounterCreatedByProcessus().get(i).get(month - 1));
        }
    }

    public List<Integer> getItemsCreatedOnMonth() {
        return itemsCreatedOnMonth;
    }

    public void setItemsCreatedOnMonth(List<Integer> itemsCreatedOnMonth) {
        this.itemsCreatedOnMonth = itemsCreatedOnMonth;
    }

    public List<Integer> getItemsApprouvedOnMonth() {
        return itemsApprouvedOnMonth;
    }

    public void setItemsApprouvedOnMonth(List<Integer> itemsApprouvedOnMonth) {
        this.itemsApprouvedOnMonth = itemsApprouvedOnMonth;
    }

    public List<Integer> getItemsProcessingOnMonth() {
        return itemsProcessingOnMonth;
    }

    public void setItemsProcessingOnMonth(List<Integer> itemsProcessingOnMonth) {
        this.itemsProcessingOnMonth = itemsProcessingOnMonth;
    }

    public List<Integer> getItemsFinishedOnMonth() {
        return itemsFinishedOnMonth;
    }

    public void setItemsFinishedOnMonth(List<Integer> itemsFinishedOnMonth) {
        this.itemsFinishedOnMonth = itemsFinishedOnMonth;
    }

    public List<Integer> getItemsCanceledOnMonth() {
        return itemsCanceledOnMonth;
    }

    public void setItemsCanceledOnMonth(List<Integer> itemsCanceledOnMonth) {
        this.itemsCanceledOnMonth = itemsCanceledOnMonth;
    }

}
