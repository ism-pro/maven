/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.smq.nc;

import org.ism.entities.admin.Company;
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
@Table(name = "non_conformite_frequency", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ncf_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteFrequency.findAll", query = "SELECT n FROM NonConformiteFrequency n"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfId", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfId = :ncfId"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfFrequency", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfFrequency = :ncfFrequency"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfDesignation", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfDesignation = :ncfDesignation"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfFrequencyOfCompany", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfFrequency = :ncfFrequency AND n.ncfCompany = :ncfCompany"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfDesignationOfCompany", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfDesignation = :ncfDesignation AND n.ncfCompany = :ncfCompany"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfDeleted", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfDeleted = :ncfDeleted"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfCreated", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfCreated = :ncfCreated"),
    @NamedQuery(name = "NonConformiteFrequency.findByNcfChanged", query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfChanged = :ncfChanged"),
    @NamedQuery(name = "NonConformiteFrequency.selectAllByLastChange", query = "SELECT n FROM NonConformiteFrequency n ORDER BY n.ncfChanged DESC")
})
public class NonConformiteFrequency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ncf_id", nullable = false)
    private Integer ncfId;
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @NotNull
    @Column(name = "ncf_frequency", nullable = false, length = 45)
    private String ncfFrequency;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @NotNull
    @Column(name = "ncf_designation", nullable = false, length = 255)
    private String ncfDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncf_deleted", nullable = false)
    private boolean ncfDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncf_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncfCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncf_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncfChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrFrequency")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @JoinColumn(name = "ncf_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncfCompany;

    public NonConformiteFrequency() {
    }

    public NonConformiteFrequency(Integer ncfId) {
        this.ncfId = ncfId;
    }

    public NonConformiteFrequency(Integer ncfId, String ncfFrequency, String ncfDesignation, boolean ncfDeleted, Date ncfCreated, Date ncfChanged) {
        this.ncfId = ncfId;
        this.ncfFrequency = ncfFrequency;
        this.ncfDesignation = ncfDesignation;
        this.ncfDeleted = ncfDeleted;
        this.ncfCreated = ncfCreated;
        this.ncfChanged = ncfChanged;
    }

    public Integer getNcfId() {
        return ncfId;
    }

    public void setNcfId(Integer ncfId) {
        this.ncfId = ncfId;
    }

    public String getNcfFrequency() {
        return ncfFrequency;
    }

    public void setNcfFrequency(String ncfFrequency) {
        this.ncfFrequency = ncfFrequency;
    }

    public String getNcfDesignation() {
        return ncfDesignation;
    }

    public void setNcfDesignation(String ncfDesignation) {
        this.ncfDesignation = ncfDesignation;
    }

    public boolean getNcfDeleted() {
        return ncfDeleted;
    }

    public void setNcfDeleted(boolean ncfDeleted) {
        this.ncfDeleted = ncfDeleted;
    }

    public Date getNcfCreated() {
        return ncfCreated;
    }

    public void setNcfCreated(Date ncfCreated) {
        this.ncfCreated = ncfCreated;
    }

    public Date getNcfChanged() {
        return ncfChanged;
    }

    public void setNcfChanged(Date ncfChanged) {
        this.ncfChanged = ncfChanged;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    public Company getNcfCompany() {
        return ncfCompany;
    }

    public void setNcfCompany(Company ncfCompany) {
        this.ncfCompany = ncfCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncfId != null ? ncfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformiteFrequency)) {
            return false;
        }
        NonConformiteFrequency other = (NonConformiteFrequency) object;
        if ((this.ncfId == null && other.ncfId != null) || (this.ncfId != null && !this.ncfId.equals(other.ncfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ncfFrequency + " - " + ncfDesignation + " [" + ncfId + "]";
        //return "org.ism.entities.NonConformiteFrequency[ ncfId=" + ncfId + " ]";
    }

}
