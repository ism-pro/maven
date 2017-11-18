/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.entities.admin;

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
 * Mailaddress class  
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "mailaddress", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ma_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailaddress.findAll", query = "SELECT m FROM Mailaddress m"),
    @NamedQuery(name = "Mailaddress.findByMaId", query = "SELECT m FROM Mailaddress m WHERE m.maId = :maId"),
    @NamedQuery(name = "Mailaddress.findByMaGroupe", query = "SELECT m FROM Mailaddress m WHERE m.maGroupe = :maGroupe"),
    @NamedQuery(name = "Mailaddress.findByMaAddress", query = "SELECT m FROM Mailaddress m WHERE m.maAddress = :maAddress"),
    @NamedQuery(name = "Mailaddress.findByComapnyGroupe", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maGroupe = :maGroupe"),
    @NamedQuery(name = "Mailaddress.findByCampanyAddress", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maAddress = :maAddress"),
    @NamedQuery(name = "Mailaddress.findByCompanyGroupAddress", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maGroupe = :maGroupe AND m.maAddress = :maAddress"),
    @NamedQuery(name = "Mailaddress.findByMaDeleted", query = "SELECT m FROM Mailaddress m WHERE m.maDeleted = :maDeleted"),
    @NamedQuery(name = "Mailaddress.findByMaCreated", query = "SELECT m FROM Mailaddress m WHERE m.maCreated = :maCreated"),
    @NamedQuery(name = "Mailaddress.findByMaChanged", query = "SELECT m FROM Mailaddress m WHERE m.maChanged = :maChanged"),
    @NamedQuery(name = "Mailaddress.selectAllByLastChange", query = "SELECT m FROM Mailaddress m ORDER BY m.maChanged DESC")
})
public class Mailaddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ma_id", nullable = false)
    private Integer maId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ma_groupe", nullable = false, length = 45)
    private String maGroupe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ma_address", nullable = false, length = 45)
    private String maAddress;
    @Column(name = "ma_deleted")
    private Boolean maDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ma_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date maCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ma_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date maChanged;
    @JoinColumn(name = "ma_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company maCompany;

    public Mailaddress() {
    }

    public Mailaddress(Integer maId) {
        this.maId = maId;
    }

    public Mailaddress(Integer maId, String maGroupe, String maAddress, Date maCreated, Date maChanged) {
        this.maId = maId;
        this.maGroupe = maGroupe;
        this.maAddress = maAddress;
        this.maCreated = maCreated;
        this.maChanged = maChanged;
    }

    public Integer getMaId() {
        return maId;
    }

    public void setMaId(Integer maId) {
        this.maId = maId;
    }

    public String getMaGroupe() {
        return maGroupe;
    }

    public void setMaGroupe(String maGroupe) {
        this.maGroupe = maGroupe;
    }

    public String getMaAddress() {
        return maAddress;
    }

    public void setMaAddress(String maAddress) {
        this.maAddress = maAddress;
    }

    public Boolean getMaDeleted() {
        return maDeleted;
    }

    public void setMaDeleted(Boolean maDeleted) {
        this.maDeleted = maDeleted;
    }

    public Date getMaCreated() {
        return maCreated;
    }

    public void setMaCreated(Date maCreated) {
        this.maCreated = maCreated;
    }

    public Date getMaChanged() {
        return maChanged;
    }

    public void setMaChanged(Date maChanged) {
        this.maChanged = maChanged;
    }

    public Company getMaCompany() {
        return maCompany;
    }

    public void setMaCompany(Company maCompany) {
        this.maCompany = maCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maId != null ? maId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mailaddress)) {
            return false;
        }
        Mailaddress other = (Mailaddress) object;
        if ((this.maId == null && other.maId != null) || (this.maId != null && !this.maId.equals(other.maId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.admin.Mailaddress[ maId=" + maId + " ]";
    }


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////


}
