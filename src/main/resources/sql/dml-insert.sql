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
--
-- Sample Employeed data
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("bobDole", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000002, "bobDole", "bdole@email.com", "Bob", "Dole", 01, 000001, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fredBarns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000003, "fredBarns", "fredb@email.com", "Frederic", "Barns", 03, 000001, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("rBurns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000004, "rBurns", "bdole@email.com", "Robet", "Dolenson", 01, 000001, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("hSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000005, "hSimpson", "homerS@email.com", "Homer", "Simpson", 03, 000004, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("magSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000006, "magSimpson", "magS@email.com", "Maggie", "Simpson", 02, 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("margSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000007, "margSimpson", "margS@email.com", "Marge", "Simpson", 01, 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("bSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000008, "bSimpson", "bartS@email.com", "Bart", "Simpson", 03, 000005, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fArbuckle", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000009, "fArbuckle", "fredA@email.com", "Fred", "Arbuckle", 02, 000004, 1);    
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("garfield", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active)
VALUES
    (000010, "garfield", "garf@email.com", "Garfield", "Arbuckle", 03, 000009, 1);
--
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
--
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
--
--
-- 
-- ASSIGN WORK PACKAGES TO EMPLOYEES
-- 
--
-- New Employees for Testing
--
-- Employee 333
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("333", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (333, "333", "3@3.com", "3", "33", 03, 123456, 1);
--  
-- Employee 555
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("555", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (555, "555", "5@5.com", "5", "55", 03, 123456, 1);
--  
-- Employee 999
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("999", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (999, "999", "9@9.com", "9", "99", 03, 123456, 1);
--  
-- Employee 888
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("888", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (888, "888", "8@8.com", "8", "88", 03, 123456, 1);
--  
-- Employee 777
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("777", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active) 
VALUES
  (777, "777", "7@7.com", "7", "77", 03, 123456, 1);
--  
-- Assigning Project/WP to Newly Created Employees
--
-- Employee 333 and 999 // Project 999 & Work Packages
--
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (999, "999 project name", "999 description.", "2015-09-09", "2016-09-09", 9000, 1, 999, TRUE);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (91, 999, "9A", 333);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (92, 999, "9A", 999);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (93, 999, "9B", 999, 999, 9001, 91, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (94, 999, "9C", 333, 999, 9002, 91, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (95, 999, "9C", 999, 999, 9002, 91, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (96, 999, "9D", 555, 999, 9002, 91, 1);
--  
-- Project 888 & Work Packages
--  
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (888, "888 project name", "888 description.", "2015-08-08", "2016-08-08", 8000, 1, 123456, TRUE);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (81, 888, "8A", 888);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (82, 888, "8B", 888, 888, 8001, 81, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (83, 888, "8C", 888, 888, 8002, 81, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (84, 888, "8X", 333, 888, 8002, 81, 1);
--  
-- Employee 555 and 777 // Project 777 & Work Packages
--
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (777, "777 project name", "777 description.", "2015-07-07", "2016-07-07", 7000, 1, 123456, TRUE);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (71, 777, "7A", 555);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (72, 777, "7A", 777);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (73, 777, "7B", 555, 777, 7001, 71, 2);
--
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (74, 777, "7B", 777, 777, 7001, 71, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (75, 777, "7C", 777, 777, 7002, 71, 1);
--  
--