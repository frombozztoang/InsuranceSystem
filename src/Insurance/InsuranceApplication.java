package Insurance;

import java.util.ArrayList;
import java.util.Date;

public class InsuranceApplication {

	private boolean approval;
	private Date createdAt;
	private int customerID;
	private String insuranceID;
	private String insurancePeriod;
	private int maxCompensation;
	private String paymentCycle;
	private int premium;
	private String reasonOfApproval;
	private String subscriptionFilePath;

	public InsuranceApplication(){

	}

	public void finalize() throws Throwable {

	}
	public String approveInsuranceApplication(){
		return "";
	}

	public InsuranceApplication calculationRate(){
		return null;
	}

	public boolean createInsuranceApplication(){
		return false;
	}

	public ArrayList<InsuranceApplication> retrieveInsuranceApplication(){
		return null;
	}

	public InsuranceApplication retrieveInsuranceApplicationDetail(){
		return null;
	}

	public String retrieveSubscription(){
		return "";
	}
}//end InsuranceApplication