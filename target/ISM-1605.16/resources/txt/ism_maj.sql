
###
### unite
### Spécifie les différentes unités disponible pour l'application
####################################################################
CREATE TABLE IF NOT EXISTS unite (
	u_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	u_company 			VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    u_unite				VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for unite' ,
    u_designation 		VARCHAR(255) 	NOT NULL 									COMMENT 'designation of unite',
	u_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if unite is software deleted' ,
	u_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	u_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (u_id),
	UNIQUE INDEX ui_unite (u_id ASC),
    INDEX i_company (u_company ASC, u_unite ASC),
    INDEX i_unite (u_unite ASC),
	CONSTRAINT u_company
		FOREIGN KEY (u_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify unite';

###
### lab_analyse_method
### Spécifie les différentes méthode d'analyse (spéctrométrie,...)
####################################################################
CREATE TABLE IF NOT EXISTS lab_analyse_method (
	lam_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	lam_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    lam_method			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for analyse method' ,
    lam_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of analyse method',
    lam_description		text			NULL	 									COMMENT 'description of analyse method',
	lam_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if analyse method is software deleted' ,
	lam_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	lam_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (lam_id),
	UNIQUE INDEX ui_method (lam_id ASC),
    INDEX i_company (lam_company ASC, lam_method ASC),
    INDEX i_method (lam_method ASC),
	CONSTRAINT lam_company
		FOREIGN KEY (lam_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify analyse method';

###
### lab_analyse_category
### Spécifie les différentes méthode d'analyse (spéctrométrie,...)
####################################################################
CREATE TABLE IF NOT EXISTS lab_analyse_category (
	lac_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	lac_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    lac_category		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for category' ,
    lac_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of category',
    lac_description		text			NULL	 									COMMENT 'description of category',
	lac_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if category is software deleted' ,
	lac_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	lac_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (lac_id),
	UNIQUE INDEX ui_category (lac_id ASC),
    INDEX i_company (lac_company ASC, lac_category ASC),
    INDEX i_analyse_category (lac_category ASC),
	CONSTRAINT lac_company
		FOREIGN KEY (lac_company) REFERENCES company (c_company)
			ON DELETE RESTRICT
			ON UPDATE NO ACTION)
ENGINE=INNODB
COMMENT = 'Specify category';


###
### lab_analyse_type
### spécifie le type d'analyse (Visuel, PH, Conductivité, TH, TH,...)
####################################################################
CREATE TABLE IF NOT EXISTS lab_analyse_type (
	lat_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	lat_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    lat_type			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for type' ,
    lat_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of type',
    lat_unite			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for unite' ,
    lat_method			VARCHAR(45) 	NULL	 									COMMENT 'Code or sigle for methode' ,
    lat_category		VARCHAR(45) 	NULL	 									COMMENT 'Code or sigle for catégorie' ,
    lat_description		text			NULL	 									COMMENT 'description of type',
	lat_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if category is software deleted' ,
	lat_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	lat_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (lat_id),
	UNIQUE INDEX ui_type (lat_id ASC),
    INDEX i_company (lat_company ASC, lat_type ASC),
    INDEX i_analyse_type (lat_type ASC),
	CONSTRAINT lat_company 	FOREIGN KEY (lat_company) 	REFERENCES company 				(c_company) 	ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lat_unite 	FOREIGN KEY (lat_unite) 	REFERENCES unite 				(u_unite) 		ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lat_method 	FOREIGN KEY (lat_method) 	REFERENCES lab_analyse_method 	(lam_method) 	ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lat_category	FOREIGN KEY (lat_category) 	REFERENCES lab_analyse_category	(lac_category)	ON DELETE RESTRICT ON UPDATE NO ACTION
)ENGINE=INNODB
COMMENT = 'Specify analyse type';


###
### equipement
### Spécifie un équipement de l'installation
####################################################################
CREATE TABLE IF NOT EXISTS equipement (
	e_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	e_company 			VARCHAR(45) 	NOT NULL	 									COMMENT 'Specify company concerned' ,
    e_equipement		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for equipement' ,
    e_designation 		VARCHAR(255) 	NOT NULL 									COMMENT 'designation of equipement',
    e_cf				VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for equipement' ,
    e_famille			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for equipement' ,
    e_ss_famille		VARCHAR(45) 	NULL	 									COMMENT 'Code or sigle for equipement' ,
    e_groupe			VARCHAR(45) 	NULL	 									COMMENT 'Code or sigle for equipement' ,
    e_responsable		VARCHAR(45) 	NULL	 									COMMENT 'Code or sigle for equipement' ,
    e_description		text			NULL	 									COMMENT 'description of equipement',
	e_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if equipement is software deleted' ,
	e_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	e_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (e_id),
	UNIQUE INDEX ui_equipement (e_id ASC),
    INDEX i_company (e_company ASC, e_equipement ASC),
    INDEX i_equipement (e_equipement ASC),
	CONSTRAINT e_company 		FOREIGN KEY (e_company) 		REFERENCES company 				(c_company) 	ON DELETE RESTRICT ON UPDATE NO ACTION
)ENGINE=INNODB
COMMENT = 'Specify the equipement';


###
### lab_sample_pt
### Spécifie le point d'échantillonnage 
####################################################################
CREATE TABLE IF NOT EXISTS lab_sample_pt (
	lsp_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	lsp_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    lsp_sample_pt		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for sampling point' ,
    lsp_designation 	VARCHAR(255) 	NOT NULL 									COMMENT 'designation of sampling point',
    lsp_equipement		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for equipement' ,
    lsp_description		text			NULL	 									COMMENT 'description of sampling point',
	lsp_deleted			BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if sampling point is software deleted' ,
	lsp_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	lsp_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (lsp_id),
	UNIQUE INDEX ui_sample_pt (lsp_id ASC),
    INDEX i_company (lsp_company ASC, lsp_sample_pt ASC),
    INDEX i_sample_pt (lsp_sample_pt ASC),
	CONSTRAINT lsp_company 		FOREIGN KEY (lsp_company) 		REFERENCES company 				(c_company) 	ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lsp_equipement 	FOREIGN KEY (lsp_equipement) 	REFERENCES equipement			(e_equipement) 	ON DELETE RESTRICT ON UPDATE NO ACTION
)ENGINE=INNODB
COMMENT = 'Specify the sampling point';



###
### lab_sample_pt_analyses
### Specifie les types d'anlyse permit sur un point d'echantillonage
####################################################################
CREATE TABLE IF NOT EXISTS lab_sample_pt_analyses (
	lspa_id 			INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	lspa_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    lspa_sample_pt		VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for sampling point analyses' ,
    lspa_type			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for sampling point analyses' ,
    lspa_observation	VARCHAR(255) 	NULL 										COMMENT 'observation of sampling point analyses',
	lspa_deleted		BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if sampling point analysesis software deleted' ,
	lspa_created		DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	lspa_changed		TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (lspa_id),
	UNIQUE INDEX ui_sample_pt_analyses (lspa_id ASC),
    INDEX i_company (lspa_company ASC, lspa_sample_pt ASC,  lspa_type ASC),
    INDEX i_sample_pt_type (lspa_sample_pt ASC,  lspa_type ASC),
    INDEX i_sample_pt (lspa_sample_pt ASC),
    INDEX i_type (lspa_type ASC),
	CONSTRAINT lspa_company 		FOREIGN KEY (lspa_company) 		REFERENCES company 				(c_company) 		ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lspa_sample_pt 		FOREIGN KEY (lspa_sample_pt) 	REFERENCES lab_sample_pt		(lsp_sample_pt) 	ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT lspa_type 			FOREIGN KEY (lspa_type) 		REFERENCES lab_analyse_type		(lat_type) 			ON DELETE RESTRICT ON UPDATE NO ACTION
)ENGINE=INNODB
COMMENT = 'Specify the sampling point analyses';



###
### lab_sample
### Ce tableau fourni les valeur d'analyse d'un point d'échantillonnage
####################################################################
CREATE TABLE IF NOT EXISTS lab_sample (
	ls_id 			INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	ls_company 		VARCHAR(45) 	NOT NULL	 								COMMENT 'Specify company concerned' ,
    ls_sample_pt	VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for sampling point analyses' ,
    ls_type			VARCHAR(45) 	NOT NULL	 								COMMENT 'Code or sigle for sampling point analyses' ,
    ls_value		DOUBLE			NOT NULL									COMMENT	'Valeur de l analyse',
    ls_value_t		VARCHAR(45)		NULL										COMMENT	'Valeur de l analyse au format texte',
    ls_sampler		varchar(45)		NOT NULL									COMMENT	'Technicien who make the sample',
    ls_validate		BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if sample value was approuve by the chef' ,
    ls_observation	VARCHAR(255) 	NULL 										COMMENT 'observation of sampling point analyses',
	ls_deleted		BIT 			NOT NULL 	DEFAULT 0 						COMMENT 'Specify if sampling point analysesis software deleted' ,
	ls_created		DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	ls_changed		TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (ls_id),
	UNIQUE INDEX ui_sample (ls_id ASC),
    INDEX iu_sample (ls_id ASC, ls_company ASC, ls_sample_pt ASC,  ls_type ASC),
    INDEX i_company (ls_company ASC, ls_sample_pt ASC,  ls_type ASC),
    INDEX i_sample_pt_type (ls_sample_pt ASC,  ls_type ASC),
    INDEX i_sample_pt (ls_sample_pt ASC),
    INDEX i_type (ls_type ASC),
	CONSTRAINT ls_company 		FOREIGN KEY (ls_company) 	REFERENCES company 				(c_company) 		ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT ls_sample_pt 	FOREIGN KEY (ls_sample_pt) 	REFERENCES lab_sample_pt		(lsp_sample_pt) 	ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT ls_type 			FOREIGN KEY (ls_type) 		REFERENCES lab_analyse_type		(lat_type) 			ON DELETE RESTRICT ON UPDATE NO ACTION,
    CONSTRAINT ls_sampler		FOREIGN KEY (ls_sampler) 	REFERENCES staff				(st_staff) 			ON DELETE RESTRICT ON UPDATE NO ACTION
)ENGINE=INNODB
COMMENT = 'Specify the sampling point analyses';




