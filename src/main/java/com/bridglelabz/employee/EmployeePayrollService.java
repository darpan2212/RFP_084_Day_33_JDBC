package com.bridglelabz.employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bridglelabz.employee.model.DepartmentModel;
import com.bridglelabz.employee.model.EmployeePayrollModel;
import com.bridglelabz.employee.model.SalaryModel;

public class EmployeePayrollService {

	SqlQueries sql = new SqlQueries();

	static List<DepartmentModel> departmentList;

	public static void main(String[] args) {

		Connection con = DatabaseConnector.getConnection();

		EmployeePayrollService service = new EmployeePayrollService();

//		service.getEmployeeList(con);
//		service.getEmployeeListWithDepartment(con);
//		service.updateSalary(3000000, "Terisa", con);
//		service.countEmployees(con);
//		service.getTotalSalary(con);

		Scanner sc = new Scanner(System.in);
		EmployeePayrollModel emp = new EmployeePayrollModel();

		System.out.println("Enter the employee name : ");
		emp.setName(sc.nextLine());

		System.out.println("Eneter the Gender : ");
		emp.setGender(sc.nextLine());
		System.out.println("Enter the joining date (yyyy-MM-dd) : ");
		String dateInStr = sc.nextLine();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			emp.setStart(sdf.parse(dateInStr));
		} catch (ParseException e) {
			System.out.println(
					"Entered date may be in wrong format, please try again");
		}

		SalaryModel salary = new SalaryModel();
		System.out.println("Enter the basic pay : ");
		salary.setBasic_pay(sc.nextDouble());
		System.out.println("Enter the employee department : ");
		departmentList = service.listDepartment(con);
		departmentList.forEach(dept -> {
			System.out.println(dept);
		});
		int dept_id = sc.nextInt();
		while (service.getSelectedDepartMent(dept_id) == null) {
			System.out.println(
					"Invalid department id, please enter again.");
			dept_id = sc.nextInt();
		}
		emp.setDepartment(service.getSelectedDepartMent(dept_id));
		sc.close();

		if (service.addEmployeeData(emp, salary, con) == 1) {
			System.out.println("Employee Data has been saved...");
		} else {
			System.out.println(
					"Some issue is there, please try again later....");
		}
	}

	public DepartmentModel getSelectedDepartMent(int dept_id) {
		for (DepartmentModel departmentModel : departmentList) {
			if (departmentModel.getDept_id() == dept_id) {
				return departmentModel;
			}
		}
		return null;
	}

	public List<DepartmentModel> listDepartment(Connection con) {
		Statement st;
		List<DepartmentModel> departmentList = new ArrayList<>();
		try {
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from department_tbl");
			while (rs.next()) {
				DepartmentModel dept = new DepartmentModel();
				dept.setDept_id(rs.getInt(1));
				dept.setDept_name(rs.getString(2));
				dept.setDescription(rs.getString(3));
				departmentList.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departmentList;
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
			ps.close();
//			this.getEmployeesSalaryDetails(con);
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

	public int addEmployeeData(EmployeePayrollModel employee,
			SalaryModel salary, Connection con) {
		int insertStatus = 0;

		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = con.prepareStatement(
					sql.ADD_EMPLOYEE_QUERY,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, employee.getName());
			ps.setInt(2, employee.getDepartment().getDept_id());
			ps.setString(3, employee.getGender());
			ps.setDate(4, new Date(employee.getStart().getTime()));

			insertStatus = ps.executeUpdate();

			if (insertStatus == 1) {
				ResultSet rsNewEmp = ps.getGeneratedKeys();
				if (rsNewEmp.next()) {
					int emp_id = rsNewEmp.getInt(1);
					insertStatus = this.addSalaryDetails(emp_id,
							salary, con);
				}
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertStatus;
	}

	private int addSalaryDetails(int emp_id, SalaryModel salary,
			Connection con) {
		int insertSalaryStatus = 0;
		try {
			salary.setDeduction(0.2 * salary.getBasic_pay());
			salary.setTaxable_pay(
					salary.getBasic_pay() - salary.getDeduction());
			salary.setTax(0.1 * salary.getTaxable_pay());
			salary.setNet_pay(
					salary.getTaxable_pay() - salary.getTax());
			salary.setEmp_id(emp_id);

			PreparedStatement ps = con
					.prepareStatement(sql.ADD_SALARY_QUERY);
			ps.setDouble(1, salary.getBasic_pay());
			ps.setDouble(2, salary.getDeduction());
			ps.setDouble(3, salary.getTaxable_pay());
			ps.setDouble(4, salary.getTax());
			ps.setDouble(5, salary.getNet_pay());
			ps.setInt(6, salary.getEmp_id());

			insertSalaryStatus = ps.executeUpdate();

			if (insertSalaryStatus == 1) {
				con.commit();
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertSalaryStatus;
	}

	public void countEmployees(Connection con) {
		int maleEmpCount = 0, femaleEmpCount = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select gender,count(*) from employee_tbl group by gender");
			while (rs.next()) {
				if (rs.getString("gender").equals("M")) {
					maleEmpCount = rs.getInt("count(*)");
				} else {
					femaleEmpCount = rs.getInt("count(*)");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Male employees are : " + maleEmpCount);
		System.out
				.println("Female employees are : " + femaleEmpCount);
	}

	public void getTotalSalary(Connection con) {
		long totalSalary = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select sum(basic_pay) from salary_tbl");
			while (rs.next()) {
				totalSalary = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(
				"Total salary paid by organisation : " + totalSalary);
	}

	public int deleteEmployee(String name, Connection con) {
		int status = 0;
		try {
			Statement st = con.createStatement();

			status = st.executeUpdate(
					"delete from employee_tbl where name='" + name
							+ "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}