-- -----------------------
-- Timesheet Sample Data
-- - - - - - - - - - - - 
-- 1) Source dbCreate.sql
-- 2) Source employeeInsert.sql
-- 3) Source timesheetInsert.sql
-- - - - - - - - - - - - 
-- 
-- Sample Signature data
--
INSERT INTO Signature
    (signId, signature, publicKey)
VALUES
    (01, 'SIGNATURE', 'PUBLICKEY');
--
-- SAMPLE Timesheets, and TimesheetRows
--
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID) 
VALUES
  (1, 4, 1, '2015-01-09', 16, 0, 0, 
    "Signed", "approved", 01);
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (777, "777 project name", "777 description.", "2015-07-07", "2016-07-07", 7000, 1, 123456, TRUE);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (71, 777, "7A", 555);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     weekending, total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
--    
--
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID) 
VALUES
  (2, 4, 2, '2015-01-09', 40, 0, 0, 
    "Signed", "approved", 01)
--
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
(3, 4, 3,'2015-01-16', 40, 3, 2,
    "Signed", "approved", 01)
--  
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
  (4, 4, 4,'2015-01-23', 40, 8, 0,
    "Signed", "approved", 01)
--
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
    (4, 4, 5,'2015-01-23', 40, 8, 0,
    "Signed", "approved", 01)