package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.NonConformiteRequest;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-03T21:31:54")
@StaticMetamodel(NonConformiteUnite.class)
public class NonConformiteUnite_ { 

    public static volatile SingularAttribute<NonConformiteUnite, String> ncuUnite;
    public static volatile SingularAttribute<NonConformiteUnite, Company> ncuCompany;
    public static volatile SingularAttribute<NonConformiteUnite, Integer> ncuId;
    public static volatile SingularAttribute<NonConformiteUnite, Date> ncuChanged;
    public static volatile SingularAttribute<NonConformiteUnite, String> ncuDesignation;
    public static volatile SingularAttribute<NonConformiteUnite, Boolean> ncuDeleted;
    public static volatile CollectionAttribute<NonConformiteUnite, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<NonConformiteUnite, Date> ncuCreated;

}