/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.ism.entities.app.IsmNcrstate;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.entities.smq.Processus;
import org.ism.jsf.app.IsmNcrstateController;
import org.ism.jsf.smq.nc.NonConformiteRequestController;
import org.ism.jsf.smq.ProcessusController;

/**
 *
 * @author r.hendrick
 */
@Named(value = "filterNCRequestView")
@ViewScoped
public class FilterNCRequestView implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProcessusController processusCtrl;
    private List<Processus> processus;

    private IsmNcrstateController ismNcrstateCtrl;
    private List<IsmNcrstate> ismNcrstate;

    private NonConformiteRequestController nonConformiteRequestCtrl;
    private List<NonConformiteRequest> nonConformiteRequest;
    private List<NonConformiteRequest> nonConformiteRequestFiltered;

    /**
     * Creates a new instance of FilterNCRequestView
     */
    public FilterNCRequestView() {
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
        processus = processusCtrl.getItems();

        ismNcrstateCtrl = (IsmNcrstateController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ismNcrstateController");
        ismNcrstate = ismNcrstateCtrl.getItems();

        nonConformiteRequestCtrl = (NonConformiteRequestController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "nonConformiteRequestController");
        nonConformiteRequest = nonConformiteRequestCtrl.getItems();
    }

    public List<Processus> getProcessus() {
        return processus;
    }

    public void setProcessus(List<Processus> processus) {
        this.processus = processus;
    }

    public List<NonConformiteRequest> getNonConformiteRequest() {
        return nonConformiteRequest;
    }

    public void setNonConformiteRequest(List<NonConformiteRequest> nonConformiteRequest) {
        this.nonConformiteRequest = nonConformiteRequest;
    }

    public List<NonConformiteRequest> getNonConformiteRequestFiltered() {
        return nonConformiteRequestFiltered;
    }

    public void setNonConformiteRequestFiltered(List<NonConformiteRequest> nonConformiteRequestFiltered) {
        this.nonConformiteRequestFiltered = nonConformiteRequestFiltered;
    }

    public List<IsmNcrstate> getIsmNcrstate() {
        return ismNcrstate;
    }

    public void setIsmNcrstate(List<IsmNcrstate> ismNcrstate) {
        this.ismNcrstate = ismNcrstate;
    }

}
