-- SAMPLE DATA for
-- Project
-- WorkPackage
--
--
-- ASSIGN WORK PACKAGES TO EMPLOYEES
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
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (6, 1202, "AB-1", 123456, 5, 2);
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (7, 1202, "AB-2", 123456, 5, 1);
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
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (93, 999, "9B", 999, 91, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (94, 999, "9C", 333, 91, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (95, 999, "9C", 999, 91, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (96, 999, "9D", 555, 91, 1);
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
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (82, 888, "8B", 888, 81, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (83, 888, "8C", 888, 81, 1);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (84, 888, "8X", 333, 81, 1);
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
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (73, 777, "7B", 555, 71, 2);
--
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (74, 777, "7B", 777, 71, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (75, 777, "7C", 777, 71, 1);
--  
-- SAMPLE PROJECT for adding TIMESHEETS to
-- (Dates reflect accurate dates)
-- 
INSERT INTO Project
(projectID, projectName, description, startDate, endDate, budget, status, employeeID, genReport)
VALUES
  (111, "111 project name", "111 description.", "2015-01-01", "2015-02-06", 9000, 1, 999, TRUE);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (11, 111, "1A", 4);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID)
VALUES
  (12, 111, "1A", 4);
--  
INSERT INTO WorkPackage
  (packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (13, 111, "9B", 4, 11, 2);
--  
INSERT INTO WorkPackage
(packageID, projectID, packageNum, employeeID, parentwpID, status)
VALUES
  (14, 111, "9C", 4, 11, 1);
--