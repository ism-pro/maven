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
import org.ism.entities.smq.nc.NonConformiteNature;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteNatureFacade extends AbstractFacade<NonConformiteNature> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "NonConformiteNature.selectAllByLastChange";    // query = "SELECT n FROM NonConformiteNature n ORDER BY n.ncnChanged DESC"
    private final String FIND_BY_CODE = "NonConformiteNature.findByNcnNature";          // query = "SELECT n FROM NonConformiteNature n WHERE n.ncnNature = :ncnNature"
    private final String FIND_BY_DESIGNATION = "NonConformiteNature.findByNcnDesignation";     // query = "SELECT n FROM NonConformiteNature n WHERE n.ncnDesignation = :ncnDesignation"
    private final String FIND_BY_CODE_OF_COMPANY = "NonConformiteNature.findByNcnNatureOfCompany";          // query = "SELECT n FROM NonConformiteNature n WHERE n.ncnNature = :ncnNature"
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "NonConformiteNature.findByNcnDesignationOfCompany";     // query = "SELECT n FROM NonConformiteNature n WHERE n.ncnDesignation = :ncnDesignation"

    public NonConformiteNatureFacade() {
        super(NonConformiteNature.class);
    }

    public List<NonConformiteNature> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteNature> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("ncnNature", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteNature> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("ncnDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteNature> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("ncnNature", code)
                .setParameter("ncnCompany", company);
        return q.getResultList();
    }

    public List<NonConformiteNature> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("ncnDesignation", designation)
                .setParameter("ncnCompany", company);
        return q.getResultList();
    }

}
