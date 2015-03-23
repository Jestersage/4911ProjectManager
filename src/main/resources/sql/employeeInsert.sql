--
-- Sample Employeed data
--
-- INSERT DATA
-- Order MUST BE: Credentials, Then employee, then roleAssign
-- READ FIRST
-- CHANGES
-- Mar 8 2015 -- removed PaygradeID on employee tables
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("root", "1000:5ebbe77a486e8aea2d893c4c4c763f0bafb2351597aa28ab:7420a2f2c2889f78e8498bf18064367680e571ab69937894");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, active, payGrade) 
VALUES 
  (000001, "root", "gleung4@gmail.com", "CEO", "Admin", 1, "P1" );
INSERT INTO HR
  (employeeID)
VALUES
  (000001);
--
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("gabeh", "1000:63c796f8882e96aef82d7cd36597240bcbfde1e513e45087:55f64c9172aadd0ff1bf21d30526d099a93e4c0f5350942d");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade) 
VALUES
  (123456, "gabeh", "gabeh3264@gmail.com", "Gabe", "Hung", 000001, 1, "P2");
INSERT INTO HR
  (employeeID)
VALUES
  (123456);
--
--
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("georgel", "1000:1a9662c26be35003f431749d57734b21e15178f4783c23c7:24741852fde968229d2270c3630d1d2644b24ba6ebbaab2c");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade) 
VALUES
  (124816, "georgel", "gleung4@gmail.com", "George", "Leung", 123456, 1, "P3");
--
--
-- Sample Employees (-Finlay)
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("bobDole", "1000:fcaa87477f15e0b142cde1d692d1401345a419c4d6c329da:3f538dfbbbf21b22330c23473a5dd744bbdd415427e30fcb");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade)
VALUES
    (000002, "bobDole", "bdole@email.com", "Bob", "Dole", 000001, 1, "P4");
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fredBarns", "1000:f1380cb98b5c9c77fc8c2682d56bdc37f05b3d4a5393a946:fd7c00bfe7df3b324da705e284195a466db7462f0a787f9f");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade)
VALUES
    (000003, "fredBarns", "fredb@email.com", "Frederic", "Barns", 000001, 1, "P5");
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("rBurns", "1000:1d6908fa9fa950130d1b0521757534d4b1319e343ec0e1ad:7aac50411a88e90a75fe4610a8943e4c7b6b092d3847d300");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active, payGrade)
VALUES
    (000004, "rBurns", "bdole@email.com", "Robet", "Dolenson", 000001, 1, "P1");
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("hSimpson", "1000:6a831b839c8bfa8d5e3483f6a58b53c98555df01fca2ec39:b95b51ff8434cd83c79fc83d2e0bbb3515f9e1a7c28b8b6e");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000005, "hSimpson", "homerS@email.com", "Homer", "Simpson", 000004, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("magSimpson", "1000:8ca79e60077eee6d73145a88b593bda5c1674dcad88f45aa:c5cc51b118703d578f9ddacacd9a7791af15e654803c9af1");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000006, "magSimpson", "magS@email.com", "Maggie", "Simpson", 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("margSimpson", "1000:a893e3564a8666890436435d02bc5d681f32b8ddc572e6dd:678006e968d634351ff066cc1ac78cbc32ca5570d2ce9366");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000007, "margSimpson", "margS@email.com", "Marge", "Simpson", 000004, 0);
--  
INSERT INTO Credentials
    (username, password)
VALUES
    ("bSimpson", "1000:b54f3b876580937a39c84a2798454e4b863ba4df99df4a5b:c85fc85fe0004a7e98fcc5427e4409609da4c94d013817c6");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000008, "bSimpson", "bartS@email.com", "Bart", "Simpson", 000005, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("fArbuckle", "1000:b64733d28716f183e61aed450cd88fc1c527e4d1e9753ef4:0519fb5d66762ee37c4b0ebc170b3bf39773216cecad716b");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000009, "fArbuckle", "fredA@email.com", "Fred", "Arbuckle", 000004, 1);    
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("garfield", "1000:d8ebf38c3badca3cdb1ebfcbfbd0c3bbde314ce1827e16fe:77512faa1ebfb0bd28e03bf9333b5c6579fc75e52c4bae37");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000010, "garfield", "garf@email.com", "Garfield", "Arbuckle", 000009, 1);
--
INSERT INTO Credentials
    (username, password)
VALUES
    ("odie", "1000:4eabded00497ebafcaec94b66e89bd70bf8d0dad94603f86:eb34035981b1b237baf0148374605162498431e4d6c22852");
INSERT INTO Employee
    (employeeID, username, email, firstName, lastName, supervisorID, active)
VALUES
    (000011, "odie", "odie@email.com", "Odie", "Arbuckle", 000009, 1);
--
--
--
-- New Employees for Testing
-- Work Packages, etc
-- 
-- Employee 333
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("333", "1000:26e1732d706c7f563bd49e80ffc27cc6dea0c1dd960f0ffd:b87bb414f5d658ec2d2304de2152950f9d304b418b3b2e13");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (333, "333", "3@3.com", "3", "33", 123456, 1);
--  
-- Employee 555
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("555", "1000:0836f0da93706a74f3eabc96f323fb065072941c0bd5ce0a:6e249f8b2aea1904f3e4d5938686246fdcf7bdc0c579e41a");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (555, "555", "5@5.com", "5", "55", 123456, 1);
--  
-- Employee 999
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("999", "1000:cdd07e605999009ab5a133858cacf75f75a3ed5ffc7d73a0:d7fbecad55cb199fc248f1e25c13e5bff2c80b9b8fa1cb4a");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (999, "999", "9@9.com", "9", "99", 123456, 1);
--  
-- Employee 888
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("888", "1000:9ec8b00d8f8cdb79d5205765c8baa31984d76a132b580d4a:3766c7416a57df428da386b08997a78e3e801718efafe373");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (888, "888", "8@8.com", "8", "88", 123456, 1);
--  
-- Employee 777
INSERT INTO Credentials
  (username, password) 
VALUES 
  ("777", "1000:001805aa3ec98c9e429c3abd46c186a4d106aac9f74e112c:33be2b8fe27040204b79d63299e06c1d2a37edccc4e776a8");
INSERT INTO Employee
  (employeeID, username, email, firstName, lastName, supervisorID, active) 
VALUES
  (777, "777", "7@7.com", "7", "77", 123456, 1);
--
