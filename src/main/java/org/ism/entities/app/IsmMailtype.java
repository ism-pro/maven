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
import org.ism.entities.admin.Maillist;

/**
 * IsmMailtype class  
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_mailtype", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmMailtype.findAll", query = "SELECT i FROM IsmMailtype i"),
    @NamedQuery(name = "IsmMailtype.findById", query = "SELECT i FROM IsmMailtype i WHERE i.id = :id"),
    @NamedQuery(name = "IsmMailtype.findByType", query = "SELECT i FROM IsmMailtype i WHERE i.type = :type"),
    @NamedQuery(name = "IsmMailtype.findByDesignation", query = "SELECT i FROM IsmMailtype i WHERE i.designation = :designation")
    })
public class IsmMailtype implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String designation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mlType")
    private Collection<Maillist> maillistCollection;

    

    public IsmMailtype() {
    }

    public IsmMailtype(Integer id) {
        this.id = id;
    }

    public IsmMailtype(Integer id, String type, String designation) {
        this.id = id;
        this.type = type;
        this.designation = designation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
        if (!(object instanceof IsmMailtype)) {
            return false;
        }
        IsmMailtype other = (IsmMailtype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return type + " - " + designation + " [" + id + "]";
        //return "org.ism.entities.app.IsmMailtype[ id=" + id + " ]";
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
