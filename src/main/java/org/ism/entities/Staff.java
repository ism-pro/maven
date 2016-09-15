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
@Table(catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"st_id", "st_staff"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByStId", query = "SELECT s FROM Staff s WHERE s.stId = :stId"),
    @NamedQuery(name = "Staff.findByStStaff", query = "SELECT s FROM Staff s WHERE s.stStaff = :stStaff"),
    @NamedQuery(name = "Staff.findByStPassword", query = "SELECT s FROM Staff s WHERE s.stPassword = :stPassword"),
    @NamedQuery(name = "Staff.findByStLastname", query = "SELECT s FROM Staff s WHERE s.stLastname = :stLastname"),
    @NamedQuery(name = "Staff.findByStFirstname", query = "SELECT s FROM Staff s WHERE s.stFirstname = :stFirstname"),
    @NamedQuery(name = "Staff.findByStMiddlename", query = "SELECT s FROM Staff s WHERE s.stMiddlename = :stMiddlename"),
    @NamedQuery(name = "Staff.findByStBorned", query = "SELECT s FROM Staff s WHERE s.stBorned = :stBorned"),
    @NamedQuery(name = "Staff.findByStMaxInactiveInterval", query = "SELECT s FROM Staff s WHERE s.stMaxInactiveInterval = :stMaxInactiveInterval"),
    @NamedQuery(name = "Staff.findByStActivated", query = "SELECT s FROM Staff s WHERE s.stActivated = :stActivated"),
    @NamedQuery(name = "Staff.findByStDeleted", query = "SELECT s FROM Staff s WHERE s.stDeleted = :stDeleted"),
    @NamedQuery(name = "Staff.findByStCreated", query = "SELECT s FROM Staff s WHERE s.stCreated = :stCreated"),
    @NamedQuery(name = "Staff.findByStChanged", query = "SELECT s FROM Staff s WHERE s.stChanged = :stChanged"),
    @NamedQuery(name = "Staff.countAllowed", query = "SELECT count(s) FROM Staff s WHERE s.stStaff = :stStaff AND s.stActivated=1"),
    @NamedQuery(name = "Staff.findByAllowed", query = "SELECT s FROM Staff s WHERE s.stStaff = :stStaff AND s.stActivated=1"),
    @NamedQuery(name = "Staff.countActiveUndeleted", query = "SELECT count(s) FROM Staff s WHERE s.stStaff = :stStaff AND s.stActivated=1 AND s.stDeleted=0"),
    @NamedQuery(name = "Staff.findByActiveUndeleted", query = "SELECT s FROM Staff s WHERE s.stStaff = :stStaff AND s.stActivated=1 AND s.stDeleted=0"),
    @NamedQuery(name = "Staff.selectAllByLastChange", query = "SELECT s FROM Staff s ORDER BY s.stChanged DESC")
})
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "st_id", nullable = false)
    private Integer stId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "st_staff", nullable = false, length = 45)
    private String stStaff;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "st_password", nullable = false, length = 256)
    private String stPassword;
    @Size(max = 45)
    @Column(name = "st_lastname", length = 45)
    private String stLastname;
    @Size(max = 45)
    @Column(name = "st_firstname", length = 45)
    private String stFirstname;
    @Size(max = 45)
    @Column(name = "st_middlename", length = 45)
    private String stMiddlename;
    @Column(name = "st_borned")
    @Temporal(TemporalType.DATE)
    private Date stBorned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_maxInactiveInterval", nullable = false)
    private int stMaxInactiveInterval = 1800;
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_activated", nullable = false)
    private boolean stActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_deleted", nullable = false)
    private boolean stDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "st_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stChanged;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncrStaff")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;

    @OneToMany(mappedBy = "ncrApprouver")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection4;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncaStaff")
    private Collection<NonConformiteActions> nonConformiteActionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncaStaffApprouver")
    private Collection<NonConformiteActions> nonConformiteActionsCollection1;

    @JoinColumn(name = "st_genre", referencedColumnName = "genre")
    @ManyToOne
    private IsmGenre stGenre;

    @OneToMany(mappedBy = "pStaffmanager")
    private Collection<Processus> processusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgStaff")
    private Collection<StaffGroups> staffGroupsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stsStaff")
    private Collection<StaffSetup> staffSetupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lsSampler")
    private Collection<LabSample> labSampleCollection;

    public Staff() {
    }

    public Staff(Integer stId) {
        this.stId = stId;
    }

    public Staff(Integer stId, String stStaff, String stPassword, int stMaxInactiveInterval, boolean stActivated, boolean stDeleted, Date stCreated, Date stChanged) {
        this.stId = stId;
        this.stStaff = stStaff;
        this.stPassword = stPassword;
        this.stMaxInactiveInterval = stMaxInactiveInterval;
        this.stActivated = stActivated;
        this.stDeleted = stDeleted;
        this.stCreated = stCreated;
        this.stChanged = stChanged;
    }

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getStStaff() {
        return stStaff;
    }

    public void setStStaff(String stStaff) {
        this.stStaff = stStaff;
    }

    public String getStPassword() {
        return stPassword;
    }

    public void setStPassword(String stPassword) {
        this.stPassword = stPassword;
    }

    public String getStLastname() {
        return stLastname;
    }

    public void setStLastname(String stLastname) {
        this.stLastname = stLastname;
    }

    public String getStFirstname() {
        return stFirstname;
    }

    public void setStFirstname(String stFirstname) {
        this.stFirstname = stFirstname;
    }

    public String getStMiddlename() {
        return stMiddlename;
    }

    public void setStMiddlename(String stMiddlename) {
        this.stMiddlename = stMiddlename;
    }

    public Date getStBorned() {
        return stBorned;
    }

    public void setStBorned(Date stBorned) {
        this.stBorned = stBorned;
    }

    public boolean getStActivated() {
        return stActivated;
    }

    public void setStActivated(boolean stActivated) {
        this.stActivated = stActivated;
    }

    public boolean getStDeleted() {
        return stDeleted;
    }

    public void setStDeleted(boolean stDeleted) {
        this.stDeleted = stDeleted;
    }

    public Date getStCreated() {
        return stCreated;
    }

    public void setStCreated(Date stCreated) {
        this.stCreated = stCreated;
    }

    public Date getStChanged() {
        return stChanged;
    }

    public void setStChanged(Date stChanged) {
        this.stChanged = stChanged;
    }

    public int getStMaxInactiveInterval() {
        return stMaxInactiveInterval;
    }

    public void setStMaxInactiveInterval(int stMaxInactiveInterval) {
        this.stMaxInactiveInterval = stMaxInactiveInterval;
    }

    @XmlTransient
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection4() {
        return nonConformiteRequestCollection4;
    }

    public void setNonConformiteRequestCollection4(Collection<NonConformiteRequest> nonConformiteRequestCollection4) {
        this.nonConformiteRequestCollection4 = nonConformiteRequestCollection4;
    }
    
    @XmlTransient
    public Collection<NonConformiteActions> getNonConformiteActionsCollection() {
        return nonConformiteActionsCollection;
    }

    public void setNonConformiteActionsCollection(Collection<NonConformiteActions> nonConformiteActionsCollection) {
        this.nonConformiteActionsCollection = nonConformiteActionsCollection;
    }

    public Collection<NonConformiteActions> getNonConformiteActionsCollection1() {
        return nonConformiteActionsCollection1;
    }

    public void setNonConformiteActionsCollection1(Collection<NonConformiteActions> nonConformiteActionsCollection1) {
        this.nonConformiteActionsCollection1 = nonConformiteActionsCollection1;
    }
    
    

    @XmlTransient
    public Collection<LabSample> getLabSampleCollection() {
        return labSampleCollection;
    }

    public void setLabSampleCollection(Collection<LabSample> labSampleCollection) {
        this.labSampleCollection = labSampleCollection;
    }

    public IsmGenre getStGenre() {
        return stGenre;
    }

    public void setStGenre(IsmGenre stGenre) {
        this.stGenre = stGenre;
    }

    @XmlTransient
    public Collection<Processus> getProcessusCollection() {
        return processusCollection;
    }

    public void setProcessusCollection(Collection<Processus> processusCollection) {
        this.processusCollection = processusCollection;
    }

    @XmlTransient
    public Collection<StaffGroups> getStaffGroupsCollection() {
        return staffGroupsCollection;
    }

    public void setStaffGroupsCollection(Collection<StaffGroups> staffGroupsCollection) {
        this.staffGroupsCollection = staffGroupsCollection;
    }

    @XmlTransient
    public Collection<StaffSetup> getStaffSetupCollection() {
        return staffSetupCollection;
    }

    public void setStaffSetupCollection(Collection<StaffSetup> staffSetupCollection) {
        this.staffSetupCollection = staffSetupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stId != null ? stId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.stId == null && other.stId != null) || (this.stId != null && !this.stId.equals(other.stId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.Staff[ stId=" + stId + " ]";
    }

}
