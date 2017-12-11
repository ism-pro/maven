/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.entities.app;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>IsmReport</h1><br>
 * IsmReport class  
 *
 * @author r.hendrick
 *
 */
@Entity
@Table(name = "ism_report", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmReport.findAll", query = "SELECT i FROM IsmReport i"),
    @NamedQuery(name = "IsmReport.findById", query = "SELECT i FROM IsmReport i WHERE i.id = :id"),
    @NamedQuery(name = "IsmReport.findByReport", query = "SELECT i FROM IsmReport i WHERE i.report = :report"),
    @NamedQuery(name = "IsmReport.findByDesignation", query = "SELECT i FROM IsmReport i WHERE i.designation = :designation"),
    @NamedQuery(name = "IsmReport.findByPath", query = "SELECT i FROM IsmReport i WHERE i.path = :path")})
public class IsmReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String report;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String designation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(nullable = false, length = 512)
    private String path;

    public IsmReport() {
    }

    public IsmReport(Integer id) {
        this.id = id;
    }

    public IsmReport(Integer id, String report, String designation, String path) {
        this.id = id;
        this.report = report;
        this.designation = designation;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IsmReport)) {
            return false;
        }
        IsmReport other = (IsmReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return report + " - " + designation + " [" + id + "]";
        //return "org.ism.entities.app.IsmReport[ id=" + id + " ]";
    }

}
