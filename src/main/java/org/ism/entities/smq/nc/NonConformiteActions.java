/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.smq.nc;

import org.ism.entities.app.IsmNcastate;
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
import org.ism.entities.hr.Staff;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "non_conformite_actions", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nca_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteActions.findAll", query = "SELECT n FROM NonConformiteActions n"),
    @NamedQuery(name = "NonConformiteActions.findByNcaId", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaId = :ncaId"),
    @NamedQuery(name = "NonConformiteActions.findByNcaDeadline", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaDeadline = :ncaDeadline"),
    @NamedQuery(name = "NonConformiteActions.findByNcaCreated", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaCreated = :ncaCreated"),
    @NamedQuery(name = "NonConformiteActions.findByNcaChanged", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaChanged = :ncaChanged"),
    @NamedQuery(name = "NonConformiteActions.selectAllByLastChange", query = "SELECT n FROM NonConformiteActions n ORDER BY n.ncaChanged DESC"),
    @NamedQuery(name = "NonConformiteActions.findAllByNCLastChange", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc  ORDER BY n.ncaChanged DESC"),
    @NamedQuery(name = "NonConformiteActions.findAllByNCLastId", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc  ORDER BY n.ncaId DESC"),
    @NamedQuery(name = "NonConformiteActions.findDescByNC", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc  ORDER BY n.ncaId DESC"),
    @NamedQuery(name = "NonConformiteActions.itemsCreateInRange", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaCreated >= :ncaFrom AND n.ncaCreated < :ncaTo"),
    @NamedQuery(name = "NonConformiteActions.itemsCreateInRangeByProcessus", query = "SELECT n FROM NonConformiteActions n WHERE n.ncaCreated >= :ncaFrom AND n.ncaCreated < :ncaTo AND n.ncaNc.ncrProcessus = :ncrProcessus"),})
public class NonConformiteActions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nca_id", nullable = false)
    private Integer ncaId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nca_description", nullable = false, length = 65535)
    private String ncaDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nca_deadline", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncaDeadline;
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nca_descApprouver", nullable = true, length = 65535)
    private String ncaDescApprouver;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nca_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncaCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nca_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncaChanged;
    @JoinColumn(name = "nca_nc", referencedColumnName = "ncr_id", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteRequest ncaNc;
    @JoinColumn(name = "nca_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff ncaStaff;
    @JoinColumn(name = "nca_staffApprouver", referencedColumnName = "st_staff", nullable = true)
    @ManyToOne(optional = true)
    private Staff ncaStaffApprouver;
    @JoinColumn(name = "nca_state", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private IsmNcastate ncaState;

    public NonConformiteActions() {
    }

    public NonConformiteActions(Integer ncaId) {
        this.ncaId = ncaId;
    }

    public NonConformiteActions(Integer ncaId, String ncaDescription,
            Date ncaDeadline, Date ncaCreated, Date ncaChanged) {
        this.ncaId = ncaId;
        this.ncaDescription = ncaDescription;
        this.ncaDeadline = ncaDeadline;
        this.ncaCreated = ncaCreated;
        this.ncaChanged = ncaChanged;
    }

    public Integer getNcaId() {
        return ncaId;
    }

    public void setNcaId(Integer ncaId) {
        this.ncaId = ncaId;
    }

    public String getNcaDescription() {
        return ncaDescription;
    }

    public void setNcaDescription(String ncaDescription) {
        this.ncaDescription = ncaDescription;
    }

    public Date getNcaDeadline() {
        return ncaDeadline;
    }

    public void setNcaDeadline(Date ncaDeadline) {
        this.ncaDeadline = ncaDeadline;
    }

    public Date getNcaCreated() {
        return ncaCreated;
    }

    public void setNcaCreated(Date ncaCreated) {
        this.ncaCreated = ncaCreated;
    }

    public Date getNcaChanged() {
        return ncaChanged;
    }

    public void setNcaChanged(Date ncaChanged) {
        this.ncaChanged = ncaChanged;
    }

    public NonConformiteRequest getNcaNc() {
        return ncaNc;
    }

    public void setNcaNc(NonConformiteRequest ncaNc) {
        this.ncaNc = ncaNc;
    }

    public Staff getNcaStaff() {
        return ncaStaff;
    }

    public void setNcaStaff(Staff ncaStaff) {
        this.ncaStaff = ncaStaff;
    }

    public IsmNcastate getNcaState() {
        return ncaState;
    }

    public void setNcaState(IsmNcastate ncaState) {
        this.ncaState = ncaState;
    }

    public String getNcaDescApprouver() {
        return ncaDescApprouver;
    }

    public void setNcaDescApprouver(String ncaDescApprouver) {
        this.ncaDescApprouver = ncaDescApprouver;
    }

    public Staff getNcaStaffApprouver() {
        return ncaStaffApprouver;
    }

    public void setNcaStaffApprouver(Staff ncaStaffApprouver) {
        this.ncaStaffApprouver = ncaStaffApprouver;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncaId != null ? ncaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NonConformiteActions)) {
            return false;
        }
        NonConformiteActions other = (NonConformiteActions) object;
        if ((this.ncaId == null && other.ncaId != null) || (this.ncaId != null && !this.ncaId.equals(other.ncaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.NonConformiteActions[ ncaId=" + ncaId + " ]";
        return ncaId + " - " + ncaNc + " [" + ncaId + "]";
    }

}
