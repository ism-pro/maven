
USE `ISM`;

DROP TABLE IF EXISTS `ism`.`non_conformite`;
#####
### ISM
####################################################################
CREATE TABLE IF NOT EXISTS ism_ncastate (
  id 			INT NOT NULL AUTO_INCREMENT,
  istate		VARCHAR(45) NOT NULL,
  statename		VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  INDEX ui_idncastate (istate ASC, statename ASC))
COMMENT = 'Existing non conformity actions state for ism app';

# DEFINE NCASTATE
INSERT INTO ism_ncastate (istate, statename) VALUES 
    ('A',		'En cours'		),
    ('B',       'Ajournée'),
    ('C',       'Terminée'),
    ('D',       'Annulée');



###
### non_conformite_actions
### Element which contains non conformity actions
####################################################################
CREATE TABLE IF NOT EXISTS non_conformite_actions (
	nca_id 				INT 			NOT NULL AUTO_INCREMENT						COMMENT 'Unique number',
	nca_nc				INT				NOT NULL	 								COMMENT 'correspond au numéro de non conformite' ,
    nca_staff		 	VARCHAR(45) 	NOT NULL 									COMMENT 'Staff fournissant l action',
    nca_description		TEXT 			NOT NULL									COMMENT 'Description de l action',
    nca_deadline		DATETIME		NOT NULL	DEFAULT 0						COMMENT 'Date de fin espérée',
    nca_action			INT							DEFAULT NULL					COMMENT 'Référence à la solution ayant aider à la résolution du problème',		
	nca_state			INT				NOT NULL									COMMENT 'State of general actions',
	nca_created			DATETIME 		NOT NULL	DEFAULT 0						COMMENT	'Creation date and time' ,
	nca_changed			TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP 	COMMENT 'Last occured changed on concerned line' ,
	PRIMARY KEY (nca_id),
	UNIQUE INDEX ui_nc_action (nca_id ASC),
    INDEX i_nca_nc (nca_nc ASC),
    CONSTRAINT nca_nc
		FOREIGN KEY (nca_nc) REFERENCES non_conformite_request (ncr_id)
			ON DELETE RESTRICT
			ON UPDATE CASCADE,
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


###
### NonConformiteRequest
### ADD NEW COLUMN
####################################################################
ALTER TABLE `ism`.`non_conformite_request` 
ADD COLUMN `ncr_approuver` 			VARCHAR(45) 	NULL 					AFTER `ncr_occuredRefuse`,
ADD COLUMN `ncr_approuved` 			BIT 			DEFAULT NULL  			AFTER `ncr_approuver`,
ADD COLUMN `ncr_approuvedDate` 		DATETIME 		NULL		 			AFTER `ncr_approuved`,
ADD COLUMN `ncr_approuvedDesc` 		TEXT			NULL					AFTER `ncr_approuvedDate`,
ADD INDEX `i_ncr_approuver` (`ncr_approuver` ASC);
ALTER TABLE `ism`.`non_conformite_request` 
ADD CONSTRAINT `ncr_approuver`
  FOREIGN KEY (`ncr_approuver`)
  REFERENCES `ism`.`staff` (`st_staff`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

### SHIFT VALUE IL FAUT D'ABORD DESACTIVER LE MODE SAFE UPDATE DANS LES PREFERENCES
UPDATE `ism`.`non_conformite_request`  SET ncr_approuver = ncr_staffOnValidate;
UPDATE `ism`.`non_conformite_request`  SET ncr_approuvedDate = ncr_occuredValidate;


DROP procedure IF EXISTS `tft_approuved`;
DELIMITER $$
USE `ism`$$
CREATE PROCEDURE `tft_approuved` ()
BEGIN
	DECLARE id  INT;
    DECLARE records INT;
	DECLARE str varchar(45);
    
	SET id = 1;
    SET str = '';
    
    WHILE id < (select COUNT(ncr_id) FROM non_conformite_request) DO
		SET str = (select non_conformite_request.ncr_staffOnRefuse from non_conformite_request where non_conformite_request.ncr_id=id);

        IF (TRIM(str) <> '') THEN
			UPDATE `ism`.`non_conformite_request`  SET non_conformite_request.ncr_approuved = 0 where ncr_id=id;
            UPDATE `ism`.`non_conformite_request`  SET non_conformite_request.ncr_approuvedDesc = non_conformite_request.ncr_descOnRefuse where ncr_id=id;
		ELSE
			UPDATE `ism`.`non_conformite_request`  SET non_conformite_request.ncr_approuved = 1 where ncr_id=id;
		END IF;
        
        SET id = id + 1;   
   END WHILE;

END$$

DELIMITER ;

call tft_approuved();


###
### NonConformiteRequest
### REMOVE COLUMN
####################################################################
ALTER TABLE `ism`.`non_conformite_request` 
DROP FOREIGN KEY `ncr_staffOnRefuse`;
ALTER TABLE `ism`.`non_conformite_request` 
DROP COLUMN `ncr_occuredRefuse`,
DROP COLUMN `ncr_descOnRefuse`,
DROP COLUMN `ncr_staffOnRefuse`,
DROP INDEX `ncr_staffOnRefuse` ;

ALTER TABLE `ism`.`non_conformite_request` 
DROP FOREIGN KEY `ncr_staffOnValidate`;
ALTER TABLE `ism`.`non_conformite_request` 
DROP COLUMN `ncr_occuredValidate`,
DROP COLUMN `ncr_descOnValidate`,
DROP COLUMN `ncr_staffOnValidate`,
DROP INDEX `ncr_staffOnValidate` ;



###
### TFT DATA IN ACTION
### 
####################################################################
DROP procedure IF EXISTS `tft_actions`;
DELIMITER $$
USE `ism`$$
CREATE PROCEDURE `tft_actions` ()
BEGIN
	DECLARE id  INT;
    DECLARE records INT;
	DECLARE str varchar(45);
    
    DECLARE nca_nc 			INT;
    DECLARE nca_staff 		varchar(45);
    DECLARE nca_desc		text;
    DECLARE nca_deadline 	datetime;
    DECLARE nca_created		datetime;
    DECLARE nca_state		varchar(45);
    
    
	SET id = 1;
    SET str = '';
    
    WHILE id < (select COUNT(ncr_id) FROM non_conformite_request) DO
		SET str = (select non_conformite_request.ncr_staffOnAction from non_conformite_request where non_conformite_request.ncr_id=id);

		# SI UN STAFF EST DEFINI
        IF (TRIM(str) <> '') THEN
			SET nca_nc 			= (select non_conformite_request.ncr_id from non_conformite_request where non_conformite_request.ncr_id=id);
            SET nca_staff 		= (select non_conformite_request.ncr_staffOnAction from non_conformite_request where non_conformite_request.ncr_id=id);
            SET nca_desc 		= (select non_conformite_request.ncr_descOnAction from non_conformite_request where non_conformite_request.ncr_id=id);
            SET nca_deadline 	= (select non_conformite_request.ncr_enddingAction from non_conformite_request where non_conformite_request.ncr_id=id);
            SET nca_created		= (select non_conformite_request.ncr_occuredAction from non_conformite_request where non_conformite_request.ncr_id=id);
            SET nca_state		= (select non_conformite_request.ncr_state from non_conformite_request where non_conformite_request.ncr_id=id);
            
            IF nca_state = 'D' THEN
				INSERT INTO `ism`.`non_conformite_actions` (`nca_nc`, `nca_staff`, `nca_description`, `nca_deadline`, `nca_state`, `nca_created`) 
				VALUES (nca_nc, nca_staff, nca_desc, nca_deadline, '3', nca_created);
            ELSEIF nca_state = 'E' THEN
				INSERT INTO `ism`.`non_conformite_actions` (`nca_nc`, `nca_staff`, `nca_description`, `nca_deadline`, `nca_state`, `nca_created`) 
				VALUES (nca_nc, nca_staff, nca_desc, nca_deadline, '4', nca_created);
            ELSE
				INSERT INTO `ism`.`non_conformite_actions` (`nca_nc`, `nca_staff`, `nca_description`, `nca_deadline`, `nca_state`, `nca_created`) 
				VALUES (nca_nc, nca_staff, nca_desc, nca_deadline, '1', nca_created);
            END IF;
        
			
		END IF;
        
        SET id = id + 1;   
   END WHILE;

END$$

DELIMITER ;

call tft_actions();


###
### ALERT NC ACTIONS
### 
####################################################################
ALTER TABLE `ism`.`non_conformite_actions` 
CHANGE COLUMN `nca_action` `nca_descrefuse` TEXT NULL COMMENT 'Référence à la solution ayant aider à la résolution du problème' ,
ADD COLUMN `nca_staffrefuse` VARCHAR(45) NULL AFTER `nca_deadline`;
ALTER TABLE `ism`.`non_conformite_actions` 
ADD CONSTRAINT `nca_staffrefuse`
  FOREIGN KEY (`nca_staff`)
  REFERENCES `ism`.`staff` (`st_staff`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE;
ALTER TABLE `ism`.`non_conformite_actions` 
CHANGE COLUMN `nca_staffrefuse` `nca_staffApprouver` VARCHAR(45) NULL ,
CHANGE COLUMN `nca_descrefuse` `nca_descApprouver` TEXT NULL COMMENT 'Description en cas de refus' ;


###
### REMOVE ACTIONS FUNCTION FROM NON CONFORMITE REQUEST
### 
####################################################################
ALTER TABLE `ism`.`non_conformite_request` 
DROP FOREIGN KEY `ncr_staffOnAction`;
ALTER TABLE `ism`.`non_conformite_request` 
DROP COLUMN `ncr_occuredAction`,
DROP COLUMN `ncr_descOnAction`,
DROP COLUMN `ncr_staffOnAction`,
DROP INDEX `ncr_staffOnAction` ;


