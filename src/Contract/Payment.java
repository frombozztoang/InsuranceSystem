package Contract;

import java.time.LocalDate;
import java.util.Date;

public class Payment {

	private int customerID;
	private int insuranceID;
	private LocalDate dateOfPayment;
	private boolean whetherPayment;
	private String stringDateOfPayment;
	
	public Payment() {

	}

	public boolean match(int customerID, int insuranceID) {
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

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
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

	public int getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(int insuranceID) {
		this.insuranceID = insuranceID;
	}

	public boolean isWhetherPayment() {
		return whetherPayment;
	}

	public void setWhetherPayment(boolean whetherPayment) {
		this.whetherPayment = whetherPayment;
	}
}// end Payment