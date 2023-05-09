package customer;

import java.util.ArrayList;

public class FamilyHistoryListImpl {

	private ArrayList<FamilyHistory> familyHistoryList;

	public FamilyHistoryListImpl(){
		this.familyHistoryList = new ArrayList<FamilyHistory>();
	}

	public boolean add(FamilyHistory familyHistory){
		if(this.familyHistoryList.add(familyHistory)) return true;	
		else return false;
	}

	public boolean delete(int customerID){
		for(FamilyHistory familyHistory : this.familyHistoryList) {
			if(familyHistory.getCustomerID() == customerID) {
				if(this.familyHistoryList.remove(familyHistory)) return true;
				break;
			}
		}
		return false;
	}


	public boolean update(FamilyHistory familyHistory, int customerID){
		for(FamilyHistory ufamilyHistory : this.familyHistoryList) {
			if(ufamilyHistory.getCustomerID() == customerID) {
				ufamilyHistory.setDiseaseName(familyHistory.getDiseaseName());
				ufamilyHistory.setRelationship(familyHistory.getRelationship());
			}
		}
		return false;
	}
	public ArrayList<FamilyHistory> retrieve() {
		return familyHistoryList;
	}
	public void setRetrieve(ArrayList<FamilyHistory> familyHistoryList) {
		this.familyHistoryList = familyHistoryList;
	}
}
