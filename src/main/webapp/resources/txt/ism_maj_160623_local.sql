
ALTER TABLE `ism`.`non_conformite_request` 
ADD COLUMN `ncr_approuver` VARCHAR(45) NULL DEFAULT NULL AFTER `ncr_changed`,
ADD COLUMN `ncr_approuvedDate` DATETIME NULL DEFAULT '0000-00-00 00:00:00' AFTER `ncr_approuver`,
ADD COLUMN `ncr_approuved` BIT(1) NULL DEFAULT NULL AFTER `ncr_approuvedDate`,
ADD COLUMN `ncr_actions` INT(22) NULL DEFAULT NULL AFTER `ncr_approuved`;

ALTER TABLE `ism`.`non_conformite_request` 
ADD INDEX `ncr_approuver_idx` (`ncr_approuver` ASC);
ALTER TABLE `ism`.`non_conformite_request` 
ADD CONSTRAINT `ncr_approuver`
  FOREIGN KEY (`ncr_approuver`)
  REFERENCES `ism`.`staff` (`st_staff`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;




CREATE TABLE IF NOT EXISTS ism_ncastate (
  id 			INT NOT NULL AUTO_INCREMENT,
  istate		VARCHAR(45) NOT NULL,
  statename		VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  INDEX ui_idncastate (istate ASC, statename ASC))
COMMENT = 'Existing non conformity actions state for ism app';

# ISM_NON CONFORMITY ACTIONS STATE
INSERT INTO ism_ncastate (istate, statename) VALUES 
    ('A',		'Créée'		),
    ('B',       'En cours'),
    ('C',       'Ajournée'),
    ('D',       'Terminée');


###
### non_conformite_actions
### ACTIONS OF NON CONFORMITY
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_actions (
	nca_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	nca_actions			INT				NOT NULL	 								COMMENT 'Non conformite number' ,
    nca_staff		 	VARCHAR(45) 	NOT NULL 									COMMENT 'Staff on this actions',
    nca_description		TEXT 			NOT NULL									COMMENT 'Description on this actions',
	nca_state			INT				NOT NULL									COMMENT 'State of general actions',
	nca_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	nca_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (nca_id),
	UNIQUE INDEX ui_nc_action (nca_id ASC),
    INDEX i_nca_actions (nca_actions ASC),
	CONSTRAINT nca_staff
		FOREIGN KEY (nca_staff) REFERENCES staff (st_staff)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
	CONSTRAINT nca_state
		FOREIGN KEY (nca_state) REFERENCES ism_ncastate (id)
			ON DELETE RESTRICT
			ON UPDATE CASCADE)
ENGINE=INNODB
COMMENT = 'Specify non conformite actions';
