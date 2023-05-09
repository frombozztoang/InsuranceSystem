package Contract;

import java.util.Date;

import CompensationClaim.CompensationClaim;

public class Contract {

	private int customerID;
	private int insuranceID;
	private String insurancePeriod;
	private int premium;
	private String paymentCycle;
	private int maxCompensation;
	private Date dateOfSubscription;
	private Date dateOfMaturity;
	private boolean maturity;
	private boolean resurrection;
	private boolean cancellation;
	
	private String stringDateOfSubscription;
	private String stringDateOfMaturity;

	public Payment m_Payment;
	public CompensationClaim m_CompensationClaim;

	public Contract() {

	}
	
	public String getStringDateOfSubscription() {
		return stringDateOfSubscription;
	}

	public void setStringDateOfSubscription(String stringDateOfSubscription) {
		this.stringDateOfSubscription = stringDateOfSubscription;
	}

	public String getStringDateOfMaturity() {
		return stringDateOfMaturity;
	}

	public void setStringDateOfMaturity(String stringDateOfMaturity) {
		this.stringDateOfMaturity = stringDateOfMaturity;
	}



	public boolean match(int customerID, int insuranceID) {
		return (this.customerID == customerID) && (this.insuranceID == insuranceID);
	}

	public String toString() {
		String stringReturn = customerID + " " + this.insuranceID + " " + this.insurancePeriod + " " + this.premium
				+ " "+this.paymentCycle + " " + this.maxCompensation + " " + this.dateOfSubscription + this.dateOfMaturity
				+ " " + this.maturity + " " + this.resurrection + " " + this.cancellation;
		return stringReturn;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(int insuranceID) {
		this.insuranceID = insuranceID;
	}

	public String getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}

	public int getPremium() {
		return premium;
	}

	public void setPremium(int premium) {
		this.premium = premium;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public int getMaxCompensation() {
		return maxCompensation;
	}

	public void setMaxCompensation(int maxCompensation) {
		this.maxCompensation = maxCompensation;
	}

	public Date getDateOfSubscription() {
		return dateOfSubscription;
	}

	public void setDateOfSubscription(Date dateOfSubscription) {
		this.dateOfSubscription = dateOfSubscription;
	}

	public Date getDateOfMaturity() {
		return dateOfMaturity;
	}

	public void setDateOfMaturity(Date dateOfMaturity) {
		this.dateOfMaturity = dateOfMaturity;
	}

	public boolean isMaturity() {
		return maturity;
	}

	public void setMaturity(boolean maturity) {
		this.maturity = maturity;
	}

	public boolean isResurrection() {
		return resurrection;
	}

	public void setResurrection(boolean resurrection) {
		this.resurrection = resurrection;
	}

	public boolean isCancellation() {
		return cancellation;
	}

	public void setCancellation(boolean cancellation) {
		this.cancellation = cancellation;
	}

	public Payment getM_Payment() {
		return m_Payment;
	}

	public void setM_Payment(Payment m_Payment) {
		this.m_Payment = m_Payment;
	}

	public CompensationClaim getM_CompensationClaim() {
		return m_CompensationClaim;
	}

	public void setM_CompensationClaim(CompensationClaim m_CompensationClaim) {
		this.m_CompensationClaim = m_CompensationClaim;
	}

	public void finalize() throws Throwable {

	}

	public boolean updateCancellation() {
		return false;
	}
}// end Contract