/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.ism.entities.DocExplorer;
import org.ism.entities.DocType;
import org.ism.entities.Processus;
import org.ism.jsf.DocExplorerController;
import org.ism.jsf.DocTypeController;
import org.ism.jsf.ProcessusController;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewTabManager")
@ViewScoped
public class ViewTabManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProcessusController processusCtrl;
    private List<Processus> processus;
    private List<Processus> processusFiltered;

    private DocExplorerController docExplorerCtrl;
    private List<DocExplorer> docExplorer;
    private List<DocExplorer> docExplorerFiltered;

    private DocTypeController docTypeCtrl;
    private List<DocType> docType;
    private List<DocType> docTypeFiltered;


    /*
    @ManagedProperty("#{processusController}")
    private ProcessusController     processusCtrl;
     */
    /**
     * Creates a new instance of FilterNCRequestView
     */
    public ViewTabManager() {
    }

    /**
     * Init. processus controller
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Setup staff controller
        processusCtrl = (ProcessusController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "processusController");
        processus = processusCtrl.getItemsByLastChanged();

        docExplorerCtrl = (DocExplorerController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docExplorerController");
        docExplorer = docExplorerCtrl.getItems();

        docTypeCtrl = (DocTypeController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docTypeController");
        docType = docTypeCtrl.getItemsByLastChanged();

    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<Processus> getProcessus() {
        return processus;
    }

    public void setProcessus(List<Processus> processus) {
        this.processus = processus;
    }

    public List<Processus> getProcessusFiltered() {
        return processusFiltered;
    }

    public void setProcessusFiltered(List<Processus> processusFiltered) {
        this.processusFiltered = processusFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<DocExplorer> getDocExplorer() {
        return docExplorer;
    }

    public void setDocExplorer(List<DocExplorer> docExplorer) {
        this.docExplorer = docExplorer;
    }

    public List<DocExplorer> getDocExplorerFiltered() {
        return docExplorerFiltered;
    }

    public void setDocExplorerFiltered(List<DocExplorer> docExplorerFiltered) {
        this.docExplorerFiltered = docExplorerFiltered;
    }

    /**
     * *************************************************************************
     * @return
     * *************************************************************************
     */
    public List<DocType> getDocType() {
        return docType;
    }

    public void setDocType(List<DocType> docType) {
        this.docType = docType;
    }

    public List<DocType> getDocTypeFiltered() {
        return docTypeFiltered;
    }

    public void setDocTypeFiltered(List<DocType> docTypeFiltered) {
        this.docTypeFiltered = docTypeFiltered;
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     *
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public boolean handleDateRangeFilters(Object value, Object filter, Locale locale) throws ParseException {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }

        //{"start":"2016-04-18","end":"2016-05-31"}
        if (!filterText.contains("start")) {
            return false;
        }
        String strDate = filterText;
        strDate = strDate.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date begin = format.parse(dates[0]);
        Date end = format.parse(dates[1]);

        //Extend limite in order to make it containt
        Calendar calValue = Calendar.getInstance();
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calValue.setTime((Date) value);
        calBegin.setTime((Date) begin);
        calEnd.setTime((Date) end);
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        calEnd.add(Calendar.DAY_OF_MONTH, +1);
        begin = calBegin.getTime();
        end = calEnd.getTime();

        //Check contain
        if (value instanceof Date) {
            Date date = (Date) value;
            if (date.before(begin) && !date.equals(begin)) {
                return false;
            }
            if (date.after(end) && !date.equals(end)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Cette méthode permet de rafraîchir le résultat en cas de modifaction et 
     * ou suppression
     */
    public void handleTableChanges() {
        processus = processusCtrl.getItemsByLastChanged();

        docExplorer = docExplorerCtrl.getItems();

        docType = docTypeCtrl.getItemsByLastChanged();
    }
    
    
    /**
     * Use to update datalist when destroy occured
     * @param ctrl 
     */
    public void handleDestroy(String ctrl){
        if(ctrl.matches("processus")){
            processusCtrl.destroy();
            processus = processusCtrl.getItemsByLastChanged();
        }else if(ctrl.matches("docType")){
            docTypeCtrl.destroy();
            docType = docTypeCtrl.getItemsByLastChanged();
        }
        
    }
}
