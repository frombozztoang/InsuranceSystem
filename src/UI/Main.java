package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

import Contract.ContractListImpl;
import Contract.PaymentListImpl;
import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.Terms;
import Insurance.TermsListImpl;

public class Main {

	public static void main(String[] args) throws Exception {

		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		InsuranceListImpl insuranceListImpl = new InsuranceListImpl("Insurance.txt");
		TermsListImpl termsListImpl = new TermsListImpl("Terms.txt");
		ContractListImpl contractListImpl = new ContractListImpl("Contract.txt");
		while (!userChoice.equals("x")) {
			showMenu();
			userChoice = inputReader.readLine().trim();
			switch (userChoice) {
			case "1":
				showOnSaleInsurance(insuranceListImpl, inputReader, "Customer");
				break;
			case "2":
				designInsurance(insuranceListImpl, termsListImpl, inputReader);
				break;
			case "3":
				showContractList(contractListImpl, inputReader);
				break;
			default:
				System.out.println("Invalid Choice !!!");
			}
		}
	}

	private static void designInsurance(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,
			BufferedReader inputReader) throws IOException {
		String choice = "";
		while (!choice.equals("x")) {
			System.out.println("****************** Insurance DESIGN MENU *******************");
			System.out.println("1. 설계한 보험 조회 2. 새 보험 설계 3. 약관 관리 4. 판매중인 보험 조회 x. 종료");
			System.out.println("선택 : ");
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
		System.out.println("1. 약관 조회 2. 새 약관 등록");
		System.out.println("선택 : ");
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
		System.out.println("약관 ID : ");
		terms.setTermsID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("약관명  : ");
		terms.setTermsName(inputReader.readLine().trim());
		System.out.println("지급보험금 산정방식(,로 이어서 작성) : ");
		terms.setCalculatedMoneyMethod(inputReader.readLine().trim());
		System.out.println("보장내용 : ");
		terms.setTermsContent(inputReader.readLine().trim());
		if (termsListImpl.createTerms(terms))
			System.out.println("등록되었습니다.");
		else
			System.out.println("등록되지 않았습니다.");
	}

	private static void showDesignedInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		showList(insuranceListImpl.getUnregisteredInsuranceList());
		System.out.println("****************** UnregisteredInsurance MENU *******************");
		System.out.println("1. 선택한 보험 수정/삭제하기 2. 판매 중으로 보험 등록하기 3. 금융감독원에 인가 요청 x. 종료");
		System.out.println("선택 : ");
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
		System.out.println("인가를 요청할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		System.out.println("선택 : ");
		String insuranceID = inputReader.readLine().trim();
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				String insuranceName = insuranceListImpl.requestAuthorization(Integer.valueOf(insuranceID));
				if (!insuranceName.equals(null))
					System.out.println(insuranceName + "인가 신청이 완료되었습니다. 인가 완료까지 수 일이 소요될 수 있습니다.");
				else
					System.out.println("신청에 실패했습니다.");
			} else
				System.out.println("아무것도 입력되지 않았습니다.");
		}
	}

	private static void registerInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("판매중으로 등록할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		System.out.println("선택 : ");
		String insuranceID = inputReader.readLine().trim();
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				System.out.println("금융감독원에 의해 인가받은 보험이 확실합니까?");
				System.out.println("Y/N : ");
				String choice = inputReader.readLine().trim();
				if (choice.equals("Y")) {
					if (insuranceListImpl.updateAuthorization(Integer.valueOf(insuranceID), true))
						System.out.println("신규 보험 등록이 완료되었습니다.");
					else
						System.out.println("신청에 실패했습니다.");
				}
			} else
				System.out.println("아무것도 입력되지 않았습니다.");
		}
	}

	private static void showOnSaleInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader, String who)
			throws IOException {
		System.out.println("****************** Insurance MENU *******************");
		String insuranceType = "";
		while (!insuranceType.equals("x")) {
			System.out.println("조회하실 보험 종류를 입력하세요");
			System.out.println("1. 전체, 2. 자동차보험, 3. 상해/질병보험, 4. 암보험, 5. 화재보험 x. 종료");
			System.out.println("선택 : ");
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
			System.out.println("보험 신청 메뉴로 이동하시겠습니까? (Y/N)");
			String choice = inputReader.readLine().trim();
			if (choice.equals("Y")) {
				// 보험가입신청메소드();
			}
		} else {
			System.out.println("설계 메뉴로 돌아갑니다.");
		}
	}

	private static void updateInsuranceDetail(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("수정/삭제할 설계서의 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		String insuranceID = "";
		Insurance insurance;
		if (!insuranceID.equals("x")) {
			if (!insuranceID.equals(null)) {
				System.out.println("선택 : ");
				insuranceID = inputReader.readLine().trim();
				insurance = insuranceListImpl.retrieveInsuranceDetail(Integer.valueOf(insuranceID));
				System.out.println(insurance.toString());
				System.out.println("1. 수정하기, 2. 삭제하기");
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
		System.out.println("보험 ID : ");
		insurance.setInsuranceID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("보험 이름 : ");
		insurance.setInsuranceName(inputReader.readLine().trim());
		System.out.println("보험 종류 : ");
		insurance.setType(inputReader.readLine().trim());
		System.out.println("보험가입금액 : ");
		insurance.setMaxCompensation(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("보험 기간 : ");
		insurance.setPeriodOfInsurance(inputReader.readLine().trim());
		System.out.println("납입 주기 : ");
		insurance.setPaymentCycle(inputReader.readLine().trim());
		System.out.println("납입 기간 : ");
		insurance.setPaymentPeriod(inputReader.readLine().trim());
		System.out.println("가입 나이 : ");
		insurance.setAgeOfTarget(inputReader.readLine().trim());
		System.out.println("기본 보험료 : ");
		insurance.setBasicPremium(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("요율 : ");
		insurance.setRate(inputReader.readLine().trim());
		System.out.println("배당 여부(False/True) : ");
		insurance.setDistributionStatus(Boolean.parseBoolean(inputReader.readLine().trim()));
		while (true) {
			System.out.println("보장 내용(약관ID, 콤마로 구분해주세요) : ");
			if (insurance.setTermsIDList(inputReader.readLine().trim()) == false)
				System.out.println("약관ID가 존재하지 않습니다. 다시 입력해주세요");
			else
				break;
		}
		System.out.println("주의사항 : ");
		insurance.setPrecaution(inputReader.readLine().trim());
		System.out.println("보험 면책 기간 : ");
		insurance.setInsuranceClausePeriod(Integer.valueOf(inputReader.readLine().trim()));
		if (insuranceListImpl.createInsurance(insurance))
			System.out.println("등록되었습니다.");
		else
			System.out.println("등록되지 않았습니다.");
	}

	private static void updateInsurance(Insurance insurance, InsuranceListImpl insuranceListImpl,
			BufferedReader inputReader) throws IOException {
		String choice = "";

		System.out.println("수정할 정보를 선택하세요.");
		System.out.println(
				"1. 보험 ID, 2. 보험 이름, 3. 보험 종류, 4. 보험가입금액, 5. 보험 기간, 6. 납입 주기, 7. 납입 기간, 8. 가입 나이, 9. 기본 보험료, 10. 요율, "
						+ "11. 배당 여부(False/True), 12. 보장 내용(약관ID, 콤마로 구분해주세요), 13. 주의사항, 14. 보험 면책 기간");
		choice = inputReader.readLine().trim();
		System.out.println("수정할 내용을 입력하세요");
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
				System.out.println("약관 ID가 존재하지 않아 정보가 저장되지 않았습니다.");
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
			System.out.println("수정이 저장되었습니다.");
		else
			System.out.println("수정에 실패했습니다.");
	}

	private static void deleteInsurance(InsuranceListImpl insuranceListImpl, String insuranceID,
			BufferedReader inputReader) throws IOException {
		System.out.println("보험ID : " + insuranceID + "를 삭제하시겠습니까? (Y/N)");
		String choice = inputReader.readLine().trim();
		if (choice.equals("Y"))
			if (insuranceListImpl.deleteInsurance(Integer.valueOf(insuranceID)))
				System.out.println("삭제되었습니다.");
			else
				System.out.println("삭제에 실패하였습니다.");
		else if (choice.equals("N"))
			System.out.println("삭제가 취소되었습니다.");
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

	private static void showContractList(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n\n****************** Contract MENU *******************");
		System.out.println("1. 납입 내역");
		System.out.println("2. 중도 해지");
		System.out.println("3. 만기 보험 해지");
		System.out.println("X. 메뉴 보기");

		System.out.println("\n--------Contract Info-------------");
		showList(contractListImpl.retrieve());

		String Choice = systemInput.readLine().trim();
		while (!Choice.equals("X")) {
			switch (Choice) {
			case "1":
				showPaymentList(new PaymentListImpl("Payment.txt"), systemInput);
				break;
			case "2":
				earlyTerminationInsurance(new ContractListImpl("Contract.txt"), systemInput);
				break;
			case "3":
				updateMaturityCancellation(new ContractListImpl("Contract.txt"), systemInput);
				break;
			default:
				try {
					Integer.parseInt(Choice);
				} catch (NumberFormatException e) {
					System.out.println("\n[System] 유효하지 않은 값입니다.");
				}

				break;
			}
		}
	}

	private static void updateMaturityCancellation(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n[System] 고객님의 ID를 입력해주세요. ");
		System.out.print("고객 ID : ");
		String customerID = systemInput.readLine().trim();

		if (contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] 등록되지 않은 고객의 정보입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
			return;
		}

		System.out.println("\n\n****************** MaturityCancellation MENU *******************");
		System.out.println("1. 만기 보험 해지 약관 안내");
		System.out.println("2. 만기 보험 해지 하기");
		System.out.println("X. 메뉴 보기");

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
				System.out.println("\n[System] 유효하지 않은 값입니다.");
			}
			break;
		}

	}

	private static void cancelMaturity(ContractListImpl contractListImpl, BufferedReader systemInput, String customerID)
			throws Exception {
		System.out.println("\n-------- Maturity Contract Cancellation Info-------------");

		showList(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));

		System.out.print("\n[System] 해지할 계약 리스트의 번호를 입력하세요. ");
		int contractIndex = Integer.parseInt(systemInput.readLine().trim());
		boolean getCustomerMaturity = contractListImpl.getCustomerMaturity(customerID, contractIndex);
		
		// 이미 해지된 보험인 경우, 만기 보험인지의 로직도 추가 필요
		if(getCustomerMaturity) {
			System.out.println("\n[System] 이미 해지된 만기 보헙입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		}
		
		boolean isMatchCustomerContract = contractListImpl.isMatchCustomerContract(contractIndex, customerID);
		if (isMatchCustomerContract) {
			boolean isUpdated = contractListImpl.updateMaturity(contractIndex);
			if (isUpdated) {
				System.out.println("\n[System] 해지 약관에 동의하십니까? (Y/N) ");
				String agree = systemInput.readLine().trim();
				if (agree.equals("N")) {
					System.out.println("\n[System] 해지 약관에 동의하지 않을 경우 해지가 불가능합니다.");
					showContractList(contractListImpl, systemInput); // showContract 메서드 호출
				} else {
					System.out.println("\n[System] 해지가 완료되었습니다.");
					// 해지 환급금 요청 화면 연결
					System.out.println("\n-------- [고객 " + customerID + "] 납입 내역" + "-------------");
					System.out.println(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));
					showContractList(contractListImpl, systemInput); // showContract 메서드 호출

				}
				return;
			} else {
				System.out.println("\n[System] 잘못된 정보입니다.");
				showContractList(contractListImpl, systemInput); // showContract 메서드 호출
			}
		} else {
			System.out.println("\n[System] 잘못된 계약 리스트 번호입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		}
		showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		return;

	}

	private static void showMaturityPolicyMessage() {
		// TODO Auto-generated method stub

	}

	private static void earlyTerminationInsurance(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n[System] 고객님의 ID를 입력해주세요. ");
		System.out.print("고객 ID : ");
		String customerID = systemInput.readLine().trim();

		if (contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] 등록되지 않은 고객의 정보입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
			return;
		}

		System.out.println("\n\n****************** earlyTermination MENU *******************");
		System.out.println("1. 중도 해지 약관 안내");
		System.out.println("2. 중도 해지 하기");
		System.out.println("X. 메뉴 보기");

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
				System.out.println("\n[System] 유효하지 않은 값입니다.");
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

		System.out.print("\n[System] 해지할 계약 리스트의 번호를 입력하세요. ");
		int contractIndex = Integer.parseInt(systemInput.readLine().trim());
		boolean isMatchCustomerContract = contractListImpl.isMatchCustomerContract(contractIndex, customerID);
		if (isMatchCustomerContract) {
			boolean isUpdated = contractListImpl.updateCancellation(contractIndex);
			if (isUpdated) {
				System.out.println("\n[System] 해지 약관에 동의하십니까? (Y/N) ");
				String agree = systemInput.readLine().trim();
				if (agree.equals("N")) {
					System.out.println("\n[System] 해지 약관에 동의하지 않을 경우 해지가 불가능합니다.");
					showContractList(contractListImpl, systemInput); // showContract 메서드 호출
				} else {
					System.out.println("\n[System] 해지가 완료되었습니다.");
					// 해지 환급금 요청 화면 연결
					System.out.println("\n-------- [고객 " + customerID + "] 납입 내역" + "-------------");
					System.out.println(contractListImpl.retreiveCustomerContract(Integer.parseInt(customerID)));
					showContractList(contractListImpl, systemInput); // showContract 메서드 호출

				}
				return;
			} else {
				System.out.println("\n[System] 잘못된 정보입니다.");
				showContractList(contractListImpl, systemInput); // showContract 메서드 호출
			}
		} else {
			System.out.println("\n[System] 잘못된 계약 리스트 번호입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		}
		showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		return;

	}

	private static String showPaymentList(PaymentListImpl paymentListImpl, BufferedReader systemInput)
			throws Exception {
		System.out.println("\n\n****************** Payment MENU *******************");
		System.out.println("1. 납입 상태 변경");
		System.out.println("X. 메뉴 보기");

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
					System.out.println("\n[System] 유효하지 않은 값입니다.");
				}
				break;
			}
			Choice = systemInput.readLine().trim();
		}
		return Choice; // showPaymentList에서 반환값을 사용
	}

	private static void updatePayment(PaymentListImpl paymentListImpl, BufferedReader systemInput) throws Exception {
		// Customer 연동 후 이름으로 수정
		System.out.println("\n[System] 계약 납입 내역을 확인할 고객 ID를 입력하세요.");
		System.out.print("고객 ID : ");
		String customerID = systemInput.readLine().trim();

		// 화면 상으로는 list 선택, TUI로는 고객ID와 보험ID가 중복되는 경우가 있어 이를 통해 match 불가능하여 index로 임의로
		// match함
		if (paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)).isEmpty()) {
			System.out.println("\n[System] 등록되지 않은 고객의 정보입니다.");
			showPaymentList(paymentListImpl, systemInput); // showPaymentList 메서드 호출
			return;
		}
		System.out.println("\n-------- [고객 " + customerID + "] 납입 내역" + "-------------");
		System.out.println(paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)));
		System.out.println("\n[System] 변경할 납입 리스트의 번호를 입력하세요.");
		System.out.print("납입 리스트 번호 : ");
		int paymentID = Integer.parseInt(systemInput.readLine().trim());
		boolean isMatchCustomerPayment = paymentListImpl.isMatchCustomerPayment(paymentID, customerID);
		if (isMatchCustomerPayment) {
			boolean isUpdated = paymentListImpl.updateWheterPayment(paymentID);
			if (isUpdated) {
				System.out.println("\n[System] 납입 상태를 변경했습니다.");
				System.out.println("\n-------- [고객 " + customerID + "] 납입 내역" + "-------------");
				System.out.println(paymentListImpl.retreiveCustomerPayment(Integer.parseInt(customerID)));
				showPaymentList(paymentListImpl, systemInput); // showPaymentList 메서드 호출
				return;
			} else {
				System.out.println("\n[System] 잘못된 정보입니다.");
				showPaymentList(paymentListImpl, systemInput); // showPaymentList 메서드 호출
			}
		} else {
			System.out.println("\n[System] 잘못된 납입 리스트 번호입니다.");
			showPaymentList(paymentListImpl, systemInput); // showPaymentList 메서드 호출
		}
		showPaymentList(paymentListImpl, systemInput); // showPaymentList 메서드 호출
		return;

	}

	private static void showMenu() {
		System.out.println("****************** MENU *******************");
		System.out.println("1. 보험 조회");
		System.out.println("2. 보험 설계");
		System.out.println("3. 계약 관리");
	}
}
