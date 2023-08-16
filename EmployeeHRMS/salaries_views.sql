 -- select * from salaries;
  /*
  select s.emp_no as 'emp_no',salary,s1.from_date,s1.to_date
   from salaries s  join
    (SELECT emp_no, MAX(from_date) AS from_date, MAX(to_date) AS to_date
    FROM salaries
    GROUP BY emp_no) s1
    on s.emp_no=s1.emp_no and s.from_date=s1.from_date and s.to_date= s1.to_date;
    
    */
    /*
    create or replace view current_emp_salary as
     select s.emp_no as 'emp_no',salary,s1.from_date,s1.to_date
   from salaries s  join
    (SELECT emp_no, MAX(from_date) AS from_date, MAX(to_date) AS to_date
    FROM salaries
    GROUP BY emp_no) s1
    on s.emp_no=s1.emp_no and s.from_date=s1.from_date and s.to_date= s1.to_date;
    
    */
/*
    select ces.emp_no,ces.salary,ces.from_date,ces.to_date,cde.dept_no,d.dept_name from current_emp_salary  ces
    join current_dept_emp cde
    on ces.emp_no=cde.emp_no 
    join departments d
    on cde.dept_no=d.dept_no;
    
    */
/*
create or replace view current_emp_dept_salary as
 select ces.emp_no,ces.salary,ces.from_date,ces.to_date,cde.dept_no,d.dept_name from current_emp_salary  ces
    join current_dept_emp cde
    on ces.emp_no=cde.emp_no 
    join departments d
    on cde.dept_no=d.dept_no;
    
*/

-- select * from current_emp_dept_salary;
-- select dept_no,dept_name,avg(salary),min(salary),max(salary) from current_emp_dept_salary group by dept_no,dept_name order by dept_no;
    