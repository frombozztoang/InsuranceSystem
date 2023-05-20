package Counsel;

import java.util.ArrayList;
import java.util.List;

import Customer.Customer;

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
//		for(CounselList counselList : this.counselList) {
//			if(counselList.getCounselID() == counselID) {
//				if(this.counselList.remove(counselID)) return true;
//				break;
//			}
//		}
		return false;
	}


	public boolean update(CounselList counselList, int counselID){
//		for(CounselList ucounselList : this.counselList) {
//			if(ucounselList.getCustomerID() == customerID) {
//				ucounselList.setDiseaseName(counselList.getDiseaseName());
//				ucounselList.setRelationship(counselList.getRelationship());
//			}
//		}
		return false;
	}
	public ArrayList<CounselList> retrieve() {
		return counselList;
	}
	public void setRetrieve(ArrayList<CounselList> familyHistoryList) {
		this.counselList = familyHistoryList;
	}

	
	
}
