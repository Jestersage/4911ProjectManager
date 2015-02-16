-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
--
--
INSERT INTO Credentials
  (username, password, salt) 
VALUES 
  ("root", "password", "");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active, role) 
VALUES 
  (000001, "root", "gleung4@gmail.com", "CEO", "Admin", 01, 000001, 1, 1 );
--
--
INSERT INTO Credentials
  (username, password, salt) 
VALUES 
  ("gabeh", "password", "");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active, role) 
VALUES
  (123456, "gabeh", "gabeh3264@gmail.com", "Gabe", "Hung", 02, 000001, 1, 1);
--
--
INSERT INTO Credentials
  (username, password, salt) 
VALUES 
  ("georgel", "password", "");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active, role) 
VALUES
  (124816, "georgel", "gleung4@gmail.com", "George", "Leung", 03, 123456, 1, 2);