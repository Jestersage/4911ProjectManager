DROP DATABASE pms;
CREATE DATABASE pms;
--
GRANT ALL PRIVILEGES ON pms.* TO atdefault@localhost IDENTIFIED BY 'atdefault';
GRANT ALL PRIVILEGES ON pms.* TO atdefault@"%" IDENTIFIED BY 'atdefault';
--
use pms;
--
ALTER TABLE employee DROP FOREIGN KEY FKemployeePG;
ALTER TABLE employee DROP FOREIGN KEY FKemployeeCred;
ALTER TABLE Timesheet DROP FOREIGN KEY FKTimesheetEmp;
ALTER TABLE TimesheetRow DROP FOREIGN KEY FKTimesheetRow;
ALTER TABLE TimesheetRow DROP FOREIGN KEY FKTimesheetRowWP;
ALTER TABLE WorkPackage DROP FOREIGN KEY FKWPProject;
ALTER TABLE Report DROP FOREIGN KEY FKReportWP;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS TimesheetRow;
DROP TABLE IF EXISTS Timesheet;
DROP TABLE IF EXISTS WorkPackage;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Credentials;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Signatures;
DROP TABLE IF EXISTS HR;
--
CREATE TABLE StatusReport (
  reportID  int(20) NOT NULL,
  packageID int(10) NOT NULL,
  comments  varchar(512),
  CONSTRAINT reportID 
    PRIMARY KEY (reportID));
--
CREATE TABLE WorkPackage (
  packageID    int(10) NOT NULL AUTO_INCREMENT,
  projectID    varchar(20) NOT NULL,
  budgetID     int(10),
  packageNum   varchar(20) NOT NULL,
  employeeID   varchar(10) NOT NULL,
  parentwpID   int(10),
  packageName  varchar(20),
  packageDesc  varchar(20),
  status       tinyint(1),
  CONSTRAINT packageID
    PRIMARY KEY (packageID));
--
CREATE TABLE Project (
  projectID    varchar(20) NOT NULL, 
  ratesheetID  int(10),
  projectName  varchar(255) NOT NULL, 
  description  varchar(255) NOT NULL, 
  startDate    date NOT NULL, 
  endDate      date, 
  status       tinyint(1), 
  employeeID    varchar(10),
  markupValue   double,
  genReport       tinyint(1) NOT NULL,
  CONSTRAINT projectID  
    PRIMARY KEY (projectID)
);
--
CREATE TABLE ProjectSummary(
    reportID     int(10) NOT NULL, 
    projectID    varchar(20) NOT NULL, 
    PRIMARY KEY (reportID)
);
--
-- Budget is for WorkPackage
CREATE TABLE Budget(
    budgetID   int(10) NOT NULL,
    JS int(10),
    SS int(10),
    DS int(10),
    P1 int(10),
    P2 int(10),
    P3 int(10),
    P4 int(10),
    P5 int(10),
    P6 int(10),
    other int(10),
    PRIMARY KEY (budgetId)
);
-- unallocated budget??
--
-- RateSheet is for Project
CREATE TABLE RateSheet(
    ratesheetID int(10) NOT NULL AUTO_INCREMENT,
    year        date,
    JS int(10),
    SS int(10),
    DS int(10),
    P1 int(10),
    P2 int(10),
    P3 int(10),
    P4 int(10),
    P5 int(10),
    P6 int(10),
    other int(10),
    PRIMARY KEY (ratesheetID)
);
--
--
CREATE TABLE TimesheetRow (
  timesheetID    int(10), 
  timesheetrowID int(10) NOT NULL, 
  projectID      varchar(20) NOT NULL, 
  packageID      int(10) NOT NULL, 
  notes          varchar(255), 
  sunday         numeric(4, 2) NOT NULL DEFAULT 0, 
  monday         numeric(4, 2) NOT NULL DEFAULT 0, 
  tuesday        numeric(4, 2) NOT NULL DEFAULT 0, 
  wednesday      numeric(4, 2) NOT NULL DEFAULT 0, 
  thursday       numeric(4, 2) NOT NULL DEFAULT 0, 
  friday         numeric(4, 2) NOT NULL DEFAULT 0, 
  saturday       numeric(4, 2) NOT NULL DEFAULT 0, 
  CONSTRAINT timesheetrowID 
    PRIMARY KEY (timesheetrowID));
--
CREATE TABLE Timesheet (
  timesheetID int(10) NOT NULL AUTO_INCREMENT, 
  employeeID  varchar(10) NOT NULL, 
  weeknumber  int(2) NOT NULL, 
  weekending  date NOT NULL, 
  overtime    numeric(4, 2) NOT NULL DEFAULT 0, 
  flexTime    numeric(4, 2) NOT NULL DEFAULT 0, 
  signed      varchar(255), 
  approved    varchar(255),
  signID      int(10),
  CONSTRAINT timesheetID 
    PRIMARY KEY (timesheetID));
