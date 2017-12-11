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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.ism.entities.admin.Maillist;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.entities.hr.Staff;

/**
 * Processus
 * 
 * @author r.hendrick
 */
@Entity
@Table(name = "processus", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"p_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processus.findAll", query = "SELECT p FROM Processus p"),
    @NamedQuery(name = "Processus.findByPId", query = "SELECT p FROM Processus p WHERE p.pId = :pId"),
    @NamedQuery(name = "Processus.findByPProcessus", query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus"),
    @NamedQuery(name = "Processus.findByPDesignation", query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation"),
    @NamedQuery(name = "Processus.findByPProcessusOfCompany", query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus AND p.pCompany = :pCompany"),
    @NamedQuery(name = "Processus.findByPDesignationOfCompany", query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation AND p.pCompany = :pCompany"),
    @NamedQuery(name = "Processus.findByPDeleted", query = "SELECT p FROM Processus p WHERE p.pDeleted = :pDeleted"),
    @NamedQuery(name = "Processus.findByPCreated", query = "SELECT p FROM Processus p WHERE p.pCreated = :pCreated"),
    @NamedQuery(name = "Processus.findByPChanged", query = "SELECT p FROM Processus p WHERE p.pChanged = :pChanged"),
    @NamedQuery(name = "Processus.selectAllByLastChange", query = "SELECT p FROM Processus p ORDER BY p.pChanged DESC"),
    @NamedQuery(name = "Processus.findAllApprouvedItems", query = "SELECT p FROM Processus p WHERE p.pDeleted=0 ORDER BY p.pChanged DESC"),
    @NamedQuery(name = "Processus.findAllApprouvedItemsAsString", query = "SELECT concat(p.pProcessus, \" - \", p.pDesignation) FROM Processus p WHERE p.pDeleted=0 ORDER BY p.pChanged DESC")
})
public class Processus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "p_id", nullable = false)
    private Integer pId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "p_processus", nullable = false, length = 45)
    private String pProcessus;
    @Size(max = 255)
    @Column(name = "p_designation", length = 255)
    private String pDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "p_deleted", nullable = false)
    private boolean pDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "p_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "p_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pChanged;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrProcessus")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcProcessus")
    private Collection<DocExplorer> docExplorerCollection;

    @JoinColumn(name = "p_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company pCompany;
    @JoinColumn(name = "p_staffmanager", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff pStaffmanager;

    @OneToMany(mappedBy = "stProcessus")
    private Collection<Staff> staffCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mlProcessus")
    private Collection<Maillist> maillistCollection;

    public Processus() {
    }

    public Processus(Integer pId) {
        this.pId = pId;
    }

    public Processus(Integer pId, String pProcessus, boolean pDeleted, Date pCreated, Date pChanged) {
        this.pId = pId;
        this.pProcessus = pProcessus;
        this.pDeleted = pDeleted;
        this.pCreated = pCreated;
        this.pChanged = pChanged;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getPProcessus() {
        return pProcessus;
    }

    public void setPProcessus(String pProcessus) {
        this.pProcessus = pProcessus;
    }

    public String getPDesignation() {
        return pDesignation;
    }

    public void setPDesignation(String pDesignation) {
        this.pDesignation = pDesignation;
    }

    public boolean getPDeleted() {
        return pDeleted;
    }

    public void setPDeleted(boolean pDeleted) {
        this.pDeleted = pDeleted;
    }

    public Date getPCreated() {
        return pCreated;
    }

    public void setPCreated(Date pCreated) {
        this.pCreated = pCreated;
    }

    public Date getPChanged() {
        return pChanged;
    }

    public void setPChanged(Date pChanged) {
        this.pChanged = pChanged;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    @XmlTransient
    public Collection<DocExplorer> getDocExplorerCollection() {
        return docExplorerCollection;
    }

    public void setDocExplorerCollection(Collection<DocExplorer> docExplorerCollection) {
        this.docExplorerCollection = docExplorerCollection;
    }

    @XmlTransient
    public Collection<Staff> getStaffCollection() {
        return staffCollection;
    }

    public void setStaffCollection(Collection<Staff> staffCollection) {
        this.staffCollection = staffCollection;
    }

    @XmlTransient
    public Collection<Maillist> getMaillistCollection() {
        return maillistCollection;
    }

    public void setMaillistCollection(Collection<Maillist> maillistCollection) {
        this.maillistCollection = maillistCollection;
    }
    
    public Company getPCompany() {
        return pCompany;
    }

    public void setPCompany(Company pCompany) {
        this.pCompany = pCompany;
    }

    public Staff getPStaffmanager() {
        return pStaffmanager;
    }

    public void setPStaffmanager(Staff pStaffmanager) {
        this.pStaffmanager = pStaffmanager;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pId != null ? pId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Processus)) {
            return false;
        }
        Processus other = (Processus) object;
        if ((this.pId == null && other.pId != null) || (this.pId != null && !this.pId.equals(other.pId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.Processus[ pId=" + pId + " ]";
        return pProcessus + " - " + pDesignation + " [" + pId + "]";
    }


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////


}
