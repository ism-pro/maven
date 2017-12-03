/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.smq.nc;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.smq.nc.NonConformiteFrequency;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteFrequencyFacade extends AbstractFacade<NonConformiteFrequency> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "NonConformiteFrequency.selectAllByLastChange";     // query = "SELECT n FROM NonConformiteFrequency n ORDER BY n.ncfChanged DESC"
    private final String FIND_BY_CODE = "NonConformiteFrequency.findByNcfFrequency";        // query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfFrequency = :ncfFrequency"
    private final String FIND_BY_DESIGNATION = "NonConformiteFrequency.findByNcfDesignation";      // query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfDesignation = :ncfDesignation
    private final String FIND_BY_CODE_OF_COMPANY = "NonConformiteFrequency.findByNcfFrequencyOfCompany";        // query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfFrequency = :ncfFrequency"
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "NonConformiteFrequency.findByNcfDesignationOfCompany";      // query = "SELECT n FROM NonConformiteFrequency n WHERE n.ncfDesignation = :ncfDesignation

    
    public NonConformiteFrequencyFacade() {
        super(NonConformiteFrequency.class);
    }

    public List<NonConformiteFrequency> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteFrequency> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("ncfFrequency", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteFrequency> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("ncfDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    
    public List<NonConformiteFrequency> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("ncfFrequency", code)
                .setParameter("ncfCompany", company);
        return q.getResultList();
    }
    
    public List<NonConformiteFrequency> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("ncfDesignation", designation)
                .setParameter("ncfCompany", company);
        return q.getResultList();
    }


}
