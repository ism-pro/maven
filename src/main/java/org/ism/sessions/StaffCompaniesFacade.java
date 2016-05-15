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
import org.ism.entities.Company;
import org.ism.entities.Staff;
import org.ism.entities.StaffCompanies;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffCompaniesFacade extends AbstractFacade<StaffCompanies> {
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StaffCompaniesFacade() {
        super(StaffCompanies.class);
    }
    
    public List<StaffCompanies> findByStaff(Staff staff) {
        em.flush();
        Query q = em.createNamedQuery("StaffCompanies.findByStcStaff").setParameter("stcStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if(countStaff > 0){
            return q.getResultList();
        }
        return null;
    }

    public StaffCompanies findByStaffCompany(Staff staff, Company company) {
        em.flush();
        Query q = em.createNamedQuery("StaffCompanies.findByStcStaffAndCompany")
                .setParameter("stcStaff", staff)
                .setParameter("stcCompany", company);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if(countStaff > 0){
            return (StaffCompanies) q.getResultList().get(0);
        }
        return null;
    }
}
