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
import org.ism.entities.admin.Company;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class StaffGroupDefFacade extends AbstractFacade<StaffGroupDef> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "StaffGroupDef.selectAllByLastChange";
    private final String FIND_BY_CODE = "StaffGroupDef.findByStgdGroupDef";       // query = "SELECT s FROM StaffGroupDef s WHERE s.stgdGroupDef = :stgdGroupDef"
    private final String FIND_BY_DESIGNATION = "StaffGroupDef.findByStgdDesignation";     //query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDesignation = :stgdDesignation"
    private final String FIND_BY_CODE_OF_COMPANY = "StaffGroupDef.findByStgdGroupDefOfCompany";  
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "StaffGroupDef.findByStgdDesignationOfCompany";    
    private final String FIND_GROUP_BY_COMPANY = "StaffGroupDef.findGroupByCompany";
    private final String FIND_BY_COMPANY = "StaffGroupDef.findByStgdCompany";

    public StaffGroupDefFacade() {
        super(StaffGroupDef.class);
    }

    public List<StaffGroupDef> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDef> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("stgdGroupDef", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDef> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("stgdDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDef> findGroupByCompany() {
        em.flush();
        Query q = em.createNamedQuery(FIND_GROUP_BY_COMPANY);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDef> findByCompany(Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_COMPANY).setParameter("stgdCompany", company);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<StaffGroupDef> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("stgdGroupDef", code)
                .setParameter("stgdCompany", company);
        return q.getResultList();
    }

    public List<StaffGroupDef> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("stgdDesignation", designation)
                .setParameter("stgdCompany", company);
        return q.getResultList();
    }

}
