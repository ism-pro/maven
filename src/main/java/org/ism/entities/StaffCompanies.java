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
@Table(name = "staff_companies", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"stc_id", "stc_staff", "stc_company"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffCompanies.findAll", query = "SELECT s FROM StaffCompanies s"),
    @NamedQuery(name = "StaffCompanies.findByStcId", query = "SELECT s FROM StaffCompanies s WHERE s.stcId = :stcId"),
    @NamedQuery(name = "StaffCompanies.findByStcMain", query = "SELECT s FROM StaffCompanies s WHERE s.stcMain = :stcMain"),
    @NamedQuery(name = "StaffCompanies.findByStcActivated", query = "SELECT s FROM StaffCompanies s WHERE s.stcActivated = :stcActivated"),
    @NamedQuery(name = "StaffCompanies.findByStcCreated", query = "SELECT s FROM StaffCompanies s WHERE s.stcCreated = :stcCreated"),
    @NamedQuery(name = "StaffCompanies.findByStcChanged", query = "SELECT s FROM StaffCompanies s WHERE s.stcChanged = :stcChanged"),
    @NamedQuery(name = "StaffCompanies.findByStcStaff", query = "SELECT s FROM StaffCompanies s WHERE s.stcStaff = :stcStaff"),
    @NamedQuery(name = "StaffCompanies.findByStcStaffAndCompany", query = "SELECT s FROM StaffCompanies s WHERE s.stcStaff = :stcStaff and s.stcCompany = :stcCompany"),
    @NamedQuery(name = "StaffCompanies.countStaffCompanies", query = "SELECT count(s) FROM StaffCompanies s WHERE s.stcStaff = :stcStaff AND s.stcCompany = :stcCompany")
})
public class StaffCompanies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stc_id", nullable = false)
    private Integer stcId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stc_main", nullable = false)
    private boolean stcMain;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stc_activated", nullable = false)
    private boolean stcActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stc_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stcCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stc_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stcChanged;
    @JoinColumn(name = "stc_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff stcStaff;
    @JoinColumn(name = "stc_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company stcCompany;

    public StaffCompanies() {
    }

    public StaffCompanies(Integer stcId) {
        this.stcId = stcId;
    }

    public StaffCompanies(Integer stcId, boolean stcMain, boolean stcActivated, Date stcCreated, Date stcChanged) {
        this.stcId = stcId;
        this.stcMain = stcMain;
        this.stcActivated = stcActivated;
        this.stcCreated = stcCreated;
        this.stcChanged = stcChanged;
    }

    public Integer getStcId() {
        return stcId;
    }

    public void setStcId(Integer stcId) {
        this.stcId = stcId;
    }

    public boolean getStcMain() {
        return stcMain;
    }

    public void setStcMain(boolean stcMain) {
        this.stcMain = stcMain;
    }

    public boolean getStcActivated() {
        return stcActivated;
    }

    public void setStcActivated(boolean stcActivated) {
        this.stcActivated = stcActivated;
    }

    public Date getStcCreated() {
        return stcCreated;
    }

    public void setStcCreated(Date stcCreated) {
        this.stcCreated = stcCreated;
    }

    public Date getStcChanged() {
        return stcChanged;
    }

    public void setStcChanged(Date stcChanged) {
        this.stcChanged = stcChanged;
    }

    public Staff getStcStaff() {
        return stcStaff;
    }

    public void setStcStaff(Staff stcStaff) {
        this.stcStaff = stcStaff;
    }

    public Company getStcCompany() {
        return stcCompany;
    }

    public void setStcCompany(Company stcCompany) {
        this.stcCompany = stcCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stcId != null ? stcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffCompanies)) {
            return false;
        }
        StaffCompanies other = (StaffCompanies) object;
        if ((this.stcId == null && other.stcId != null) || (this.stcId != null && !this.stcId.equals(other.stcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.StaffCompanies[ stcId=" + stcId + " ]";
    }
    
}
