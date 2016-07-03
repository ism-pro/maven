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
import org.ism.entities.StaffGroupDef;

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
}
