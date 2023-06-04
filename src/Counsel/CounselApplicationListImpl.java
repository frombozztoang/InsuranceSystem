package Counsel;
import java.util.ArrayList;

import Dao.CounselApplicationDao;


public class CounselApplicationListImpl implements CounselApplicationList{

	private ArrayList<CounselApplication> CounselApplicationList;
	private CounselApplicationDao counselApplicationDao;

	public CounselApplicationListImpl() throws Exception {
		this.counselApplicationDao = new CounselApplicationDao();
		this.CounselApplicationList = counselApplicationDao.retrieveAll();
	}
	

	@Override
	public boolean add(CounselApplication counselApplication) throws Exception {
		if(this.CounselApplicationList.add(counselApplication)) {
			counselApplicationDao.create(counselApplication);
			return true;
		}
		else return false;
	}

	@Override
	public boolean delete(String counselID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CounselApplication counselApplication, String counselID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CounselApplication> retrieve() {
		// TODO Auto-generated method stub
		return null;
	}
	public CounselApplication getCounselApplicationById(String id) {
		for (CounselApplication counselApplication : CounselApplicationList) {
			if (counselApplication.getCustomerID().equals(id)) 
				return counselApplication;
		}
		return null;
	}
}
