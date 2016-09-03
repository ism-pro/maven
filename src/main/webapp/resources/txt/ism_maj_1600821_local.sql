USE ISM;

## REMOVE EXISTING LINK BETWEEN GROUPDEF AND ROLE
#######################################################################################
Truncate table staff_group_def_role;

## REMOVE EXISTING ROLE
#######################################################################################
Truncate table ism_role;

## ADD NEW ROLE
#######################################################################################
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('GUEST', 'Guest');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('STAFF', 'Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('ADMIN', 'Administration');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_COMPANY', 'Company');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_COMPANY_C', 'Create Company');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_COMPANY_E', 'Edit Company');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_COMPANY_L', 'List Company');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_COMPANY_V', 'View Company');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_ENTREPRISE', 'Entreprise');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_ENTREPRISE_C', 'Create Entreprise');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_ENTREPRISE_E', 'Edit Entreprise');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_ENTREPRISE_L', 'List Entreprise');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('A_ENTREPRISE_V', 'View Entreprise');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('APP', 'Application');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_GENRE', 'App. Genre');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_GENRE_C', 'Create App. Genre');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_GENRE_E', 'Edit App. Genre');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_GENRE_L', 'List App. Genre');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_GENRE_V', 'View App. Genre');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCSTATE', 'App. NC State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCSTATE_C', 'Create App. NC State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCSTATE_E', 'Edit App. NC State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCSTATE_L', 'List App. NC State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCSTATE_V', 'View App. NC State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCRSTATE', 'App. NCR State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCRSTATE_C', 'Create App. NCR State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCRSTATE_E', 'Edit App. NCR State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCRSTATE_L', 'List App. NCR State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_NCRSTATE_V', 'View App. NCR State');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_ROLE', 'App. Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_ROLE_C', 'Create App. Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_ROLE_E', 'Edit App. Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_ROLE_L', 'List App. Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('AP_ROLE_V', 'View App. Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HRESOURCES', 'Human Resources');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF', 'HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_C', 'Create HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_E', 'Edit HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_L', 'List HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_V', 'View HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_M', 'MainMenu HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_P', 'Profile HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_STAFF_S', 'Setup HR. Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUP', 'HR. Groups');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUP_C', 'Create HR. Group');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUP_E', 'Edit HR. Group');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUP_L', 'List HR. Groups');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUP_V', 'View HR. Group');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE', 'HR. Groups Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE_C', 'Create HR. Group Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE_E', 'Edit HR. Group Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE_L', 'List HR. Group Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE_V', 'View HR. Group Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPROLE_CT', 'Create by Tree HR. Group Role');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPSTAFF', 'HR. Groups staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPSTAFF_C', 'Create HR. Group Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPSTAFF_E', 'Edit HR. Group Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPSTAFF_L', 'List HR. Group Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_GROUPSTAFF_V', 'View HR. Group Staff');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_MANAGER', 'HR. Manager');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_MANAGER_C', 'Create HR. Manager');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_MANAGER_E', 'Edit HR. Manager');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_MANAGER_L', 'List HR. Manager');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_MANAGER_V', 'View HR. Manager');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP', 'HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP_C', 'Create HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP_E', 'Edit HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP_L', 'List HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP_V', 'View HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('HR_SETUP_M', 'My Setup HR. Setup');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SMQ', 'Sys. Management Qualité');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_EXPLORER', 'SMQ Documents');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_EXPLORER_C', 'Create SMQ Documents');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_EXPLORER_E', 'Edit SMQ Documents');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_EXPLORER_L', 'List SMQ Documents');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_EXPLORER_V', 'View SMQ Documents');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_TYPE', 'SMQ Type Document');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_TYPE_C', 'Create SMQ Type Document');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_TYPE_E', 'Edit SMQ Type Document');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_TYPE_L', 'List SMQ Type Document');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_TYPE_V', 'View SMQ Type Document');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC', 'SMQ Non Conformite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_ACTIONS', 'SMQ Actions');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_ACTIONS_C', 'Create SMQ Actions');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_ACTIONS_E', 'Edit SMQ Actions');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_ACTIONS_L', 'List SMQ Actions');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_ACTIONS_V', 'View SMQ Actions');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_FREQUENCE', 'SMQ Frequence');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_FREQUENCE_C', 'Create SMQ Frequence');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_FREQUENCE_E', 'Edit SMQ Frequence');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_FREQUENCE_L', 'List SMQ Frequence');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_FREQUENCE_V', 'View SMQ Frequence');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_GRAVITE', 'SMQ Gravite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_GRAVITE_C', 'Create SMQ Gravite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_GRAVITE_E', 'Edit SMQ Gravite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_GRAVITE_L', 'List SMQ Gravite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_GRAVITE_V', 'View SMQ Gravite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_NATURE', 'SMQ Nature');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_NATURE_C', 'Create SMQ Nature');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_NATURE_E', 'Edit SMQ Nature');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_NATURE_L', 'List SMQ Nature');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_NATURE_V', 'View SMQ Nature');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST', 'SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_C', 'Create SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_E', 'Edit SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_L', 'List SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_V', 'View SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_A', 'Action SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_R', 'Review SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_REQUEST_VA', 'Validate SMQ Request');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_UNITE', 'SMQ Unite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_UNITE_C', 'Create SMQ Unite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_UNITE_E', 'Edit SMQ Unite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_UNITE_L', 'List SMQ Unite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('SNC_UNITE_V', 'View SMQ Unite');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_PROCESSUS', 'SMQ processus');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_PROCESSUS_C', 'Create SMQ processus');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_PROCESSUS_E', 'Edit SMQ processus');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_PROCESSUS_L', 'List SMQ processus');
INSERT INTO `ism`.`ism_role` (`role`, `rolename`) VALUES ('S_PROCESSUS_V', 'View SMQ processus');


