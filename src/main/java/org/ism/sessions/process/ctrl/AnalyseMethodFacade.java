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
import org.ism.entities.process.ctrl.AnalyseMethod;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseMethodFacade extends AbstractFacade<AnalyseMethod> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseMethod.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseMethod.findByAmMethod";
    private final String FIND_BY_DESIGNATION = "AnalyseMethod.findByAmDesignation";
    private final String FIND_BY_CODE_OF_COMPANY = "AnalyseMethod.findByAmMethodOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "AnalyseMethod.findByAmDesignationOfCompany";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseMethodFacade() {
        super(AnalyseMethod.class);
    }

    public List<AnalyseMethod> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseMethod> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("amMethod", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseMethod> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("amDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseMethod> findByCode(String method, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("amMethod", method)
                .setParameter("amCompany", company);
        return q.getResultList();
    }

    public List<AnalyseMethod> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("amDesignation", designation)
                .setParameter("amCompany", company);
        return q.getResultList();
    }

}
