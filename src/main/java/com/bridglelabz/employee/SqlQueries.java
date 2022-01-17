package com.bridglelabz.employee;

public class SqlQueries {

	public final String GET_EMPLOYEES_QUERY = "select * from employee_tbl";
	public final String GET_EMPLOYEES_WITH_DEPT_NAME_QUERY = "select emp_id, "
			+ "name,dept_name,gender,start from employee_tbl,department_table "
			+ "where employee_tbl.dept_id=department_table.dept_id order by emp_id";

	public final String UPDATE_EMPLOYEE_SALARY_QUERY = "update salary_tbl set basic_pay=? "
			+ "where salary_tbl.emp_id=(select emp_id from employee_tbl where name=?)";

	public final String GET_EMP_SALARY_DETAILS_QUERY = "select employee_tbl.emp_id,"
			+ "name,salary_tbl.basic_pay from employee_tbl,salary_tbl "
			+ "where employee_tbl.emp_id = salary_tbl.emp_id";

	public final String ADD_EMPLOYEE_QUERY = "insert into employee_tbl (name, dept_id,"
			+ " gender, start) values (?,?,?,?)";

	public final String ADD_SALARY_QUERY = "insert into salary_tbl (basic_pay, deduction,"
			+ " taxable_pay, tax, net_pay, emp_id) values (?,?,?,?,?,?)";
}
