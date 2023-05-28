package Contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Customer.Customer;

public class ContractListImpl {

	private ArrayList<Contract> contractList;
	public Contract m_Contract;

	public ContractListImpl(String contractFileName) throws IOException, ParseException {
		BufferedReader contractFile = new BufferedReader(new FileReader(contractFileName));
		this.contractList = new ArrayList<Contract>();
		while (contractFile.ready()) {
			Contract contract = makeContract(contractFile.readLine());
			if (contract != null)
				this.contractList.add(contract);
		}
		contractFile.close();
	}

	public static LocalDate stringToDate(String dateString) {
		String[] dateParts = dateString.split("-");
		int year = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[2]);
		return LocalDate.of(year, month, day);
	}

	private Contract makeContract(String contractInfo) throws ParseException {
		Contract contract = new Contract();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

		StringTokenizer stringTokenizer = new StringTokenizer(contractInfo);
//		contract.setContractIndex(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setCustomerID(stringTokenizer.nextToken());
		contract.setInsuranceID(stringTokenizer.nextToken());
		contract.setInsurancePeriod(stringTokenizer.nextToken());
		contract.setPremium(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setPaymentCycle(stringTokenizer.nextToken());
		contract.setMaxCompensation(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setStringDateOfSubscription(stringTokenizer.nextToken());
		LocalDate dateOfSubscription = stringToDate(contract.getStringDateOfSubscription());
		contract.setDateOfSubscription(dateOfSubscription);
		contract.setStringDateOfMaturity(stringTokenizer.nextToken());
		LocalDate dateOfMaturity = stringToDate(contract.getStringDateOfMaturity());
		contract.setDateOfMaturity(dateOfMaturity);
		contract.setMaturity(Boolean.parseBoolean(stringTokenizer.nextToken()));
		contract.setResurrection(Boolean.parseBoolean(stringTokenizer.nextToken()));
		contract.setCancellation(Boolean.parseBoolean(stringTokenizer.nextToken()));
		contract.setM_Payment(new Payment());

		return contract;
	}

	public void finalize() throws Throwable {

	}

	public boolean add() throws IOException {
		if (this.contractList.add(new Contract())) {
			updateFile("Contract.txt");
			return true;
		}
		return false;
	}

	private void updateFile(String string) throws IOException {
		File file = new File(string);
		if (!file.exists())
			file.createNewFile();
		String contractInfo = "";
		if (!contractList.isEmpty()) {
			contractInfo = contractList.get(0).toString();
		}
		BufferedWriter contractFileWriter = new BufferedWriter(new FileWriter(file));
		for (int i = 1; i < this.contractList.size(); i++)
			contractInfo = contractInfo + "\r\n" + contractList.get(i).toString();

		contractFileWriter.write(contractInfo);
		contractFileWriter.flush();
		contractFileWriter.close();

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
				this.contractList.get(i).setCancellation(this.contractList.get(i).updateCancellation());
				updateFile("data/Contract.txt");
				return true;
			}
		}

		return false; // exception
	}

//	public boolean isMatchCustomerContract(int contractIndex, String customerID) {
//		for (int i = 0; i < this.contractList.size(); i++) {
//			if (this.contractList.get(i).matchCustomerContract(contractIndex, customerID))
//				return true;
//		}
//		return false;
//	}
//
//	public boolean update() {
//		return false;
//	}
//
//	public boolean getCustomerMaturity(String customerID, int contractIndex) {
//		for (int i = 0; i < this.contractList.size(); i++) {
//			if (this.contractList.get(i).matchCustomerContract(contractIndex, customerID))
//				return contractList.get(i).isMaturity();
//		}
//		return false;
//	}
//
//	public boolean updateMaturity(int contractIndex) throws IOException {
//		for (int i = 0; i < this.contractList.size(); i++) {
//			if (this.contractList.get(i).matchContractIndex(contractIndex)) {
//				this.contractList.get(i).setMaturity(!this.contractList.get(i).updateMaturity());
//				updateFile("Contract.txt");
//				return true;
//			}
//		}
//
//		return false; // exception
//	}

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
}// end ContractListImpl