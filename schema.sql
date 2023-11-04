create database  springBatchDb;
use database springBatchDb;

create table employee (id bigint not null, age integer, job varchar(255), name varchar(255), salary integer, primary key (id)) engine=InnoDB
create table employee_seq (next_val bigint) engine=InnoDB

