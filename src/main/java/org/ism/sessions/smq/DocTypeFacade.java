/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.smq;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.smq.DocType;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class DocTypeFacade extends AbstractFacade<DocType> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "DocType.selectAllByLastChange";
    private final String FIND_BY_PROCESSUS = "DocType.findByDctType";            // query = "SELECT d FROM DocType d WHERE d.dctType = :dctType"
    private final String FIND_BY_DESIGNATION = "DocType.findByDctDesignation";     // query = "SELECT d FROM DocType d WHERE d.dctDesignation = :dctDesignation"
    private final String FIND_BY_PROCESSUS_OF_COMPANY = "DocType.findByDctTypeOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "DocType.findByDctDesignationOfCompany";
    
    
    public DocTypeFacade() {
        super(DocType.class);
    }

    public List<DocType> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocType> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("dctType", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocType> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("dctDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocType> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS_OF_COMPANY)
                .setParameter("dctType", code)
                .setParameter("dctCompany", company);
        return q.getResultList();
    }

    public List<DocType> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("dctDesignation", designation)
                .setParameter("dctCompany", company);
        return q.getResultList();
    }
}
