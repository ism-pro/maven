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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "non_conformite", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nc_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformite.findAll", query = "SELECT n FROM NonConformite n"),
    @NamedQuery(name = "NonConformite.findByNcId", query = "SELECT n FROM NonConformite n WHERE n.ncId = :ncId"),
    @NamedQuery(name = "NonConformite.findByNcOccured", query = "SELECT n FROM NonConformite n WHERE n.ncOccured = :ncOccured"),
    @NamedQuery(name = "NonConformite.findByNcProduct", query = "SELECT n FROM NonConformite n WHERE n.ncProduct = :ncProduct"),
    @NamedQuery(name = "NonConformite.findByNcTrace", query = "SELECT n FROM NonConformite n WHERE n.ncTrace = :ncTrace"),
    @NamedQuery(name = "NonConformite.findByNcQuantity", query = "SELECT n FROM NonConformite n WHERE n.ncQuantity = :ncQuantity"),
    @NamedQuery(name = "NonConformite.findByNcDelay", query = "SELECT n FROM NonConformite n WHERE n.ncDelay = :ncDelay"),
    @NamedQuery(name = "NonConformite.findByNcLink", query = "SELECT n FROM NonConformite n WHERE n.ncLink = :ncLink"),
    @NamedQuery(name = "NonConformite.findByNcTitle", query = "SELECT n FROM NonConformite n WHERE n.ncTitle = :ncTitle"),
    @NamedQuery(name = "NonConformite.findByNcCreated", query = "SELECT n FROM NonConformite n WHERE n.ncCreated = :ncCreated"),
    @NamedQuery(name = "NonConformite.findByNcChanged", query = "SELECT n FROM NonConformite n WHERE n.ncChanged = :ncChanged")})
public class NonConformite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nc_id", nullable = false)
    private Integer ncId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nc_occured", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncOccured;
    @Size(max = 128)
    @Column(name = "nc_product", length = 128)
    private String ncProduct;
    @Size(max = 45)
    @Column(name = "nc_trace", length = 45)
    private String ncTrace;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nc_quantity", precision = 22)
    private Double ncQuantity;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nc_description", nullable = false, length = 65535)
    private String ncDescription;
    @Lob
    @Size(max = 65535)
    @Column(name = "nc_action", length = 65535)
    private String ncAction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nc_delay", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncDelay;
    @Size(max = 512)
    @Column(name = "nc_link", length = 512)
    private String ncLink;
    
        
    @NotNull
    @Size(max = 128)
    @Column(name = "nc_title", length = 128)
    private String ncTitle;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nc_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nc_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncChanged;
    @JoinColumn(name = "nc_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncCompany;
    @JoinColumn(name = "nc_processus", referencedColumnName = "p_processus", nullable = false)
    @ManyToOne(optional = false)
    private Processus ncProcessus;
    @JoinColumn(name = "nc_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff ncStaff;
    @JoinColumn(name = "nc_state", referencedColumnName = "istate")
    @ManyToOne
    private IsmNcrstate ncState;
    @JoinColumn(name = "nc_nature", referencedColumnName = "ncn_nature")
    @ManyToOne
    private NonConformiteNature ncNature;
    @JoinColumn(name = "nc_unite", referencedColumnName = "ncu_unite")
    @ManyToOne
    private NonConformiteUnite ncUnite;
    @JoinColumn(name = "nc_gravity", referencedColumnName = "ncg_gravity", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteGravity ncGravity;
    @JoinColumn(name = "nc_frequency", referencedColumnName = "ncf_frequency", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteFrequency ncFrequency;

    public NonConformite() {
    }

    public NonConformite(Integer ncId) {
        this.ncId = ncId;
    }

    public NonConformite(Integer ncId, Date ncOccured, String ncDescription, Date ncDelay, Date ncCreated, Date ncChanged) {
        this.ncId = ncId;
        this.ncOccured = ncOccured;
        this.ncDescription = ncDescription;
        this.ncDelay = ncDelay;
        this.ncCreated = ncCreated;
        this.ncChanged = ncChanged;
    }

    public Integer getNcId() {
        return ncId;
    }

    public void setNcId(Integer ncId) {
        this.ncId = ncId;
    }

    public Date getNcOccured() {
        return ncOccured;
    }

    public void setNcOccured(Date ncOccured) {
        this.ncOccured = ncOccured;
    }

    public String getNcProduct() {
        return ncProduct;
    }

    public void setNcProduct(String ncProduct) {
        this.ncProduct = ncProduct;
    }

    public String getNcTrace() {
        return ncTrace;
    }

    public void setNcTrace(String ncTrace) {
        this.ncTrace = ncTrace;
    }

    public Double getNcQuantity() {
        return ncQuantity;
    }

    public void setNcQuantity(Double ncQuantity) {
        this.ncQuantity = ncQuantity;
    }

    public String getNcDescription() {
        return ncDescription;
    }

    public void setNcDescription(String ncDescription) {
        this.ncDescription = ncDescription;
    }

    public String getNcAction() {
        return ncAction;
    }

    public void setNcAction(String ncAction) {
        this.ncAction = ncAction;
    }

    public Date getNcDelay() {
        return ncDelay;
    }

    public void setNcDelay(Date ncDelay) {
        this.ncDelay = ncDelay;
    }

    public String getNcLink() {
        return ncLink;
    }

    public void setNcLink(String ncLink) {
        this.ncLink = ncLink;
    }

    public String getNcTitle() {
        return ncTitle;
    }

    public void setNcTitle(String ncTitle) {
        this.ncTitle = ncTitle;
    }

    
    
    public Date getNcCreated() {
        return ncCreated;
    }

    public void setNcCreated(Date ncCreated) {
        this.ncCreated = ncCreated;
    }

    public Date getNcChanged() {
        return ncChanged;
    }

    public void setNcChanged(Date ncChanged) {
        this.ncChanged = ncChanged;
    }

    public Company getNcCompany() {
        return ncCompany;
    }

    public void setNcCompany(Company ncCompany) {
        this.ncCompany = ncCompany;
    }

    public Processus getNcProcessus() {
        return ncProcessus;
    }

    public void setNcProcessus(Processus ncProcessus) {
        this.ncProcessus = ncProcessus;
    }

    public Staff getNcStaff() {
        return ncStaff;
    }

    public void setNcStaff(Staff ncStaff) {
        this.ncStaff = ncStaff;
    }

    public IsmNcrstate getNcState() {
        return ncState;
    }

    public void setNcState(IsmNcrstate ncState) {
        this.ncState = ncState;
    }

    public NonConformiteNature getNcNature() {
        return ncNature;
    }

    public void setNcNature(NonConformiteNature ncNature) {
        this.ncNature = ncNature;
    }

    public NonConformiteUnite getNcUnite() {
        return ncUnite;
    }

    public void setNcUnite(NonConformiteUnite ncUnite) {
        this.ncUnite = ncUnite;
    }

    public NonConformiteGravity getNcGravity() {
        return ncGravity;
    }

    public void setNcGravity(NonConformiteGravity ncGravity) {
        this.ncGravity = ncGravity;
    }

    public NonConformiteFrequency getNcFrequency() {
        return ncFrequency;
    }

    public void setNcFrequency(NonConformiteFrequency ncFrequency) {
        this.ncFrequency = ncFrequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncId != null ? ncId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformite)) {
            return false;
        }
        NonConformite other = (NonConformite) object;
        if ((this.ncId == null && other.ncId != null) || (this.ncId != null && !this.ncId.equals(other.ncId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.NonConformite[ ncId=" + ncId + " ]";
    }
    
}
