use employees;
create table users (
	username int primary key,
    password varchar(255) not null,
    role varchar(15) not null,
    FOREIGN KEY (username) 	references employees.employees(emp_no),
    unique (username)
);