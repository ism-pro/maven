package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.NonConformite;
import org.ism.entities.NonConformiteRequest;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-22T14:57:51")
@StaticMetamodel(NonConformiteGravity.class)
public class NonConformiteGravity_ { 

    public static volatile SingularAttribute<NonConformiteGravity, Date> ncgChanged;
    public static volatile SingularAttribute<NonConformiteGravity, Integer> ncgId;
    public static volatile SingularAttribute<NonConformiteGravity, String> ncgGravity;
    public static volatile SingularAttribute<NonConformiteGravity, Date> ncgCreated;
    public static volatile SingularAttribute<NonConformiteGravity, Company> ncgCompany;
    public static volatile SingularAttribute<NonConformiteGravity, Boolean> ncgDeleted;
    public static volatile CollectionAttribute<NonConformiteGravity, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<NonConformiteGravity, String> ncgDesignation;
    public static volatile CollectionAttribute<NonConformiteGravity, NonConformite> nonConformiteCollection;

}