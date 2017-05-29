/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.hr;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffSetup;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffSetupFacade extends AbstractFacade<StaffSetup> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "StaffSetup.selectAllByLastChange";

    public StaffSetupFacade() {
        super(StaffSetup.class);
    }

    public StaffSetup findByStaff(Staff staff) {
        em.flush();
        Query q = em.createNamedQuery("StaffSetup.findByStsStaff").setParameter("stsStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if (countStaff > 0) {
            return (StaffSetup) q.getSingleResult();
        }
        return null;
    }

    public StaffSetup findByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery("StaffSetup.findByStsStaffStr").setParameter("stsStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if (countStaff > 0) {
            return (StaffSetup) q.getSingleResult();
        }
        return null;
    }

    public List<StaffSetup> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

}
