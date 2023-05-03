package Customer;

import java.util.ArrayList;

public interface FamilyHistoryList {

	public FamilyHistoryListImpl m_FamilyHistoryListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<FamilyHistory> retrieve();

	public boolean update();

}