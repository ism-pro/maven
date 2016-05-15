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
@Table(name = "lab_sample", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ls_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabSample.findAll", query = "SELECT l FROM LabSample l"),
    @NamedQuery(name = "LabSample.findByLsId", query = "SELECT l FROM LabSample l WHERE l.lsId = :lsId"),
    @NamedQuery(name = "LabSample.findByLsValue", query = "SELECT l FROM LabSample l WHERE l.lsValue = :lsValue"),
    @NamedQuery(name = "LabSample.findByLsValueT", query = "SELECT l FROM LabSample l WHERE l.lsValueT = :lsValueT"),
    @NamedQuery(name = "LabSample.findByLsValidate", query = "SELECT l FROM LabSample l WHERE l.lsValidate = :lsValidate"),
    @NamedQuery(name = "LabSample.findByLsObservation", query = "SELECT l FROM LabSample l WHERE l.lsObservation = :lsObservation"),
    @NamedQuery(name = "LabSample.findByLsDeleted", query = "SELECT l FROM LabSample l WHERE l.lsDeleted = :lsDeleted"),
    @NamedQuery(name = "LabSample.findByLsCreated", query = "SELECT l FROM LabSample l WHERE l.lsCreated = :lsCreated"),
    @NamedQuery(name = "LabSample.findByLsChanged", query = "SELECT l FROM LabSample l WHERE l.lsChanged = :lsChanged")})
public class LabSample implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ls_id", nullable = false)
    private Integer lsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ls_value", nullable = false)
    private double lsValue;
    @Size(max = 45)
    @Column(name = "ls_value_t", length = 45)
    private String lsValueT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ls_validate", nullable = false)
    private boolean lsValidate;
    @Size(max = 255)
    @Column(name = "ls_observation", length = 255)
    private String lsObservation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ls_deleted", nullable = false)
    private boolean lsDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ls_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lsCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ls_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lsChanged;
    @JoinColumn(name = "ls_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company lsCompany;
    @JoinColumn(name = "ls_sample_pt", referencedColumnName = "lsp_sample_pt", nullable = false)
    @ManyToOne(optional = false)
    private LabSamplePt lsSamplePt;
    @JoinColumn(name = "ls_sampler", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff lsSampler;
    @JoinColumn(name = "ls_type", referencedColumnName = "lat_type", nullable = false)
    @ManyToOne(optional = false)
    private LabAnalyseType lsType;

    public LabSample() {
    }

    public LabSample(Integer lsId) {
        this.lsId = lsId;
    }

    public LabSample(Integer lsId, double lsValue, boolean lsValidate, boolean lsDeleted, Date lsCreated, Date lsChanged) {
        this.lsId = lsId;
        this.lsValue = lsValue;
        this.lsValidate = lsValidate;
        this.lsDeleted = lsDeleted;
        this.lsCreated = lsCreated;
        this.lsChanged = lsChanged;
    }

    public Integer getLsId() {
        return lsId;
    }

    public void setLsId(Integer lsId) {
        this.lsId = lsId;
    }

    public double getLsValue() {
        return lsValue;
    }

    public void setLsValue(double lsValue) {
        this.lsValue = lsValue;
    }

    public String getLsValueT() {
        return lsValueT;
    }

    public void setLsValueT(String lsValueT) {
        this.lsValueT = lsValueT;
    }

    public boolean getLsValidate() {
        return lsValidate;
    }

    public void setLsValidate(boolean lsValidate) {
        this.lsValidate = lsValidate;
    }

    public String getLsObservation() {
        return lsObservation;
    }

    public void setLsObservation(String lsObservation) {
        this.lsObservation = lsObservation;
    }

    public boolean getLsDeleted() {
        return lsDeleted;
    }

    public void setLsDeleted(boolean lsDeleted) {
        this.lsDeleted = lsDeleted;
    }

    public Date getLsCreated() {
        return lsCreated;
    }

    public void setLsCreated(Date lsCreated) {
        this.lsCreated = lsCreated;
    }

    public Date getLsChanged() {
        return lsChanged;
    }

    public void setLsChanged(Date lsChanged) {
        this.lsChanged = lsChanged;
    }

    public Company getLsCompany() {
        return lsCompany;
    }

    public void setLsCompany(Company lsCompany) {
        this.lsCompany = lsCompany;
    }

    public LabSamplePt getLsSamplePt() {
        return lsSamplePt;
    }

    public void setLsSamplePt(LabSamplePt lsSamplePt) {
        this.lsSamplePt = lsSamplePt;
    }

    public Staff getLsSampler() {
        return lsSampler;
    }

    public void setLsSampler(Staff lsSampler) {
        this.lsSampler = lsSampler;
    }

    public LabAnalyseType getLsType() {
        return lsType;
    }

    public void setLsType(LabAnalyseType lsType) {
        this.lsType = lsType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lsId != null ? lsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabSample)) {
            return false;
        }
        LabSample other = (LabSample) object;
        if ((this.lsId == null && other.lsId != null) || (this.lsId != null && !this.lsId.equals(other.lsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabSample[ lsId=" + lsId + " ]";
    }
    
}
