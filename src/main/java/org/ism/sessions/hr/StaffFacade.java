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
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffFacade extends AbstractFacade<Staff> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "Staff.selectAllByLastChange";
    private final String FIND_BY_CODE = "Staff.findByStStaff";       // query = "SELECT s FROM Staff s WHERE s.stStaff = :stStaff"
    private final String FIND_BY_LASTNAME = "Staff.findByStLastname";     // query = "SELECT s FROM Staff s WHERE s.stLastname = :stLastname"
    private final String FIND_BY_FIRSTNAME = "Staff.findByStFirstname";     // query = "SELECT s FROM Staff s WHERE s.stFirstname = :stFirstname"
    private final String FIND_BY_MIDDLENAME = "Staff.findByStMiddlename";     // query = "SELECT s FROM Staff s WHERE s.stMiddlename = :stMiddlename"
    
    private final String STARTWITH_NAME_AND_LIMIT = "Staff.startWithNameAndLimit";    //SELECT s FROM Staff s WHERE concat(s.stFirstname, ' ', s.stLastname, ' ', s.stMiddlename, ' [', s.stStaff, ']') like ':startWith%'  LIMIT :queryLimit

    public StaffFacade() {
        super(Staff.class);
    }

    public List<Staff> findByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery("Staff.findByStStaff").setParameter("stStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if (countStaff > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("stStaff", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findByLastName(String lastname) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_LASTNAME).setParameter("stLastname", lastname);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findByFirstName(String firstname) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_FIRSTNAME).setParameter("stFirstname", firstname);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findByMiddlename(String middlename) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_MIDDLENAME).setParameter("stMiddlename", middlename);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Staff> findStaffStartingWith(String query, int limit) {
        em.flush();
        Query q = em.createNamedQuery(STARTWITH_NAME_AND_LIMIT).setParameter("startWith", query+"%");
        return q.setMaxResults(limit).getResultList();
    }
}
