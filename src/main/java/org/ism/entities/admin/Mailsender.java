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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Mailsender</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@Entity
@Table(catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailsender.findAll", query = "SELECT m FROM Mailsender m"),
    @NamedQuery(name = "Mailsender.findByMsId", query = "SELECT m FROM Mailsender m WHERE m.msId = :msId"),
    @NamedQuery(name = "Mailsender.findByMsAddress", query = "SELECT m FROM Mailsender m WHERE m.msAddress = :msAddress"),
    @NamedQuery(name = "Mailsender.findByMsSmtpsrv", query = "SELECT m FROM Mailsender m WHERE m.msSmtpsrv = :msSmtpsrv"),
    @NamedQuery(name = "Mailsender.findByMsPort", query = "SELECT m FROM Mailsender m WHERE m.msPort = :msPort"),
    @NamedQuery(name = "Mailsender.findByMsSsl", query = "SELECT m FROM Mailsender m WHERE m.msSsl = :msSsl"),
    @NamedQuery(name = "Mailsender.findByMsrequiresAuth", query = "SELECT m FROM Mailsender m WHERE m.msrequiresAuth = :msrequiresAuth"),
    @NamedQuery(name = "Mailsender.findByMsUsername", query = "SELECT m FROM Mailsender m WHERE m.msUsername = :msUsername"),
    @NamedQuery(name = "Mailsender.findByMsPassword", query = "SELECT m FROM Mailsender m WHERE m.msPassword = :msPassword"),
    @NamedQuery(name = "Mailsender.findByAmEnabled", query = "SELECT m FROM Mailsender m WHERE m.amEnabled = :amEnabled"),
    @NamedQuery(name = "Mailsender.findByMsDeleted", query = "SELECT m FROM Mailsender m WHERE m.msDeleted = :msDeleted"),
    @NamedQuery(name = "Mailsender.findByMsCreated", query = "SELECT m FROM Mailsender m WHERE m.msCreated = :msCreated"),
    @NamedQuery(name = "Mailsender.findByMsChanged", query = "SELECT m FROM Mailsender m WHERE m.msChanged = :msChanged"),
    @NamedQuery(name = "Mailsender.selectAllByLastChange", query = "SELECT m FROM Mailsender m ORDER BY m.msChanged DESC"),
    @NamedQuery(name = "Mailsender.findByCompany", query = "SELECT m FROM Mailsender m WHERE m.msCompany = :msCompany")
})
public class Mailsender implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ms_id", nullable = false)
    private Integer msId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ms_address", nullable = false, length = 45)
    private String msAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ms_smtpsrv", nullable = false, length = 45)
    private String msSmtpsrv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ms_port", nullable = false)
    private int msPort;
    @Column(name = "ms_ssl")
    private Boolean msSsl;
    @Column(name = "ms_requiresAuth")
    private Boolean msrequiresAuth;
    @Size(max = 45)
    @Column(name = "ms_username", length = 45)
    private String msUsername;
    @Size(max = 256)
    @Column(name = "ms_password", length = 256)
    private String msPassword;
    @Column(name = "am_enabled")
    private Boolean amEnabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ms_deleted", nullable = false)
    private boolean msDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ms_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date msCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ms_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date msChanged;
    @JoinColumn(name = "ms_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company msCompany;

    public Mailsender() {
    }

    public Mailsender(Integer msId) {
        this.msId = msId;
    }

    public Mailsender(Integer msId, String msAddress, String msSmtpsrv, int msPort, boolean msDeleted, Date msCreated, Date msChanged) {
        this.msId = msId;
        this.msAddress = msAddress;
        this.msSmtpsrv = msSmtpsrv;
        this.msPort = msPort;
        this.msDeleted = msDeleted;
        this.msCreated = msCreated;
        this.msChanged = msChanged;
    }

    public Integer getMsId() {
        return msId;
    }

    public void setMsId(Integer msId) {
        this.msId = msId;
    }

    public String getMsAddress() {
        return msAddress;
    }

    public void setMsAddress(String msAddress) {
        this.msAddress = msAddress;
    }

    public String getMsSmtpsrv() {
        return msSmtpsrv;
    }

    public void setMsSmtpsrv(String msSmtpsrv) {
        this.msSmtpsrv = msSmtpsrv;
    }

    public int getMsPort() {
        return msPort;
    }

    public void setMsPort(int msPort) {
        this.msPort = msPort;
    }

    public Boolean getMsSsl() {
        return msSsl;
    }

    public void setMsSsl(Boolean msSsl) {
        this.msSsl = msSsl;
    }

    public Boolean getMsrequiresAuth() {
        return msrequiresAuth;
    }

    public void setMsrequiresAuth(Boolean msrequiresAuth) {
        this.msrequiresAuth = msrequiresAuth;
    }

    public String getMsUsername() {
        return msUsername;
    }

    public void setMsUsername(String msUsername) {
        this.msUsername = msUsername;
    }

    public String getMsPassword() {
        return msPassword;
    }

    public void setMsPassword(String msPassword) {
        this.msPassword = msPassword;
    }

    public Boolean getAmEnabled() {
        return amEnabled;
    }

    public void setAmEnabled(Boolean amEnabled) {
        this.amEnabled = amEnabled;
    }

    public boolean getMsDeleted() {
        return msDeleted;
    }

    public void setMsDeleted(boolean msDeleted) {
        this.msDeleted = msDeleted;
    }

    public Date getMsCreated() {
        return msCreated;
    }

    public void setMsCreated(Date msCreated) {
        this.msCreated = msCreated;
    }

    public Date getMsChanged() {
        return msChanged;
    }

    public void setMsChanged(Date msChanged) {
        this.msChanged = msChanged;
    }

    public Company getMsCompany() {
        return msCompany;
    }

    public void setMsCompany(Company msCompany) {
        this.msCompany = msCompany;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msId != null ? msId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mailsender)) {
            return false;
        }
        Mailsender other = (Mailsender) object;
        if ((this.msId == null && other.msId != null) || (this.msId != null && !this.msId.equals(other.msId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.admin.Mailsender[ msId=" + msId + " ]";
    }

}
