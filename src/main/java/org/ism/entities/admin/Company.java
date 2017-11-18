/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.admin;

import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.entities.process.ctrl.AnalyseNotify;
import org.ism.entities.process.ctrl.AnalyseMethod;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.entities.process.ctrl.AnalyseCategory;
import org.ism.entities.process.ctrl.AnalysePoint;
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
import org.ism.entities.process.Equipement;
import org.ism.entities.smq.nc.NonConformiteFrequency;
import org.ism.entities.smq.nc.NonConformiteGravity;
import org.ism.entities.smq.nc.NonConformiteNature;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.entities.smq.nc.NonConformiteUnite;
import org.ism.entities.smq.Processus;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.process.Unite;
import org.ism.entities.smq.PointInfos;
import org.ism.entities.smq.ProcessAccess;

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
    @NamedQuery(name = "Company.findByCChanged", query = "SELECT c FROM Company c WHERE c.cChanged = :cChanged"),
    @NamedQuery(name = "Company.selectAllByLastChange", query = "SELECT c FROM Company c ORDER BY c.cChanged DESC")
})
public class Company implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maCompany")
    private Collection<Mailaddress> mailaddressCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mlCompany")
    private Collection<Maillist> maillistCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aaCompany")
    private Collection<AnalyseAllowed> analyseAllowedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apCompany")
    private Collection<AnalysePoint> analysePointCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atCompany")
    private Collection<AnalyseType> analyseTypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "amCompany")
    private Collection<AnalyseMethod> analyseMethodCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adCompany")
    private Collection<AnalyseData> analyseDataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anCompany")
    private Collection<AnalyseNotify> analyseNotifyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acCompany")
    private Collection<AnalyseCategory> analyseCategoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgdCompany")
    private Collection<StaffGroupDef> staffGroupDefCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrCompany")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncfCompany")
    private Collection<NonConformiteFrequency> nonConformiteFrequencyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncnCompany")
    private Collection<NonConformiteNature> nonConformiteNatureCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncuCompany")
    private Collection<NonConformiteUnite> nonConformiteUniteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncgCompany")
    private Collection<NonConformiteGravity> nonConformiteGravityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pCompany")
    private Collection<Processus> processusCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "piCompany")
    private Collection<PointInfos> pointInfosCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "msCompany")
    private Collection<Mailsender> mailsenderCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paCompany")
    private Collection<ProcessAccess> processAccessCollection;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eCompany")
    private Collection<Equipement> equipementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uCompany")
    private Collection<Unite> uniteCollection;
    @JoinColumn(name = "c_entreprise", referencedColumnName = "e_entreprise", nullable = false)
    @ManyToOne(optional = false)
    private Entreprise cEntreprise;

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
    public Collection<Equipement> getEquipementCollection() {
        return equipementCollection;
    }

    public void setEquipementCollection(Collection<Equipement> equipementCollection) {
        this.equipementCollection = equipementCollection;
    }

    @XmlTransient
    public Collection<Unite> getUniteCollection() {
        return uniteCollection;
    }

    public void setUniteCollection(Collection<Unite> uniteCollection) {
        this.uniteCollection = uniteCollection;
    }

    public Entreprise getCEntreprise() {
        return cEntreprise;
    }

    public void setCEntreprise(Entreprise cEntreprise) {
        this.cEntreprise = cEntreprise;
    }

    
    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    @XmlTransient
    public Collection<NonConformiteFrequency> getNonConformiteFrequencyCollection() {
        return nonConformiteFrequencyCollection;
    }

    public void setNonConformiteFrequencyCollection(Collection<NonConformiteFrequency> nonConformiteFrequencyCollection) {
        this.nonConformiteFrequencyCollection = nonConformiteFrequencyCollection;
    }

    @XmlTransient
    public Collection<NonConformiteNature> getNonConformiteNatureCollection() {
        return nonConformiteNatureCollection;
    }

    public void setNonConformiteNatureCollection(Collection<NonConformiteNature> nonConformiteNatureCollection) {
        this.nonConformiteNatureCollection = nonConformiteNatureCollection;
    }

    @XmlTransient
    public Collection<NonConformiteUnite> getNonConformiteUniteCollection() {
        return nonConformiteUniteCollection;
    }

    public void setNonConformiteUniteCollection(Collection<NonConformiteUnite> nonConformiteUniteCollection) {
        this.nonConformiteUniteCollection = nonConformiteUniteCollection;
    }

    @XmlTransient
    public Collection<NonConformiteGravity> getNonConformiteGravityCollection() {
        return nonConformiteGravityCollection;
    }

    public void setNonConformiteGravityCollection(Collection<NonConformiteGravity> nonConformiteGravityCollection) {
        this.nonConformiteGravityCollection = nonConformiteGravityCollection;
    }

    @XmlTransient
    public Collection<Processus> getProcessusCollection() {
        return processusCollection;
    }

    @XmlTransient
    public Collection<PointInfos> getPointInfosCollection() {
        return pointInfosCollection;
    }

    @XmlTransient
    public Collection<Mailsender> getMailsenderCollection() {
        return mailsenderCollection;
    }
    
    @XmlTransient
    public Collection<ProcessAccess> getProcessAccessCollection() {
        return processAccessCollection;
    }

    @XmlTransient
    public Collection<AnalyseAllowed> getAnalyseAllowedCollection() {
        return analyseAllowedCollection;
    }

    public void setAnalyseAllowedCollection(Collection<AnalyseAllowed> analyseAllowedCollection) {
        this.analyseAllowedCollection = analyseAllowedCollection;
    }

    @XmlTransient
    public Collection<AnalysePoint> getAnalysePointCollection() {
        return analysePointCollection;
    }

    public void setAnalysePointCollection(Collection<AnalysePoint> analysePointCollection) {
        this.analysePointCollection = analysePointCollection;
    }

    @XmlTransient
    public Collection<AnalyseType> getAnalyseTypeCollection() {
        return analyseTypeCollection;
    }

    public void setAnalyseTypeCollection(Collection<AnalyseType> analyseTypeCollection) {
        this.analyseTypeCollection = analyseTypeCollection;
    }

    @XmlTransient
    public Collection<AnalyseMethod> getAnalyseMethodCollection() {
        return analyseMethodCollection;
    }

    public void setAnalyseMethodCollection(Collection<AnalyseMethod> analyseMethodCollection) {
        this.analyseMethodCollection = analyseMethodCollection;
    }

    @XmlTransient
    public Collection<AnalyseData> getAnalyseDataCollection() {
        return analyseDataCollection;
    }

    public void setAnalyseDataCollection(Collection<AnalyseData> analyseDataCollection) {
        this.analyseDataCollection = analyseDataCollection;
    }

    @XmlTransient
    public Collection<AnalyseNotify> getAnalyseNotifyCollection() {
        return analyseNotifyCollection;
    }

    public void setAnalyseNotifyCollection(Collection<AnalyseNotify> analyseNotifyCollection) {
        this.analyseNotifyCollection = analyseNotifyCollection;
    }

    @XmlTransient
    public Collection<AnalyseCategory> getAnalyseCategoryCollection() {
        return analyseCategoryCollection;
    }

    public void setAnalyseCategoryCollection(Collection<AnalyseCategory> analyseCategoryCollection) {
        this.analyseCategoryCollection = analyseCategoryCollection;
    }

    @XmlTransient
    public Collection<StaffGroupDef> getStaffGroupDefCollection() {
        return staffGroupDefCollection;
    }

    public void setStaffGroupDefCollection(Collection<StaffGroupDef> staffGroupDefCollection) {
        this.staffGroupDefCollection = staffGroupDefCollection;
    }

    @XmlTransient
    public Collection<Mailaddress> getMailaddressCollection() {
        return mailaddressCollection;
    }

    public void setMailaddressCollection(Collection<Mailaddress> mailaddressCollection) {
        this.mailaddressCollection = mailaddressCollection;
    }

    @XmlTransient
    public Collection<Maillist> getMaillistCollection() {
        return maillistCollection;
    }

    public void setMaillistCollection(Collection<Maillist> maillistCollection) {
        this.maillistCollection = maillistCollection;
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
        return "" + cCompany + " - " + cDesignation + " [" + cId + "]";
    }

   

}
