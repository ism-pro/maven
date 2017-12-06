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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_category", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ac_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseCategory.findAll", query = "SELECT a FROM AnalyseCategory a"),
    @NamedQuery(name = "AnalyseCategory.findByAcId", query = "SELECT a FROM AnalyseCategory a WHERE a.acId = :acId"),
    @NamedQuery(name = "AnalyseCategory.findByAcCategory", query = "SELECT a FROM AnalyseCategory a WHERE a.acCategory = :acCategory"),
    @NamedQuery(name = "AnalyseCategory.findByAcDesignation", query = "SELECT a FROM AnalyseCategory a WHERE a.acDesignation = :acDesignation"),
    @NamedQuery(name = "AnalyseCategory.findByAcCategoryOfCompany", query = "SELECT a FROM AnalyseCategory a WHERE a.acCategory = :acCategory AND a.acCompany = :acCompany"),
    @NamedQuery(name = "AnalyseCategory.findByAcDesignationOfCompany", query = "SELECT a FROM AnalyseCategory a WHERE a.acDesignation = :acDesignation AND a.acCompany = :acCompany"),
    @NamedQuery(name = "AnalyseCategory.findByAcDeleted", query = "SELECT a FROM AnalyseCategory a WHERE a.acDeleted = :acDeleted"),
    @NamedQuery(name = "AnalyseCategory.findByAcCreated", query = "SELECT a FROM AnalyseCategory a WHERE a.acCreated = :acCreated"),
    @NamedQuery(name = "AnalyseCategory.findByAcChanged", query = "SELECT a FROM AnalyseCategory a WHERE a.acChanged = :acChanged"),
    @NamedQuery(name = "AnalyseCategory.selectAllByLastChange", query = "SELECT a FROM AnalyseCategory a ORDER BY a.acChanged DESC")
})
public class AnalyseCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ac_id", nullable = false)
    private Integer acId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ac_category", nullable = false, length = 45)
    private String acCategory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ac_designation", nullable = false, length = 255)
    private String acDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "ac_description", length = 65535)
    private String acDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ac_deleted", nullable = false)
    private boolean acDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ac_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date acCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ac_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date acChanged;
    @OneToMany(mappedBy = "atCategory")
    private Collection<AnalyseType> analyseTypeCollection;
    @JoinColumn(name = "ac_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company acCompany;

    public AnalyseCategory() {
    }

    public AnalyseCategory(Integer acId) {
        this.acId = acId;
    }

    public AnalyseCategory(Integer acId, String acCategory, String acDesignation, boolean acDeleted, Date acCreated, Date acChanged) {
        this.acId = acId;
        this.acCategory = acCategory;
        this.acDesignation = acDesignation;
        this.acDeleted = acDeleted;
        this.acCreated = acCreated;
        this.acChanged = acChanged;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getAcCategory() {
        return acCategory;
    }

    public void setAcCategory(String acCategory) {
        this.acCategory = acCategory;
    }

    public String getAcDesignation() {
        return acDesignation;
    }

    public void setAcDesignation(String acDesignation) {
        this.acDesignation = acDesignation;
    }

    public String getAcDescription() {
        return acDescription;
    }

    public void setAcDescription(String acDescription) {
        this.acDescription = acDescription;
    }

    public boolean getAcDeleted() {
        return acDeleted;
    }

    public void setAcDeleted(boolean acDeleted) {
        this.acDeleted = acDeleted;
    }

    public Date getAcCreated() {
        return acCreated;
    }

    public void setAcCreated(Date acCreated) {
        this.acCreated = acCreated;
    }

    public Date getAcChanged() {
        return acChanged;
    }

    public void setAcChanged(Date acChanged) {
        this.acChanged = acChanged;
    }

    @XmlTransient
    public Collection<AnalyseType> getAnalyseTypeCollection() {
        return analyseTypeCollection;
    }

    public void setAnalyseTypeCollection(Collection<AnalyseType> analyseTypeCollection) {
        this.analyseTypeCollection = analyseTypeCollection;
    }

    public Company getAcCompany() {
        return acCompany;
    }

    public void setAcCompany(Company acCompany) {
        this.acCompany = acCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acId != null ? acId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseCategory)) {
            return false;
        }
        AnalyseCategory other = (AnalyseCategory) object;
        if ((this.acId == null && other.acId != null) || (this.acId != null && !this.acId.equals(other.acId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.AnalyseCategory[ acId=" + acId + " ]";
    }

}
