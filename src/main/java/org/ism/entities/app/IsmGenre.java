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
import org.ism.entities.hr.Staff;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_genre", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmGenre.findAll", query = "SELECT i FROM IsmGenre i"),
    @NamedQuery(name = "IsmGenre.findById", query = "SELECT i FROM IsmGenre i WHERE i.id = :id"),
    @NamedQuery(name = "IsmGenre.findByGenre", query = "SELECT i FROM IsmGenre i WHERE i.genre = :genre"),
    @NamedQuery(name = "IsmGenre.findByGenrename", query = "SELECT i FROM IsmGenre i WHERE i.genrename = :genrename")})
public class IsmGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "genre", nullable = false, length = 10)
    private String genre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String genrename;
    @OneToMany(mappedBy = "stGenre")
    private Collection<Staff> staffCollection;
    private static final long serialVersionUID = 1L;

    public IsmGenre() {
    }

    public IsmGenre(Integer id) {
        this.id = id;
    }

    public IsmGenre(Integer id, String genre, String genrename) {
        this.id = id;
        this.genre = genre;
        this.genrename = genrename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }

    @XmlTransient
    public Collection<Staff> getStaffCollection() {
        return staffCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsmGenre)) {
            return false;
        }
        IsmGenre other = (IsmGenre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return genre + " - " + genrename + " [" + id + "]";
        //return "org.ism.entities.IsmGenre[ id=" + id + " ]";
    }
}
