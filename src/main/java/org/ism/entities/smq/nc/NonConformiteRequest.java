/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.smq.nc;

import org.ism.entities.app.IsmNcrstate;
import org.ism.entities.smq.Processus;
import org.ism.entities.admin.Company;
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
import javax.persistence.Lob;
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
import org.ism.entities.hr.Staff;

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
    @NamedQuery(name = "NonConformiteRequest.findByNcrTitle", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrTitle = :ncrTitle"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrTitleOfCompany", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrTitle = :ncrTitle AND n.ncrCompany = :ncrCompany"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrLink", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrLink = :ncrLink"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrenddingAction", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrenddingAction = :ncrenddingAction"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrCreated", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCreated = :ncrCreated"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrChanged", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrChanged = :ncrChanged"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrApprouved", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrApprouved = :ncrApprouved"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrapprouvedDate", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrapprouvedDate = :ncrapprouvedDate"),
    @NamedQuery(name = "NonConformiteRequest.selectAllByLastChange", query = "SELECT n FROM NonConformiteRequest n ORDER BY n.ncrChanged DESC"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrCompany", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCompany.cCompany = :ncrCompany"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrProcessus.pProcessus = :ncrProcessus"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrStaff", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrStaff.stStaff = :ncrStaff"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrState", query = "SELECT n.ncrState FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrNature", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrNature.ncnNature = :ncrNature"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrUnite", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrUnite.ncuUnite = :ncrUnite"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrGravity", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrGravity.ncgGravity = :ncrGravity"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrFrequency", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrFrequency.ncfFrequency = :ncrFrequency"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrClientname", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrClientname = :ncrClientname"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrClientaddress", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrClientaddress = :ncrClientaddress"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrClientphone", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrClientphone = :ncrClientphone"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrClientemail", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrClientemail = :ncrClientemail"),
    @NamedQuery(name = "NonConformiteRequest.findByNcrClienttype", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrClienttype = :ncrClienttype"),
    
    @NamedQuery(name = "NonConformiteRequest.countProcessusInState", query = "SELECT count(n) FROM NonConformiteRequest n WHERE n.ncrProcessus = :ncrProcessus AND n.ncrState.istate = :ncrState"),

   
    @NamedQuery(name = "NonConformiteRequest.itemsCreateInRange", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCreated >= :ncrFrom AND n.ncrCreated < :ncrTo"),
    @NamedQuery(name = "NonConformiteRequest.itemsApprouvedInRange", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrapprouvedDate >= :ncrFrom AND n.ncrapprouvedDate < :ncrTo"),
    @NamedQuery(name = "NonConformiteRequest.itemsStateInRange", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState AND  n.ncrCreated >= :ncrFrom AND n.ncrCreated < :ncrTo"),
    @NamedQuery(name = "NonConformiteRequest.itemsStateInChangedRange", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState AND  n.ncrChanged >= :ncrFrom AND n.ncrChanged < :ncrTo"),

    @NamedQuery(name = "NonConformiteRequest.itemsCreateInRangeByProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrCreated >= :ncrFrom AND n.ncrCreated < :ncrTo AND n.ncrProcessus= :ncrProcessus"),
    @NamedQuery(name = "NonConformiteRequest.itemsApprouvedInRangeByProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrapprouvedDate >= :ncrFrom AND n.ncrapprouvedDate < :ncrTo AND n.ncrProcessus= :ncrProcessus"),
    @NamedQuery(name = "NonConformiteRequest.itemsStateInRangeByProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState AND  n.ncrCreated >= :ncrFrom AND n.ncrCreated < :ncrTo AND n.ncrProcessus= :ncrProcessus"),
    @NamedQuery(name = "NonConformiteRequest.itemsStateInChangedRangeByProcessus", query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrState.istate = :ncrState AND  n.ncrChanged >= :ncrFrom AND n.ncrChanged < :ncrTo AND n.ncrProcessus= :ncrProcessus"),})
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ncr_quantity", precision = 22)
    private Double ncrQuantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ncr_title", nullable = false, length = 128)
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
    @Column(name = "ncr_approuved")
    private Boolean ncrApprouved;
    @Column(name = "ncr_approuvedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ncrapprouvedDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "ncr_approuvedDesc", length = 65535)
    private String ncrapprouvedDesc;

    @Size(max = 255)
    @Column(name = "ncr_clientname")
    private String ncrClientname;
    @Size(max = 255)
    @Column(name = "ncr_clientaddress")
    private String ncrClientaddress;
    @Size(max = 45)
    @Column(name = "ncr_clientphone")
    private String ncrClientphone;
    @Size(max = 255)
    @Column(name = "ncr_clientemail")
    private String ncrClientemail;
    @Size(max = 128)
    @Column(name = "ncr_clienttype")
    private String ncrClienttype;

    @JoinColumn(name = "ncr_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company ncrCompany;
    @JoinColumn(name = "ncr_approuver", referencedColumnName = "st_staff")
    @ManyToOne
    private Staff ncrApprouver;
    @JoinColumn(name = "ncr_frequency", referencedColumnName = "ncf_frequency", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteFrequency ncrFrequency;
    @JoinColumn(name = "ncr_gravity", referencedColumnName = "ncg_gravity", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteGravity ncrGravity;
    @JoinColumn(name = "ncr_nature", referencedColumnName = "ncn_nature")
    @ManyToOne
    private NonConformiteNature ncrNature;
    @JoinColumn(name = "ncr_processus", referencedColumnName = "p_processus", nullable = false)
    @ManyToOne(optional = false)
    private Processus ncrProcessus;
    @JoinColumn(name = "ncr_staff", referencedColumnName = "st_staff", nullable = false)
    @ManyToOne(optional = false)
    private Staff ncrStaff;
    @JoinColumn(name = "ncr_unite", referencedColumnName = "ncu_unite", nullable = false)
    @ManyToOne(optional = false)
    private NonConformiteUnite ncrUnite;

    @JoinColumn(name = "ncr_state", referencedColumnName = "istate")
    @ManyToOne
    private IsmNcrstate ncrState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncaNc")
    private Collection<NonConformiteActions> nonConformiteActionsCollection;

    public NonConformiteRequest() {
    }

    public NonConformiteRequest(Integer ncrId) {
        this.ncrId = ncrId;
    }

    public NonConformiteRequest(Integer ncrId, Date ncrOccured, String ncrTitle, String ncrDescription, Date ncrCreated, Date ncrChanged) {
        this.ncrId = ncrId;
        this.ncrOccured = ncrOccured;
        this.ncrTitle = ncrTitle;
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

    public Date getNcrenddingAction() {
        return ncrenddingAction;
    }

    public void setNcrenddingAction(Date ncrenddingAction) {
        this.ncrenddingAction = ncrenddingAction;
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

    public Boolean getNcrApprouved() {
        return ncrApprouved;
    }

    public void setNcrApprouved(Boolean ncrApprouved) {
        this.ncrApprouved = ncrApprouved;
    }

    public Date getNcrapprouvedDate() {
        return ncrapprouvedDate;
    }

    public void setNcrapprouvedDate(Date ncrapprouvedDate) {
        this.ncrapprouvedDate = ncrapprouvedDate;
    }

    public String getNcrapprouvedDesc() {
        return ncrapprouvedDesc;
    }

    public void setNcrapprouvedDesc(String ncrapprouvedDesc) {
        this.ncrapprouvedDesc = ncrapprouvedDesc;
    }

    public Company getNcrCompany() {
        return ncrCompany;
    }

    public void setNcrCompany(Company ncrCompany) {
        this.ncrCompany = ncrCompany;
    }

    public Staff getNcrApprouver() {
        return ncrApprouver;
    }

    public void setNcrApprouver(Staff ncrApprouver) {
        this.ncrApprouver = ncrApprouver;
    }

    public NonConformiteFrequency getNcrFrequency() {
        return ncrFrequency;
    }

    public void setNcrFrequency(NonConformiteFrequency ncrFrequency) {
        this.ncrFrequency = ncrFrequency;
    }

    public NonConformiteGravity getNcrGravity() {
        return ncrGravity;
    }

    public void setNcrGravity(NonConformiteGravity ncrGravity) {
        this.ncrGravity = ncrGravity;
    }

    public NonConformiteNature getNcrNature() {
        return ncrNature;
    }

    public void setNcrNature(NonConformiteNature ncrNature) {
        this.ncrNature = ncrNature;
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

    public NonConformiteUnite getNcrUnite() {
        return ncrUnite;
    }

    public void setNcrUnite(NonConformiteUnite ncrUnite) {
        this.ncrUnite = ncrUnite;
    }

    public IsmNcrstate getNcrState() {
        return ncrState;
    }

    public void setNcrState(IsmNcrstate ncrState) {
        this.ncrState = ncrState;
    }

    @XmlTransient
    public Collection<NonConformiteActions> getNonConformiteActionsCollection() {
        return nonConformiteActionsCollection;
    }

    public void setNonConformiteActionsCollection(Collection<NonConformiteActions> nonConformiteActionsCollection) {
        this.nonConformiteActionsCollection = nonConformiteActionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncrId != null ? ncrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return ncrProcessus + " - " + ncrTitle + " [" + ncrId + "]";
        //return "org.ism.entities.NonConformiteRequest[ ncrId=" + ncrId + " ]";
    }

    public String getNcrClientname() {
        return ncrClientname;
    }

    public void setNcrClientname(String ncrClientname) {
        this.ncrClientname = ncrClientname;
    }

    public String getNcrClientaddress() {
        return ncrClientaddress;
    }

    public void setNcrClientaddress(String ncrClientaddress) {
        this.ncrClientaddress = ncrClientaddress;
    }

    public String getNcrClientphone() {
        return ncrClientphone;
    }

    public void setNcrClientphone(String ncrClientphone) {
        this.ncrClientphone = ncrClientphone;
    }

    public String getNcrClientemail() {
        return ncrClientemail;
    }

    public void setNcrClientemail(String ncrClientemail) {
        this.ncrClientemail = ncrClientemail;
    }

    public String getNcrClienttype() {
        return ncrClienttype;
    }

    public void setNcrClienttype(String ncrClienttype) {
        this.ncrClienttype = ncrClienttype;
    }

}
