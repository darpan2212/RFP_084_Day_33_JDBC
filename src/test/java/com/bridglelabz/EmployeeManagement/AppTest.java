package com.bridglelabz.EmployeeManagement;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.Test;

import com.bridglelabz.employee.DatabaseConnector;
import com.bridglelabz.employee.EmployeePayrollService;

public class AppTest {

	@Test
	public void updateSalary() {
		Connection con = DatabaseConnector.getConnection();
		EmployeePayrollService service = new EmployeePayrollService();
		int updateStatus = service.updateSalary(1500000, "Mark", con);
		assertEquals(1, updateStatus);
	}
	
}
