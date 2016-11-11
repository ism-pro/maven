/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process.ctrl;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.entities.hr.Staff;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseDataFacade extends AbstractFacade<AnalyseData> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseData.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseData.findByAdPointStr";      
    private final String FIND_BY_DESIGNATION = "AnalyseData.findByAdTypeStr"; 
    private final String FIND_BY_POINT = "AnalyseData.findByAdPoint";      
    private final String FIND_BY_TYPE = "AnalyseData.findByAdType"; 
    private final String FIND_BY_SAMPLER = "AnalyseData.findByAdSampler"; 
    private final String FIND_BY_VALIDATOR = "AnalyseData.findByAdValidator"; 
    private final String FIND_BY_POINT_AND_TYPE = "AnalyseData.findByAdPointType"; 
    private final String FIND_BY_POINT_AND_TYPE_SAMPLE_TIME_RANGE = "AnalyseData.findByAdPointTypeSampleTimeRange"; 
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseDataFacade() {
        super(AnalyseData.class);
    }

    public List<AnalyseData> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("adPoint", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("adType", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPoint(AnalysePoint adPoint) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT).setParameter("adPoint", adPoint);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByType(AnalyseType adType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE).setParameter("adType", adType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findBySampler(Staff adSampler) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_SAMPLER).setParameter("adSampler", adSampler);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByValidator(Staff adValidator) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_VALIDATOR).setParameter("adValidator", adValidator);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPointType(AnalysePoint adPoint, AnalyseType adType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_AND_TYPE)
                .setParameter("adPoint", adPoint)
                .setParameter("adType", adType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPointTypeSampleTimeRange(AnalysePoint point, AnalyseType type, Date from, Date to) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_AND_TYPE_SAMPLE_TIME_RANGE)
                .setParameter("adPoint", point)
                .setParameter("adType", type)
                .setParameter("from", from)
                .setParameter("to", to);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }


}
