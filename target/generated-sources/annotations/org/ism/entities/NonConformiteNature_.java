package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.NonConformiteRequest;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-17T07:17:53")
@StaticMetamodel(NonConformiteNature.class)
public class NonConformiteNature_ { 

    public static volatile SingularAttribute<NonConformiteNature, String> ncnNature;
    public static volatile SingularAttribute<NonConformiteNature, Date> ncnChanged;
    public static volatile SingularAttribute<NonConformiteNature, Date> ncnCreated;
    public static volatile SingularAttribute<NonConformiteNature, Company> ncnCompany;
    public static volatile SingularAttribute<NonConformiteNature, Integer> ncnId;
    public static volatile SingularAttribute<NonConformiteNature, Boolean> ncnDeleted;
    public static volatile CollectionAttribute<NonConformiteNature, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<NonConformiteNature, String> ncnDesignation;

}