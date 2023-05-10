package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

import Contract.ContractListImpl;
import Contract.PaymentListImpl;

import CompensationClaim.CarAccident;
import CompensationClaim.CarAccidentListImpl;
import CompensationClaim.CompensationClaim;
import CompensationClaim.CompensationClaimListImpl;
import CompensationClaim.Survey;
import Contract.ContractList;

import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.Terms;
import Insurance.TermsListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;

public class Main {

	private static CompensationClaimListImpl compensationClaimList;
	private static CarAccidentListImpl carAccidentList;
	private static ContractList contract;
	private static Survey survey;

	public static void main(String[] args) throws Exception {
		compensationClaimList = new CompensationClaimListImpl("data/CompensationClaim.txt");
		InsuranceListImpl insuranceList = new InsuranceListImpl("data/Insurance.txt");
		TermsListImpl termsListImpl = new TermsListImpl("data/Terms.txt");
		ContractListImpl contractListImpl = new ContractListImpl("data/Contract.txt");

		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		while (!userChoice.equals("x")) {
			showMenu();
			userChoice = inputReader.readLine().trim();
			switch (userChoice) {
			case "1":
				createCompensationClaim(insuranceList, compensationClaimList, inputReader);
				break;
			case "2":
				retrieveCompensationClaim(compensationClaimList, inputReader);
				break;
			case "3":
				showOnSaleInsurance(insuranceList, inputReader, "Customer");
				break;
			case "4":
				designInsurance(insuranceList, termsListImpl, inputReader);
				break;
			case "5":
				showContractList(contractListImpl, inputReader);
				break;
			default:
				System.out.println("Invalid Choice !!!");

			}
		}
	}

	private static void retrieveCompensationClaim(CompensationClaimListImpl compensationClaimList,
			BufferedReader inputReader) throws IOException {
		System.out.println("****************** Compensation Claim List *******************");
		System.out.println("����ID ��ID û��ID �����ڸ� ��������ȭ��ȣ �������ڿ��� ���� ���񼭷����ϰ�� ���� ���¹�ȣ �����ָ�");
		// ������ ����� û�� ID(����ID+��ID), �����ڸ�, ����� ����ؾ��ϳ� TUI������ list��ü�� ������
		System.out.println("1. ����� û�� ���� ����(�̱���)");
		System.out.println("2. ���ػ���");
		System.out.println("3. ��������� ���� ��û");
		String userChoice = "";
		userChoice = inputReader.readLine().trim();
		switch (userChoice) {
		case "1":
//				retrieveCompensationClaimDetail(compensationClaimList, inputReader);
			break;
		case "2":
			createSurvey(inputReader);
			break;
		case "3":
			// ���� ���� �ӽ� �ּ� ó���س����ϴ�.
//			survey.requestBanking();
		}
	}

	private static void createSurvey(BufferedReader inputReader) throws IOException {
	}

