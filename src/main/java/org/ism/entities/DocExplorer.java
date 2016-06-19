/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "doc_explorer", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dc_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocExplorer.findAll", query = "SELECT d FROM DocExplorer d"),
    @NamedQuery(name = "DocExplorer.findByDcId", query = "SELECT d FROM DocExplorer d WHERE d.dcId = :dcId"),
    @NamedQuery(name = "DocExplorer.findByDcDesignation", query = "SELECT d FROM DocExplorer d WHERE d.dcDesignation = :dcDesignation"),
    @NamedQuery(name = "DocExplorer.findByDcVersion", query = "SELECT d FROM DocExplorer d WHERE d.dcVersion = :dcVersion"),
    @NamedQuery(name = "DocExplorer.findByDcLink", query = "SELECT d FROM DocExplorer d WHERE d.dcLink = :dcLink"),
    @NamedQuery(name = "DocExplorer.findByDcApprouved", query = "SELECT d FROM DocExplorer d WHERE d.dcApprouved = :dcApprouved"),
    @NamedQuery(name = "DocExplorer.findByDcActivated", query = "SELECT d FROM DocExplorer d WHERE d.dcActivated = :dcActivated"),
    @NamedQuery(name = "DocExplorer.findByDcCreated", query = "SELECT d FROM DocExplorer d WHERE d.dcCreated = :dcCreated"),
    @NamedQuery(name = "DocExplorer.findByDcChanged", query = "SELECT d FROM DocExplorer d WHERE d.dcChanged = :dcChanged"),
    @NamedQuery(name = "DocExplorer.selectAllByLastChange", query = "SELECT d FROM DocExplorer d ORDER BY d.dcChanged DESC")
})
public class DocExplorer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dc_id", nullable = false)
    private Integer dcId;
    @Size(max = 255)
    @Column(name = "dc_designation", length = 255)
    private String dcDesignation;
    @Size(max = 128)
    @Column(name = "dc_version", length = 128)
    private String dcVersion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "dc_comment", length = 2147483647)
    private String dcComment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "dc_link", nullable = false, length = 512)
    private String dcLink;
    @Column(name = "dc_approuved")
    @Temporal(TemporalType.DATE)
    private Date dcApprouved;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dc_activated", nullable = false)
    private boolean dcActivated = true;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dc_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dc_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcChanged;
    @JoinColumn(name = "dc_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company dcCompany;
    @JoinColumn(name = "dc_type", referencedColumnName = "dct_type", nullable = false)
    @ManyToOne(optional = false)
    private DocType dcType;
    @JoinColumn(name = "dc_processus", referencedColumnName = "p_processus", nullable = false)
    @ManyToOne(optional = false)
    private Processus dcProcessus;

    public DocExplorer() {
    }

    public DocExplorer(Integer dcId) {
        this.dcId = dcId;
    }

    public DocExplorer(Integer dcId, String dcLink, boolean dcActivated, Date dcCreated, Date dcChanged) {
        this.dcId = dcId;
        this.dcLink = dcLink;
        this.dcActivated = dcActivated;
        this.dcCreated = dcCreated;
        this.dcChanged = dcChanged;
    }

    public Integer getDcId() {
        return dcId;
    }

    public void setDcId(Integer dcId) {
        this.dcId = dcId;
    }

    public String getDcDesignation() {
        return dcDesignation;
    }

    public void setDcDesignation(String dcDesignation) {
        this.dcDesignation = dcDesignation;
    }

    public String getDcVersion() {
        return dcVersion;
    }

    public void setDcVersion(String dcVersion) {
        this.dcVersion = dcVersion;
    }

    public String getDcComment() {
        return dcComment;
    }

    public void setDcComment(String dcComment) {
        this.dcComment = dcComment;
    }

    public String getDcLink() {
        return dcLink;
    }

    public void setDcLink(String dcLink) {
        this.dcLink = dcLink;
    }

    public Date getDcApprouved() {
        return dcApprouved;
    }

    public void setDcApprouved(Date dcApprouved) {
        this.dcApprouved = dcApprouved;
    }

    public boolean getDcActivated() {
        return dcActivated;
    }

    public void setDcActivated(boolean dcActivated) {
        this.dcActivated = dcActivated;
    }

    public Date getDcCreated() {
        return dcCreated;
    }

    public void setDcCreated(Date dcCreated) {
        this.dcCreated = dcCreated;
    }

    public Date getDcChanged() {
        return dcChanged;
    }

    public void setDcChanged(Date dcChanged) {
        this.dcChanged = dcChanged;
    }

    public Company getDcCompany() {
        return dcCompany;
    }

    public void setDcCompany(Company dcCompany) {
        this.dcCompany = dcCompany;
    }

    public DocType getDcType() {
        return dcType;
    }

    public void setDcType(DocType dcType) {
        this.dcType = dcType;
    }

    public Processus getDcProcessus() {
        return dcProcessus;
    }

    public void setDcProcessus(Processus dcProcessus) {
        this.dcProcessus = dcProcessus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcId != null ? dcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocExplorer)) {
            return false;
        }
        DocExplorer other = (DocExplorer) object;
        if ((this.dcId == null && other.dcId != null) || (this.dcId != null && !this.dcId.equals(other.dcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.DocExplorer[ dcId=" + dcId + " ]";
    }
    
}
