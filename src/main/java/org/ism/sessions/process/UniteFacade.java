/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.process.Unite;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class UniteFacade extends AbstractFacade<Unite> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "Unite.selectAllByLastChange";
    private final String FIND_BY_CODE = "Unite.findByUUnite";
    private final String FIND_BY_DESIGNATION = "Unite.findByUDesignation";
    private final String FIND_BY_CODE_OF_COMPANY = "Unite.findByUUniteOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "Unite.findByUDesignationOfCompany";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UniteFacade() {
        super(Unite.class);
    }

    public List<Unite> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Unite> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("uUnite", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Unite> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("uDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Unite> findByCode(String unite, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("uUnite", unite)
                .setParameter("uCompany", company);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        return q.getResultList();
    }

    public List<Unite> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("uDesignation", designation)
                .setParameter("uCompany", company);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        return q.getResultList();
    }

}
