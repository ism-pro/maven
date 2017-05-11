/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.ism.charts.model.ChartModel;
import org.ism.entities.smq.nc.NonConformiteRequest;

/**
 * <h1>NonConformiteRequestDomain</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
public class NonConformiteRequestDomain {

    private List<NonConformiteRequest>  itemsCreated;
    private List<NonConformiteRequest>  itemsRequest;
    private List<NonConformiteRequest>  itemsProcessing;
    private List<NonConformiteRequest>  itemsFinished;
    private List<NonConformiteRequest>  itemsCanceled;
    
    private List<Integer>  itemsCounterCreated;
    private List<Integer>  itemsCounterRequest;
    private List<Integer>  itemsCounterProcessing;
    private List<Integer>  itemsCounterFinished;
    private List<Integer>  itemsCounterCanceled;
    
    
    
    private Integer year = LocalDateTime.now().getYear();
    /**
     * <h1>nonConformiteModel</h1>
     * Session model containt datas to render a pie chart to illustrate number
     * of non conformite state
     */
    ChartModel ncStateLineModel = null;
    
    
    
    public void addItemCreated(NonConformiteRequest item){
        if(itemsCreated==null){
            itemsCreated = new ArrayList<>();
        }
        itemsCreated.add(item);
    }
    
    public void addItemRequest(NonConformiteRequest item){
        if(itemsRequest==null){
            itemsRequest = new ArrayList<>();
        }
        itemsRequest.add(item);
    }
    
    public void addItemProcessing(NonConformiteRequest item){
        if(itemsProcessing==null){
            itemsProcessing = new ArrayList<>();
        }
        itemsProcessing.add(item);
    }
    
    public void addItemFinished(NonConformiteRequest item){
        if(itemsFinished==null){
            itemsFinished = new ArrayList<>();
        }
        itemsFinished.add(item);
    }
    
    public void addItemCanceled(NonConformiteRequest item){
        if(itemsCanceled==null){
            itemsCanceled = new ArrayList<>();
        }
        itemsCanceled.add(item);
    }
   
    
    
    public List<NonConformiteRequest> getItemsCreated() {
        return itemsCreated;
    }

    public void setItemsCreated(List<NonConformiteRequest> itemsCreated) {
        this.itemsCreated = itemsCreated;
    }

    public List<NonConformiteRequest> getItemsRequest() {
        return itemsRequest;
    }

    public void setItemsRequest(List<NonConformiteRequest> itemsRequest) {
        this.itemsRequest = itemsRequest;
    }

    public List<NonConformiteRequest> getItemsProcessing() {
        return itemsProcessing;
    }

    public void setItemsProcessing(List<NonConformiteRequest> itemsProcessing) {
        this.itemsProcessing = itemsProcessing;
    }

    public List<NonConformiteRequest> getItemsFinished() {
        return itemsFinished;
    }

    public void setItemsFinished(List<NonConformiteRequest> itemsFinished) {
        this.itemsFinished = itemsFinished;
    }

    public List<NonConformiteRequest> getItemsCanceled() {
        return itemsCanceled;
    }

    public void setItemsCanceled(List<NonConformiteRequest> itemsCanceled) {
        this.itemsCanceled = itemsCanceled;
    }

    public List<Integer> getItemsCounterCreated() {
        return itemsCounterCreated;
    }

    public void setItemsCounterCreated(List<Integer> itemsCounterCreated) {
        this.itemsCounterCreated = itemsCounterCreated;
    }

    public List<Integer> getItemsCounterRequest() {
        return itemsCounterRequest;
    }

    public void setItemsCounterRequest(List<Integer> itemsCounterRequest) {
        this.itemsCounterRequest = itemsCounterRequest;
    }

    public List<Integer> getItemsCounterProcessing() {
        return itemsCounterProcessing;
    }

    public void setItemsCounterProcessing(List<Integer> itemsCounterProcessing) {
        this.itemsCounterProcessing = itemsCounterProcessing;
    }

    public List<Integer> getItemsCounterFinished() {
        return itemsCounterFinished;
    }

    public void setItemsCounterFinished(List<Integer> itemsCounterFinished) {
        this.itemsCounterFinished = itemsCounterFinished;
    }

    public List<Integer> getItemsCounterCanceled() {
        return itemsCounterCanceled;
    }

    public void setItemsCounterCanceled(List<Integer> itemsCounterCanceled) {
        this.itemsCounterCanceled = itemsCounterCanceled;
    }

    
    
    
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ChartModel getNcStateLineModel() {
        return ncStateLineModel;
    }

    public void setNcStateLineModel(ChartModel ncStateLineModel) {
        this.ncStateLineModel = ncStateLineModel;
    }

    
}
