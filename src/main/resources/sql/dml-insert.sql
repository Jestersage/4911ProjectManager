-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
--
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("root", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, active) 
VALUES 
  (000001, "root", "gleung4@gmail.com", "CEO", "Admin", 01, 1 );
INSERT INTO HR
  (employeeID)
VALUES
  (000001);
--
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("gabeh", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (123456, "gabeh", "gabeh3264@gmail.com", "Gabe", "Hung", 02, 000001, 1);
INSERT INTO HR
  (employeeID)
VALUES
  (123456);
--
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("georgel", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (124816, "georgel", "gleung4@gmail.com", "George", "Leung", 03, 123456, 1);

------------------------
-- Sample Employeed data
------------------------
INSERT INTO Credentials
    (username, password)
VALUES
    ("bobDole", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000002, "bobDole", "bdole@email.com", "Bob", "Dole", 01, 000001, 1);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("fredBarns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000003, "fredBarns", "fredb@email.com", "Frederic", "Barns", 03, 000001, 1);

INSERT INTO Credentials
    (username, password)
VALUES
    ("rBurns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000004, "rBurns", "bdole@email.com", "Robet", "Dolenson", 01, 000001, 1);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("hSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000005, "hSimpson", "homerS@email.com", "Homer", "Simpson", 03, 000004, 1);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("magSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000006, "magSimpson", "magS@email.com", "Maggie", "Simpson", 02, 000004, 0);
    
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("margSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000007, "margSimpson", "margS@email.com", "Marge", "Simpson", 01, 000004, 0);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("bSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000008, "bSimpson", "bartS@email.com", "Bart", "Simpson", 03, 000005, 1);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("fArbuckle", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000009, "fArbuckle", "fredA@email.com", "Fred", "Arbuckle", 02, 000004, 1);    

INSERT INTO Credentials
    (username, password)
VALUES
    ("garfield", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000010, "garfield", "garf@email.com", "Garfield", "Arbuckle", 03, 000009, 1);
    
INSERT INTO Credentials
    (username, password)
VALUES
    ("odie", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000011, "odie", "odie@email.com", "Odie", "Arbuckle", 02, 000009, 1);
--
--
INSERT INTO Project
  (projectID, projectName, description, startDate, status, employeeID, genReport)
VALUES
  (010, "Admin", "Administrative items.", "2015-01-01", 1, 000001, FALSE);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (1, 010, "SICK", 000001);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID)
VALUES
  (2, 010, "VACN", 000001);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (3, 010, "SHOL", 000001);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (4, 010, "FLEX", 000001);
------------------------------
-- END Sample Employee Data --
------------------------------

  
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (1202, "BlackBerry", "BlackBerry was formally know as RIM.", "2015-02-15", "2215-08-30", 10000, 1, 123456, TRUE);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (5, 1202, "AB", 123456);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (6, 1202, "AB-1", 123456, 5000, 5000, 5, 2);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (7, 1202, "AB-2", 123456, 5000, 4500, 5, 1);
  
  
--  data for RateSheet and Budget Entities in Database 
-- 	assuming that the same employee can work in different work packages
-- 	RateSheet - Project 

INSERT INTO RateSheet
	( projectID, year, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 010, '2015-01-01', 2, 3, 2, 4, 5, 3, 1, 0, 4, 1);

INSERT INTO RateSheet
	( projectID, year, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 1202, '2015-01-01', 1, 4, 3, 6, 1, 4, 4, 3, 1, 2);


-- Budget - Workpackage

-- PROJECT 010

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 1, 1, 2, 0, 1, 2, 2, 0, 0, 1, 0 );

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 2, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1 );

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 3, 0, 1, 1, 1, 1, 1, 0, 0, 2, 0 );

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 4, 0, 0, 1, 1, 2, 1, 1, 0, 1, 0 );


-- PROJECT 1202

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 5, 1, 1, 1, 2, 0, 0, 2, 2, 0, 0 );

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 6, 1, 2, 1, 1, 1, 2, 0, 0, 1, 1 );

INSERT INTO Budget
	( packageID, JS, SS, DS, P1, P2, P3, P4, P5, P6, other )
VALUES 
	( 7, 1, 1, 2, 3, 0, 2, 2, 1, 0, 1 );

