package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.StaffGroupDefRole;
import org.ism.entities.StaffGroups;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-28T21:21:56")
@StaticMetamodel(StaffGroupDef.class)
public class StaffGroupDef_ { 

    public static volatile CollectionAttribute<StaffGroupDef, StaffGroupDefRole> staffGroupDefRoleCollection;
    public static volatile SingularAttribute<StaffGroupDef, Date> stgdCreated;
    public static volatile SingularAttribute<StaffGroupDef, Date> stgdChanged;
    public static volatile SingularAttribute<StaffGroupDef, Integer> stgdId;
    public static volatile CollectionAttribute<StaffGroupDef, StaffGroups> staffGroupsCollection;
    public static volatile SingularAttribute<StaffGroupDef, String> stgdGroupDef;
    public static volatile SingularAttribute<StaffGroupDef, String> stgdDesignation;
    public static volatile SingularAttribute<StaffGroupDef, Company> stgdCompany;
    public static volatile SingularAttribute<StaffGroupDef, Boolean> stgdDeleted;

}