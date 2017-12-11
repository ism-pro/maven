/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.entities.app;

import org.ism.entities.process.ctrl.AnalyseNotify;
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

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "ism_analyse_alarm", catalog = "ism", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsmAnalyseAlarm.findAll", query = "SELECT i FROM IsmAnalyseAlarm i"),
    @NamedQuery(name = "IsmAnalyseAlarm.findById", query = "SELECT i FROM IsmAnalyseAlarm i WHERE i.id = :id"),
    @NamedQuery(name = "IsmAnalyseAlarm.findByAlarm", query = "SELECT i FROM IsmAnalyseAlarm i WHERE i.alarm = :alarm"),
    @NamedQuery(name = "IsmAnalyseAlarm.findByAlarmname", query = "SELECT i FROM IsmAnalyseAlarm i WHERE i.alarmname = :alarmname"),
    @NamedQuery(name = "IsmAnalyseAlarm.selectAllByLastChange", query = "SELECT i FROM IsmAnalyseAlarm i ORDER BY i.id DESC")
})
public class IsmAnalyseAlarm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "alarm", nullable = false, length = 45)
    private String alarm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "alarmname", nullable = false, length = 255)
    private String alarmname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anAlarm")
    private Collection<AnalyseNotify> analyseNotifyCollection;

    public IsmAnalyseAlarm() {
    }

    public IsmAnalyseAlarm(Integer id) {
        this.id = id;
    }

    public IsmAnalyseAlarm(Integer id, String alarm, String alarmname) {
        this.id = id;
        this.alarm = alarm;
        this.alarmname = alarmname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getAlarmname() {
        return alarmname;
    }

    public void setAlarmname(String alarmname) {
        this.alarmname = alarmname;
    }

    @XmlTransient
    public Collection<AnalyseNotify> getAnalyseNotifyCollection() {
        return analyseNotifyCollection;
    }

    public void setAnalyseNotifyCollection(Collection<AnalyseNotify> analyseNotifyCollection) {
        this.analyseNotifyCollection = analyseNotifyCollection;
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
        if (!(object instanceof IsmAnalyseAlarm)) {
            return false;
        }
        IsmAnalyseAlarm other = (IsmAnalyseAlarm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return alarm + " - " + alarmname + " [" + id + "]";
        //return "org.ism.entities.IsmAnalyseAlarm[ id=" + id + " ]";
    }

}
