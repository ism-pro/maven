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
import org.ism.entities.hr.StaffGroupDefRole;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_role", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmRole.findAll", query = "SELECT i FROM IsmRole i"),
    @NamedQuery(name = "IsmRole.findById", query = "SELECT i FROM IsmRole i WHERE i.id = :id"),
    @NamedQuery(name = "IsmRole.findByRole", query = "SELECT i FROM IsmRole i WHERE i.role = :role"),
    @NamedQuery(name = "IsmRole.findByRolename", query = "SELECT i FROM IsmRole i WHERE i.rolename = :rolename")})
public class IsmRole implements Serializable {

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
    private String role;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String rolename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgdrRole")
    private Collection<StaffGroupDefRole> staffGroupDefRoleCollection;

    public IsmRole() {
    }

    public IsmRole(Integer id) {
        this.id = id;
    }

    public IsmRole(Integer id, String role, String rolename) {
        this.id = id;
        this.role = role;
        this.rolename = rolename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @XmlTransient
    public Collection<StaffGroupDefRole> getStaffGroupDefRoleCollection() {
        return staffGroupDefRoleCollection;
    }

    public void setStaffGroupDefRoleCollection(Collection<StaffGroupDefRole> staffGroupDefRoleCollection) {
        this.staffGroupDefRoleCollection = staffGroupDefRoleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsmRole)) {
            return false;
        }
        IsmRole other = (IsmRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "org.ism.entities.IsmRole[ id=" + id + " ]";
        return role + " - " + rolename + " [" + id + "]";
    }

}
