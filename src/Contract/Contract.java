package Contract;

import java.time.LocalDate;
import java.util.Date;

import CompensationClaim.CompensationClaim;

public class Contract {

	public String getCustomerID;
	private String customerID;
	private String insuranceID;
	private String insurancePeriod;
	private int premium;
	private String paymentCycle;
	private int maxCompensation;
	private LocalDate dateOfSubscription;
	private LocalDate dateOfMaturity;
	private boolean maturity;
	private boolean resurrection;
	private boolean cancellation; // 해지 여부

	private String stringDateOfSubscription;
	private String stringDateOfMaturity;

	public Payment m_Payment; // 납입
	public CompensationClaim m_CompensationClaim;

	public Contract() {
		
	}

	private int contractIndex;

	public int getContractIndex() {
		return contractIndex;
	}

	public void setContractIndex(int contractIndex) {
		this.contractIndex = contractIndex;
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

	public boolean match(String customerID, String insuranceID) {
		return (this.customerID.equals(customerID)) && (this.insuranceID == insuranceID);
	}

	public String toString() {
		String stringReturn = contractIndex + " " + customerID + " " + this.insuranceID + " " + this.insurancePeriod
				+ " " + this.premium + " " + this.paymentCycle + " " + this.maxCompensation + " "
				+ this.dateOfSubscription + " " + this.dateOfMaturity + " " + this.maturity + " " + this.resurrection
				+ " " + this.cancellation;
		return stringReturn;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(String insuranceID) {
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

	public LocalDate getDateOfSubscription() {
		return dateOfSubscription;
	}

	public void setDateOfSubscription(LocalDate dateOfSubscription2) {
		this.dateOfSubscription = dateOfSubscription2;
	}

	public LocalDate getDateOfMaturity() {
		return dateOfMaturity;
	}

	public void setDateOfMaturity(LocalDate dateOfMaturity) {
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
		return !this.cancellation;
	}

	public boolean matchCustomerID(String customerID) {
		return this.customerID.equals(customerID);
	}

	public boolean matchContractIndex(int contractIndex) {
		return this.contractIndex == contractIndex;
	}

	public boolean matchCustomerContract(int contractIndex, String customerID) {
		return (this.contractIndex == contractIndex) && (this.customerID.equals(customerID));
	}

	public boolean updateMaturity() {
		return !this.maturity;
	}
}// end Contract