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
import org.ism.entities.hr.Staff;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "analyse_data", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ad_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalyseData.findAll", query = "SELECT a FROM AnalyseData a"),
    @NamedQuery(name = "AnalyseData.findByAdId", query = "SELECT a FROM AnalyseData a WHERE a.adId = :adId"),
    @NamedQuery(name = "AnalyseData.findByAdValue", query = "SELECT a FROM AnalyseData a WHERE a.adValue = :adValue"),
    @NamedQuery(name = "AnalyseData.findByAdValueT", query = "SELECT a FROM AnalyseData a WHERE a.adValueT = :adValueT"),
    @NamedQuery(name = "AnalyseData.findByAdValidate", query = "SELECT a FROM AnalyseData a WHERE a.adValidate = :adValidate"),
    @NamedQuery(name = "AnalyseData.findByAdenlimitHH", query = "SELECT a FROM AnalyseData a WHERE a.adenlimitHH = :adenlimitHH"),
    @NamedQuery(name = "AnalyseData.findByAdlimitHH", query = "SELECT a FROM AnalyseData a WHERE a.adlimitHH = :adlimitHH"),
    @NamedQuery(name = "AnalyseData.findByAdenlimitH", query = "SELECT a FROM AnalyseData a WHERE a.adenlimitH = :adenlimitH"),
    @NamedQuery(name = "AnalyseData.findByAdlimitH", query = "SELECT a FROM AnalyseData a WHERE a.adlimitH = :adlimitH"),
    @NamedQuery(name = "AnalyseData.findByAdenlimitB", query = "SELECT a FROM AnalyseData a WHERE a.adenlimitB = :adenlimitB"),
    @NamedQuery(name = "AnalyseData.findByAdlimitB", query = "SELECT a FROM AnalyseData a WHERE a.adlimitB = :adlimitB"),
    @NamedQuery(name = "AnalyseData.findByAdenlimitBB", query = "SELECT a FROM AnalyseData a WHERE a.adenlimitBB = :adenlimitBB"),
    @NamedQuery(name = "AnalyseData.findByAdlimitBB", query = "SELECT a FROM AnalyseData a WHERE a.adlimitBB = :adlimitBB"),
    @NamedQuery(name = "AnalyseData.findByAdPoint", query = "SELECT a FROM AnalyseData a WHERE a.adPoint = :adPoint"),
    @NamedQuery(name = "AnalyseData.findByAdType", query = "SELECT a FROM AnalyseData a WHERE a.adType = :adType"),
    @NamedQuery(name = "AnalyseData.findByAdPointStr", query = "SELECT a FROM AnalyseData a WHERE a.adPoint.apPoint = :adPoint"),
    @NamedQuery(name = "AnalyseData.findByAdTypeStr", query = "SELECT a FROM AnalyseData a WHERE a.adType.atType = :adType"),
    @NamedQuery(name = "AnalyseData.findByAdSampler", query = "SELECT a FROM AnalyseData a WHERE a.adSampler = :adSampler"),
    @NamedQuery(name = "AnalyseData.findByAdValidator", query = "SELECT a FROM AnalyseData a WHERE a.adValidator = :adValidator"),
    @NamedQuery(name = "AnalyseData.findByAdPointType", query = "SELECT a FROM AnalyseData a WHERE a.adPoint = :adPoint AND a.adType = :adType"),
    @NamedQuery(name = "AnalyseData.findByAdPointTypeSampleTimeRange", query = "SELECT a FROM AnalyseData a WHERE a.adPoint = :adPoint AND a.adType = :adType AND a.adsampleTime >= :from AND a.adsampleTime <= :to"),
    @NamedQuery(name = "AnalyseData.findByAdObservation", query = "SELECT a FROM AnalyseData a WHERE a.adObservation = :adObservation"),
    @NamedQuery(name = "AnalyseData.findByAdBatch", query = "SELECT a FROM AnalyseData a WHERE a.adBatch = :adBatch"),
    @NamedQuery(name = "AnalyseData.findByAdsampleTime", query = "SELECT a FROM AnalyseData a WHERE a.adsampleTime = :adsampleTime"),
    @NamedQuery(name = "AnalyseData.findByAdDeleted", query = "SELECT a FROM AnalyseData a WHERE a.adDeleted = :adDeleted"),
    @NamedQuery(name = "AnalyseData.findByAdCreated", query = "SELECT a FROM AnalyseData a WHERE a.adCreated = :adCreated"),
    @NamedQuery(name = "AnalyseData.findByAdChanged", query = "SELECT a FROM AnalyseData a WHERE a.adChanged = :adChanged"),
    @NamedQuery(name = "AnalyseData.selectAllByLastChange", query = "SELECT a FROM AnalyseData a ORDER BY a.adChanged DESC")
})
public class AnalyseData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ad_id", nullable = false)
    private Integer adId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_value", nullable = false)
    private double adValue;
    @Size(max = 45)
    @Column(name = "ad_value_t", length = 45)
    private String adValueT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_validate", nullable = false)
    private boolean adValidate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_enlimitHH", nullable = false)
    private boolean adenlimitHH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_limitHH", nullable = false)
    private double adlimitHH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_enlimitH", nullable = false)
    private boolean adenlimitH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_limitH", nullable = false)
    private double adlimitH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_enlimitB", nullable = false)
    private boolean adenlimitB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_limitB", nullable = false)
    private double adlimitB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_enlimitBB", nullable = false)
    private boolean adenlimitBB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_limitBB", nullable = false)
    private double adlimitBB;
    @Column(name = "ad_batch")
    private Integer adBatch;
    @Column(name = "ad_sampleTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adsampleTime;
    @Size(max = 255)
    @Column(name = "ad_observation", length = 255)
    private String adObservation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_deleted", nullable = false)
    private boolean adDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date adCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ad_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date adChanged;
    @JoinColumn(name = "ad_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company adCompany;
    @JoinColumn(name = "ad_point", referencedColumnName = "ap_point", nullable = false)
    @ManyToOne(optional = false)
    private AnalysePoint adPoint;
    @JoinColumn(name = "ad_sampler", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff adSampler;
    @JoinColumn(name = "ad_type", referencedColumnName = "at_type", nullable = false)
    @ManyToOne(optional = false)
    private AnalyseType adType;
    @JoinColumn(name = "ad_validator", referencedColumnName = "st_staff", nullable = true)
    @ManyToOne(optional = false)
    private Staff adValidator;

    public AnalyseData() {
    }

    public AnalyseData(Integer adId) {
        this.adId = adId;
    }

    public AnalyseData(Integer adId, double adValue, boolean adValidate, boolean adenlimitHH, double adlimitHH, boolean adenlimitH, double adlimitH, boolean adenlimitB, double adlimitB, boolean adenlimitBB, double adlimitBB, boolean adDeleted, Date adCreated, Date adChanged) {
        this.adId = adId;
        this.adValue = adValue;
        this.adValidate = adValidate;
        this.adenlimitHH = adenlimitHH;
        this.adlimitHH = adlimitHH;
        this.adenlimitH = adenlimitH;
        this.adlimitH = adlimitH;
        this.adenlimitB = adenlimitB;
        this.adlimitB = adlimitB;
        this.adenlimitBB = adenlimitBB;
        this.adlimitBB = adlimitBB;
        this.adDeleted = adDeleted;
        this.adCreated = adCreated;
        this.adChanged = adChanged;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public double getAdValue() {
        return adValue;
    }

    public void setAdValue(double adValue) {
        this.adValue = adValue;
    }

    public String getAdValueT() {
        return adValueT;
    }

    public void setAdValueT(String adValueT) {
        this.adValueT = adValueT;
    }

    public boolean getAdValidate() {
        return adValidate;
    }

    public void setAdValidate(boolean adValidate) {
        this.adValidate = adValidate;
    }

    public boolean getAdenlimitHH() {
        return adenlimitHH;
    }

    public void setAdenlimitHH(boolean adenlimitHH) {
        this.adenlimitHH = adenlimitHH;
    }

    public double getAdlimitHH() {
        return adlimitHH;
    }

    public void setAdlimitHH(double adlimitHH) {
        this.adlimitHH = adlimitHH;
    }

    public boolean getAdenlimitH() {
        return adenlimitH;
    }

    public void setAdenlimitH(boolean adenlimitH) {
        this.adenlimitH = adenlimitH;
    }

    public double getAdlimitH() {
        return adlimitH;
    }

    public void setAdlimitH(double adlimitH) {
        this.adlimitH = adlimitH;
    }

    public boolean getAdenlimitB() {
        return adenlimitB;
    }

    public void setAdenlimitB(boolean adenlimitB) {
        this.adenlimitB = adenlimitB;
    }

    public double getAdlimitB() {
        return adlimitB;
    }

    public void setAdlimitB(double adlimitB) {
        this.adlimitB = adlimitB;
    }

    public boolean getAdenlimitBB() {
        return adenlimitBB;
    }

    public void setAdenlimitBB(boolean adenlimitBB) {
        this.adenlimitBB = adenlimitBB;
    }

    public double getAdlimitBB() {
        return adlimitBB;
    }

    public void setAdlimitBB(double adlimitBB) {
        this.adlimitBB = adlimitBB;
    }

    public String getAdObservation() {
        return adObservation;
    }

    public void setAdObservation(String adObservation) {
        this.adObservation = adObservation;
    }

    public Integer getAdBatch() {
        return adBatch;
    }

    public void setAdBatch(Integer adBatch) {
        this.adBatch = adBatch;
    }

    public Date getAdsampleTime() {
        return adsampleTime;
    }

    public void setAdsampleTime(Date adsampleTime) {
        this.adsampleTime = adsampleTime;
    }

    public boolean getAdDeleted() {
        return adDeleted;
    }

    public void setAdDeleted(boolean adDeleted) {
        this.adDeleted = adDeleted;
    }

    public Date getAdCreated() {
        return adCreated;
    }

    public void setAdCreated(Date adCreated) {
        this.adCreated = adCreated;
    }

    public Date getAdChanged() {
        return adChanged;
    }

    public void setAdChanged(Date adChanged) {
        this.adChanged = adChanged;
    }

    public Company getAdCompany() {
        return adCompany;
    }

    public void setAdCompany(Company adCompany) {
        this.adCompany = adCompany;
    }

    public AnalysePoint getAdPoint() {
        return adPoint;
    }

    public void setAdPoint(AnalysePoint adPoint) {
        this.adPoint = adPoint;
    }

    public Staff getAdSampler() {
        return adSampler;
    }

    public void setAdSampler(Staff adSampler) {
        this.adSampler = adSampler;
    }

    public AnalyseType getAdType() {
        return adType;
    }

    public void setAdType(AnalyseType adType) {
        this.adType = adType;
    }

    public Staff getAdValidator() {
        return adValidator;
    }

    public void setAdValidator(Staff adValidator) {
        this.adValidator = adValidator;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adId != null ? adId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalyseData)) {
            return false;
        }
        AnalyseData other = (AnalyseData) object;
        if ((this.adId == null && other.adId != null) || (this.adId != null && !this.adId.equals(other.adId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.AnalyseData[ adId=" + adId + " ]";
    }

}
