package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.DocType;
import org.ism.entities.Processus;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-31T14:08:53")
@StaticMetamodel(DocExplorer.class)
public class DocExplorer_ { 

    public static volatile SingularAttribute<DocExplorer, String> dcDesignation;
    public static volatile SingularAttribute<DocExplorer, Company> dcCompany;
    public static volatile SingularAttribute<DocExplorer, String> dcLink;
    public static volatile SingularAttribute<DocExplorer, Processus> dcProcessus;
    public static volatile SingularAttribute<DocExplorer, String> dcVersion;
    public static volatile SingularAttribute<DocExplorer, Integer> dcId;
    public static volatile SingularAttribute<DocExplorer, Date> dcChanged;
    public static volatile SingularAttribute<DocExplorer, DocType> dcType;
    public static volatile SingularAttribute<DocExplorer, Date> dcApprouved;
    public static volatile SingularAttribute<DocExplorer, String> dcComment;
    public static volatile SingularAttribute<DocExplorer, Boolean> dcActivated;
    public static volatile SingularAttribute<DocExplorer, Date> dcCreated;

}