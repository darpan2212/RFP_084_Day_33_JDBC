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

	public void addNewEmployee() {
		Connection con = DatabaseConnector.getConnection();

		Date currentDate = new Date(System.currentTimeMillis());

		EmployeePayrollService service = new EmployeePayrollService();
		EmployeePayrollModel emp = new EmployeePayrollModel();
		emp.setName("Stefan");
		emp.setGender("M");
		emp.setStart(currentDate);
		DepartmentModel deptModel = new DepartmentModel();
		deptModel.setDept_id(1);
		emp.setDepartment(deptModel);

		SalaryModel salary = new SalaryModel();
		salary.setBasic_pay(1400000);
		int insertStatus = service.addEmployeeData(emp, salary, con);
		assertEquals(1, insertStatus);
	}
	
	@Test
	public void deleteEmployee() {
		EmployeePayrollService service = new EmployeePayrollService();
		Connection con = DatabaseConnector.getConnection();
		assertEquals(1, service.deleteEmployee("Jeff",con));
	}
}
