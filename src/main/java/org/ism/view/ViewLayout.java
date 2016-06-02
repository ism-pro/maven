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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.ism.jsf.util.JsfUtil;
import org.primefaces.component.layout.Layout;
import org.primefaces.component.layout.LayoutUnit;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent;

@ManagedBean
@SessionScoped
public class ViewLayout implements Serializable {

    private static final long serialVersionUID = 20120925L;

    private Layout layout;
    private LayoutUnit northUnit;
    private LayoutUnit westUnit;
    private LayoutUnit eastUnit;
    private LayoutUnit southUnit;
    private LayoutUnit centerUnit;

    @PostConstruct
    protected void initialize() {
        // General setup
        Integer gutter = 4;             //!< 2px
        Integer collapseSize = 6;      //!< 20px
        String effect = "fade";         //!< effect : blind, bounce, clip, drop, explode, fold, highlight, puff, pulsate, scale, shake, size and slide.
        String effectSpeed = "1500";    //!< ms effect

        // Layout
        layout = new Layout();
        layout.setFullPage(true);
        layout.setStateful(true);

        // NorthUnit
        {
            northUnit = new LayoutUnit();
            northUnit.setSize(String.valueOf(153));
            northUnit.setResizable(true);
            northUnit.setClosable(true);
            northUnit.setCollapsible(true);
            northUnit.setMinSize(152);
            northUnit.setMaxSize(200);
            northUnit.setGutter(gutter);
            //>>> default
            northUnit.setVisible(true);
            northUnit.setCollapsed(false);
            northUnit.setCollapseSize(collapseSize);
            northUnit.setEffect(effect);
            northUnit.setEffectSpeed(effectSpeed);
        }
        // WestUnit
        {
            westUnit = new LayoutUnit();
            westUnit.setSize(String.valueOf(200));
            westUnit.setResizable(true);
            westUnit.setClosable(true);
            westUnit.setCollapsible(true);
            westUnit.setMinSize(150);
            westUnit.setMaxSize(200);
            westUnit.setGutter(gutter);
            //>>> default
            westUnit.setVisible(true);
            westUnit.setCollapsed(false);
            westUnit.setCollapseSize(collapseSize);
            westUnit.setEffect(effect);
            westUnit.setEffectSpeed(effectSpeed);
        }

        // EastUnit
        {
            eastUnit = new LayoutUnit();
            eastUnit.setSize(String.valueOf(200));
            eastUnit.setResizable(true);
            eastUnit.setClosable(true);
            eastUnit.setCollapsible(true);
            eastUnit.setMinSize(150);
            eastUnit.setMaxSize(200);
            eastUnit.setGutter(gutter);
            //>>> default
            eastUnit.setVisible(true);
            eastUnit.setCollapsed(true);
            eastUnit.setCollapseSize(collapseSize);
            eastUnit.setEffect(effect);
            eastUnit.setEffectSpeed(effectSpeed);
        }

        // SouthUnit
        {
            southUnit = new LayoutUnit();
            southUnit.setSize(String.valueOf(50));
            southUnit.setResizable(true);
            southUnit.setClosable(true);
            southUnit.setCollapsible(true);
            southUnit.setMinSize(30);
            southUnit.setMaxSize(200);
            southUnit.setGutter(gutter);
            //>>> default
            southUnit.setVisible(false);
            southUnit.setCollapsed(true);
            southUnit.setCollapseSize(collapseSize);
            southUnit.setEffect(effect);
            southUnit.setEffectSpeed(effectSpeed);
        }

        // CenterUnit
        centerUnit = new LayoutUnit();

    }

    public void handleInitLayout() {
        initialize();
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     *
     * @param event
     */
    public void handleToggle(ToggleEvent event) {
        LayoutUnit unit = ((LayoutUnit) event.getComponent());
        if (null != unit.getPosition()) {
            switch (unit.getPosition()) {
                case "north":
                    northUnit.setCollapsed(unit.isCollapsed());
                    break;
                case "west":
                    westUnit.setCollapsed(unit.isCollapsed());
                    break;
                case "east":
                    eastUnit.setCollapsed(unit.isCollapsed());
                    break;
                case "south":
                    southUnit.setCollapsed(unit.isCollapsed());
                    break;
                default:
                    break;
            }
        }
        /*
        JsfUtil.addSuccessMessage("Layout Pane resized"
                + "Position:" + unit.getPosition() + ", isVisibility = "
                + event.getVisibility());*/
    }

    public void handleClose(CloseEvent event) {
        LayoutUnit unit = ((LayoutUnit) event.getComponent());
        if (null != unit.getPosition()) {
            switch (unit.getPosition()) {
                case "north":
                    //northUnit.setClosable(unit.ge);
                    break;
                case "west":
                    //westUnit.setCollapsed(unit.isCollapsed());
                    break;
                case "east":
                    //eastUnit.setCollapsed(unit.isCollapsed());
                    break;
                case "south":
                    //southUnit.setCollapsed(unit.isCollapsed());
                    break;
                default:
                    break;
            }
        }
        /*
        JsfUtil.addSuccessMessage("Layout Pane resized"
                + "Position:" + unit.getPosition() + ", Closed = "
                );*/
    }

    public void handleResize(ResizeEvent event) {
        LayoutUnit unit = ((LayoutUnit) event.getComponent());
        if (null != unit.getPosition()) {
            switch (unit.getPosition()) {
                case "north":
                    northUnit.setSize(unit.getSize());
                    break;
                case "west":
                    westUnit.setSize(unit.getSize());
                    break;
                case "east":
                    eastUnit.setSize(unit.getSize());
                    break;
                case "south":
                    southUnit.setSize(unit.getSize());
                    break;
                default:
                    break;
            }
        }
        /*
        JsfUtil.addSuccessMessage("Layout Pane resized"
                + "Position:" + unit.getPosition() + ", new width = "
                + event.getWidth() + "px, new height = " + event.getHeight() + "px");*/
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public LayoutUnit getNorthUnit() {
        return northUnit;
    }

    public void setNorthUnit(LayoutUnit northUnit) {
        this.northUnit = northUnit;
    }

    public LayoutUnit getWestUnit() {
        return westUnit;
    }

    public void setWestUnit(LayoutUnit westUnit) {
        this.westUnit = westUnit;
    }

    public LayoutUnit getEastUnit() {
        return eastUnit;
    }

    public void setEastUnit(LayoutUnit eastUnit) {
        this.eastUnit = eastUnit;
    }

    public LayoutUnit getSouthUnit() {
        return southUnit;
    }

    public void setSouthUnit(LayoutUnit southUnit) {
        this.southUnit = southUnit;
    }

    public LayoutUnit getCenterUnit() {
        return centerUnit;
    }

    public void setCenterUnit(LayoutUnit centerUnit) {
        this.centerUnit = centerUnit;
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
}
