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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "staff_groups", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"stg_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffGroups.findAll", query = "SELECT s FROM StaffGroups s"),
    @NamedQuery(name = "StaffGroups.findByStgId", query = "SELECT s FROM StaffGroups s WHERE s.stgId = :stgId"),
    @NamedQuery(name = "StaffGroups.findByStgStaff", query = "SELECT s FROM StaffGroups s WHERE s.stgStaff = :stgStaff"),
    @NamedQuery(name = "StaffGroups.findByStgStaffAndCompanyAndStaffGroupDef", 
            query = "SELECT s FROM StaffGroups s WHERE s.stgStaff = :stgStaff AND s.stgCompany = :stgCompany AND s.stgGroupDef = :stgGroupDef"),
    @NamedQuery(name = "StaffGroups.findByStgActivated", query = "SELECT s FROM StaffGroups s WHERE s.stgActivated = :stgActivated"),
    @NamedQuery(name = "StaffGroups.findByStgCreated", query = "SELECT s FROM StaffGroups s WHERE s.stgCreated = :stgCreated"),
    @NamedQuery(name = "StaffGroups.findByStgChanged", query = "SELECT s FROM StaffGroups s WHERE s.stgChanged = :stgChanged"),
    @NamedQuery(name = "StaffGroups.countStaffCompanyActivated", 
            query = "SELECT count(s) FROM StaffGroups s WHERE s.stgCompany = :stgCompany AND s.stgStaff = :stgStaff AND s.stgActivated=1"),
    @NamedQuery(name = "StaffGroups.findByStaffCompanyActivated", 
            query = "SELECT s FROM StaffGroups s WHERE s.stgCompany = :stgCompany AND s.stgStaff = :stgStaff AND s.stgActivated=1"),
    @NamedQuery(name = "StaffGroups.selectAllByLastChange", 
            query = "SELECT s FROM StaffGroups s ORDER BY s.stgChanged DESC")
})
public class StaffGroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stg_id", nullable = false)
    private Integer stgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stg_activated", nullable = false)
    private boolean stgActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stg_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stg_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgChanged;
    @JoinColumn(name = "stg_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company stgCompany;
    @JoinColumn(name = "stg_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff stgStaff;
    @JoinColumn(name = "stg_group_def", referencedColumnName = "stgd_group_def", nullable = false)
    @ManyToOne(optional = false)
    private StaffGroupDef stgGroupDef;

    public StaffGroups() {
    }

    public StaffGroups(Integer stgId) {
        this.stgId = stgId;
    }

    public StaffGroups(Integer stgId, boolean stgActivated, Date stgCreated, Date stgChanged) {
        this.stgId = stgId;
        this.stgActivated = stgActivated;
        this.stgCreated = stgCreated;
        this.stgChanged = stgChanged;
    }

    public Integer getStgId() {
        return stgId;
    }

    public void setStgId(Integer stgId) {
        this.stgId = stgId;
    }

    public boolean getStgActivated() {
        return stgActivated;
    }

    public void setStgActivated(boolean stgActivated) {
        this.stgActivated = stgActivated;
    }

    public Date getStgCreated() {
        return stgCreated;
    }

    public void setStgCreated(Date stgCreated) {
        this.stgCreated = stgCreated;
    }

    public Date getStgChanged() {
        return stgChanged;
    }

    public void setStgChanged(Date stgChanged) {
        this.stgChanged = stgChanged;
    }

    public Company getStgCompany() {
        return stgCompany;
    }

    public void setStgCompany(Company stgCompany) {
        this.stgCompany = stgCompany;
    }

    public Staff getStgStaff() {
        return stgStaff;
    }

    public void setStgStaff(Staff stgStaff) {
        this.stgStaff = stgStaff;
    }

    public StaffGroupDef getStgGroupDef() {
        return stgGroupDef;
    }

    public void setStgGroupDef(StaffGroupDef stgGroupDef) {
        this.stgGroupDef = stgGroupDef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stgId != null ? stgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffGroups)) {
            return false;
        }
        StaffGroups other = (StaffGroups) object;
        if ((this.stgId == null && other.stgId != null) || (this.stgId != null && !this.stgId.equals(other.stgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.StaffGroups[ stgId=" + stgId + " ]";
    }
    
}
