/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.domain.exporter;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <h1>Document</h1><br>
 * Document class
 *
 * @author r.hendrick
 *
 */
public class Document {


    private String filename = "";

    private float marginLeft = -50.0f;
    private float marginRight = -50.0f;
    private float marginTop = 10.0f;
    private float marginBottom = 10.0f;

    private Boolean landscape = false;

    private PageSizeName pageSize = new PageSizeName("A4", PageSize.A4);
    private List<PageSizeName> pageSizes = new ArrayList<>();

    public Document() {
        Collections.addAll(pageSizes,
                new PageSizeName("A4", PageSize.A4),
                new PageSizeName("A3", PageSize.A3),
                new PageSizeName("A2", PageSize.A2),
                new PageSizeName("A1", PageSize.A1),
                new PageSizeName("A0", PageSize.A0)
                );
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    

    public float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public PageSizeName getPageSize() {
        return pageSize;
    }

    public void setPageSize(PageSizeName pageSize) {
        this.pageSize = pageSize;
    }



    public Boolean getLandscape() {
        return landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    public List<PageSizeName> getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(List<PageSizeName> pageSizes) {
        this.pageSizes = pageSizes;
    }

    
}
