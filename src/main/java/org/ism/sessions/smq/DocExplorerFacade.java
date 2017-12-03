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
import org.ism.entities.admin.Company;
import org.ism.entities.smq.DocExplorer;
import org.ism.entities.smq.DocType;
import org.ism.entities.smq.Processus;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class DocExplorerFacade extends AbstractFacade<DocExplorer> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "DocExplorer.selectAllByLastChange";  // query = "SELECT d FROM DocExplorer d ORDER BY d.dcChanged DESC"
    private final String FIND_BY_CODE = "DocExplorer.findByDcVersion";             // query = "SELECT d FROM DocExplorer d WHERE WHERE d.dcVersion = :dcVersion"
    private final String FIND_BY_DESIGNATION = "DocExplorer.findByDcDesignation";    // query = "SELECT d FROM DocExplorer d WHERE d.dcDesignation = :dcDesignation"
    private final String FIND_BY_PROCESSUS = "DocExplorer.findByProcessus";
    private final String FIND_BY_PROCESSUS_AND_TYPE = "DocExplorer.findByProcessusAndType";
    private final String FIND_BY_CODE_OF_COMPANY = "DocExplorer.findByDcVersionOfCompany";
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "DocExplorer.findByDcDesignationOfCompany";
    private final String FIND_BY_LINK = "DocExplorer.findByDcLink";
    private final String FIND_BY_LINK_OF_COMPANY = "DocExplorer.findByDcLinkOfCompany";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocExplorerFacade() {
        super(DocExplorer.class);
    }

    public List<DocExplorer> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocExplorer> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("dcVersion", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocExplorer> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("dcDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocExplorer> findByProcessus(Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("dcProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocExplorer> findByProcessusAndType(Processus processus, DocType docType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS_AND_TYPE)
                .setParameter("dcProcessus", processus)
                .setParameter("dcType", docType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<DocExplorer> findByCode(String code, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("dcVersion", code)
                .setParameter("dcCompany", company);
        return q.getResultList();
    }

    public List<DocExplorer> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("dcDesignation", designation)
                .setParameter("dcCompany", company);
        return q.getResultList();
    }

    public List<DocExplorer> findByLink(String link) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_LINK).setParameter("dcLink", link);
        return q.getResultList();
    }

    public List<DocExplorer> findByLink(String link, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_LINK_OF_COMPANY)
                .setParameter("dcLink", link)
                .setParameter("dcCompany", company);
        return q.getResultList();
    }

}
