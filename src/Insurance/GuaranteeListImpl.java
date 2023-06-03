package Insurance;

import java.util.ArrayList;

import Dao.GuaranteeDao;

public class GuaranteeListImpl implements GuaranteeList{

	private ArrayList<Guarantee> guaranteeList;
	private GuaranteeDao guaranteeDao;

	public GuaranteeListImpl() throws Exception {
		guaranteeDao = new GuaranteeDao();
		this.guaranteeList = guaranteeDao.retrieveAll();
	}

	@Override
	public boolean create(Guarantee newguarantee) throws Exception{
		if(this.guaranteeList.add(newguarantee)) {
			guaranteeDao.create(newguarantee);
			return true;
		}
		else return false;
	}
	
	public boolean alreadyExistInsurance(String insuranceID) throws Exception{
		for(int i=0;i<this.guaranteeList.size();i++) {
			if(this.guaranteeList.get(i).matchInsuranceId(insuranceID)) 
				guaranteeList.remove(this.guaranteeList.get(i));
		}
		guaranteeDao.deleteByInsuranceId(insuranceID);
		return false;
	}
		
	public ArrayList<Guarantee> getAllGuranteeByID(String insuranceID) {
		ArrayList<Guarantee> guarantees = new ArrayList<Guarantee>();
		for(int i=0;i<this.guaranteeList.size();i++) {
			if(this.guaranteeList.get(i).matchID(insuranceID))
				guarantees.add(this.guaranteeList.get(i));
		}
		return guarantees;
	}

	public boolean deleteGuranteeById(String insuranceID) throws Exception{
		boolean DoDelete=false;
		for(int i=0;i<this.guaranteeList.size();i++) {
			if(this.guaranteeList.get(i).matchID(insuranceID)) {this.guaranteeList.remove(i); DoDelete=true;}
		}
		this.guaranteeDao.deleteByInsuranceId(insuranceID);
		return DoDelete;
	}
}
