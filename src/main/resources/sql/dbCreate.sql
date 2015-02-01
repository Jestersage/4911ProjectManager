DROP DATABASE pms;
CREATE DATABASE pms;
--
GRANT ALL PRIVILEGES ON pms.* TO username@localhost IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON pms.* TO username@"%" IDENTIFIED BY 'password';
--
use pms;
--
ALTER TABLE employee DROP FOREIGN KEY FKemployee160589;
ALTER TABLE permissionAssign DROP FOREIGN KEY FKpermission315525;
ALTER TABLE permissionAssign DROP FOREIGN KEY FKpermission966818;
ALTER TABLE roleAssign DROP FOREIGN KEY FKroleAssign822707;
ALTER TABLE roleAssign DROP FOREIGN KEY FKroleAssign974720;
ALTER TABLE employee DROP FOREIGN KEY FKemployee427530;
ALTER TABLE employee DROP FOREIGN KEY FKemployee94085;
DROP TABLE IF EXISTS Credential;
DROP TABLE IF EXISTS Paygrade;
DROP TABLE IF EXISTS permissionAssign;
DROP TABLE IF EXISTS roleAssign;
DROP TABLE IF EXISTS Permission;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS employee;
--
CREATE TABLE Credential (
  username varchar(255) NOT NULL, 
  password varchar(512) NOT NULL, 
  PRIMARY KEY (username));
CREATE TABLE Paygrade (
  paygradeID int(10) NOT NULL, 
  name       varchar(10) NOT NULL, 
  CONSTRAINT paygradeID 
    PRIMARY KEY (paygradeID));
CREATE TABLE permissionAssign (
  permissionID int(10) NOT NULL, 
  roleID       int(10) NOT NULL, 
  PRIMARY KEY (permissionID, 
  roleID));
CREATE TABLE roleAssign (
  roleID     int(10) NOT NULL, 
  employeeID varchar(10) NOT NULL, 
  CONSTRAINT `roleID,employeeID` 
    PRIMARY KEY (roleID, 
  employeeID));
CREATE TABLE Permission (
  permissionID int(10) NOT NULL, 
  name         varchar(255) NOT NULL, 
  CONSTRAINT permissionID 
    PRIMARY KEY (permissionID));
CREATE TABLE Role (
  roleID int(10) NOT NULL, 
  name   varchar(255) NOT NULL, 
  PRIMARY KEY (roleID));
CREATE TABLE employee (
  employeeID   varchar(10) NOT NULL, 
  username     varchar(255) NOT NULL, 
  email        varchar(255) NOT NULL, 
  firstName    varchar(255) NOT NULL, 
  lastName     varchar(255) NOT NULL, 
  payGrade     int(10) NOT NULL, 
  supervisorID varchar(10) NOT NULL, 
  active       int(1) NOT NULL, 
  CONSTRAINT employeeID 
    PRIMARY KEY (employeeID));
ALTER TABLE employee ADD INDEX FKemployee160589 (payGrade), ADD CONSTRAINT FKemployee160589 FOREIGN KEY (payGrade) REFERENCES Paygrade (paygradeID);
ALTER TABLE permissionAssign ADD INDEX FKpermission315525 (roleID), ADD CONSTRAINT FKpermission315525 FOREIGN KEY (roleID) REFERENCES Role (roleID);
ALTER TABLE permissionAssign ADD INDEX FKpermission966818 (permissionID), ADD CONSTRAINT FKpermission966818 FOREIGN KEY (permissionID) REFERENCES Permission (permissionID);
ALTER TABLE roleAssign ADD INDEX FKroleAssign822707 (roleID), ADD CONSTRAINT FKroleAssign822707 FOREIGN KEY (roleID) REFERENCES Role (roleID);
ALTER TABLE roleAssign ADD INDEX FKroleAssign974720 (employeeID), ADD CONSTRAINT FKroleAssign974720 FOREIGN KEY (employeeID) REFERENCES employee (employeeID);
ALTER TABLE employee ADD INDEX FKemployee427530 (supervisorID), ADD CONSTRAINT FKemployee427530 FOREIGN KEY (supervisorID) REFERENCES employee (employeeID);
ALTER TABLE employee ADD INDEX FKemployee94085 (username), ADD CONSTRAINT FKemployee94085 FOREIGN KEY (username) REFERENCES Credential (username);