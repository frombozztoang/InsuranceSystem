package Customer;

import java.util.StringTokenizer;

public class FamilyHistory {

	private int customerID;
	private String diseaseName;
	private String relationship;
	public FamilyHistory(String inputString){
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.diseaseName = stringTokenizer.nextToken();
		this.relationship = stringTokenizer.nextToken();
		

	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
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


}
