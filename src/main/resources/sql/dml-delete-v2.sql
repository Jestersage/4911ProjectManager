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
