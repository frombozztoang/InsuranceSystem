package Counsel;
import java.util.Date;

import Customer.Customer;


public class CounselApplication {

	private String category;
	private int counselID;
	private int customerID;
	private Date dateOfFirst;
	private Date dateOfSecond;
	private String requirement;
	public Customer m_Customer;
	public Counsel m_Counsel;

	public CounselApplication(){

	}

	public void finalize() throws Throwable {

	}
	public boolean requireCounsel(){
		return false;
	}
}//end CounselApplication