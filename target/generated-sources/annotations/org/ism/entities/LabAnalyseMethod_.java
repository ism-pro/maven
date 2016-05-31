package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-31T23:40:44")
@StaticMetamodel(LabAnalyseMethod.class)
public class LabAnalyseMethod_ { 

    public static volatile SingularAttribute<LabAnalyseMethod, Date> lamCreated;
    public static volatile SingularAttribute<LabAnalyseMethod, Date> lamChanged;
    public static volatile CollectionAttribute<LabAnalyseMethod, LabAnalyseType> labAnalyseTypeCollection;
    public static volatile SingularAttribute<LabAnalyseMethod, String> lamMethod;
    public static volatile SingularAttribute<LabAnalyseMethod, Integer> lamId;
    public static volatile SingularAttribute<LabAnalyseMethod, Company> lamCompany;
    public static volatile SingularAttribute<LabAnalyseMethod, String> lamDescription;
    public static volatile SingularAttribute<LabAnalyseMethod, Boolean> lamDeleted;
    public static volatile SingularAttribute<LabAnalyseMethod, String> lamDesignation;

}