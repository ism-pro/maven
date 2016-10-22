/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ism.domain.chart.ChartModel;
import org.ism.entities.app.IsmNcrstate;
import org.ism.jsf.app.IsmNcrstateController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;

@ManagedBean(name = "mainChartView")
public class MainChartView implements Serializable {


    @Inject
    private NonConformiteRequestController nonConformiteRequestController;
    @Inject
    private IsmNcrstateController ismNcrstateController;

    private ChartModel smqStatePieModel;
    
    List<ChartModel.ChartCartesianIntersec> pieSerie = new ArrayList<ChartModel.ChartCartesianIntersec>();
    

 
    @PostConstruct
    public void init() {
        // Confirm Injection
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(nonConformiteRequestController==null){
            nonConformiteRequestController = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
            nonConformiteRequestController.prepareCreate();
        }
        if(ismNcrstateController==null){
            ismNcrstateController = (IsmNcrstateController)facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ismNcrstateController");
            ismNcrstateController.prepareCreate();
        }
        
        createPieModels();
    }

    /**
     *
     */
    private void createPieModels() {
        smqStatePieModel = new ChartModel();
        smqStatePieModel.setType(ChartModel.ChartType.TYPE_PIE);
        
        Iterator<IsmNcrstate> iterState = ismNcrstateController.getItems().iterator();
        while (iterState.hasNext()) {
            IsmNcrstate ncState = iterState.next();
            int cntNCState = nonConformiteRequestController.countByState(ncState.getIstate());
            smqStatePieModel.add(ncState.getStatename(), cntNCState);
        }
        smqStatePieModel.setTitle("Etat de NC");
    }

    public ChartModel getSmqStatePieModel() {
        return smqStatePieModel;
    }

    public void setSmqStatePieModel(ChartModel smqStatePieModel) {
        this.smqStatePieModel = smqStatePieModel;
    }

    
    
    
}
