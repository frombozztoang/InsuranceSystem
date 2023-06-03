package CompensationClaim;


import Dao.CompensationClaimDao;
import java.util.ArrayList;

public class CompensationClaimListImpl {

	private ArrayList<CompensationClaim> compensationClaimList;
	private CompensationClaimDao compensationClaimDao;
	public SurveyListImpl surveyList;

	public CompensationClaimListImpl() throws Exception {
		this.compensationClaimDao = new CompensationClaimDao();
		this.compensationClaimList = compensationClaimDao.retrieveAll();
		surveyList = new SurveyListImpl();
	}
	public void finalize() throws Throwable {
	}
	public ArrayList<CompensationClaim> retrieve(){
		return compensationClaimList;
	}
	public boolean update(){
		return false;
	}
	public boolean createCompensationClaim(CompensationClaim compensationClaim) throws Exception {
		if (this.compensationClaimList.add(compensationClaim)) {
			compensationClaimDao.create(compensationClaim);
			Survey survey = new Survey();
			surveyList.create(survey);
			return true;
		}
		else return false;
	}

    public CompensationClaim getCompensationClaimbyID(String inputCCID) {
		for(int i=0;i<this.compensationClaimList.size();i++) {
			CompensationClaim compensationClaim = (CompensationClaim) this.compensationClaimList.get(i);
			if(compensationClaim.matchId(inputCCID))
				return compensationClaim;
		}
		return null;
    }
}//end CompensationClaimListImpl