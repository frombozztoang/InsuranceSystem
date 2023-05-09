package CompensationClaim;


import java.io.BufferedReader;

public class Survey {
	private int CCID;
	//		private int insuranceID;
//		private int customerID;
	private String managerName;
	private int decisionMoney;
	private String reportFilePath;
	private boolean responsibility;
	private String responsibilityReason;
	private int surveyFee;


	/**
	 * Getters & Setters
	 */
	public int getCCID() {
		return CCID;
	}

	public void setCCID(int CCID) {
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

	public boolean createSurvey(BufferedReader inputReader) {
		return false;
	}
}

