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
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.Equipement;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalysePointFacade extends AbstractFacade<AnalysePoint> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalysePoint.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalysePoint.findByApPoint";
    private final String FIND_BY_DESIGNATION = "AnalysePoint.findByApDesignation";
    private final String FIND_BY_CODE_OF_COMPANY = "AnalysePoint.findByApPointOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "AnalysePoint.findByApDesignationOfCompany";
    private final String FIND_BY_EQUIPEMENT = "AnalysePoint.findByApEquipement";
    private final String FIND_BY_POINT_EQUIPEMENT = "AnalysePoint.findByApPointEquipement";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalysePointFacade() {
        super(AnalysePoint.class);
    }

    public List<AnalysePoint> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalysePoint> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("apPoint", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalysePoint> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("apDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalysePoint> findByEquipement(Equipement apEquipement) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_EQUIPEMENT).setParameter("apEquipement", apEquipement);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalysePoint> findByPointEquipement(AnalysePoint apPoint, Equipement apEquipement) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_EQUIPEMENT)
                .setParameter("apPoint", apPoint)
                .setParameter("apEquipement", apEquipement);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalysePoint> findByCode(String point, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("apPoint", point)
                .setParameter("apCompany", company);
        return q.getResultList();
    }

    public List<AnalysePoint> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("apDesignation", designation)
                .setParameter("apCompany", company);
        return q.getResultList();
    }

}
