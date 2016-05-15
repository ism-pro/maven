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
@Table(name = "lab_analyse_method", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lam_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabAnalyseMethod.findAll", query = "SELECT l FROM LabAnalyseMethod l"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamId", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamId = :lamId"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamMethod", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamMethod = :lamMethod"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamDesignation", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamDesignation = :lamDesignation"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamDeleted", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamDeleted = :lamDeleted"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamCreated", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamCreated = :lamCreated"),
    @NamedQuery(name = "LabAnalyseMethod.findByLamChanged", query = "SELECT l FROM LabAnalyseMethod l WHERE l.lamChanged = :lamChanged")})
public class LabAnalyseMethod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lam_id", nullable = false)
    private Integer lamId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lam_method", nullable = false, length = 45)
    private String lamMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lam_designation", nullable = false, length = 255)
    private String lamDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "lam_description", length = 65535)
    private String lamDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lam_deleted", nullable = false)
    private boolean lamDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lam_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lamCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lam_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lamChanged;
    @JoinColumn(name = "lam_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company lamCompany;
    @OneToMany(mappedBy = "latMethod")
    private Collection<LabAnalyseType> labAnalyseTypeCollection;

    public LabAnalyseMethod() {
    }

    public LabAnalyseMethod(Integer lamId) {
        this.lamId = lamId;
    }

    public LabAnalyseMethod(Integer lamId, String lamMethod, String lamDesignation, boolean lamDeleted, Date lamCreated, Date lamChanged) {
        this.lamId = lamId;
        this.lamMethod = lamMethod;
        this.lamDesignation = lamDesignation;
        this.lamDeleted = lamDeleted;
        this.lamCreated = lamCreated;
        this.lamChanged = lamChanged;
    }

    public Integer getLamId() {
        return lamId;
    }

    public void setLamId(Integer lamId) {
        this.lamId = lamId;
    }

    public String getLamMethod() {
        return lamMethod;
    }

    public void setLamMethod(String lamMethod) {
        this.lamMethod = lamMethod;
    }

    public String getLamDesignation() {
        return lamDesignation;
    }

    public void setLamDesignation(String lamDesignation) {
        this.lamDesignation = lamDesignation;
    }

    public String getLamDescription() {
        return lamDescription;
    }

    public void setLamDescription(String lamDescription) {
        this.lamDescription = lamDescription;
    }

    public boolean getLamDeleted() {
        return lamDeleted;
    }

    public void setLamDeleted(boolean lamDeleted) {
        this.lamDeleted = lamDeleted;
    }

    public Date getLamCreated() {
        return lamCreated;
    }

    public void setLamCreated(Date lamCreated) {
        this.lamCreated = lamCreated;
    }

    public Date getLamChanged() {
        return lamChanged;
    }

    public void setLamChanged(Date lamChanged) {
        this.lamChanged = lamChanged;
    }

    public Company getLamCompany() {
        return lamCompany;
    }

    public void setLamCompany(Company lamCompany) {
        this.lamCompany = lamCompany;
    }

    @XmlTransient
    public Collection<LabAnalyseType> getLabAnalyseTypeCollection() {
        return labAnalyseTypeCollection;
    }

    public void setLabAnalyseTypeCollection(Collection<LabAnalyseType> labAnalyseTypeCollection) {
        this.labAnalyseTypeCollection = labAnalyseTypeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lamId != null ? lamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabAnalyseMethod)) {
            return false;
        }
        LabAnalyseMethod other = (LabAnalyseMethod) object;
        if ((this.lamId == null && other.lamId != null) || (this.lamId != null && !this.lamId.equals(other.lamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabAnalyseMethod[ lamId=" + lamId + " ]";
    }
    
}
