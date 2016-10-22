/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartModelRow;
import org.primefaces.extensions.component.gchart.model.GChartType;

/**
 *
 * @author r.hendrick
 */
@ManagedBean
@RequestScoped
public class BasicGChartView implements Serializable {

protected List<Births> boys;
    protected List<Births> girls;
     
    public List<Births> getBoys() {
        return boys;
    }           
     
    public List<Births> getGirls() {
        return girls;
    }   
     
    public BasicGChartView() {
        reload();
    }
 
    private void reload() {
        boys = new ArrayList<>();
        girls = new ArrayList<>();
        Random r = new Random();
        for (int i = 2000; i < 2010; i++) {
            boys.add(new Births(Integer.toString(i), r.nextInt(500) + 800));   
            girls.add(new Births(Integer.toString(i), r.nextInt(500) + 800));   
        }  
    }

    public class Births {

        protected String year;
        protected Integer amount;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Births() {
        }

        public Births(String year, Integer amount) {
            this.year = year;
            this.amount = amount;
        }
    }
}
