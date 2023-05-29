package Insurance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
	

}
