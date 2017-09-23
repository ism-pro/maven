/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.domain.exporter;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import java.io.Serializable;

/**
 * <h1>PageSizeName</h1><br>
 * PageSizeName class
 *
 * @author r.hendrick
 *
 */
public class PageSizeName implements Serializable {

    public PageSizeName() {

    }

    public PageSizeName(String name, Rectangle page) {
        this.name = name;
        this.page = page;
    }

    private Rectangle page = PageSize.A4;
    private String name = "A4";

    public Rectangle getPage() {
        return page;
    }

    public void setPage(Rectangle page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PageSizeName)) {
            return false;
        }
        PageSizeName other = (PageSizeName) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
