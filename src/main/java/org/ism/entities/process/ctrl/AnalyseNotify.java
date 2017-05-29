/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.process.ctrl;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.ism.entities.admin.Company;
import org.ism.entities.app.IsmAnalyseAlarm;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_notify", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"an_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseNotify.findAll", query = "SELECT a FROM AnalyseNotify a"),
    @NamedQuery(name = "AnalyseNotify.findByAnId", query = "SELECT a FROM AnalyseNotify a WHERE a.anId = :anId"),
    @NamedQuery(name = "AnalyseNotify.findByAnIsgroup", query = "SELECT a FROM AnalyseNotify a WHERE a.anIsgroup = :anIsgroup"),
    @NamedQuery(name = "AnalyseNotify.findByAnAllowedId", query = "SELECT a FROM AnalyseNotify a WHERE a.anallowedId = :anallowedId"),
    @NamedQuery(name = "AnalyseNotify.findByAnAllowedIdInt", query = "SELECT a FROM AnalyseNotify a WHERE a.anallowedId.aaId = :anallowedId"),
    @NamedQuery(name = "AnalyseNotify.findByAnAlarm", query = "SELECT a FROM AnalyseNotify a WHERE a.anAlarm = :anAlarm"),
    @NamedQuery(name = "AnalyseNotify.findByAnAlarmStr", query = "SELECT a FROM AnalyseNotify a WHERE a.anAlarm.alarmname = :anAlarm"),
    @NamedQuery(name = "AnalyseNotify.findByAnGroup", query = "SELECT a FROM AnalyseNotify a WHERE a.anGroup = :anGroup"),
    @NamedQuery(name = "AnalyseNotify.findByAnStaff", query = "SELECT a FROM AnalyseNotify a WHERE a.anStaff = :anStaff"),
    @NamedQuery(name = "AnalyseNotify.findByAnAlarmAllowedId", query = "SELECT a FROM AnalyseNotify a WHERE a.anAlarm = :anAlarm AND a.anallowedId = :anallowedId"),
    @NamedQuery(name = "AnalyseNotify.findByAnAlarmGroup", query = "SELECT a FROM AnalyseNotify a WHERE a.anAlarm = :anAlarm AND a.anGroup = :anGroup"),
    @NamedQuery(name = "AnalyseNotify.findByAnAlarmStaff", query = "SELECT a FROM AnalyseNotify a WHERE a.anAlarm = :anAlarm AND a.anStaff = :anStaff"),
    @NamedQuery(name = "AnalyseNotify.findByAaObservation", query = "SELECT a FROM AnalyseNotify a WHERE a.aaObservation = :aaObservation"),
    @NamedQuery(name = "AnalyseNotify.findByAaDeleted", query = "SELECT a FROM AnalyseNotify a WHERE a.aaDeleted = :aaDeleted"),
    @NamedQuery(name = "AnalyseNotify.findByAaCreated", query = "SELECT a FROM AnalyseNotify a WHERE a.aaCreated = :aaCreated"),
    @NamedQuery(name = "AnalyseNotify.findByAaChanged", query = "SELECT a FROM AnalyseNotify a WHERE a.aaChanged = :aaChanged"),
    @NamedQuery(name = "AnalyseNotify.selectAllByLastChange", query = "SELECT a FROM AnalyseNotify a ORDER BY a.aaChanged DESC")
})
public class AnalyseNotify implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "an_id", nullable = false)
    private Integer anId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "an_isgroup", nullable = false)
    private boolean anIsgroup;
    @Size(max = 255)
    @Column(name = "aa_observation", length = 255)
    private String aaObservation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_deleted", nullable = false)
    private boolean aaDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date aaCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date aaChanged;
    @JoinColumn(name = "an_alarm", referencedColumnName = "alarm", nullable = false)
    @ManyToOne(optional = false)
    private IsmAnalyseAlarm anAlarm;
    @JoinColumn(name = "an_allowedId", referencedColumnName = "aa_id", nullable = false)
    @ManyToOne(optional = false)
    private AnalyseAllowed anallowedId;
    @JoinColumn(name = "an_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company anCompany;
    @JoinColumn(name = "an_group", referencedColumnName = "stgd_group_def")
    @ManyToOne
    private StaffGroupDef anGroup;
    @JoinColumn(name = "an_staff", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff anStaff;

    public AnalyseNotify() {
    }

    public AnalyseNotify(Integer anId) {
        this.anId = anId;
    }

    public AnalyseNotify(Integer anId, boolean anIsgroup, boolean aaDeleted, Date aaCreated, Date aaChanged) {
        this.anId = anId;
        this.anIsgroup = anIsgroup;
        this.aaDeleted = aaDeleted;
        this.aaCreated = aaCreated;
        this.aaChanged = aaChanged;
    }

    public Integer getAnId() {
        return anId;
    }

    public void setAnId(Integer anId) {
        this.anId = anId;
    }

    public boolean getAnIsgroup() {
        return anIsgroup;
    }

    public void setAnIsgroup(boolean anIsgroup) {
        this.anIsgroup = anIsgroup;
    }

    public String getAaObservation() {
        return aaObservation;
    }

    public void setAaObservation(String aaObservation) {
        this.aaObservation = aaObservation;
    }

    public boolean getAaDeleted() {
        return aaDeleted;
    }

    public void setAaDeleted(boolean aaDeleted) {
        this.aaDeleted = aaDeleted;
    }

    public Date getAaCreated() {
        return aaCreated;
    }

    public void setAaCreated(Date aaCreated) {
        this.aaCreated = aaCreated;
    }

    public Date getAaChanged() {
        return aaChanged;
    }

    public void setAaChanged(Date aaChanged) {
        this.aaChanged = aaChanged;
    }

    public IsmAnalyseAlarm getAnAlarm() {
        return anAlarm;
    }

    public void setAnAlarm(IsmAnalyseAlarm anAlarm) {
        this.anAlarm = anAlarm;
    }

    public AnalyseAllowed getAnallowedId() {
        return anallowedId;
    }

    public void setAnallowedId(AnalyseAllowed anallowedId) {
        this.anallowedId = anallowedId;
    }

    public Company getAnCompany() {
        return anCompany;
    }

    public void setAnCompany(Company anCompany) {
        this.anCompany = anCompany;
    }

    public StaffGroupDef getAnGroup() {
        return anGroup;
    }

    public void setAnGroup(StaffGroupDef anGroup) {
        this.anGroup = anGroup;
    }

    public Staff getAnStaff() {
        return anStaff;
    }

    public void setAnStaff(Staff anStaff) {
        this.anStaff = anStaff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (anId != null ? anId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseNotify)) {
            return false;
        }
        AnalyseNotify other = (AnalyseNotify) object;
        if ((this.anId == null && other.anId != null) || (this.anId != null && !this.anId.equals(other.anId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.AnalyseNotify[ anId=" + anId + " ]";
    }

}
