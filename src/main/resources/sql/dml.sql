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
