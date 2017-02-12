/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process.ctrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;
import org.ism.entities.hr.Staff;
import org.ism.sessions.AbstractFacade;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class AnalyseDataFacade extends AbstractFacade<AnalyseData> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    private final String SELECTALLBYLASTCHANGED = "AnalyseData.selectAllByLastChange";
    private final String FIND_BY_CODE = "AnalyseData.findByAdPointStr";
    private final String FIND_BY_DESIGNATION = "AnalyseData.findByAdTypeStr";
    private final String FIND_BY_POINT = "AnalyseData.findByAdPoint";
    private final String FIND_BY_TYPE = "AnalyseData.findByAdType";
    private final String FIND_BY_SAMPLER = "AnalyseData.findByAdSampler";
    private final String FIND_BY_VALIDATOR = "AnalyseData.findByAdValidator";
    private final String FIND_BY_POINT_AND_TYPE = "AnalyseData.findByAdPointType";
    private final String FIND_BY_POINT_AND_TYPE_SAMPLE_TIME_RANGE = "AnalyseData.findByAdPointTypeSampleTimeRange";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalyseDataFacade() {
        super(AnalyseData.class);
    }

    public List<AnalyseData> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByCode(String code) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("adPoint", code);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("adType", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPoint(AnalysePoint adPoint) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT).setParameter("adPoint", adPoint);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByType(AnalyseType adType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_TYPE).setParameter("adType", adType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findBySampler(Staff adSampler) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_SAMPLER).setParameter("adSampler", adSampler);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByValidator(Staff adValidator) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_VALIDATOR).setParameter("adValidator", adValidator);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPointType(AnalysePoint adPoint, AnalyseType adType) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_AND_TYPE)
                .setParameter("adPoint", adPoint)
                .setParameter("adType", adType);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<AnalyseData> findByPointTypeSampleTimeRange(AnalysePoint point, AnalyseType type, Date from, Date to) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_POINT_AND_TYPE_SAMPLE_TIME_RANGE)
                .setParameter("adPoint", point)
                .setParameter("adType", type)
                .setParameter("from", from)
                .setParameter("to", to);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    
    public List<AnalyseData> findByCriteria(Integer firstItem, Integer rows,
        Map<String, String> sorts, Map<String, String> filters){
        em.flush();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AnalyseData> criteriaQuery = criteriaBuilder.createQuery(AnalyseData.class);
        Metamodel metamodel = em.getMetamodel();
        EntityType<AnalyseData> et = metamodel.entity(AnalyseData.class);
        Root<AnalyseData> root = criteriaQuery.from(AnalyseData.class);

        List<Order> orders = sortBy(sorts);
        List<Predicate> predicates = filterBy(filters);
        
        // Apply Select
        criteriaQuery = criteriaQuery.select(root);
        
        // Apply filters
        if(predicates!=null){
            criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        // Sort By
        if(orders!=null){
            criteriaQuery = criteriaQuery.orderBy(orders);
        }else{
            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(root.get("adId")));
        }
        
        
        TypedQuery<AnalyseData> q = em.createQuery(criteriaQuery);
        q.setFirstResult(firstItem);
        q.setMaxResults(rows);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
    
    public List<Order> sortBy(Map<String, String> sorts) {
        em.flush();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AnalyseData> criteriaQuery = criteriaBuilder.createQuery(AnalyseData.class);
        Metamodel metamodel = em.getMetamodel();
        EntityType<AnalyseData> et = metamodel.entity(AnalyseData.class);
        Root<AnalyseData> root = criteriaQuery.from(em.getMetamodel().entity(AnalyseData.class));

        List<Order> orders = new ArrayList<>();
        for(Map.Entry<String, String> sort : sorts.entrySet()){
            Order order = ordering(sort.getKey(), sort.getValue());
            if(order!=null)
                orders.add(order);
        }
        return orders;
    }
    
    public List<Predicate> filterBy(Map<String, String> filters){
        List<Predicate> predicates = null;
        return predicates;
    }

    public Order ordering(String field, String sortway) {
        Order order = null;
        // Dismiss rong way
        if(sortway == null || sortway.matches("UNSORTED"))
            return null;
        // Define way
        Boolean asc = sortway.matches("ASCENDING");
        // Get managin order
        em.flush();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Root<AnalyseData> root = cb.createQuery(AnalyseData.class).from(em.getMetamodel().entity(AnalyseData.class));
        
        Expression<String> ex = null;
        switch (field) {
            case "adId":
                ex = root.get("adId");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adCompany":
                ex = root.get("adCompany");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adPoint":
                ex = root.get("adPoint");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adType":
               ex = root.get("adType");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adValue":
                ex = root.get("adValue");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adValueT":
                ex = root.get("adValueT");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adSampler":
                ex = root.get("adSampler");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adValidate":
                ex = root.get("adValidate");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adValidator":
                ex = root.get("adValidator");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adenlimitHH":
                ex = root.get("adenlimitHH");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adlimitHH":
                ex = root.get("adlimitHH");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adenlimitH":
                ex = root.get("adenlimitH");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adlimitH":
                ex = root.get("adlimitH");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adenlimitBB":
                ex = root.get("adenlimitBB");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adlimitBB":
                ex = root.get("adlimitBB");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adenlimitB":
                ex = root.get("adenlimitB");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adlimitB":
                ex = root.get("adlimitB");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adBatch":
                ex = root.get("adBatch");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adsampleTime":
                ex = root.get("adsampleTime");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adObservation":
                ex = root.get("adObservation");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adDeleted":
                ex = root.get("adDeleted");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adCreated":
                ex = root.get("adCreated");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            case "adChanged":
                ex = root.get("adChanged");
                order = asc ? cb.asc(ex) : cb.desc(ex);
                break;
            default:
                break;
        }
        return order;
    }

    
    
}
