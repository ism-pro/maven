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
import org.ism.entities.smq.PointInfos;
import org.ism.sessions.AbstractFacade;

/**
 * <h1>PointInfosFacade</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@Stateless
public class PointInfosFacade extends AbstractFacade<PointInfos> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "PointInfos.selectAllByLastChange";
    private final String FIND_BY_GROUP          = "PointInfos.findByPiGroup";           // query = "SELECT p FROM Processus p WHERE p.pProcessus = :pProcessus"),
    private final String FIND_BY_DESIGNATION    = "PointInfos.findByPiSlideshow";       //, query = "SELECT p FROM Processus p WHERE p.pDesignation = :pDesignation"),
    private final String FIND_DISTINCT_SLIDESHOW= "PointInfos.findDistinctSlideShow";
    
    
    public PointInfosFacade() {
        super(PointInfos.class);
    }

    
    public List<PointInfos> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<PointInfos> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_GROUP).setParameter("piGroup", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<PointInfos> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("piSlideshow", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
    

    public List<PointInfos> findDistinctSlideShow() {
        em.flush();
        Query q = em.createNamedQuery(FIND_DISTINCT_SLIDESHOW);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
}
