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
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.ism.entities.DocExplorer;
import org.ism.entities.DocType;
import org.ism.entities.Processus;
import org.ism.jsf.DocExplorerController;
import org.ism.jsf.DocTypeController;
import org.ism.jsf.ProcessusController;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.event.SelectEvent;

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
    private DateTwin processusFieldChangedDates;
    private String processusFieldChanged;

    private DocTypeController docTypeCtrl;
    private List<DocType> docType;

    private DocExplorerController docExplorerCtrl;
    private List<DocExplorer> docExplorer;
    private List<DocExplorer> docExplorerFiltered;

    /*
    @ManagedProperty("org.ism.jsf.processusController")
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
        processusFieldChanged = "";
        // Setup staff controller
        processusCtrl = (ProcessusController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "processusController");
        processus = processusCtrl.getItemsByLastChanged();

        docTypeCtrl = (DocTypeController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docTypeController");
        docType = docTypeCtrl.getItems();

        docExplorerCtrl = (DocExplorerController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "docExplorerController");
        docExplorer = docExplorerCtrl.getItems();

    }

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

    public List<DocType> getDocType() {
        return docType;
    }

    public void setDocType(List<DocType> docType) {
        this.docType = docType;
    }

    public String getProcessusFieldChanged() {
        return processusFieldChanged;
    }

    public void setProcessusFieldChanged(String processusFieldChanged) {
        this.processusFieldChanged = processusFieldChanged;
    }
    
    
    public DateTwin getProcessusFieldChangedDates() {
        if (processusFieldChangedDates == null) {
            processusFieldChangedDates = new DateTwin();
        }
        return processusFieldChangedDates;
    }

    public void setProcessusFieldChangedDates(DateTwin processusFilteredDateChanged) {
        getProcessusFieldChangedDates();
        this.processusFieldChangedDates = processusFilteredDateChanged;
    }

    public String getProcessusFilteredFieldChangedRange() {
        return processusFieldChanged;
    }

    public void setProcessusFilteredFieldChangedRange(String processusFilteredFieldChangedRange) {
        this.processusFieldChanged = processusFilteredFieldChangedRange;
    }

    
    
    
    
    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    public void handleProcessusFilterChanged(SelectEvent e) {
        getProcessusFieldChangedDates();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Formatter formatter = new Formatter();
        formatter.format("%1$2s;%2$2s",
                df.format(processusFieldChangedDates.begin),
                df.format(processusFieldChangedDates.end));
        processusFieldChanged = formatter.toString();
    }

    
    
    public boolean handleDateRangeFilters(Object value, Object filter, Locale locale) throws ParseException {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        
        
        if(!filterText.contains(";")){
            return false;
        }
        
        String dates[] = filterText.split(";");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        Date begin = format.parse(dates[0]);
        Date end = format.parse(dates[1]);
        
        if (value instanceof Date) {
            Date date = (Date) value;
            if (date.before(begin)) {
                return false;
            }
            if (date.after(end)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public class DateTwin {

        private Date begin = new Date();
        private Date end = new Date();

        DateTwin() {
        }

        public Date getBegin() {
            if (begin == null) {
                begin = new Date();
            }
            return begin;
        }

        public void setBegin(Date begin) {
            if (begin == null) {
                begin = new Date();
            }
            this.begin = begin;
        }

        public Date getEnd() {
            if (end == null) {
                end = new Date();
            }
            return end;
        }

        public void setEnd(Date end) {
            if (end == null) {
                end = new Date();
            }
            this.end = end;
        }

    }
}
