/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.smq.nc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.ism.entities.admin.Company;
import org.ism.entities.smq.Processus;
import org.ism.entities.smq.nc.NonConformiteRequest;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class NonConformiteRequestFacade extends AbstractFacade<NonConformiteRequest> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final String NC_REQUEST_FINDALL = "NonConformiteRequest.findAll";
    private final String NC_REQUEST_FINDBY_OCCURED = "NonConformiteRequest.findByNcrOccured";
    private final String NC_REQUEST_FINDBY_PRODUCT = "NonConformiteRequest.findByNcrProduct";
    private final String NC_REQUEST_FINDBY_TRACE = "NonConformiteRequest.findByNcrTrace";
    private final String NC_REQUEST_FINDBY_QUANTITY = "NonConformiteRequest.findByNcrQuantity";
    private final String NC_REQUEST_FINDBY_LINK = "NonConformiteRequest.findByNcrLink";
    private final String NC_REQUEST_FINDBY_TITLE = "NonConformiteRequest.findByNcrTitle";

    private final String NC_REQUEST_FINDBY_COMPANY = "NonConformiteRequest.findByNcrCompany";
    private final String NC_REQUEST_FINDBY_PROCESSUS = "NonConformiteRequest.findByNcrProcessus";
    private final String NC_REQUEST_FINDBY_STAFF = "NonConformiteRequest.findByNcrStaff";
    private final String NC_REQUEST_FINDBY_STATE = "NonConformiteRequest.findByNcrState";
    private final String NC_REQUEST_FINDBY_NATURE = "NonConformiteRequest.findByNcrNature";
    private final String NC_REQUEST_FINDBY_UNITE = "NonConformiteRequest.findByNcrUnite";
    private final String NC_REQUEST_FINDBY_GRAVITY = "NonConformiteRequest.findByNcrGravity";
    private final String NC_REQUEST_FINDBY_FREQUENCY = "NonConformiteRequest.findByNcrFrequency";
    // private final String NC_REQUEST_FINDIN_DESCRIPTION        = "NonConformiteRequest.findInNcrDescription";
    private final String NC_REQUEST_FINDBY_STAFFONVALIDATE = "NonConformiteRequest.findByNcrStaffOnValidate";
    private final String NC_REQUEST_FINDBY_STAFFONACTION = "NonConformiteRequest.findByNcrStaffOnAction";
    private final String NC_REQUEST_FINDBY_STAFFONREFUSE = "NonConformiteRequest.findByNcrStaffOnRefuse";
    private final String NC_REQUEST_FINDBY_CREATED = "NonConformiteRequest.findByNcrCreated";
    private final String NC_REQUEST_FINDBY_CHANGED = "NonConformiteRequest.findByNcrChanged";

    private final String SELECTALLBYLASTCHANGED = "NonConformiteRequest.selectAllByLastChange";     // query = "SELECT n FROM NonConformiteRequest n ORDER BY n.ncrChanged DESC"
    private final String FIND_BY_CODE = "";
    private final String FIND_BY_DESIGNATION = "NonConformiteRequest.findByNcrTitle";            // query = "SELECT n FROM NonConformiteRequest n WHERE n.ncrTitle = :ncrTitle"

    private final String FIND_BY_DESIGNATION_OF_COMPANY = "NonConformiteRequest.findByNcrTitleOfCompany";

    private final String COUNT_ITEMS_CREATE_IN_RANGE = "NonConformiteRequest.countItemsCreateInRange";

    private final String ITEMS_CREATE_IN_RANGE = "NonConformiteRequest.itemsCreateInRange";
    private final String ITEMS_APPROUVED_IN_RANGE = "NonConformiteRequest.itemsApprouvedInRange";
    private final String ITEMS_STATE_IN_RANGE = "NonConformiteRequest.itemsStateInRange";
    private final String ITEMS_STATE_IN_CHANGE_RANGE = "NonConformiteRequest.itemsStateInChangedRange";

    private final String ITEMS_CREATE_IN_RANGE_BY_PROCESSUS = "NonConformiteRequest.itemsCreateInRangeByProcessus";
    private final String ITEMS_APPROUVED_IN_RANGE_BY_PROCESSUS = "NonConformiteRequest.itemsApprouvedInRangeByProcessus";
    private final String ITEMS_STATE_IN_RANGE_BY_PROCESSUS = "NonConformiteRequest.itemsStateInRangeByProcessus";
    private final String ITEMS_STATE_IN_CHANGE_RANGE_BY_PROCESSUS = "NonConformiteRequest.itemsStateInChangedRangeByProcessus";

    private final String COUNT_PROCESSUS_IN_STATECODE = "NonConformiteRequest.countProcessusInState";

    public NonConformiteRequestFacade() {
        super(NonConformiteRequest.class);
    }

    public List<NonConformiteRequest> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteRequest> findByCode(String code) {
        /*
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("pProcessus", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
         */
        return null;
    }

    public List<NonConformiteRequest> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("ncrTitle", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<NonConformiteRequest> findByProcessus(String processus) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_PROCESSUS).setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public Integer countByProcessus(String processus) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_PROCESSUS).setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return count;
        }
        return 0;
    }

    public List<NonConformiteRequest> findByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STAFF).setParameter("ncrStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public Integer countByStaff(String staff) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STAFF).setParameter("ncrStaff", staff);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return count;
        }
        return 0;
    }

    public List<NonConformiteRequest> findByState(String state) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STATE).setParameter("ncrState", state);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public Integer countByState(String state) {
        em.flush();
        Query q = em.createNamedQuery(NC_REQUEST_FINDBY_STATE).setParameter("ncrState", state);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return count;
        }
        return 0;
    }

    /**
     *
     * @param fromInclude
     * @param toExclude
     * @return
     */
    public List<NonConformiteRequest> itemsCreateInRange(Date fromInclude, Date toExclude) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_CREATE_IN_RANGE).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param fromInclude
     * @param toExclude
     * @return
     */
    public List<NonConformiteRequest> itemsApprouvedInRange(Date fromInclude, Date toExclude) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_APPROUVED_IN_RANGE).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param fromInclude
     * @param toExclude
     * @return
     */
    public List<NonConformiteRequest> itemsStateInRange(String state, Date fromInclude, Date toExclude) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_STATE_IN_RANGE).setParameter("ncrState", state).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param fromInclude
     * @param toExclude
     * @return
     */
    public List<NonConformiteRequest> itemsStateInChangeRange(String state, Date fromInclude, Date toExclude) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_STATE_IN_CHANGE_RANGE).setParameter("ncrState", state).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param fromInclude
     * @param toExclude
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> itemsCreateInRangeByProcessus(Date fromInclude, Date toExclude, Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_CREATE_IN_RANGE_BY_PROCESSUS)
                .setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude)
                .setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param fromInclude
     * @param toExclude
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> itemsApprouvedInRangeByProcessus(Date fromInclude, Date toExclude, Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_APPROUVED_IN_RANGE_BY_PROCESSUS)
                .setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude)
                .setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param fromInclude
     * @param toExclude
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> itemsStateInRangeByProcessus(String state, Date fromInclude, Date toExclude, Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_STATE_IN_RANGE_BY_PROCESSUS)
                .setParameter("ncrState", state).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude)
                .setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     *
     * @param state is one of (A, B, C, D, E) respectively (Créé, en attente de
     * solution, en cours, terminé, annulé)
     * @param fromInclude
     * @param toExclude
     * @param processus
     * @return
     */
    public List<NonConformiteRequest> itemsStateInChangeRangeByProcessus(String state, Date fromInclude, Date toExclude, Processus processus) {
        em.flush();
        Query q = em.createNamedQuery(ITEMS_STATE_IN_CHANGE_RANGE_BY_PROCESSUS)
                .setParameter("ncrState", state).setParameter("ncrFrom", fromInclude).setParameter("ncrTo", toExclude)
                .setParameter("ncrProcessus", processus);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     * Count the amount of non conformite for a specified processus and state.
     *
     * @param processus is the object processus
     * @param state is one of the istate not the object like A, B, C, D, E
     * @return a integer value corresponding to the result
     */
    public Integer countProcessusInState(Processus processus, String state) {
        Query q = em.createNamedQuery(COUNT_PROCESSUS_IN_STATECODE)
                .setParameter("ncrProcessus", processus)
                .setParameter("ncrState", state);
        return Integer.valueOf(q.getSingleResult().toString());
    }

    public List<NonConformiteRequest> findByCode(String code, Company company) {
        /*
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_PROCESSUS).setParameter("pProcessus", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
         */
        return null;
    }

    public List<NonConformiteRequest> findByDesignation(String designation, Company company) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION_OF_COMPANY)
                .setParameter("ncrTitle", designation)
                .setParameter("ncrCompany", company);
        return q.getResultList();
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Criteria
    //
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Filtring allow to append condition to a predicate depending on field.
     * Main purpose of filtring is to implement entities conditions.
     *
     * <br> NonConformiteRequestField_ncrExtend=Etendre
     * <br> NonConformiteRequestField_ncrId=N°
     * <br> NonConformiteRequestField_ncrCompany=Société
     * <br> NonConformiteRequestField_ncrProcessus=Processus
     * <br> NonConformiteRequestField_ncrStaff=Emetteur
     * <br> NonConformiteRequestField_ncrState=Etat
     * <br> NonConformiteRequestField_ncrStatePrevious=Etat Actuel
     * <br> NonConformiteRequestField_ncrStateNew=Etat à venir
     * <br> NonConformiteRequestField_ncrNature=Nature
     * <br> NonConformiteRequestField_ncrOccured=Apparition
     * <br> NonConformiteRequestField_ncrProduct=Produit
     * <br> NonConformiteRequestField_ncrTrace=Traçabilité
     * <br> NonConformiteRequestField_ncrQuantity=Quantité
     * <br> NonConformiteRequestField_ncrUnite=Unité
     * <br> NonConformiteRequestField_ncrGravity=Gravité
     * <br> NonConformiteRequestField_ncrFrequency=Fréquence
     * <br> NonConformiteRequestField_ncrDescription=Description
     * <br> NonConformiteRequestField_ncrTitle=Intitulé
     * <br> NonConformiteRequestField_ncrLink=Lien/Image
     * <br> NonConformiteRequestField_ncrApprouver=Approbateur
     * <br> NonConformiteRequestField_ncrApprouved=Approuver
     * <br> NonConformiteRequestField_ncrApprouvedDate=Date approb.
     * <br> NonConformiteRequestField_ncrApprouvedDesc=Raison refus
     * <br> NonConformiteRequestField_ncrCreated=Création
     * <br> NonConformiteRequestField_ncrChanged=Modif.
     * <br> NonConformiteRequestField_ncrEnddingAction=Echéance
     * <br> NonConformiteRequestField_ncrClientname=Client - Nom
     * <br> NonConformiteRequestField_ncrClientaddress=Client - Adresse
     * <br> NonConformiteRequestField_ncrClientphone=Client - Phone
     * <br> NonConformiteRequestField_ncrClientemail=Client - E-mail
     * <br> NonConformiteRequestField_ncrClienttype=Client - Type
     *
     * @param cb is a criteria builder allowing to concat in this case
     * @param rt is the main data
     * @param filter couple of field filter and value of filter
     * @return a predicate of filter
     */
    @Override
    public Predicate filtring(CriteriaBuilder cb, Root<NonConformiteRequest> rt, Map.Entry<String, Object> filter) {
        Expression<String> expr;
        final String field = filter.getKey();
        final String value = filter.getValue().toString();
        Predicate p = null;
        switch (field) {
            case "ncrStaff":
                p = likeFieldStaffComposite(cb, rt, field, value);
                break;
            case "ncrProcessus":
                p = likeFieldComposite(cb, rt, field, "pProcessus", "pDesignation", "pId", value);
                break;
            case "ncrState":
                p = likeFieldComposite(cb, rt, field, "istate", "statename", "id", value);
                break;
            case "ncrNature":
                p = likeFieldComposite(cb, rt, field, "ncnNature", "ncnDesignation", "ncnId", value);
                break;
            case "ncrGravity":
                p = likeFieldComposite(cb, rt, field, "ncgGravity", "ncgDesignation", "ncgId", value);
                break;
            case "ncrFrequency":
                p = likeFieldComposite(cb, rt, field, "ncfFrequency", "ncfDesignation", "ncfId", value);
                break;
            case "ncrCompany":
                p = likeFieldComposite(cb, rt, field, "cCompany", "cDesignation", "cId", value);
                break;
            default:
                expr = rt.get(field);
                if (filter.getValue() instanceof Boolean) {
                    p = cb.equal(expr, value);
                } else if (value.contains("start") && value.contains("end")) {
                    p = betweenFieldDate(cb, rt, field, value);
                } else {
                    p = cb.like(expr, value);
                }
                break;
        }
        return p;
    }
}
