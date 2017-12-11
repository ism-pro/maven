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
@Table(name = "non_conformite_unite", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ncu_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteUnite.findAll", query = "SELECT n FROM NonConformiteUnite n"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuId", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuId = :ncuId"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuUnite", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuUnite = :ncuUnite"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuDesignation", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuDesignation = :ncuDesignation"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuUniteOfCompany", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuUnite = :ncuUnite AND n.ncuCompany = :ncuCompany"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuDesignationOfCompany", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuDesignation = :ncuDesignation AND n.ncuCompany = :ncuCompany"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuDeleted", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuDeleted = :ncuDeleted"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuCreated", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuCreated = :ncuCreated"),
    @NamedQuery(name = "NonConformiteUnite.findByNcuChanged", query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuChanged = :ncuChanged"),
    @NamedQuery(name = "NonConformiteUnite.selectAllByLastChange", query = "SELECT n FROM NonConformiteUnite n ORDER BY n.ncuChanged DESC")
})
public class NonConformiteUnite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ncu_id", nullable = false)
    private Integer ncuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ncu_unite", nullable = false, length = 45)
    private String ncuUnite;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ncu_designation", nullable = false, length = 255)
    private String ncuDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncu_deleted", nullable = false)
    private boolean ncuDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncu_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncuCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncu_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncuChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrUnite")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @JoinColumn(name = "ncu_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncuCompany;

    public NonConformiteUnite() {
    }

    public NonConformiteUnite(Integer ncuId) {
        this.ncuId = ncuId;
    }

    public NonConformiteUnite(Integer ncuId, String ncuUnite, String ncuDesignation, boolean ncuDeleted, Date ncuCreated, Date ncuChanged) {
        this.ncuId = ncuId;
        this.ncuUnite = ncuUnite;
        this.ncuDesignation = ncuDesignation;
        this.ncuDeleted = ncuDeleted;
        this.ncuCreated = ncuCreated;
        this.ncuChanged = ncuChanged;
    }

    public Integer getNcuId() {
        return ncuId;
    }

    public void setNcuId(Integer ncuId) {
        this.ncuId = ncuId;
    }

    public String getNcuUnite() {
        return ncuUnite;
    }

    public void setNcuUnite(String ncuUnite) {
        this.ncuUnite = ncuUnite;
    }

    public String getNcuDesignation() {
        return ncuDesignation;
    }

    public void setNcuDesignation(String ncuDesignation) {
        this.ncuDesignation = ncuDesignation;
    }

    public boolean getNcuDeleted() {
        return ncuDeleted;
    }

    public void setNcuDeleted(boolean ncuDeleted) {
        this.ncuDeleted = ncuDeleted;
    }

    public Date getNcuCreated() {
        return ncuCreated;
    }

    public void setNcuCreated(Date ncuCreated) {
        this.ncuCreated = ncuCreated;
    }

    public Date getNcuChanged() {
        return ncuChanged;
    }

    public void setNcuChanged(Date ncuChanged) {
        this.ncuChanged = ncuChanged;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    public Company getNcuCompany() {
        return ncuCompany;
    }

    public void setNcuCompany(Company ncuCompany) {
        this.ncuCompany = ncuCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncuId != null ? ncuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformiteUnite)) {
            return false;
        }
        NonConformiteUnite other = (NonConformiteUnite) object;
        if ((this.ncuId == null && other.ncuId != null) || (this.ncuId != null && !this.ncuId.equals(other.ncuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ncuUnite + " - " + ncuDesignation + " [" + ncuId + "]";
        //return "org.ism.entities.NonConformiteUnite[ ncuId=" + ncuId + " ]";
    }

}
