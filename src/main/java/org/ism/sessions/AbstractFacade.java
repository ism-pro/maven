/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author r.hendrick
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    // Lazy data model
    //
    // /////////////////////////////////////////////////////////////////////////
    
    public List<T> findAllByFieldDescending(String field){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq = cq.select(root);

        // Setup corresponding sort by
        if(field!=null)
            cq = cq.orderBy(cb.desc(root.get(field)));

        // Processing Query
        TypedQuery<T> q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
    
    
    /**
     * Count result of criterias filters
     *
     * @param filters filters to managed limitation
     * @return number of items found with specified criteria
     */
    public Integer countByCriterias(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);

        CriteriaQuery<Long> select = cq.select(cb.count(root));

        // Setup corresponding filters
        List<Predicate> predicates = filterBy(filters, cb, root);
        if (predicates != null && !predicates.isEmpty()) {
            cq = cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        // Processing Query
        return getEntityManager().createQuery(select).getSingleResult().intValue();
    }

    public List<T> findByCriteria(Integer firstItem, Integer rows, Map<String, String> sorts, Map<String, Object> filters) {
        getEntityManager().flush();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Metamodel metamodel = getEntityManager().getMetamodel();
        EntityType<T> et = metamodel.entity(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        List<Order> orders = sortBy(sorts, criteriaBuilder, root);
        List<Predicate> predicates = filterBy(filters, criteriaBuilder, root);

        // Apply Select
        criteriaQuery = criteriaQuery.select(root);

        // Apply filters
        if (predicates != null) {
            criteriaQuery = criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        // Sort By
        if (orders != null) {
            criteriaQuery = criteriaQuery.orderBy(orders);
        } else {
            // ERREUR DE ID A NE PAS CONSIDERER CAR NON APPELER
            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(root.get("adId")));
        }

        TypedQuery<T> q = getEntityManager().createQuery(criteriaQuery);
        q.setFirstResult(firstItem);
        q.setMaxResults(rows);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    /**
     * Find items of the class defined by sort and criteria
     *
     * @param first the first item from which to start
     * @param pageSize number of content per page
     * @param sorts hasMap with the corresponding field and sort direction
     * @param filters hasMap with field and content to be filtered
     * @return content of the specified criteria return a map number result and
     * datasource
     */
    public List<T> findByCriterias(Integer first, Integer pageSize, Map<String, String> sorts, Map<String, Object> filters) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        // Create the query from select
        cq = cq.select(root);

        // Setup corresponding filters
        List<Predicate> predicates = filterBy(filters, cb, root);
        if (predicates != null && !predicates.isEmpty()) {
            cq = cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        // Setup corresponding sort by
        List<Order> orders = sortBy(sorts, cb, root);
        if (orders != null) {
            cq = cq.orderBy(orders);
//        } else {
//            cq = cq.orderBy(cb.desc(root.get("adsampleTime")));
        }

        // Processing Query
        TypedQuery<T> q = getEntityManager().createQuery(cq);
        q.setFirstResult(first).setMaxResults(pageSize);
        return q.getResultList();
    }

    /**
     * Ordering allow to create a persistence order way for the specified field
     *
     * @param field concern by the sort way
     * @param sortway one of UNSORTED, ASCENDING, DESCENDING
     * @return an Order persitence combination of ordering of the field
     * specified and way
     */
    protected Order ordering(String field, String sortway) {
        //Order order = null;
        // Dismiss rong way
        if (sortway == null || sortway.matches("UNSORTED")) {
            return null;
        }
        // Define way
        Boolean asc = sortway.matches("ASCENDING");
        // Get managin order
        getEntityManager().flush();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> root = cb.createQuery(entityClass).from(getEntityManager().getMetamodel().entity(entityClass));

        Expression<String> ex = root.get(field);
        return asc ? cb.asc(ex) : cb.desc(ex);
    }

    /**
     * Sort By allow to identifify
     *
     * @param sorts Map couple of field and sortway
     * @param cb criteria builder used from main process
     * @param data represent the entity class
     * @return a list of sort order
     */
    protected List<Order> sortBy(Map<String, String> sorts, CriteriaBuilder cb, Root<T> data) {
        // Case of empty sorting
        if (sorts == null || sorts.isEmpty()) {
            return null;
        }
        List<Order> orders = new ArrayList<>();
        for (Map.Entry<String, String> sort : sorts.entrySet()) {
            if (sort.getValue() != null || !sort.getValue().matches("UNSORTED")) {
                // Default Ascanding
                Boolean asc = sort.getValue().matches("ASCENDING");
                Expression<String> ex = data.get(sort.getKey());
                orders.add(asc ? cb.asc(ex) : cb.desc(ex));
            }
        }
        return orders;
    }

    /**
     * Filter from filters defined by user. <br>
     * Filter By only make a contain filter
     *
     * @param filters is a hasMap of key object and string data
     * @param cb is the owner criteria builder used in order to not create more
     * clause from
     * @param root current root data
     * @return a predicate attribute containing all where clause
     */
    protected List<Predicate> filterBy(Map<String, Object> filters, CriteriaBuilder cb, Root<T> root) {
        // null in case of empty filters
        if (filters == null || filters.isEmpty()) {
            return null;
        }
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> f : filters.entrySet()) {
            if (f.getValue() != null) {
                predicates.add(filtring(cb, root, f));
            }
        }
        return predicates;
    }

    /**
     * Read a part of date range method allow to read as string one of start or
     * end in a date range defined like below : <br>
     * "start":"2016-04-18","end":"2016-05-31" <br>
     *
     * @param dateRange a string date range defined like
     * "start":"2016-04-18","end":"2016-05-31"
     * @param isFrom if true specify from date otherwise to date
     * @return from or to date as string
     */
    protected String readPartOfDateRange(String dateRange, Boolean isFrom) {
        String filterText = (dateRange == null) ? null : dateRange.trim();
        if (filterText == null || filterText.equals("")) {
            return null;
        }

        if (!filterText.contains(",")) {
            return null;
        }

        // Remove all sequence exept ,
        String strDate = filterText.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");

        if (isFrom) {
            return dates[0];
        }
        return dates[1];
    }

    /**
     * 
     * @param cb
     * @param rt
     * @param field concerned field in the main process
     * @param code  corresponding to the code of the entities 
     * @param designation corresponding to the name field of the designation
     * @param id corresponding to the name field of id
     * @param value value to be liked
     * @return corresponding predected filter
     */
    protected Predicate likeFieldComposite(CriteriaBuilder cb, Root<T> rt, String field, String code, String designation, String id, String value){
         return cb.like(cb.concat(cb.concat(cb.concat(cb.concat(cb.concat(rt.get(field).get(code), " - "), rt.get(field).get(designation)), " ["), rt.get(field).get(id)), "]"), value);
    }
    
    protected Predicate likeFieldStaffComposite(CriteriaBuilder cb, Root<T> rt, String field, String value){
        return cb.like(cb.concat(cb.concat(cb.concat(cb.concat(cb.concat(cb.concat(cb.concat(rt.get(field).get("stFirstname"), " "), rt.get(field).get("stLastname")), " "), rt.get(field).get("stMiddlename")), " ["), rt.get(field).get("stStaff")), "]"), value); 
    }
    
    protected Predicate betweenFieldDate(CriteriaBuilder cb, Root<T> rt, String field, String value) {
        return cb.between(rt.get(field), readPartOfDateRange(value, true), readPartOfDateRange(value, false));
    }

    /**
     * Filtring allow to append condition to a predicate depending on field.
     * Main purpose of filtring is to implement entities conditions.
     *
     * @param cb is a criteria builder allowing to concat in this case
     * @param rt is the main data
     * @param filter couple of field filter and value of filter
     * @return a predicate of filter
     */
    protected Predicate filtring(CriteriaBuilder cb, Root<T> rt, Map.Entry<String, Object> filter) {
        return null;
    }

}
