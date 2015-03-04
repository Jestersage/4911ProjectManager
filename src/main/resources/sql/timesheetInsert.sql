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
<<<<<<< HEAD
-- - - - - - - -- - 
-- PROJECT ID: 111
-- Week 1, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
  (1, 4, 1, '2015-01-02', 16, 0, 0, 
    "Signed", "approved", 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (1, 1, 111, 11, 16, "notes",
     0, 0, 0, 0, 8, 8, 0);
--
-- Week 2, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved, signID) 
VALUES
  (2, 4, 2, '2015-01-09', 
   40, 0, 0, "Signed", "approved", 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (2, 2, 111, 11,
     40, "notes", 0, 8, 8,
     8, 8, 8, 0);
--
-- Week 3, Employee 4
=======
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
>>>>>>> Adding timesheet sample data
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
<<<<<<< HEAD
    (3, 4, 3,'2015-01-16', 40, 3, 2,
    "Signed", "approved", 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (3, 3, 111, 11,
     43, "notes", 0, 8, 8,
     8, 8, 8, 3);
--  
-- Week 4, Employee 4
=======
(3, 4, 3,'2015-01-16', 40, 3, 2,
    "Signed", "approved", 01)
--  
>>>>>>> Adding timesheet sample data
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
  (4, 4, 4,'2015-01-23', 40, 8, 0,
<<<<<<< HEAD
    "Signed", "approved", 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (4, 4, 111, 11,
     48, "notes", 0, 9, 9,
     9, 9, 9, 3);
--
-- Week 5, Employee 4
=======
    "Signed", "approved", 01)
--
>>>>>>> Adding timesheet sample data
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    grandTotal, overtime, flexTime, signed, approved,
    signID)
VALUES
<<<<<<< HEAD
    (5, 4, 5,'2015-01-23', 40, 8, 0,
    "Signed", "approved", 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     total, notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (5, 5, 111, 11,
     48, "notes", 0, 9, 9,
     8, 8, 8, 0);
--
=======
    (4, 4, 5,'2015-01-23', 40, 8, 0,
    "Signed", "approved", 01)
>>>>>>> Adding timesheet sample data
