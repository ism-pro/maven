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
@Table(name = "non_conformite_nature", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ncn_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteNature.findAll", query = "SELECT n FROM NonConformiteNature n"),
    @NamedQuery(name = "NonConformiteNature.findByNcnId", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnId = :ncnId"),
    @NamedQuery(name = "NonConformiteNature.findByNcnNature", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnNature = :ncnNature"),
    @NamedQuery(name = "NonConformiteNature.findByNcnDesignation", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnDesignation = :ncnDesignation"),
    @NamedQuery(name = "NonConformiteNature.findByNcnNatureOfCompany", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnNature = :ncnNature AND n.ncnCompany = :ncnCompany"),
    @NamedQuery(name = "NonConformiteNature.findByNcnDesignationOfCompany", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnDesignation = :ncnDesignation AND n.ncnCompany = :ncnCompany"),
    @NamedQuery(name = "NonConformiteNature.findByNcnDeleted", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnDeleted = :ncnDeleted"),
    @NamedQuery(name = "NonConformiteNature.findByNcnCreated", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnCreated = :ncnCreated"),
    @NamedQuery(name = "NonConformiteNature.findByNcnChanged", query = "SELECT n FROM NonConformiteNature n WHERE n.ncnChanged = :ncnChanged"),
    @NamedQuery(name = "NonConformiteNature.selectAllByLastChange", query = "SELECT n FROM NonConformiteNature n ORDER BY n.ncnChanged DESC")
})
public class NonConformiteNature implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ncn_id", nullable = false)
    private Integer ncnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ncn_nature", nullable = false, length = 45)
    private String ncnNature;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ncn_designation", nullable = false, length = 255)
    private String ncnDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncn_deleted", nullable = false)
    private boolean ncnDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncn_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncnCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncn_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncnChanged;
    @OneToMany(mappedBy = "ncrNature")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @JoinColumn(name = "ncn_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncnCompany;

    public NonConformiteNature() {
    }

    public NonConformiteNature(Integer ncnId) {
        this.ncnId = ncnId;
    }

    public NonConformiteNature(Integer ncnId, String ncnNature, String ncnDesignation, boolean ncnDeleted, Date ncnCreated, Date ncnChanged) {
        this.ncnId = ncnId;
        this.ncnNature = ncnNature;
        this.ncnDesignation = ncnDesignation;
        this.ncnDeleted = ncnDeleted;
        this.ncnCreated = ncnCreated;
        this.ncnChanged = ncnChanged;
    }

    public Integer getNcnId() {
        return ncnId;
    }

    public void setNcnId(Integer ncnId) {
        this.ncnId = ncnId;
    }

    public String getNcnNature() {
        return ncnNature;
    }

    public void setNcnNature(String ncnNature) {
        this.ncnNature = ncnNature;
    }

    public String getNcnDesignation() {
        return ncnDesignation;
    }

    public void setNcnDesignation(String ncnDesignation) {
        this.ncnDesignation = ncnDesignation;
    }

    public boolean getNcnDeleted() {
        return ncnDeleted;
    }

    public void setNcnDeleted(boolean ncnDeleted) {
        this.ncnDeleted = ncnDeleted;
    }

    public Date getNcnCreated() {
        return ncnCreated;
    }

    public void setNcnCreated(Date ncnCreated) {
        this.ncnCreated = ncnCreated;
    }

    public Date getNcnChanged() {
        return ncnChanged;
    }

    public void setNcnChanged(Date ncnChanged) {
        this.ncnChanged = ncnChanged;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    public Company getNcnCompany() {
        return ncnCompany;
    }

    public void setNcnCompany(Company ncnCompany) {
        this.ncnCompany = ncnCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncnId != null ? ncnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformiteNature)) {
            return false;
        }
        NonConformiteNature other = (NonConformiteNature) object;
        if ((this.ncnId == null && other.ncnId != null) || (this.ncnId != null && !this.ncnId.equals(other.ncnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ncnNature + " - " + ncnDesignation + " [" + ncnId + "]";
        //return "org.ism.entities.NonConformiteNature[ ncnId=" + ncnId + " ]";
    }

}
