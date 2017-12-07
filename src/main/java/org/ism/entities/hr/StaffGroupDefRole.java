/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.hr;

import org.ism.entities.app.IsmRole;
import org.ism.entities.admin.Company;
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
@Table(name = "staff_group_def_role", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"stgdr_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffGroupDefRole.findAll", query = "SELECT s FROM StaffGroupDefRole s"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrId", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrId = :stgdrId"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrActivated", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrActivated = :stgdrActivated"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrCreated", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrCreated = :stgdrCreated"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrChanged", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrChanged = :stgdrChanged"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrGroups", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrGroupDef = :stgdrGroupDef AND s.stgdrActivated =true"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrGroupAndRole", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrGroupDef = :stgdrGroupDef AND s.stgdrRole = :stgdrRole AND s.stgdrActivated =true"),
    @NamedQuery(name = "StaffGroupDefRole.findByStgdrGroupAndRoleOfCompany",query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrGroupDef = :stgdrGroupDef AND s.stgdrRole = :stgdrRole AND s.stgdrCompany = :stgdrCompany"),
    @NamedQuery(name = "StaffGroupDefRole.findByCompanyGroupsActivated", query = "SELECT s FROM StaffGroupDefRole s WHERE s.stgdrCompany = :stgdrCompany AND s.stgdrGroupDef = :stgdrGroupDef AND s.stgdrActivated =true"),
    @NamedQuery(name = "StaffGroupDefRole.countCompanyGroupsActivated", query = "SELECT count(s) FROM StaffGroupDefRole s WHERE s.stgdrCompany = :stgdrCompany AND s.stgdrGroupDef = :stgdrGroupDef AND s.stgdrActivated =true"),
    @NamedQuery(name = "StaffGroupDefRole.selectAllByLastChange", query = "SELECT s FROM StaffGroupDefRole s ORDER BY s.stgdrChanged DESC")
})
public class StaffGroupDefRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stgdr_id", nullable = false)
    private Integer stgdrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgdr_activated", nullable = false)
    private boolean stgdrActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgdr_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgdrCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stgdr_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stgdrChanged;

    @JoinColumn(name = "stgdr_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company stgdrCompany;

    @JoinColumn(name = "stgdr_group_def", referencedColumnName = "stgd_group_def", nullable = false)
    @ManyToOne(optional = false)
    private StaffGroupDef stgdrGroupDef;

    @JoinColumn(name = "stgdr_role", referencedColumnName = "role", nullable = false)
    @ManyToOne(optional = false)
    private IsmRole stgdrRole;

    public StaffGroupDefRole() {
    }

    public StaffGroupDefRole(Integer stgdrId) {
        this.stgdrId = stgdrId;
    }

    public StaffGroupDefRole(Integer stgdrId, boolean stgdrActivated, Date stgdrCreated, Date stgdrChanged) {
        this.stgdrId = stgdrId;
        this.stgdrActivated = stgdrActivated;
        this.stgdrCreated = stgdrCreated;
        this.stgdrChanged = stgdrChanged;
    }

    public Integer getStgdrId() {
        return stgdrId;
    }

    public void setStgdrId(Integer stgdrId) {
        this.stgdrId = stgdrId;
    }

    public boolean getStgdrActivated() {
        return stgdrActivated;
    }

    public void setStgdrActivated(boolean stgdrActivated) {
        this.stgdrActivated = stgdrActivated;
    }

    public Date getStgdrCreated() {
        return stgdrCreated;
    }

    public void setStgdrCreated(Date stgdrCreated) {
        this.stgdrCreated = stgdrCreated;
    }

    public Date getStgdrChanged() {
        return stgdrChanged;
    }

    public void setStgdrChanged(Date stgdrChanged) {
        this.stgdrChanged = stgdrChanged;
    }

    public Company getStgdrCompany() {
        return stgdrCompany;
    }

    public void setStgdrCompany(Company stgdrCompany) {
        this.stgdrCompany = stgdrCompany;
    }

    public StaffGroupDef getStgdrGroupDef() {
        return stgdrGroupDef;
    }

    public void setStgdrGroupDef(StaffGroupDef stgdrGroupDef) {
        this.stgdrGroupDef = stgdrGroupDef;
    }

    public IsmRole getStgdrRole() {
        return stgdrRole;
    }

    public void setStgdrRole(IsmRole stgdrRole) {
        this.stgdrRole = stgdrRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stgdrId != null ? stgdrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffGroupDefRole)) {
            return false;
        }
        StaffGroupDefRole other = (StaffGroupDefRole) object;
        if ((this.stgdrId == null && other.stgdrId != null) || (this.stgdrId != null && !this.stgdrId.equals(other.stgdrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.StaffGroupDefRole[ stgdrId=" + stgdrId + " ]";
    }

}
