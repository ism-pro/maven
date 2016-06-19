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
import org.ism.entities.DocExplorer;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class DocExplorerFacade extends AbstractFacade<DocExplorer> {
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED         = "DocExplorer.selectAllByLastChange";  // query = "SELECT d FROM DocExplorer d ORDER BY d.dcChanged DESC"
    private final String FIND_BY_CODE                   = "DocExplorer.findByDcVersion";             // query = "SELECT d FROM DocExplorer d WHERE WHERE d.dcVersion = :dcVersion"
    private final String FIND_BY_DESIGNATION            = "DocExplorer.findByDcDesignation";    // query = "SELECT d FROM DocExplorer d WHERE d.dcDesignation = :dcDesignation"
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocExplorerFacade() {
        super(DocExplorer.class);
    }

    public List<DocExplorer> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    
    public List<DocExplorer> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("dcVersion", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    public List<DocExplorer> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("dcDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }

}
