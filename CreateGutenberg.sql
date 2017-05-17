DROP DATABASE IF EXISTS `gutenberg`;

CREATE SCHEMA `gutenberg` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `gutenberg`.`Books` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(6000) NOT NULL,
  PRIMARY KEY (`ID`));

CREATE TABLE `gutenberg`.`Authors` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`));

CREATE TABLE `gutenberg`.`Books_Authors` (
  `Book_ID` INT NOT NULL,
  `Author_ID` INT NOT NULL,
  INDEX `Books_ID_idx` (`Book_ID` ASC),
  INDEX `Authors_ID_idx` (`Author_ID` ASC),
  CONSTRAINT `Books_ID`
    FOREIGN KEY (`Book_ID`)
    REFERENCES `gutenberg`.`Books` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Authors_ID`
    FOREIGN KEY (`Author_ID`)
    REFERENCES `gutenberg`.`Authors` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `gutenberg`.`Cities` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NOT NULL,
  `Geolat` FLOAT(10,6) NULL,
  `Geolng` FLOAT(10,6) NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `gutenberg`.`Books_Cities` (
  `Book_ID` INT NOT NULL,
  `City_ID` INT NOT NULL,
  INDEX `Book_ID_idx` (`Book_ID` ASC),
  INDEX `City_ID_idx` (`City_ID` ASC),
  CONSTRAINT `Book_ID`
    FOREIGN KEY (`Book_ID`)
    REFERENCES `gutenberg`.`Books` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `City_ID`
    FOREIGN KEY (`City_ID`)
    REFERENCES `gutenberg`.`Cities` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
