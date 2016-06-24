package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.NonConformite;
import org.ism.entities.NonConformiteRequest;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-25T01:17:29")
@StaticMetamodel(NonConformiteFrequency.class)
public class NonConformiteFrequency_ { 

    public static volatile SingularAttribute<NonConformiteFrequency, Integer> ncfId;
    public static volatile SingularAttribute<NonConformiteFrequency, String> ncfDesignation;
    public static volatile SingularAttribute<NonConformiteFrequency, Company> ncfCompany;
    public static volatile SingularAttribute<NonConformiteFrequency, Date> ncfChanged;
    public static volatile SingularAttribute<NonConformiteFrequency, Date> ncfCreated;
    public static volatile SingularAttribute<NonConformiteFrequency, String> ncfFrequency;
    public static volatile SingularAttribute<NonConformiteFrequency, Boolean> ncfDeleted;
    public static volatile CollectionAttribute<NonConformiteFrequency, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile CollectionAttribute<NonConformiteFrequency, NonConformite> nonConformiteCollection;

}