package Counsel;

import java.util.ArrayList;
import java.util.Date;

public class Counsel {

	private String content;
	private int counselID;
	private int customerID;
	private Date dateOfCounsel;
	private String managerName;
	private String requirement;

	public Counsel(){

	}

	public void finalize() throws Throwable {

	}
	public boolean createCounsel(){
		return false;
	}

	public boolean createCounselContent(){
		return false;
	}

	public boolean deleteCounsel(){
		return false;
	}

	public String retrieveCounselContent(){
		return "";
	}

	public Counsel retrieveCounselDetail(){
		return null;
	}

	public ArrayList<Counsel> retrieveCounselList(){
		return null;
	}
}//end Counsel