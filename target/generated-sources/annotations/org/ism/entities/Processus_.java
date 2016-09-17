package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.DocExplorer;
import org.ism.entities.NonConformiteRequest;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-17T14:49:26")
@StaticMetamodel(Processus.class)
public class Processus_ { 

    public static volatile SingularAttribute<Processus, Date> pCreated;
    public static volatile SingularAttribute<Processus, Date> pChanged;
    public static volatile SingularAttribute<Processus, Staff> pStaffmanager;
    public static volatile SingularAttribute<Processus, String> pDesignation;
    public static volatile CollectionAttribute<Processus, DocExplorer> docExplorerCollection;
    public static volatile SingularAttribute<Processus, Integer> pId;
    public static volatile CollectionAttribute<Processus, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<Processus, Company> pCompany;
    public static volatile SingularAttribute<Processus, String> pProcessus;
    public static volatile SingularAttribute<Processus, Boolean> pDeleted;

}