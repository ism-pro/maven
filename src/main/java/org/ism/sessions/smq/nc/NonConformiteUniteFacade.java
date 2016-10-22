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
import org.ism.entities.smq.nc.NonConformiteUnite;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteUniteFacade extends AbstractFacade<NonConformiteUnite> {
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED           = "NonConformiteUnite.selectAllByLastChange";     // query = "SELECT n FROM NonConformiteUnite n ORDER BY n.ncuChanged DESC"
    private final String FIND_BY_PROCESSUS                = "NonConformiteUnite.findByNcuUnite";            // query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuUnite = :ncuUnite"
    private final String FIND_BY_DESIGNATION              = "NonConformiteUnite.findByNcuDesignation";      // query = "SELECT n FROM NonConformiteUnite n WHERE n.ncuDesignation = :ncuDesignation"
    
    
    public NonConformiteUniteFacade() {
        super(NonConformiteUnite.class);
    }

    public List<NonConformiteUnite> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    
    public List<NonConformiteUnite> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("ncuUnite", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    public List<NonConformiteUnite> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("ncuDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }

}
