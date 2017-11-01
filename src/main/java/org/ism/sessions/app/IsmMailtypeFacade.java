/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.app;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Maillist;
import org.ism.entities.app.IsmMailtype;
import org.ism.sessions.AbstractFacade;

/**
 * IsmMailtypeFacade class
 *
 * @author r.hendrick
 */
@Stateless
public class IsmMailtypeFacade extends AbstractFacade<IsmMailtype> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String FIND_BY_CODE           = "IsmMailtype.findByType";
    private final String FIND_BY_DESIGNATION    = "IsmMailtype.findByDesignation";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public List<IsmMailtype> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("type", code);
        return q.getResultList();
    }
    
    public List<IsmMailtype> findByType(String type) {
        return findByCode(type);
    }
    
    public List<IsmMailtype> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("designation", designation);
        return q.getResultList();
    }
    
    
    
    
    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public IsmMailtypeFacade() {
        super(IsmMailtype.class);
    }
}
