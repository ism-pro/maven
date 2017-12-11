/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.app;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.ism.entities.smq.nc.NonConformiteActions;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_ncastate", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmNcastate.findAll", query = "SELECT i FROM IsmNcastate i"),
    @NamedQuery(name = "IsmNcastate.findById", query = "SELECT i FROM IsmNcastate i WHERE i.id = :id"),
    @NamedQuery(name = "IsmNcastate.findByIstate", query = "SELECT i FROM IsmNcastate i WHERE i.istate = :istate"),
    @NamedQuery(name = "IsmNcastate.findByStatename", query = "SELECT i FROM IsmNcastate i WHERE i.statename = :statename")})
public class IsmNcastate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "istate", nullable = false, length = 45)
    private String istate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "statename", nullable = false, length = 255)
    private String statename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncaState")
    private Collection<NonConformiteActions> nonConformiteActionsCollection;

    public static final Integer INPROGRESS_ID = 1;  // En cours
    public static final Integer REVIEW_ID = 2;  // Ajournée
    public static final Integer FINISH_ID = 3;  // Terminée / Clôturé
    public static final Integer CANCEL_ID = 4;  // Annulée

    public static final String INPROGRESS = "A";  // En cours
    public static final String REVIEW = "B";  // Ajournée
    public static final String FINISH = "C";  // Terminée
    public static final String CANCEL = "D";  // Annulée

    public IsmNcastate() {
    }

    public IsmNcastate(Integer id) {
        this.id = id;
    }

    public IsmNcastate(Integer id, String istate, String statename) {
        this.id = id;
        this.istate = istate;
        this.statename = statename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIstate() {
        return istate;
    }

    public void setIstate(String istate) {
        this.istate = istate;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IsmNcastate)) {
            return false;
        }
        IsmNcastate other = (IsmNcastate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return istate + " - " + statename + " [" + id + "]";
        //return "org.ism.entities.IsmNcastate[ id=" + id + " ]";
    }

}
