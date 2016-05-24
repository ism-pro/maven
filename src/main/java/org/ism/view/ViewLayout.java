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
import org.primefaces.component.layout.LayoutUnit;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent;

@ManagedBean
@ViewScoped
public class ViewLayout implements Serializable {

    private static final long serialVersionUID = 20120925L;

    private Integer northSize   = 200;      //!< North size
    private Integer westSize    = 200;      //!< West size
    private Integer eastSize    = 200;      //!< East size
    private Integer southSize   = 200;      //!< South Size
    
    
    

    @PostConstruct
    protected void initialize() {
        northSize = 200;
        westSize = 200;
        eastSize = 200;
        southSize = 200;
    }


    
    public void handleToggle(ToggleEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane Toggle",
                        "Position:" + ((LayoutUnit) event.getComponent()).getPosition());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void handleClose(CloseEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane closed",
                        "Position:" + ((LayoutUnit) event.getComponent()).getPosition());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
    public void handleResize(ResizeEvent event) {
        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "Layout Pane resized",
                        "Position:" + ((LayoutUnit) event.getComponent()).getPosition() + ", new width = "
                        + event.getWidth() + "px, new height = " + event.getHeight() + "px");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public Integer getNorthSize() {
        return northSize;
    }

    public void setNorthSize(Integer northSize) {
        this.northSize = northSize;
    }

    public Integer getWestSize() {
        return westSize;
    }

    public void setWestSize(Integer westSize) {
        this.westSize = westSize;
    }

    public Integer getEastSize() {
        return eastSize;
    }

    public void setEastSize(Integer eastSize) {
        this.eastSize = eastSize;
    }

    public Integer getSouthSize() {
        return southSize;
    }

    public void setSouthSize(Integer southSize) {
        this.southSize = southSize;
    }

    
    
    

    
    
}
