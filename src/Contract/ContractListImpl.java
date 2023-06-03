package Contract;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import Customer.Customer;
import Dao.ContractDao;

public class ContractListImpl {

	private ArrayList<Contract> contractList;
	private ContractDao contractDao;
	public Contract m_Contract;

	public ContractListImpl() throws Exception {
		this.contractDao = new ContractDao();
		this.contractList = contractDao.retrieveAll();
	}

	public static LocalDate stringToDate(String dateString) {
		String[] dateParts = dateString.split("-");
		int year = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[2]);
		return LocalDate.of(year, month, day);
	}

	public void finalize() throws Throwable {

	}

	public boolean add(Contract contract) throws IOException {
		if (this.contractList.add(contract)) {

			return true;
		} else
			return false;
	}

	public boolean delete() {
		return false;
	}

	public ArrayList<Contract> retrieve() throws Exception {
		if (this.contractList.size() == 0)
			throw new Exception("payment 데이터가 없습니다.");
		return this.contractList;
	}

	public ArrayList<Contract> retreiveCustomerContract(String customerID) {
		ArrayList<Contract> customerContracts = new ArrayList<Contract>();
		for (Contract contract : this.contractList) {
			if (contract.matchCustomerID(customerID)) {
				customerContracts.add(contract);
			}
		}
		return customerContracts;
	}

	public ArrayList<String> retreiveCustomerContractStatus(String customerId) {
		ArrayList<String> contractStatus = new ArrayList<String>();
		Payment payment = new Payment();

		for (Contract contract : this.contractList) {
			if (contract.matchCustomerID(customerId)) {
				contractStatus.add(contract.isMaturity() + " " + contract.isCancellation());
			}
		}

		return contractStatus;
	}

	public Contract getContractByInsuranceID(String insuranceId) {
		for (Contract contract : this.contractList) {
			if (contract.getInsuranceID().equals(insuranceId)) {
				return contract;
			}
		}

		return null;
	}

	public Boolean updateCancellation(String customerId, String insuranceId) throws IOException {
		for (int i = 0; i < this.contractList.size(); i++) {
			if (this.contractList.get(i).getCustomerID().equals(customerId)
					&& contractList.get(i).getInsuranceID().equals(insuranceId)) {

				contractDao.updateCancellation(customerId, insuranceId, !this.contractList.get(i).isCancellation());
				return true;
			}
		}

		return false; // exception
	}

	public void setResurrectFromCustomer(Customer customer) {
		for (Contract contract : contractList) {
			if (contract.getCustomerID().equals(customer.getCustomerID()))
				contract.setResurrection(false);
		}
	}

	public void setMaturityFromCustomer(Customer customer) {
		for (Contract contract : contractList) {
			if (contract.getCustomerID().equals(customer.getCustomerID()))
				contract.setMaturity(false);
		}
	}

	public void setWheaterPaymentFromCustomer(Customer customer) {
		for (Contract contract : contractList) {
			if (contract.getCustomerID().equals(customer.getCustomerID()))
				contract.m_Payment.setWhetherPayment(true);
		}
	}

	public ArrayList<Contract> getContractsByCID(String inputCustomerId) throws IOException, ParseException {
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		for (int i = 0; i < this.contractList.size(); i++) {
			if (this.contractList.get(i).matchCID(inputCustomerId))
				contracts.add(this.contractList.get(i));
		}
		return contracts;
	}

	public ArrayList<String> getInsuranceIdFromCustomerId(String customerId) {
		ArrayList<String> insuranceIdFromCustomerId = new ArrayList<String>();
		for (Contract contract : contractList) {
			if (contract.getCustomerID().equals(customerId)) {
				insuranceIdFromCustomerId.add(contract.getInsuranceID());
			}
		}
		return insuranceIdFromCustomerId;

	}

	public ArrayList<String> retreivePremiumById(String selectedCustomerId, String selectedInsuranceId) {
		ArrayList<String> premiums = new ArrayList<String>();
		for (int i = 0; i < this.contractList.size(); i++) {
			if (contractList.get(i).getCustomerID().equals(selectedCustomerId)
					&& contractList.get(i).getInsuranceID().equals(selectedInsuranceId)) {
				premiums.add(Integer.toString(contractList.get(i).getPremium()));
			}
		}
		return premiums;
	}

	public boolean update(Contract updatedContract) {
		for (int i = 0; i < this.contractList.size(); i++) {
			Contract contract = (Contract) this.contractList.get(i);
			if (contract.match(updatedContract.getInsuranceID(), updatedContract.getCustomerID())) {
				this.contractList.set(i, updatedContract);
				contractDao.update(updatedContract);
				return true;
			}
		}
		return false;
	}
}// end ContractListImpl