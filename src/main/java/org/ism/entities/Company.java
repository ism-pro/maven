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
@Table(name = "company", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"c_id", "c_company"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
    @NamedQuery(name = "Company.findByCId", query = "SELECT c FROM Company c WHERE c.cId = :cId"),
    @NamedQuery(name = "Company.findByCCompany", query = "SELECT c FROM Company c WHERE c.cCompany = :cCompany"),
    @NamedQuery(name = "Company.findByCDesignation", query = "SELECT c FROM Company c WHERE c.cDesignation = :cDesignation"),
    @NamedQuery(name = "Company.findByCBuilded", query = "SELECT c FROM Company c WHERE c.cBuilded = :cBuilded"),
    @NamedQuery(name = "Company.findByCMain", query = "SELECT c FROM Company c WHERE c.cMain = :cMain"),
    @NamedQuery(name = "Company.findByCActivated", query = "SELECT c FROM Company c WHERE c.cActivated = :cActivated"),
    @NamedQuery(name = "Company.findByCDeleted", query = "SELECT c FROM Company c WHERE c.cDeleted = :cDeleted"),
    @NamedQuery(name = "Company.findByCCreated", query = "SELECT c FROM Company c WHERE c.cCreated = :cCreated"),
    @NamedQuery(name = "Company.findByCChanged", query = "SELECT c FROM Company c WHERE c.cChanged = :cChanged")})
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "c_id", nullable = false)
    private Integer cId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "c_company", nullable = false, length = 45)
    private String cCompany;
    @Size(max = 255)
    @Column(name = "c_designation", length = 255)
    private String cDesignation;
    @Column(name = "c_builded")
    private Integer cBuilded;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_main", nullable = false)
    private boolean cMain;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_activated", nullable = false)
    private boolean cActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_deleted", nullable = false)
    private boolean cDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lamCompany")
    private Collection<LabAnalyseMethod> labAnalyseMethodCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eCompany")
    private Collection<Equipement> equipementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lacCompany")
    private Collection<LabAnalyseCategory> labAnalyseCategoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uCompany")
    private Collection<Unite> uniteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "latCompany")
    private Collection<LabAnalyseType> labAnalyseTypeCollection;
    @JoinColumn(name = "c_entreprise", referencedColumnName = "e_entreprise", nullable = false)
    @ManyToOne(optional = false)
    private Entreprise cEntreprise;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lspCompany")
    private Collection<LabSamplePt> labSamplePtCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lspaCompany")
    private Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection;

    public Company() {
    }

    public Company(Integer cId) {
        this.cId = cId;
    }

    public Company(Integer cId, String cCompany, boolean cMain, boolean cActivated, boolean cDeleted, Date cCreated, Date cChanged) {
        this.cId = cId;
        this.cCompany = cCompany;
        this.cMain = cMain;
        this.cActivated = cActivated;
        this.cDeleted = cDeleted;
        this.cCreated = cCreated;
        this.cChanged = cChanged;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCCompany() {
        return cCompany;
    }

    public void setCCompany(String cCompany) {
        this.cCompany = cCompany;
    }

    public String getCDesignation() {
        return cDesignation;
    }

    public void setCDesignation(String cDesignation) {
        this.cDesignation = cDesignation;
    }

    public Integer getCBuilded() {
        return cBuilded;
    }

    public void setCBuilded(Integer cBuilded) {
        this.cBuilded = cBuilded;
    }

    public boolean getCMain() {
        return cMain;
    }

    public void setCMain(boolean cMain) {
        this.cMain = cMain;
    }

    public boolean getCActivated() {
        return cActivated;
    }

    public void setCActivated(boolean cActivated) {
        this.cActivated = cActivated;
    }

    public boolean getCDeleted() {
        return cDeleted;
    }

    public void setCDeleted(boolean cDeleted) {
        this.cDeleted = cDeleted;
    }

    public Date getCCreated() {
        return cCreated;
    }

    public void setCCreated(Date cCreated) {
        this.cCreated = cCreated;
    }

    public Date getCChanged() {
        return cChanged;
    }

    public void setCChanged(Date cChanged) {
        this.cChanged = cChanged;
    }

    @XmlTransient
    public Collection<LabAnalyseMethod> getLabAnalyseMethodCollection() {
        return labAnalyseMethodCollection;
    }

    public void setLabAnalyseMethodCollection(Collection<LabAnalyseMethod> labAnalyseMethodCollection) {
        this.labAnalyseMethodCollection = labAnalyseMethodCollection;
    }

    @XmlTransient
    public Collection<Equipement> getEquipementCollection() {
        return equipementCollection;
    }

    public void setEquipementCollection(Collection<Equipement> equipementCollection) {
        this.equipementCollection = equipementCollection;
    }

    @XmlTransient
    public Collection<LabAnalyseCategory> getLabAnalyseCategoryCollection() {
        return labAnalyseCategoryCollection;
    }

    public void setLabAnalyseCategoryCollection(Collection<LabAnalyseCategory> labAnalyseCategoryCollection) {
        this.labAnalyseCategoryCollection = labAnalyseCategoryCollection;
    }

    @XmlTransient
    public Collection<Unite> getUniteCollection() {
        return uniteCollection;
    }

    public void setUniteCollection(Collection<Unite> uniteCollection) {
        this.uniteCollection = uniteCollection;
    }

    @XmlTransient
    public Collection<LabAnalyseType> getLabAnalyseTypeCollection() {
        return labAnalyseTypeCollection;
    }

    public void setLabAnalyseTypeCollection(Collection<LabAnalyseType> labAnalyseTypeCollection) {
        this.labAnalyseTypeCollection = labAnalyseTypeCollection;
    }

    public Entreprise getCEntreprise() {
        return cEntreprise;
    }

    public void setCEntreprise(Entreprise cEntreprise) {
        this.cEntreprise = cEntreprise;
    }

    @XmlTransient
    public Collection<LabSamplePt> getLabSamplePtCollection() {
        return labSamplePtCollection;
    }

    public void setLabSamplePtCollection(Collection<LabSamplePt> labSamplePtCollection) {
        this.labSamplePtCollection = labSamplePtCollection;
    }

    @XmlTransient
    public Collection<LabSamplePtAnalyses> getLabSamplePtAnalysesCollection() {
        return labSamplePtAnalysesCollection;
    }

    public void setLabSamplePtAnalysesCollection(Collection<LabSamplePtAnalyses> labSamplePtAnalysesCollection) {
        this.labSamplePtAnalysesCollection = labSamplePtAnalysesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cId != null ? cId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.cId == null && other.cId != null) || (this.cId != null && !this.cId.equals(other.cId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.Company[ cId=" + cId + " ]";
    }
    
}
