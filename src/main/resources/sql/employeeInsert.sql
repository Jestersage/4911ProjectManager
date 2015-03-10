--
-- Sample Employeed data
--
-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
-- READ FIRST
-- CHANGES
-- Mar 8 2015 -- removed PaygradeID on employee tables
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("root", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, active) 
VALUES 
  (000001, "root", "gleung4@gmail.com", "CEO", "Admin", 1 );
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
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (123456, "gabeh", "gabeh3264@gmail.com", "Gabe", "Hung", 000001, 1);
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
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (124816, "georgel", "gleung4@gmail.com", "George", "Leung", 123456, 1);
--
--
-- Sample Employees (-Finlay)
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("bobDole", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000002, "bobDole", "bdole@email.com", "Bob", "Dole", 000001, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fredBarns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000003, "fredBarns", "fredb@email.com", "Frederic", "Barns", 000001, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("rBurns", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade)
VALUES
    (000004, "rBurns", "bdole@email.com", "Robet", "Dolenson", 000001, 1, "P1");
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("hSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000005, "hSimpson", "homerS@email.com", "Homer", "Simpson", 000004, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("magSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000006, "magSimpson", "magS@email.com", "Maggie", "Simpson", 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("margSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000007, "margSimpson", "margS@email.com", "Marge", "Simpson", 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("bSimpson", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000008, "bSimpson", "bartS@email.com", "Bart", "Simpson", 000005, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fArbuckle", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000009, "fArbuckle", "fredA@email.com", "Fred", "Arbuckle", 000004, 1);    
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("garfield", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000010, "garfield", "garf@email.com", "Garfield", "Arbuckle", 000009, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("odie", "password");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000011, "odie", "odie@email.com", "Odie", "Arbuckle", 000009, 1);
--
--
--
-- New Employees for Testing
-- Work Packages, etc
-- 
-- Employee 333
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("333", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (333, "333", "3@3.com", "3", "33", 123456, 1);
--  
-- Employee 555
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("555", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (555, "555", "5@5.com", "5", "55", 123456, 1);
--  
-- Employee 999
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("999", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (999, "999", "9@9.com", "9", "99", 123456, 1);
--  
-- Employee 888
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("888", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (888, "888", "8@8.com", "8", "88", 123456, 1);
--  
-- Employee 777
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("777", "password");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (777, "777", "7@7.com", "7", "77", 123456, 1);
--
