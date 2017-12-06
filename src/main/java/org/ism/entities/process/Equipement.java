/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.process;

import org.ism.entities.admin.Company;
import org.ism.entities.process.ctrl.AnalysePoint;
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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "equipement", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"e_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipement.findAll", query = "SELECT e FROM Equipement e"),
    @NamedQuery(name = "Equipement.findByEId", query = "SELECT e FROM Equipement e WHERE e.eId = :eId"),
    @NamedQuery(name = "Equipement.findByEEquipement", query = "SELECT e FROM Equipement e WHERE e.eEquipement = :eEquipement"),
    @NamedQuery(name = "Equipement.findByEDesignation", query = "SELECT e FROM Equipement e WHERE e.eDesignation = :eDesignation"),
    @NamedQuery(name = "Equipement.findByEEquipementOfCompany", query = "SELECT e FROM Equipement e WHERE e.eEquipement = :eEquipement AND e.eCompany = :eCompany"),
    @NamedQuery(name = "Equipement.findByEDesignationOfCompany", query = "SELECT e FROM Equipement e WHERE e.eDesignation = :eDesignation AND e.eCompany = :eCompany"),
    @NamedQuery(name = "Equipement.findByECf", query = "SELECT e FROM Equipement e WHERE e.eCf = :eCf"),
    @NamedQuery(name = "Equipement.findByEFamille", query = "SELECT e FROM Equipement e WHERE e.eFamille = :eFamille"),
    @NamedQuery(name = "Equipement.findByESsFamille", query = "SELECT e FROM Equipement e WHERE e.eSsFamille = :eSsFamille"),
    @NamedQuery(name = "Equipement.findByEGroupe", query = "SELECT e FROM Equipement e WHERE e.eGroupe = :eGroupe"),
    @NamedQuery(name = "Equipement.findByEResponsable", query = "SELECT e FROM Equipement e WHERE e.eResponsable = :eResponsable"),
    @NamedQuery(name = "Equipement.findByEDeleted", query = "SELECT e FROM Equipement e WHERE e.eDeleted = :eDeleted"),
    @NamedQuery(name = "Equipement.findByECreated", query = "SELECT e FROM Equipement e WHERE e.eCreated = :eCreated"),
    @NamedQuery(name = "Equipement.findByEChanged", query = "SELECT e FROM Equipement e WHERE e.eChanged = :eChanged"),
    @NamedQuery(name = "Equipement.selectAllByLastChange", query = "SELECT e FROM Equipement e ORDER BY e.eChanged DESC")
})
public class Equipement implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apEquipement")
    private Collection<AnalysePoint> analysePointCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_id", nullable = false)
    private Integer eId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "e_equipement", nullable = false, length = 45)
    private String eEquipement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "e_designation", nullable = false, length = 255)
    private String eDesignation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "e_cf", nullable = false, length = 45)
    private String eCf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "e_famille", nullable = false, length = 45)
    private String eFamille;
    @Size(max = 45)
    @Column(name = "e_ss_famille", length = 45)
    private String eSsFamille;
    @Size(max = 45)
    @Column(name = "e_groupe", length = 45)
    private String eGroupe;
    @Size(max = 45)
    @Column(name = "e_responsable", length = 45)
    private String eResponsable;
    @Lob
    @Size(max = 65535)
    @Column(name = "e_description", length = 65535)
    private String eDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_deleted", nullable = false)
    private boolean eDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eChanged;
    @JoinColumn(name = "e_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company eCompany;

    public Equipement() {
    }

    public Equipement(Integer eId) {
        this.eId = eId;
    }

    public Equipement(Integer eId, String eEquipement, String eDesignation, String eCf, String eFamille, boolean eDeleted, Date eCreated, Date eChanged) {
        this.eId = eId;
        this.eEquipement = eEquipement;
        this.eDesignation = eDesignation;
        this.eCf = eCf;
        this.eFamille = eFamille;
        this.eDeleted = eDeleted;
        this.eCreated = eCreated;
        this.eChanged = eChanged;
    }

    public Integer getEId() {
        return eId;
    }

    public void setEId(Integer eId) {
        this.eId = eId;
    }

    public String getEEquipement() {
        return eEquipement;
    }

    public void setEEquipement(String eEquipement) {
        this.eEquipement = eEquipement;
    }

    public String getEDesignation() {
        return eDesignation;
    }

    public void setEDesignation(String eDesignation) {
        this.eDesignation = eDesignation;
    }

    public String getECf() {
        return eCf;
    }

    public void setECf(String eCf) {
        this.eCf = eCf;
    }

    public String getEFamille() {
        return eFamille;
    }

    public void setEFamille(String eFamille) {
        this.eFamille = eFamille;
    }

    public String getESsFamille() {
        return eSsFamille;
    }

    public void setESsFamille(String eSsFamille) {
        this.eSsFamille = eSsFamille;
    }

    public String getEGroupe() {
        return eGroupe;
    }

    public void setEGroupe(String eGroupe) {
        this.eGroupe = eGroupe;
    }

    public String getEResponsable() {
        return eResponsable;
    }

    public void setEResponsable(String eResponsable) {
        this.eResponsable = eResponsable;
    }

    public String getEDescription() {
        return eDescription;
    }

    public void setEDescription(String eDescription) {
        this.eDescription = eDescription;
    }

    public boolean getEDeleted() {
        return eDeleted;
    }

    public void setEDeleted(boolean eDeleted) {
        this.eDeleted = eDeleted;
    }

    public Date getECreated() {
        return eCreated;
    }

    public void setECreated(Date eCreated) {
        this.eCreated = eCreated;
    }

    public Date getEChanged() {
        return eChanged;
    }

    public void setEChanged(Date eChanged) {
        this.eChanged = eChanged;
    }

    public Company getECompany() {
        return eCompany;
    }

    public void setECompany(Company eCompany) {
        this.eCompany = eCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eId != null ? eId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipement)) {
            return false;
        }
        Equipement other = (Equipement) object;
        if ((this.eId == null && other.eId != null) || (this.eId != null && !this.eId.equals(other.eId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.Equipement[ eId=" + eId + " ]";
    }

    @XmlTransient
    public Collection<AnalysePoint> getAnalysePointCollection() {
        return analysePointCollection;
    }

    public void setAnalysePointCollection(Collection<AnalysePoint> analysePointCollection) {
        this.analysePointCollection = analysePointCollection;
    }

}
