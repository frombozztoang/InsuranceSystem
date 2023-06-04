package Counsel;

import java.util.ArrayList;


public interface CounselApplicationList {

	public boolean add(CounselApplication counselApplication)throws Exception;
	public boolean delete(String counselID);
	public boolean update(CounselApplication counselApplication, String counselID);
	public ArrayList<CounselApplication> retrieve();
	public CounselApplication getCounselApplicationById(String id);
}
