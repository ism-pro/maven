package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-19T22:02:23")
@StaticMetamodel(StaffCompanies.class)
public class StaffCompanies_ { 

    public static volatile SingularAttribute<StaffCompanies, Integer> stcId;
    public static volatile SingularAttribute<StaffCompanies, Boolean> stcActivated;
    public static volatile SingularAttribute<StaffCompanies, Date> stcChanged;
    public static volatile SingularAttribute<StaffCompanies, Boolean> stcMain;
    public static volatile SingularAttribute<StaffCompanies, Date> stcCreated;
    public static volatile SingularAttribute<StaffCompanies, Staff> stcStaff;
    public static volatile SingularAttribute<StaffCompanies, Company> stcCompany;

}