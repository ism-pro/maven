/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "lab_sample_pt", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lsp_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabSamplePt.findAll", query = "SELECT l FROM LabSamplePt l"),
    @NamedQuery(name = "LabSamplePt.findByLspId", query = "SELECT l FROM LabSamplePt l WHERE l.lspId = :lspId"),
    @NamedQuery(name = "LabSamplePt.findByLspSamplePt", query = "SELECT l FROM LabSamplePt l WHERE l.lspSamplePt = :lspSamplePt"),
    @NamedQuery(name = "LabSamplePt.findByLspDesignation", query = "SELECT l FROM LabSamplePt l WHERE l.lspDesignation = :lspDesignation"),
    @NamedQuery(name = "LabSamplePt.findByLspDeleted", query = "SELECT l FROM LabSamplePt l WHERE l.lspDeleted = :lspDeleted"),
    @NamedQuery(name = "LabSamplePt.findByLspCreated", query = "SELECT l FROM LabSamplePt l WHERE l.lspCreated = :lspCreated"),
    @NamedQuery(name = "LabSamplePt.findByLspChanged", query = "SELECT l FROM LabSamplePt l WHERE l.lspChanged = :lspChanged")})
public class LabSamplePt implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lspaSamplePt")
    private Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lsp_id", nullable = false)
    private Integer lspId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lsp_sample_pt", nullable = false, length = 45)
    private String lspSamplePt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lsp_designation", nullable = false, length = 255)
    private String lspDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "lsp_description", length = 65535)
    private String lspDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lsp_deleted", nullable = false)
    private boolean lspDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lsp_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lspCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lsp_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lspChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lsSamplePt")
    private Collection<LabSample> labSampleCollection;
    @JoinColumn(name = "lsp_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company lspCompany;
    @JoinColumn(name = "lsp_equipement", referencedColumnName = "e_equipement", nullable = false)
    @ManyToOne(optional = false)
    private Equipement lspEquipement;

    public LabSamplePt() {
    }

    public LabSamplePt(Integer lspId) {
        this.lspId = lspId;
    }

    public LabSamplePt(Integer lspId, String lspSamplePt, String lspDesignation, boolean lspDeleted, Date lspCreated, Date lspChanged) {
        this.lspId = lspId;
        this.lspSamplePt = lspSamplePt;
        this.lspDesignation = lspDesignation;
        this.lspDeleted = lspDeleted;
        this.lspCreated = lspCreated;
        this.lspChanged = lspChanged;
    }

    public Integer getLspId() {
        return lspId;
    }

    public void setLspId(Integer lspId) {
        this.lspId = lspId;
    }

    public String getLspSamplePt() {
        return lspSamplePt;
    }

    public void setLspSamplePt(String lspSamplePt) {
        this.lspSamplePt = lspSamplePt;
    }

    public String getLspDesignation() {
        return lspDesignation;
    }

    public void setLspDesignation(String lspDesignation) {
        this.lspDesignation = lspDesignation;
    }

    public String getLspDescription() {
        return lspDescription;
    }

    public void setLspDescription(String lspDescription) {
        this.lspDescription = lspDescription;
    }

    public boolean getLspDeleted() {
        return lspDeleted;
    }

    public void setLspDeleted(boolean lspDeleted) {
        this.lspDeleted = lspDeleted;
    }

    public Date getLspCreated() {
        return lspCreated;
    }

    public void setLspCreated(Date lspCreated) {
        this.lspCreated = lspCreated;
    }

    public Date getLspChanged() {
        return lspChanged;
    }

    public void setLspChanged(Date lspChanged) {
        this.lspChanged = lspChanged;
    }

    @XmlTransient
    public Collection<LabSample> getLabSampleCollection() {
        return labSampleCollection;
    }

    public void setLabSampleCollection(Collection<LabSample> labSampleCollection) {
        this.labSampleCollection = labSampleCollection;
    }

    public Company getLspCompany() {
        return lspCompany;
    }

    public void setLspCompany(Company lspCompany) {
        this.lspCompany = lspCompany;
    }

    public Equipement getLspEquipement() {
        return lspEquipement;
    }

    public void setLspEquipement(Equipement lspEquipement) {
        this.lspEquipement = lspEquipement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lspId != null ? lspId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabSamplePt)) {
            return false;
        }
        LabSamplePt other = (LabSamplePt) object;
        if ((this.lspId == null && other.lspId != null) || (this.lspId != null && !this.lspId.equals(other.lspId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabSamplePt[ lspId=" + lspId + " ]";
    }

    @XmlTransient
    public Collection<LabSamplePtAnalyses> getLabSamplePtAnalysesCollection() {
        return labSamplePtAnalysesCollection;
    }

    public void setLabSamplePtAnalysesCollection(Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection) {
        this.labSamplePtAnalysesCollection = labSamplePtAnalysesCollection;
    }
    
}
