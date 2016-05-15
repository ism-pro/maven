
#####################################################################################
#####################################################################################
###	CREATION DE LA BASE DE DONNEE
#####################################################################################
#####################################################################################
###
### DROP EXISTING SCHEMA BEFORE CREATING A NEW ONE
### DROP SCHEMA ISM;
DROP SCHEMA ISM;
CREATE DATABASE IF NOT EXISTS `ISM` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `ISM`;


#####################################################################################
#####################################################################################
###	ISM DEFAULT DATA
#####################################################################################
#####################################################################################

#####
### ISM
####################################################################
CREATE TABLE IF NOT EXISTS ism_genre (
  id 			INT NOT NULL AUTO_INCREMENT,
  genre 		VARCHAR(10) NOT NULL,
  genrename 	VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  INDEX ui_idgenre (genre ASC, id ASC))
COMMENT = 'Existing genre for ism app';


CREATE TABLE IF NOT EXISTS ism_role (
  id 		INT NOT NULL AUTO_INCREMENT,
  role 		VARCHAR(45) NOT NULL,
  rolename 	VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  INDEX ui_idrole (role ASC, id ASC))
COMMENT = 'Existing role for ism app';


CREATE TABLE IF NOT EXISTS ism_ncrstate (
  id 			INT NOT NULL AUTO_INCREMENT,
  istate		VARCHAR(45) NOT NULL,
  statename		VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  INDEX ui_idncrstate (istate ASC, statename ASC))
COMMENT = 'Existing non conformity request state for ism app';


#####################################################################################
#####################################################################################
###	GESTION ENTREPRISE
#####################################################################################
#####################################################################################

#####
### ENTREPRISE
### Entreprise is assimilated as firm or entity which could be 
####################################################################
CREATE TABLE IF NOT EXISTS entreprise (
	e_id				INT NOT NULL AUTO_INCREMENT,	
	e_entreprise		VARCHAR(45)  	NOT NULL 									COMMENT 'Sigle or specifique entreprise code' ,
	e_designation		VARCHAR(255) 	NULL 										COMMENT 'entreprise designation' ,
	e_builded			INT 														COMMENT 'Création date with 4 digit' ,
	e_main				BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'specify if it is the main entreprise' ,
    e_activated			BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if entreprise is activated' ,
	e_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if entreprise is software deleted' ,
	e_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	e_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (e_id) ,
	UNIQUE INDEX ui_entreprise (e_entreprise ASC) )
ENGINE = InnoDB
COMMENT = 'Entreprise is the main entity off companies' ;
               
