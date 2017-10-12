/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.sessions.admin;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.admin.Mailsender;
import org.ism.sessions.AbstractFacade;

/**
 * <h1>MailsenderFacade</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@Stateless
public class MailsenderFacade extends AbstractFacade<Mailsender> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "Mailsender.selectAllByLastChange";     
    private final String FIND_BY_ADDRESS        = "Mailsender.findByMsAddress";        
    private final String FIND_BY_SMTP           = "Mailsender.selectAllByLastChange";    
    private final String FIND_BY_COMPANY        = "Mailsender.findByCompany";

    public MailsenderFacade() {
        super(Mailsender.class);
    }


    public List<Mailsender> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Mailsender> findByCode(String msAddress) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_ADDRESS).setParameter("msAddress", msAddress);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Mailsender> findByDesignation(String msSmtpsrv) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_SMTP).setParameter("msSmtpsrv", msSmtpsrv);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public Mailsender findByCompany(Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_COMPANY).setParameter("msCompany", company);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return (Mailsender) q.getResultList().get(0);
        }
        return null;
    }

    
    
    
}
