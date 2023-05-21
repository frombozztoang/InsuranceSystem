package Insurance;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Insurance {

	private String insuranceID;

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	private String insuranceName; // 보험명
	private String type; // 보험 종류
	private int maxCompensation; // 최대보장한도
	private String periodOfInsurance; // 보험기간
	private String paymentCycle; // 납입주기
	private String paymentPeriod; // 납입기간
	private String ageOfTarget; // 가입나이
	private int basicPremium; // 기본보험료
	private String rate; // 요율
	private boolean distributionStatus; // 배당여부
	private String TermsIDList;
	private String insuranceClausePeriod; // 보험면책기간 (단위:월)
	private String precaution; // 주의사항
	private boolean authorization; // 인가여부

	public GuaranteeListImpl guaranteeList;
	public InsuranceApplication m_InsuranceApplication;

	public Insurance() throws FileNotFoundException, IOException {
		authorization = false;
	}

	public boolean matchId(String insuranceID) {

		return this.insuranceID.equals(insuranceID);
	}

	public String toString() {
		String stringReturn = this.insuranceID + " " + this.insuranceName + " " + this.type + " " + this.maxCompensation
				+ " " + this.periodOfInsurance + " " + this.paymentCycle + " " + this.paymentPeriod + " "
				+ this.ageOfTarget + " " + this.basicPremium + " " + this.rate + " " + this.distributionStatus + " "
				+ this.TermsIDList + " " + this.insuranceClausePeriod + " " + this.authorization + " "
				+ this.precaution;
		return stringReturn;
	}

	public boolean matchType(String type) {
		return this.type.equals(type);
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMaxCompensation() {
		return maxCompensation;
	}

	public void setMaxCompensation(int maxCompensation) {
		this.maxCompensation = maxCompensation;
	}

	public String getPeriodOfInsurance() {
		return periodOfInsurance;
	}

	public void setPeriodOfInsurance(String periodOfInsurance) {
		this.periodOfInsurance = periodOfInsurance;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public String getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

	public String getAgeOfTarget() {
		return ageOfTarget;
	}

	public void setAgeOfTarget(String ageOfTarget) {
		this.ageOfTarget = ageOfTarget;
	}

	public int getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(int basicPremium) {
		this.basicPremium = basicPremium;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public boolean isDistributionStatus() {
		return distributionStatus;
	}

	public void setDistributionStatus(boolean distributionStatus) {
		this.distributionStatus = distributionStatus;
	}

	public String getTermsIDList() {
		return TermsIDList;
	}

	public String getInsuranceClausePeriod() {
		return insuranceClausePeriod;
	}

	public void setInsuranceClausePeriod(String insuranceClausePeriod) {
		this.insuranceClausePeriod = insuranceClausePeriod;
	}

	public String getPrecaution() {
		return precaution;
	}

	public void setPrecaution(String precaution) {
		this.precaution = precaution;
	}

	public boolean isAuthorization() {
		return authorization;
	}

	public void setAuthorization(boolean authorization) {
		this.authorization = authorization;
	}

	public GuaranteeListImpl getGuaranteeList() {
		return guaranteeList;
	}

	public void setGuaranteeList(GuaranteeListImpl guaranteeList) {
		this.guaranteeList = guaranteeList;
	}

	public InsuranceApplication getM_InsuranceApplication() {
		return m_InsuranceApplication;
	}

	public void setM_InsuranceApplication(InsuranceApplication m_InsuranceApplication) {
		this.m_InsuranceApplication = m_InsuranceApplication;
	}

	public boolean setTermsIDList(String termsIDList) throws FileNotFoundException, IOException {
		guaranteeList = new GuaranteeListImpl("data/Guarantee.txt");
		if (guaranteeList.alreadyExistInsurance(this.insuranceID))
			guaranteeList.delete(insuranceID);
		String[] termsIDListSplit = termsIDList.split(",");
		Guarantee guarantee = new Guarantee();
		for (int i = 0; i < termsIDListSplit.length; i++) {
			guarantee.setInsuranceID(this.insuranceID);
			guarantee.setTermsID(termsIDListSplit[i]);
			if (!guarantee.isExistTermsID())
				return false;
		}
		guaranteeList.create(guarantee);
		TermsIDList = termsIDList;
		return true;
	}
}
