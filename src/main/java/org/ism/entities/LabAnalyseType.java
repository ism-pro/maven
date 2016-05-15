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
@Table(name = "lab_analyse_type", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lat_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabAnalyseType.findAll", query = "SELECT l FROM LabAnalyseType l"),
    @NamedQuery(name = "LabAnalyseType.findByLatId", query = "SELECT l FROM LabAnalyseType l WHERE l.latId = :latId"),
    @NamedQuery(name = "LabAnalyseType.findByLatType", query = "SELECT l FROM LabAnalyseType l WHERE l.latType = :latType"),
    @NamedQuery(name = "LabAnalyseType.findByLatDesignation", query = "SELECT l FROM LabAnalyseType l WHERE l.latDesignation = :latDesignation"),
    @NamedQuery(name = "LabAnalyseType.findByLatDeleted", query = "SELECT l FROM LabAnalyseType l WHERE l.latDeleted = :latDeleted"),
    @NamedQuery(name = "LabAnalyseType.findByLatCreated", query = "SELECT l FROM LabAnalyseType l WHERE l.latCreated = :latCreated"),
    @NamedQuery(name = "LabAnalyseType.findByLatChanged", query = "SELECT l FROM LabAnalyseType l WHERE l.latChanged = :latChanged")})
public class LabAnalyseType implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lspaType")
    private Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lat_id", nullable = false)
    private Integer latId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lat_type", nullable = false, length = 45)
    private String latType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lat_designation", nullable = false, length = 255)
    private String latDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "lat_description", length = 65535)
    private String latDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat_deleted", nullable = false)
    private boolean latDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date latCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date latChanged;
    @JoinColumn(name = "lat_category", referencedColumnName = "lac_category")
    @ManyToOne
    private LabAnalyseCategory latCategory;
    @JoinColumn(name = "lat_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company latCompany;
    @JoinColumn(name = "lat_method", referencedColumnName = "lam_method")
    @ManyToOne
    private LabAnalyseMethod latMethod;
    @JoinColumn(name = "lat_unite", referencedColumnName = "u_unite", nullable = false)
    @ManyToOne(optional = false)
    private Unite latUnite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lsType")
    private Collection<LabSample> labSampleCollection;

    public LabAnalyseType() {
    }

    public LabAnalyseType(Integer latId) {
        this.latId = latId;
    }

    public LabAnalyseType(Integer latId, String latType, String latDesignation, boolean latDeleted, Date latCreated, Date latChanged) {
        this.latId = latId;
        this.latType = latType;
        this.latDesignation = latDesignation;
        this.latDeleted = latDeleted;
        this.latCreated = latCreated;
        this.latChanged = latChanged;
    }

    public Integer getLatId() {
        return latId;
    }

    public void setLatId(Integer latId) {
        this.latId = latId;
    }

    public String getLatType() {
        return latType;
    }

    public void setLatType(String latType) {
        this.latType = latType;
    }

    public String getLatDesignation() {
        return latDesignation;
    }

    public void setLatDesignation(String latDesignation) {
        this.latDesignation = latDesignation;
    }

    public String getLatDescription() {
        return latDescription;
    }

    public void setLatDescription(String latDescription) {
        this.latDescription = latDescription;
    }

    public boolean getLatDeleted() {
        return latDeleted;
    }

    public void setLatDeleted(boolean latDeleted) {
        this.latDeleted = latDeleted;
    }

    public Date getLatCreated() {
        return latCreated;
    }

    public void setLatCreated(Date latCreated) {
        this.latCreated = latCreated;
    }

    public Date getLatChanged() {
        return latChanged;
    }

    public void setLatChanged(Date latChanged) {
        this.latChanged = latChanged;
    }

    public LabAnalyseCategory getLatCategory() {
        return latCategory;
    }

    public void setLatCategory(LabAnalyseCategory latCategory) {
        this.latCategory = latCategory;
    }

    public Company getLatCompany() {
        return latCompany;
    }

    public void setLatCompany(Company latCompany) {
        this.latCompany = latCompany;
    }

    public LabAnalyseMethod getLatMethod() {
        return latMethod;
    }

    public void setLatMethod(LabAnalyseMethod latMethod) {
        this.latMethod = latMethod;
    }

    public Unite getLatUnite() {
        return latUnite;
    }

    public void setLatUnite(Unite latUnite) {
        this.latUnite = latUnite;
    }

    @XmlTransient
    public Collection<LabSample> getLabSampleCollection() {
        return labSampleCollection;
    }

    public void setLabSampleCollection(Collection<LabSample> labSampleCollection) {
        this.labSampleCollection = labSampleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (latId != null ? latId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabAnalyseType)) {
            return false;
        }
        LabAnalyseType other = (LabAnalyseType) object;
        if ((this.latId == null && other.latId != null) || (this.latId != null && !this.latId.equals(other.latId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabAnalyseType[ latId=" + latId + " ]";
    }

    @XmlTransient
    public Collection<LabSamplePtAnalyses> getLabSamplePtAnalysesCollection() {
        return labSamplePtAnalysesCollection;
    }

    public void setLabSamplePtAnalysesCollection(Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection) {
        this.labSamplePtAnalysesCollection = labSamplePtAnalysesCollection;
    }
    
}
