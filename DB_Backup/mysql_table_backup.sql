drop table if exists table_demo_a_bak;
create table table_demo_a_bak like table_demo_a;
insert into table_demo_a_bak select * from table_demo_a;

drop table if exists table_demo_b_bak;
create table table_demo_b_bak like table_demo_b;
insert into table_demo_b_bak select * from table_demo_b;

drop table if exists table_demo_c_bak;
create table table_demo_c_bak like table_demo_c;
insert into table_demo_c_bak select * from table_demo_c;
