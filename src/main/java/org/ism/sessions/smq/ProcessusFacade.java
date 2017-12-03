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
import org.ism.entities.smq.Processus;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class ProcessusFacade extends AbstractFacade<Processus> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    private final String APPROUVEDITEMS = "Processus.findAllApprouvedItems";
    private final String APPROUVEDITEMS_ASSTRING = "Processus.findAllApprouvedItemsAsString";
    private final String SELECTALLBYLASTCHANGED = "Processus.selectAllByLastChange";
    private final String FIND_BY_PROCESSUS = "Processus.findByPProcessus";       // query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus"),
    private final String FIND_BY_DESIGNATION = "Processus.findByPDesignation";     //, query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation"),
    private final String FIND_BY_PROCESSUS_OF_COMPANY = "Processus.findByPProcessusOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "Processus.findByPDesignationOfCompany";

    public ProcessusFacade() {
        super(Processus.class);
    }

    public List<Processus> findApprouvedItems() {
        em.flush();
        Query q = em.createNamedQuery(APPROUVEDITEMS);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<String> findApprouvedItemsAsString() {
        em.flush();
        Query q = em.createNamedQuery(APPROUVEDITEMS_ASSTRING);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Processus> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Processus> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("pProcessus", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Processus> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("pDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Processus> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS_OF_COMPANY)
                .setParameter("pProcessus", code)
                .setParameter("pCompany", company);
        return q.getResultList();
    }

    public List<Processus> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("pDesignation", designation)
                .setParameter("pCompany", company);
        return q.getResultList();
    }

}
