package org.ism.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.StaffGroupDefRole;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-18T07:41:03")
@StaticMetamodel(IsmRole.class)
public class IsmRole_ { 

    public static volatile CollectionAttribute<IsmRole, StaffGroupDefRole> staffGroupDefRoleCollection;
    public static volatile SingularAttribute<IsmRole, String> role;
    public static volatile SingularAttribute<IsmRole, String> rolename;
    public static volatile SingularAttribute<IsmRole, Integer> id;

}