	private static void createCompensationClaim(InsuranceListImpl insuranceList,
			CompensationClaimListImpl compensationClaimList, BufferedReader inputReader)
			throws IOException, ParseException {
		CompensationClaim compensationClaim = new CompensationClaim();
		System.out.println("****************** Compensation Claim *******************");
		System.out.println("�������� Ȯ���� ���� ��ID�� �Է����ּ���");
		compensationClaim.setCustomerID(Integer.parseInt(inputReader.readLine().trim()));
		// �� ID�� ���� ��� Alter �߰��ؾ���
		System.out.println("������ �����Ͻ� ���� ������ �Ʒ��� �����ϴ�.\n" + "������� û���� ����ID�� �Է��ϼ���.");
//		���� ������ ���踮��Ʈ ��ȸ method (in contract)
		compensationClaim.setInsuranceID(Integer.parseInt(inputReader.readLine().trim()));
		compensationClaim.setCCID(Integer.parseInt(Integer.toString(compensationClaim.getInsuranceID())
				+ Integer.toString(compensationClaim.getCustomerID())));
		// id�� insurance ��ü ã�ƿ���
		System.out.println("��������: {insurance.type}\n" + "�����: {insurance.insuranceName}\n"
				+ "����� û���� ���� ���� ���(�ϳ��� �������Ϸ� ���ε�)\n" + "1. ����� û����" + "  2. ����(�ſ�)����ó�����Ǽ�" + "  3. ������ �ź���(�ո�) �纻"
				+ "  4. ������ ���� �纻\n" + "���������� �ڵ��������� ���, 5. ������ ��� Ȯ�ο�  6. ������ż�ó�����Ǽ� �߰� ����");
		System.out.print("�����ڸ�: ");
		compensationClaim.setReceptionistName(inputReader.readLine().trim());
		System.out.print("������ ��ȭ��ȣ: ");
		compensationClaim.setReceptionistPNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("�������ڿ��� ����(��:��): ");
		compensationClaim.setRelationshipOfContractor(inputReader.readLine().trim());
		System.out.print("���񼭷� ���ε��: ");
		compensationClaim.setDocumentFilePath(inputReader.readLine().trim());
		System.out.print("����: ");
		compensationClaim.setBank(inputReader.readLine().trim());
		System.out.print("���¹�ȣ: ");
		compensationClaim.setAccountNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("�����ָ�: ");
		compensationClaim.setAccountHolderName(inputReader.readLine().trim());
		if (compensationClaimList.createCompensationClaim(compensationClaim)) {
			if (insuranceList.getInsuranceTypebyId(compensationClaim.getInsuranceID()) == "Car")
				createCarAccident(compensationClaim, inputReader);
			System.out.println(compensationClaim.getCCID() + "��û�� �Ϸ�Ǿ����ϴ�. �ɻ� ����� ���� ������ ���ѵǰų� ������ �� �ֽ��ϴ�.");
		} else
			System.out.println("��û�� �����Ͽ����ϴ�. �ٽ� �õ����ֽʽÿ�.");
	}

	private static void createCarAccident(CompensationClaim compensationClaim, BufferedReader inputReader)
			throws IOException, ParseException {
		CarAccident carAccident = new CarAccident();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("****************** Compensation Claim - Car Accident*******************");
		System.out.print("�������: ");
		carAccident.setType(inputReader.readLine().trim());
		System.out.print("���¥: ");
		carAccident.setDate(formatter.parse(inputReader.readLine().trim()));
		System.out.print("���ð�: ");
		carAccident.setTime(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("������: ");
		carAccident.setPlace(inputReader.readLine().trim());
		System.out.print("������ȣ: ");
		carAccident.setCarNumber(inputReader.readLine().trim());
		System.out.print("������ ����: ");
		carAccident.setDriverName(inputReader.readLine().trim());
		System.out.print("�����ȣ: ");
		carAccident.setLicenseNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("��� ����: ");
		carAccident.setAccidentDetail(inputReader.readLine().trim());
		if (carAccidentList.createCarAccident(carAccident))
			System.out.println(compensationClaim.getCCID() + "��û�� �Ϸ�Ǿ����ϴ�. �ɻ� ����� ���� ������ ���ѵǰų� ������ �� �ֽ��ϴ�.");
		else
			System.out.println("��û�� �����Ͽ����ϴ�. �ٽ� �õ����ֽʽÿ�.");
	}

	private static void designInsurance(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,
			BufferedReader inputReader) throws IOException {
		String choice = "";
		while (!choice.equals("x")) {
			System.out.println("****************** Insurance DESIGN MENU *******************");
			System.out.println("1. ������ ���� ��ȸ 2. �� ���� ���� 3. ��� ���� 4. �Ǹ����� ���� ��ȸ x. ����");
			System.out.println("���� : ");
			choice = inputReader.readLine().trim();
			if (choice.equals("1"))
				showDesignedInsurance(insuranceListImpl, inputReader);
			else if (choice.equals("2"))
				createInsurance(insuranceListImpl, inputReader);
			else if (choice.equals("3"))
				termsManagement(insuranceListImpl, termsListImpl, inputReader);
			else if (choice.equals("4"))
				showOnSaleInsurance(insuranceListImpl, inputReader, "Manager");
			else if (!choice.equals("x"))
				System.out.println("Invalid Choice !!!");
		}
	}

	private static void termsManagement(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,
			BufferedReader inputReader) throws IOException {
		System.out.println("****************** Terms Management MENU *******************");
		System.out.println("1. ��� ��ȸ 2. �� ��� ���");
		System.out.println("���� : ");
		String choice = inputReader.readLine().trim();
		switch (choice) {
		case "1":
			showList(termsListImpl.retrieveAllTerms());
			break;
		case "2":
			createTerms(termsListImpl, inputReader);
			break;
		default:
			System.out.println("Invalid Choice !!!");
		}
	}

	private static void createTerms(TermsListImpl termsListImpl, BufferedReader inputReader)
			throws NumberFormatException, IOException {
		Terms terms = new Terms();
		System.out.println("--------Terms Info---------");
		System.out.println("��� ID : ");
		terms.setTermsID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("�����  : ");
		terms.setTermsName(inputReader.readLine().trim());
		System.out.println("���޺���� �������(,�� �̾ �ۼ�) : ");
		terms.setCalculatedMoneyMethod(inputReader.readLine().trim());
		System.out.println("���峻�� : ");
		terms.setTermsContent(inputReader.readLine().trim());
		if (termsListImpl.createTerms(terms))
			System.out.println("��ϵǾ����ϴ�.");
		else
			System.out.println("��ϵ��� �ʾҽ��ϴ�.");
	}

	private static void showDesignedInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		showList(insuranceListImpl.getUnregisteredInsuranceList());
		System.out.println("****************** UnregisteredInsurance MENU *******************");
		System.out.println("1. ������ ���� ����/�����ϱ� 2. �Ǹ� ������ ���� ����ϱ� 3. ������������ �ΰ� ��û x. ����");
		System.out.println("���� : ");
		String choice = inputReader.readLine().trim();
		switch (choice) {
		case "1":
			updateInsuranceDetail(insuranceListImpl, inputReader);
			break;
		case "2":
			registerInsurance(insuranceListImpl, inputReader);
			break;
		case "3":
			requestAuthorization(insuranceListImpl, inputReader);
			break;
		case "x":
			break;
		default:
			System.out.println("Invalid Choice !!!");
		}
	}

