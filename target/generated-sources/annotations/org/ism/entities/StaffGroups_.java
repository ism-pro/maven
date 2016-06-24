package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.Staff;
import org.ism.entities.StaffGroupDef;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-25T01:17:29")
@StaticMetamodel(StaffGroups.class)
public class StaffGroups_ { 

    public static volatile SingularAttribute<StaffGroups, Boolean> stgActivated;
    public static volatile SingularAttribute<StaffGroups, Date> stgCreated;
    public static volatile SingularAttribute<StaffGroups, Date> stgChanged;
    public static volatile SingularAttribute<StaffGroups, StaffGroupDef> stgGroupDef;
    public static volatile SingularAttribute<StaffGroups, Integer> stgId;
    public static volatile SingularAttribute<StaffGroups, Staff> stgStaff;
    public static volatile SingularAttribute<StaffGroups, Company> stgCompany;

}