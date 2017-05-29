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
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.DocType;
import org.ism.entities.smq.Processus;
import org.ism.jsf.smq.DocExplorerController;
import org.ism.jsf.smq.DocTypeController;
import org.ism.jsf.smq.ProcessusController;

/**
 *
 * @author r.hendrick
 */
@Named(value = "filterSMQDocExplorerView")
@ViewScoped
public class FilterSMQDocExplorerView implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProcessusController processusCtrl;
    private List<Processus> processus;

    private DocTypeController docTypeCtrl;
    private List<DocType> docType;

    private DocExplorerController docExplorerCtrl;
    private List<DocExplorer> docExplorer;
    private List<DocExplorer> docExplorerFiltered;

    /**
     * Creates a new instance of FilterNCRequestView
     */
    public FilterSMQDocExplorerView() {
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

    public ProcessusController getProcessusCtrl() {
        return processusCtrl;
    }

    public void setProcessusCtrl(ProcessusController processusCtrl) {
        this.processusCtrl = processusCtrl;
    }

    public DocExplorerController getDocExplorerCtrl() {
        return docExplorerCtrl;
    }

    public void setDocExplorerCtrl(DocExplorerController docExplorerCtrl) {
        this.docExplorerCtrl = docExplorerCtrl;
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

    public DocTypeController getDocTypeCtrl() {
        return docTypeCtrl;
    }

    public void setDocTypeCtrl(DocTypeController docTypeCtrl) {
        this.docTypeCtrl = docTypeCtrl;
    }

    public List<DocType> getDocType() {
        return docType;
    }

    public void setDocType(List<DocType> docType) {
        this.docType = docType;
    }

}
