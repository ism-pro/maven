package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.IsmNcrstate;
import org.ism.entities.NonConformiteFrequency;
import org.ism.entities.NonConformiteGravity;
import org.ism.entities.NonConformiteNature;
import org.ism.entities.NonConformiteUnite;
import org.ism.entities.Processus;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-31T23:40:44")
@StaticMetamodel(NonConformite.class)
public class NonConformite_ { 

    public static volatile SingularAttribute<NonConformite, String> ncTrace;
    public static volatile SingularAttribute<NonConformite, Integer> ncId;
    public static volatile SingularAttribute<NonConformite, String> ncLink;
    public static volatile SingularAttribute<NonConformite, Processus> ncProcessus;
    public static volatile SingularAttribute<NonConformite, Date> ncDelay;
    public static volatile SingularAttribute<NonConformite, Double> ncQuantity;
    public static volatile SingularAttribute<NonConformite, String> ncTitle;
    public static volatile SingularAttribute<NonConformite, NonConformiteNature> ncNature;
    public static volatile SingularAttribute<NonConformite, Staff> ncStaff;
    public static volatile SingularAttribute<NonConformite, Date> ncCreated;
    public static volatile SingularAttribute<NonConformite, NonConformiteUnite> ncUnite;
    public static volatile SingularAttribute<NonConformite, Company> ncCompany;
    public static volatile SingularAttribute<NonConformite, Date> ncOccured;
    public static volatile SingularAttribute<NonConformite, NonConformiteGravity> ncGravity;
    public static volatile SingularAttribute<NonConformite, String> ncAction;
    public static volatile SingularAttribute<NonConformite, NonConformiteFrequency> ncFrequency;
    public static volatile SingularAttribute<NonConformite, String> ncProduct;
    public static volatile SingularAttribute<NonConformite, String> ncDescription;
    public static volatile SingularAttribute<NonConformite, IsmNcrstate> ncState;
    public static volatile SingularAttribute<NonConformite, Date> ncChanged;

}