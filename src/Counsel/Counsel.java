package Counsel;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import Customer.Customer;

public class Counsel {
	private String content;
	private String managerName;
	private Date dateOfCounsel;

	private String counselID;
	private String customerID;
	private String requirement;
	private ArrayList<Counsel> counselList;
	private Customer customer;

	// composition
	public Counsel(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.content = stringTokenizer.nextToken();
		this.managerName = stringTokenizer.nextToken();

	}

	public Counsel() {

	}

	public Counsel(String managerName, String customerID, Customer customer, String requirement) {

		this.managerName = managerName;
		this.customerID = customerID;
		this.customer = customer;
		this.requirement = requirement;

	}

	public ArrayList<Counsel> retrieveCounselList(String customerID) {
		ArrayList<Counsel> customerCounselList = new ArrayList<>();
		// 지정된 고객에 대한 상담 일정 검색
		for (Counsel counsel : counselList) {
			if (counsel.getCustomerID().equals(customerID)) {
				customerCounselList.add(counsel);
			}
		}

		return customerCounselList;
	}

	public Counsel retrieveCounselDetail(String counselID) {
		// 지정된 ID로 상담 일정 찾음
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID().equals(counselID)) {
				return counsel;
			}
		}
		// 상담일정을 찾을 수 없음.
		return null;
	}

	public String retrieveCounselContent(String counselID) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID() == counselID) {
				return counsel.getContent();
			}
		}

		// 상담일정을 찾을 수 없음.
		return null;
	}

	public boolean deleteCounsel(String counselID) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID().equals(counselID)) {
				counselList.remove(counsel);
				return true;
			}
		}
		// 상담 일정을 찾을 수 없거나 삭제하지 못함.
		return false;
	}

	public boolean createCounsel(String managerName, String customerID, Customer customer, String requirement) {
		// 새 상담 일정 만들기
		Counsel newCounsel = new Counsel(managerName, customerID, customer, requirement);

		if (counselList.add(newCounsel)) {
			// 더한 것 성공
			return true;
		}

		return false;
	}

	public boolean createCounselContent(String counselID, String content) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID().equals(counselID)) {
				// Set the content of the counseling schedule
				counsel.setContent(content);
				return true; // Addition successful
			}
		}

		return false; // Counseling schedule not found or addition unsuccessful
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getDateOfCounsel() {
		return dateOfCounsel;
	}

	public void setDateOfCounsel(Date dateOfCounsel) {
		this.dateOfCounsel = dateOfCounsel;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
