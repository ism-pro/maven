package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-28T01:33:13")
@StaticMetamodel(LabAnalyseCategory.class)
public class LabAnalyseCategory_ { 

    public static volatile CollectionAttribute<LabAnalyseCategory, LabAnalyseType> labAnalyseTypeCollection;
    public static volatile SingularAttribute<LabAnalyseCategory, Company> lacCompany;
    public static volatile SingularAttribute<LabAnalyseCategory, Date> lacChanged;
    public static volatile SingularAttribute<LabAnalyseCategory, Integer> lacId;
    public static volatile SingularAttribute<LabAnalyseCategory, String> lacDescription;
    public static volatile SingularAttribute<LabAnalyseCategory, String> lacDesignation;
    public static volatile SingularAttribute<LabAnalyseCategory, Boolean> lacDeleted;
    public static volatile SingularAttribute<LabAnalyseCategory, String> lacCategory;
    public static volatile SingularAttribute<LabAnalyseCategory, Date> lacCreated;

}