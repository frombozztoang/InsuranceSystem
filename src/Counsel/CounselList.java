package counsel;

import java.util.ArrayList;


public interface CounselList {

	
	public boolean add(CounselList counselList);

	public boolean delete(int counselID);

	public ArrayList<CounselList> retrieve();

	public boolean update(CounselList counselList,int counselID);

}
