package Customer;

import java.util.StringTokenizer;

public class FamilyHistory {

	private String customerID;
	private String diseaseName; // 가족 질환
	private String relationship; // 가족 관계
	public FamilyHistory(String inputString){
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.diseaseName = stringTokenizer.nextToken();
		this.relationship = stringTokenizer.nextToken();
	}
	public FamilyHistory() {
		
	}
	public String toString() {
		String stringReturn = this.diseaseName + " " + this.relationship;
		return stringReturn;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public boolean matchCID(String customerID) {
		return (this.customerID.equals(customerID));

	}
}
