package Counsel;

import java.util.ArrayList;


public interface CounselApplicationList {

	public boolean add(CounselApplication counselApplication);
	public boolean delete(int counselID);
	public boolean update(CounselApplication counselApplication, int counselID);
	public ArrayList<CounselApplication> retrieve();

}
