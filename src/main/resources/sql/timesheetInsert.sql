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
-- - - - - - - -- - 
-- PROJECT ID: 111
-- Week 1, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    overtime, flexTime, approved,
    signID)
VALUES
  (1, 1, 1, '2015-01-02',
  0, 0, true,
  01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (1, 1, 111, 5,
    "notes", 0, 0, 0, 
    0, 8, 8, 0);
--
-- Week 2, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    overtime, flexTime, approved, signID)
VALUES
  (2, 2, 2, '2015-01-09',
   0, 0, true, 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (2, 2, 111, 6,
     "notes", 0, 8, 8,
     8, 8, 8, 0);
--
-- Week 3, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    overtime, flexTime, approved, signID)
VALUES
    (3, 3, 3,'2015-01-16',
    3, 2, true, 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (3, 3, 111, 7,
     "notes", 0, 8, 8,
     8, 8, 8, 3);
--  
-- Week 4, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    overtime, flexTime, approved, signID)
VALUES
  (4, 123456, 4,'2015-01-23', 
  8, 0, true, 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
    notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (4, 4, 111, 5,
     "notes", 0, 9, 9,
     9, 9, 9, 3);
--
-- Week 5, Employee 4
INSERT INTO Timesheet
  (timesheetID, employeeID, weeknumber, weekending,
    overtime, flexTime, approved,
    signID)
VALUES
    (5, 124816, 5,'2015-01-23',
    8, 0, true, 01);
INSERT INTO TimesheetRow
    (timesheetID, timesheetrowID, projectID, packageID,
     notes, sunday, monday, tuesday,
     wednesday, thursday, friday, saturday)
VALUES
    (5, 5, 111, 6,
     "notes", 0, 9, 9,
     8, 8, 8, 0);
--
-- Pending timsheets
-- Week 1, (empId 4)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (6, 4, 1, '2015-01-02',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (6, 6, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- Week 2, (empId 4)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (7, 4, 1, '2015-01-09',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (7, 7, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- Week 3, (empId 5)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (8, 5, 1, '2015-01-16',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (8, 8, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- Week 3, (empId 4)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (9, 4, 1, '2015-01-16',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (9, 9, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- Week 4, (empId 4)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (10, 4, 1, '2015-01-23',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (10, 10, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- Week 4, (empId 4)
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (11, 5, 1, '2015-01-23',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (11, 11, 111, 6,
   "notes", 0, 8, 8,
   8, 8, 8, 0);
--
-- SAMPLE DATA FOR root USER    --
INSERT INTO Timesheet
(timesheetID, employeeID, weeknumber, weekending,
 overtime, flexTime, approved, signID)
VALUES
  (666, 1, 14, '2015-04-03',
   0, 0, NULL, 01);
INSERT INTO TimesheetRow
(timesheetID, timesheetrowID, projectID, packageID,
 notes, sunday, monday, tuesday,
 wednesday, thursday, friday, saturday)
VALUES
  (666, 666, 1202, 5,
   "notes", 0, 8, 0,
   8, 8, 8, 0),
   (666, 667, 1202, 6,
   "notes", 0, 0, 8,
   0, 0, 0, 0),
   (666, 668, 1202, 
   7,"notes", 0, 0, 8,
   0, 0, 0, 0);
--