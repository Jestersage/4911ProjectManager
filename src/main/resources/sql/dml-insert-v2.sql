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
--
INSERT INTO Project
  (projectID, projectName, description, startDate, status, pmEmployeeID, genReport)
VALUES
  (010, "Admin", "Administrative items.", "2015-01-01", 1, 000001, FALSE);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID)
VALUES
  (1, 010, "SICK", 000001);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID)
VALUES
  (2, 010, "VACN", 000001);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID)
VALUES
  (3, 010, "SHOL", 000001);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID)
VALUES
  (4, 010, "FLEX", 000001);
--
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, pmEmployeeID, genReport)
VALUES
  (1202, "BlackBerry", "BlackBerry was formally know as RIM.", "2015-02-15", "2215-08-30", 10000, 1, 123456, TRUE);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID)
VALUES
  (5, 1202, "AB", 123456);
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, wpEmployeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (6, 1202, "AB-1", 123456, 5000, 5000, 5, 2);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, wpEmployeeID, estimateCost, actualCost, parentwpID, status)
VALUES
  (7, 1202, "AB-2", 123456, 5000, 4500, 5, 1);