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
import org.ism.entities.admin.Company;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseAllowedFacade extends AbstractFacade<AnalyseAllowed> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseAllowed.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseAllowed.findByAaPointStr";
    private final String FIND_BY_DESIGNATION = "AnalyseAllowed.findByAaTypeStr";
    private final String FIND_BY_POINT = "AnalyseAllowed.findByAaPoint";
    private final String FIND_BY_TYPE = "AnalyseAllowed.findByAaType";
    private final String FIND_BY_POINT_TYPE = "AnalyseAllowed.findByAaPointType";
    private final String FIND_BY_POINT_TYPE_OF_COMPANY = "AnalyseAllowed.findByAaPointTypeOfCompany";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseAllowedFacade() {
        super(AnalyseAllowed.class);
    }

    public List<AnalyseAllowed> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("aaPoint", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByPoint(AnalysePoint aPoint) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT).setParameter("aaPoint", aPoint);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("aaType", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByType(AnalyseType aType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE).setParameter("aaType", aType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByPointType(AnalysePoint aPoint, AnalyseType aType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_TYPE)
                .setParameter("aaPoint", aPoint)
                .setParameter("aaType", aType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseAllowed> findByPointType(AnalysePoint aaPoint, AnalyseType aaType, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_TYPE_OF_COMPANY)
                .setParameter("aaPoint", aaPoint)
                .setParameter("aaType", aaType)
                .setParameter("aaCompany", company);
        return q.getResultList();
    }
}
