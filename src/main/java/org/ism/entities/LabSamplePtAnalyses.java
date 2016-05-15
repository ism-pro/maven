/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "lab_sample_pt_analyses", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lspa_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabSamplePtAnalyses.findAll", query = "SELECT l FROM LabSamplePtAnalyses l"),
    @NamedQuery(name = "LabSamplePtAnalyses.findByLspaId", query = "SELECT l FROM LabSamplePtAnalyses l WHERE l.lspaId = :lspaId"),
    @NamedQuery(name = "LabSamplePtAnalyses.findByLspaObservation", query = "SELECT l FROM LabSamplePtAnalyses l WHERE l.lspaObservation = :lspaObservation"),
    @NamedQuery(name = "LabSamplePtAnalyses.findByLspaDeleted", query = "SELECT l FROM LabSamplePtAnalyses l WHERE l.lspaDeleted = :lspaDeleted"),
    @NamedQuery(name = "LabSamplePtAnalyses.findByLspaCreated", query = "SELECT l FROM LabSamplePtAnalyses l WHERE l.lspaCreated = :lspaCreated"),
    @NamedQuery(name = "LabSamplePtAnalyses.findByLspaChanged", query = "SELECT l FROM LabSamplePtAnalyses l WHERE l.lspaChanged = :lspaChanged")})
public class LabSamplePtAnalyses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lspa_id", nullable = false)
    private Integer lspaId;
    @Size(max = 255)
    @Column(name = "lspa_observation", length = 255)
    private String lspaObservation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lspa_deleted", nullable = false)
    private boolean lspaDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lspa_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lspaCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lspa_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lspaChanged;
    @JoinColumn(name = "lspa_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company lspaCompany;
    @JoinColumn(name = "lspa_sample_pt", referencedColumnName = "lsp_sample_pt", nullable = false)
    @ManyToOne(optional = false)
    private LabSamplePt lspaSamplePt;
    @JoinColumn(name = "lspa_type", referencedColumnName = "lat_type", nullable = false)
    @ManyToOne(optional = false)
    private LabAnalyseType lspaType;

    public LabSamplePtAnalyses() {
    }

    public LabSamplePtAnalyses(Integer lspaId) {
        this.lspaId = lspaId;
    }

    public LabSamplePtAnalyses(Integer lspaId, boolean lspaDeleted, Date lspaCreated, Date lspaChanged) {
        this.lspaId = lspaId;
        this.lspaDeleted = lspaDeleted;
        this.lspaCreated = lspaCreated;
        this.lspaChanged = lspaChanged;
    }

    public Integer getLspaId() {
        return lspaId;
    }

    public void setLspaId(Integer lspaId) {
        this.lspaId = lspaId;
    }

    public String getLspaObservation() {
        return lspaObservation;
    }

    public void setLspaObservation(String lspaObservation) {
        this.lspaObservation = lspaObservation;
    }

    public boolean getLspaDeleted() {
        return lspaDeleted;
    }

    public void setLspaDeleted(boolean lspaDeleted) {
        this.lspaDeleted = lspaDeleted;
    }

    public Date getLspaCreated() {
        return lspaCreated;
    }

    public void setLspaCreated(Date lspaCreated) {
        this.lspaCreated = lspaCreated;
    }

    public Date getLspaChanged() {
        return lspaChanged;
    }

    public void setLspaChanged(Date lspaChanged) {
        this.lspaChanged = lspaChanged;
    }

    public Company getLspaCompany() {
        return lspaCompany;
    }

    public void setLspaCompany(Company lspaCompany) {
        this.lspaCompany = lspaCompany;
    }

    public LabSamplePt getLspaSamplePt() {
        return lspaSamplePt;
    }

    public void setLspaSamplePt(LabSamplePt lspaSamplePt) {
        this.lspaSamplePt = lspaSamplePt;
    }

    public LabAnalyseType getLspaType() {
        return lspaType;
    }

    public void setLspaType(LabAnalyseType lspaType) {
        this.lspaType = lspaType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lspaId != null ? lspaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabSamplePtAnalyses)) {
            return false;
        }
        LabSamplePtAnalyses other = (LabSamplePtAnalyses) object;
        if ((this.lspaId == null && other.lspaId != null) || (this.lspaId != null && !this.lspaId.equals(other.lspaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabSamplePtAnalyses[ lspaId=" + lspaId + " ]";
    }
    
}
