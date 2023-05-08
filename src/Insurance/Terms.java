package Insurance;
import java.util.ArrayList;


public class Terms {

	private int termsID;
	private String termsName;
	private String calculatedMoneyMethod;
	private String termsContent;

	public Terms(){

	}

	public String getCalculatedMoneyMethod() {
		return calculatedMoneyMethod;
	}


	public void setCalculatedMoneyMethod(String calculatedMoneyMethod) {
		this.calculatedMoneyMethod = calculatedMoneyMethod;
	}


	public String getTermsContent() {
		return termsContent;
	}


	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}


	public int getTermsID() {
		return termsID;
	}


	public void setTermsID(int termsID) {
		this.termsID = termsID;
	}


	public String getTermsName() {
		return termsName;
	}


	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}
	  public String toString() {
	        String stringReturn = this.termsID + "/" + this.termsName + "/" + this.calculatedMoneyMethod + "/" + this.termsContent;
	        return stringReturn;
	    }

	public boolean matchID(int termsID) {
		return this.termsID == termsID;
	}

	
}