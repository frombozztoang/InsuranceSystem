package Counsel;

import java.util.ArrayList;
import java.util.List;

import Dao.CounselDao;

public class CounselListImpl implements CounselList {

	private ArrayList<Counsel> counselList;
	private CounselDao counselDao;

	public CounselListImpl() throws Exception {
		this.counselDao = new CounselDao();
		this.counselList = counselDao.retrieveAll();
	}

	@Override
	public boolean add(Counsel counsel) throws Exception {
		if(this.counselList.add(counsel)) {
			counselDao.create(counsel);
			return true;}
		else return false;
	}

	@Override
	public boolean delete(String counselID) throws Exception {
		for (Counsel counsel : counselList) { // 배열 내 삭제
			if (counsel.getCounselID().equals(counselID)) {
				counselList.remove(counsel);
				break;
			}
		}
		counselDao.deleteByCounselId(counselID); // DB 삭제
		return true;
	}

	@Override
	public ArrayList<Counsel> retrieve() {
		return null;
	}

	public void update(Counsel updateCounsel) throws Exception {
		counselDao.updateCounsel(updateCounsel);
	}
	public Counsel getCounselbyId(String customerID) {
		for (int i = 0; i < this.counselList.size(); i++) {
			Counsel counsel = (Counsel) this.counselList.get(i);
			if (counsel.matchId(customerID))
				return counsel;
		}
		return null;
	}

	public List<Counsel> getCounselList(String customerID) {
		List<Counsel> selectedCounsels = new ArrayList<Counsel>();
		for (Counsel counsel : counselList) {
			if (counsel.getCustomerID().equals(customerID))
				selectedCounsels.add(counsel);
		}
		return selectedCounsels;
	}
}
