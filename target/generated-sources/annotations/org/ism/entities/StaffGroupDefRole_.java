package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.IsmRole;
import org.ism.entities.StaffGroupDef;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-18T21:52:59")
@StaticMetamodel(StaffGroupDefRole.class)
public class StaffGroupDefRole_ { 

    public static volatile SingularAttribute<StaffGroupDefRole, Date> stgdrChanged;
    public static volatile SingularAttribute<StaffGroupDefRole, IsmRole> stgdrRole;
    public static volatile SingularAttribute<StaffGroupDefRole, Company> stgdrCompany;
    public static volatile SingularAttribute<StaffGroupDefRole, Integer> stgdrId;
    public static volatile SingularAttribute<StaffGroupDefRole, Date> stgdrCreated;
    public static volatile SingularAttribute<StaffGroupDefRole, Boolean> stgdrActivated;
    public static volatile SingularAttribute<StaffGroupDefRole, StaffGroupDef> stgdrGroupDef;

}