## MANAGED GROUPE DEF
#######################################################################################
INSERT INTO `ism`.`staff_group_def` (`stgd_company`, `stgd_group_def`, `stgd_designation`, `stgd_deleted`, `stgd_created`) 
	SELECT * FROM (SELECT '39', 'ADMIN', 'Administrateur', 0, NOW()) AS tmp
	WHERE NOT EXISTS (
		SELECT stgd_group_def FROM `ism`.`staff_group_def` WHERE stgd_group_def='ADMIN' and stgd_company='39'
)LIMIT 1;

INSERT INTO `ism`.`staff_group_def` (`stgd_company`, `stgd_group_def`, `stgd_designation`, `stgd_deleted`, `stgd_created`) 
	SELECT * FROM (SELECT '39', 'GOUROU', 'Master access', 0, NOW()) AS tmp
	WHERE NOT EXISTS (
		SELECT stgd_group_def FROM `ism`.`staff_group_def` WHERE stgd_group_def='GOUROU' and stgd_company='39'
)LIMIT 1; 



## CONNECTED NEW ROLE TO DEFAULT ADMIN USER
#######################################################################################
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'GUEST', 1, NOW()),('39', 'ADMIN', 'GUEST', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'STAFF', 1, NOW()),('39', 'ADMIN', 'STAFF', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'ADMIN', 1, NOW()),('39', 'ADMIN', 'ADMIN', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_COMPANY', 1, NOW()),('39', 'ADMIN', 'A_COMPANY', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_COMPANY_C', 1, NOW()),('39', 'ADMIN', 'A_COMPANY_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_COMPANY_E', 1, NOW()),('39', 'ADMIN', 'A_COMPANY_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_COMPANY_L', 1, NOW()),('39', 'ADMIN', 'A_COMPANY_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_COMPANY_V', 1, NOW()),('39', 'ADMIN', 'A_COMPANY_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_ENTREPRISE', 1, NOW()),('39', 'ADMIN', 'A_ENTREPRISE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_ENTREPRISE_C', 1, NOW()),('39', 'ADMIN', 'A_ENTREPRISE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_ENTREPRISE_E', 1, NOW()),('39', 'ADMIN', 'A_ENTREPRISE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_ENTREPRISE_L', 1, NOW()),('39', 'ADMIN', 'A_ENTREPRISE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'A_ENTREPRISE_V', 1, NOW()),('39', 'ADMIN', 'A_ENTREPRISE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'APP', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_GENRE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_GENRE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_GENRE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_GENRE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_GENRE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCSTATE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCSTATE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCSTATE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCSTATE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCSTATE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCRSTATE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCRSTATE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCRSTATE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCRSTATE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_NCRSTATE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_ROLE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_ROLE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_ROLE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_ROLE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'AP_ROLE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HRESOURCES', 1, NOW()),('39', 'ADMIN', 'HRESOURCES', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF', 1, NOW()),('39', 'ADMIN', 'HR_STAFF', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_C', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_E', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_L', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_V', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_M', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_M', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_P', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_P', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_STAFF_S', 1, NOW()),('39', 'ADMIN', 'HR_STAFF_S', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUP', 1, NOW()),('39', 'ADMIN', 'HR_GROUP', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUP_C', 1, NOW()),('39', 'ADMIN', 'HR_GROUP_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUP_E', 1, NOW()),('39', 'ADMIN', 'HR_GROUP_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUP_L', 1, NOW()),('39', 'ADMIN', 'HR_GROUP_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUP_V', 1, NOW()),('39', 'ADMIN', 'HR_GROUP_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE_C', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE_E', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE_L', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE_V', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPROLE_CT', 1, NOW()),('39', 'ADMIN', 'HR_GROUPROLE_CT', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPSTAFF', 1, NOW()),('39', 'ADMIN', 'HR_GROUPSTAFF', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPSTAFF_C', 1, NOW()),('39', 'ADMIN', 'HR_GROUPSTAFF_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPSTAFF_E', 1, NOW()),('39', 'ADMIN', 'HR_GROUPSTAFF_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPSTAFF_L', 1, NOW()),('39', 'ADMIN', 'HR_GROUPSTAFF_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_GROUPSTAFF_V', 1, NOW()),('39', 'ADMIN', 'HR_GROUPSTAFF_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_MANAGER', 1, NOW()),('39', 'ADMIN', 'HR_MANAGER', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_MANAGER_C', 1, NOW()),('39', 'ADMIN', 'HR_MANAGER_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_MANAGER_E', 1, NOW()),('39', 'ADMIN', 'HR_MANAGER_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_MANAGER_L', 1, NOW()),('39', 'ADMIN', 'HR_MANAGER_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_MANAGER_V', 1, NOW()),('39', 'ADMIN', 'HR_MANAGER_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP', 1, NOW()),('39', 'ADMIN', 'HR_SETUP', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP_C', 1, NOW()),('39', 'ADMIN', 'HR_SETUP_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP_E', 1, NOW()),('39', 'ADMIN', 'HR_SETUP_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP_L', 1, NOW()),('39', 'ADMIN', 'HR_SETUP_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP_V', 1, NOW()),('39', 'ADMIN', 'HR_SETUP_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'HR_SETUP_M', 1, NOW()),('39', 'ADMIN', 'HR_SETUP_M', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SMQ', 1, NOW()),('39', 'ADMIN', 'SMQ', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_EXPLORER', 1, NOW()),('39', 'ADMIN', 'S_EXPLORER', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_EXPLORER_C', 1, NOW()),('39', 'ADMIN', 'S_EXPLORER_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_EXPLORER_E', 1, NOW()),('39', 'ADMIN', 'S_EXPLORER_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_EXPLORER_L', 1, NOW()),('39', 'ADMIN', 'S_EXPLORER_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_EXPLORER_V', 1, NOW()),('39', 'ADMIN', 'S_EXPLORER_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_TYPE', 1, NOW()),('39', 'ADMIN', 'S_TYPE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_TYPE_C', 1, NOW()),('39', 'ADMIN', 'S_TYPE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_TYPE_E', 1, NOW()),('39', 'ADMIN', 'S_TYPE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_TYPE_L', 1, NOW()),('39', 'ADMIN', 'S_TYPE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_TYPE_V', 1, NOW()),('39', 'ADMIN', 'S_TYPE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC', 1, NOW()),('39', 'ADMIN', 'SNC', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_ACTIONS', 1, NOW()),('39', 'ADMIN', 'SNC_ACTIONS', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_ACTIONS_C', 1, NOW()),('39', 'ADMIN', 'SNC_ACTIONS_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_ACTIONS_E', 1, NOW()),('39', 'ADMIN', 'SNC_ACTIONS_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_ACTIONS_L', 1, NOW()),('39', 'ADMIN', 'SNC_ACTIONS_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_ACTIONS_V', 1, NOW()),('39', 'ADMIN', 'SNC_ACTIONS_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_FREQUENCE', 1, NOW()),('39', 'ADMIN', 'SNC_FREQUENCE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_FREQUENCE_C', 1, NOW()),('39', 'ADMIN', 'SNC_FREQUENCE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_FREQUENCE_E', 1, NOW()),('39', 'ADMIN', 'SNC_FREQUENCE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_FREQUENCE_L', 1, NOW()),('39', 'ADMIN', 'SNC_FREQUENCE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_FREQUENCE_V', 1, NOW()),('39', 'ADMIN', 'SNC_FREQUENCE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_GRAVITE', 1, NOW()),('39', 'ADMIN', 'SNC_GRAVITE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_GRAVITE_C', 1, NOW()),('39', 'ADMIN', 'SNC_GRAVITE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_GRAVITE_E', 1, NOW()),('39', 'ADMIN', 'SNC_GRAVITE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_GRAVITE_L', 1, NOW()),('39', 'ADMIN', 'SNC_GRAVITE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_GRAVITE_V', 1, NOW()),('39', 'ADMIN', 'SNC_GRAVITE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_NATURE', 1, NOW()),('39', 'ADMIN', 'SNC_NATURE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_NATURE_C', 1, NOW()),('39', 'ADMIN', 'SNC_NATURE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_NATURE_E', 1, NOW()),('39', 'ADMIN', 'SNC_NATURE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_NATURE_L', 1, NOW()),('39', 'ADMIN', 'SNC_NATURE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_NATURE_V', 1, NOW()),('39', 'ADMIN', 'SNC_NATURE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_C', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_E', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_L', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_V', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_A', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_A', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_R', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_R', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_REQUEST_VA', 1, NOW()),('39', 'ADMIN', 'SNC_REQUEST_VA', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_UNITE', 1, NOW()),('39', 'ADMIN', 'SNC_UNITE', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_UNITE_C', 1, NOW()),('39', 'ADMIN', 'SNC_UNITE_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_UNITE_E', 1, NOW()),('39', 'ADMIN', 'SNC_UNITE_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_UNITE_L', 1, NOW()),('39', 'ADMIN', 'SNC_UNITE_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'SNC_UNITE_V', 1, NOW()),('39', 'ADMIN', 'SNC_UNITE_V', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_PROCESSUS', 1, NOW()),('39', 'ADMIN', 'S_PROCESSUS', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_PROCESSUS_C', 1, NOW()),('39', 'ADMIN', 'S_PROCESSUS_C', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_PROCESSUS_E', 1, NOW()),('39', 'ADMIN', 'S_PROCESSUS_E', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_PROCESSUS_L', 1, NOW()),('39', 'ADMIN', 'S_PROCESSUS_L', 1, NOW());
INSERT INTO `ism`.`staff_group_def_role` (`stgdr_company`, `stgdr_group_def`, `stgdr_role`, `stgdr_activated`, `stgdr_created`) VALUES ('39', 'GOUROU', 'S_PROCESSUS_V', 1, NOW()),('39', 'ADMIN', 'S_PROCESSUS_V', 1, NOW());

# SUPPRESSION DE LA TABLE DE LIAISON STAFF A SOCIETE
DROP TABLE IF EXISTS `ism`.`staff_companies`;

# CHANGE viewLogin
USE `ism`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `stafflogin` AS
    SELECT 
        `staff`.`st_id` AS `st_id`,
        `staff_group_def_role`.`stgdr_company` AS `stgdr_company`,
        `staff`.`st_staff` AS `st_staff`,
        `staff`.`st_password` AS `st_password`,
        `staff_groups`.`stg_group_def` AS `stg_group_def`,
        `staff_group_def_role`.`stgdr_role` AS `stgdr_role`,
        `staff`.`st_activated` AS `st_activated`
    FROM
        ((`staff`
        JOIN `staff_groups`)
        JOIN `staff_group_def_role`)
    WHERE
        ((`staff_groups`.`stg_staff` = `staff`.`st_staff`) );

