package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseCategory;
import org.ism.entities.LabAnalyseMethod;
import org.ism.entities.LabSample;
import org.ism.entities.LabSamplePtAnalyses;
import org.ism.entities.Unite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-19T12:21:46")
@StaticMetamodel(LabAnalyseType.class)
public class LabAnalyseType_ { 

    public static volatile SingularAttribute<LabAnalyseType, Date> latChanged;
    public static volatile SingularAttribute<LabAnalyseType, Integer> latId;
    public static volatile CollectionAttribute<LabAnalyseType, LabSample> labSampleCollection;
    public static volatile SingularAttribute<LabAnalyseType, String> latType;
    public static volatile SingularAttribute<LabAnalyseType, String> latDescription;
    public static volatile SingularAttribute<LabAnalyseType, Unite> latUnite;
    public static volatile SingularAttribute<LabAnalyseType, Boolean> latDeleted;
    public static volatile SingularAttribute<LabAnalyseType, LabAnalyseMethod> latMethod;
    public static volatile SingularAttribute<LabAnalyseType, Company> latCompany;
    public static volatile SingularAttribute<LabAnalyseType, Date> latCreated;
    public static volatile SingularAttribute<LabAnalyseType, String> latDesignation;
    public static volatile SingularAttribute<LabAnalyseType, LabAnalyseCategory> latCategory;
    public static volatile CollectionAttribute<LabAnalyseType, LabSamplePtAnalyses> labSamplePtAnalysesCollection;

}