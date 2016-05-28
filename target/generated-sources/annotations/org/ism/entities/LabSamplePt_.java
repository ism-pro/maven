package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Company;
import org.ism.entities.Equipement;
import org.ism.entities.LabSample;
import org.ism.entities.LabSamplePtAnalyses;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-28T01:33:13")
@StaticMetamodel(LabSamplePt.class)
public class LabSamplePt_ { 

    public static volatile SingularAttribute<LabSamplePt, Boolean> lspDeleted;
    public static volatile SingularAttribute<LabSamplePt, Integer> lspId;
    public static volatile SingularAttribute<LabSamplePt, String> lspSamplePt;
    public static volatile SingularAttribute<LabSamplePt, String> lspDesignation;
    public static volatile SingularAttribute<LabSamplePt, String> lspDescription;
    public static volatile CollectionAttribute<LabSamplePt, LabSample> labSampleCollection;
    public static volatile SingularAttribute<LabSamplePt, Equipement> lspEquipement;
    public static volatile SingularAttribute<LabSamplePt, Company> lspCompany;
    public static volatile CollectionAttribute<LabSamplePt, LabSamplePtAnalyses> labSamplePtAnalysesCollection;
    public static volatile SingularAttribute<LabSamplePt, Date> lspChanged;
    public static volatile SingularAttribute<LabSamplePt, Date> lspCreated;

}