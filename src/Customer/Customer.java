package Customer;

import java.io.Serializable;

public class Customer implements Serializable {

	public enum EGender {

		male("남"), female("여");

		private String genderStr;

		private EGender(String genderStr) {
			this.genderStr = genderStr;
		}

		public String getGenderStr() {
			return genderStr;
		}

		public void setGenderStr(String genderStr) {
			this.genderStr = genderStr;
		}
	}

	private static final long serialVersionUID = 1L;
	private String address;
	private String customerID;
	private String customerName;
	private String job;
	private String pnumber;
	private String birth; // 생년월일(yyyy-mm-dd, String)
	private EGender eGender; // 성별

	// composition
	public FamilyHistory familyHistory;

	public Customer() {
		this.familyHistory = new FamilyHistory();
	}

	public String toString() {
		String stringReturn = this.customerID + " " + this.customerName + " " + this.birth + " " + this.eGender + " "
				+ this.pnumber + " " + this.job + " " + this.address;
		return stringReturn;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public EGender getEGender() {
		return eGender;
	}

	public void setEGender(EGender eGender) {
		this.eGender = eGender;
	}

	public boolean matchId(String customerID) {
		return this.customerID.equals(customerID);
	}

}