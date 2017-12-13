/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions.process.ctrl;

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
        //int count = q.getResultList().size();
        if (!q.getResultList().isEmpty()) {
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
     * String src_01 = "AnalyseDataField_adId";<br>
     * String src_02 = "AnalyseDataField_adPoint";<br>
     * String src_03 = "AnalyseDataField_adType";<br>
     * String src_04 = "AnalyseDataField_adValue";<br>
     * String src_05 = "AnalyseDataField_adValueT";<br>
     * String src_06 = "AnalyseDataField_adSampler";<br>
     * String src_07 = "AnalyseDataField_adValidate";<br>
     * String src_08 = "AnalyseDataField_adValidator";<br>
     * String src_09 = "AnalyseDataField_adenlimitHH";<br>
     * String src_10 = "AnalyseDataField_adlimitHH";<br>
     * String src_11 = "AnalyseDataField_adenlimitH";<br>
     * String src_12 = "AnalyseDataField_adlimitH";<br>
     * String src_13 = "AnalyseDataField_adenlimitB";<br>
     * String src_14 = "AnalyseDataField_adlimitB";<br>
     * String src_15 = "AnalyseDataField_adenlimitBB";<br>
     * String src_16 = "AnalyseDataField_adlimitBB";<br>
     * String src_17 = "AnalyseDataField_adBatch";<br>
     * String src_18 = "AnalyseDataField_adsampleTime";<br>
     * String src_19 = "AnalyseDataField_adObservation";<br>
     * String src_20 = "AnalyseDataField_adDeleted";<br>
     * String src_21 = "AnalyseDataField_adCreated";<br>
     * String src_22 = "AnalyseDataField_adChanged";<br>
     * String src_23 = "AnalyseDataField_adCompany";<br>
     *
     * @param cb is a criteria builder allowing to concat in this case
     * @param rt is the main data
     * @param filter couple of field filter and value of filter
     * @return a predicate of filter
     */
    @Override
    public Predicate filtring(CriteriaBuilder cb, Root<AnalyseData> rt, Map.Entry<String, Object> filter) {
        Expression<String> expr;
        final String field = filter.getKey();
        final String value = filter.getValue().toString(); //"%" + f.getValue() + "%";
        Predicate p = null;
        switch (field) {
            case "adPoint":
                p = likeFieldComposite(cb, rt, field, "apPoint", "apDesignation", "apId", value);
                break;
            case "adType":
                p = likeFieldComposite(cb, rt, field, "atType", "atDesignation", "atId", value);
                break;
            case "adSampler":
                p = likeFieldStaffComposite(cb, rt, field, value);
                break;
            case "adValidator":
                p = likeFieldStaffComposite(cb, rt, field, value);
                break;
            case "adsampleTime":
                p = betweenFieldDate(cb, rt, field, value);
                break;
            case "adCreated":
                p = betweenFieldDate(cb, rt, field, value);
                break;
            case "adChanged":
                p = betweenFieldDate(cb, rt, field, value);
                break;
            case "adCompany":
                p = likeFieldComposite(cb, rt, field, "cCompany", "cDesignation", "cId", value);
                break;
            default:
                expr = rt.get(field);
                if (filter.getValue() instanceof Boolean) {
                    p = cb.equal(expr, value);
                } else {
                    p = cb.like(expr, value);
                }
                break;
        }
        return p;
    }

}
