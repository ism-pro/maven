package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.DocExplorer;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-18T07:41:03")
@StaticMetamodel(DocType.class)
public class DocType_ { 

    public static volatile SingularAttribute<DocType, String> dctDesignation;
    public static volatile SingularAttribute<DocType, Date> dctCreated;
    public static volatile SingularAttribute<DocType, String> dctType;
    public static volatile SingularAttribute<DocType, Boolean> dctDeleted;
    public static volatile SingularAttribute<DocType, Company> dctCompany;
    public static volatile CollectionAttribute<DocType, DocExplorer> docExplorerCollection;
    public static volatile SingularAttribute<DocType, Date> dctChanged;
    public static volatile SingularAttribute<DocType, Integer> dctId;

}