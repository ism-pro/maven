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
import org.ism.entities.StaffGroupDef;
import org.ism.entities.StaffGroups;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffGroupsFacade extends AbstractFacade<StaffGroups> {
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "StaffGroups.selectAllByLastChange";
    

    public StaffGroupsFacade() {
        super(StaffGroups.class);
    }

    public List<StaffGroups> findByStaff(Staff staff) {
        em.flush();
        Query q = em.createNamedQuery("StaffGroups.findByStgStaff").setParameter("stgStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if(countStaff > 0){
            return q.getResultList();
        }
        return null;
    }

    public StaffGroups findByStaffAndCompanyAndGroupDef(Staff staff, Company company, StaffGroupDef staffGroupDef) {
        em.flush();
        Query q = em.createNamedQuery("StaffGroups.findByStgStaffAndCompanyAndStaffGroupDef")
                .setParameter("stgStaff", staff)
                .setParameter("stgCompany", company)
                .setParameter("stgGroupDef", staffGroupDef);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if(countStaff > 0){ 
            StaffGroups staffGroups = (StaffGroups) q.getResultList().get(0);
            return staffGroups;
        } 
        return null;
    }
    
    
    
    public List<StaffGroups> findAllByLastChanged() {
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
