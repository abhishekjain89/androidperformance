select application.package,application.name,sum(total_diff) from application_use,application where application_use.package = application.package and total_diff > 0 group by application.package order by sum(total_diff) desc limit 50;

