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
import org.ism.entities.DocType;

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
    
    private final String DOCTYPE_SELECTALLBYLASTCHANGED           = "DocType.selectAllByLastChange";
    private final String DOCTYPE_FIND_BY_PROCESSUS                = "DocType.findByDctType";            // query = "SELECT d FROM DocType d WHERE d.dctType = :dctType"
    private final String DOCTYPE_FIND_BY_DESIGNATION              = "DocType.findByDctDesignation";     // query = "SELECT d FROM DocType d WHERE d.dctDesignation = :dctDesignation"
    
    
    

    public DocTypeFacade() {
        super(DocType.class);
    }

    public List<DocType> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(DOCTYPE_SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    
    public List<DocType> findByCode(String docType) {
        em.flush();
        Query q = em.createNamedQuery(DOCTYPE_FIND_BY_PROCESSUS).setParameter("dctType", docType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    
    public List<DocType> findByDesignation(String docTypeDesignation) {
        em.flush();
        Query q = em.createNamedQuery(DOCTYPE_FIND_BY_DESIGNATION).setParameter("dctDesignation", docTypeDesignation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
}
