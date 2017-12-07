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
import org.ism.entities.process.ctrl.AnalyseCategory;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseCategoryFacade extends AbstractFacade<AnalyseCategory> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseCategory.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseCategory.findByAcMethod";
    private final String FIND_BY_DESIGNATION = "AnalyseCategory.findByAcDesignation";
    private final String FIND_BY_CODE_OF_COMPANY = "AnalyseCategory.findByAcCategoryOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "AnalyseCategory.findByAcDesignationOfCompany";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseCategoryFacade() {
        super(AnalyseCategory.class);
    }

    public List<AnalyseCategory> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseCategory> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("acCategory", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseCategory> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("acDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseCategory> findByCode(String category, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("acCategory", category)
                .setParameter("acCompany", company);
        return q.getResultList();
    }

    public List<AnalyseCategory> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("acDesignation", designation)
                .setParameter("acCompany", company);
        return q.getResultList();
    }

}
