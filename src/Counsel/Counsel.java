package counsel;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import customer.Customer;

public class Counsel {
	private String content;
	private int counselID;
	private int customerID;
	private Date dateOfCounsel;
	private String managerName;
	private String requirement;
	private ArrayList<Counsel> counselList;
	private Customer customer;
	//composition
	public CounselApplication counselApplication;
	public Counsel(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.content = stringTokenizer.nextToken();
		this.managerName = stringTokenizer.nextToken();

	}
	public Counsel() {
		this.counselApplication = new CounselApplication();
	}
	 public Counsel (String managerName, int customerID, Customer customer, String requirement) {
		
	    	this.managerName = managerName;
	    	this.customerID=customerID;
	    	this.customer= customer;
	    	this.requirement=requirement;
	    	
	    }
	public ArrayList<Counsel> retrieveCounselList(int customerID) {
		ArrayList<Counsel> customerCounselList = new ArrayList<>();
		// 지정된 고객에 대한 상담 일정 검색
		for (Counsel counsel : counselList) {
			if (counsel.getCustomerID() == customerID) {
				customerCounselList.add(counsel);
			}
		}

		return customerCounselList;
	}

	public Counsel retrieveCounselDetail(int counselID) {
		// 지정된 ID로 상담 일정 찾음
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID() == counselID) {
				return counsel;
			}
		}
		// 상담일정을 찾을 수 없음.
		return null; 
	}

	public String retrieveCounselContent(int counselID) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID() == counselID) {
				return counsel.getContent();
			}
		}

		// 상담일정을 찾을 수 없음.
		return null; 
	}

	public boolean deleteCounsel(int counselID) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID() == counselID) {
				counselList.remove(counsel);
				return true; 
			}
		}
		// 상담 일정을 찾을 수 없거나 삭제하지 못함.
		return false; 
	}

	public boolean createCounsel(String managerName, int customerID, Customer customer, String requirement) {
		// 새 상담 일정 만들기
		Counsel newCounsel = new Counsel(managerName, customerID, customer, requirement);

		if (counselList.add(newCounsel)) {
			//더한 것 성공
			return true;
		}

		return false;
	}

	public boolean createCounselContent(int counselID, String content) {
		// 지정된 ID로 상담 일정 찾기
		for (Counsel counsel : counselList) {
			if (counsel.getCounselID() == counselID) {
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

	public int getCounselID() {
		return counselID;
	}

	public void setCounselID(int counselID) {
		this.counselID = counselID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
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
