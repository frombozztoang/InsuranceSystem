package Counsel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


public class CounselApplication implements Serializable {

	private static final long serialVersionUID = 1L;

	private String category;
	private String counselID;
	private String customerID;
	private LocalDate dateOfFirst;
	private LocalDate dateOfSecond;
	private String requirement;
	
	private Counsel counsel;
	private ArrayList<CounselApplication> CounselApplicationList;

	public CounselApplication() {
		counsel = new Counsel();
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCounselID() {
		return counselID;
	}

	public void setCounselID(String counselID) {
		this.counselID = counselID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public LocalDate getDateOfFirst() {
		return dateOfFirst;
	}

	public void setDateOfFirst(LocalDate dateOfFirst) {
		this.dateOfFirst = dateOfFirst;
	}

	public LocalDate getDateOfSecond() {
		return dateOfSecond;
	}

	public void setDateOfSecond(LocalDate dateOfSecond) {
		this.dateOfSecond = dateOfSecond;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public Counsel getCounsel() {
		return counsel;
	}

	public void setCounsel(Counsel counsel) {
		this.counsel = counsel;
	}


	public ArrayList<CounselApplication> getCounselApplicationList() {
		return CounselApplicationList;
	}

	public void setCounselApplicationList(ArrayList<CounselApplication> counselApplicationList) {
		CounselApplicationList = counselApplicationList;
	}
	
}
