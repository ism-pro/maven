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
@Table(name = "non_conformite_gravity", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ncg_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteGravity.findAll", query = "SELECT n FROM NonConformiteGravity n"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgId", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgId = :ncgId"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgGravity", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgGravity = :ncgGravity"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgDesignation", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgDesignation = :ncgDesignation"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgGravityOfCompany", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgGravity = :ncgGravity AND n.ncgCompany = :ncgCompany"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgDesignationOfCompany", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgDesignation = :ncgDesignation AND n.ncgCompany = :ncgCompany"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgDeleted", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgDeleted = :ncgDeleted"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgCreated", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgCreated = :ncgCreated"),
    @NamedQuery(name = "NonConformiteGravity.findByNcgChanged", query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgChanged = :ncgChanged"),
    @NamedQuery(name = "NonConformiteGravity.selectAllByLastChange", query = "SELECT n FROM NonConformiteGravity n ORDER BY n.ncgChanged DESC")
})
public class NonConformiteGravity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ncg_id", nullable = false)
    private Integer ncgId;
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ncg_gravity", nullable = false, length = 45)
    private String ncgGravity;
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ncg_designation", nullable = false, length = 255)
    private String ncgDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncg_deleted", nullable = false)
    private boolean ncgDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncg_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncgCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncg_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncgChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrGravity")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @JoinColumn(name = "ncg_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncgCompany;

    public NonConformiteGravity() {
    }

    public NonConformiteGravity(Integer ncgId) {
        this.ncgId = ncgId;
    }

    public NonConformiteGravity(Integer ncgId, String ncgGravity, String ncgDesignation, boolean ncgDeleted, Date ncgCreated, Date ncgChanged) {
        this.ncgId = ncgId;
        this.ncgGravity = ncgGravity;
        this.ncgDesignation = ncgDesignation;
        this.ncgDeleted = ncgDeleted;
        this.ncgCreated = ncgCreated;
        this.ncgChanged = ncgChanged;
    }

    public Integer getNcgId() {
        return ncgId;
    }

    public void setNcgId(Integer ncgId) {
        this.ncgId = ncgId;
    }

    public String getNcgGravity() {
        return ncgGravity;
    }

    public void setNcgGravity(String ncgGravity) {
        this.ncgGravity = ncgGravity;
    }

    public String getNcgDesignation() {
        return ncgDesignation;
    }

    public void setNcgDesignation(String ncgDesignation) {
        this.ncgDesignation = ncgDesignation;
    }

    public boolean getNcgDeleted() {
        return ncgDeleted;
    }

    public void setNcgDeleted(boolean ncgDeleted) {
        this.ncgDeleted = ncgDeleted;
    }

    public Date getNcgCreated() {
        return ncgCreated;
    }

    public void setNcgCreated(Date ncgCreated) {
        this.ncgCreated = ncgCreated;
    }

    public Date getNcgChanged() {
        return ncgChanged;
    }

    public void setNcgChanged(Date ncgChanged) {
        this.ncgChanged = ncgChanged;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    public Company getNcgCompany() {
        return ncgCompany;
    }

    public void setNcgCompany(Company ncgCompany) {
        this.ncgCompany = ncgCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncgId != null ? ncgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformiteGravity)) {
            return false;
        }
        NonConformiteGravity other = (NonConformiteGravity) object;
        if ((this.ncgId == null && other.ncgId != null) || (this.ncgId != null && !this.ncgId.equals(other.ncgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ncgGravity + " - " + ncgDesignation + " [" + ncgId + "]";
        //return "org.ism.entities.NonConformiteGravity[ ncgId=" + ncgId + " ]";
    }

}
