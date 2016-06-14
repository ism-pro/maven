package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabSamplePt;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-14T22:52:42")
@StaticMetamodel(Equipement.class)
public class Equipement_ { 

    public static volatile SingularAttribute<Equipement, Integer> eId;
    public static volatile SingularAttribute<Equipement, String> eDescription;
    public static volatile SingularAttribute<Equipement, String> eResponsable;
    public static volatile SingularAttribute<Equipement, String> eCf;
    public static volatile SingularAttribute<Equipement, String> eEquipement;
    public static volatile SingularAttribute<Equipement, String> eFamille;
    public static volatile SingularAttribute<Equipement, Boolean> eDeleted;
    public static volatile SingularAttribute<Equipement, Company> eCompany;
    public static volatile SingularAttribute<Equipement, String> eGroupe;
    public static volatile SingularAttribute<Equipement, String> eSsFamille;
    public static volatile SingularAttribute<Equipement, Date> eChanged;
    public static volatile CollectionAttribute<Equipement, LabSamplePt> labSamplePtCollection;
    public static volatile SingularAttribute<Equipement, String> eDesignation;
    public static volatile SingularAttribute<Equipement, Date> eCreated;

}