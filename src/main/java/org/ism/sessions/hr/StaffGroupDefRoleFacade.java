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
import org.ism.entities.app.IsmRole;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.hr.StaffGroupDefRole;
import org.ism.sessions.AbstractFacade;
import org.ism.entities.admin.Company;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffGroupDefRoleFacade extends AbstractFacade<StaffGroupDefRole> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "StaffGroupDefRole.selectAllByLastChange";

    public StaffGroupDefRoleFacade() {
        super(StaffGroupDefRole.class);
    }

    public List<StaffGroupDefRole> findByStaffGroups(StaffGroupDef groupDef) {
        em.flush();
        Query q = em.createNamedQuery("StaffGroupDefRole.findByStgdrGroups").setParameter("stgdrGroupDef", groupDef);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if (countStaff > 0) {
            return q.getResultList();
        }
        return null;
    }

    public StaffGroupDefRole findBy(StaffGroupDef groupDef, IsmRole role) {
        em.flush();
        Query q = em.createNamedQuery("StaffGroupDefRole.findByStgdrGroupAndRole")
                .setParameter("stgdrGroupDef", groupDef)
                .setParameter("stgdrRole", role);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int countStaff = q.getResultList().size();
        if (countStaff > 0) {
            return (StaffGroupDefRole) q.getSingleResult();
        }
        return null;
    }

    public List<StaffGroupDefRole> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDefRole> findByGroupDefRole(StaffGroupDef groupDef, IsmRole role, Company company) {
        em.flush();
        Query q = em.createNamedQuery("StaffGroupDefRole.findByStgdrGroupAndRoleOfCompany")
                .setParameter("stgdrGroupDef", groupDef)
                .setParameter("stgdrRole", role)
                .setParameter("stgdrCompany", company);
        return q.getResultList();
    }
}
