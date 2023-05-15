package Contract;

import java.time.LocalDate;

public class Payment {

	private int paymentIndex;
	private int customerID;
	private int insuranceID;
	private LocalDate dateOfPayment;
	private boolean whetherPayment; // 미납 여부
	private String stringDateOfPayment;

	public Payment() {
		whetherPayment = false;
	}

	public boolean match(int customerID, int insuranceID) {
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

	public boolean matchCustomerID(int customerID) {
		return this.customerID == customerID;
	}

	public boolean matchPaymentID(int paymentIndex) {
		return this.paymentIndex == paymentIndex;
	}

	public boolean matchCustomerPayment(int paymentIndex, String customerID) {
		// TODO Auto-generated method stub
		return (this.paymentIndex == paymentIndex) && (this.customerID == Integer.parseInt(customerID));
	}
}// end Payment