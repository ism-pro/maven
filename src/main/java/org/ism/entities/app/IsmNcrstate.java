/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.app;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import org.ism.entities.smq.nc.NonConformiteRequest;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_ncrstate", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmNcrstate.findAll", query = "SELECT i FROM IsmNcrstate i"),
    @NamedQuery(name = "IsmNcrstate.findById", query = "SELECT i FROM IsmNcrstate i WHERE i.id = :id"),
    @NamedQuery(name = "IsmNcrstate.findByIstate", query = "SELECT i FROM IsmNcrstate i WHERE i.istate = :istate"),
    @NamedQuery(name = "IsmNcrstate.findByStatename", query = "SELECT i FROM IsmNcrstate i WHERE i.statename = :statename")})
public class IsmNcrstate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String istate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String statename;
    @OneToMany(mappedBy = "ncrState")
    private Collection<NonConformiteRequest> nonConformiteRequestCollection;

    public static final Integer CREATE_ID = 1;  // Créée
    public static final Integer WAITFORSOLUTION_ID = 2;  // En attente de solution
    public static final Integer INPROGRESS_ID = 3;  // En cours
    public static final Integer FINISH_ID = 4;  // Terminée / Clôturé
    public static final Integer CANCEL_ID = 5;  // Annulée

    public static final String CREATE = "A";  // Créée
    public static final String WAITFORSOLUTION = "B";  // En attente de solution
    public static final String INPROGRESS = "C";  // En cours
    public static final String FINISH = "D";  // Terminée / Clôturé
    public static final String CANCEL = "E";  // Annulée

    public IsmNcrstate() {
    }

    public IsmNcrstate(Integer id) {
        this.id = id;
    }

    public IsmNcrstate(Integer id, String istate, String statename) {
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
    public Collection<NonConformiteRequest> getNonConformiteRequestCollection() {
        return nonConformiteRequestCollection;
    }

    public void setNonConformiteRequestCollection(Collection<NonConformiteRequest> nonConformiteRequestCollection) {
        this.nonConformiteRequestCollection = nonConformiteRequestCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsmNcrstate)) {
            return false;
        }
        IsmNcrstate other = (IsmNcrstate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return istate + " - " + statename + " [" + id + "]";
        //return "org.ism.entities.IsmNcrstate[ id=" + id + " ]";
    }

}
