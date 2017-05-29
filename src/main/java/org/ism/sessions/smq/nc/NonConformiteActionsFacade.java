/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.smq.nc;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.smq.Processus;
import org.ism.entities.smq.nc.NonConformiteActions;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteActionsFacade extends AbstractFacade<NonConformiteActions> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "NonConformiteActions.selectAllByLastChange";
    private final String FIND_BY_CODE = "NonConformiteActions.findByCode";       // query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus"),
    private final String FIND_BY_DESIGNATION = "NonConformiteActions.findByDesignation";     //, query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation"),
    private final String FIND_BY_NCLAST = "NonConformiteActions.findAllByNCLastChange";   // query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc BY n.ncaChanged DESC"
    private final String FIND_DESC_BY_DESC = "NonConformiteActions.findDescByNC";            // query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc  ORDER BY n.ncaId DESC"
    private final String FIND_BY_NCLASTID = "NonConformiteActions.findAllByNCLastId";

    private final String ITEMS_CREATE_IN_RANGE = "NonConformiteActions.itemsCreateInRange";
    private final String ITEMS_CREATE_IN_RANGE_BY_PROCESSUS = "NonConformiteActions.itemsCreateInRangeByProcessus";

    public NonConformiteActionsFacade() {
        super(NonConformiteActions.class);
    }

    public List<NonConformiteActions> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("pProcessus", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("pDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findAllByNCLast(NonConformiteRequest nc) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_NCLAST).setParameter("ncaNc", nc);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findAllByNCLastID(NonConformiteRequest nc) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_NCLASTID).setParameter("ncaNc", nc);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findDescByNC(NonConformiteRequest nc) {
        em.flush();
        Query q = em.createNamedQuery(FIND_DESC_BY_DESC).setParameter("ncaNc", nc);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param fromInclude
     * @param toExclude
     * @return
     */
    public List<NonConformiteActions> itemsCreateInRange(Date fromInclude, Date toExclude) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_CREATE_IN_RANGE).setParameter("ncaFrom", fromInclude).setParameter("ncaTo", toExclude);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> itemsCreateInRange(Date fromInclude, Date toExclude, Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_CREATE_IN_RANGE_BY_PROCESSUS)
                .setParameter("ncaFrom", fromInclude).setParameter("ncaTo", toExclude)
                .setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
}
