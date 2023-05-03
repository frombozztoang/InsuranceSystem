package Insurance;
import java.util.ArrayList;

import Contract.Contract;

public class Insurance {

	private String ageOfTarget;
	private boolean authorization;
	private int basicPremium;
	private boolean distributionStatus;
	private int insuranceClausePeriod;
	private int insuranceID;
	private String insuranceName;
	private int maxCompensation;
	private String paymentCycle;
	private String paymentPeriod;
	private String periodOfInsurance;
	private String precaution;
	private String rate;
	private String type;
	public Contract m_Contract;
	public Guarantee m_Guarantee;
	public InsuranceApplication m_InsuranceApplication;

	public Insurance(){

	}

	public void finalize() throws Throwable {

	}
	public boolean createInsurance(){
		return false;
	}

	public boolean deleteInsurance(){
		return false;
	}

	public String requestAuthorization(){
		return "";
	}

	public boolean requestBanking(){
		return false;
	}

	public ArrayList<Insurance> retrieveInsurance(){
		return null;
	}

	public Insurance retrieveInsuranceDetail(){
		return null;
	}

	public boolean updateAuthorization(){
		return false;
	}

	public boolean updateInsurance(){
		return false;
	}
}//end Insurance