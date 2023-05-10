package Insurance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Guarantee {

	private int insuranceID;
	private int termsID;

	public Guarantee(){
	}
	
	  public String toString() {
	        String stringReturn = this.insuranceID + " " + this.termsID;
	        return stringReturn;
	    }

	public boolean matchInsuranceId(int insuranceId) {
		return this.insuranceID == insuranceId;
	}
	
	public int getInsuranceID() {
		return insuranceID;
	}


	public void setInsuranceID(int insuranceID) {
		this.insuranceID = insuranceID;
	}


	public int getTermsID() {
		return termsID;
	}


	public void setTermsID(int termsID) {
		this.termsID = termsID;
	}

	public boolean isExistTermsID() throws FileNotFoundException, IOException {
		TermsListImpl termsList = new TermsListImpl("data/Terms.txt");
		ArrayList<Terms> existTermsList = termsList.retrieveAllTerms();
		for(int i=0;i<existTermsList.size();i++)
			if(existTermsList.get(i).matchID(this.termsID)) return true;
		return false;
	}
}