package Insurance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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

	public boolean isExistTermsID() throws FileNotFoundException, IOException {
		TermsListImpl termsList = new TermsListImpl("data/Terms.txt");
		ArrayList<Terms> existTermsList = termsList.retrieveAllTerms();
		for(int i=0;i<existTermsList.size();i++)
			if(existTermsList.get(i).matchID(this.termsID)) return true;
		return false;
	}

    public boolean matchID(String insuranceID) {
		return (this.insuranceID.equals(insuranceID));
    }
}
