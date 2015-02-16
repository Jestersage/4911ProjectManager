DROP DATABASE pms;
CREATE DATABASE pms;
--
GRANT ALL PRIVILEGES ON pms.* TO atdefault@localhost IDENTIFIED BY 'atdefault';
GRANT ALL PRIVILEGES ON pms.* TO atdefault@"%" IDENTIFIED BY 'atdefault';
--
use pms;
--
ALTER TABLE SubWorkPackage DROP FOREIGN KEY FKSubWorkPackage;
ALTER TABLE employee DROP FOREIGN KEY FKemployeePG;
ALTER TABLE employee DROP FOREIGN KEY FKemployeeCred;
ALTER TABLE Timesheet DROP FOREIGN KEY FKTimesheetEmp;
ALTER TABLE TimesheetRow DROP FOREIGN KEY FKTimesheetRow;
ALTER TABLE WorkPackage DROP FOREIGN KEY FKWPProject;
ALTER TABLE TimesheetRow DROP FOREIGN KEY FKTimesheetRowWP;
ALTER TABLE Report DROP FOREIGN KEY FKReportProject;
DROP TABLE IF EXISTS SubWorkPackage;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS TimesheetRow;
DROP TABLE IF EXISTS Timesheet;
DROP TABLE IF EXISTS WorkPackage;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Credentials;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Signatures;
--
CREATE TABLE SubWorkPackage (
  workPackageID int(10) NOT NULL);
CREATE TABLE Report (
  projectID varchar(20) NOT NULL, 
  CONSTRAINT projectID 
    PRIMARY KEY (projectID));
CREATE TABLE TimesheetRow (
  timesheetID    int(10), 
  timesheetrowID int(10) NOT NULL AUTO_INCREMENT, 
  projectID      varchar(20) NOT NULL, 
  packageID      int(10) NOT NULL, 
  weekending     date NOT NULL, 
  total          numeric(20, 4) NOT NULL, 
  notes          varchar(255) NOT NULL, 
  sunday         numeric(2, 2) DEFAULT 0, 
  monday         numeric(2, 2), 
  tuesday        numeric(2, 2), 
  wednesday      numeric(2, 2), 
  thursday       numeric(2, 2), 
  friday         numeric(2, 2), 
  saturday       numeric(2, 2), 
  CONSTRAINT rowID 
    PRIMARY KEY (timesheetrowID));
CREATE TABLE Timesheet (
  timesheetID int(10) NOT NULL AUTO_INCREMENT, 
  employeeID  varchar(10) NOT NULL, 
  weeknumber  int(2) NOT NULL, 
  weekending  date NOT NULL, 
  grandTotal  numeric(2, 2) NOT NULL, 
  overtime    numeric(2, 2), 
  flexTime    numeric(2, 2), 
  signed      varchar(255), 
  approved    varchar(255), 
  CONSTRAINT timesheetID 
    PRIMARY KEY (timesheetID));
CREATE TABLE WorkPackage (
  packageID    int(10) NOT NULL AUTO_INCREMENT, 
  projectID    varchar(20) NOT NULL, 
  wpEmployeeID varchar(10) NOT NULL, 
  estimateCost numeric(20, 4), 
  actualCost   numeric(20, 4), 
  PRIMARY KEY (packageID));
CREATE TABLE Project (
  projectID    varchar(20) NOT NULL, 
  projectName  varchar(255) NOT NULL, 
  description  varchar(255) NOT NULL, 
  startDate    date NOT NULL, 
  endDate      date NOT NULL, 
  budget       numeric(20, 4) NOT NULL, 
  status       varchar(255) NOT NULL, 
  pmEmployeeID varchar(10) NOT NULL, 
  PRIMARY KEY (projectID));
-- salt need to be as long as hashed password
CREATE TABLE Credentials (
  username varchar(255) NOT NULL, 
  password varchar(512) NOT NULL,
  salt     varchar(512) NOT NULL,
  PRIMARY KEY (username));
CREATE TABLE Employee (
  employeeID   varchar(10) NOT NULL, 
  username     varchar(255) NOT NULL, 
  email        varchar(255) NOT NULL, 
  firstName    varchar(255) NOT NULL, 
  lastName     varchar(255) NOT NULL, 
  paygradeID   varchar(10) NOT NULL, 
  supervisorID varchar(10) NOT NULL, 
  active       int(1) NOT NULL, 
  role         int(2) NOT NULL, 
  CONSTRAINT employeeID 
    PRIMARY KEY (employeeID));
CREATE TABLE Signatures(
	SignId INT NOT NULL PRIMARY KEY ,
	Signature TINYBLOB NOT NULL, 
	PublicKey BLOB NOT NULL
);
ALTER TABLE SubWorkPackage ADD CONSTRAINT FKSubWorkPackage FOREIGN KEY (workPackageID) REFERENCES WorkPackage (packageID);
ALTER TABLE employee ADD CONSTRAINT FKemployeeCred FOREIGN KEY (username) REFERENCES Credentials (username);
ALTER TABLE Timesheet ADD CONSTRAINT FKTimesheetEmp FOREIGN KEY (employeeID) REFERENCES employee (employeeID);
ALTER TABLE TimesheetRow  ADD CONSTRAINT FKTimesheetRow FOREIGN KEY (timesheetID) REFERENCES Timesheet (timesheetID);
ALTER TABLE WorkPackage ADD CONSTRAINT FKWPProject FOREIGN KEY (projectID) REFERENCES Project (projectID);
ALTER TABLE TimesheetRow ADD CONSTRAINT FKTimesheetRowWP FOREIGN KEY (packageID) REFERENCES WorkPackage (packageID);
ALTER TABLE Report ADD CONSTRAINT FKReportProject FOREIGN KEY (projectID) REFERENCES Project (projectID);
