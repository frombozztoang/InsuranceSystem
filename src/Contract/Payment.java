package Contract;

import java.time.LocalDate;

public class Payment {

	private int paymentIndex;
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
		String stringReturn = paymentIndex + " " + customerID + " " + this.insuranceID + " " + this.dateOfPayment + " "
				+ this.whetherPayment;
		return stringReturn;
	}

	public void finalize() throws Throwable {

	}

	public int getPaymentID() {
		return paymentIndex;
	}

	public void setPaymentID(int paymentID) {
		this.paymentIndex = paymentID;
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

	public boolean matchCustomerID(String customerID) {
		return this.customerID == customerID;
	}

	public boolean matchPaymentID(int paymentIndex) {
		return this.paymentIndex == paymentIndex;
	}

	public boolean matchCustomerPayment(int paymentIndex, String customerID) {
		// TODO Auto-generated method stub
		return (this.paymentIndex == paymentIndex) && (this.customerID == customerID);
	}
}// end Payment