package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-16T21:32:01")
@StaticMetamodel(Entreprise.class)
public class Entreprise_ { 

    public static volatile SingularAttribute<Entreprise, Integer> eId;
    public static volatile CollectionAttribute<Entreprise, Company> companyCollection;
    public static volatile SingularAttribute<Entreprise, String> eEntreprise;
    public static volatile SingularAttribute<Entreprise, Date> eChanged;
    public static volatile SingularAttribute<Entreprise, Boolean> eActivated;
    public static volatile SingularAttribute<Entreprise, Integer> eBuilded;
    public static volatile SingularAttribute<Entreprise, Boolean> eMain;
    public static volatile SingularAttribute<Entreprise, Boolean> eDeleted;
    public static volatile SingularAttribute<Entreprise, String> eDesignation;
    public static volatile SingularAttribute<Entreprise, Date> eCreated;

}