/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.view.process.ctrl;

import java.util.Comparator;
import org.ism.entities.process.ctrl.AnalyseData;
import org.primefaces.model.SortOrder;

/**
 * <h1>DataLazySorter</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
public class DataLazySorter implements Comparator<AnalyseData> {
 
    private String sortField;
    private SortOrder sortOrder;
     
    public DataLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 


    @Override
    public int compare(AnalyseData data1, AnalyseData data2) {
        try {
            Object value1 = AnalyseData.class.getField(this.sortField).get(data1);
            Object value2 = AnalyseData.class.getField(this.sortField).get(data2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
