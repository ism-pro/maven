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
import org.ism.entities.smq.nc.NonConformiteGravity;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteGravityFacade extends AbstractFacade<NonConformiteGravity> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "NonConformiteGravity.selectAllByLastChange";       // query = "SELECT n FROM NonConformiteGravity n ORDER BY n.ncgChanged DESC"
    private final String FIND_BY_CODE = "NonConformiteGravity.findByNcgGravity";            // query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgGravity = :ncgGravity"
    private final String FIND_BY_DESIGNATION = "NonConformiteGravity.findByNcgDesignation";        // query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgDesignation = :ncgDesignation"
    private final String FIND_BY_CODE_OF_COMPANY = "NonConformiteGravity.findByNcgGravityOfCompany";            // query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgGravity = :ncgGravity"
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "NonConformiteGravity.findByNcgDesignationOfCompany";        // query = "SELECT n FROM NonConformiteGravity n WHERE n.ncgDesignation = :ncgDesignation"

    public NonConformiteGravityFacade() {
        super(NonConformiteGravity.class);
    }

    public List<NonConformiteGravity> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteGravity> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("ncgGravity", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteGravity> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("ncgDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteGravity> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("ncgGravity", code)
                .setParameter("ncgCompany", company);
        return q.getResultList();
    }

    public List<NonConformiteGravity> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("ncgDesignation", designation)
                .setParameter("ncgCompany", company);
        return q.getResultList();
    }

}
