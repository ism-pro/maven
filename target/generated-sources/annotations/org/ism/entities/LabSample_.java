package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseType;
import org.ism.entities.LabSamplePt;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-15T23:36:35")
@StaticMetamodel(LabSample.class)
public class LabSample_ { 

    public static volatile SingularAttribute<LabSample, Boolean> lsValidate;
    public static volatile SingularAttribute<LabSample, Integer> lsId;
    public static volatile SingularAttribute<LabSample, LabSamplePt> lsSamplePt;
    public static volatile SingularAttribute<LabSample, Company> lsCompany;
    public static volatile SingularAttribute<LabSample, Date> lsChanged;
    public static volatile SingularAttribute<LabSample, Staff> lsSampler;
    public static volatile SingularAttribute<LabSample, LabAnalyseType> lsType;
    public static volatile SingularAttribute<LabSample, Boolean> lsDeleted;
    public static volatile SingularAttribute<LabSample, Date> lsCreated;
    public static volatile SingularAttribute<LabSample, String> lsValueT;
    public static volatile SingularAttribute<LabSample, Double> lsValue;
    public static volatile SingularAttribute<LabSample, String> lsObservation;

}