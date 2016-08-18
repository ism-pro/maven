package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Entreprise;
import org.ism.entities.Equipement;
import org.ism.entities.LabAnalyseCategory;
import org.ism.entities.LabAnalyseMethod;
import org.ism.entities.LabAnalyseType;
import org.ism.entities.LabSamplePt;
import org.ism.entities.LabSamplePtAnalyses;
import org.ism.entities.NonConformiteFrequency;
import org.ism.entities.NonConformiteGravity;
import org.ism.entities.NonConformiteNature;
import org.ism.entities.NonConformiteRequest;
import org.ism.entities.NonConformiteUnite;
import org.ism.entities.Processus;
import org.ism.entities.Unite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-17T07:17:53")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, Boolean> cDeleted;
    public static volatile CollectionAttribute<Company, NonConformiteNature> nonConformiteNatureCollection;
    public static volatile CollectionAttribute<Company, LabAnalyseMethod> labAnalyseMethodCollection;
    public static volatile SingularAttribute<Company, Boolean> cActivated;
    public static volatile SingularAttribute<Company, Date> cChanged;
    public static volatile CollectionAttribute<Company, NonConformiteFrequency> nonConformiteFrequencyCollection;
    public static volatile SingularAttribute<Company, Boolean> cMain;
    public static volatile CollectionAttribute<Company, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile CollectionAttribute<Company, NonConformiteUnite> nonConformiteUniteCollection;
    public static volatile CollectionAttribute<Company, Unite> uniteCollection;
    public static volatile SingularAttribute<Company, String> cCompany;
    public static volatile CollectionAttribute<Company, LabAnalyseType> labAnalyseTypeCollection;
    public static volatile SingularAttribute<Company, Entreprise> cEntreprise;
    public static volatile CollectionAttribute<Company, NonConformiteGravity> nonConformiteGravityCollection;
    public static volatile CollectionAttribute<Company, LabAnalyseCategory> labAnalyseCategoryCollection;
    public static volatile CollectionAttribute<Company, Equipement> equipementCollection;
    public static volatile SingularAttribute<Company, Integer> cBuilded;
    public static volatile CollectionAttribute<Company, LabSamplePtAnalyses> labSamplePtAnalysesCollection;
    public static volatile CollectionAttribute<Company, LabSamplePt> labSamplePtCollection;
    public static volatile SingularAttribute<Company, String> cDesignation;
    public static volatile SingularAttribute<Company, Date> cCreated;
    public static volatile CollectionAttribute<Company, Processus> processusCollection;
    public static volatile SingularAttribute<Company, Integer> cId;

}