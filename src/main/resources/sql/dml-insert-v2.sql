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
  