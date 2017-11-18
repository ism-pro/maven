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
import org.ism.entities.app.IsmMailtype;
import org.ism.entities.smq.Processus;

/**
 * Maillist class  
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "maillist", catalog = "ism", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ml_id"}),
    @UniqueConstraint(columnNames = {"ml_company", "ml_groupe"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maillist.findAll", query = "SELECT m FROM Maillist m"),
    @NamedQuery(name = "Maillist.findByMlId", query = "SELECT m FROM Maillist m WHERE m.mlId = :mlId"),
    @NamedQuery(name = "Maillist.findByMlEvent", query = "SELECT m FROM Maillist m WHERE m.mlEvent = :mlEvent"),
    @NamedQuery(name = "Maillist.findByMlGroupe", query = "SELECT m FROM Maillist m WHERE m.mlGroupe = :mlGroupe"),
    @NamedQuery(name = "Maillist.findByMlTos", query = "SELECT m FROM Maillist m WHERE m.mlTos = :mlTos"),
    @NamedQuery(name = "Maillist.findByMlCcs", query = "SELECT m FROM Maillist m WHERE m.mlCcs = :mlCcs"),
    @NamedQuery(name = "Maillist.findByMlCcis", query = "SELECT m FROM Maillist m WHERE m.mlCcis = :mlCcis"),
    @NamedQuery(name = "Maillist.findByMlDeleted", query = "SELECT m FROM Maillist m WHERE m.mlDeleted = :mlDeleted"),
    @NamedQuery(name = "Maillist.findByMlCreated", query = "SELECT m FROM Maillist m WHERE m.mlCreated = :mlCreated"),
    @NamedQuery(name = "Maillist.findByMlChanged", query = "SELECT m FROM Maillist m WHERE m.mlChanged = :mlChanged"),
    @NamedQuery(name = "Maillist.selectAllByLastChange", query = "SELECT m FROM Maillist m ORDER BY m.mlChanged DESC")
    })
public class Maillist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ml_id", nullable = false)
    private Integer mlId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ml_event", nullable = false, length = 45)
    private String mlEvent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ml_groupe", nullable = false, length = 45)
    private String mlGroupe;
    @Size(max = 45)
    @Column(name = "ml_tos", length = 45)
    private String mlTos;
    @Size(max = 45)
    @Column(name = "ml_ccs", length = 45)
    private String mlCcs;
    @Size(max = 45)
    @Column(name = "ml_ccis", length = 45)
    private String mlCcis;
    @Column(name = "ml_deleted")
    private Boolean mlDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ml_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date mlCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ml_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date mlChanged;
    @JoinColumn(name = "ml_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company mlCompany;
    @JoinColumn(name = "ml_processus", referencedColumnName = "p_processus", nullable = false)
    @ManyToOne(optional = false)
    private Processus mlProcessus;
    @JoinColumn(name = "ml_type", referencedColumnName = "type", nullable = false)
    @ManyToOne(optional = false)
    private IsmMailtype mlType;

    public Maillist() {
    }

    public Maillist(Integer mlId) {
        this.mlId = mlId;
    }

    public Maillist(Integer mlId, String mlEvent, String mlGroupe, Date mlCreated, Date mlChanged) {
        this.mlId = mlId;
        this.mlEvent = mlEvent;
        this.mlGroupe = mlGroupe;
        this.mlCreated = mlCreated;
        this.mlChanged = mlChanged;
    }

    public Integer getMlId() {
        return mlId;
    }

    public void setMlId(Integer mlId) {
        this.mlId = mlId;
    }

    public String getMlEvent() {
        return mlEvent;
    }

    public void setMlEvent(String mlEvent) {
        this.mlEvent = mlEvent;
    }

    public String getMlGroupe() {
        return mlGroupe;
    }

    public void setMlGroupe(String mlGroupe) {
        this.mlGroupe = mlGroupe;
    }

    public String getMlTos() {
        return mlTos;
    }

    public void setMlTos(String mlTos) {
        this.mlTos = mlTos;
    }

    public String getMlCcs() {
        return mlCcs;
    }

    public void setMlCcs(String mlCcs) {
        this.mlCcs = mlCcs;
    }

    public String getMlCcis() {
        return mlCcis;
    }

    public void setMlCcis(String mlCcis) {
        this.mlCcis = mlCcis;
    }

    public Boolean getMlDeleted() {
        return mlDeleted;
    }

    public void setMlDeleted(Boolean mlDeleted) {
        this.mlDeleted = mlDeleted;
    }

    public Date getMlCreated() {
        return mlCreated;
    }

    public void setMlCreated(Date mlCreated) {
        this.mlCreated = mlCreated;
    }

    public Date getMlChanged() {
        return mlChanged;
    }

    public void setMlChanged(Date mlChanged) {
        this.mlChanged = mlChanged;
    }

    public Company getMlCompany() {
        return mlCompany;
    }

    public void setMlCompany(Company mlCompany) {
        this.mlCompany = mlCompany;
    }

    public Processus getMlProcessus() {
        return mlProcessus;
    }

    public void setMlProcessus(Processus mlProcessus) {
        this.mlProcessus = mlProcessus;
    }

    public IsmMailtype getMlType() {
        return mlType;
    }

    public void setMlType(IsmMailtype mlType) {
        this.mlType = mlType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mlId != null ? mlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maillist)) {
            return false;
        }
        Maillist other = (Maillist) object;
        if ((this.mlId == null && other.mlId != null) || (this.mlId != null && !this.mlId.equals(other.mlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.admin.Maillist[ mlId=" + mlId + " ]";
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
