package Customer;

import java.util.ArrayList;

public interface FamilyHistoryList {

	public boolean add(FamilyHistory familyHistory);

	public boolean delete(int customerID);

	public ArrayList<FamilyHistory> retrieve();

	public boolean update(FamilyHistory familyHistory,int customerID);

}
