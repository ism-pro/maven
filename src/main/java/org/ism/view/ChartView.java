/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.ism.entities.IsmNcrstate;
import org.ism.jsf.IsmNcrstateController;
import org.ism.jsf.NonConformiteRequestController;
import org.ism.listener.SessionCounterListener;
import org.primefaces.event.ItemSelectEvent;
 
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
 
 

@ManagedBean(name = "chartView")
public class ChartView implements Serializable {
 
    private org.ism.jsf.NonConformiteRequestController ncRequestCtrl = new org.ism.jsf.NonConformiteRequestController();  
    private IsmNcrstateController ncStateCtrl = new IsmNcrstateController(); 
    
    
    private BarChartModel barModel;
    private PieChartModel pieModel1;
    private PieChartModel pieSMQNCStateModel;
 
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Get Bean
        ncRequestCtrl = (org.ism.jsf.NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
        ncRequestCtrl.prepareCreate();
        
        ncStateCtrl = (IsmNcrstateController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ismNcrstateController");
        ncStateCtrl.prepareCreate();
        
        createBarModels();
        createPieModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
    
    public PieChartModel getPieSMQNCStateModel(){
        return pieSMQNCStateModel;
    }
 

    
    private void createBarModels() {
        createBarModel();
    }
    
    private void createPieModels() {
        createPieModel1();
        createPieSMQNCStateModel();
    }
    
    
    
    
    
    
    
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
     
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }
 
    
     
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        Integer cntActSession = SessionCounterListener.getTotalActiveSessionsAuthenticated();
        Integer cntUnActSession = SessionCounterListener.getTotalActiveSession()-cntActSession;
        
        pieModel1.set("Actif", cntActSession);
        pieModel1.set("Inactif", cntUnActSession);

        pieModel1.setTitle("Taux de session actif");
        pieModel1.setLegendPosition("s");
    }
 
    private void createPieSMQNCStateModel() {
        pieSMQNCStateModel = new PieChartModel();
        
        Iterator<IsmNcrstate> iterState = ncStateCtrl.getItems().iterator();
        
        while(iterState.hasNext()){
            IsmNcrstate ncState = iterState.next();
            int cntNCState = ncRequestCtrl.countByState(ncState.getIstate());
            pieSMQNCStateModel.set(ncState.getStatename(), cntNCState);
        }

        pieSMQNCStateModel.setTitle("Etat de NC");
        pieSMQNCStateModel.setLegendPosition("e");
    }
    
    
    
    
    
    
    
    
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}