package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.LabAnalyseType;
import org.ism.entities.LabSamplePt;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-16T21:32:01")
@StaticMetamodel(LabSamplePtAnalyses.class)
public class LabSamplePtAnalyses_ { 

    public static volatile SingularAttribute<LabSamplePtAnalyses, Date> lspaCreated;
    public static volatile SingularAttribute<LabSamplePtAnalyses, Date> lspaChanged;
    public static volatile SingularAttribute<LabSamplePtAnalyses, Integer> lspaId;
    public static volatile SingularAttribute<LabSamplePtAnalyses, LabAnalyseType> lspaType;
    public static volatile SingularAttribute<LabSamplePtAnalyses, String> lspaObservation;
    public static volatile SingularAttribute<LabSamplePtAnalyses, Company> lspaCompany;
    public static volatile SingularAttribute<LabSamplePtAnalyses, LabSamplePt> lspaSamplePt;
    public static volatile SingularAttribute<LabSamplePtAnalyses, Boolean> lspaDeleted;

}