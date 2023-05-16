package Insurance;

import java.time.LocalDate;
import java.util.ArrayList;


public class InsuranceApplication {

	private String insuranceID;
	private String customerID;
	private LocalDate createdAt;
	private String insurancePeriod;
	private int maxCompensation;
	private String paymentCycle;
	private int premium;
	private String reasonOfApproval;
	private String subscriptionFilePath;
	private boolean approval;

	public InsuranceApplication(){

	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public String getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}

	public int getMaxCompensation() {
		return maxCompensation;
	}

	public void setMaxCompensation(int maxCompensation) {
		this.maxCompensation = maxCompensation;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public int getPremium() {
		return premium;
	}

	public void setPremium(int premium) {
		this.premium = premium;
	}

	public String getReasonOfApproval() {
		return reasonOfApproval;
	}

	public void setReasonOfApproval(String reasonOfApproval) {
		this.reasonOfApproval = reasonOfApproval;
	}

	public String getSubscriptionFilePath() {
		return subscriptionFilePath;
	}

	public void setSubscriptionFilePath(String subscriptionFilePath) {
		this.subscriptionFilePath = subscriptionFilePath;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public void finalize() throws Throwable {

	}
	public String approveInsuranceApplication(){
		return "";
	}

	public InsuranceApplication calculationRate(){
		return null;
	}

	public boolean createInsuranceApplication(){
		return false;
	}

	public ArrayList<InsuranceApplication> retrieveInsuranceApplication(){
		return null;
	}

	public InsuranceApplication retrieveInsuranceApplicationDetail(){
		return null;
	}

	public String retrieveSubscription(){
		return "";
	}
}//end InsuranceApplication