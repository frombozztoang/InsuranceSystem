package Insurance;

public class Terms {

	private String termsID;
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


	public String getTermsID() {
		return termsID;
	}


	public void setTermsID(String termsID) {
		this.termsID = termsID;
	}


	public String getTermsName() {
		return termsName;
	}


	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}
	  public String toString() {
	        String stringReturn = this.termsID + "," + this.termsName + "," + this.termsContent  + "," + this.calculatedMoneyMethod;
	        return stringReturn;
	    }

	public boolean matchID(String termsID) {
		return this.termsID.equals(termsID);
	}

	public boolean checkAllFillIn() {
		boolean AllFullIn = true;
		if(this.termsID.isEmpty()) AllFullIn = false;
		if(this.termsName.isEmpty()) AllFullIn = false;
		if(this.calculatedMoneyMethod.isEmpty()) AllFullIn = false;
		if(this.termsContent.isEmpty()) AllFullIn = false;
		return AllFullIn;
	}
}
