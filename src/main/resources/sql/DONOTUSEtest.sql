-- for active
select c.username from credentials c, employee e where c.username = e.username and e.active=true;