/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.admin;

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
@Table(name = "entreprise", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"e_entreprise"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entreprise.findAll", query = "SELECT e FROM Entreprise e"),
    @NamedQuery(name = "Entreprise.findByEId", query = "SELECT e FROM Entreprise e WHERE e.eId = :eId"),
    @NamedQuery(name = "Entreprise.findByEEntreprise", query = "SELECT e FROM Entreprise e WHERE e.eEntreprise = :eEntreprise"),
    @NamedQuery(name = "Entreprise.findByEDesignation", query = "SELECT e FROM Entreprise e WHERE e.eDesignation = :eDesignation"),
    @NamedQuery(name = "Entreprise.findByEBuilded", query = "SELECT e FROM Entreprise e WHERE e.eBuilded = :eBuilded"),
    @NamedQuery(name = "Entreprise.findByEMain", query = "SELECT e FROM Entreprise e WHERE e.eMain = :eMain"),
    @NamedQuery(name = "Entreprise.findByEActivated", query = "SELECT e FROM Entreprise e WHERE e.eActivated = :eActivated"),
    @NamedQuery(name = "Entreprise.findByEDeleted", query = "SELECT e FROM Entreprise e WHERE e.eDeleted = :eDeleted"),
    @NamedQuery(name = "Entreprise.findByECreated", query = "SELECT e FROM Entreprise e WHERE e.eCreated = :eCreated"),
    @NamedQuery(name = "Entreprise.findByEChanged", query = "SELECT e FROM Entreprise e WHERE e.eChanged = :eChanged")})
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_id", nullable = false)
    private Integer eId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "e_entreprise", nullable = false, length = 45)
    private String eEntreprise;
    @Size(max = 255)
    @Column(name = "e_designation", length = 255)
    private String eDesignation;
    @Column(name = "e_builded")
    private Integer eBuilded;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_main", nullable = false)
    private boolean eMain;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_activated", nullable = false)
    private boolean eActivated;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cEntreprise")
    private Collection<Company> companyCollection;

    public Entreprise() {
    }

    public Entreprise(Integer eId) {
        this.eId = eId;
    }

    public Entreprise(Integer eId, String eEntreprise, boolean eMain, boolean eActivated, boolean eDeleted, Date eCreated, Date eChanged) {
        this.eId = eId;
        this.eEntreprise = eEntreprise;
        this.eMain = eMain;
        this.eActivated = eActivated;
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

    public String getEEntreprise() {
        return eEntreprise;
    }

    public void setEEntreprise(String eEntreprise) {
        this.eEntreprise = eEntreprise;
    }

    public String getEDesignation() {
        return eDesignation;
    }

    public void setEDesignation(String eDesignation) {
        this.eDesignation = eDesignation;
    }

    public Integer getEBuilded() {
        return eBuilded;
    }

    public void setEBuilded(Integer eBuilded) {
        this.eBuilded = eBuilded;
    }

    public boolean getEMain() {
        return eMain;
    }

    public void setEMain(boolean eMain) {
        this.eMain = eMain;
    }

    public boolean getEActivated() {
        return eActivated;
    }

    public void setEActivated(boolean eActivated) {
        this.eActivated = eActivated;
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

    @XmlTransient
    public Collection<Company> getCompanyCollection() {
        return companyCollection;
    }

    public void setCompanyCollection(Collection<Company> companyCollection) {
        this.companyCollection = companyCollection;
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
        if (!(object instanceof Entreprise)) {
            return false;
        }
        Entreprise other = (Entreprise) object;
        if ((this.eId == null && other.eId != null) || (this.eId != null && !this.eId.equals(other.eId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.Entreprise[ eId=" + eId + " ]";
    }

}
