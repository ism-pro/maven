/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.process;

import org.ism.entities.admin.Company;
import org.ism.entities.process.ctrl.AnalyseType;
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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "unite", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"u_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unite.findAll", query = "SELECT u FROM Unite u"),
    @NamedQuery(name = "Unite.findByUId", query = "SELECT u FROM Unite u WHERE u.uId = :uId"),
    @NamedQuery(name = "Unite.findByUUnite", query = "SELECT u FROM Unite u WHERE u.uUnite = :uUnite"),
    @NamedQuery(name = "Unite.findByUDesignation", query = "SELECT u FROM Unite u WHERE u.uDesignation = :uDesignation"),
    @NamedQuery(name = "Unite.findByUUniteOfCompany", query = "SELECT u FROM Unite u WHERE u.uUnite = :uUnite AND u.uCompany = :uCompany"),
    @NamedQuery(name = "Unite.findByUDesignationOfCompany", query = "SELECT u FROM Unite u WHERE u.uDesignation = :uDesignation AND u.uCompany = :uCompany"),
    @NamedQuery(name = "Unite.findByUDeleted", query = "SELECT u FROM Unite u WHERE u.uDeleted = :uDeleted"),
    @NamedQuery(name = "Unite.findByUCreated", query = "SELECT u FROM Unite u WHERE u.uCreated = :uCreated"),
    @NamedQuery(name = "Unite.findByUChanged", query = "SELECT u FROM Unite u WHERE u.uChanged = :uChanged"),
    @NamedQuery(name = "Unite.selectAllByLastChange", query = "SELECT u FROM Unite u ORDER BY u.uChanged DESC")
})
public class Unite implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atUnite")
    private Collection<AnalyseType> analyseTypeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "u_id", nullable = false)
    private Integer uId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "u_unite", nullable = false, length = 45)
    private String uUnite;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "u_designation", nullable = false, length = 255)
    private String uDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "u_deleted", nullable = false)
    private boolean uDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "u_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "u_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uChanged;
    @JoinColumn(name = "u_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company uCompany;

    public Unite() {
    }

    public Unite(Integer uId) {
        this.uId = uId;
    }

    public Unite(Integer uId, String uUnite, String uDesignation, boolean uDeleted, Date uCreated, Date uChanged) {
        this.uId = uId;
        this.uUnite = uUnite;
        this.uDesignation = uDesignation;
        this.uDeleted = uDeleted;
        this.uCreated = uCreated;
        this.uChanged = uChanged;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public String getUUnite() {
        return uUnite;
    }

    public void setUUnite(String uUnite) {
        this.uUnite = uUnite;
    }

    public String getUDesignation() {
        return uDesignation;
    }

    public void setUDesignation(String uDesignation) {
        this.uDesignation = uDesignation;
    }

    public boolean getUDeleted() {
        return uDeleted;
    }

    public void setUDeleted(boolean uDeleted) {
        this.uDeleted = uDeleted;
    }

    public Date getUCreated() {
        return uCreated;
    }

    public void setUCreated(Date uCreated) {
        this.uCreated = uCreated;
    }

    public Date getUChanged() {
        return uChanged;
    }

    public void setUChanged(Date uChanged) {
        this.uChanged = uChanged;
    }

    public Company getUCompany() {
        return uCompany;
    }

    public void setUCompany(Company uCompany) {
        this.uCompany = uCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uId != null ? uId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unite)) {
            return false;
        }
        Unite other = (Unite) object;
        if ((this.uId == null && other.uId != null) || (this.uId != null && !this.uId.equals(other.uId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.Unite[ uId=" + uId + " ]";
    }

    @XmlTransient
    public Collection<AnalyseType> getAnalyseTypeCollection() {
        return analyseTypeCollection;
    }

    public void setAnalyseTypeCollection(Collection<AnalyseType> analyseTypeCollection) {
        this.analyseTypeCollection = analyseTypeCollection;
    }

}
