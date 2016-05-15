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

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteRequestFacade extends AbstractFacade<NonConformiteRequest> {
    
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private final String NC_REQUEST_FINDALL             = "NonConformiteRequest.findAll";
    private final String NC_REQUEST_FINDBY_OCCURED      = "NonConformiteRequest.findByNcrOccured";
    private final String NC_REQUEST_FINDBY_PRODUCT      = "NonConformiteRequest.findByNcrProduct";
    private final String NC_REQUEST_FINDBY_TRACE        = "NonConformiteRequest.findByNcrTrace";
    private final String NC_REQUEST_FINDBY_QUANTITY     = "NonConformiteRequest.findByNcrQuantity";
    private final String NC_REQUEST_FINDBY_LINK         = "NonConformiteRequest.findByNcrLink";
    private final String NC_REQUEST_FINDBY_TITLE        = "NonConformiteRequest.findByNcrTitle";
    
    private final String NC_REQUEST_FINDBY_COMPANY      = "NonConformiteRequest.findByNcrCompany";
    private final String NC_REQUEST_FINDBY_PROCESSUS    = "NonConformiteRequest.findByNcrProcessus";
    private final String NC_REQUEST_FINDBY_STAFF        = "NonConformiteRequest.findByNcrStaff";
    private final String NC_REQUEST_FINDBY_STATE        = "NonConformiteRequest.findByNcrState";
    private final String NC_REQUEST_FINDBY_NATURE       = "NonConformiteRequest.findByNcrNature";
    private final String NC_REQUEST_FINDBY_UNITE        = "NonConformiteRequest.findByNcrUnite";
    private final String NC_REQUEST_FINDBY_GRAVITY      = "NonConformiteRequest.findByNcrGravity";
    private final String NC_REQUEST_FINDBY_FREQUENCY    = "NonConformiteRequest.findByNcrFrequency";
   // private final String NC_REQUEST_FINDIN_DESCRIPTION        = "NonConformiteRequest.findInNcrDescription";
    private final String NC_REQUEST_FINDBY_STAFFONVALIDATE = "NonConformiteRequest.findByNcrStaffOnValidate";
    private final String NC_REQUEST_FINDBY_STAFFONACTION  = "NonConformiteRequest.findByNcrStaffOnAction";
    private final String NC_REQUEST_FINDBY_STAFFONREFUSE  = "NonConformiteRequest.findByNcrStaffOnRefuse";
    private final String NC_REQUEST_FINDBY_CREATED        = "NonConformiteRequest.findByNcrCreated";
    private final String NC_REQUEST_FINDBY_CHANGED        = "NonConformiteRequest.findByNcrChanged";

    
    
    public NonConformiteRequestFacade() {
        super(NonConformiteRequest.class);
    }
    
    public List<NonConformiteRequest> findByProcessus(String processus) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_PROCESSUS).setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    public Integer countByProcessus(String processus) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_PROCESSUS).setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return count;
        }
        return 0;
    }
        
    public List<NonConformiteRequest> findByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STAFF).setParameter("ncrStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    public Integer countByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STAFF).setParameter("ncrStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return count;
        }
        return 0;
    }
    
    public List<NonConformiteRequest> findByState(String state) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STATE).setParameter("ncrState", state);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return q.getResultList();
        }
        return null;
    }
    public Integer countByState(String state) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STATE).setParameter("ncrState", state);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if(count > 0){
            return count;
        }
        return 0;
    }

    
}
