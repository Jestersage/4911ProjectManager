--
-- Sample Employeed data
--
-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
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