/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.process.Equipement;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class EquipementFacade extends AbstractFacade<Equipement> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "Equipement.selectAllByLastChange";
    private final String FIND_BY_CODE = "Equipement.findByEEquipement";       // query = "SELECT s FROM StaffGroupDef s WHERE s.stgdGroupDef = :stgdGroupDef"
    private final String FIND_BY_DESIGNATION = "Equipement.findByEDesignation";     //query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDesignation = :stgdDesignation"
    private final String FIND_BY_CODE_OF_COMPANY = "Equipement.findByEEquipementOfCompany";       // query = "SELECT s FROM StaffGroupDef s WHERE s.stgdGroupDef = :stgdGroupDef"
    private final String FIND_BY_DESIGNATION_OF_COMPANY = "Equipement.findByEDesignationOfCompany";     //query = "SELECT s FROM StaffGroupDef s WHERE s.stgdDesignation = :stgdDesignation"

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipementFacade() {
        super(Equipement.class);
    }

    public List<Equipement> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Equipement> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("eEquipement", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Equipement> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("eDesignation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Equipement> findByCode(String equipement, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE_OF_COMPANY)
                .setParameter("eEquipement", equipement)
                .setParameter("eCompany", company);
        return q.getResultList();
    }

    public List<Equipement> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("eDesignation", designation)
                .setParameter("eCompany", company);
        return q.getResultList();
    }

}
