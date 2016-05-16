package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-16T18:01:42")
@StaticMetamodel(Unite.class)
public class Unite_ { 

    public static volatile SingularAttribute<Unite, Integer> uId;
    public static volatile SingularAttribute<Unite, Company> uCompany;
    public static volatile CollectionAttribute<Unite, LabAnalyseType> labAnalyseTypeCollection;
    public static volatile SingularAttribute<Unite, Date> uChanged;
    public static volatile SingularAttribute<Unite, Date> uCreated;
    public static volatile SingularAttribute<Unite, String> uUnite;
    public static volatile SingularAttribute<Unite, String> uDesignation;
    public static volatile SingularAttribute<Unite, Boolean> uDeleted;

}