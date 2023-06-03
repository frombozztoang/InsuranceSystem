package Insurance;

public class Guarantee {

	private String insuranceID;
	private String termsID;

	public Guarantee(){
	}
	
	  public String toString() {
	        String stringReturn = this.insuranceID + " " + this.termsID;
	        return stringReturn;
	    }

	public boolean matchInsuranceId(String insuranceID) {
		return this.insuranceID.equals(insuranceID);
	}

	
	public String getInsuranceID() {
		return insuranceID;
	}


	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}


	public String getTermsID() {
		return termsID;
	}


	public void setTermsID(String termsID) {
		this.termsID = termsID;
	}

	

    public boolean matchID(String insuranceID) {
		return (this.insuranceID.equals(insuranceID));
    }
}
