/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.admin;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ism.entities.admin.Company;
import org.ism.entities.admin.Mailaddress;
import org.ism.sessions.AbstractFacade;

/**
 * MailaddressFacade class
 *
 * @author r.hendrick
 */
@Stateless
public class MailaddressFacade extends AbstractFacade<Mailaddress> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED     = "Mailaddress.selectAllByLastChange";
    private final String FIND_BY_CODE               = "Mailaddress.findByMaGroupe";
    private final String FIND_BY_DESIGNATION        = "Mailaddress.findByMaAddress";
    private final String FindByGroupe               = "Mailaddress.findByMaGroupe";
    private final String FindByAddress              = "Mailaddress.findByMaAddress";
    private final String FindByComapnyGroupe        = "Mailaddress.findByComapnyGroupe";
    private final String FindByCampanyAddress       = "Mailaddress.findByCampanyAddress";
    private final String FindByCompanyGroupAddress  = "Mailaddress.findByCompanyGroupAddress";

//    @NamedQuery(name = "Mailaddress.findByMaId", query = "SELECT m FROM Mailaddress m WHERE m.maId = :maId"),
//    @NamedQuery(name = "Mailaddress.findByMaGroupe", query = "SELECT m FROM Mailaddress m WHERE m.maGroupe = :maGroupe"),
//    @NamedQuery(name = "Mailaddress.findByMaAddress", query = "SELECT m FROM Mailaddress m WHERE m.maAddress = :maAddress"),
//    @NamedQuery(name = "Mailaddress.findByComapnyGroupe", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maGroupe = :maGroupe"),
//    @NamedQuery(name = "Mailaddress.findByCampanyAddress", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maGroupe = :maAddress"),
//    @NamedQuery(name = "Mailaddress.findByCompanyGroupAddress", query = "SELECT m FROM Mailaddress m WHERE m.maCompany = :maCompany AND m.maGroupe = : AND m.maAddress = :maAddress"),
//    @NamedQuery(name = "Mailaddress.findByMaDeleted", query = "SELECT m FROM Mailaddress m WHERE m.maDeleted = :maDeleted"),
//    @NamedQuery(name = "Mailaddress.findByMaCreated", query = "SELECT m FROM Mailaddress m WHERE m.maCreated = :maCreated"),
//    @NamedQuery(name = "Mailaddress.findByMaChanged", query = "SELECT m FROM Mailaddress m WHERE m.maChanged = :maChanged"),
//    @NamedQuery(name = "Mailaddress.selectAllByLastChange", query = "SELECT m FROM Mailaddress m ORDER BY m.maChanged DESC")

    public MailaddressFacade() {
        super(Mailaddress.class);
    }
    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////

    public List<Mailaddress> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        return q.getResultList();
    }

    public List<Mailaddress> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("maGroupe", code);
        return q.getResultList();
    }
    
    public List<Mailaddress> findByGroupe(String groupe) {
        return findByCode(groupe);
    }

    public List<Mailaddress> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("maAddress", designation);
        return q.getResultList();
    }
    
    public List<Mailaddress> findByAddress(String address) {
        return findByDesignation(address);
    }
    
    public List<Mailaddress> findByCompanyGroupe(Company company, String Groupe) {
        em.flush();
        Query q = em.createNamedQuery(FindByComapnyGroupe).setParameter("maCompany", company).setParameter("maGroupe", Groupe);
        return q.getResultList();
    }
    
    public List<Mailaddress> findByCompanyAddress(Company company, String address) {
        em.flush();
        Query q = em.createNamedQuery(FindByCampanyAddress).setParameter("maCompany", company).setParameter("maAddress", address);
        return q.getResultList();
    }
    
    public List<Mailaddress> findByCompanyGroupeAddress(Company company, String groupe, String address) {
        em.flush();
        Query q = em.createNamedQuery(FindByCompanyGroupAddress)
                .setParameter("maCompany", company)
                .setParameter("maGroupe", groupe)
                .setParameter("maAddress", address);
        return q.getResultList();
    }
    

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
