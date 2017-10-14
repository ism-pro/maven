/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.smq;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.hr.Staff;
import org.ism.entities.hr.StaffGroupDef;
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.ProcessAccess;
import org.ism.sessions.AbstractFacade;

/**
 * <h1>ProcessAccessFacade</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@Stateless
public class ProcessAccessFacade extends AbstractFacade<ProcessAccess> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String SELECTALLBYLASTCHANGED = "ProcessAccess.selectAllByLastChange";
    private final String FIND_BY_DOC_LAST = "ProcessAccess.findByPaDocLast";
    private final String FIND_BY_USER = "ProcessAccess.findByStaff";
    private final String FIND_BY_UNGROUP_DOC = "ProcessAccess.findByUngroupDoc";
    private final String FIND_BY_GROUPDEF = "ProcessAccess.findByGroupdef";
    private final String FIND_BY_DOC_AND_USER = "ProcessAccess.findByDocAndUser";
    private final String FIND_BY_DOC_AND_GROUPDEF = "ProcessAccess.findByDocAndGroupdef";

    public ProcessAccessFacade() {
        super(ProcessAccess.class);
    }

    public List<ProcessAccess> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findAllByDocLast(DocExplorer document) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DOC_LAST).setParameter("paDocexplorer", document);;
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findByStaff(Staff staff) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_USER).setParameter("paStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findByUnGroupDocument(DocExplorer docExplorer) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_UNGROUP_DOC).setParameter("paDocexplorer", docExplorer);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findAllByGroup(StaffGroupDef groupdef) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_GROUPDEF).setParameter("paGroupdef", groupdef);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findByDocAndStaff(DocExplorer docExplorer, Staff staff) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DOC_AND_USER).setParameter("paDocexplorer", docExplorer).setParameter("paStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<ProcessAccess> findByDocAndGroup(DocExplorer docExplorer, StaffGroupDef groupdef) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DOC_AND_GROUPDEF).setParameter("paDocexplorer", docExplorer).setParameter("paGroupdef", groupdef);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

}
