--FOR ADDING VARIABLE RECORDS
--
--
INSERT INTO employee
  (employeeID, email, firstName, lastName, payGrade, supervisorID, username) 
VALUES
  (124816, "gleung4@gmail.com", "George", "Leung", 03, 123456, "georgel");
INSERT INTO Credential
  (username, password) 
VALUES 
  ("georgel", "password");
--
--
INSERT INTO employee
  (employeeID, email, firstName, lastName, payGrade, supervisorID, username) 
VALUES
  (123456, "gabeh3264@gmail.com", "Gabe Hung", "Leung", 02, 000001, "gabeh");
INSERT INTO Credential
  (username, password) 
VALUES 
  ("gabeh", "password");
--
--
INSERT INTO employee
  (employeeID, email, firstName, lastName, payGrade, supervisorID, username) 
VALUES
  (000001, "gleung4@gmail.com", "CEO", "Admin", 01, 000001, "root");
INSERT INTO Credential
  (username, password) 
VALUES 
  ("root", "password");
--
--
INSERT INTO roleAssign
  (roleID, employeeID) 
VALUES 
  (1, 123456);