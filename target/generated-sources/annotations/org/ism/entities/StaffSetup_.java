package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-17T00:04:55")
@StaticMetamodel(StaffSetup.class)
public class StaffSetup_ { 

    public static volatile SingularAttribute<StaffSetup, String> stsTheme;
    public static volatile SingularAttribute<StaffSetup, Staff> stsStaff;
    public static volatile SingularAttribute<StaffSetup, Integer> ststimeOutSession;
    public static volatile SingularAttribute<StaffSetup, Integer> stsmenuIndex;
    public static volatile SingularAttribute<StaffSetup, Integer> stsId;
    public static volatile SingularAttribute<StaffSetup, Date> stsCreated;
    public static volatile SingularAttribute<StaffSetup, Date> stsChanged;

}