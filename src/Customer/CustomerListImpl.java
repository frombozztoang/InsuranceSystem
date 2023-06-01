package Customer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import Contract.Contract;
import Contract.ContractListImpl;
import Counsel.CounselApplication;
import Customer.Customer.EGender;
import Dao.CustomerDao;
import Insurance.Guarantee;

public class CustomerListImpl implements CustomerList {

	// 만기계약대상자/미납대상자/부활대상자 enum
	public enum TargetType {
		EXPIRED_CONTRACTS, UNPAID_CUSTOMERS, RESURRECT_CANDIDATES
	}

	private ArrayList<Customer> customerList;
	private ArrayList<Customer> expiredContracts; // 만기계약 대상자 리스트
	private ArrayList<Customer> unpaidCustomers; // 미납대상자 리스트
	private ArrayList<Customer> resurrectCandidates; // 부활대상자 리스트
	private ContractListImpl contractList;
	private CustomerDao customerDao;
	public CustomerListImpl()throws Exception {
		this.customerDao = new CustomerDao();
		this.customerList = customerDao.retrieveAll();
	}

	
	public ArrayList<Customer> getResurrectCandidates(boolean resurrection) throws Exception {
	    resurrectCandidates = new ArrayList<Customer>();
	    HashMap<String, Boolean> customerMap = new HashMap<String, Boolean>(); // 중복 호출 방지를 위한 맵

	    for (Customer customer : customerList) {
	        if (customerMap.containsKey(customer.getCustomerID())) {
	            continue;
	        }

	        for (Contract contract : contractList.retrieve()) {
	            if (customer.getCustomerID().equals(contract.getCustomerID())) {
	                if (contract.isResurrection() == resurrection) {
	                    resurrectCandidates.add(customer);
	                    customerMap.put(customer.getCustomerID(), true);
	                    break;
	                }
	            }
	        }
	    }
	    return resurrectCandidates;
	}

	public ArrayList<Customer> getExpiredContracts(boolean maturity) throws Exception {
		expiredContracts = new ArrayList<Customer>(); // 만기계약 리스트
		HashMap<String, Boolean> customerMap = new HashMap<String, Boolean>(); // 중복 호출 방지를 위한 맵

		for (Customer customer : customerList) {
			if (customerMap.containsKey(customer.getCustomerID())) {
				continue; // 이미 출력된 고객이므로 중복 호출 방지
			}

			for (Contract contract : contractList.retrieve()) {
				if (customer.getCustomerID().equals(contract.getCustomerID())) {
					if (contract.isMaturity()) {
						// 새로 만듬 -> 1 3 4
						// 1 3 4 -> 1 2 3 4
						expiredContracts.add(customer);
						customerMap.put(customer.getCustomerID(), true); // 고객 ID를 맵에 추가
						break; // 해당 고객의 계약이 만기되었으므로 다음 고객으로 넘어감
					}
				}
			}
		}
		return expiredContracts;

	}


	private Customer makeCustomer(String customerInfo) throws ParseException {
		Customer customer = new Customer();

		StringTokenizer stringTokenizer = new StringTokenizer(customerInfo);
		customer.setCustomerID(stringTokenizer.nextToken());
		customer.setCustomerName(stringTokenizer.nextToken());
		customer.setBirth(stringTokenizer.nextToken());
		customer.setEGender(stringTokenizer.nextToken().equals("남") ? EGender.male : EGender.female);
		customer.setPnumber(stringTokenizer.nextToken());
		customer.setJob(stringTokenizer.nextToken());

		StringBuffer buffer = new StringBuffer();
		while (stringTokenizer.hasMoreTokens()) {
			buffer.append(stringTokenizer.nextToken());
			buffer.append(" ");
		}
		customer.setAddress(buffer.toString());
		return customer;
	}

	public Customer retrieveCustomer(String customerID) {
		// 지정된 ID를 가진 고객 찾기
		for (Customer customer : customerList) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		// Customer가 없을 때
		return null;
	}

	// 이름 & 휴대전화로 고객 정보 찾기
	public String getCustomerIdFromNameAndPH(String customerName, String customerPH) {
		for (Customer customer : customerList) {
			if (customer.getCustomerName().equals(customerName) && customer.getPnumber().equals(customerPH)) {
				return customer.getCustomerID();
			}
		}
		return null;

	}

	public Customer retrieveCustomerFromResurrect(String customerID) {
		for (Customer customer : resurrectCandidates) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}

	public Customer retrieveCustomerFromExpired(String customerID) {
		for (Customer customer : expiredContracts) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}


	public boolean deleteResurrectCandidatesCustomer(Customer customer) { // 부활 대상자에서 제외
		return resurrectCandidates.remove(customer);
	}

	public boolean deleteExpiredCustomer(Customer customer) { // 만기계약 대상자에서 제외
		return expiredContracts.remove(customer);
	}

	public boolean deleteUnpaidCustomer(Customer customer) { // 미납 대상자에서 제외
		return unpaidCustomers.remove(customer);
	}

	public Customer getCustomerFromCouncels(CounselApplication counselApplication, CustomerListImpl customerListImpl) {
		Customer selectedCustomer = null;
		for (Customer customer : customerListImpl.retrieve()) {
			if (customer.getCustomerID().equals(counselApplication.getCustomerID()))
				selectedCustomer = customer;
		}
		return selectedCustomer;
	}

	public static List<Contract> getContractFromCustomerId(String id, ContractListImpl contractListImpl)
			throws Exception {
		List<Contract> selectedContracts = new ArrayList<Contract>();
		ArrayList<Contract> contracts = contractListImpl.retrieve();
		for (Contract contract : contracts) {
			if (contract.getCustomerID().equals(id)) {
				selectedContracts.add(contract);
			}
		}
		// 여기서 한 사람당 계약 정보가 둘 이상일 때 첫 번째 계약만 값을 받아옴
		return selectedContracts;
	}

	public Customer getCustomerByID(String customerID) {
		for (int i = 0; i < this.customerList.size(); i++) {
			Customer customer = (Customer) this.customerList.get(i);
			if (customer.matchId(customerID))
				return customer;
		}
		return null;
	}
	public ArrayList<Customer> retrieve() {
		return customerList;
	}

	public boolean delete(String customerID) {
		for (Customer customer : this.customerList) {
			if (customer.getCustomerID().equals(customerID)) {
				if (this.customerList.remove(customer))
					return true;
				break;
			}
		}
		return false;
	}

	public boolean update(Customer customer, String customerID) {
		for (int i = 0; i < customerList.size(); i++) {
			if (customerList.get(i).getCustomerID().equals(customerID))
				customerList.set(i, customer);
		}
		return false;
	}
	public Customer retrieveCustomerFromUnpaid(String customerID) {
		for (Customer customer : unpaidCustomers) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}



}
