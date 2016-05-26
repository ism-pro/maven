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
import org.ism.entities.NonConformiteRequest;
import org.ism.entities.Processus;

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

    private final String PROCESSUS_SELECTALLBYLASTCHANGED             = "Processus.selectAllByLastChange";

    
    
    public ProcessusFacade() {
        super(Processus.class);
    }

    public List<Processus> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(PROCESSUS_SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }

}
