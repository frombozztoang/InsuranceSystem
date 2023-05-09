package CompensationClaim;

import java.io.BufferedReader;
import java.io.IOException;

	public class CompensationClaim {
		private int insuranceID;
		private int customerID;
		private int CCID = Integer.parseInt(String.valueOf(insuranceID) + String.valueOf(customerID));
		private String receptionistName;
		private int receptionistPNumber;
		private String relationshipOfContractor;
		private String documentFilePath;
		private String bank;
		private int accountNumber;
		private String accountHolderName;
		public Survey m_Survey;

		public CompensationClaim() {
			Survey survey = new Survey();
		}

		/**
		 * Getters & Setters
		 */
		public int getInsuranceID() {
			return insuranceID;
		}

		public void setInsuranceID(int insuranceID) {
			this.insuranceID = insuranceID;
		}

		public int getCustomerID() {
			return customerID;
		}

		public void setCustomerID(int customerID) {
			this.customerID = customerID;
		}

		public int getCCID() {
			return CCID;
		}

		public void setCCID(int CCID) {
			this.CCID = CCID;
		}

		public String getReceptionistName() {
			return receptionistName;
		}

		public void setReceptionistName(String receptionistName) {
			this.receptionistName = receptionistName;
		}

		public int getReceptionistPNumber() {
			return receptionistPNumber;
		}

		public void setReceptionistPNumber(int receptionistPNumber) {
			this.receptionistPNumber = receptionistPNumber;
		}

		public String getRelationshipOfContractor() {
			return relationshipOfContractor;
		}

		public void setRelationshipOfContractor(String relationshipOfContractor) {
			this.relationshipOfContractor = relationshipOfContractor;
		}

		public String getDocumentFilePath() {
			return documentFilePath;
		}

		public void setDocumentFilePath(String documentFilePath) {
			this.documentFilePath = documentFilePath;
		}

		public String getBank() {
			return bank;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public int getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getAccountHolderName() {
			return accountHolderName;
		}

		public void setAccountHolderName(String accountHolderName) {
			this.accountHolderName = accountHolderName;
		}

		public String toString() {
			String stringReturn = this.insuranceID + " " + this.customerID + " " + this.CCID + " " + this.receptionistName + " " + this.receptionistPNumber + " " + this.relationshipOfContractor
					+ " " + this.documentFilePath + " " + this.bank + " " + this.accountNumber + " " + this.accountHolderName;
			return stringReturn;
		}

		public void finalize() throws Throwable {

		}

		public String createCompensationClaim(BufferedReader inputReader) throws IOException {
			return "";
		}

		public CompensationClaim retrieveCompensationClaim() {
			return null;
		}
	}