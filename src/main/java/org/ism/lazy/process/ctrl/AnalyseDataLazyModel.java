/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.lazy.process.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.sessions.process.ctrl.AnalyseDataFacade;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * <h1>AnalyseDataLazyModel</h1>
 *
 * @author r.hendrick
 */
public class AnalyseDataLazyModel extends LazyDataModel<AnalyseData> implements Serializable {

    private org.ism.sessions.process.ctrl.AnalyseDataFacade ejbFacade;

    /**
     * data source save current data value of the page
     */
    private List<AnalyseData> datasource;
    private List<SortMeta> multiSortMeta = null;
    private Map<String, Object> filters = null;

    public AnalyseDataLazyModel() {
    }

    /**
     * Default lazy model constructor
     *
     * @param facade facade to make request
     */
    public AnalyseDataLazyModel(AnalyseDataFacade facade) {
        ejbFacade = facade;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Loading request
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * This load method intend to load data only with one sort order specify on
     * one field. <br>
     * Data will be load from facade from specified first page calculate over
     * page row size
     *
     * @param first specified from which row to start getting items
     * @param pageSize specified numbers of items to read which correspond to
     * page size
     * @param sortField field to be sorted
     * @param sortOrder sort order value
     * @param filters is a map defining a couple of field and criteria of
     * filters
     * @return list of analyse data corresponding to defined criteria
     *
     */
    @Override
    public List<AnalyseData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        // Create sort order Map
        //Map<String, Object> 
        return ejbFacade.findByCriterias(first, pageSize, null, filters);

    }

    /**
     * This load method intend to load data only with multi sort order specify
     * on multiple fields. <br>
     * Data will be load from facade from specified first page calculate over
     * page row size
     *
     * @param first specified from which row to start getting items
     * @param pageSize specified numbers of items to read which correspond to
     * page size
     * @param multiSortMeta is a map defining a couple of field sort order way
     * @param filters is a map defining a couple of field and criteria of
     * filters
     * @return list of analyse data corresponding to defined criteria
     */
    @Override
    public List<AnalyseData> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

        // Restore filter if required
        if (this.multiSortMeta != null && this.multiSortMeta!=multiSortMeta) {
            multiSortMeta = this.multiSortMeta;
        }else{
            // Sorting default by date d'échantillonnage
            multiSortMeta = new ArrayList<>();
            SortMeta metaSort = new SortMeta(null, "adsampleTime", SortOrder.DESCENDING, null);
            multiSortMeta.add(metaSort);
        }
        if (this.filters != null && this.filters!=filters) {
            filters = this.filters;
        }
        // Get data
        datasource = ejbFacade.findByCriterias(first, pageSize, convertSortMeta(multiSortMeta), filters);
        // Count rows
        this.setRowCount(ejbFacade.countByCriterias(filters));

        return datasource;
    }

    /**
     * Convert SortMeta is usefuel in order to change from List<SortMeta> to Map
     * key and sortway.
     *
     * @return HashMap of the sortMeta
     */
    private Map<String, String> convertSortMeta(List<SortMeta> multiSortMeta) {
        Map<String, String> sorts = null;
        if (multiSortMeta != null) {
            for (SortMeta e : multiSortMeta) {
                if (sorts == null) {
                    sorts = new HashMap<>();
                }
                sorts.put(e.getSortField(), e.getSortOrder().toString());
            }
        }
        return sorts;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getters / Setters
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Read facade used for request
     *
     * @return analyse data facade to make jpa request
     */
    private AnalyseDataFacade getFacade() {
        return ejbFacade;
    }

    /**
     * Read row data from specified key.
     *
     * @param rowKey the corresponding key of current data
     * @return corresponding data
     */
    @Override
    public AnalyseData getRowData(String rowKey) {
        for (AnalyseData data : datasource) {
            if (data.getAdId().equals(Integer.valueOf(rowKey))) {
                return data;
            }
        }
        return null;
    }

    /**
     * Read row key of current data
     *
     * @param data current data
     * @return corresponding id key
     */
    @Override
    public Object getRowKey(AnalyseData data) {
        return data.getAdId();
    }

    public List<SortMeta> getMultiSortMeta() {
        return multiSortMeta;
    }

    public void setMultiSortMeta(List<SortMeta> multiSortMeta) {
        this.multiSortMeta = multiSortMeta;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }


}
