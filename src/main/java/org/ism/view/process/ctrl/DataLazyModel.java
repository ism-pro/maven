/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.process.ctrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.ism.entities.process.ctrl.AnalyseData;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * <h1>DataLazyModel</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class DataLazyModel extends LazyDataModel<AnalyseData> {

    private List<AnalyseData> datasource;

    /**
     * <p>
     * Default constructor with data to process
     * </p>
     * @param datasource Data provided
     */
    public DataLazyModel(List<AnalyseData> datasource) {
        this.datasource = datasource;
    }

    @Override
    public AnalyseData getRowData(String rowKey) {
        for (AnalyseData data : datasource) {
            if (data.getAdId().equals(rowKey)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(AnalyseData data) {
        return data.getAdId();
    }

    @Override
    public List<AnalyseData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        List<AnalyseData> datas = new ArrayList<AnalyseData>();

        //filter
        for (AnalyseData data : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(data.getClass().getField(filterProperty).get(data));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                datas.add(data);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(datas, new DataLazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = datas.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return datas.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return datas.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return datas;
        }
    }
}
