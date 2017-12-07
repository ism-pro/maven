/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.process.ctrl;

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
import org.ism.entities.admin.Company;
import org.ism.entities.process.Equipement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_point", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ap_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalysePoint.findAll", query = "SELECT a FROM AnalysePoint a"),
    @NamedQuery(name = "AnalysePoint.findByApId", query = "SELECT a FROM AnalysePoint a WHERE a.apId = :apId"),
    @NamedQuery(name = "AnalysePoint.findByApPoint", query = "SELECT a FROM AnalysePoint a WHERE a.apPoint = :apPoint"),
    @NamedQuery(name = "AnalysePoint.findByApDesignation", query = "SELECT a FROM AnalysePoint a WHERE a.apDesignation = :apDesignation"),
    @NamedQuery(name = "AnalysePoint.findByApPointOfCompany", query = "SELECT a FROM AnalysePoint a WHERE a.apPoint = :apPoint AND a.apCompany = :apCompany"),
    @NamedQuery(name = "AnalysePoint.findByApDesignationOfCompany", query = "SELECT a FROM AnalysePoint a WHERE a.apDesignation = :apDesignation AND a.apCompany = :apCompany"),
    @NamedQuery(name = "AnalysePoint.findByApEquipement", query = "SELECT a FROM AnalysePoint a WHERE a.apEquipement = :apEquipement"),
    @NamedQuery(name = "AnalysePoint.findByApEquipementStr", query = "SELECT a FROM AnalysePoint a WHERE a.apEquipement.eEquipement = :apEquipement"),
    @NamedQuery(name = "AnalysePoint.findByApPointEquipement", query = "SELECT a FROM AnalysePoint a WHERE a.apPoint = :apPoint AND a.apEquipement = :apEquipement"),
    @NamedQuery(name = "AnalysePoint.findByApDeleted", query = "SELECT a FROM AnalysePoint a WHERE a.apDeleted = :apDeleted"),
    @NamedQuery(name = "AnalysePoint.findByApCreated", query = "SELECT a FROM AnalysePoint a WHERE a.apCreated = :apCreated"),
    @NamedQuery(name = "AnalysePoint.findByApChanged", query = "SELECT a FROM AnalysePoint a WHERE a.apChanged = :apChanged"),
    @NamedQuery(name = "AnalysePoint.selectAllByLastChange", query = "SELECT a FROM AnalysePoint a ORDER BY a.apChanged DESC")
})
public class AnalysePoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ap_id", nullable = false)
    private Integer apId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ap_point", nullable = false, length = 45)
    private String apPoint;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ap_designation", nullable = false, length = 255)
    private String apDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "ap_description", length = 65535)
    private String apDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ap_deleted", nullable = false)
    private boolean apDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ap_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date apCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ap_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date apChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aaPoint")
    private Collection<AnalyseAllowed> analyseAllowedCollection;
    @JoinColumn(name = "ap_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company apCompany;
    @JoinColumn(name = "ap_equipement", referencedColumnName = "e_equipement", nullable = false)
    @ManyToOne(optional = false)
    private Equipement apEquipement;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adPoint")
    private Collection<AnalyseData> analyseDataCollection;

    public AnalysePoint() {
    }

    public AnalysePoint(Integer apId) {
        this.apId = apId;
    }

    public AnalysePoint(Integer apId, String apPoint, String apDesignation, boolean apDeleted, Date apCreated, Date apChanged) {
        this.apId = apId;
        this.apPoint = apPoint;
        this.apDesignation = apDesignation;
        this.apDeleted = apDeleted;
        this.apCreated = apCreated;
        this.apChanged = apChanged;
    }

    public Integer getApId() {
        return apId;
    }

    public void setApId(Integer apId) {
        this.apId = apId;
    }

    public String getApPoint() {
        return apPoint;
    }

    public void setApPoint(String apPoint) {
        this.apPoint = apPoint;
    }

    public String getApDesignation() {
        return apDesignation;
    }

    public void setApDesignation(String apDesignation) {
        this.apDesignation = apDesignation;
    }

    public String getApDescription() {
        return apDescription;
    }

    public void setApDescription(String apDescription) {
        this.apDescription = apDescription;
    }

    public boolean getApDeleted() {
        return apDeleted;
    }

    public void setApDeleted(boolean apDeleted) {
        this.apDeleted = apDeleted;
    }

    public Date getApCreated() {
        return apCreated;
    }

    public void setApCreated(Date apCreated) {
        this.apCreated = apCreated;
    }

    public Date getApChanged() {
        return apChanged;
    }

    public void setApChanged(Date apChanged) {
        this.apChanged = apChanged;
    }

    @XmlTransient
    public Collection<AnalyseAllowed> getAnalyseAllowedCollection() {
        return analyseAllowedCollection;
    }

    public void setAnalyseAllowedCollection(Collection<AnalyseAllowed> analyseAllowedCollection) {
        this.analyseAllowedCollection = analyseAllowedCollection;
    }

    public Company getApCompany() {
        return apCompany;
    }

    public void setApCompany(Company apCompany) {
        this.apCompany = apCompany;
    }

    public Equipement getApEquipement() {
        return apEquipement;
    }

    public void setApEquipement(Equipement apEquipement) {
        this.apEquipement = apEquipement;
    }

    @XmlTransient
    public Collection<AnalyseData> getAnalyseDataCollection() {
        return analyseDataCollection;
    }

    public void setAnalyseDataCollection(Collection<AnalyseData> analyseDataCollection) {
        this.analyseDataCollection = analyseDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apId != null ? apId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalysePoint)) {
            return false;
        }
        AnalysePoint other = (AnalysePoint) object;
        if ((this.apId == null && other.apId != null) || (this.apId != null && !this.apId.equals(other.apId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.AnalysePoint[ apId=" + apId + " ]";
        return apPoint + " - " + apDesignation + "[" + apId +"]";
    }

}
