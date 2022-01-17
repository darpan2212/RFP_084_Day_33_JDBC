package com.bridglelabz.employee.model;

public class SalaryModel {

	double basic_pay, deduction, taxable_pay, tax, net_pay;
	int emp_id;

	public double getBasic_pay() {
		return basic_pay;
	}

	public void setBasic_pay(double basic_pay) {
		this.basic_pay = basic_pay;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getNet_pay() {
		return net_pay;
	}

	public void setNet_pay(double net_pay) {
		this.net_pay = net_pay;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public double getTaxable_pay() {
		return taxable_pay;
	}

	public void setTaxable_pay(double taxable_pay) {
		this.taxable_pay = taxable_pay;
	}
}