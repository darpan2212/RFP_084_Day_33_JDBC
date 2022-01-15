package com.bridglelabz.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bridglelabz.employee.model.DepartmentModel;
import com.bridglelabz.employee.model.EmployeePayrollModel;

public class EmployeePayrollService {

	SqlQueries sql = new SqlQueries();

	public static void main(String[] args) {

		Connection con = DatabaseConnector.getConnection();

		EmployeePayrollService service = new EmployeePayrollService();

//		service.getEmployeeList(con);
//		service.getEmployeeListWithDepartment(con);
		service.updateSalary(3000000, "Terisa", con);
	}

	public void getEmployeeList(Connection con) {
		List<EmployeePayrollModel> employeeList = new ArrayList<>();
		try {
			Statement selectEmpStatement = con.createStatement();
			ResultSet employeeResult = selectEmpStatement
					.executeQuery(sql.GET_EMPLOYEES_QUERY);

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
			selectEmpStatement.close();
			employeeResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getEmployeeListWithDepartment(Connection con) {

		List<EmployeePayrollModel> employeeList = new ArrayList<>();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					sql.GET_EMPLOYEES_WITH_DEPT_NAME_QUERY);

			while (rs.next()) {
				EmployeePayrollModel empModel = new EmployeePayrollModel();
				DepartmentModel deptModel = new DepartmentModel();

				deptModel.setDept_name(rs.getString("dept_name"));

				empModel.setEmp_id((rs.getInt(1)));
				empModel.setName((rs.getString(2)));
				;
				empModel.setDepartment(deptModel);
				empModel.setGender(rs.getString(4));
				empModel.setStart(rs.getDate(5));

				employeeList.add(empModel);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		employeeList.forEach(emp -> {
			System.out.println("Emp Id : " + emp.getEmp_id());
			System.out.println("Emp Name : " + emp.getName());
			System.out.println("Department Name : "
					+ emp.getDepartment().getDept_name());
			System.out.println("Gender : " + emp.getGender());
			System.out.println("Date of Joining : " + emp.getStart());
			System.out.println(
					"----------------------------------------------------");
		});
	}

	public int updateSalary(double amount, String empName,
			Connection con) {
		int updateStatus = 0;
		try {

			PreparedStatement ps = con.prepareStatement(
					sql.UPDATE_EMPLOYEE_SALARY_QUERY);
			ps.setDouble(1, amount);
			ps.setString(2, empName);

			updateStatus = ps.executeUpdate();
			System.out.println(
					"Update salary query status " + updateStatus);
			ps.close();
			this.getEmployeesSalaryDetails(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateStatus;
	}

	public void getEmployeesSalaryDetails(Connection con) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery(sql.GET_EMP_SALARY_DETAILS_QUERY);
			while (rs.next()) {
				System.out.println("Emp Id : " + rs.getInt("emp_id"));
				System.out.println(
						"Emp Name : " + rs.getString("name"));
				System.out.println(
						"Salary : " + rs.getString("basic_pay"));
				System.out.println(
						"----------------------------------------------------");
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}