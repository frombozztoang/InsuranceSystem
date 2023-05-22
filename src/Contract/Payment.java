package Contract;

import java.time.LocalDate;

public class Payment {

	private String customerID;
	private String insuranceID;
	private LocalDate dateOfPayment;
	private boolean whetherPayment; // 미납 여부
	private String stringDateOfPayment;

	public Payment() {
		whetherPayment = false;
	}

	public boolean match(String customerID, String insuranceID) {
		return (this.customerID == customerID) && (this.insuranceID == insuranceID);
	}

	public String toString() {
		String stringReturn = customerID + " " + this.insuranceID + " " + this.dateOfPayment + " "
				+ this.whetherPayment;
		return stringReturn;
	}

	public void finalize() throws Throwable {

	}

	public boolean updatePayment() {
		return !this.whetherPayment;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getStringDateOfPayment() {
		return stringDateOfPayment;
	}

	public void setStringDateOfPayment(String stringDateOfPayment) {
		this.stringDateOfPayment = stringDateOfPayment;
	}

	public LocalDate getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(LocalDate date) {
		this.dateOfPayment = date;
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	public boolean isWhetherPayment() {
		return whetherPayment;
	}

	public void setWhetherPayment(boolean whetherPayment) {
		this.whetherPayment = whetherPayment;
	}




}// end Payment