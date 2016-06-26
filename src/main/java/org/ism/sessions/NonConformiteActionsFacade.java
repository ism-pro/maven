/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.NonConformiteActions;
import org.ism.entities.NonConformiteRequest;

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

    private final String SELECTALLBYLASTCHANGED           = "NonConformiteActions.selectAllByLastChange";
    private final String FIND_BY_CODE                     = "NonConformiteActions.findByCode";       // query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus"),
    private final String FIND_BY_DESIGNATION              = "NonConformiteActions.findByDesignation";     //, query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation"),
    private final String FIND_BY_NCLAST                   = "NonConformiteActions.findAllByNCLastChange"; // query = "SELECT n FROM NonConformiteActions n WHERE n.ncaNc=:ncaNc BY n.ncaChanged DESC"

    public NonConformiteActionsFacade() {
        super(NonConformiteActions.class);
    }

    public List<NonConformiteActions> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    
    public List<NonConformiteActions> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("pProcessus", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    public List<NonConformiteActions> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("pDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteActions> findAllByNCLast(NonConformiteRequest nc) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_NCLAST).setParameter("ncaNc", nc);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }

}
