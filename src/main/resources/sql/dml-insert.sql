-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
--
--
INSERT INTO Credential
  (username, password) 
VALUES 
  ("root", "password");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, payGrade, supervisorID, active)
VALUES 
  (000001, "root", "gleung4@gmail.com", "CEO", "Admin", 01, 000001, 1 );
INSERT INTO roleAssign
  (roleID, employeeID) 
VALUES 
  (1, 000001);
--
--
INSERT INTO Credential
  (username, password) 
VALUES 
  ("gabeh", "password");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, payGrade, supervisorID, active)
VALUES
  (123456, "gabeh", "gabeh3264@gmail.com", "Gabe", "Hung", 02, 000001, 1);
INSERT INTO roleAssign
  (roleID, employeeID) 
VALUES 
  (1, 123456);
--
--
INSERT INTO Credential
  (username, password) 
VALUES 
  ("georgel", "password");
INSERT INTO employee
  (employeeID, username, email, firstName, lastName, payGrade, supervisorID, active)
VALUES
  (124816, "georgel", "gleung4@gmail.com", "George", "Leung", 03, 123456, 1);
INSERT INTO roleAssign
  (roleID, employeeID) 
VALUES 
  (2, 124816);
