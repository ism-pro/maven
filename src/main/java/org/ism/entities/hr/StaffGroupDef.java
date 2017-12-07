/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.hr;

import org.ism.entities.admin.Company;
import org.ism.entities.process.ctrl.AnalyseNotify;
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
@Table(name = "staff_group_def", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"stgd_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffGroupDef.findAll", query = "SELECT s FROM StaffGroupDef s"),
    @NamedQuery(name = "StaffGroupDef.findByStgdId", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdId = :stgdId"),
    @NamedQuery(name = "StaffGroupDef.findByStgdGroupDef", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdGroupDef = :stgdGroupDef"),
    @NamedQuery(name = "StaffGroupDef.findByStgdDesignation", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDesignation = :stgdDesignation"),
    @NamedQuery(name = "StaffGroupDef.findByStgdGroupDefOfCompany", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdGroupDef = :stgdGroupDef AND  s.stgdCompany = :stgdCompany"),
    @NamedQuery(name = "StaffGroupDef.findByStgdDesignationOfCompany", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDesignation = :stgdDesignation AND  s.stgdCompany = :stgdCompany"),
    @NamedQuery(name = "StaffGroupDef.findByStgdDeleted", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDeleted = :stgdDeleted"),
    @NamedQuery(name = "StaffGroupDef.findByStgdCreated", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdCreated = :stgdCreated"),
    @NamedQuery(name = "StaffGroupDef.findByStgdChanged", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdChanged = :stgdChanged"),
    @NamedQuery(name = "StaffGroupDef.selectAllByLastChange", query = "SELECT s FROM StaffGroupDef s ORDER BY s.stgdChanged DESC"),
    @NamedQuery(name = "StaffGroupDef.findGroupByCompany", query = "SELECT s FROM StaffGroupDef s GROUP BY s.stgdCompany"),
    @NamedQuery(name = "StaffGroupDef.findByStgdCompany", query = "SELECT s FROM StaffGroupDef s WHERE s.stgdCompany = :stgdCompany")
})
public class StaffGroupDef implements Serializable {

    @OneToMany(mappedBy = "anGroup")
    private Collection<AnalyseNotify> analyseNotifyCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stgd_id", nullable = false)
    private Integer stgdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "stgd_group_def", nullable = false, length = 45)
    private String stgdGroupDef;
    @Size(max = 255)
    @Column(name = "stgd_designation", length = 255)
    private String stgdDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgd_deleted", nullable = false)
    private boolean stgdDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgd_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgdCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgd_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgdChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgdrGroupDef")
    private Collection<StaffGroupDefRole> staffGroupDefRoleCollection;
    @JoinColumn(name = "stgd_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company stgdCompany;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgGroupDef")
    private Collection<StaffGroups> staffGroupsCollection;

    public StaffGroupDef() {
    }

    public StaffGroupDef(Integer stgdId) {
        this.stgdId = stgdId;
    }

    public StaffGroupDef(Integer stgdId, String stgdGroupDef, boolean stgdDeleted, Date stgdCreated, Date stgdChanged) {
        this.stgdId = stgdId;
        this.stgdGroupDef = stgdGroupDef;
        this.stgdDeleted = stgdDeleted;
        this.stgdCreated = stgdCreated;
        this.stgdChanged = stgdChanged;
    }

    public Integer getStgdId() {
        return stgdId;
    }

    public void setStgdId(Integer stgdId) {
        this.stgdId = stgdId;
    }

    public String getStgdGroupDef() {
        return stgdGroupDef;
    }

    public void setStgdGroupDef(String stgdGroupDef) {
        this.stgdGroupDef = stgdGroupDef;
    }

    public String getStgdDesignation() {
        return stgdDesignation;
    }

    public void setStgdDesignation(String stgdDesignation) {
        this.stgdDesignation = stgdDesignation;
    }

    public boolean getStgdDeleted() {
        return stgdDeleted;
    }

    public void setStgdDeleted(boolean stgdDeleted) {
        this.stgdDeleted = stgdDeleted;
    }

    public Date getStgdCreated() {
        return stgdCreated;
    }

    public void setStgdCreated(Date stgdCreated) {
        this.stgdCreated = stgdCreated;
    }

    public Date getStgdChanged() {
        return stgdChanged;
    }

    public void setStgdChanged(Date stgdChanged) {
        this.stgdChanged = stgdChanged;
    }

    @XmlTransient
    public Collection<StaffGroupDefRole> getStaffGroupDefRoleCollection() {
        return staffGroupDefRoleCollection;
    }

    public void setStaffGroupDefRoleCollection(Collection<StaffGroupDefRole> staffGroupDefRoleCollection) {
        this.staffGroupDefRoleCollection = staffGroupDefRoleCollection;
    }

    public Company getStgdCompany() {
        return stgdCompany;
    }

    public void setStgdCompany(Company stgdCompany) {
        this.stgdCompany = stgdCompany;
    }

    @XmlTransient
    public Collection<StaffGroups> getStaffGroupsCollection() {
        return staffGroupsCollection;
    }

    public void setStaffGroupsCollection(Collection<StaffGroups> staffGroupsCollection) {
        this.staffGroupsCollection = staffGroupsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stgdId != null ? stgdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffGroupDef)) {
            return false;
        }
        StaffGroupDef other = (StaffGroupDef) object;
        if ((this.stgdId == null && other.stgdId != null) || (this.stgdId != null && !this.stgdId.equals(other.stgdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.StaffGroupDef[ stgdId=" + stgdId + " ]";
        return stgdGroupDef + " - " + stgdDesignation + " [" + stgdId + "]";
    }

    @XmlTransient
    public Collection<AnalyseNotify> getAnalyseNotifyCollection() {
        return analyseNotifyCollection;
    }

    public void setAnalyseNotifyCollection(Collection<AnalyseNotify> analyseNotifyCollection) {
        this.analyseNotifyCollection = analyseNotifyCollection;
    }

}
