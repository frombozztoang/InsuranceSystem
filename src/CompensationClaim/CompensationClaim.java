package CompensationClaim;

import java.io.BufferedReader;
import java.io.IOException;

	public class CompensationClaim {
		protected String CCID;
		protected String insuranceID;
		protected String customerID;
		protected String receptionistName;
		protected String receptionistPNumber;
		protected String relationshipOfContractor;
		protected String documentFilePath;
		protected String bank;
		protected String accountNumber;
		protected String accountHolderName;
		public Survey m_Survey;

		public CompensationClaim() {
			Survey survey = new Survey();
		}

		/**
		 * Getters & Setters
		 */
		public String getCCID() {
			return CCID;
		}
		public void setCCID(String CCID) {
			this.CCID = CCID;
		}public String getInsuranceID() {
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

		public String getReceptionistName() {
			return receptionistName;
		}

		public void setReceptionistName(String receptionistName) {
			this.receptionistName = receptionistName;
		}

		public String getReceptionistPNumber() {
			return receptionistPNumber;
		}

		public void setReceptionistPNumber(String receptionistPNumber) {
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

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getAccountHolderName() {
			return accountHolderName;
		}

		public void setAccountHolderName(String accountHolderName) {
			this.accountHolderName = accountHolderName;
		}

		public String toString() {
			String stringReturn =  this.CCID + " " + this.insuranceID + " " + this.customerID + " " + this.receptionistName + " " + this.receptionistPNumber + " " + this.relationshipOfContractor
					+ " " + this.documentFilePath + " " + this.bank + " " + this.accountNumber + " " + this.accountHolderName;
			return stringReturn;
		}

		public void finalize() throws Throwable {

		}

        public boolean matchId(String ccid) {
			return this.CCID.equals(ccid);
        }
    }