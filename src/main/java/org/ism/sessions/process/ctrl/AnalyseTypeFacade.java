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
import org.ism.entities.process.ctrl.AnalyseMethod;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.entities.process.Unite;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseTypeFacade extends AbstractFacade<AnalyseType> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseType.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseType.findByAtType";
    private final String FIND_BY_DESIGNATION = "AnalyseType.findByAtDesignation";
    private final String FIND_BY_CODE_OF_COMPANY = "AnalyseType.findByAtTypeOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "AnalyseType.findByAtDesignationOfCompany";
    private final String FIND_BY_UNITE = "AnalyseType.findByAtUnite";
    private final String FIND_BY_CATEGORY = "AnalyseType.findByAtCategory";
    private final String FIND_BY_METHOD = "AnalyseType.findByAtMethod";
    private final String FIND_BY_TYPE_UNITE = "AnalyseType.findByAtTypeUnite";
    private final String FIND_BY_TYPE_CATEGORY = "AnalyseType.findByAtTypeCategory";
    private final String FIND_BY_TYPE_METHOD = "AnalyseType.findByAtTypeMethod";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseTypeFacade() {
        super(AnalyseType.class);
    }

    public List<AnalyseType> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("atType", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("atDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByUnite(Unite atUnite) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_UNITE).setParameter("atUnite", atUnite);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByCategory(AnalyseCategory atCategory) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CATEGORY).setParameter("atCategory", atCategory);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByMethod(AnalyseMethod atMethod) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_METHOD).setParameter("atMethod", atMethod);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByTypeUnite(AnalyseType atType, Unite atUnite) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE_UNITE)
                .setParameter("atType", atType)
                .setParameter("atUnite", atUnite);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByTypeCategory(AnalyseType atType, AnalyseCategory atCategory) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE_CATEGORY)
                .setParameter("atType", atType)
                .setParameter("atCategory", atCategory);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByTypeMethod(AnalyseType atType, AnalyseMethod atMethod) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE_METHOD)
                .setParameter("atType", atType)
                .setParameter("atMethod", atMethod);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseType> findByCode(String type, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("atType", type)
                .setParameter("atCompany", company);
        return q.getResultList();
    }

    public List<AnalyseType> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("atDesignation", designation)
                .setParameter("atCompany", company);
        return q.getResultList();
    }
}