--
CREATE TABLE Signature(
	signId    int(10) NOT NULL PRIMARY KEY ,
	signature TINYBLOB NOT NULL, 
	publicKey BLOB NOT NULL
);
-- salt need to be as long as hashed password
CREATE TABLE Credentials (
  username varchar(255) NOT NULL, 
  password varchar(512) NOT NULL,
  CONSTRAINT username
    PRIMARY KEY (username));
--
CREATE TABLE Employee (
  employeeID   varchar(10) NOT NULL, 
  username     varchar(255) NOT NULL, 
  email        varchar(255) NOT NULL, 
  firstName    varchar(255) NOT NULL, 
  lastName     varchar(255) NOT NULL,
  supervisorID varchar(10),
  approverId   varchar(10),
  payGrade     VARCHAR(2),
  active       bool NOT NULL,
  vacationTime    numeric(4, 2) NOT NULL DEFAULT 0,
  flexTime        numeric(4, 2) NOT NULL DEFAULT 0,
  CONSTRAINT employeeID
    PRIMARY KEY (employeeID)
);
--
CREATE TABLE HR(
    employeeID varchar(10) NOT NULL,
    PRIMARY KEY (employeeID)
);
--
CREATE TABLE PayGrade(
    paygradeID int(10) NOT NULL AUTO_INCREMENT,
    name       varchar(2)   NOT NULL,
    year       int(4)       NOT NULL,
    cost       numeric(4,2) NOT NULL,
    PRIMARY KEY (paygradeID)
);
--
CREATE TABLE ProjectAssignment(
    projectID    varchar(20) NOT NULL, 
    employeeID   varchar(10) NOT NULL, 
    PRIMARY KEY (projectID, employeeID)
);
--
CREATE TABLE WorkAssignment(
    packageID    int(10)     NOT NULL, 
    employeeID   varchar(10) NOT NULL, 
    PRIMARY KEY (packageID, employeeID)
);
--
ALTER TABLE StatusReport ADD CONSTRAINT FKReportWP FOREIGN KEY (packageID) REFERENCES WorkPackage (packageID);
--
ALTER TABLE WorkPackage ADD CONSTRAINT FKSubWorkPackage FOREIGN KEY (parentwpID) REFERENCES WorkPackage (packageID);
ALTER TABLE WorkPackage ADD CONSTRAINT FKWPProject FOREIGN KEY (projectID) REFERENCES Project (projectID);
--
ALTER TABLE TimesheetRow  ADD CONSTRAINT FKTimesheetRow FOREIGN KEY (timesheetID) REFERENCES Timesheet (timesheetID);
ALTER TABLE TimesheetRow ADD CONSTRAINT FKTimesheetRowWP FOREIGN KEY (packageID) REFERENCES WorkPackage (packageID);
ALTER TABLE Timesheet ADD CONSTRAINT FKTimesheetEmp FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
ALTER TABLE Timesheet ADD CONSTRAINT FKsignature FOREIGN KEY (signID) REFERENCES Signature (signID);
--
ALTER TABLE Employee ADD CONSTRAINT FKSupervisor FOREIGN KEY (supervisorID) REFERENCES Employee (employeeID);
ALTER TABLE Employee ADD CONSTRAINT FKApprover FOREIGN KEY (approverID) REFERENCES Employee (employeeID);
ALTER TABLE Employee ADD CONSTRAINT FKemployeeCred FOREIGN KEY (username) REFERENCES Credentials (username);
-- ALTER TABLE Employee ADD CONSTRAINT FKemployeePay FOREIGN KEY (paygradeID) REFERENCES Paygrade (paygradeID);
ALTER TABLE HR ADD CONSTRAINT FKHR FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
--
ALTER TABLE ProjectAssignment ADD CONSTRAINT FKPAPro FOREIGN KEY (projectID) REFERENCES Project (projectID);
ALTER TABLE ProjectAssignment ADD CONSTRAINT FKPAEmp FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
--
--
ALTER TABLE WorkAssignment ADD CONSTRAINT FKWAWork FOREIGN KEY (packageID) REFERENCES WorkPackage (packageID);
ALTER TABLE WorkAssignment ADD CONSTRAINT FKWAEmp FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
--
ALTER TABLE ProjectSummary ADD CONSTRAINT FKPSproj FOREIGN KEY (projectID) REFERENCES Project (projectID);
--
-- ALTER TABLE Budget ADD CONSTRAINT FKBWork FOREIGN KEY (packageID) REFERENCES WorkPackage (packageID);
ALTER TABLE WorkPackage ADD CONSTRAINT FKBWork FOREIGN KEY (budgetID) REFERENCES Budget (budgetID);
--
-- ALTER TABLE RateSheet ADD CONSTRAINT FKRSProj FOREIGN KEY (projectID) REFERENCES Project (projectID);
ALTER TABLE Project ADD CONSTRAINT FKRSProj FOREIGN KEY (ratesheetID) REFERENCES Ratesheet (ratesheetID);
