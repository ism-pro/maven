/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.process.ctrl;

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
import org.ism.entities.admin.Company;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_allowed", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"aa_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseAllowed.findAll", query = "SELECT a FROM AnalyseAllowed a"),
    @NamedQuery(name = "AnalyseAllowed.findByAaId", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaId = :aaId"),
    @NamedQuery(name = "AnalyseAllowed.findByAaPoint", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaPoint = :aaPoint"),
    @NamedQuery(name = "AnalyseAllowed.findByAaPointStr", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaPoint.apPoint = :aaPoint"),
    @NamedQuery(name = "AnalyseAllowed.findByAaType", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaType = :aaType"),
    @NamedQuery(name = "AnalyseAllowed.findByAaTypeStr", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaType.atType = :aaType"),
    @NamedQuery(name = "AnalyseAllowed.findByAaPointType", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaPoint = :aaPoint AND a.aaType = :aaType"),
    @NamedQuery(name = "AnalyseAllowed.findByAaPointTypeOfCompany", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaPoint = :aaPoint AND a.aaType = :aaType AND a.aaCompany = :aaCompany"),
    @NamedQuery(name = "AnalyseAllowed.findByAaenlimitHH", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaenlimitHH = :aaenlimitHH"),
    @NamedQuery(name = "AnalyseAllowed.findByAalimitHH", query = "SELECT a FROM AnalyseAllowed a WHERE a.aalimitHH = :aalimitHH"),
    @NamedQuery(name = "AnalyseAllowed.findByAaenlimitH", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaenlimitH = :aaenlimitH"),
    @NamedQuery(name = "AnalyseAllowed.findByAalimitH", query = "SELECT a FROM AnalyseAllowed a WHERE a.aalimitH = :aalimitH"),
    @NamedQuery(name = "AnalyseAllowed.findByAaenlimitB", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaenlimitB = :aaenlimitB"),
    @NamedQuery(name = "AnalyseAllowed.findByAalimitB", query = "SELECT a FROM AnalyseAllowed a WHERE a.aalimitB = :aalimitB"),
    @NamedQuery(name = "AnalyseAllowed.findByAaenlimitBB", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaenlimitBB = :aaenlimitBB"),
    @NamedQuery(name = "AnalyseAllowed.findByAalimitBB", query = "SELECT a FROM AnalyseAllowed a WHERE a.aalimitBB = :aalimitBB"),
    @NamedQuery(name = "AnalyseAllowed.findByAaObservation", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaObservation = :aaObservation"),
    @NamedQuery(name = "AnalyseAllowed.findByAaDeleted", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaDeleted = :aaDeleted"),
    @NamedQuery(name = "AnalyseAllowed.findByAaCreated", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaCreated = :aaCreated"),
    @NamedQuery(name = "AnalyseAllowed.findByAaChanged", query = "SELECT a FROM AnalyseAllowed a WHERE a.aaChanged = :aaChanged"),
    @NamedQuery(name = "AnalyseAllowed.selectAllByLastChange", query = "SELECT a FROM AnalyseAllowed a ORDER BY a.aaChanged DESC")
})
public class AnalyseAllowed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aa_id", nullable = false)
    private Integer aaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_enlimitHH", nullable = false)
    private boolean aaenlimitHH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_limitHH", nullable = false)
    private double aalimitHH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_enlimitH", nullable = false)
    private boolean aaenlimitH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_limitH", nullable = false)
    private double aalimitH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_enlimitB", nullable = false)
    private boolean aaenlimitB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_limitB", nullable = false)
    private double aalimitB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_enlimitBB", nullable = false)
    private boolean aaenlimitBB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aa_limitBB", nullable = false)
    private double aalimitBB;
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
    @JoinColumn(name = "aa_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company aaCompany;
    @JoinColumn(name = "aa_point", referencedColumnName = "ap_point", nullable = false)
    @ManyToOne(optional = false)
    private AnalysePoint aaPoint;
    @JoinColumn(name = "aa_type", referencedColumnName = "at_type", nullable = false)
    @ManyToOne(optional = false)
    private AnalyseType aaType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anallowedId")
    private Collection<AnalyseNotify> analyseNotifyCollection;

    public AnalyseAllowed() {
    }

    public AnalyseAllowed(Integer aaId) {
        this.aaId = aaId;
    }

    public AnalyseAllowed(Integer aaId, boolean aaenlimitHH, double aalimitHH, boolean aaenlimitH, double aalimitH, boolean aaenlimitB, double aalimitB, boolean aaenlimitBB, double aalimitBB, boolean aaDeleted, Date aaCreated, Date aaChanged) {
        this.aaId = aaId;
        this.aaenlimitHH = aaenlimitHH;
        this.aalimitHH = aalimitHH;
        this.aaenlimitH = aaenlimitH;
        this.aalimitH = aalimitH;
        this.aaenlimitB = aaenlimitB;
        this.aalimitB = aalimitB;
        this.aaenlimitBB = aaenlimitBB;
        this.aalimitBB = aalimitBB;
        this.aaDeleted = aaDeleted;
        this.aaCreated = aaCreated;
        this.aaChanged = aaChanged;
    }

    public Integer getAaId() {
        return aaId;
    }

    public void setAaId(Integer aaId) {
        this.aaId = aaId;
    }

    public boolean getAaenlimitHH() {
        return aaenlimitHH;
    }

    public void setAaenlimitHH(boolean aaenlimitHH) {
        this.aaenlimitHH = aaenlimitHH;
    }

    public double getAalimitHH() {
        return aalimitHH;
    }

    public void setAalimitHH(double aalimitHH) {
        this.aalimitHH = aalimitHH;
    }

    public boolean getAaenlimitH() {
        return aaenlimitH;
    }

    public void setAaenlimitH(boolean aaenlimitH) {
        this.aaenlimitH = aaenlimitH;
    }

    public double getAalimitH() {
        return aalimitH;
    }

    public void setAalimitH(double aalimitH) {
        this.aalimitH = aalimitH;
    }

    public boolean getAaenlimitB() {
        return aaenlimitB;
    }

    public void setAaenlimitB(boolean aaenlimitB) {
        this.aaenlimitB = aaenlimitB;
    }

    public double getAalimitB() {
        return aalimitB;
    }

    public void setAalimitB(double aalimitB) {
        this.aalimitB = aalimitB;
    }

    public boolean getAaenlimitBB() {
        return aaenlimitBB;
    }

    public void setAaenlimitBB(boolean aaenlimitBB) {
        this.aaenlimitBB = aaenlimitBB;
    }

    public double getAalimitBB() {
        return aalimitBB;
    }

    public void setAalimitBB(double aalimitBB) {
        this.aalimitBB = aalimitBB;
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

    public Company getAaCompany() {
        return aaCompany;
    }

    public void setAaCompany(Company aaCompany) {
        this.aaCompany = aaCompany;
    }

    public AnalysePoint getAaPoint() {
        return aaPoint;
    }

    public void setAaPoint(AnalysePoint aaPoint) {
        this.aaPoint = aaPoint;
    }

    public AnalyseType getAaType() {
        return aaType;
    }

    public void setAaType(AnalyseType aaType) {
        this.aaType = aaType;
    }

    @XmlTransient
    public Collection<AnalyseNotify> getAnalyseNotifyCollection() {
        return analyseNotifyCollection;
    }

    public void setAnalyseNotifyCollection(Collection<AnalyseNotify> analyseNotifyCollection) {
        this.analyseNotifyCollection = analyseNotifyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aaId != null ? aaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseAllowed)) {
            return false;
        }
        AnalyseAllowed other = (AnalyseAllowed) object;
        if ((this.aaId == null && other.aaId != null) || (this.aaId != null && !this.aaId.equals(other.aaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.AnalyseAllowed[ aaId=" + aaId + " ]";
    }

}
