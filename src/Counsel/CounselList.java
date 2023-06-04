package Counsel;

import java.util.ArrayList;


public interface CounselList {

	
	public boolean add(Counsel counsel)throws Exception;

	public boolean delete(String counselID) throws Exception;

	public ArrayList<Counsel> retrieve();

	public void update(Counsel updateCounsel)throws Exception;
	public Counsel getCounselbyId(String customerID);
}
