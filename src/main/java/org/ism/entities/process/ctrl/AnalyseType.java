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
import org.ism.entities.process.Unite;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_type", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"at_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseType.findAll", query = "SELECT a FROM AnalyseType a"),
    @NamedQuery(name = "AnalyseType.findByAtId", query = "SELECT a FROM AnalyseType a WHERE a.atId = :atId"),
    @NamedQuery(name = "AnalyseType.findByAtType", query = "SELECT a FROM AnalyseType a WHERE a.atType = :atType"),
    @NamedQuery(name = "AnalyseType.findByAtDesignation", query = "SELECT a FROM AnalyseType a WHERE a.atDesignation = :atDesignation"),
    @NamedQuery(name = "AnalyseType.findByAtTypeOfCompany", query = "SELECT a FROM AnalyseType a WHERE a.atType = :atType AND a.atCompany = :atCompany"),
    @NamedQuery(name = "AnalyseType.findByAtDesignationOfCompany", query = "SELECT a FROM AnalyseType a WHERE a.atDesignation = :atDesignation AND a.atCompany = :atCompany"),
    @NamedQuery(name = "AnalyseType.findByAtUnite", query = "SELECT a FROM AnalyseType a WHERE a.atUnite = :atUnite"),
    @NamedQuery(name = "AnalyseType.findByAtCategory", query = "SELECT a FROM AnalyseType a WHERE a.atCategory = :atCategory"),
    @NamedQuery(name = "AnalyseType.findByAtMethod", query = "SELECT a FROM AnalyseType a WHERE a.atMethod = :atMethod"),
    @NamedQuery(name = "AnalyseType.findByAtTypeUnite", query = "SELECT a FROM AnalyseType a WHERE a.atType = :atType AND a.atUnite = :atUnite"),
    @NamedQuery(name = "AnalyseType.findByAtTypeCategory", query = "SELECT a FROM AnalyseType a WHERE a.atType = :atType AND a.atCategory = :atCategory"),
    @NamedQuery(name = "AnalyseType.findByAtTypeMethod", query = "SELECT a FROM AnalyseType a WHERE a.atType = :atType AND a.atMethod = :atMethod"),
    @NamedQuery(name = "AnalyseType.findByAtDeleted", query = "SELECT a FROM AnalyseType a WHERE a.atDeleted = :atDeleted"),
    @NamedQuery(name = "AnalyseType.findByAtCreated", query = "SELECT a FROM AnalyseType a WHERE a.atCreated = :atCreated"),
    @NamedQuery(name = "AnalyseType.findByAtChanged", query = "SELECT a FROM AnalyseType a WHERE a.atChanged = :atChanged"),
    @NamedQuery(name = "AnalyseType.selectAllByLastChange", query = "SELECT a FROM AnalyseType a ORDER BY a.atChanged DESC")
})
public class AnalyseType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "at_id", nullable = false)
    private Integer atId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "at_type", nullable = false, length = 45)
    private String atType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "at_designation", nullable = false, length = 255)
    private String atDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "at_description", length = 65535)
    private String atDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "at_deleted", nullable = false)
    private boolean atDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "at_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date atCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "at_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date atChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aaType")
    private Collection<AnalyseAllowed> analyseAllowedCollection;
    @JoinColumn(name = "at_category", referencedColumnName = "ac_category")
    @ManyToOne
    private AnalyseCategory atCategory;
    @JoinColumn(name = "at_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company atCompany;
    @JoinColumn(name = "at_method", referencedColumnName = "am_method")
    @ManyToOne
    private AnalyseMethod atMethod;
    @JoinColumn(name = "at_unite", referencedColumnName = "u_unite", nullable = false)
    @ManyToOne(optional = false)
    private Unite atUnite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adType")
    private Collection<AnalyseData> analyseDataCollection;

    public AnalyseType() {
    }

    public AnalyseType(Integer atId) {
        this.atId = atId;
    }

    public AnalyseType(Integer atId, String atType, String atDesignation, boolean atDeleted, Date atCreated, Date atChanged) {
        this.atId = atId;
        this.atType = atType;
        this.atDesignation = atDesignation;
        this.atDeleted = atDeleted;
        this.atCreated = atCreated;
        this.atChanged = atChanged;
    }

    public Integer getAtId() {
        return atId;
    }

    public void setAtId(Integer atId) {
        this.atId = atId;
    }

    public String getAtType() {
        return atType;
    }

    public void setAtType(String atType) {
        this.atType = atType;
    }

    public String getAtDesignation() {
        return atDesignation;
    }

    public void setAtDesignation(String atDesignation) {
        this.atDesignation = atDesignation;
    }

    public String getAtDescription() {
        return atDescription;
    }

    public void setAtDescription(String atDescription) {
        this.atDescription = atDescription;
    }

    public boolean getAtDeleted() {
        return atDeleted;
    }

    public void setAtDeleted(boolean atDeleted) {
        this.atDeleted = atDeleted;
    }

    public Date getAtCreated() {
        return atCreated;
    }

    public void setAtCreated(Date atCreated) {
        this.atCreated = atCreated;
    }

    public Date getAtChanged() {
        return atChanged;
    }

    public void setAtChanged(Date atChanged) {
        this.atChanged = atChanged;
    }

    @XmlTransient
    public Collection<AnalyseAllowed> getAnalyseAllowedCollection() {
        return analyseAllowedCollection;
    }

    public void setAnalyseAllowedCollection(Collection<AnalyseAllowed> analyseAllowedCollection) {
        this.analyseAllowedCollection = analyseAllowedCollection;
    }

    public AnalyseCategory getAtCategory() {
        return atCategory;
    }

    public void setAtCategory(AnalyseCategory atCategory) {
        this.atCategory = atCategory;
    }

    public Company getAtCompany() {
        return atCompany;
    }

    public void setAtCompany(Company atCompany) {
        this.atCompany = atCompany;
    }

    public AnalyseMethod getAtMethod() {
        return atMethod;
    }

    public void setAtMethod(AnalyseMethod atMethod) {
        this.atMethod = atMethod;
    }

    public Unite getAtUnite() {
        return atUnite;
    }

    public void setAtUnite(Unite atUnite) {
        this.atUnite = atUnite;
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
        hash += (atId != null ? atId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseType)) {
            return false;
        }
        AnalyseType other = (AnalyseType) object;
        if ((this.atId == null && other.atId != null) || (this.atId != null && !this.atId.equals(other.atId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.AnalyseType[ atId=" + atId + " ]";
        return atType + " - " + atDesignation + "[" + atId + "]";
    }

}
