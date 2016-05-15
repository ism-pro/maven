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
@Table(name = "lab_analyse_category", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lac_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LabAnalyseCategory.findAll", query = "SELECT l FROM LabAnalyseCategory l"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacId", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacId = :lacId"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacCategory", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacCategory = :lacCategory"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacDesignation", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacDesignation = :lacDesignation"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacDeleted", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacDeleted = :lacDeleted"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacCreated", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacCreated = :lacCreated"),
    @NamedQuery(name = "LabAnalyseCategory.findByLacChanged", query = "SELECT l FROM LabAnalyseCategory l WHERE l.lacChanged = :lacChanged")})
public class LabAnalyseCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lac_id", nullable = false)
    private Integer lacId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lac_category", nullable = false, length = 45)
    private String lacCategory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lac_designation", nullable = false, length = 255)
    private String lacDesignation;
    @Lob
    @Size(max = 65535)
    @Column(name = "lac_description", length = 65535)
    private String lacDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lac_deleted", nullable = false)
    private boolean lacDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lac_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lacCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lac_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lacChanged;
    @JoinColumn(name = "lac_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company lacCompany;
    @OneToMany(mappedBy = "latCategory")
    private Collection<LabAnalyseType> labAnalyseTypeCollection;

    public LabAnalyseCategory() {
    }

    public LabAnalyseCategory(Integer lacId) {
        this.lacId = lacId;
    }

    public LabAnalyseCategory(Integer lacId, String lacCategory, String lacDesignation, boolean lacDeleted, Date lacCreated, Date lacChanged) {
        this.lacId = lacId;
        this.lacCategory = lacCategory;
        this.lacDesignation = lacDesignation;
        this.lacDeleted = lacDeleted;
        this.lacCreated = lacCreated;
        this.lacChanged = lacChanged;
    }

    public Integer getLacId() {
        return lacId;
    }

    public void setLacId(Integer lacId) {
        this.lacId = lacId;
    }

    public String getLacCategory() {
        return lacCategory;
    }

    public void setLacCategory(String lacCategory) {
        this.lacCategory = lacCategory;
    }

    public String getLacDesignation() {
        return lacDesignation;
    }

    public void setLacDesignation(String lacDesignation) {
        this.lacDesignation = lacDesignation;
    }

    public String getLacDescription() {
        return lacDescription;
    }

    public void setLacDescription(String lacDescription) {
        this.lacDescription = lacDescription;
    }

    public boolean getLacDeleted() {
        return lacDeleted;
    }

    public void setLacDeleted(boolean lacDeleted) {
        this.lacDeleted = lacDeleted;
    }

    public Date getLacCreated() {
        return lacCreated;
    }

    public void setLacCreated(Date lacCreated) {
        this.lacCreated = lacCreated;
    }

    public Date getLacChanged() {
        return lacChanged;
    }

    public void setLacChanged(Date lacChanged) {
        this.lacChanged = lacChanged;
    }

    public Company getLacCompany() {
        return lacCompany;
    }

    public void setLacCompany(Company lacCompany) {
        this.lacCompany = lacCompany;
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
        hash += (lacId != null ? lacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabAnalyseCategory)) {
            return false;
        }
        LabAnalyseCategory other = (LabAnalyseCategory) object;
        if ((this.lacId == null && other.lacId != null) || (this.lacId != null && !this.lacId.equals(other.lacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.LabAnalyseCategory[ lacId=" + lacId + " ]";
    }
    
}
