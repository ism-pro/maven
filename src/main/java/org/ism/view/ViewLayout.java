/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.extensions.component.layout.LayoutPane;
import org.primefaces.extensions.event.CloseEvent;
import org.primefaces.extensions.event.OpenEvent;
import org.primefaces.extensions.event.ResizeEvent;
import org.primefaces.extensions.model.layout.LayoutOptions;

@ManagedBean
@ViewScoped
public class ViewLayout implements Serializable {

    private static final long serialVersionUID = 20120925L;

    private LayoutOptions layoutOptions;    //!< Main Setup layout
    private LayoutOptions northLayout;      //!< West pane layout
    private LayoutOptions westLayout;       //!< West pane layout
    private LayoutOptions centerLayout;     //!< West pane layout
    private LayoutOptions eastLayout;       //!< West pane layout
    private LayoutOptions southLayout;      //!< West pane layout
    

    @PostConstruct
    protected void initialize() {
        layoutOptions = new LayoutOptions();

        // options for all panes  
        LayoutOptions panes = new LayoutOptions();
        panes.addOption("slidable", false);
        panes.addOption("resizeWhileDragging", false);
        layoutOptions.setPanesOptions(panes);

        // options for west pane  
        northLayout = new LayoutOptions();
        northLayout.addOption("size", 153);
        northLayout.addOption("maxHeight", 153);
        northLayout.addOption("minHeight", 35);
        layoutOptions.setWestOptions(westLayout);
        
        // options for west pane  
        westLayout = new LayoutOptions();
        westLayout.addOption("size", 200);
        westLayout.addOption("initClosed", true);
        layoutOptions.setWestOptions(westLayout);
        
        
        // options for center pane  
        centerLayout = new LayoutOptions();
        centerLayout.addOption("minHeight", 60);
        layoutOptions.setCenterOptions(centerLayout);

        
        
        // options for east pane  
        eastLayout = new LayoutOptions();
        eastLayout.addOption("size", 200);
        eastLayout.addOption("initClosed", true);
        layoutOptions.setEastOptions(eastLayout);

        
        // options for south pane  
        southLayout = new LayoutOptions();
        southLayout.addOption("size", 10);
        //south.addOption("initHidden", true);
        southLayout.addOption("initClosed", true);
        layoutOptions.setSouthOptions(southLayout);
    }

    
    public LayoutOptions getLayoutOptions() {
        return layoutOptions;
    }

    
    
    
    
    
    
    
    
    
    /*
    public void handleClose(CloseEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane closed",
                        "Position:" + ((LayoutPane) event.getComponent()).getPosition());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
    public void handleOpen(OpenEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane opened",
                        "Position:" + ((LayoutPane) event.getComponent()).getPosition());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
    public void handleResize(ResizeEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane resized",
                        "Position:" + ((LayoutPane) event.getComponent()).getPosition() + ", new width = "
                        + event.getWidth() + "px, new height = " + event.getHeight() + "px");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
*/

    public LayoutOptions getNorthLayout() {
        return northLayout;
    }

    public void setNorthLayout(LayoutOptions northLayout) {
        this.northLayout = northLayout;
    }

    public LayoutOptions getWestLayout() {
        return westLayout;
    }

    public void setWestLayout(LayoutOptions westLayout) {
        this.westLayout = westLayout;
    }

    public LayoutOptions getCenterLayout() {
        return centerLayout;
    }

    public void setCenterLayout(LayoutOptions centerLayout) {
        this.centerLayout = centerLayout;
    }

    public LayoutOptions getEastLayout() {
        return eastLayout;
    }

    public void setEastLayout(LayoutOptions eastLayout) {
        this.eastLayout = eastLayout;
    }

    public LayoutOptions getSouthLayout() {
        return southLayout;
    }

    public void setSouthLayout(LayoutOptions southLayout) {
        this.southLayout = southLayout;
    }
    
    
    
    
    
}
