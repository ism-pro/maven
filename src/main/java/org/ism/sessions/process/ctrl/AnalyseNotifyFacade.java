/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process.ctrl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalyseNotify;
import org.ism.entities.app.IsmAnalyseAlarm;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseNotifyFacade extends AbstractFacade<AnalyseNotify> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseNotify.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseNotify.findByAnAllowedIdInt";
    private final String FIND_BY_DESIGNATION = "AnalyseNotify.findByAnAlarmStr";
    private final String FIND_BY_ALLOWEDID = "AnalyseNotify.findByAnAllowedId";
    private final String FIND_BY_ALARM = "AnalyseNotify.findByAnAlarm";
    private final String FIND_BY_GROUP = "AnalyseNotify.findByAnGroup";
    private final String FIND_BY_STAFF = "AnalyseNotify.findByAnSTAFF";
    private final String FIND_BY_ALARM_AND_ALLOWEDID = "AnalyseNotify.findByAnAlarmAllowedId";
    private final String FIND_BY_ALARM_AND_GROUP = "AnalyseNotify.findByAnAlarmGroup";
    private final String FIND_BY_ALARM_AND_STAFF = "AnalyseNotify.findByAnAlarmStaff";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseNotifyFacade() {
        super(AnalyseNotify.class);
    }

    public List<AnalyseNotify> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("anallowedId", Integer.valueOf(code));
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("anAlarm", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByAllowed(AnalyseAllowed anallowedId) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ALLOWEDID).setParameter("anallowedId", anallowedId);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByAnalyseAlarm(IsmAnalyseAlarm anAlarm) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ALARM).setParameter("anAlarm", anAlarm);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByStaffGroupDef(StaffGroupDef anGroup) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_GROUP).setParameter("anGroup", anGroup);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByStaff(Staff anStaff) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_STAFF).setParameter("anStaff", anStaff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByAlarmAllowedId(IsmAnalyseAlarm anAlarm, AnalyseAllowed anallowedId) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ALARM_AND_ALLOWEDID)
                .setParameter("anAlarm", anAlarm)
                .setParameter("anallowedId", anallowedId);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByAlarmStaffGroupDef(IsmAnalyseAlarm anAlarm, StaffGroupDef anGroup) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ALARM_AND_GROUP)
                .setParameter("anAlarm", anAlarm)
                .setParameter("anGroup", anGroup);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseNotify> findByAlarmStaff(IsmAnalyseAlarm anAlarm, Staff anStaff) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ALARM_AND_STAFF)
                .setParameter("anAlarm", anAlarm)
                .setParameter("apEquipement", anStaff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

}
