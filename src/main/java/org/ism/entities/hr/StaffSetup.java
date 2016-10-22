/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.hr;

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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "staff_setup", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sts_id", "sts_staff"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffSetup.findAll", query = "SELECT s FROM StaffSetup s"),
    @NamedQuery(name = "StaffSetup.findByStsId", query = "SELECT s FROM StaffSetup s WHERE s.stsId = :stsId"),
    @NamedQuery(name = "StaffSetup.findByStsStaff", query = "SELECT s FROM StaffSetup s WHERE s.stsStaff = :stsStaff"),
    @NamedQuery(name = "StaffSetup.findByStsStaffStr", query = "SELECT s FROM StaffSetup s WHERE s.stsStaff.stStaff = :stsStaff"),
    @NamedQuery(name = "StaffSetup.findByStsmenuIndex", query = "SELECT s FROM StaffSetup s WHERE s.stsmenuIndex = :stsmenuIndex"),
    @NamedQuery(name = "StaffSetup.findByStstimeOutSession", query = "SELECT s FROM StaffSetup s WHERE s.ststimeOutSession = :ststimeOutSession"),
    @NamedQuery(name = "StaffSetup.findByStsTheme", query = "SELECT s FROM StaffSetup s WHERE s.stsTheme = :stsTheme"),
    @NamedQuery(name = "StaffSetup.findByStsCreated", query = "SELECT s FROM StaffSetup s WHERE s.stsCreated = :stsCreated"),
    @NamedQuery(name = "StaffSetup.findByStsChanged", query = "SELECT s FROM StaffSetup s WHERE s.stsChanged = :stsChanged"),
    @NamedQuery(name = "StaffSetup.selectAllByLastChange", query = "SELECT s FROM StaffSetup s ORDER BY s.stsChanged DESC")
})
public class StaffSetup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sts_id", nullable = false)
    private Integer stsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sts_menuIndex", nullable = false)
    private int stsmenuIndex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sts_timeOutSession", nullable = false)
    private int ststimeOutSession;
    @Size(max = 65)
    @Column(name = "sts_theme", length = 65)
    private String stsTheme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sts_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stsCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sts_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stsChanged;
    @JoinColumn(name = "sts_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff stsStaff;

    public StaffSetup() {
    }

    public StaffSetup(Integer stsId) {
        this.stsId = stsId;
    }

    public StaffSetup(Integer stsId, int stsmenuIndex, int ststimeOutSession, Date stsCreated, Date stsChanged) {
        this.stsId = stsId;
        this.stsmenuIndex = stsmenuIndex;
        this.ststimeOutSession = ststimeOutSession;
        this.stsCreated = stsCreated;
        this.stsChanged = stsChanged;
    }

    public Integer getStsId() {
        return stsId;
    }

    public void setStsId(Integer stsId) {
        this.stsId = stsId;
    }

    public int getStsmenuIndex() {
        return stsmenuIndex;
    }

    public void setStsmenuIndex(int stsmenuIndex) {
        this.stsmenuIndex = stsmenuIndex;
    }

    public int getStstimeOutSession() {
        return ststimeOutSession;
    }

    public void setStstimeOutSession(int ststimeOutSession) {
        this.ststimeOutSession = ststimeOutSession;
    }

    public Date getStsCreated() {
        return stsCreated;
    }

    public void setStsCreated(Date stsCreated) {
        this.stsCreated = stsCreated;
    }

    public Date getStsChanged() {
        return stsChanged;
    }

    public void setStsChanged(Date stsChanged) {
        this.stsChanged = stsChanged;
    }

    public Staff getStsStaff() {
        return stsStaff;
    }

    public void setStsStaff(Staff stsStaff) {
        this.stsStaff = stsStaff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stsId != null ? stsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaffSetup)) {
            return false;
        }
        StaffSetup other = (StaffSetup) object;
        if ((this.stsId == null && other.stsId != null) || (this.stsId != null && !this.stsId.equals(other.stsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.ex.StaffSetup[ stsId=" + stsId + " ]";
    }

    public String getStsTheme() {
        return stsTheme;
    }

    public void setStsTheme(String stsTheme) {
        this.stsTheme = stsTheme;
    }

}
