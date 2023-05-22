package Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FamilyHistoryListImpl {

	private ArrayList<FamilyHistory> familyHistoryList;

	public FamilyHistoryListImpl(String familyFileName) throws IOException, ParseException {
		BufferedReader familyFile = new BufferedReader(new FileReader(familyFileName));
		this.familyHistoryList = new ArrayList<FamilyHistory>();
		while (familyFile.ready()) {
			FamilyHistory familyHistory = makeFamilyHistory(familyFile.readLine());
			if (familyHistory != null)
				this.familyHistoryList.add(familyHistory);
		}
		familyFile.close();
	}

	private FamilyHistory makeFamilyHistory(String familyInfo) throws ParseException {
		FamilyHistory familyHistory = new FamilyHistory();

		StringTokenizer stringTokenizer = new StringTokenizer(familyInfo);
		familyHistory.setCustomerID(stringTokenizer.nextToken());
		familyHistory.setDiseaseName(stringTokenizer.nextToken());
		familyHistory.setRelationship(stringTokenizer.nextToken());
		return familyHistory;
	}

	public boolean add(FamilyHistory familyHistory) {
		if (this.familyHistoryList.add(familyHistory))
			return true;
		else
			return false;
	}

	public boolean delete(String customerID) {
		for (FamilyHistory familyHistory : this.familyHistoryList) {
			if (familyHistory.getCustomerID().equals(customerID)) {
				if (this.familyHistoryList.remove(familyHistory))
					return true;
				break;
			}
		}
		return false;
	}

	public boolean update(FamilyHistory familyHistory, String customerID) {
		for (FamilyHistory ufamilyHistory : this.familyHistoryList) {
			if (ufamilyHistory.getCustomerID().equals(customerID)) {
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

	public FamilyHistory getFamilyHistoryFromId(String id, FamilyHistoryListImpl familyHistoryListImpl) { // 고객 아이디에 맞는
																											// 가족력 반환
		ArrayList<FamilyHistory> familyHistories = familyHistoryListImpl.retrieve();
		for (FamilyHistory familyHistory : familyHistories) {
			if (familyHistory.getCustomerID().equals(id)) {
				return familyHistory;
			}
		}
		return null;
	}

	public ArrayList<FamilyHistory> getAllFamilyHistoryFromId(String id, FamilyHistoryListImpl familyHistoryListImpl) {
		ArrayList<FamilyHistory> familyHistories = familyHistoryListImpl.retrieve();
		ArrayList<FamilyHistory> matchingFamilyHistories = new ArrayList<>();

		for (FamilyHistory familyHistory : familyHistories) {
			if (familyHistory.getCustomerID().equals(id)) {
				matchingFamilyHistories.add(familyHistory);
			}
		}

		return matchingFamilyHistories;
	}
}
