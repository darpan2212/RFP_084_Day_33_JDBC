package com.bridglelabz.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bridglelabz.employee.model.DepartmentModel;
import com.bridglelabz.employee.model.EmployeePayrollModel;

public class EmployeePayrollService {

	public static void main(String[] args) {

		Connection con = DatabaseConnector.getConnection();

		EmployeePayrollService service = new EmployeePayrollService();

//		service.getEmployeeList(con);
		service.getEmployeeListWithDepartment(con);
	}

	public void getEmployeeList(Connection con) {
		List<EmployeePayrollModel> employeeList = new ArrayList<>();
		try {
			Statement selectEmpStatement = con.createStatement();
			ResultSet employeeResult = selectEmpStatement
					.executeQuery("select * from employee_tbl");

			while (employeeResult.next()) {
				EmployeePayrollModel model = new EmployeePayrollModel();
				model.setEmp_id(employeeResult.getInt("emp_id"));
				model.setName(employeeResult.getString("name"));
				DepartmentModel department = new DepartmentModel();
				department
						.setDept_id(employeeResult.getInt("dept_id"));
				model.setDepartment(department);
				model.setGender(employeeResult.getString("gender"));
				model.setStart(employeeResult.getDate("start"));

				employeeList.add(model);
			}

			employeeList.forEach(emp -> {
				System.out.println("Emp Id : " + emp.getEmp_id());
				System.out.println("Emp Name : " + emp.getName());
				System.out.println("Department Id : "
						+ emp.getDepartment().getDept_id());
				System.out.println("Gender : " + emp.getGender());
				System.out.println(
						"Date of Joining : " + emp.getStart());
				System.out.println(
						"----------------------------------------------------");
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getEmployeeListWithDepartment(Connection con) {

		List<EmployeePayrollModel> employeeList = new ArrayList<>();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select emp_id, name,dept_name,gender,start from employee_tbl,"
							+ "department_table where employee_tbl.dept_id=department_table.dept_id "
							+ "order by emp_id;");

			while (rs.next()) {
				EmployeePayrollModel empModel = new EmployeePayrollModel();
				DepartmentModel deptModel = new DepartmentModel();
				
				deptModel.setDept_name(rs.getString("dept_name"));
				
				empModel.setEmp_id((rs.getInt(1)));
				empModel.setName((rs.getString(2)));;
				empModel.setDepartment(deptModel);
				empModel.setGender(rs.getString(4));
				empModel.setStart(rs.getDate(5));
				
				employeeList.add(empModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		employeeList.forEach(emp -> {
			System.out.println("Emp Id : " + emp.getEmp_id());
			System.out.println("Emp Name : " + emp.getName());
			System.out.println("Department Name : "
					+ emp.getDepartment().getDept_name());
			System.out.println("Gender : " + emp.getGender());
			System.out.println(
					"Date of Joining : " + emp.getStart());
			System.out.println(
					"----------------------------------------------------");
		});
	}
}