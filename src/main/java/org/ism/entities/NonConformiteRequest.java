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
@Table(name = "non_conformite_request", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ncr_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonConformiteRequest.findAll", query = "SELECT n FROM NonConformiteRequest n"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrId", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrId = :ncrId"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrOccured", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrOccured = :ncrOccured"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrProduct", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrProduct = :ncrProduct"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrTrace", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrTrace = :ncrTrace"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrQuantity", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrQuantity = :ncrQuantity"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrLink", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrLink = :ncrLink"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrTitle", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrTitle = :ncrTitle"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrCreated", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCreated = :ncrCreated"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrChanged", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrChanged = :ncrChanged"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrCompany", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCompany.cCompany = :ncrCompany"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrProcessus.pProcessus = :ncrProcessus"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrStaff", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrStaff.stStaff = :ncrStaff"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrState", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrNature", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrNature.ncnNature = :ncrNature"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrUnite", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrUnite.ncuUnite = :ncrUnite"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrGravity", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrGravity.ncgGravity = :ncrGravity"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrFrequency", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrFrequency.ncfFrequency = :ncrFrequency"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrStaffOnValidate", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrstaffOnValidate.stStaff = :ncrstaffOnValidate"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrStaffOnAction", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrstaffOnAction.stStaff = :ncrstaffOnAction"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrStaffOnRefuse", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrstaffOnRefuse.stStaff = :ncrstaffOnRefuse")})
public class NonConformiteRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ncr_id", nullable = false)
    private Integer ncrId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "ncr_occured", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncrOccured;
    
    @Size(max = 128)
    @Column(name = "ncr_product", length = 128)
    private String ncrProduct;
    
    @Size(max = 45)
    @Column(name = "ncr_trace", length = 45)
    private String ncrTrace;
    
    //@Max(value=500.0)  @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ncr_quantity", precision = 22)
    private Double ncrQuantity;
    

    @NotNull
    @Size(max = 128)
    @Column(name = "ncr_title", length = 128)
    private String ncrTitle;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "ncr_description", nullable = false, length = 65535)
    private String ncrDescription;
    
    @Size(max = 512)
    @Column(name = "ncr_link", length = 512)
    private String ncrLink;

    
    @Lob
    @Size(max = 65535)
    @Column(name = "ncr_descOnValidate", length = 65535)
    private String ncrdescOnValidate;
    
    @Column(name = "ncr_occuredValidate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncroccuredValidate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "ncr_descOnAction", length = 65535)
    private String ncrdescOnAction;
    
    @Column(name = "ncr_occuredAction")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncroccuredAction;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "ncr_descOnRefuse", length = 65535)
    private String ncrdescOnRefuse;
    
    @Column(name = "ncr_occuredRefuse")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncroccuredRefuse;
    
    @Column(name = "ncr_enddingAction")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncrenddingAction;
    
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncr_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncrCreated;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ncr_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncrChanged;
    
    
    @JoinColumn(name = "ncr_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff ncrStaff;

    @JoinColumn(name = "ncr_staffOnValidate", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff ncrstaffOnValidate;
    
    @JoinColumn(name = "ncr_staffOnAction", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff ncrstaffOnAction;
    
    @JoinColumn(name = "ncr_staffOnRefuse", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff ncrstaffOnRefuse;

    @JoinColumn(name = "ncr_state", referencedColumnName = "istate")
    @ManyToOne
    private IsmNcrstate ncrState;

    @JoinColumn(name = "ncr_nature", referencedColumnName = "ncn_nature")
    @ManyToOne
    private NonConformiteNature ncrNature;

    @JoinColumn(name = "ncr_unite", referencedColumnName = "ncu_unite", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteUnite ncrUnite;

    @JoinColumn(name = "ncr_gravity", referencedColumnName = "ncg_gravity", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteGravity ncrGravity;

    @JoinColumn(name = "ncr_frequency", referencedColumnName = "ncf_frequency", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteFrequency ncrFrequency;
    
    @JoinColumn(name = "ncr_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncrCompany;

    @JoinColumn(name = "ncr_processus", referencedColumnName = "p_processus", nullable = false)
    @ManyToOne(optional = false)
    private Processus ncrProcessus;
    
    
    
    public NonConformiteRequest() {
    }

    public NonConformiteRequest(Integer ncrId) {
        this.ncrId = ncrId;
    }

    public NonConformiteRequest(Integer ncrId, Date ncrOccured, String ncrDescription, Date ncrCreated, Date ncrChanged) {
        this.ncrId = ncrId;
        this.ncrOccured = ncrOccured;
        this.ncrDescription = ncrDescription;
        this.ncrCreated = ncrCreated;
        this.ncrChanged = ncrChanged;
    }

    public Integer getNcrId() {
        return ncrId;
    }

    public void setNcrId(Integer ncrId) {
        this.ncrId = ncrId;
    }

    public Date getNcrOccured() {
        return ncrOccured;
    }

    public void setNcrOccured(Date ncrOccured) {
        this.ncrOccured = ncrOccured;
    }

    public String getNcrProduct() {
        return ncrProduct;
    }

    public void setNcrProduct(String ncrProduct) {
        this.ncrProduct = ncrProduct;
    }

    public String getNcrTrace() {
        return ncrTrace;
    }

    public void setNcrTrace(String ncrTrace) {
        this.ncrTrace = ncrTrace;
    }

    public Double getNcrQuantity() {
        return ncrQuantity;
    }

    public void setNcrQuantity(Double ncrQuantity) {
        this.ncrQuantity = ncrQuantity;
    }

    public String getNcrTitle() {
        return ncrTitle;
    }

    public void setNcrTitle(String ncrTitle) {
        this.ncrTitle = ncrTitle;
    }

    public String getNcrDescription() {
        return ncrDescription;
    }

    public void setNcrDescription(String ncrDescription) {
        this.ncrDescription = ncrDescription;
    }

    public String getNcrLink() {
        return ncrLink;
    }

    public void setNcrLink(String ncrLink) {
        this.ncrLink = ncrLink;
    }

    public Date getNcrCreated() {
        return ncrCreated;
    }

    public void setNcrCreated(Date ncrCreated) {
        this.ncrCreated = ncrCreated;
    }

    public Date getNcrChanged() {
        return ncrChanged;
    }

    public void setNcrChanged(Date ncrChanged) {
        this.ncrChanged = ncrChanged;
    }

    public Company getNcrCompany() {
        return ncrCompany;
    }

    public void setNcrCompany(Company ncrCompany) {
        this.ncrCompany = ncrCompany;
    }

    public Processus getNcrProcessus() {
        return ncrProcessus;
    }

    public void setNcrProcessus(Processus ncrProcessus) {
        this.ncrProcessus = ncrProcessus;
    }

    public Staff getNcrStaff() {
        return ncrStaff;
    }

    public void setNcrStaff(Staff ncrStaff) {
        this.ncrStaff = ncrStaff;
    }

    public IsmNcrstate getNcrState() {
        return ncrState;
    }

    public void setNcrState(IsmNcrstate ncrState) {
        this.ncrState = ncrState;
    }

    public NonConformiteNature getNcrNature() {
        return ncrNature;
    }

    public void setNcrNature(NonConformiteNature ncrNature) {
        this.ncrNature = ncrNature;
    }

    public NonConformiteUnite getNcrUnite() {
        return ncrUnite;
    }

    public void setNcrUnite(NonConformiteUnite ncrUnite) {
        this.ncrUnite = ncrUnite;
    }

    public NonConformiteGravity getNcrGravity() {
        return ncrGravity;
    }

    public void setNcrGravity(NonConformiteGravity ncrGravity) {
        this.ncrGravity = ncrGravity;
    }

    public NonConformiteFrequency getNcrFrequency() {
        return ncrFrequency;
    }

    public void setNcrFrequency(NonConformiteFrequency ncrFrequency) {
        this.ncrFrequency = ncrFrequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncrId != null ? ncrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NonConformiteRequest)) {
            return false;
        }
        NonConformiteRequest other = (NonConformiteRequest) object;
        if ((this.ncrId == null && other.ncrId != null) || (this.ncrId != null && !this.ncrId.equals(other.ncrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.NonConformiteRequest[ ncrId=" + ncrId + " ]";
    }

    public String getNcrdescOnValidate() {
        return ncrdescOnValidate;
    }

    public void setNcrdescOnValidate(String ncrdescOnValidate) {
        this.ncrdescOnValidate = ncrdescOnValidate;
    }

    public Date getNcroccuredValidate() {
        return ncroccuredValidate;
    }

    public void setNcroccuredValidate(Date ncroccuredValidate) {
        this.ncroccuredValidate = ncroccuredValidate;
    }

    public String getNcrdescOnAction() {
        return ncrdescOnAction;
    }

    public void setNcrdescOnAction(String ncrdescOnAction) {
        this.ncrdescOnAction = ncrdescOnAction;
    }

    public Date getNcroccuredAction() {
        return ncroccuredAction;
    }

    public void setNcroccuredAction(Date ncroccuredAction) {
        this.ncroccuredAction = ncroccuredAction;
    }

    public String getNcrdescOnRefuse() {
        return ncrdescOnRefuse;
    }

    public void setNcrdescOnRefuse(String ncrdescOnRefuse) {
        this.ncrdescOnRefuse = ncrdescOnRefuse;
    }

    public Date getNcroccuredRefuse() {
        return ncroccuredRefuse;
    }

    public void setNcroccuredRefuse(Date ncroccuredRefuse) {
        this.ncroccuredRefuse = ncroccuredRefuse;
    }

    public Staff getNcrstaffOnValidate() {
        return ncrstaffOnValidate;
    }

    public void setNcrstaffOnValidate(Staff ncrstaffOnValidate) {
        this.ncrstaffOnValidate = ncrstaffOnValidate;
    }

    public Staff getNcrstaffOnAction() {
        return ncrstaffOnAction;
    }

    public void setNcrstaffOnAction(Staff ncrstaffOnAction) {
        this.ncrstaffOnAction = ncrstaffOnAction;
    }

    public Staff getNcrstaffOnRefuse() {
        return ncrstaffOnRefuse;
    }

    public void setNcrstaffOnRefuse(Staff ncrstaffOnRefuse) {
        this.ncrstaffOnRefuse = ncrstaffOnRefuse;
    }

    public Date getNcrenddingAction() {
        return ncrenddingAction;
    }

    public void setNcrenddingAction(Date ncrenddingAction) {
        this.ncrenddingAction = ncrenddingAction;
    }

}
