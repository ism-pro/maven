/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.smq;

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
@Table(name = "doc_type", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dct_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocType.findAll", query = "SELECT d FROM DocType d"),
    @NamedQuery(name = "DocType.findByDctId", query = "SELECT d FROM DocType d WHERE d.dctId = :dctId"),
    @NamedQuery(name = "DocType.findByDctType", query = "SELECT d FROM DocType d WHERE d.dctType = :dctType"),
    @NamedQuery(name = "DocType.findByDctDesignation", query = "SELECT d FROM DocType d WHERE d.dctDesignation = :dctDesignation"),
    @NamedQuery(name = "DocType.findByDctTypeOfCompany", query = "SELECT d FROM DocType d WHERE d.dctType = :dctType AND d.dctCompany = :dctCompany"),
    @NamedQuery(name = "DocType.findByDctDesignationOfCompany", query = "SELECT d FROM DocType d WHERE d.dctDesignation = :dctDesignation AND d.dctCompany = :dctCompany"),
    @NamedQuery(name = "DocType.findByDctDeleted", query = "SELECT d FROM DocType d WHERE d.dctDeleted = :dctDeleted"),
    @NamedQuery(name = "DocType.findByDctCreated", query = "SELECT d FROM DocType d WHERE d.dctCreated = :dctCreated"),
    @NamedQuery(name = "DocType.findByDctChanged", query = "SELECT d FROM DocType d WHERE d.dctChanged = :dctChanged"),
    @NamedQuery(name = "DocType.selectAllByLastChange", query = "SELECT d FROM DocType d ORDER BY d.dctChanged DESC")

})
public class DocType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dct_id", nullable = false)
    private Integer dctId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "dct_type", nullable = false, length = 45)
    private String dctType;
    @Size(max = 255)
    @Column(name = "dct_designation", length = 255)
    private String dctDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dct_deleted", nullable = false)
    private boolean dctDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dct_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dctCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dct_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dctChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcType")
    private Collection<DocExplorer> docExplorerCollection;
    @JoinColumn(name = "dct_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company dctCompany;

    public DocType() {
    }

    public DocType(Integer dctId) {
        this.dctId = dctId;
    }

    public DocType(Integer dctId, String dctType, boolean dctDeleted, Date dctCreated, Date dctChanged) {
        this.dctId = dctId;
        this.dctType = dctType;
        this.dctDeleted = dctDeleted;
        this.dctCreated = dctCreated;
        this.dctChanged = dctChanged;
    }

    public Integer getDctId() {
        return dctId;
    }

    public void setDctId(Integer dctId) {
        this.dctId = dctId;
    }

    public String getDctType() {
        return dctType;
    }

    public void setDctType(String dctType) {
        this.dctType = dctType;
    }

    public String getDctDesignation() {
        return dctDesignation;
    }

    public void setDctDesignation(String dctDesignation) {
        this.dctDesignation = dctDesignation;
    }

    public boolean getDctDeleted() {
        return dctDeleted;
    }

    public void setDctDeleted(boolean dctDeleted) {
        this.dctDeleted = dctDeleted;
    }

    public Date getDctCreated() {
        return dctCreated;
    }

    public void setDctCreated(Date dctCreated) {
        this.dctCreated = dctCreated;
    }

    public Date getDctChanged() {
        return dctChanged;
    }

    public void setDctChanged(Date dctChanged) {
        this.dctChanged = dctChanged;
    }

    @XmlTransient
    public Collection<DocExplorer> getDocExplorerCollection() {
        return docExplorerCollection;
    }

    public void setDocExplorerCollection(Collection<DocExplorer> docExplorerCollection) {
        this.docExplorerCollection = docExplorerCollection;
    }

    public Company getDctCompany() {
        return dctCompany;
    }

    public void setDctCompany(Company dctCompany) {
        this.dctCompany = dctCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dctId != null ? dctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocType)) {
            return false;
        }
        DocType other = (DocType) object;
        if ((this.dctId == null && other.dctId != null) || (this.dctId != null && !this.dctId.equals(other.dctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.DocType[ dctId=" + dctId + " ]";
    }

}