	private static void requestAuthorization(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("�ΰ��� ��û�� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		System.out.println("���� : ");
		String insuranceID = inputReader.readLine().trim();
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				String insuranceName = insuranceListImpl.requestAuthorization(Integer.valueOf(insuranceID));
				if (!insuranceName.equals(null))
					System.out.println(insuranceName + "�ΰ� ��û�� �Ϸ�Ǿ����ϴ�. �ΰ� �Ϸ���� �� ���� �ҿ�� �� �ֽ��ϴ�.");
				else
					System.out.println("��û�� �����߽��ϴ�.");
			} else
				System.out.println("�ƹ��͵� �Էµ��� �ʾҽ��ϴ�.");
		}
	}

	private static void registerInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("�Ǹ������� ����� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		System.out.println("���� : ");
		String insuranceID = inputReader.readLine().trim();
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				System.out.println("������������ ���� �ΰ����� ������ Ȯ���մϱ�?");
				System.out.println("Y/N : ");
				String choice = inputReader.readLine().trim();
				if (choice.equals("Y")) {
					if (insuranceListImpl.updateAuthorization(Integer.valueOf(insuranceID), true))
						System.out.println("�ű� ���� ����� �Ϸ�Ǿ����ϴ�.");
					else
						System.out.println("��û�� �����߽��ϴ�.");
				}
			} else
				System.out.println("�ƹ��͵� �Էµ��� �ʾҽ��ϴ�.");
		}
	}

	private static void showOnSaleInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader, String who)
			throws IOException {
		System.out.println("****************** Insurance MENU *******************");
		String insuranceType = "";
		while (!insuranceType.equals("x")) {
			System.out.println("��ȸ�Ͻ� ���� ������ �Է��ϼ���");
			System.out.println("1. ��ü, 2. �ڵ�������, 3. ����/��������, 4. �Ϻ���, 5. ȭ�纸�� x. ����");
			System.out.println("���� : ");
			insuranceType = inputReader.readLine().trim();
			if (insuranceType.equals("1"))
				showList(insuranceListImpl.getOnSaleInsuranceList());
			else if (insuranceType.equals("2"))
				showList(insuranceListImpl.retrieveInsurance("Car"));
			else if (insuranceType.equals("3"))
				showList(insuranceListImpl.retrieveInsurance("Disease"));
			else if (insuranceType.equals("4"))
				showList(insuranceListImpl.retrieveInsurance("Cancer"));
			else if (insuranceType.equals("5"))
				showList(insuranceListImpl.retrieveInsurance("Fire"));
			else if (!insuranceType.equals("x"))
				System.out.println("Invalid Choice !!!");
		}
		if (who.equals("Customer")) {
			System.out.println("���� ��û �޴��� �̵��Ͻðڽ��ϱ�? (Y/N)");
			String choice = inputReader.readLine().trim();
			if (choice.equals("Y")) {
				// ���谡�Խ�û�޼ҵ�();
			}
		} else {
			System.out.println("���� �޴��� ���ư��ϴ�.");
		}
	}

	private static void updateInsuranceDetail(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("����/������ ���輭�� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		String insuranceID = "";
		Insurance insurance;
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				System.out.println("���� : ");
				insuranceID = inputReader.readLine().trim();
				insurance = insuranceListImpl.retrieveInsuranceDetail(Integer.valueOf(insuranceID));
				System.out.println(insurance.toString());
				System.out.println("1. �����ϱ�, 2. �����ϱ�");
				String choice = inputReader.readLine().trim();
				switch (choice) {
				case "1":
					updateInsurance(insurance, insuranceListImpl, inputReader);
					break;
				case "2":
					deleteInsurance(insuranceListImpl, insurance.getInsuranceID(), inputReader);
					break;
				default:
					System.out.println("Invalid Choice !!");
				}
			}
		}
	}

	private static void createInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		Insurance insurance = new Insurance();
		System.out.println("--------Insurance Info---------");
		System.out.println("���� ID : ");
		insurance.setInsuranceID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� �̸� : ");
		insurance.setInsuranceName(inputReader.readLine().trim());
		System.out.println("���� ���� : ");
		insurance.setType(inputReader.readLine().trim());
		System.out.println("���谡�Աݾ� : ");
		insurance.setMaxCompensation(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� �Ⱓ : ");
		insurance.setPeriodOfInsurance(inputReader.readLine().trim());
		System.out.println("���� �ֱ� : ");
		insurance.setPaymentCycle(inputReader.readLine().trim());
		System.out.println("���� �Ⱓ : ");
		insurance.setPaymentPeriod(inputReader.readLine().trim());
		System.out.println("���� ���� : ");
		insurance.setAgeOfTarget(inputReader.readLine().trim());
		System.out.println("�⺻ ����� : ");
		insurance.setBasicPremium(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� : ");
		insurance.setRate(inputReader.readLine().trim());
		System.out.println("��� ����(False/True) : ");
		insurance.setDistributionStatus(Boolean.parseBoolean(inputReader.readLine().trim()));
		while (true) {
			System.out.println("���� ����(���ID, �޸��� �������ּ���) : ");
			if (insurance.setTermsIDList(inputReader.readLine().trim()) == false)
				System.out.println("���ID�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���");
			else
				break;
		}
		System.out.println("���ǻ��� : ");
		insurance.setPrecaution(inputReader.readLine().trim());
		System.out.println("���� ��å �Ⱓ : ");
		insurance.setInsuranceClausePeriod(Integer.valueOf(inputReader.readLine().trim()));
		if (insuranceListImpl.createInsurance(insurance))
			System.out.println("��ϵǾ����ϴ�.");
		else
			System.out.println("��ϵ��� �ʾҽ��ϴ�.");
	}

	private static void updateInsurance(Insurance insurance, InsuranceListImpl insuranceListImpl,
			BufferedReader inputReader) throws IOException {
		String choice = "";

		System.out.println("������ ������ �����ϼ���.");
		System.out.println(
				"1. ���� ID, 2. ���� �̸�, 3. ���� ����, 4. ���谡�Աݾ�, 5. ���� �Ⱓ, 6. ���� �ֱ�, 7. ���� �Ⱓ, 8. ���� ����, 9. �⺻ �����, 10. ����, "
						+ "11. ��� ����(False/True), 12. ���� ����(���ID, �޸��� �������ּ���), 13. ���ǻ���, 14. ���� ��å �Ⱓ");
		choice = inputReader.readLine().trim();
		System.out.println("������ ������ �Է��ϼ���");
		String content = inputReader.readLine().trim();
		switch (choice) {
		case ("1"):
			insurance.setInsuranceID(Integer.valueOf(content));
			break;
		case ("2"):
			insurance.setInsuranceName(content);
			break;
		case ("3"):
			insurance.setType(content);
			break;
		case ("4"):
			insurance.setMaxCompensation(Integer.valueOf(content));
			break;
		case ("5"):
			insurance.setPeriodOfInsurance(content);
			break;
		case ("6"):
			insurance.setPaymentCycle(content);
			break;
		case ("7"):
			insurance.setPaymentPeriod(content);
			break;
		case ("8"):
			insurance.setAgeOfTarget(content);
			break;
		case ("9"):
			insurance.setBasicPremium(Integer.valueOf(content));
			break;
		case ("10"):
			insurance.setRate(content);
			break;
		case ("11"):
			insurance.setDistributionStatus(Boolean.parseBoolean(content));
			break;
		case ("12"):
			if (insurance.setTermsIDList(content) == false)
				System.out.println("��� ID�� �������� �ʾ� ������ ������� �ʾҽ��ϴ�.");
			break;
		case ("13"):
			insurance.setPrecaution(content);
			break;
		case ("14"):
			insurance.setInsuranceClausePeriod(Integer.valueOf(content));
			break;
		default:
			System.out.println("Invalid Choice !!!");
		}

		if (insuranceListImpl.updateinsurance(insurance))
			System.out.println("������ ����Ǿ����ϴ�.");
		else
			System.out.println("������ �����߽��ϴ�.");
	}

	private static void deleteInsurance(InsuranceListImpl insuranceListImpl, String insuranceID,
			BufferedReader inputReader) throws IOException {
		System.out.println("����ID : " + insuranceID + "�� �����Ͻðڽ��ϱ�? (Y/N)");
		String choice = inputReader.readLine().trim();
		if (choice.equals("Y"))
			if (insuranceListImpl.deleteInsurance(Integer.valueOf(insuranceID)))
				System.out.println("�����Ǿ����ϴ�.");
			else
				System.out.println("������ �����Ͽ����ϴ�.");
		else if (choice.equals("N"))
			System.out.println("������ ��ҵǾ����ϴ�.");
		else
			System.out.println("Invalid Choice !!!");
	}

	private static void showList(ArrayList<?> dataList) {
		String list = "";
		for (int i = 0; i < dataList.size(); i++) {
			list += dataList.get(i) + "\n";
		}
		System.out.println(list);
	}

	private static void showMenu() {
		System.out.println("****************** Initial Menu *******************");
		System.out.println("1. ����� û��");
		System.out.println("2. ����� û�� ����(������)");
		System.out.println("3. ���� ��ȸ");
		System.out.println("4. ���� ����(������)");
		System.out.println("5. ��� ����");
		System.out.println("x. Exit");

	}

	private static void showContractList(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n\n****************** Contract MENU *******************");
		System.out.println("1. ���� ����");
		System.out.println("2. �ߵ� ����");
		System.out.println("3. ���� ���� ����");
		System.out.println("X. �޴� ����");

		System.out.println("\n--------Contract Info-------------");
		showList(contractListImpl.retrieve());

		String Choice = systemInput.readLine().trim();
		while (!Choice.equals("X")) {
			switch (Choice) {
			case "1":
				showPaymentList(new PaymentListImpl("data/Payment.txt"), systemInput);
				break;
			case "2":
				earlyTerminationInsurance(new ContractListImpl("data/Contract.txt"), systemInput);
				break;
			case "3":
				updateMaturityCancellation(new ContractListImpl("data/Contract.txt"), systemInput);
				break;
			default:
				try {
					Integer.parseInt(Choice);
				} catch (NumberFormatException e) {
					System.out.println("\n[System] ��ȿ���� ���� ���Դϴ�.");
				}

				break;
			}
		}
	}

	private static void updateMaturityCancellation(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n[System] ������ ID�� �Է����ּ���. ");
		System.out.print("�� ID : ");
		String customerID = systemInput.readLine().trim();

		if (contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] ��ϵ��� ���� ���� �����Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
			return;
		}

		System.out.println("\n\n****************** MaturityCancellation MENU *******************");
		System.out.println("1. ���� ���� ���� ��� �ȳ�");
		System.out.println("2. ���� ���� ���� �ϱ�");
		System.out.println("X. �޴� ����");

		String choice = systemInput.readLine().trim();
		switch (choice) {
		case "1":
			showMaturityPolicyMessage();
			break;
		case "2":
			cancelMaturity(contractListImpl, systemInput, customerID);
			break;
		case "X":
			showContractList(contractListImpl, systemInput);
			break;
		default:
			try {
				Integer.parseInt(choice);
			} catch (NumberFormatException e) {
				System.out.println("\n[System] ��ȿ���� ���� ���Դϴ�.");
			}
			break;
		}

	}

	private static void cancelMaturity(ContractListImpl contractListImpl, BufferedReader systemInput, String customerID)
			throws Exception {
		System.out.println("\n-------- Maturity Contract Cancellation Info-------------");

		showList(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));

		System.out.print("\n[System] ������ ��� ����Ʈ�� ��ȣ�� �Է��ϼ���. ");
		int contractIndex = Integer.parseInt(systemInput.readLine().trim());
		boolean getCustomerMaturity = contractListImpl.getCustomerMaturity(customerID, contractIndex);

		// �̹� ������ ������ ���
		if (getCustomerMaturity) {
			System.out.println("\n[System] �̹� ������ ���� �����Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		}

		boolean isMatchCustomerContract = contractListImpl.isMatchCustomerContract(contractIndex, customerID);
		if (isMatchCustomerContract) {
			boolean isUpdated = contractListImpl.updateMaturity(contractIndex);
			if (isUpdated) {
				System.out.println("\n[System] ���� ����� �����Ͻʴϱ�? (Y/N) ");
				String agree = systemInput.readLine().trim();
				if (agree.equals("N")) {
					System.out.println("\n[System] ���� ����� �������� ���� ��� ������ �Ұ����մϴ�.");
					showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
				} else {
					System.out.println("\n[System] ������ �Ϸ�Ǿ����ϴ�.");
					// ���� ȯ�ޱ� ��û ȭ�� ����
					System.out.println("\n-------- [�� " + customerID + "] ���� ����" + "-------------");
					System.out.println(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));
					showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��

				}
				return;
			} else {
				System.out.println("\n[System] �߸��� �����Դϴ�.");
				showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
			}
		} else {
			System.out.println("\n[System] �߸��� ��� ����Ʈ ��ȣ�Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		}
		showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		return;

	}

	private static void showMaturityPolicyMessage() {
		// TODO Auto-generated method stub

	}

	private static void earlyTerminationInsurance(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n[System] ������ ID�� �Է����ּ���. ");
		System.out.print("�� ID : ");
		String customerID = systemInput.readLine().trim();

		if (contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] ��ϵ��� ���� ���� �����Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
			return;
		}

		System.out.println("\n\n****************** earlyTermination MENU *******************");
		System.out.println("1. �ߵ� ���� ��� �ȳ�");
		System.out.println("2. �ߵ� ���� �ϱ�");
		System.out.println("X. �޴� ����");

		String choice = systemInput.readLine().trim();
		switch (choice) {
		case "1":
			showEarlyTerminationPolicyMessage();
			break;
		case "2":
			cancelContract(contractListImpl, systemInput, customerID);
			break;
		case "X":
			showContractList(contractListImpl, systemInput);
			break;
		default:
			try {
				Integer.parseInt(choice);
			} catch (NumberFormatException e) {
				System.out.println("\n[System] ��ȿ���� ���� ���Դϴ�.");
			}
			break;
		}

	}

	private static void showEarlyTerminationPolicyMessage() {
		// TODO Auto-generated method stub

	}

	private static void cancelContract(ContractListImpl contractListImpl, BufferedReader systemInput, String customerID)
			throws Exception {
		System.out.println("\n-------- Contract Cancellation Info-------------");

		showList(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));

		System.out.print("\n[System] ������ ��� ����Ʈ�� ��ȣ�� �Է��ϼ���. ");
		int contractIndex = Integer.parseInt(systemInput.readLine().trim());
		boolean getCustomerCancellation = contractListImpl.getCustomerCancellation(customerID, contractIndex);
		// �̹� ������ ������ ���
		if (getCustomerCancellation) {
			System.out.println("\n[System] �̹� �ߵ� ������ �����Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		}
		boolean isMatchCustomerContract = contractListImpl.isMatchCustomerContract(contractIndex, customerID);
		if (isMatchCustomerContract) {
			boolean isUpdated = contractListImpl.updateCancellation(contractIndex);
			if (isUpdated) {
				System.out.println("\n[System] ���� ����� �����Ͻʴϱ�? (Y/N) ");
				String agree = systemInput.readLine().trim();
				if (agree.equals("N")) {
					System.out.println("\n[System] ���� ����� �������� ���� ��� ������ �Ұ����մϴ�.");
					showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
				} else {
					System.out.println("\n[System] ������ �Ϸ�Ǿ����ϴ�.");
					// ���� ȯ�ޱ� ��û ȭ�� ����
					System.out.println("\n-------- [�� " + customerID + "] ���� ����" + "-------------");
					System.out.println(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));
					showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��

				}
				return;
			} else {
				System.out.println("\n[System] �߸��� �����Դϴ�.");
				showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
			}
		} else {
			System.out.println("\n[System] �߸��� ��� ����Ʈ ��ȣ�Դϴ�.");
			showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		}
		showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
		return;

	}

	private static String showPaymentList(PaymentListImpl paymentListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n\n****************** Payment MENU *******************");
		System.out.println("1. ���� ���� ����");
		System.out.println("X. �޴� ����");

		System.out.println("\n--------Payment Info-------------");
		showList(paymentListImpl.retrieve());

		String Choice = systemInput.readLine().trim();
		while (!Choice.equals("X")) {
			switch (Choice) {
			case "1":
				updatePayment(paymentListImpl, systemInput);
				break;
			default:
				try {
					Integer.parseInt(Choice);
				} catch (NumberFormatException e) {
					System.out.println("\n[System] ��ȿ���� ���� ���Դϴ�.");
				}
				break;
			}
			Choice = systemInput.readLine().trim();
		}
		return Choice; // showPaymentList���� ��ȯ���� ���
	}

	private static void updatePayment(PaymentListImpl paymentListImpl, BufferedReader systemInput) throws Exception {
		// Customer ���� �� �̸����� ����
		System.out.println("\n[System] ��� ���� ������ Ȯ���� �� ID�� �Է��ϼ���.");
		System.out.print("�� ID : ");
		String customerID = systemInput.readLine().trim();

		// ȭ�� �����δ� list ����, TUI�δ� ��ID�� ����ID�� �ߺ��Ǵ� ��찡 �־� �̸� ���� match �Ұ����Ͽ� index�� ���Ƿ�
		// match��
		if (paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] ��ϵ��� ���� ���� �����Դϴ�.");
			showPaymentList(paymentListImpl, systemInput); // showPaymentList �޼��� ȣ��
			return;
		}
		System.out.println("\n-------- [�� " + customerID + "] ���� ����" + "-------------");
		System.out.println(paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)));
		System.out.println("\n[System] ������ ���� ����Ʈ�� ��ȣ�� �Է��ϼ���.");
		System.out.print("���� ����Ʈ ��ȣ : ");
		int paymentID = Integer.parseInt(systemInput.readLine().trim());
		boolean isMatchCustomerPayment = paymentListImpl.isMatchCustomerPayment(paymentID, customerID);
		if (isMatchCustomerPayment) {
			boolean isUpdated = paymentListImpl.updateWheterPayment(paymentID);
			if (isUpdated) {
				System.out.println("\n[System] ���� ���¸� �����߽��ϴ�.");
				System.out.println("\n-------- [�� " + customerID + "] ���� ����" + "-------------");
				System.out.println(paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)));
				showPaymentList(paymentListImpl, systemInput); // showPaymentList �޼��� ȣ��
				return;
			} else {
				System.out.println("\n[System] �߸��� �����Դϴ�.");
				showPaymentList(paymentListImpl, systemInput); // showPaymentList �޼��� ȣ��
			}
		} else {
			System.out.println("\n[System] �߸��� ���� ����Ʈ ��ȣ�Դϴ�.");
			showPaymentList(paymentListImpl, systemInput); // showPaymentList �޼��� ȣ��
		}
		showPaymentList(paymentListImpl, systemInput); // showPaymentList �޼��� ȣ��
		return;

	}
}