-- for active, login
select c.username from credentials c, employee e where c.username = e.username and e.active=true;
-- alt
select * from credentials c inner join employee e where c.username = e.username and e.active=true;
