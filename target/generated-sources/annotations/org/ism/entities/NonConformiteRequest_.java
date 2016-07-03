package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.IsmNcrstate;
import org.ism.entities.NonConformiteActions;
import org.ism.entities.NonConformiteFrequency;
import org.ism.entities.NonConformiteGravity;
import org.ism.entities.NonConformiteNature;
import org.ism.entities.NonConformiteUnite;
import org.ism.entities.Processus;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-03T20:49:05")
@StaticMetamodel(NonConformiteRequest.class)
public class NonConformiteRequest_ { 

    public static volatile SingularAttribute<NonConformiteRequest, String> ncrProduct;
    public static volatile SingularAttribute<NonConformiteRequest, Integer> ncrId;
    public static volatile SingularAttribute<NonConformiteRequest, String> ncrapprouvedDesc;
    public static volatile SingularAttribute<NonConformiteRequest, Company> ncrCompany;
    public static volatile SingularAttribute<NonConformiteRequest, NonConformiteGravity> ncrGravity;
    public static volatile SingularAttribute<NonConformiteRequest, NonConformiteNature> ncrNature;
    public static volatile SingularAttribute<NonConformiteRequest, String> ncrDescription;
    public static volatile SingularAttribute<NonConformiteRequest, IsmNcrstate> ncrState;
    public static volatile SingularAttribute<NonConformiteRequest, String> ncrTrace;
    public static volatile SingularAttribute<NonConformiteRequest, Double> ncrQuantity;
    public static volatile SingularAttribute<NonConformiteRequest, String> ncrLink;
    public static volatile SingularAttribute<NonConformiteRequest, NonConformiteUnite> ncrUnite;
    public static volatile SingularAttribute<NonConformiteRequest, Date> ncrenddingAction;
    public static volatile SingularAttribute<NonConformiteRequest, Boolean> ncrApprouved;
    public static volatile SingularAttribute<NonConformiteRequest, Date> ncrapprouvedDate;
    public static volatile SingularAttribute<NonConformiteRequest, String> ncrTitle;
    public static volatile SingularAttribute<NonConformiteRequest, Date> ncrChanged;
    public static volatile CollectionAttribute<NonConformiteRequest, NonConformiteActions> nonConformiteActionsCollection;
    public static volatile SingularAttribute<NonConformiteRequest, Date> ncrCreated;
    public static volatile SingularAttribute<NonConformiteRequest, Staff> ncrStaff;
    public static volatile SingularAttribute<NonConformiteRequest, Processus> ncrProcessus;
    public static volatile SingularAttribute<NonConformiteRequest, Staff> ncrApprouver;
    public static volatile SingularAttribute<NonConformiteRequest, Date> ncrOccured;
    public static volatile SingularAttribute<NonConformiteRequest, NonConformiteFrequency> ncrFrequency;

}