package org.ism.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.IsmGenre;
import org.ism.entities.LabSample;
import org.ism.entities.NonConformiteActions;
import org.ism.entities.NonConformiteRequest;
import org.ism.entities.Processus;
import org.ism.entities.StaffCompanies;
import org.ism.entities.StaffGroups;
import org.ism.entities.StaffSetup;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-03T20:49:05")
@StaticMetamodel(Staff.class)
public class Staff_ { 

    public static volatile SingularAttribute<Staff, Date> stBorned;
    public static volatile CollectionAttribute<Staff, NonConformiteActions> nonConformiteActionsCollection1;
    public static volatile CollectionAttribute<Staff, LabSample> labSampleCollection;
    public static volatile SingularAttribute<Staff, String> stMiddlename;
    public static volatile CollectionAttribute<Staff, NonConformiteRequest> nonConformiteRequestCollection4;
    public static volatile CollectionAttribute<Staff, StaffGroups> staffGroupsCollection;
    public static volatile SingularAttribute<Staff, Integer> stId;
    public static volatile SingularAttribute<Staff, Date> stCreated;
    public static volatile SingularAttribute<Staff, String> stLastname;
    public static volatile CollectionAttribute<Staff, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<Staff, Boolean> stDeleted;
    public static volatile CollectionAttribute<Staff, StaffCompanies> staffCompaniesCollection;
    public static volatile SingularAttribute<Staff, Boolean> stActivated;
    public static volatile SingularAttribute<Staff, IsmGenre> stGenre;
    public static volatile SingularAttribute<Staff, String> stFirstname;
    public static volatile SingularAttribute<Staff, Integer> stMaxInactiveInterval;
    public static volatile SingularAttribute<Staff, String> stPassword;
    public static volatile CollectionAttribute<Staff, NonConformiteActions> nonConformiteActionsCollection;
    public static volatile SingularAttribute<Staff, String> stStaff;
    public static volatile SingularAttribute<Staff, Date> stChanged;
    public static volatile CollectionAttribute<Staff, Processus> processusCollection;
    public static volatile CollectionAttribute<Staff, StaffSetup> staffSetupCollection;

}