package com.bridglelabz.EmployeeManagement;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.Date;

import org.junit.Test;

import com.bridglelabz.employee.DatabaseConnector;
import com.bridglelabz.employee.EmployeePayrollService;
import com.bridglelabz.employee.model.DepartmentModel;
import com.bridglelabz.employee.model.EmployeePayrollModel;
import com.bridglelabz.employee.model.SalaryModel;

public class AppTest {

	@Test
	public void updateSalary() {
		Connection con = DatabaseConnector.getConnection();
		EmployeePayrollService service = new EmployeePayrollService();
		int updateStatus = service.updateSalary(1500000, "Mark", con);
		assertEquals(1, updateStatus);
	}

	@Test
	public void addNewEmployee() {
		Connection con = DatabaseConnector.getConnection();

		Date currentDate = new Date(System.currentTimeMillis());

		EmployeePayrollService service = new EmployeePayrollService();
		EmployeePayrollModel emp = new EmployeePayrollModel();
		emp.setName("Stefan");
		emp.setGender("M");
		emp.setStart(currentDate);
		DepartmentModel deptModel = new DepartmentModel();
		deptModel.setDept_name("Sales");
		emp.setDepartment(deptModel);

		SalaryModel salary = new SalaryModel();
		salary.setBasic_pay(1400000);
		int insertStatus = service.addEmployeeData(emp, salary, con);
		assertEquals(1, insertStatus);
	}
}
