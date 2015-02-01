--
-- SELECT / R
SELECT username, password 
  FROM Credential;
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
SELECT employeeID, username, email, firstName, lastName, payGrade, supervisorID, active 
  FROM employee;
--
-- UPDATE / U
UPDATE Credential SET password='P@$$w0rd' 
    WHERE username = 'georgel';
--
UPDATE roleAssign SET roleID = 1
WHERE
  employeeID = 124816;
--
-- feel free to add the right field
UPDATE employee SET 
  email = 'gleung4@hotmail.com', 
  firstName = 'George gh', 
  lastName = 'Leung', 
  supervisorID = 000001, 
  payGrade = 1,
  active = 0 
WHERE
  employeeID = 124816;
--
--
--DELETE -- Variables only
--Order: deelete roleAssign, SELECT username
DELETE from roleAssign
  WHERE employeeID = 124816;
SELECT username
  FROM employee 
  WHERE employeeID = 124816;
--store username
DELETE from employee
  WHERE employeeID = 124816;
DELETE from Credential
  WHERE username = 'georgel';
