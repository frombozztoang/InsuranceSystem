package Counsel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Customer.Customer;

public class CounselApplication implements Serializable {

	private static final long serialVersionUID = 1L;
	private String category;
	private String counselID;
	private String customerID;
	private LocalDate dateOfFirst;
	private LocalDate dateOfSecond;
	private String requirement;
	
	private Counsel counsel;
	private ArrayList<Counsel> counselList;

	public CounselApplication() {
		counsel = new Counsel();
	}

	public boolean requireCounsel(String managerName, String customerID, Customer customer, String requirement) {
		// Check if the customer already has an existing counseling schedule
		for (Counsel counsel : counselList) {
			if (counsel.getCustomerID().equals(customerID)) {
				return false; // Application unsuccessful - customer already has a counseling schedule
			}
		}

		// Create a new counseling schedule and add it to the list
		Counsel newCounsel = new Counsel(managerName, customerID, customer, requirement);
		return counselList.add(newCounsel);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public LocalDate getDateOfFirst() {
		return dateOfFirst;
	}

	public void setDateOfFirst(LocalDate dateOfFirst) {
		this.dateOfFirst = dateOfFirst;
	}

	public LocalDate getDateOfSecond() {
		return dateOfSecond;
	}

	public void setDateOfSecond(LocalDate dateOfSecond) {
		this.dateOfSecond = dateOfSecond;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public Counsel getCounsel() {
		return counsel;
	}

	public void setCounsel(Counsel counsel) {
		this.counsel = counsel;
	}
	
}
