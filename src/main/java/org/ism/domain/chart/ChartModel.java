/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.domain.chart;

import java.util.ArrayList;
import java.util.List;
import org.highfaces.component.api.ChartSeries;
import org.highfaces.component.chartserie.ChartSerie;

/**
 *
 * @author r.hendrick
 */
public class ChartModel  {

    public enum ChartType {

        TYPE_PIE("pie"),
        TYPE_LINE("line"),
        TYPE_BAR("bar"),
        TYPE_OHLC("ohlc"),
        TYPE_DONUT("donut"),
        TYPE_BUBBLE("bubble"),
        TYPE_METERGAUGE("metergauge");

        private String text;

        ChartType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    public class ChartCartesianIntersec {
        private String label = "";
        private double doubleValue = 0.0;
        private Integer integerValue = 0;

        public ChartCartesianIntersec(String label, int value) {
            this.label = label;
            this.doubleValue = value;
            this.integerValue = value;
        }
        
        public ChartCartesianIntersec(String label, double value) {
            this.label = label;
            this.doubleValue = value;
            this.integerValue = (int) value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public void setIntegerValue(Integer integerValue) {
            this.integerValue = integerValue;
        }
        
        
        
    }
    
    public class Axes {
        private String xLabel = "xLabel";
        private String yLabel = "yLabel";
        private Double xMin = new Double(-100);
        private Double xMax = new Double(100);
        private Double yMin = new Double(-100);
        private Double yMax = new Double(100);

        public String getxLabel() {
            return xLabel;
        }

        public void setxLabel(String xLabel) {
            this.xLabel = xLabel;
        }

        public String getyLabel() {
            return yLabel;
        }

        public void setyLabel(String yLabel) {
            this.yLabel = yLabel;
        }

        public Double getxMin() {
            return xMin;
        }

        public void setxMin(Double xMin) {
            this.xMin = xMin;
        }

        public Double getxMax() {
            return xMax;
        }

        public void setxMax(Double xMax) {
            this.xMax = xMax;
        }

        public Double getyMin() {
            return yMin;
        }

        public void setyMin(Double yMin) {
            this.yMin = yMin;
        }

        public Double getyMax() {
            return yMax;
        }

        public void setyMax(Double yMax) {
            this.yMax = yMax;
        }
        
        
    }
    
    
    private ChartType type = null;
    private String title = "";
    private String curveLabel = "";
    private List<ChartCartesianIntersec> serie = new ArrayList<>();
    private Axes axes = null;
    
    public ChartType getType() {
        if(type==null)  type = ChartType.TYPE_PIE;
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }
    
    public String getTypeStr(){
        return type.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurveLabel() {
        return curveLabel;
    }

    public void setCurveLabel(String curveLabel) {
        this.curveLabel = curveLabel;
    }
    
    

    public Axes getAxes() {
        if(axes==null) axes = new Axes();
        return axes;
    }

    public void setAxes(Axes axes) {
        getAxes();
        this.axes = axes;
    }
    
    
        
    


    
    public void clearSerie(){
        serie = new ArrayList<ChartCartesianIntersec>();
    }
    
    public void add(String label, Number value){
        if(serie==null){
            clearSerie();
        }
        serie.add(new ChartCartesianIntersec(label, value.doubleValue()));
    }

    public List<ChartCartesianIntersec> getSerie() {
        return serie;
    }

    public void setSerie(List<ChartCartesianIntersec> serie) {
        this.serie = serie;
    }  

    
    
}
