/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.smq;

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
import org.ism.entities.hr.Staff;

/**
 * <h1>PointInfos</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "point_infos", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pi_id", "pi_group"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PointInfos.findAll", query = "SELECT p FROM PointInfos p"),
    @NamedQuery(name = "PointInfos.findByPiId", query = "SELECT p FROM PointInfos p WHERE p.piId = :piId"),
    @NamedQuery(name = "PointInfos.findByPiGroup", query = "SELECT p FROM PointInfos p WHERE p.piGroup = :piGroup"),
    @NamedQuery(name = "PointInfos.findByPiSlideshow", query = "SELECT p FROM PointInfos p WHERE p.piSlideshow = :piSlideshow"),
    @NamedQuery(name = "PointInfos.findByPiSlide", query = "SELECT p FROM PointInfos p WHERE p.piSlide = :piSlide"),
    @NamedQuery(name = "PointInfos.findByPislidePath", query = "SELECT p FROM PointInfos p WHERE p.pislidePath = :pislidePath"),
    @NamedQuery(name = "PointInfos.findByPiDescription", query = "SELECT p FROM PointInfos p WHERE p.piDescription = :piDescription"),
    @NamedQuery(name = "PointInfos.findByPislideDuration", query = "SELECT p FROM PointInfos p WHERE p.pislideDuration = :pislideDuration"),
    @NamedQuery(name = "PointInfos.findByPislideShowDuration", query = "SELECT p FROM PointInfos p WHERE p.pislideShowDuration = :pislideShowDuration"),
    @NamedQuery(name = "PointInfos.findByPiEnabled", query = "SELECT p FROM PointInfos p WHERE p.piEnabled = :piEnabled"),
    @NamedQuery(name = "PointInfos.findByPilockOnStaff", query = "SELECT p FROM PointInfos p WHERE p.pilockOnStaff = :pilockOnStaff"),
    @NamedQuery(name = "PointInfos.findByPiDeleted", query = "SELECT p FROM PointInfos p WHERE p.piDeleted = :piDeleted"),
    @NamedQuery(name = "PointInfos.findByPiCreated", query = "SELECT p FROM PointInfos p WHERE p.piCreated = :piCreated"),
    @NamedQuery(name = "PointInfos.findByPiChanged", query = "SELECT p FROM PointInfos p WHERE p.piChanged = :piChanged"),
    @NamedQuery(name = "PointInfos.selectAllByLastChange", query = "SELECT p FROM PointInfos p ORDER BY p.piChanged DESC"),
    @NamedQuery(name = "PointInfos.findDistinctSlideShow", query = "SELECT p FROM PointInfos p WHERE p.piDeleted=0 AND p.piId IN (select Min(p.piId) FROM PointInfos p GROUP BY p.piGroup)")

})
public class PointInfos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pi_id", nullable = false)
    private Integer piId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_group", nullable = false)
    private int piGroup;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pi_slideshow", nullable = false, length = 45)
    private String piSlideshow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_slide", nullable = false)
    private int piSlide;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "pi_slidePath", nullable = false, length = 512)
    private String pislidePath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_description", nullable = false)
    private int piDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_slideDuration", nullable = false)
    private int pislideDuration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_slideShowDuration", nullable = false)
    private int pislideShowDuration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_enabled", nullable = false)
    private boolean piEnabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_lockOnStaff", nullable = false)
    private boolean pilockOnStaff;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_deleted", nullable = false)
    private boolean piDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date piCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pi_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date piChanged;
    @JoinColumn(name = "pi_staff", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff piStaff;
    @JoinColumn(name = "pi_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company piCompany;

    public PointInfos() {
    }

    public PointInfos(Integer piId) {
        this.piId = piId;
    }

    public PointInfos(Integer piId, int piGroup, String piSlideshow, int piSlide, String pislidePath, int piDescription, int pislideDuration, int pislideShowDuration, boolean piEnabled, boolean pilockOnStaff, boolean piDeleted, Date piCreated, Date piChanged) {
        this.piId = piId;
        this.piGroup = piGroup;
        this.piSlideshow = piSlideshow;
        this.piSlide = piSlide;
        this.pislidePath = pislidePath;
        this.piDescription = piDescription;
        this.pislideDuration = pislideDuration;
        this.pislideShowDuration = pislideShowDuration;
        this.piEnabled = piEnabled;
        this.pilockOnStaff = pilockOnStaff;
        this.piDeleted = piDeleted;
        this.piCreated = piCreated;
        this.piChanged = piChanged;
    }

    public Integer getPiId() {
        return piId;
    }

    public void setPiId(Integer piId) {
        this.piId = piId;
    }

    public int getPiGroup() {
        return piGroup;
    }

    public void setPiGroup(int piGroup) {
        this.piGroup = piGroup;
    }

    public String getPiSlideshow() {
        return piSlideshow;
    }

    public void setPiSlideshow(String piSlideshow) {
        this.piSlideshow = piSlideshow;
    }

    public int getPiSlide() {
        return piSlide;
    }

    public void setPiSlide(int piSlide) {
        this.piSlide = piSlide;
    }

    public String getPislidePath() {
        return pislidePath;
    }

    public void setPislidePath(String pislidePath) {
        this.pislidePath = pislidePath;
    }

    public int getPiDescription() {
        return piDescription;
    }

    public void setPiDescription(int piDescription) {
        this.piDescription = piDescription;
    }

    public int getPislideDuration() {
        return pislideDuration;
    }

    public void setPislideDuration(int pislideDuration) {
        this.pislideDuration = pislideDuration;
    }

    public int getPislideShowDuration() {
        return pislideShowDuration;
    }

    public void setPislideShowDuration(int pislideShowDuration) {
        this.pislideShowDuration = pislideShowDuration;
    }

    public boolean getPiEnabled() {
        return piEnabled;
    }

    public void setPiEnabled(boolean piEnabled) {
        this.piEnabled = piEnabled;
    }

    public boolean getPilockOnStaff() {
        return pilockOnStaff;
    }

    public void setPilockOnStaff(boolean pilockOnStaff) {
        this.pilockOnStaff = pilockOnStaff;
    }

    public boolean getPiDeleted() {
        return piDeleted;
    }

    public void setPiDeleted(boolean piDeleted) {
        this.piDeleted = piDeleted;
    }

    public Date getPiCreated() {
        return piCreated;
    }

    public void setPiCreated(Date piCreated) {
        this.piCreated = piCreated;
    }

    public Date getPiChanged() {
        return piChanged;
    }

    public void setPiChanged(Date piChanged) {
        this.piChanged = piChanged;
    }

    public Staff getPiStaff() {
        return piStaff;
    }

    public void setPiStaff(Staff piStaff) {
        this.piStaff = piStaff;
    }

    public Company getPiCompany() {
        return piCompany;
    }

    public void setPiCompany(Company piCompany) {
        this.piCompany = piCompany;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (piId != null ? piId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PointInfos)) {
            return false;
        }
        PointInfos other = (PointInfos) object;
        if ((this.piId == null && other.piId != null) || (this.piId != null && !this.piId.equals(other.piId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.smq.PointInfos[ piId=" + piId + " ]";
    }

}