#####
### COMPANY original
####################################################################
CREATE TABLE IF NOT EXISTS company (
	c_id				INT 			NOT NULL 	AUTO_INCREMENT,
	c_company			VARCHAR(45) 	NOT NULL 									COMMENT 'Sigle or spécifique company code' ,
	c_designation		VARCHAR(255) 	NULL										COMMENT 'Company designation' ,
	c_entreprise		VARCHAR(45) 	NOT NULL 									COMMENT 'entreprise on which is link the company',
	c_builded			INT 														COMMENT 'Création date with 4 digit' ,
    c_main				BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'specify if it is the main company' ,
    c_activated			BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if company is activated' ,
	c_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if company is software deleted' ,
	c_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	c_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (c_id),
	UNIQUE INDEX ui_company (c_id ASC, c_company ASC),
    INDEX (c_company ASC, c_entreprise ASC),
	CONSTRAINT c_entreprise
		FOREIGN KEY (c_entreprise) REFERENCES entreprise (e_entreprise)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Company is one entity on which many people work';


#####################################################################################
#####################################################################################
###	GESTION DU STAFF
#####################################################################################
#####################################################################################

#####
### staff
### Contain main information of a staff user or people
####################################################################
CREATE TABLE IF NOT EXISTS staff (
	st_id 				INT 			NOT NULL 	AUTO_INCREMENT,
    st_staff 			VARCHAR(45) 	NOT NULL									COMMENT 'Staff code or matricule or id referenced',
	st_password			VARCHAR(256) 	NOT NULL									COMMENT 'Associated password for staff authentication',
    st_lastname 		VARCHAR(45) 	NULL 										COMMENT 'Name of staff id like Anderson for John Anderson',
	st_firstname 		VARCHAR(45) 	NULL 										COMMENT 'Firstname of staff id like John for John Anderson',
	st_middlename 		VARCHAR(45) 	NULL 										COMMENT 'middlename or postname or othername for staff',
    st_genre 			VARCHAR(10) 	NULL 										COMMENT 'gender of staff like female or male',
	st_borned		 	DATE			NULL 										COMMENT 'Staff born date this allow to get the age',
    st_maxInactiveInterval	INT			NOT NULL	DEFAULT 1800					COMMENT 'Staff max inactive interval to unconnect',
    st_activated		BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if staff is activated' ,
	st_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if staff is software deleted' ,
	st_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	st_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (st_id),
	UNIQUE INDEX ui_staff (st_id ASC, st_staff ASC),
    INDEX i_staff(st_staff ASC),
	CONSTRAINT st_genre
		FOREIGN KEY (st_genre) REFERENCES ism_genre (genre)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Specify staff main information';

#####
### staff_companies
### Contain companies allows for staff
####################################################################
CREATE TABLE IF NOT EXISTS staff_companies (
	stc_id 				INT 			NOT NULL 	AUTO_INCREMENT,
    stc_staff 			VARCHAR(45) 	NOT NULL									COMMENT 'Staff code or matricule referenced',
	stc_company			VARCHAR(45) 	NOT NULL									COMMENT 'Associated company for staff',
    stc_main	 		BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'specify if it is the main company' ,
    stc_activated		BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if staff allow to this company' ,
	stc_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	stc_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (stc_id),
	UNIQUE INDEX ui_staff_companies (stc_id ASC, stc_staff ASC, stc_company ASC),
    INDEX i_staff (stc_staff),
    INDEX i_company (stc_company),
	CONSTRAINT stc_staff
		FOREIGN KEY (stc_staff) REFERENCES staff (st_staff)
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	CONSTRAINT stc_company
		FOREIGN KEY (stc_company) REFERENCES company (c_company)
			ON DELETE CASCADE
            ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Specify allowed companies for staff';

###
### staff_group_def
### contain group name and information for a company
####################################################################
CREATE TABLE IF NOT EXISTS staff_group_def (
	stgd_id 			INT 			NOT NULL 	AUTO_INCREMENT,
	stgd_company		VARCHAR(45) 	NOT NULL									COMMENT 'Associated company for group_def',
    stgd_group_def		VARCHAR(45) 	NOT NULL									COMMENT 'Identification code for staff_group',
    stgd_designation	VARCHAR(255) 	NULL										COMMENT 'Description name for groupe',
	stgd_deleted		BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if group_def is software deleted' ,
	stgd_created		DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	stgd_changed		TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (stgd_id),
	UNIQUE INDEX ui_stgd_id (stgd_id ASC),
	INDEX i_staff_group_def (stgd_group_def ASC),
	CONSTRAINT stgd_company
		FOREIGN KEY (stgd_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Defining group allowed';

###
### staff_group_def_role
### Containing link betwnee group and application role
####################################################################
CREATE TABLE IF NOT EXISTS staff_group_def_role (
	stgdr_id 			INT 			NOT NULL AUTO_INCREMENT,
	stgdr_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
	stgdr_group_def 	VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify group def to link to role' ,
	stgdr_role 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify role link to group def' ,
    stgdr_activated		BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if def_role is activated' ,
	stgdr_created		DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	stgdr_changed		TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (stgdr_id),
	UNIQUE INDEX ui_group_def_role (stgdr_id ASC),
	CONSTRAINT stgdr_company
		FOREIGN KEY (stgdr_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION,
	CONSTRAINT stgdr_group_def
		FOREIGN KEY (stgdr_group_def) REFERENCES staff_group_def (stgd_group_def)
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	CONSTRAINT stgdr_role
		FOREIGN KEY (stgdr_role) REFERENCES ism_role (role)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'link between application role and specified ';


### STAFF SETUP
#####
### staff setup
### Contain main information of a staff user or people
####################################################################
CREATE TABLE IF NOT EXISTS staff_Setup (
	sts_id 				INT 			NOT NULL 	AUTO_INCREMENT,
    sts_staff 			VARCHAR(45) 	NOT NULL									COMMENT 'Staff code or matricule or id referenced',
    sts_menuIndex		INT				NOT NULL	DEFAULT	1						COMMENT 'Staff last index menu used',
    sts_timeOutSession	INT				NOT NULL	DEFAULT 300						COMMENT 'Staff time out for invalidate session default 5 min',    
    sts_theme			VARCHAR(65)													COMMENT 'USER THEME',
	sts_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	sts_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (sts_id),
	UNIQUE INDEX ui_staff (sts_id ASC, sts_staff ASC),
    INDEX i_staff(sts_staff ASC),
	CONSTRAINT sts_staff
		FOREIGN KEY (sts_staff) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Specify staff setup of application';
    

###
### staff_groups
### Link staff to groups
####################################################################
CREATE TABLE IF NOT EXISTS staff_groups (
	stg_id 				INT 			NOT NULL AUTO_INCREMENT,
	stg_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    stg_staff	 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify staff to associated to group' ,
    stg_group_def 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify group_def to associate staff' ,
    stg_activated		BIT 			NOT NULL 	DEFAULT 1 						COMMENT 'specify if staff group is activated' ,
	stg_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	stg_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (stg_id),
    UNIQUE INDEX ui_staff_groups (stg_id ASC),
	INDEX i_company (stg_company ASC, stg_staff ASC, stg_group_def ASC),
	INDEX i_stg_staff (stg_staff ASC),
    INDEX i_stg_stg_group_def (stg_group_def ASC),
	CONSTRAINT stg_company
		FOREIGN KEY (stg_company) REFERENCES company (c_company)
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	CONSTRAINT stg_staff
		FOREIGN KEY (stg_staff) REFERENCES staff (st_staff)
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	CONSTRAINT stg_group_def
		FOREIGN KEY (stg_group_def) REFERENCES staff_group_def (stgd_group_def)
			ON DELETE CASCADE
			ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'specify staff affected to a group';


#####################################################################################
#####################################################################################
###	GESTION DES PROCESSUS
#####################################################################################
#####################################################################################

###
### processus
### CREATE DEFINITION OF  TABLE
####################################################################
CREATE TABLE IF NOT EXISTS processus (
	p_id 				INT 			NOT NULL AUTO_INCREMENT,
	p_company 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    p_processus	 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for processus' ,
    p_designation 		VARCHAR(255) 	NULL	 									COMMENT 'Description name of processus' ,
    p_staffmanager 		VARCHAR(45) 	NULL	 									COMMENT 'Manager of processus' ,
	p_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if processus is software deleted' ,
	p_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	p_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (p_id),
	UNIQUE INDEX ui_processus (p_id ASC),
    INDEX i_company (p_company ASC, p_processus ASC),
    INDEX i_processus (p_processus ASC),
	CONSTRAINT p_company
		FOREIGN KEY (p_company) REFERENCES company (c_company)
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	CONSTRAINT p_staffmanager
		FOREIGN KEY (p_staffmanager) REFERENCES staff (st_staff)
			ON DELETE NO ACTION
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify processus';


#####################################################################################
#####################################################################################
###	GESTION DE LA DOCUMENTATION
#####################################################################################
#####################################################################################

###
### doc_type
### CREATE TYPE OF DOCUMENT EXISTING
####################################################################
CREATE TABLE IF NOT EXISTS doc_type (
	dct_id 				INT 			NOT NULL AUTO_INCREMENT,
	dct_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    dct_type		 	VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for document type' ,
    dct_designation 	VARCHAR(255) 	NULL	 									COMMENT 'Description name of document type' ,
	dct_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Spécify if doc type is software deleted' ,
	dct_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	dct_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (dct_id),
	UNIQUE INDEX ui_doc_type (dct_id ASC),
    INDEX i_company (dct_company ASC, dct_type ASC),
    INDEX i_doc_type (dct_type ASC),
	CONSTRAINT dct_company
		FOREIGN KEY (dct_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify document type';


###
### ddoc_explorer
### CREATE DOCUMENT EXPLORER
####################################################################
CREATE TABLE IF NOT EXISTS doc_explorer (
	dc_id 				INT 			NOT NULL AUTO_INCREMENT,
	dc_company 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    dc_processus 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for processus' ,
    dc_type		 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for document type' ,
    dc_designation 		VARCHAR(255) 	NULL	 									COMMENT 'designation of document',
    dc_version	 		VARCHAR(20) 	NULL	 									COMMENT 'version of document',
    dc_comment	 		LONGTEXT 		NULL	 									COMMENT 'Comment about the document',
    dc_link		 		VARCHAR(512)   	NOT NULL	 								COMMENT 'Path and file description location',
    dc_approuved	 	DATE			NULL 										COMMENT 'Approuval date of the document',
	dc_activated		BIT 			NOT NULL 	DEFAULT 1						COMMENT 'Spécify if exploration is software deleted' ,
	dc_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	dc_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (dc_id),
    UNIQUE INDEX ui_company (dc_id ASC),
    INDEX i_company (dc_company ASC, dc_processus ASC, dc_type ASC, dc_link ASC),
	INDEX i_doc_type (dc_type ASC),
	INDEX i_processus (dc_processus ASC),
    CONSTRAINT dc_company
		FOREIGN KEY (dc_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION,
	CONSTRAINT dc_type
		FOREIGN KEY (dc_type) REFERENCES doc_type (dct_type)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT dc_processus
		FOREIGN KEY (dc_processus) REFERENCES processus (p_processus)
			ON DELETE RESTRICT
			ON UPDATE CASCADE)
ENGINE=INNODB
COMMENT = 'Containt document information';


#####################################################################################
#####################################################################################
###	GESTION DES NON CONFORMITEE
#####################################################################################
#####################################################################################


###
### non_conformite_nature
### NATURE OF NON CONFORMITY
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_nature (
	ncn_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	ncn_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ncn_nature 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for nature' ,
    ncn_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of nature',
	ncn_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if nature is software deleted' ,
	ncn_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ncn_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ncn_id),
	UNIQUE INDEX ui_nc_nature (ncn_id ASC),
    INDEX i_company (ncn_company ASC, ncn_nature ASC),
    INDEX i_ncn_nature (ncn_nature ASC),
	CONSTRAINT ncn_company
		FOREIGN KEY (ncn_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify non conformite nature';

###
### non_conformite_nature
### UNITE OF NON CONFORMITY
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_unite (
	ncu_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	ncu_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ncu_unite 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for unite' ,
    ncu_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of unite',
	ncu_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if unite is software deleted' ,
	ncu_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ncu_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ncu_id),
	UNIQUE INDEX ui_nc_nature (ncu_id ASC),
    INDEX i_company (ncu_company ASC, ncu_unite ASC),
    INDEX i_ncu_unite (ncu_unite ASC),
	CONSTRAINT ncu_company
		FOREIGN KEY (ncu_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify non conformite unite';

###
### non_conformite_gravity
### GRAVITY OF NON CONFORMITY
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_gravity (
	ncg_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	ncg_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ncg_gravity			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for gravity' ,
    ncg_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of gravity',
	ncg_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if gravity is software deleted' ,
	ncg_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ncg_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ncg_id),
	UNIQUE INDEX ui_nc_gravity (ncg_id ASC),
    INDEX i_company (ncg_company ASC, ncg_gravity ASC),
    INDEX i_ncg_gravity (ncg_gravity ASC),
	CONSTRAINT ncg_company
		FOREIGN KEY (ncg_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify non conformite gravity';


###
### non_conformite_frequency
### GRAVITY OF NON CONFORMITY
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_frequency (
	ncf_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	ncf_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ncf_frequency		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for frequency' ,
    ncf_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of frequency',
	ncf_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if frequency is software deleted' ,
	ncf_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ncf_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ncf_id),
	UNIQUE INDEX ui_nc_frequency (ncf_id ASC),
    INDEX i_company (ncf_company ASC, ncf_frequency ASC),
    INDEX i_ncf_frequency (ncf_frequency ASC),
	CONSTRAINT ncf_company
		FOREIGN KEY (ncf_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify non conformite frequency';


###
### non_conformite_request
### CREATE NON_CONFORMITE REQUEST TABLE TO SPECIFY NON CONFORMITE FROM USER
#############################################################################
CREATE TABLE IF NOT EXISTS non_conformite_request (
	ncr_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number ',
	ncr_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ncr_processus 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for processus' ,
    ncr_staff 			VARCHAR(45) 	NOT NULL									COMMENT 'Staff code or matricule or id referenced',
    ncr_state 			VARCHAR(45) 	NULL 		 								COMMENT 'Code or sigle for state' ,
    ncr_nature 			VARCHAR(45) 	DEFAULT "A"									COMMENT 'Code or sigle for nature' ,
    ncr_occured			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Date and time when problem occured' ,
	ncr_product			VARCHAR(128)	NULL										COMMENT 'Identify product or specific element',
    ncr_trace			VARCHAR(45)		NULL										COMMENT	'Number or similar element to identify concerned element',
	ncr_quantity		DOUBLE 			NULL 										COMMENT 'Quantity of nc, product or trace specify' ,
	ncr_unite 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for unite' ,
    ncr_gravity	 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for gravity' ,
    ncr_frequency 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for frequency' ,
    ncr_title			VARCHAR(128)	NOT NULL									COMMENT 'Title of non conformity request',
    ncr_description		TEXT 			NOT NULL 									COMMENT 'Description of nc' ,
    ncr_link			VARCHAR(512)	NULL										COMMENT 'Saving link of img of nc',
    ncr_staffOnValidate	VARCHAR(45) 	NULL										COMMENT 'Staff code or matricule or id referenced for on state refuse',
	ncr_descOnValidate	TEXT			NULL										COMMENT 'Comment when nc is unapprouved',
    ncr_occuredValidate	DATETIME 		DEFAULT 0									COMMENT	'Date and time when validation occured' ,
    ncr_staffOnAction	VARCHAR(45) 	NULL										COMMENT 'Staff code or matricule or id referenced for on state refuse',
	ncr_descOnAction	TEXT			NULL										COMMENT 'Comment when nc is unapprouved',
    ncr_occuredAction	DATETIME 		DEFAULT 0									COMMENT	'Date and time when validation occured' ,
    ncr_enddingAction	DATETIME 		DEFAULT 0									COMMENT	'Date and time when ACTION SHOULD BE DONE' ,
	ncr_staffOnRefuse	VARCHAR(45) 	NULL										COMMENT 'Staff code or matricule or id referenced for on state refuse',
	ncr_descOnRefuse	TEXT			NULL										COMMENT 'Comment when nc is unapprouved',
	ncr_occuredRefuse	DATETIME 		DEFAULT 0									COMMENT	'Date and time when validation occured' ,
	ncr_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ncr_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ncr_id),
    UNIQUE INDEX ui_company (ncr_id ASC),
    INDEX i_company (ncr_company ASC, ncr_processus ASC, ncr_state ASC, ncr_staff ASC),
    INDEX i_ncr_state (ncr_state ASC),
	INDEX i_ncr_staff (ncr_staff ASC),
	INDEX i_ncr_processus (ncr_processus ASC),
    CONSTRAINT ncr_company
		FOREIGN KEY (ncr_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION,
	CONSTRAINT ncr_processus
		FOREIGN KEY (ncr_processus) REFERENCES processus (p_processus)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_staff
		FOREIGN KEY (ncr_staff) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_staffOnValidate
		FOREIGN KEY (ncr_staffOnValidate) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_staffOnAction
		FOREIGN KEY (ncr_staffOnAction) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_staffOnRefuse
		FOREIGN KEY (ncr_staffOnRefuse) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_state
		FOREIGN KEY (ncr_state) REFERENCES ism_ncrstate (istate)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_nature
		FOREIGN KEY (ncr_nature) REFERENCES non_conformite_nature (ncn_nature)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_unite
		FOREIGN KEY (ncr_unite) REFERENCES non_conformite_unite (ncu_unite)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_gravity
		FOREIGN KEY (ncr_gravity) REFERENCES non_conformite_gravity (ncg_gravity)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT ncr_frequency
		FOREIGN KEY (ncr_frequency) REFERENCES non_conformite_frequency (ncf_frequency)
			ON DELETE RESTRICT
			ON UPDATE CASCADE)
ENGINE=INNODB
COMMENT = 'Containt non conformite request list';


#####################################################################################
#####################################################################################
###	VUE
#####################################################################################
#####################################################################################

###
### Stafflogin
### Utiliser pour se conneccter
#####################################################################
CREATE OR REPLACE VIEW StaffLogin AS 
	SELECT st_id, stgdr_company,  st_staff, st_password, stg_group_def, stgdr_role, st_activated
	FROM staff, staff_groups, staff_group_def_role
	WHERE stg_staff=staff.st_staff AND stgdr_group_def=stg_group_def;
;


#####################################################################################
#####################################################################################
###	GESTION DES DES DONNEES DE BASE
#####################################################################################
#####################################################################################

### ISM ROLE
INSERT INTO ism_genre (genre, genrename) VALUES 
	('N',	'None'),
	('F', 	'Femme'),
    ('H',	'Homme');
    
### ISM ROLE
INSERT INTO ism_role (role, rolename) VALUES 
    ('USER',        'Utilisateur'),
    ('CO',        'Compagnie'),
    ('CO_ADMI',        				'Administration'),
    ('CO_ADMICOMPANY_R',        	'Compagnie Lecture'),
    ('CO_ADMICOMPANY_W',        	'Compagnie Ecriture'),
    ('CO_ADMIENTREPRISE_R',        	'Entreprise Lecture'),
    ('CO_ADMIENTREPRISE_W',        	'Entreprise Ecriture'),
    ('CO_ADMIISMROLE_R',        	'Rôle Lecture'),
    ('CO_ADMIISMGENRE_R',        	'Genre Lecture'),
    ('CO_HR',        				'Ressources Humaines'),
    ('CO_HRSTAFF_R',        		'Staff Lecture'),
    ('CO_HRSTAFF_W',        		'Staff Ecriture'),
    ('CO_HRSTAFFCOMPANIES_R',       'Staff Compagnie Lecture'),
    ('CO_HRSTAFFCOMPANIES_W',       'Staff Compagnie Ecriture'),
    ('CO_HRSTAFFGROUPDEF_R',        'Staff Définition Groupe Lecture'),
    ('CO_HRSTAFFGROUPDEF_W',        'Staff Définition Groupe Ecriture'),
    ('CO_HRSTAFFGROUPDEFROLE_R',    'Staff Connexion role Lecture'),
    ('CO_HRSTAFFGROUPDEFROLE_W',    'Staff Connexion role Ecriture'),
    ('CO_HRSTAFFGROUPS_R',        	'Staff Groupe Lecture'),
    ('CO_HRSTAFFGROUPS_W',        	'Staff Groupe Ecriture'),
    ('CO_SMQ',        				'Syst. Management Qualité'),
    ('CO_SMQDOCEXPLORER_R',        	'Smq Exploration Document Lecture'),
    ('CO_SMQDOCEXPLORER_W',        	'Smq Exploration Document Ecriture'),
    ('CO_SMQDOCTYPE_R',        		'Smq Type de Document Lecture'),
    ('CO_SMQDOCTYPE_W',        		'Smq Type de Document Ecriture'),
    ('CO_SMQPROCESSUS_R',        	'Smq Processus Lecture'),
    ('CO_SMQPROCESSUS_W',        	'Smq Processus Ecriture'),
    ('CO_SMQNC_R',        			'Smq non conformite Lecture'),
    ('CO_SMQNC_W',        			'Smq non conformite écriture'),
	('CO_SMQNC_UNITE_R', 				'SMQ nc unité lecture'),
	('CO_SMQNC_UNITE_W', 				'SMQ nc unité écriture'),
	('CO_SMQNC_NATURE_R', 			'SMQ nc nature lecture'),
	('CO_SMQNC_NATURE_W', 			'SMQ nc nature écriture'),
	('CO_SMQNC_GRAVITE_R', 			'SMQ nc gravité lecture'),
	('CO_SMQNC_GRAVITE_W', 			'SMQ nc gravité écriture'),
	('CO_SMQNC_FREQUENCE_R', 			'SMQ nc fréquence lecture'),
	('CO_SMQNC_FREQUENCE_W', 			'SMQ nc fréquence écriture'),
	('CO_SMQNC_REQUEST_R',        	'Smq nc request lecture'),
    ('CO_SMQNC_REQUEST_W',        	'Smq nc request écriture'),
    ('CO_SMQNC_CLOTURE_R',	'SMQ NC clôture - lecture'),
    ('CO_SMQNC_CLOTURE_W',	'SMQ NC clôture - ecriture'),
    ('CO_SMQNC_REFUSE_R',	'SMQ NC refuser - lecture'),
    ('CO_SMQNC_REFUSE_W',	'SMQ NC refuser - ecriture'),
    ('CO_SMQNC_VALIDATE_R',	'SMQ NC valider - lecture'),
    ('CO_SMQNC_VALIDATE_W',	'SMQ NC valider - ecriture'),
    ('CO_SMQNC_ACTION_R',	'SMQ NC action - lecture'),
    ('CO_SMQNC_ACTION_W',	'SMQ NC action - ecriture'),
    ('CO_HRSTAFFMANAGER_R',	'RH Staff manager - lecture'),
    ('CO_HRSTAFFMANAGER_W',	'RH Staff manager - ecriture'),
    ('CO_HRSTAFFPROFILE_R',	'RH Staff profile - lecture'),
    ('CO_HRSTAFFPROFILE_W',	'RH Staff profile - ecriture'),
    ('CO_HRSTAFFSETUP_R',		'RH Staff config - lecture'),
    ('CO_HRSTAFFSETUP_W',		'RH Staff config - ecriture'),
    ('CO_SMQNCMANAGESTATE_R', 'SMQ NC MANAGE STATE - Lecture'),
    ('CO_SMQNCMANAGESTATE_W', 'SMQ NC MANAGE STATE - Ecriture');


# ISM_NON CONFORMITY REQUEST STATE
INSERT INTO ism_ncrstate (istate, statename) VALUES 
    ('A',		'Créée'		),
    ('B',       'En cours de validation'),
    ('C',       'En attente de solution'),
    ('D',       'En cours'),
    ('E',       'En retard'),
    ('F',       'Terminé'),
    ('G',		'Annulé');
    
### ENTREPRISE
INSERT INTO entreprise (e_entreprise, e_designation, e_builded, e_created) VALUES 
	('BRAS', 	'BRASIMBA', 1924, now()),
    ('ISM', 'Industrial System Manager', 2014, now());
         
### COMPANY                
INSERT INTO company (c_company, c_designation, c_entreprise, c_builded, c_created) VALUES 
	('39', 		'BRASIMBA BENI', 		'BRAS',	2012, NOW()),
	('ISM-01', 	'ISM PARIS', 			'ISM', 	2014, NOW()),
    ('36', 		'BRASIMBA LUBUMBASHI', 	'BRAS',	1924, NOW());

    
### STAFF
INSERT INTO staff (st_staff, st_password, st_lastname, st_firstname, st_genre, st_created) VALUES
	('ism', 	'd2cd76186c93a0d46befa655f222ffe47bbc362d55057460b1ba32ad91c22e9a', 'ISM', 		'IS. Manager', 	'N', 	now()); 	/* id: ism mp: iSm */

### STAFF_COMPANIES
INSERT INTO staff_companies (stc_staff, stc_company, stc_created) VALUES
	('ism',		'ISM-01',		now()),
	('ism',		'36',			now()),
	('ism',		'39',			now());
    
### STAFF GROUP DEF
INSERT INTO staff_group_def (stgd_company, stgd_group_def, stgd_designation, stgd_created) VALUES
    ('39', 'ADMIN', 	'Administrateur',			NOW()),
    ('39', 'MAINT', 	'Maintenance',				NOW()),
    ('39', 'COMP', 		'Compagnie',				NOW()),
    ('39', 'SMQ', 		'Sys. Management Qualité',	NOW()),
    ('39', 'USER', 		'Utilisateur',				NOW()),
    ('39', 'RH', 		'Ressources Humaine',		NOW()),
    ('39', 'IT', 		'Informatique',				NOW());

### STAFF GROUP DEF ROLE
INSERT INTO staff_group_def_role (stgdr_company, stgdr_group_def, stgdr_role, stgdr_created) VALUES
    ('39', 'ADMIN',    'USER',							now()),
	('39', 'ADMIN',    'CO',							now()),
	('39', 'ADMIN',    'CO_ADMI',							now()),
	('39', 'ADMIN',    'CO_ADMICOMPANY_R',							now()),
	('39', 'ADMIN',    'CO_ADMICOMPANY_W',							now()),
	('39', 'ADMIN',    'CO_ADMIENTREPRISE_R',							now()),
	('39', 'ADMIN',    'CO_ADMIENTREPRISE_W',							now()),
	('39', 'ADMIN',    'CO_ADMIISMROLE_R',							now()),
	('39', 'ADMIN',    'CO_ADMIISMGENRE_R',							now()),
	('39', 'ADMIN',    'CO_HR',							now()),
	('39', 'ADMIN',    'CO_HRSTAFF_R',							now()),
	('39', 'ADMIN',    'CO_HRSTAFF_W',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFCOMPANIES_R',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFCOMPANIES_W',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPDEF_R',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPDEF_W',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPDEFROLE_R',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPDEFROLE_W',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPS_R',							now()),
	('39', 'ADMIN',    'CO_HRSTAFFGROUPS_W',							now()),
	('39', 'ADMIN',    'CO_SMQ',							now()),
	('39', 'ADMIN',    'CO_SMQDOCEXPLORER_R',							now()),
	('39', 'ADMIN',    'CO_SMQDOCEXPLORER_W',							now()),
	('39', 'ADMIN',    'CO_SMQDOCTYPE_R',							now()),
	('39', 'ADMIN',    'CO_SMQDOCTYPE_W',							now()),
	('39', 'ADMIN',    'CO_SMQPROCESSUS_R',							now()),
	('39', 'ADMIN',    'CO_SMQPROCESSUS_W',							now()),
    ('39', 'ADMIN',    'CO_SMQNC_R',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_W',        			now()),
	('39', 'ADMIN',    'CO_SMQNC_UNITE_R', 				now()),
	('39', 'ADMIN',    'CO_SMQNC_UNITE_W', 				now()),
	('39', 'ADMIN',    'CO_SMQNC_NATURE_R', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_NATURE_W', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_GRAVITE_R', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_GRAVITE_W', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_FREQUENCE_R', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_FREQUENCE_W', 			now()),
	('39', 'ADMIN',    'CO_SMQNC_REQUEST_R',        	now()),
    ('39', 'ADMIN',    'CO_SMQNC_REQUEST_W',        	now()),
    ('39', 'ADMIN',    'CO_SMQNC_CLOTURE_R',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_CLOTURE_W',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_REFUSE_R',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_REFUSE_W',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_VALIDATE_R',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_VALIDATE_W',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_ACTION_R',        			now()),
    ('39', 'ADMIN',    'CO_SMQNC_ACTION_W',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFMANAGER_R',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFMANAGER_W',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFPROFILE_R',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFPROFILE_W',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFSETUP_R',        			now()),
    ('39', 'ADMIN',    'CO_HRSTAFFSETUP_W',        			now());

    
### STAFF_GROUPS
INSERT INTO staff_groups (stg_company, stg_staff, stg_group_def, stg_created) VALUES
	('ISM-01', 'ism', 		'ADMIN', 	now()),
    ('39', 'ism', 		'ADMIN', 	now());

### PROCESSUS
INSERT INTO processus (p_company, p_processus, p_designation, p_created) VALUES 
	('39','BE01', 'MANAGEMENT QUALITE', 		now()),
	('39','BE02', 'SUPPLY CHAIN', 				now()),
	('39','BE03', 'COMMERCIAL', 				now()),
	('39','BE04', 'FABRICATION', 				now()),
	('39','BE05', 'CONDITIONNEMENT', 			now()),
	('39','BE06', 'GESTION MPP MPC', 			now()),
	('39','BE07', 'CONTROLE QUALITE', 			now()),
	('39','BE08', 'LOGISTIQUE ET ACHAT', 		now()),
	('39','BE09', 'FINANCE ET COMPTABILITE', 	now()),
	('39','BE10', 'DISTRIBUTION', 				now()),
	('39','BE11', 'RESSOURCES HUMAINES', 		now()),
	('39','BE12', 'INFORMATIQUE', 				now()),
	('39','BE13', 'MAINTENANCE', 				now());

### DOC TYPE
INSERT INTO doc_type (dct_company, dct_type, dct_designation, dct_created) VALUES 
	('39','FPS', 'FICHE PROCESSUS', 	now()),
	('39','IT', 'INSTRUCTION', 			now()),
	('39','P', 'PROCEDURE', 			now()),
	('39','PS', 'PROCESSUS', 			now());

### NON CONFORMITE NATURE
INSERT INTO non_conformite_nature (ncn_company, ncn_nature, ncn_designation, ncn_created) VALUES
	('39', '00', 	'Aucune', 	now()),
	('39', '01', 	'PROD. HORS SPECIFICATION', 	now()),
    ('39', '02', 	'NON RESPECT DES PROCEDURES', 	now()),
    ('39', '03', 	'NON RESCPECT DES EXIGENCES SMQ', 	now()),
    ('39', '04', 	'NON RESPECT DE LA PROPRETE', 	now()),
    ('39', '05', 	'NON RESPECT DE L\'ORDRE', 	now()),
    ('39', '06', 	'DEFAUT D\'EQUIPEMENT', 	now()),
    ('39', '07', 	'DEGAT', 	now());

### NON CONFORMITE UNITE
INSERT INTO non_conformite_unite (ncu_company, ncu_unite, ncu_designation, ncu_created) VALUES
    ('39','NONE', 	'AUCUN', 	now()),
    ('39','Bout', 	'BOUTEILLE', 	now()),
    ('39','HL', 	'HECTOLITRE', 	now()),
    ('39','KG', 	'KILOGRAMME', 	now()),
    ('39','PCE', 	'PIECES', 	now()),
    ('39','CAS', 	'CASIER', 	now()),
    ('39','L', 		'LITRE', 	now());





