package Counsel;

import java.util.ArrayList;

public class CounselListImpl {

	private ArrayList<CounselList> counselList;

	public CounselListImpl(){
		this.counselList = new ArrayList<CounselList>();
	}

	public boolean add(CounselList counselList){
		if(this.counselList.add(counselList)) return true;	
		else return false;
	}

	public boolean delete(int counselID){

		return false;
	}


	public boolean update(CounselList counselList, int counselID){

		return false;
	}
	public ArrayList<CounselList> retrieve() {
		return counselList;
	}
	public void setRetrieve(ArrayList<CounselList> familyHistoryList) {
		this.counselList = familyHistoryList;
	}
}
