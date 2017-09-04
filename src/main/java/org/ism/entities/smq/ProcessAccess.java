/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.entities.smq;

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
import org.ism.entities.admin.Company;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;

/**
 * <h1>ProcessAccess</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "process_access", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessAccess.findAll", query = "SELECT p FROM ProcessAccess p"),
    @NamedQuery(name = "ProcessAccess.findByPaId", query = "SELECT p FROM ProcessAccess p WHERE p.paId = :paId"),
    @NamedQuery(name = "ProcessAccess.findByPaIsgroup", query = "SELECT p FROM ProcessAccess p WHERE p.paIsgroup = :paIsgroup"),
    @NamedQuery(name = "ProcessAccess.findByPaDeleted", query = "SELECT p FROM ProcessAccess p WHERE p.paDeleted = :paDeleted"),
    @NamedQuery(name = "ProcessAccess.findByPaCreated", query = "SELECT p FROM ProcessAccess p WHERE p.paCreated = :paCreated"),
    @NamedQuery(name = "ProcessAccess.findByPaChanged", query = "SELECT p FROM ProcessAccess p WHERE p.paChanged = :paChanged"),
    @NamedQuery(name = "ProcessAccess.findByPaCompany", query = "SELECT p FROM ProcessAccess p WHERE p.paCompany = :paCompany"),
    @NamedQuery(name = "ProcessAccess.findByPaDocexplorer", query = "SELECT p FROM ProcessAccess p WHERE p.paDocexplorer = :paDocexplorer"),
    @NamedQuery(name = "ProcessAccess.findByPaDocLast", query = "SELECT p FROM ProcessAccess p WHERE p.paDocexplorer = :paDocexplorer ORDER BY p.paChanged DESC"),
    @NamedQuery(name = "ProcessAccess.findByPaGroupdef", query = "SELECT p FROM ProcessAccess p WHERE p.paGroupdef = :paGroupdef"),
    @NamedQuery(name = "ProcessAccess.findByPaStaff", query = "SELECT p FROM ProcessAccess p WHERE p.paStaff = :paStaff"),
    @NamedQuery(name = "ProcessAccess.selectAllByLastChange", query = "SELECT p FROM ProcessAccess p ORDER BY p.paChanged DESC"),
    @NamedQuery(name = "ProcessAccess.findByStaff", query = "SELECT p FROM ProcessAccess p WHERE p.paStaff = :paStaff"),
    @NamedQuery(name = "ProcessAccess.findByUngroupDoc", query = "SELECT p FROM ProcessAccess p WHERE p.paIsgroup=0 AND p.paDocexplorer = :paDocexplorer"),
    @NamedQuery(name = "ProcessAccess.findByGroupdef", query = "SELECT p FROM ProcessAccess p WHERE p.paGroupdef = :paGroupdef"),
    @NamedQuery(name = "ProcessAccess.findByDocAndUser", query = "SELECT p FROM ProcessAccess p WHERE p.paDocexplorer = :paDocexplorer AND p.paStaff = :paStaff"),
    @NamedQuery(name = "ProcessAccess.findByDocAndGroupdef", query = "SELECT p FROM ProcessAccess p WHERE  p.paDocexplorer = :paDocexplorer  AND p.paGroupdef = :paGroupdef")
})
public class ProcessAccess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pa_id")
    private Integer paId;
    
    @Column(name = "pa_isgroup")
    private Boolean paIsgroup;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pa_deleted")
    private boolean paDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pa_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pa_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paChanged;
    
    @JoinColumn(name = "pa_company", referencedColumnName = "c_company", nullable = false)
    @ManyToOne(optional = false)
    private Company paCompany;
    
    @JoinColumn(name = "pa_docexplorer", referencedColumnName = "dc_id", nullable = false)
    @ManyToOne(optional = false)
    private DocExplorer paDocexplorer;
    
    @JoinColumn(name = "pa_groupdef", referencedColumnName = "stgd_group_def", nullable = true)
    @ManyToOne
    private StaffGroupDef paGroupdef;
    
    @JoinColumn(name = "pa_staff", referencedColumnName = "st_staff", nullable = true)
    @ManyToOne
    private Staff paStaff;

    public ProcessAccess() {
    }

    public ProcessAccess(Integer paId) {
        this.paId = paId;
    }

    public ProcessAccess(Integer paId, boolean paDeleted, Date paCreated, Date paChanged) {
        this.paId = paId;
        this.paDeleted = paDeleted;
        this.paCreated = paCreated;
        this.paChanged = paChanged;
    }

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
    }

    public Boolean getPaIsgroup() {
        return paIsgroup;
    }

    public void setPaIsgroup(Boolean paIsgroup) {
        this.paIsgroup = paIsgroup;
    }

    public boolean getPaDeleted() {
        return paDeleted;
    }

    public void setPaDeleted(boolean paDeleted) {
        this.paDeleted = paDeleted;
    }

    public Date getPaCreated() {
        return paCreated;
    }

    public void setPaCreated(Date paCreated) {
        this.paCreated = paCreated;
    }

    public Date getPaChanged() {
        return paChanged;
    }

    public void setPaChanged(Date paChanged) {
        this.paChanged = paChanged;
    }

    public Company getPaCompany() {
        return paCompany;
    }

    public void setPaCompany(Company paCompany) {
        this.paCompany = paCompany;
    }

    public DocExplorer getPaDocexplorer() {
        return paDocexplorer;
    }

    public void setPaDocexplorer(DocExplorer paDocexplorer) {
        this.paDocexplorer = paDocexplorer;
    }

    public StaffGroupDef getPaGroupdef() {
        return paGroupdef;
    }

    public void setPaGroupdef(StaffGroupDef paGroupdef) {
        this.paGroupdef = paGroupdef;
    }

    public Staff getPaStaff() {
        return paStaff;
    }

    public void setPaStaff(Staff paStaff) {
        this.paStaff = paStaff;
    }


    
    
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paId != null ? paId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessAccess)) {
            return false;
        }
        ProcessAccess other = (ProcessAccess) object;
        if ((this.paId == null && other.paId != null) || (this.paId != null && !this.paId.equals(other.paId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ism.entities.smq.ProcessAccess[ paId=" + paId + " ]";
    }

}
