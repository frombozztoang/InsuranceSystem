package Counsel;

import java.time.LocalDate;

import Customer.Customer;

public class Counsel {
	private String content; // 
	private String managerName;
	private LocalDate dateOfCounsel;

	private String counselID; // 
	private String customerID;
	private String requirement;
	private Customer customer;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public LocalDate getDateOfCounsel() {
		return dateOfCounsel;
	}

	public void setDateOfCounsel(LocalDate dateOfCounsel) {
		this.dateOfCounsel = dateOfCounsel;
	}

	public String getCounselID() {
		return counselID;
	}

	public void setCounselID(String counselID) {
		this.counselID = counselID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean matchId(String customerID) {
		return this.customerID.equals(customerID);
	}
}
