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
@Table(name = "analyse_method", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"am_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseMethod.findAll", query = "SELECT a FROM AnalyseMethod a"),
    @NamedQuery(name = "AnalyseMethod.findByAmId", query = "SELECT a FROM AnalyseMethod a WHERE a.amId = :amId"),
    @NamedQuery(name = "AnalyseMethod.findByAmMethod", query = "SELECT a FROM AnalyseMethod a WHERE a.amMethod = :amMethod"),
    @NamedQuery(name = "AnalyseMethod.findByAmDesignation", query = "SELECT a FROM AnalyseMethod a WHERE a.amDesignation = :amDesignation"),
    @NamedQuery(name = "AnalyseMethod.findByAmMethodOfCompany", query = "SELECT a FROM AnalyseMethod a WHERE a.amMethod = :amMethod AND a.amCompany = :amCompany"),
    @NamedQuery(name = "AnalyseMethod.findByAmDesignationOfCompany", query = "SELECT a FROM AnalyseMethod a WHERE a.amDesignation = :amDesignation AND a.amCompany = :amCompany"),
    @NamedQuery(name = "AnalyseMethod.findByAmDeleted", query = "SELECT a FROM AnalyseMethod a WHERE a.amDeleted = :amDeleted"),
    @NamedQuery(name = "AnalyseMethod.findByAmCreated", query = "SELECT a FROM AnalyseMethod a WHERE a.amCreated = :amCreated"),
    @NamedQuery(name = "AnalyseMethod.findByAmChanged", query = "SELECT a FROM AnalyseMethod a WHERE a.amChanged = :amChanged"),
    @NamedQuery(name = "AnalyseMethod.selectAllByLastChange", query = "SELECT a FROM AnalyseMethod a ORDER BY a.amChanged DESC")
})
public class AnalyseMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "am_id", nullable = false)
    private Integer amId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "am_method", nullable = false, length = 45)
    private String amMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "am_designation", nullable = false, length = 255)
    private String amDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "am_description", length = 65535)
    private String amDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "am_deleted", nullable = false)
    private boolean amDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "am_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date amCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "am_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date amChanged;
    @OneToMany(mappedBy = "atMethod")
    private Collection<AnalyseType> analyseTypeCollection;
    @JoinColumn(name = "am_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company amCompany;

    public AnalyseMethod() {
    }

    public AnalyseMethod(Integer amId) {
        this.amId = amId;
    }

    public AnalyseMethod(Integer amId, String amMethod, String amDesignation, boolean amDeleted, Date amCreated, Date amChanged) {
        this.amId = amId;
        this.amMethod = amMethod;
        this.amDesignation = amDesignation;
        this.amDeleted = amDeleted;
        this.amCreated = amCreated;
        this.amChanged = amChanged;
    }

    public Integer getAmId() {
        return amId;
    }

    public void setAmId(Integer amId) {
        this.amId = amId;
    }

    public String getAmMethod() {
        return amMethod;
    }

    public void setAmMethod(String amMethod) {
        this.amMethod = amMethod;
    }

    public String getAmDesignation() {
        return amDesignation;
    }

    public void setAmDesignation(String amDesignation) {
        this.amDesignation = amDesignation;
    }

    public String getAmDescription() {
        return amDescription;
    }

    public void setAmDescription(String amDescription) {
        this.amDescription = amDescription;
    }

    public boolean getAmDeleted() {
        return amDeleted;
    }

    public void setAmDeleted(boolean amDeleted) {
        this.amDeleted = amDeleted;
    }

    public Date getAmCreated() {
        return amCreated;
    }

    public void setAmCreated(Date amCreated) {
        this.amCreated = amCreated;
    }

    public Date getAmChanged() {
        return amChanged;
    }

    public void setAmChanged(Date amChanged) {
        this.amChanged = amChanged;
    }

    @XmlTransient
    public Collection<AnalyseType> getAnalyseTypeCollection() {
        return analyseTypeCollection;
    }

    public void setAnalyseTypeCollection(Collection<AnalyseType> analyseTypeCollection) {
        this.analyseTypeCollection = analyseTypeCollection;
    }

    public Company getAmCompany() {
        return amCompany;
    }

    public void setAmCompany(Company amCompany) {
        this.amCompany = amCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amId != null ? amId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseMethod)) {
            return false;
        }
        AnalyseMethod other = (AnalyseMethod) object;
        if ((this.amId == null && other.amId != null) || (this.amId != null && !this.amId.equals(other.amId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.AnalyseMethod[ amId=" + amId + " ]";
    }

}
