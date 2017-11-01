/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.admin;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Maillist;
import org.ism.sessions.AbstractFacade;

/**
 * MaillistFacade class
 *
 * @author r.hendrick
 */
@Stateless
public class MaillistFacade extends AbstractFacade<Maillist> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "Maillist.selectAllByLastChange";
    private final String FIND_BY_CODE = "Maillist.findByMlEvent";
    private final String FIND_BY_DESIGNATION = "Maillist.findByMlGroupe";
    private final String FindById = "Maillist.findByMlId";
    private final String FindByEvent = "Maillist.findByMlEvent";
    private final String FindByGroupe = "Maillist.findByMlGroupe";
    private final String FindByTos = "Maillist.findByMlTos";
    private final String FindByCcs = "Maillist.findByMlCcs";
    private final String FindByCcis = "Maillist.findByMlCcis";

    public MaillistFacade() {
        super(Maillist.class);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public List<Maillist> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        return q.getResultList();
    }
    /**
     * @NamedQuery(name = "Maillist.findByMlEvent", 
     * query = "SELECT m FROM Maillist m WHERE m.mlEvent = :mlEvent"),
    
     * @param code corresponding to event code
     * @return a list if any found
     */
    public List<Maillist> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("mlEvent", code);
        return q.getResultList();
    }
    /**
     * Same as findByCode surcharge
     * @param event event
     * @return list if any
     */
    public List<Maillist> findByEvent(String event) {
        return findByCode(event);
    }

    
    /**
     * @NamedQuery(name = "Maillist.findByMlGroupe", 
     * query = "SELECT m FROM Maillist m WHERE m.mlGroupe = :mlGroupe"),
    
     * @param designation correspond to groupe
     * @return 
     */
    public List<Maillist> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("mlGroupe", designation);
        return q.getResultList();
    }
    /**
     * Surchage method of findByDesignation
     * @param groupe groupe defined
     * @return lsit of restul if any
     */
    public List<Maillist> findByGroup(String groupe) {
        return findByDesignation(groupe);
    }
    
    public List<Maillist> findByTos(String tos) {
        em.flush();
        Query q = em.createNamedQuery(FindByTos).setParameter("mlTos", tos);
        return q.getResultList();
    }
    
    public List<Maillist> findByCcs(String ccs) {
        em.flush();
        Query q = em.createNamedQuery(FindByCcs).setParameter("mlCcs", ccs);
        return q.getResultList();
    }
    
    public List<Maillist> findByCcis(String ccis) {
        em.flush();
        Query q = em.createNamedQuery(FindByCcis).setParameter("mlCcis", ccis);
        return q.getResultList();
    }
    
    
    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
