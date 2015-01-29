--INSERT / C
INSERT INTO Paygrade
  (paygradeID, 
  name) 
VALUES 
  (09, 
  "XX");
INSERT INTO permissionAssign
  (permissionID, 
  roleID) 
VALUES 
  (2, 
  2);
INSERT INTO roleAssign
  (roleID, 
  employeeID) 
VALUES 
  (1, 
  000001);
INSERT INTO Permission
  (permissionID, 
  name) 
VALUES 
  (2, 
  "Medium ACCESS");
INSERT INTO Role
  (roleID, 
  name) 
VALUES 
  (03, 
  worker);
INSERT INTO employee
  (employeeID, 
  username, 
  password, 
  email, 
  firstName, 
  lastName, 
  payGrade, 
  supervisorID) 
VALUES 
  (124816, 
  georgel, 
  pw1, 
  gleung4@gmail.com, 
  George, 
  Leung, 
  03, 
  123456);
--
--SELECT / R
SELECT paygradeID, name 
  FROM Paygrade;
SELECT permissionID, roleID 
  FROM permissionAssign;
SELECT roleID, employeeID 
  FROM roleAssign;
SELECT permissionID, name 
  FROM Permission;
SELECT roleID, name 
  FROM Role;
SELECT employeeID, username, password, email, firstName, lastName, payGrade, supervisorID 
  FROM employee;
--
--UPDATE
UPDATE Paygrade SET 
  name = "ZZ" 
WHERE
  paygradeID = 09;
UPDATE permissionAssign SET 
   
WHERE
  permissionID = 1 AND roleID = 1;
UPDATE roleAssign SET 
   
WHERE
  roleID = 1 AND employeeID = 123456;
UPDATE Permission SET 
  name = "Supervisor" 
WHERE
  permissionID = 1;
UPDATE Role SET 
  name = "Supervisor" 
WHERE
  roleID = 5;
UPDATE employee SET 
  username = "abc", 
  password = "123", 
  email = "abc@abc.com", 
  firstName = "hello", 
  lastName = "world", 
  payGrade = "YY", 
  supervisorID = 000002 
WHERE
  employeeID = 124816;
--
--DELETE
DELETE FROM Paygrade 
  WHERE paygradeID = 09;
DELETE FROM permissionAssign 
  WHERE permissionID = 2 AND roleID = 2;
DELETE FROM roleAssign 
  WHERE roleID = 1 AND employeeID = 000001;
DELETE FROM Permission 
  WHERE permissionID = 2;
DELETE FROM Role 
  WHERE roleID = 1;
DELETE FROM employee 
  WHERE employeeID = 124816;