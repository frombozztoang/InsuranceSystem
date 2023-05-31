package CompensationClaim;

import java.io.BufferedReader;

public class Survey {
	private String CCID;
	private String managerName;
	private String reportFilePath;
	private int surveyFee;
	private int decisionMoney;
	private boolean responsibility;
	private String responsibilityReason;

	/**
	 * Getters & Setters
	 */
	public String getCCID() {
		return CCID;
	}

	public void setCCID(String CCID) {
		this.CCID = CCID;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getDecisionMoney() {
		return decisionMoney;
	}

	public void setDecisionMoney(int decisionMoney) {
		this.decisionMoney = decisionMoney;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public boolean isResponsibility() {
		return responsibility;
	}

	public void setResponsibility(boolean responsibility) {
		this.responsibility = responsibility;
	}

	public String getResponsibilityReason() {
		return responsibilityReason;
	}

	public void setResponsibilityReason(String responsibilityReason) {
		this.responsibilityReason = responsibilityReason;
	}

	public int getSurveyFee() {
		return surveyFee;
	}

	public void setSurveyFee(int surveyFee) {
		this.surveyFee = surveyFee;
	}

	public void finalize() throws Throwable {

	}

	public String toString() {
		String stringReturn = this.CCID + " " + this.managerName + " " + this.reportFilePath + " " + this.surveyFee
				+ " " + this.decisionMoney + " " + this.responsibility + " " + this.responsibilityReason;
		return stringReturn;
	}

	public void requestBanking() {
	}

	public boolean matchId(String ccid) {
		return this.CCID.equals(ccid);

	}
}
