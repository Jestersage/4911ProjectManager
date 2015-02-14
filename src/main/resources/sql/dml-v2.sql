--
-- SELECT / R
SELECT username, password 
  FROM Credentials;
SELECT paygradeID, name 
  FROM Paygrade;
SELECT employeeID, username, email, firstName, lastName, paygradeID, supervisorID, active, role 
  FROM employee;
--
-- UPDATE / U
UPDATE Credentials SET password='P@$$w0rd' 
    WHERE username = 'georgel';
--
UPDATE employee SET role = 1
WHERE
  employeeID = 124816;
--
-- feel free to add the right field
UPDATE employee SET 
  email = 'gleung4@hotmail.com', 
  firstName = 'George gh', 
  lastName = 'Leung', 
  supervisorID = 000001, 
  paygradeID = 1,
  active = 0 
WHERE
  employeeID = 124816;
--
--
-- DELETE -- Variables only
-- Order:  SELECT username
SELECT username
  FROM employee 
  WHERE employeeID = 124816;
-- store username with beans
DELETE from employee
  WHERE employeeID = 124816;
DELETE from Credentials
  WHERE username = 'georgel';
