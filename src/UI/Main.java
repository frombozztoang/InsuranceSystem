package UI;

import CompensationClaim.*;
import Contract.Contract;
import Contract.ContractListImpl;
import Contract.Payment;
import Contract.PaymentListImpl;
import Counsel.Counsel;
import Counsel.CounselApplication;
import Counsel.CounselApplicationListImpl;
import Customer.Customer;
import Customer.Customer.EGender;
import Customer.CustomerListImpl;
import Customer.CustomerListImpl.TargetType;
import Customer.FamilyHistory;
import Customer.FamilyHistoryListImpl;
import Dao.InsuranceDao;
import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.Terms;
import Insurance.TermsListImpl;
import Insurance.Guarantee;
import Insurance.GuaranteeListImpl;
import Insurance.InsuranceApplication;
import Insurance.InsuranceApplicationListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		CompensationClaimListImpl compensationClaimList = new CompensationClaimListImpl("data/CompensationClaim.txt");
		SurveyListImpl surveyList = new SurveyListImpl("data/Survey.txt");
		CarAccidentListImpl carAccidentList = new CarAccidentListImpl("data/CarAccident.txt");

		InsuranceListImpl insuranceList = new InsuranceListImpl();
		GuaranteeListImpl guaranteeList = new GuaranteeListImpl();
		TermsListImpl termsListImpl = new TermsListImpl();
		InsuranceApplicationListImpl insuranceApplicationList = new InsuranceApplicationListImpl("data/InsuranceApplication.txt");

		ContractListImpl contractListImpl = new ContractListImpl("data/Contract.txt");
		CounselApplicationListImpl counselApplicationListImpl = new CounselApplicationListImpl("data/CounselList.txt");
		CustomerListImpl customerListImpl = new CustomerListImpl("data/Customer.txt");
		FamilyHistoryListImpl familyHistoryListImpl = new FamilyHistoryListImpl("data/FamilyHistory.txt");
		PaymentListImpl paymentListImpl = new PaymentListImpl("data/Payment.txt");
		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		while (!userChoice.equals("x")) {
			showMenu();
			userChoice = inputReader.readLine().trim();
			switch (userChoice) {
			case "1":
				createCompensationClaim(insuranceList, compensationClaimList, carAccidentList, contractListImpl,
						customerListImpl, inputReader);
				break;
			case "2":
				retrieveCompensationClaim(insuranceList, compensationClaimList, surveyList, inputReader);
				break;
			case "3":
				showOnSaleInsurance(insuranceList, insuranceApplicationList, customerListImpl, familyHistoryListImpl,
						guaranteeList, termsListImpl, inputReader, "Customer");
				break;
			case "4":
				designInsurance(insuranceList, termsListImpl, customerListImpl, familyHistoryListImpl, guaranteeList,
						inputReader, insuranceApplicationList);
				break;
			case "5":
				showCustomerList(customerListImpl, inputReader, familyHistoryListImpl, contractListImpl, insuranceList,
						paymentListImpl, compensationClaimList);
				break;
			case "6":
				showCouncel(inputReader, counselApplicationListImpl);
				break;
			case "7":
				showManageConsultation(inputReader, counselApplicationListImpl, customerListImpl);
				break;
			case "8":
				showInsuranceApplicationList(contractListImpl, insuranceApplicationList, insuranceList,
						customerListImpl, familyHistoryListImpl, inputReader);
				break;
			case "9":
				showSubscriptionInsurance(inputReader, contractListImpl, customerListImpl, insuranceList);
				break;
			case "10":
				// 추후 수정 - 계약유지대상자 조회 하면에서 '만기 계약자 조회 메뉴' 클릭 시 이동하는 곳
				showPaymentManagement(inputReader, customerListImpl, contractListImpl, paymentListImpl, insuranceList);
				break;
			case "11":
				// 보함료 조회/납부
				showInsurancePayment(inputReader, customerListImpl, contractListImpl, paymentListImpl, insuranceList);
				break;
			case "x":
				break;

			default:
				System.out.println("잘못된 선택지입니다.");

			}
		}
	}

	private static void showInsuranceApplicationList(ContractListImpl contractList,
			InsuranceApplicationListImpl insuranceApplicationList, InsuranceListImpl insuranceList,
			CustomerListImpl customerListImpl, FamilyHistoryListImpl familyHistoryList, BufferedReader inputReader)
			throws IOException {
		System.out.println("****************** 보험 가입 신청 내역 *******************");
		System.out.println("신청ID 보험ID 고객ID 신청일자");
		for (InsuranceApplication insuranceApplication : insuranceApplicationList.retrieve()) {
			System.out.println("신청ID: " + insuranceApplication.getApplicationID() + "  보험ID: "
					+ insuranceApplication.getInsuranceID() + "  고객ID: " + insuranceApplication.getCustomerID()
					+ "  신청일자: " + insuranceApplication.getCreatedAt());
		}
		System.out.println("1. 상세보기");
		System.out.println("2. 청약서 조회");
		System.out.println("3. 보험료 산정");
		String userChoice = "";
		userChoice = inputReader.readLine().trim();
		InsuranceApplication insuranceApplication;
		Insurance insurance;
		switch (userChoice) {
		case "1":
			System.out.println("상세내용을 조회할 신청건의 신청ID를 입력하세요");
			insuranceApplication = insuranceApplicationList.getApplicationbyId(inputReader.readLine().trim());
			insurance = insuranceList.getInsurancebyId(insuranceApplication.getInsuranceID());
			Customer customer = customerListImpl.getCustomerByID(insuranceApplication.getCustomerID());
			System.out.println("보험종류: " + insurance.getType() + "  보험명: " + insurance.getInsuranceName() + "  보험명:"
					+ insuranceApplication.getCreatedAt() + "\n이름: " + customer.getCustomerName() + "  생년월일: "
					+ customer.getBirth() + "  성별: " + customer.getEGender() + "  주소: " + customer.getAddress()
					+ "  전화번호: " + customer.getPnumber() + "  직업: " + customer.getJob() + "\n가족력");
			for (FamilyHistory familyHistory : familyHistoryList.getFamilyHistoryByCID(customer.getCustomerID())) {
				System.out.println(
						"가족관계: " + familyHistory.getRelationship() + "  질환명: " + familyHistory.getDiseaseName());
			}
			System.out.println("보험기간: " + insuranceApplication.getInsurancePeriod() + "  납입주기: "
					+ insuranceApplication.getPaymentCycle());
			break;
		case "2":
			System.out.println("청약서를 조회할 신청건의 신청ID를 입력하세요");
			System.out.println(insuranceApplicationList.getApplicationbyId(inputReader.readLine().trim())
					.getSubscriptionFilePath());
			break;
		case "3":
			System.out.println("보험료를 산정할 신청건의 신청ID를 입력하세요");
			insuranceApplication = insuranceApplicationList.getApplicationbyId(inputReader.readLine().trim());
			insurance = insuranceList.getInsurancebyId(insuranceApplication.getInsuranceID());
			Customer customer1 = customerListImpl.getCustomerByID(insuranceApplication.getCustomerID());
			ratePremium(insuranceApplicationList, contractList, insuranceApplication, insurance, customer1,
					familyHistoryList, inputReader);
		}
	}

	private static void ratePremium(InsuranceApplicationListImpl insuranceApplicationList,
			ContractListImpl contractList, InsuranceApplication insuranceApplication, Insurance insurance,
			Customer customer, FamilyHistoryListImpl familyHistoryList, BufferedReader inputReader) throws IOException {
		System.out.println(insurance.getType() + " " + insurance.getInsuranceName() + " " + insurance.getBasicPremium()
				+ " " + insurance.getRate());
		System.out.println(customer.getCustomerName() + " " + customer.getBirth() + " " + customer.getEGender() + " "
				+ customer.getAddress() + " " + customer.getPnumber() + " " + customer.getJob());
		for (FamilyHistory familyHistory : familyHistoryList.getFamilyHistoryByCID(customer.getCustomerID())) {
			System.out.println("가족관계: " + familyHistory.getRelationship() + "  질환명: " + familyHistory.getDiseaseName());
		}
		System.out.println("********보험료 산정**********");
		System.out.print("보험료 산정 이유: ");
		insuranceApplication.setReasonOfApproval(inputReader.readLine().trim());
		System.out.print("최대보장한도: ");
		insuranceApplication.setMaxCompensation(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("산정보험료: ");
		insuranceApplication.setPremium(Integer.parseInt(inputReader.readLine().trim()));
		System.out.println(insuranceApplication.getReasonOfApproval() + "\n위 내용에 따라, 고객의 보험료가 "
				+ insuranceApplication.getPremium() + "원으로 산정되었습니다.");
		System.out.println("최대보장한도는 " + insuranceApplication.getMaxCompensation() + "원입니다.");
		approveInsuranceApplication(insuranceApplicationList, contractList, insuranceApplication, insurance, customer,
				inputReader);
	}

	private static void approveInsuranceApplication(InsuranceApplicationListImpl insuranceApplicationList,
			ContractListImpl contractList, InsuranceApplication insuranceApplication, Insurance insurance,
			Customer customer, BufferedReader inputReader) throws IOException {
		System.out.println(
				insuranceApplication.getApplicationID() + " " + insurance.getType() + " " + insurance.getInsuranceName()
						+ " " + customer.getCustomerName() + " " + insuranceApplication.getReasonOfApproval() + " "
						+ insuranceApplication.getMaxCompensation() + " " + insuranceApplication.getPremium());
		System.out.println("1. 승인\n2. 거절");
		if (inputReader.readLine().trim().equals("1")) {
			insuranceApplication.setApproval(true);
			Contract contract = new Contract();
			contract.setCustomerID(customer.getCustomerID());
			contract.setInsuranceID(insurance.getInsuranceID());
			contract.setInsurancePeriod(insurance.getPeriodOfInsurance());
			contract.setPremium(insuranceApplication.getPremium());
			contract.setPaymentCycle(insuranceApplication.getPaymentCycle());
			contract.setMaxCompensation(insuranceApplication.getMaxCompensation());
			contract.setDateOfSubscription(insuranceApplication.getCreatedAt());
			contract.setDateOfMaturity(LocalDate.parse("2099-12-31"));
			contract.setMaturity(false);
			contract.setResurrection(false);
			contract.setCancellation(false);
			contractList.add(contract);
			System.out.println(insuranceApplication.getApplicationID() + " 보험 가입이 승인되었습니다.");
		} else {
			System.out.println("거절 사유: ");
			insuranceApplication.setReasonOfApproval(inputReader.readLine().trim());
			System.out.println(insuranceApplication.getReasonOfApproval() + "\n위 사유로 인해 "
					+ insuranceApplication.getApplicationID() + " 보험 가입이 거절되었습니다.");
		}
		insuranceApplicationList.updateInsuranceApplication(insuranceApplication);
	}

	private static void showManageConsultation(BufferedReader inputReader,
			CounselApplicationListImpl counselApplicationListImpl, CustomerListImpl customerListImpl)
			throws IOException, ParseException {
		if (selectConsultationCase(inputReader)) { // 상담 정보 조회
			String id = null;
			do {
				System.out.print("고객 아이디 : ");
				id = inputReader.readLine().trim();
			} while (!checkInputId(id));

			System.out.print("날짜 : "); // yyyymmdd
			String dateStr = inputReader.readLine().trim();
			int year = Integer.parseInt(dateStr.substring(0, 4));
			int month = Integer.parseInt(dateStr.substring(4, 6));
			int day = Integer.parseInt(dateStr.substring(6, 8));
			LocalDate date = LocalDate.of(year, month, day);
			CounselApplication counselApplication = counselApplicationListImpl.getConsuleInfo(id, date,
					counselApplicationListImpl);

			if (counselApplication == null) {
				System.out.println("일치하는 data가 하나도 없습니다.");
				return;
			}
			Customer customer = customerListImpl.getCustomerFromCouncels(counselApplication, customerListImpl);
			showCounselSchedule(counselApplication, customer);
			if (!getCustomerDetails(inputReader))
				return;
			if (counselApplication.getCounsel().getContent() == null) {
				System.out.println("상담을 아직 진행하지 않아 내용이 없습니다.");
				return;
			}
			showDetailcounselInfo(counselApplication, customer);

			if (selectRetOrDel(inputReader)) // 내용 조회
				showContentInfo(customer, counselApplication.getCounsel());
			else { // 내용 삭제
				counselApplicationListImpl.delete(counselApplication.getCounselID());
				System.out.println("삭제되었습니다");
			}
		} else { // 상담 정보 등록
			if (selectScheduleOrContent(inputReader)) {
				System.out.println("\n[상담 일정 등록]");
				CounselApplication newCounsel = getNewCounsel(inputReader);
				if (counselApplicationListImpl.add(newCounsel))
					System.out.println("상담 일정을 등록하였습니다.");
				else
					System.out.println("등록에 실패했습니다.");
			} else {
				System.out.println("\n상담 내용 등록");
				String input = null;
				boolean isInputed = false;
				do {
					input = inputCustomerId(inputReader);
					isInputed = input.length() != 0;
					if (!isInputed)
						System.out.println("조건을 최소 하나라도 기입했는지 체크해주세요.");
				} while (!isInputed);
				Customer customer = customerListImpl.retrieveCustomer(input);
				if (customer == null) {
					System.out.println("관련 상담 일정이 하나도 존재하지 않습니다.");
					return;
				}
				List<CounselApplication> selectedCouncels = CounselApplicationListImpl.getCounselList(customer,
						counselApplicationListImpl);
				showCounseList(customer, selectedCouncels);
				if (!selectContentAcquire(inputReader))
					return;
				String content = inputContent(inputReader); // 상담내용
				CounselApplication councelApp = null;
				if (selectedCouncels.size() == 1) // Basic
					councelApp = selectedCouncels.get(0);
				else if (selectedCouncels.size() > 2) // A3.
					councelApp = selectFromCouncels(inputReader, selectedCouncels);
				Counsel councel = councelApp.getCounsel();
				councel.setContent(content);
				System.out.println("상담 내용을 등록하였습니다.");
			}
		}
	}

	private static CounselApplication selectFromCouncels(BufferedReader inputReader,
			List<CounselApplication> selectedCouncels) throws IOException {
		System.out.println("내용을 작성할 상담을 선택해주세요");
		System.out.print("상담 ID : ");
		String id = inputReader.readLine().trim();
		for (CounselApplication counselApp : selectedCouncels) {
			if (counselApp.getCounselID().equals(id))
				return counselApp;
		}
		return null;
	}

	private static void showContentInfo(Customer customer, Counsel counsel) {
		System.out.println("[상담 내용 정보]");
		System.out.println("고객명 : " + customer.getCustomerName());
		System.out.println("담당자명 : " + counsel.getManagerName());
		System.out.println("상담 내용 : " + counsel.getContent());
	}

	private static void showDetailcounselInfo(CounselApplication counselApplication, Customer customer) {
		System.out.println("\n[세부 내역]");
		System.out.println("첫째날 : " + counselApplication.getDateOfFirst());
		System.out.println("둘째날 : " + counselApplication.getDateOfSecond());
		System.out.println("담당자명 : " + counselApplication.getCounsel().getManagerName());
		System.out.println("고객 아이디 : " + customer.getCustomerID());
		System.out.println("고객명 : " + customer.getCustomerName());
		System.out.println("고객 전화번호 : " + customer.getPnumber());
		System.out.println("요구사항 : " + counselApplication.getRequirement());
	}

	private static CounselApplication getNewCounsel(BufferedReader inputReader) throws ParseException, IOException {
		CounselApplication newCounsel = new CounselApplication();
		String dateStr = null;
		String manager = null;
		String id = null;
		String req = null;
		boolean allInput = false;
		do {
			System.out.print("날짜 : "); // yyyymmdd
			dateStr = inputReader.readLine().trim();
			System.out.print("담당자명 : ");
			manager = inputReader.readLine().trim();
			System.out.print("고객 아이디 : ");
			id = inputReader.readLine().trim();
			System.out.print("요구사항 : ");
			req = inputReader.readLine().trim();
			allInput = isAllInput(dateStr, manager, id, req);
			if (!allInput)
				System.out.println("항목을 모두 입력해주세요.");
		} while (!allInput);

		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(4, 6));
		int day = Integer.parseInt(dateStr.substring(6, 8));
		LocalDate date = LocalDate.of(year, month, day);
		newCounsel.setDateOfFirst(date);
		newCounsel.setDateOfSecond(date);
		newCounsel.getCounsel().setManagerName(manager);
		newCounsel.setCustomerID(id);
		newCounsel.setRequirement(req);

		return newCounsel;
	}

	private static boolean isAllInput(String dateStr, String manager, String id, String req) {
		if (dateStr.length() == 0)
			return false;
		if (manager.length() == 0)
			return false;
		if (id.length() == 0)
			return false;
		if (req.length() == 0)
			return false;
		return true;
	}

	private static String inputContent(BufferedReader inputReader) throws IOException {
		System.out.println("내용을 입력해 주세요");
		System.out.print("내용 : ");
		return inputReader.readLine().trim();
	}

	private static boolean selectContentAcquire(BufferedReader inputReader) throws IOException {
		System.out.println("내용을 등록하겠습니까?");
		System.out.print("1.예 2. 아니오");
		System.out.print("입력 : ");
		return inputReader.readLine().trim().equals("1");
	}

	private static boolean selectScheduleOrContent(BufferedReader inputReader) throws IOException {
		System.out.println("\n[상담 정보 등록]");
		System.out.println("1. 상담 일정 등록");
		System.out.println("2. 상담 내용 등록");
		System.out.print("입력 : ");
		return inputReader.readLine().trim().equals("1");
	}

	private static boolean selectRetOrDel(BufferedReader inputReader) throws IOException {
		System.out.print("1.상담 내용 조회. 2.상담 일정 삭제  ");
		return inputReader.readLine().trim().equals("1");
	}

	private static void showCounseList(Customer customer, List<CounselApplication> selectedCouncels) {
		System.out.println("<상담 일정 목록>");
		for (CounselApplication counselApplication : selectedCouncels) {
			System.out.println("첫째날 : " + counselApplication.getDateOfFirst());
			System.out.println("둘째날 : " + counselApplication.getDateOfSecond());
			System.out.println("고객명 : " + customer.getCustomerName());
			System.out.println("담당자명 : " + counselApplication.getCounsel().getManagerName());
		}
	}

	private static void showCounselSchedule(CounselApplication counselApplication, Customer customer) {
		System.out.println("\n[상담 일정]");
		System.out.println("첫째날 : " + counselApplication.getDateOfFirst());
		System.out.println("둘째날 : " + counselApplication.getDateOfSecond());
		System.out.println("이름 : " + customer.getCustomerName());
		System.out.println("담당자명 : " + counselApplication.getCounsel().getManagerName());
	}

	private static boolean checkInputId(String id) {
		if (id.equals("")) {
			System.out.println("조건을 기입 주세요");
			return false;
		}
		for (char c : id.toCharArray()) {
			if (!Character.isDigit(c)) {
				System.out.println("조건을 기입 주세요");
				return false;
			}
		}
		return true;
	}

	private static boolean selectConsultationCase(BufferedReader inputReader) throws IOException {
		System.out.println("\n[상담 정보 관리]");
		System.out.println("1. 상담 정보 조회");
		System.out.println("2. 상담 정보 등록");
		System.out.print("입력 : ");
		return inputReader.readLine().trim().equals("1") ? true : false;
	}

	private static void showCouncel(BufferedReader inputReader, CounselApplicationListImpl counselApplicationListImpl)
			throws NumberFormatException, IOException, ParseException {
		CounselApplication counselApplication = getNewCouncel(inputReader); // 상담 내역 입력
		if (counselApplication == null)
			return;
		counselApplicationListImpl.add(counselApplication); // 상담 내역 추가
		System.out.println("상담 신청이 완료되었습니다.\n신청하신 상담일자에 전화드릴 예정입니다.");
	}

	private static CounselApplication getNewCouncel(BufferedReader inputReader) throws IOException, ParseException {
		System.out.println("상담 신청 입력");
		String id = null;
		String dateStr = null;
		String category = null;
		String content = null;

		do {
			System.out.print("고객 ID : ");
			id = inputReader.readLine().trim();
			if (id.length() == 0)
				System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		} while (id.length() == 0);

		do {
			System.out.print("1지망 일시 : ");
			dateStr = inputReader.readLine().trim();
			if (dateStr.length() == 0)
				System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		} while (dateStr.length() == 0);

		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(4, 6));
		int day = Integer.parseInt(dateStr.substring(6, 8));
		LocalDate date1 = LocalDate.of(year, month, day);

		do {
			System.out.print("2지망 일시 : ");
			dateStr = inputReader.readLine().trim();
			if (dateStr.length() == 0)
				System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		} while (dateStr.length() == 0);

		year = Integer.parseInt(dateStr.substring(0, 4));
		month = Integer.parseInt(dateStr.substring(4, 6));
		day = Integer.parseInt(dateStr.substring(6, 8));
		LocalDate date2 = LocalDate.of(year, month, day);

		do {
			System.out.print("상담 유형 : ");
			category = inputReader.readLine().trim();
			if (category.length() == 0)
				System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		} while (category.length() == 0);

		do {
			System.out.print("상세 내용 입력 : ");
			content = inputReader.readLine().trim();
			if (content.length() == 0)
				System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		} while (content.length() == 0);

		System.out.println("제출하겠습니까?");
		System.out.print("1. 예, 2. 아니오 : ");
		if (!inputReader.readLine().trim().equals("1"))
			return null;
		CounselApplication counselApplication = new CounselApplication();
		counselApplication.setCustomerID(id);
		counselApplication.setDateOfFirst(date1);
		counselApplication.setDateOfSecond(date2);
		counselApplication.setCategory(category);
		counselApplication.getCounsel().setContent(content);
		return counselApplication;
	}

	private static void showfMaturityList(BufferedReader inputReader, ContractListImpl contractListImpl,
			CustomerListImpl customerListImpl, FamilyHistoryListImpl familyHistoryListImpl,
			InsuranceListImpl insuranceList, PaymentListImpl paymentListImpl,
			CompensationClaimListImpl compensationClaimList) throws Exception { // 만기계약 대상자 조회
		TargetType targetType = showKeepContract(inputReader); // 계약유지대상자 조회화면 출력 및 대상자 입력 - Enum 반환
		ArrayList<Customer> customerList = null;

		if (targetType == TargetType.RESURRECT_CANDIDATES) { // 1. 부활 대상자
			customerList = customerListImpl.getResurrectCandidates(contractListImpl); // 부활 대상자들 받아옴
			showResurrectContracts(customerList); // 부활 대상자 출력
			boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
			if (!isShowDetail)
				return;
			Customer customer = getCustomerFromResurrect(customerListImpl, inputReader); // 부활 대상자에서 고객 조회
			if (customer == null) {
				System.out.println("입력하신 고객 정보가 없습니다.");
				return;
			}
			showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			// 리스트에서 지운다 -> 전체 계약 목록
			if (!selectCustomerDelete(inputReader))
				return;
			customerListImpl.deleteResurrectCandidatesCustomer(customer);
			contractListImpl.setResurrectFromCustomer(customer);
			System.out.println("대상자에서 제외되었습니다.");
		} else if (targetType == TargetType.EXPIRED_CONTRACTS) { // 2. 만기계약자
			customerList = customerListImpl.getExpiredContracts(contractListImpl); // 만기계약 대상자들 받아옴
			showExpiredContracts(customerList); // 만기계약 대상자 출력
			boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
			if (!isShowDetail)
				return;
			Customer customer = getCustomerFromExpired(customerListImpl, inputReader); // 만기계약 대상자에서 고객 조회
			if (customer == null) {
				System.out.println("입력하신 고객 정보가 없습니다.");
				return;
			}
			showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			// 리스트에서 지운다 -> 전체 계약 목록
			if (!selectCustomerDelete(inputReader))
				return;
			customerListImpl.deleteExpiredCustomer(customer);
			contractListImpl.setMaturityFromCustomer(customer);
			System.out.println("대상자에서 제외되었습니다.");
		} else if (targetType == TargetType.UNPAID_CUSTOMERS) { // 3. 보험료 미납자
//			customerList = customerListImpl.getUnpaidContracts(contractListImpl); // 미납 대상자들 받아옴
//			showUnPaidContracts(customerList); // 미납 대상자 출력
//			boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
//			if (!isShowDetail)
//				return;
//			Customer customer = getCustomerFromUnpaid(customerListImpl, inputReader); // 미납 대상자에서 고객 조회
//			if (customer == null) {
//				System.out.println("입력하신 고객 정보가 없습니다.");
//				return;
//			}
//			showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
//			if (!selectCustomerDelete(inputReader))
//				return;
//			customerListImpl.deleteUnpaidCustomer(customer);
//			contractListImpl.setWheaterPaymentFromCustomer(customer);
//			System.out.println("대상자에서 제외되었습니다.");
			showUnpaidCustomer(inputReader, contractListImpl, customerListImpl, insuranceList, familyHistoryListImpl,
					paymentListImpl, compensationClaimList);
		}
	}

	private static boolean selectCustomerDelete(BufferedReader inputReader) throws IOException {
		System.out.println("해당 대상자를 지우겠습니까?");
		System.out.print("1. 예 2. 아니오  ");
		return inputReader.readLine().trim().equals("1");
	}

	private static TargetType showKeepContract(BufferedReader inputReader) throws IOException {
		System.out.println("계약유지 대상자를 선택해 주세요");
		System.out.println("1. 부활대상자");
		System.out.println("2. 만기계약자");
		System.out.println("3. 보험료 미납자");
		System.out.print("입력 : ");
		switch (inputReader.readLine().trim()) {
		case "1":
			return TargetType.RESURRECT_CANDIDATES;
		case "2":
			return TargetType.EXPIRED_CONTRACTS;
		case "3":
			return TargetType.UNPAID_CUSTOMERS;
		}
		return null;
	}

	private static void showResurrectContracts(ArrayList<Customer> customerList) {
		System.out.println("부활 대상자 리스트");
		for (Customer customer : customerList) {
			System.out.println("아이디: " + customer.getCustomerID() + ", 이름: " + customer.getCustomerName() + ", 성별: "
					+ customer.getEGender().getGenderStr());
		}
	}

	private static void showExpiredContracts(ArrayList<Customer> customerList) {
		System.out.println("만기 계약자 리스트");
		for (Customer customer : customerList) {
			System.out.println("아이디: " + customer.getCustomerID() + ", 이름: " + customer.getCustomerName() + ", 성별: "
					+ customer.getEGender().getGenderStr());
		}
	}

	private static void showUnPaidContracts(ArrayList<Customer> customerList) {
		System.out.println("미납 대상자 리스트");
		for (Customer customer : customerList) {
			System.out.println("아이디: " + customer.getCustomerID() + ", 이름: " + customer.getCustomerName() + ", 성별: "
					+ customer.getEGender().getGenderStr());
		}
	}

	private static Customer getCustomerFromUnpaid(CustomerListImpl customerListImpl, BufferedReader inputReader)
			throws NumberFormatException, IOException {
		System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");
		System.out.print("아이디 : ");
		String id = inputReader.readLine().trim();
		return customerListImpl.retrieveCustomerFromUnpaid(id);
	}

	private static Customer getCustomerFromExpired(CustomerListImpl customerListImpl, BufferedReader inputReader)
			throws NumberFormatException, IOException {
		System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");
		System.out.print("아이디 : ");
		String id = inputReader.readLine().trim();
		return customerListImpl.retrieveCustomerFromExpired(id);
	}

	private static Customer getCustomerFromResurrect(CustomerListImpl customerListImpl, BufferedReader inputReader)
			throws NumberFormatException, IOException {
		System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");
		System.out.print("아이디 : ");
		String id = inputReader.readLine().trim();
		return customerListImpl.retrieveCustomerFromResurrect(id);
	}

	private static void showCustomerList(CustomerListImpl customerListImpl, BufferedReader inputReader,
			FamilyHistoryListImpl familyHistoryListImpl, ContractListImpl contractListImpl,
			InsuranceListImpl insuranceList, PaymentListImpl paymentListImpl,
			CompensationClaimListImpl compensationClaimList) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("고객 조회 메뉴입니다.");
		System.out.println("1. 고객 정보 조회");
		System.out.println("2. 계약 유지 대상자 조회");
		String userChoice = "";
		userChoice = inputReader.readLine().trim();
		switch (userChoice) {
		case "1":
			System.out.println("[고객 정보 조회]");
			do {
				userChoice = inputCustomerId(inputReader); // 아이디 받아옴
				if (userChoice.equals(""))
					System.out.println("조건을 기입했는지 체크해주세요.");
			} while (userChoice.equals(""));
			String id = userChoice;
			Customer customer = customerListImpl.retrieveCustomer(id); // 아이디에 따른 고객정보 받아옴
			if (customer == null) {
				System.out.println("조건에 맞는 고객 정보가 하나도 없습니다.\n조건을 확인해주세요.");
				return;
			}
			showCustomerInfo(customer); // id 이름 생일 성별
			boolean isRetrieveDetail = getCustomerDetails(inputReader); // 세부정보 읽을지 확인
			if (isRetrieveDetail)
				showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			else
				return;
			if (inputDelOrUpd(inputReader)) {
				customerListImpl.delete(id); // 삭제 or 업뎃 여부 입력 후 해당 고객 삭제
				System.out.println("고객 정보가 삭제되었습니다.");
			} else
				customerListImpl.update(getUpdatedCustomer(inputReader), id); // 고객 정보 입력 받아서 해당 고객 업데이트
			break;
		case "2":
			System.out.println("[계약 유지 대상자 조회]");
			showfMaturityList(inputReader, contractListImpl, customerListImpl, familyHistoryListImpl, insuranceList,
					paymentListImpl, compensationClaimList);
			break;
		}
	}

	private static boolean inputDelOrUpd(BufferedReader inputReader) throws IOException { // 세부정보 삭제(T) or 업뎃(F) 입력
		System.out.println("문구를 선택해 주세요");
		System.out.print("1. 세부정보 삭제, 2. 세부정보 수정 : ");
		return inputReader.readLine().trim().toLowerCase().equals("1");
	}

	private static Customer getUpdatedCustomer(BufferedReader inputReader) throws IOException {
		Customer upCustomer = new Customer();
		System.out.println("수정할 정보를 입력해 주세요");
		System.out.print("고객 ID : ");
		upCustomer.setCustomerID(inputReader.readLine().trim());
		System.out.print("고객 이름 : ");
		upCustomer.setCustomerName(inputReader.readLine().trim());
		System.out.print("고객 성별(남/여) : ");
		upCustomer.setEGender(inputReader.readLine().trim().equals("남") ? EGender.male : EGender.female);
		System.out.print("고객 생년월일 : ");
		upCustomer.setBirth(inputReader.readLine().trim());
		System.out.print("고객 전화번호 : ");
		upCustomer.setPnumber(inputReader.readLine().trim());
		System.out.print("고객 주소 : ");
		upCustomer.setAddress(inputReader.readLine().trim());
		System.out.print("고객 직업 : ");
		upCustomer.setJob(inputReader.readLine().trim());
		return upCustomer;
	}

	private static void showCustomerDetailInfos(Customer customer, FamilyHistoryListImpl familyHistoryListImpl,
			ContractListImpl contractListImpl, InsuranceListImpl insuranceList) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("\n[고객 세부 정보]");
		showCustomerInfo(customer); // 고객 ID, 이름, 생일, 성별
		showCustomerInfoDetail(customer); // 전번 주소 직업
		showFamilyHistory(
				familyHistoryListImpl.getFamilyHistoryFromId(customer.getCustomerID(), familyHistoryListImpl));
		// 가족력 리스트(질환명,가족관계)
		List<Contract> contracts = CustomerListImpl.getContractFromCustomerId(customer.getCustomerID(),
				contractListImpl);
		List<Insurance> insurances = InsuranceListImpl.getInsuranceFromId(contracts, insuranceList);
		showInsuranceList(contracts, insurances);
		// 보유 계약 리스트(보험명/만기 여부/해지여부/납입 여부)
	}

	private static void showInsuranceList(List<Contract> contracts, List<Insurance> insurances) {
		for (Contract contract : contracts) {
			Insurance selectedInsurance = null;
			for (Insurance insurance : insurances) {
				selectedInsurance = insurance;
			}
			System.out.println("보험명 : " + selectedInsurance.getInsuranceName());
			System.out.println("만기 여부 : " + contract.isMaturity());
			System.out.println("해지여부 : " + contract.isCancellation());
			System.out.println("부활 여부 : " + contract.isResurrection() + "\n");
		}

	}

	private static void showFamilyHistory(FamilyHistory familyHistory) {
		System.out.println("질환명 : " + familyHistory.getDiseaseName());
		System.out.println("가족관계 : " + familyHistory.getRelationship());
	}

	private static boolean getCustomerDetails(BufferedReader inputReader) throws IOException {
		// 세부정보 조회 여부 확인
		System.out.println("고객 세부정보를 조회하겠습니까?");
		System.out.print("세부 정보 조회를 희망하시면 Yes 입력해주시고 아니면 No 입력해주세요 ");
		return inputReader.readLine().trim().toLowerCase().equals("yes");
	}

	private static void showCustomerInfo(Customer customer) {
		// TODO Auto-generated method stub
		// 선택 고객 정보 표시
		StringBuffer buffer = new StringBuffer();
		buffer.append("아이디 : " + customer.getCustomerID());
		buffer.append("\n이름 : " + customer.getCustomerName());
		buffer.append("\n생년월일 : " + customer.getBirth());
		buffer.append("\n성별 : " + customer.getEGender().getGenderStr()); // 성별 : male 혹은 남
		System.out.println(buffer.toString());
	}

	private static void showCustomerInfoDetail(Customer customer) {
		// TODO Auto-generated method stub
		// 선택 고객 정보 표시
		StringBuffer buffer = new StringBuffer();
		buffer.append("전화 번호 : " + customer.getPnumber());
		buffer.append("\n주소 : " + customer.getAddress());
		buffer.append("\n직업 : " + customer.getJob());
		System.out.println(buffer.toString());
	}

	private static String inputCustomerId(BufferedReader inputReader) throws IOException {
		System.out.print("고객 아이디를 입력해 주세요 : ");
		return inputReader.readLine().trim();
	}

	private static void createCompensationClaim(InsuranceListImpl insuranceList,
			CompensationClaimListImpl compensationClaimList, CarAccidentListImpl carAccidentList,
			ContractListImpl contractList, CustomerListImpl customerList, BufferedReader inputReader) throws Exception {
		CompensationClaim compensationClaim = new CompensationClaim();
		System.out.println("****************** Compensation Claim *******************");
		System.out.println("개인정보 확인을 위해 고객ID를 입력해주세요");
		String inputCustomerId = inputReader.readLine().trim();
		if (customerList.getCustomerByID(inputCustomerId) == null)
			System.out.println("존재하지 않는 ID입니다");
		System.out.println("고객님이 가입하신 보험 정보는 아래와 같습니다.\n" + "보험금을 청구할 보험ID를 입력하세요.");
		ArrayList<Contract> contracts = contractList.getContractsByCID(inputCustomerId);
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = contractList.getContractsByCID(inputCustomerId).get(i);
			Insurance insurance = insuranceList.getInsurancebyId(contract.getInsuranceID());
			System.out.println(
					insurance.getInsuranceID() + " " + insurance.getType() + " " + insurance.getInsuranceName());
		}
		String inputInsuranceId = inputReader.readLine().trim();
		compensationClaim.setCustomerID(inputCustomerId);
		compensationClaim.setInsuranceID(inputInsuranceId);
		compensationClaim.setCCID("CC" + compensationClaim.getInsuranceID() + compensationClaim.getCustomerID());
		Insurance selectedInsurance = insuranceList.retrieveInsuranceDetail(compensationClaim.getInsuranceID());
		System.out.println("보험종류: " + selectedInsurance.getType() + "  보험명: " + selectedInsurance.getInsuranceName()
				+ "\n" + "보험금 청구를 위한 서류 목록(하나의 압축파일로 업로드)\n" + "1. 보험금 청구서" + "  2. 개인(신용)정보처리동의서"
				+ "  3. 수익자 신분증(앞면) 사본" + "  4. 수익자 통장 사본\n" + "보험종류가 자동차보험인 경우, 5. 교통사고 사실 확인원  6. 교통사고신속처리협의서 추가 제출");
		System.out.print("접수자명: ");
		compensationClaim.setReceptionistName(inputReader.readLine().trim());
		System.out.print("접수자 전화번호: ");
		compensationClaim.setReceptionistPNumber(inputReader.readLine().trim());
		System.out.print("보험계약자와의 관계(예:부): ");
		compensationClaim.setRelationshipOfContractor(inputReader.readLine().trim());
		System.out.print("구비서류 업로드란: ");
		compensationClaim.setDocumentFilePath(inputReader.readLine().trim());
		System.out.print("은행: ");
		compensationClaim.setBank(inputReader.readLine().trim());
		System.out.print("계좌번호: ");
		compensationClaim.setAccountNumber(inputReader.readLine().trim());
		System.out.print("예금주명: ");
		compensationClaim.setAccountHolderName(inputReader.readLine().trim());
		if (insuranceList.getInsurancebyId(compensationClaim.getInsuranceID()).getType().equals("Car")) {
			CarAccident carAccident = createCarAccident(compensationClaim, inputReader);
			if (compensationClaimList.createCompensationClaim(compensationClaim)
					&& carAccidentList.createCarAccident(carAccident))
				System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
			else
				System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
		} else {
			if (compensationClaimList.createCompensationClaim(compensationClaim)) {
				System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
			} else
				System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
		}
	}

	private static void retrieveCompensationClaim(InsuranceListImpl insuranceList,
			CompensationClaimListImpl compensationClaimList, SurveyListImpl surveyList, BufferedReader inputReader)
			throws IOException {
		System.out.println("****************** Compensation Claim List *******************");
		System.out.println("청구ID 보험ID 고객ID 접수자명 접수자전화번호 보험계약자와의 관계 구비서류파일경로 은행 계좌번호 예금주명");
		showList(compensationClaimList.retrieve());
		System.out.println("1. 손해사정");
		String userChoice = "";
		userChoice = inputReader.readLine().trim();
		switch (userChoice) {
		case "1":
			createSurvey(compensationClaimList, surveyList, insuranceList, inputReader);
			break;

		}
	}

	private static void createSurvey(CompensationClaimListImpl compensationClaimList, SurveyListImpl surveyList,
			InsuranceListImpl insuranceList, BufferedReader inputReader) throws IOException {
		Survey survey = new Survey();
		System.out.println("****************** Survey *******************");
		System.out.println("손해사정할 청구ID를 입력하세요: ");
		survey.setCCID(inputReader.readLine().trim());
		CompensationClaim compensationClaim = compensationClaimList.getCompensationClaimbyID(survey.getCCID());
		System.out.print("담당자명: ");
		survey.setManagerName(inputReader.readLine().trim());
		System.out.print("조사보고서 업로드: ");
		survey.setReportFilePath(inputReader.readLine().trim());
		System.out.print("손해사정료: ");
		survey.setSurveyFee(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("결정보험금: ");
		survey.setDecisionMoney(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("면/부책 (면책은 false, 부책은 true를 입력하세요) : ");
		survey.setResponsibility(Boolean.parseBoolean(inputReader.readLine().trim()));
		System.out.print("면/부책 사유 : ");
		survey.setResponsibilityReason(inputReader.readLine().trim());
		if (surveyList.createSurvey(survey)) {
			System.out.println("수정이 완료되었습니다.");
			System.out.println("결정보험금(" + survey.getDecisionMoney() + "원)을 지급요청하려면 Y를 누르십시오");
			if (inputReader.readLine().trim().equals("Y"))
				System.out.println(
						compensationClaim.getReceptionistName() + " " + compensationClaim.getReceptionistPNumber() + " "
								+ insuranceList.getInsurancebyId(compensationClaim.getInsuranceID()).getInsuranceName()
								+ " " + compensationClaim.getBank() + " " + compensationClaim.getAccountHolderName()
								+ " " + survey.getDecisionMoney());
			requestBanking(survey, insuranceList, inputReader);
		} else
			System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}

	private static void requestBanking(Survey survey, InsuranceListImpl insuranceList, BufferedReader inputReader)
			throws IOException {
		// 보험사 시스템은 결정보험금 지급 내용(접수자명, 접수자 전화번호, 보험명, 은행, 계좌번호, 예금주명, 결정보험금액)과 이체 요청 메시지를
		// 은행에 전달한다.
		System.out.println("이체 요청을 완료했습니다. 보험금이 입금되기까지는 수일이 소요될 수 있습니다.");
		// 보상처리팀 직원은 접수자의 전화번호로 ‘보험금 이체 신청이 완료되었습니다. 보험금이 입금되기까지는 수일이 소요될 수 있습니다.’ 라는
		// 메시지를 보낸다
	}

	private static CarAccident createCarAccident(CompensationClaim compensationClaim, BufferedReader inputReader)
			throws IOException {
		CarAccident carAccident = new CarAccident();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
		System.out.println("****************** Compensation Claim - Car Accident*******************");
		carAccident.setCCID(compensationClaim.getCCID());
		System.out.print("사고유형: ");
		carAccident.setType(inputReader.readLine().trim());
		System.out.print("사고일시 (형식 예시-2023년 01월 01일 17시 30분): ");
		carAccident.setDateTime(LocalDateTime.parse(inputReader.readLine().trim(), dateTimeFormatter));
		System.out.print("사고장소: ");
		carAccident.setPlace(inputReader.readLine().trim());
		System.out.print("차량번호: ");
		carAccident.setCarNumber(inputReader.readLine().trim());
		System.out.print("운전자 성명: ");
		carAccident.setDriverName(inputReader.readLine().trim());
		System.out.print("면허번호: ");
		carAccident.setLicenseNumber(inputReader.readLine().trim());
		System.out.print("사고 내용: ");
		carAccident.setAccidentDetail(inputReader.readLine().trim());
		return carAccident;
	}


	private static void designInsurance(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,
			CustomerListImpl customerList, FamilyHistoryListImpl familyHistoryList, GuaranteeListImpl guaranteeList,
			BufferedReader inputReader, InsuranceApplicationListImpl insuranceApplicationList) throws Exception {

		String choice = "";
		while (true) {
			System.out.println("****************** 보험 설계 화면 *******************");
			System.out.println("1. 설계 보험 관리 2. 새 보험 설계 3. 약관 관리 4. 판매중인 보험 조회 x. 종료");
			System.out.println("선택 : ");
			choice = inputReader.readLine().trim();



			if (choice.equals("1")) showDesignedInsurance(insuranceListImpl, inputReader);
			else if (choice.equals("2")) createInsurance(insuranceListImpl, inputReader);
			else if (choice.equals("3")) termsManagement(insuranceListImpl, termsListImpl, inputReader);
			else if (choice.equals("4")) showOnSaleInsurance(insuranceListImpl, insuranceApplicationList, customerList, familyHistoryList,
					guaranteeList, termsListImpl, inputReader, "Manager");
			else if (choice.equals("x")) break;
			else System.out.println("잘못된 선택지입니다.");}
	}
	private static void termsManagement(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl, BufferedReader inputReader) throws Exception {
		while (true) {
			System.out.println("****************** 약관 관리 화면 *******************");
			System.out.println("1. 약관 조회 2. 새 약관 등록 x. 종료");
			System.out.println("선택 : ");
			String choice = inputReader.readLine().trim();
			if (choice.equals("1")) showList(termsListImpl.retrieveAllTerms());
			else if (choice.equals("2")) createTerms(termsListImpl, inputReader);
			else if (choice.equals("x")) break;
			else System.out.println("잘못된 선택지입니다.");
		}
	}


	private static void createTerms(TermsListImpl termsListImpl, BufferedReader inputReader) throws Exception {

		Terms terms = new Terms();
		System.out.println("--------약관 추가 화면---------");
		System.out.println("약관 ID : ");
		terms.setTermsID(inputReader.readLine().trim());
		System.out.println("약관명  : ");
		terms.setTermsName(inputReader.readLine().trim());
		System.out.println("보장내용 : ");
		terms.setTermsContent(inputReader.readLine().trim());
		System.out.println("지급보험금 산정방식: ");
		terms.setCalculatedMoneyMethod(inputReader.readLine().trim());
		System.out.println("입력한 내용을 저장하시겠습니까? (Y/N)");
		while(true) {
		System.out.println("선택 : ");
		String save = inputReader.readLine().trim();
		if (save.equals("Y") && terms.checkAllFillIn()== true) {
			if (termsListImpl.createTerms(terms)) System.out.println("저장되었습니다.");
			else System.out.println("저장되지 않았습니다.");	
		break;}
		else if(save.equals("Y") && terms.checkAllFillIn() == false) {System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해 주세요"); break;}
		else if(save.equals("N")) { System.out.println("저장을 취소했습니다."); break;}
		else System.out.println("Y/N중 하나는 입력하셔야합니다.");
		}

	}

	private static void showDesignedInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws Exception {
		while (true) {
			System.out.println("****************** 설계 보험 관리 화면 *******************");
			showList(insuranceListImpl.getUnregisteredInsuranceList());
			System.out.println("1. 설계서 수정/삭제하기 2. 보험 등록하기 3. 금융감독원에 인가 요청 x. 종료");
			System.out.println("선택 : ");
			String choice = inputReader.readLine().trim();
			if (choice.equals("1")) updateInsuranceDetail(insuranceListImpl, inputReader);
			else if (choice.equals("2")) registerInsurance(insuranceListImpl, inputReader);
			else if (choice.equals("3")) requestAuthorization(insuranceListImpl, inputReader);
			else if (choice.equals("x")) break;
			else System.out.println("잘못된 선택지입니다.");}
	}
	

	private static void requestAuthorization(InsuranceListImpl insuranceListImpl, BufferedReader inputReader)
			throws IOException {
		System.out.println("****************** 인가 요청 화면 *******************");
		System.out.println("인가를 요청할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		while(true) {
		System.out.println("선택 : ");
		String insuranceID = inputReader.readLine().trim();
		if (!insuranceID.equals("x") && !insuranceID.equals(null)) {
			String insuranceName = insuranceListImpl.requestAuthorization(insuranceID);
			if (!insuranceName.equals(null)) System.out.println(insuranceName + "인가 신청이 완료되었습니다. 인가 완료까지 수 일이 소요될 수 있습니다.");
			else System.out.println("신청에 실패했습니다."); 
			break;} 
		else if (insuranceID.equals("x")) { System.out.println("이전화면으로 돌아갑니다."); break;}
		else if (insuranceID.equals(null)) System.out.println("아무것도 입력되지 않았습니다.");}
	}
	private static void registerInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws Exception {
			System.out.println("****************** 보험 등록 화면 *******************");
			System.out.println("판매중으로 등록할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
			System.out.println("선택 : ");
			String insuranceID = inputReader.readLine().trim();
			if (!insuranceID.equals("x") && !insuranceID.equals(null)) {
				while(true) {
				System.out.println("금융감독원에 의해 인가받은 보험이 확실합니까? (Y/N)");
				System.out.println("선택 : ");
				String choice = inputReader.readLine().trim();
				if (choice.equals("Y")) {
					if (insuranceListImpl.updateAuthorization(insuranceID, true)) System.out.println("신규 보험 등록이 완료되었습니다.");
					else System.out.println("존재하지 않는 보험ID입니다.");
					break;}
				else if(choice.equals("N")) {System.out.println("신청이 취소되었습니다.");break;}
				else System.out.println("Y/N 중 하나를 입력해주세요");
				}}
			else if (insuranceID.equals(null)) System.out.println("아무것도 입력되지 않았습니다.");
			else if (insuranceID.equals("x")) System.out.println("보험 등록을 종료합니다.");
		}


	private static void showOnSaleInsurance(InsuranceListImpl insuranceListImpl,
			InsuranceApplicationListImpl insuranceApplicationList, CustomerListImpl customerList,
			FamilyHistoryListImpl familyHistoryList, GuaranteeListImpl guaranteeList, TermsListImpl termsList,
			BufferedReader inputReader, String who) throws IOException {
		String insuranceType = "";
		while (true) {
			System.out.println("****************** 보험 조회 화면 *******************");
			System.out.println("조회하실 보험 종류를 입력하세요");
			System.out.println("1. 전체, 2. 자동차보험, 3. 건강보험, 4. 종신보험, 5. 화재보험 6. 재물보험 x. 조회 종료");
			System.out.println("선택 : ");
			insuranceType = inputReader.readLine().trim();
			if (insuranceType.equals("1")) showList(insuranceListImpl.getOnSaleInsuranceList());
			else if (insuranceType.equals("2")) showList(insuranceListImpl.retrieveInsurance("Car"));
			else if (insuranceType.equals("3")) showList(insuranceListImpl.retrieveInsurance("Health"));
			else if (insuranceType.equals("4")) showList(insuranceListImpl.retrieveInsurance("Life"));
			else if (insuranceType.equals("5")) showList(insuranceListImpl.retrieveInsurance("Fire"));
			else if (insuranceType.equals("6")) showList(insuranceListImpl.retrieveInsurance("Property"));
			else if (!insuranceType.equals("x")) System.out.println("잘못된 선택지입니다.");
			else if (insuranceType.equals("x")) break;
			if (who.equals("Customer")) {
				System.out.println("보험 신청 메뉴로 이동하시겠습니까? (Y/N)");
				String choice = inputReader.readLine().trim();
				if (choice.equals("Y") || choice.equals("y")) {
					System.out.println("가입 신청할 보험ID를 입력하세요");
					Insurance insurance = insuranceListImpl.retrieveInsuranceDetail(inputReader.readLine().trim());
					System.out.println("********** 보험 정보 **********");
					System.out.println("보험종류: " + insurance.getType() + "\n보험명: " + insurance.getInsuranceName()
							+ "\n최대보장한도: " + insurance.getMaxCompensation() + "\n보험기간: "
							+ insurance.getPeriodOfInsurance() + "\n납입기간: " + insurance.getPaymentPeriod() + "\n가입나이: "
							+ insurance.getAgeOfTarget() + "\n납입주기: " + insurance.getPaymentCycle() + "\n보장내용(보통약관):");
					ArrayList<Guarantee> guarantees = guaranteeList.getAllGuranteeByID(insurance.getInsuranceID());
					for (int i = 0; i < guarantees.size(); i++) {
						Guarantee guaranteeByIID = guaranteeList.getAllGuranteeByID(insurance.getInsuranceID()).get(i);
						Terms terms = termsList.getTermsByID(guaranteeByIID.getTermsID());
						System.out.println("약관명: " + terms.getTermsName() + "  약관내용: " + terms.getTermsContent());
					}
					System.out.println(
							"배당여부: " + insurance.isDistributionStatus() + "\n주의사항: " + insurance.getPrecaution());
					createInsuranceApplication(insurance, insuranceApplicationList, customerList, familyHistoryList,
							guaranteeList, termsList, inputReader);
				}
			} else {
				System.out.println("이전 화면으로 돌아갑니다.");
			}
		}
	}

	private static void createInsuranceApplication(Insurance insurance,
			InsuranceApplicationListImpl insuranceApplicationList, CustomerListImpl customerList,
			FamilyHistoryListImpl familyHistoryList, GuaranteeListImpl guaranteeList, TermsListImpl termsList,
			BufferedReader inputReader) throws IOException {
		InsuranceApplication insuranceApplication = new InsuranceApplication();
		insuranceApplication.setInsuranceID(insurance.getInsuranceID());
		insuranceApplication.setCreatedAt(LocalDate.now());
		System.out.println("****************** Insurance Application *******************");
		System.out.println("고객ID를 입력해주세요");
		Customer customer = customerList.getCustomerByID(inputReader.readLine().trim());
		insuranceApplication.setCustomerID(customer.getCustomerID());
		insuranceApplication
				.setApplicationID(insuranceApplication.getInsuranceID() + insuranceApplication.getCustomerID());
		System.out.print("보험 기간: ");
		insuranceApplication.setInsurancePeriod(inputReader.readLine().trim());
		System.out.print("납입 주기: ");
		insuranceApplication.setPaymentCycle(inputReader.readLine().trim());
		System.out.print("청약서 업로드: ");
		insuranceApplication.setSubscriptionFilePath(inputReader.readLine().trim());
		if (showTermsAndConditions(customer, insurance, insuranceApplication, familyHistoryList, guaranteeList,
				termsList, inputReader)) {
			if (insuranceApplicationList.createInsuranceApplication(insuranceApplication)) {
				System.out.println("신청이 완료되었습니다. 심사 결과에 따라 최대보장한도 또는 보험료가 제한되거나 가입이 불가능할 수 있습니다.");
			} else
				System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
		} else
			System.out.println("약관에 동의하지 않으면 가입 신청이 불가능합니다. 약관을 읽고 동의란에 체크해주세요");
	}

	private static boolean showTermsAndConditions(Customer customer, Insurance insurance,
			InsuranceApplication insuranceApplication, FamilyHistoryListImpl familyHistoryList,
			GuaranteeListImpl guaranteeList, TermsListImpl termsList, BufferedReader inputReader) throws IOException {
		System.out.println("********** 보험 약관 안내 **********");
		System.out.println("--------보험 정보--------");
		System.out.println("보험종류: " + insurance.getType() + "\n보험명: " + insurance.getInsuranceName() + "\n최대보장한도: "
				+ insurance.getMaxCompensation() + "\n보험기간: " + insurance.getPeriodOfInsurance() + "\n납입기간: "
				+ insurance.getPaymentPeriod() + "\n가입나이: " + insurance.getAgeOfTarget() + "\n납입주기: "
				+ insurance.getPaymentCycle() + "\n보장내용(보통약관):");
		ArrayList<Guarantee> guarantees = guaranteeList.getAllGuranteeByID(insurance.getInsuranceID());
		for (int i = 0; i < guarantees.size(); i++) {
			Guarantee guaranteeByIID = guaranteeList.getAllGuranteeByID(insurance.getInsuranceID()).get(i);
			Terms terms = termsList.getTermsByID(guaranteeByIID.getTermsID());
			System.out.println("약관명: " + terms.getTermsName() + "  약관내용: " + terms.getTermsContent());
		}
		System.out.println("배당여부: " + insurance.isDistributionStatus() + "\n주의사항: " + insurance.getPrecaution());
		System.out.println("선택한 보험 기간: " + insuranceApplication.getInsurancePeriod() + "  선택한 납입 주기: "
				+ insuranceApplication.getPaymentCycle());
		System.out.println("\n--------고객 정보--------");
		System.out.println("이름: " + customer.getCustomerName() + "  성별: " + customer.getEGender() + "  생년월일: "
				+ customer.getBirth() + "\n전화번호: " + customer.getPnumber() + "  주소: " + customer.getAddress() + "  직업: "
				+ customer.getJob());
		System.out.println("가족력: ");
		for (FamilyHistory familyHistory : familyHistoryList.getFamilyHistoryByCID(customer.getCustomerID())) {
			System.out.println("가족관계: " + familyHistory.getRelationship() + "  질환명: " + familyHistory.getDiseaseName());
		}
		System.out.println("\n--------보험 약관 안내--------");
		System.out
				.println("제1조(보험계약의 성립)\n" + "  ① 보험계약은 보험계약자의 청약과 보험회사의 승낙으로 이루어집니다...(생략)\n" + "제2조(약관교부 및 설명의무 등) \n"
						+ "  ① 회사는 계약자가 청약한 경우 계약자에게 약관 및 계약자 보관용 청약서(청약서 부본)를 드리고 약관의 중요한 내용을 설명하여 드립니다...(이하생략)");
		System.out.println("\n위 약관에 동의하십니까? (Y/N)");
		if (inputReader.readLine().trim().equals("Y")) {
			return true;
		} else
			return false;
	}

	private static void updateInsuranceDetail(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws Exception {
		while (true) {
			System.out.println("****************** 설계서 관리 화면 *******************");
			System.out.println("수정/삭제할 설계서의 보험 ID를 입력하세요. 없으면 x를 입력하세요");
			String insuranceID = "";
			Insurance insurance;
			System.out.println("보험 ID : ");
			insuranceID = inputReader.readLine().trim();
			if (!insuranceID.equals("x")) {
				insurance = insuranceListImpl.retrieveInsuranceDetail(insuranceID);
				System.out.println(insurance.toStringDesignInsurance());
				System.out.println("1. 수정, 2. 삭제");
				String choice = inputReader.readLine().trim();
				if (choice.equals("1")) updateInsurance(insurance, insuranceListImpl, inputReader);
				else if (choice.equals("2")) deleteInsurance(insuranceListImpl, insurance.getInsuranceID(), inputReader);
				else System.out.println("잘못된 선택지 입니다.");} 
			else if (insuranceID.equals("x")) break;
			else if (!insuranceID.equals(null)) System.out.println("아무것도 입력되지 않았습니다.");}
	}

	
	private static boolean isInteger(String input) {
	    try {
	      Integer.parseInt(input);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
	  }

	private static void createInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws Exception {

		Insurance insurance = new Insurance();
		System.out.println("--------보험 설계 화면---------");
		System.out.println("보험 ID : "); insurance.setInsuranceID(inputReader.readLine().trim());
		System.out.println("보험명 : "); insurance.setInsuranceName(inputReader.readLine().trim());
		System.out.println("보험 종류 : "); insurance.setType(inputReader.readLine().trim());
		while(true) {
		System.out.println("보험가입금액 : "); String input = inputReader.readLine().trim();
		if(isInteger(input)) { insurance.setMaxCompensation(Integer.valueOf(input)); break;}
		else System.out.println("숫자로 입력해주세요");
		}
		System.out.println("보험 기간 : "); insurance.setPeriodOfInsurance(inputReader.readLine().trim());
		System.out.println("납입 주기 : "); insurance.setPaymentCycle(inputReader.readLine().trim());
		System.out.println("납입 기간 : "); insurance.setPaymentPeriod(inputReader.readLine().trim());
		System.out.println("가입 나이 : "); insurance.setAgeOfTarget(inputReader.readLine().trim());
		while(true) {
			System.out.println("기본 보험료 : "); String input = inputReader.readLine().trim();
			if(isInteger(input)) {insurance.setBasicPremium(Integer.valueOf(input)); break;}
			else System.out.println("숫자로 입력해주세요");
		}
		System.out.println("요율 : "); insurance.setRate(inputReader.readLine().trim());
		System.out.println("배당 여부(False/True) : "); insurance.setDistributionStatus(Boolean.parseBoolean(inputReader.readLine().trim()));
		while (true) {
			System.out.println("보장 내용(약관ID, 콤마로 구분해주세요) : ");
			if (insurance.setTermsIDList(inputReader.readLine().trim()) == false) System.out.println("약관ID가 존재하지 않습니다. 다시 입력해주세요");
			else break;
		}
		System.out.println("주의사항 : "); insurance.setPrecaution(inputReader.readLine().trim());
		System.out.println("보험 면책 기간 : "); insurance.setInsuranceClausePeriod(inputReader.readLine().trim());
		System.out.println("입력한 내용을 저장하시겠습니까? (Y/N)");

		while(true) {
		System.out.println("선택 : ");
		String save = inputReader.readLine().trim();	
		if (save.equals("Y")&&insurance.checkAllFillIn()==true) {	
			if(insuranceListImpl.createInsurance(insurance)) System.out.println("저장되었습니다.");
			else System.out.println("저장에 실패했습니다.");
			break;}
		else if(save.equals("Y")&&insurance.checkAllFillIn()==false) {System.out.println("입력하지 않은 항목이 있습니다. 모든 항목을 입력해 주세요"); break;}
		else if(save.equals("N")) {System.out.println("저장을 취소했습니다."); break;}
		else System.out.println("Y/N중 하나는 입력하셔야합니다.");
		}
	}	

	private static void updateInsurance(Insurance insurance, InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws Exception {
		String choice = "";
		System.out.println("수정할 정보를 선택하고 내용을 입력하세요.");
		System.out.println(
				"1. 보험 ID, 2. 보험 이름, 3. 보험 종류, 4. 보험가입금액, 5. 보험 기간, 6. 납입 주기, 7. 납입 기간, 8. 가입 나이, 9. 기본 보험료, 10. 요율, "
						+ "11. 배당 여부(False/True), 12. 보장 내용(약관ID, 콤마로 구분해주세요), 13. 주의사항, 14. 보험 면책 기간");
		System.out.println("수정할 정보 : "); choice = inputReader.readLine().trim();
		System.out.println("수정할 내용 :"); String content = inputReader.readLine().trim();
		switch (choice) {
		case ("1"): insurance.setInsuranceID(content); break;
		case ("2"): insurance.setInsuranceName(content); break;
		case ("3"): insurance.setType(content); break;
		case ("4"): 
			if(isInteger(content)) insurance.setMaxCompensation(Integer.valueOf(content)); 
			else System.out.println("숫자로 입력되지 않아 수정이 저장되지 않았습니다.");
			break;
		case ("5"): insurance.setPeriodOfInsurance(content); break;
		case ("6"): insurance.setPaymentCycle(content); break;
		case ("7"): insurance.setPaymentPeriod(content); break;
		case ("8"): insurance.setAgeOfTarget(content); break;
		case ("9"):
			if(isInteger(content)) insurance.setBasicPremium(Integer.valueOf(content));
			else System.out.println("숫자로 입력되지 않아 수정이 저장되지 않았습니다.");
			break;
		case ("10"): insurance.setRate(content); break;
		case ("11"): insurance.setDistributionStatus(Boolean.parseBoolean(content)); break;
		case ("12"): if (insurance.setTermsIDList(content) == false) System.out.println("약관 ID가 존재하지 않아 정보가 저장되지 않았습니다."); break;
		case ("13"): insurance.setPrecaution(content); break;
		case ("14"): insurance.setInsuranceClausePeriod(content); break;
		default: System.out.println("올바르지 않은 선택지입니다.");
		}
		if (insuranceListImpl.updateinsurance(insurance)) System.out.println("수정이 저장되었습니다.");
		else System.out.println("수정에 실패했습니다.");
	}

	private static void deleteInsurance(InsuranceListImpl insuranceListImpl, String insuranceID, BufferedReader inputReader) throws Exception {
		System.out.println("보험ID : " + insuranceID + "를 삭제하시겠습니까? (Y/N)");
		while(true) {
		String choice = inputReader.readLine().trim();
		if (choice.equals("Y")) {
			if (insuranceListImpl.deleteInsurance(insuranceID)) System.out.println("삭제되었습니다.");
			else System.out.println("삭제에 실패하였습니다.");
			break;}
		else if (choice.equals("N")) System.out.println("삭제가 취소되었습니다.");
		else System.out.println("Y/N 중 하나를 입력해야합니다.");}
	}

	private static void showList(ArrayList<?> dataList) {
		String list = "";
		for (int i = 0; i < dataList.size(); i++) {
			list += dataList.get(i) + "\n";
		}
		System.out.println(list);
	}


	private static void showMenu() {
		System.out.println("\n****************** Initial Menu *******************");
		System.out.println("1. 보험금 청구");
		System.out.println("2. 보험금 청구 내역(직원용)");
		System.out.println("3. 보험 조회");
		System.out.println("4. 보험 설계(직원용)");
		System.out.println("5. 고객 조회");
		System.out.println("6. 고객 상담 신청");
		System.out.println("7. 상담 정보 관리");
		System.out.println("8. 보험 가입 신청 내역");
		System.out.println("9. 내 보험 확인");
		System.out.println("10. 납입 관리 메뉴 ");
		System.out.println("11. 보험료 조회/납부");
		System.out.println("x. Exit");
	}

// uc17) 가입된 보험을 조회한다.
	private static void showSubscriptionInsurance(BufferedReader inputReader, ContractListImpl contractListImpl,
			CustomerListImpl customerListImpl, InsuranceListImpl insuranceListImpl) throws ParseException, Exception {
		// 가입된 보험을 조회하다.
		System.out.println("\n[ 보험자 정보 확인 ]");
		System.out.print("이름 : ");
		String customerName = inputReader.readLine().trim();
		System.out.print("전화번호 : ");
		String customerPH = inputReader.readLine().trim();

		// A1. 입력된 정보의 사용자가 없는 경우
		String customerId = customerListImpl.getCustomerIdFromNameAndPH(customerName, customerPH);
		if (customerId == null) {
			System.out.println("\n[System] 등록되지 않은 사용자입니다.");
			return;
		}

		System.out.println("\n[ 내 보험 리스트 ]");
		ArrayList<Contract> customerContract = contractListImpl.retreiveCustomerContract(customerId);
		ArrayList<String> customerInsuranceId = contractListImpl.getInsuranceIdFromCustomerId(customerId);
		ArrayList<String> customerInsuranceInfo = new ArrayList<>();
		int indexBtn = 0;
		// customerContract와 customerInsuranceId의 크기가 같다고 가정, 같지 않을 시 잘못 등록된 정보
		for (int i = 0; i < customerInsuranceId.size(); i++) {
			Contract contract = customerContract.get(i); // 현재 계약 정보
			ArrayList<String> insuranceInfo = insuranceListImpl
					.getInsuranceNameTypeInfoById(customerInsuranceId.get(i)); // 현재 보험 정보

			// 계약 정보와 보험 정보를 문자열로 merge
			String mergedInfo = String.join(" ", insuranceInfo) + " " + contract.getMaxCompensation() + " "
					+ contract.getPremium() + " " + contract.isCancellation() + " " + contract.getDateOfSubscription()
					+ " " + contract.getDateOfMaturity() + " " + contract.isMaturity() + " " + contract.isResurrection()
					+ " " + contract.getInsurancePeriod() + " " + contract.getPaymentCycle() + " " + indexBtn;
			indexBtn++;
			customerInsuranceInfo.add(mergedInfo); // 합쳐진 정보를 리스트에 추가

		}

		showInsuranceInfoTable(); // 테이블 헤더 출력

		for (String info : customerInsuranceInfo) {
			// 각 열의 너비를 조정하여 사이즈 맞추기
			String[] columns = info.split(" ");
			System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
					centerAlign(columns[0], 15), centerAlign(columns[1], 15), centerAlign(columns[2], 15),
					centerAlign(columns[3], 15), centerAlign(columns[4], 10), centerAlign(columns[5], 12),
					centerAlign(columns[6], 12), centerAlign(columns[7], 10), centerAlign(columns[8], 10),
					centerAlign(columns[9], 12), centerAlign(columns[10], 11), centerAlign(columns[11], 10));

		}

		while (true) {
			System.out.print("\n\n• 버튼 선택 : ");
			boolean found = false;
			String cancelBtn = inputReader.readLine().trim();

			for (String info : customerInsuranceInfo) {
				String[] columns = info.split(" ");
				if (columns[11].equals(cancelBtn)) {
					found = true;
					boolean isCancelled = Boolean.parseBoolean(columns[4]);
					boolean isMatured = Boolean.parseBoolean(columns[7]);

					if (isCancelled && isMatured) {
						// A6. 해지된 계약의 해지버튼을 누른 경우
						System.out.println("[System] 이미 해지된 보험입니다.");
						return;
					} else if (!isCancelled && isMatured) {
						// A4. 만기된 보험의 해지 버튼을 누른 경우
						insuranceTermination("만기", customerName, customerPH, info, contractListImpl, inputReader);
						return;
					} else if (!isCancelled && !isMatured) {
						// A3. 만기되지 않은 보험의 해지 버튼을 누른 경우
						insuranceTermination("중도", customerName, customerPH, info, contractListImpl, inputReader);
						return;
					}
					return;
				}
			}

			if (!found) {
				System.out.println("[System] 올바르지 않은 버튼입니다. ");
				continue;
			}
		}

	}

	private static void showInsuranceInfoTable() {
		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");
		System.out.printf("\n%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
				centerAlign("보험명", 15), centerAlign("보험종류", 13), centerAlign("최대보장한도", 13), centerAlign("보험료", 13),
				centerAlign("해지여부", 10), centerAlign("가입일자", 8), centerAlign("만기일자", 8), centerAlign("만기여부", 10),
				centerAlign("부활여부", 6), centerAlign("보험기간", 6), centerAlign("납입주기", 6), centerAlign("해지버튼", 4));
		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________\n");
	}

	private static String centerAlign(String text, int width) {
		int totalWidth = width;
		int padding = (totalWidth - text.length()) / 2;
		return String.format("%-" + totalWidth + "s", " ".repeat(padding) + text + " ".repeat(padding));
	}

// END Of uc17) 가입된 보험을 조회한다.
// uc18) 보험을 중도 해지한다. , uc19) 만기 보험을 해지하다.  
	private static void insuranceTermination(String insuranceStatus, String customerName, String customerPH,
			String info, ContractListImpl contractListImpl, BufferedReader systemInput) throws IOException {
		System.out.printf("\n[ %s 해지 화면 ]", insuranceStatus);
		System.out.println(
				"\n_____________________________________________________________________________________________");
		System.out.printf("\n%-10s %-10s %-10s %-10s %-10s\n", centerAlign("이름", 10), centerAlign("전화번호", 10),
				centerAlign("해지 약관 안내", 13), centerAlign("해지 약관 동의", 13), centerAlign("해지하기 버튼", 10));
		System.out.println(
				"_____________________________________________________________________________________________\n");
		System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", centerAlign("[입력]", 10), centerAlign("[입력]", 10),
				centerAlign("[show]", 15), centerAlign("[Y/N]", 15), centerAlign("[Y/N]", 13));

		System.out.print("\n\n• 해지 약관 안내 보기 (Y/N) : ");
		String choice = systemInput.readLine().trim();
		// A1. 해지 약관 안내를 누른 경우
		if (choice.equals("Y"))
			showTerminationPolicyMessage();

		System.out.printf("\n• %s 해지 정보 입력", insuranceStatus);
		System.out.print("\n이름 : ");
		String inputName = systemInput.readLine().trim();
		System.out.print("전화번호 : ");
		String inputPH = systemInput.readLine().trim();
		System.out.print("해지 약관 동의 (Y/N) : ");
		String inputAgree = systemInput.readLine().trim();
		System.out.print("해지하기 (Y/N) : ");
		String inputCancel = systemInput.readLine().trim();

		if (inputName.isEmpty() || inputPH.isEmpty())
			System.out.println("\n[System] 입력하지 않은 항목이 있습니다. 모든 항목을 입력해주세요.");
		// A2. 잘못된 이름이나 전화번호를 입력한 경우
		if (!inputName.equals(customerName) || !inputPH.equals(customerPH))
			System.out.println("\n[System] 보험자 정보를 다시 확인해주십시오.");
		if (inputName.equals(customerName) && inputPH.equals(customerPH) && inputAgree.equals("N"))
			// A3. 해지 약관 동의를 체크하지 않은 경우
			System.out.println("\n[System] 해지 약관에 동의하지 않을 경우 해지가 불가능합니다.");
		if (inputName.equals(customerName) && inputPH.equals(customerPH) && inputAgree.equals("Y")
				&& inputCancel.equals("Y"))
			System.out.println("해지 환급금 요청 화면");
		if (inputName.equals(customerName) && inputPH.equals(customerPH) && inputCancel.equals("N"))
			System.out.println("[System] 해지 요청을 취소합니다.");

	}

	private static void showTerminationPolicyMessage() {
		// A1. 해지 약관 안내를 누른 경우

	}

	// End Of uc18) 보험을 중도 해지한다. , uc19) 만기 보험을 해지하다.
// uc26) 보험료 미납자를 조회하다. 	
	private static void showUnpaidCustomer(BufferedReader inputReader, ContractListImpl contractListImpl,
			CustomerListImpl customerListImpl, InsuranceListImpl insuranceList,
			FamilyHistoryListImpl familyHistoryListImpl, PaymentListImpl paymentListImpl,
			CompensationClaimListImpl compensationClaimList) throws Exception {

		System.out.println("\n[ 보험료 미납자 리스트 ]");
		System.out.println("_____________________________________________________");
		System.out.printf("\n%-10s %-10s %-10s %-10s \n", centerAlign("고객 ID", 10), centerAlign("이름", 9),
				centerAlign("성별", 9), centerAlign("세부정보 보기 버튼", 9));
		System.out.println("_____________________________________________________\n");

		ArrayList<String> unPaidCustomerId = paymentListImpl.retreiveUnpaidCustomerId();
		ArrayList<Customer> customerInfo = new ArrayList<Customer>();
		ArrayList<FamilyHistory> familyInfo = new ArrayList<FamilyHistory>();
		ArrayList<String> unpaidContractStatus = new ArrayList<>();
		ArrayList<String> unpaidInsuranceId = new ArrayList<>();
		ArrayList<String> unpaidInsuranceName = new ArrayList<>();

		int detailBtn = 0;

		for (String customerId : unPaidCustomerId) {
			Customer customer = customerListImpl.getCustomerByID(customerId);
			System.out.printf("%-10s %-10s %-10s %-10s\n", centerAlign(customer.getCustomerID(), 10),
					centerAlign(customer.getCustomerName(), 10), centerAlign(customer.getEGender().toString(), 10),
					centerAlign(Integer.toString(detailBtn), 12));
			detailBtn++;
		}

		System.out.print("\n\n• 버튼 선택 : ");
		String btnChoice = inputReader.readLine().trim();
		String selectedCustomerId = "";
		if (Integer.parseInt(btnChoice) >= 0 && Integer.parseInt(btnChoice) < unPaidCustomerId.size()) {
			selectedCustomerId = unPaidCustomerId.get(Integer.parseInt(btnChoice));
			customerInfo.add(customerListImpl.retrieveCustomer(selectedCustomerId));
			familyInfo
					.addAll(familyHistoryListImpl.getAllFamilyHistoryFromId(selectedCustomerId, familyHistoryListImpl));
			ArrayList<Payment> customerPayments = paymentListImpl.retreiveCustomerPayment(selectedCustomerId);
			for (Payment payment : customerPayments) {
				Contract contract = contractListImpl.getContractByInsuranceID(payment.getInsuranceID());
				if (contract != null && !payment.isWhetherPayment()) {
					unpaidContractStatus.add(contract.isMaturity() + " " + contract.isCancellation());
					unpaidInsuranceId.add(contract.getInsuranceID());
				}
			}
			for (String insurance : unpaidInsuranceId)
				unpaidInsuranceName.addAll(insuranceList.getInsuranceNameById(insurance));
		} else {
			System.out.println("[System] 올바르지 않은 버튼 선택입니다. ");
			return;
		}

		System.out.println("\n[ 고객 정보 화면 ]");
		System.out.println("___________________________________________________________________");
		System.out.printf("\n%-60s \n", centerAlign("고객 정보", 60));
		System.out.println("___________________________________________________________________\n");
		System.out.printf("%60s\n", customerInfo.get(0).toString());

		System.out.println("\n\n___________________________________________________________________\n");
		System.out.println("[      가족력 리스트      /     보유 계약 리스트(보험명, 만기 여부, 해지 여부)    ]");
		System.out.println("___________________________________________________________________\n");

		// 출력할 행의 개수는 네 개의 리스트 중 가장 큰 크기로 설정
		int maxIndex = Math.max(Math.max(familyInfo.size(), unpaidInsuranceName.size()), unpaidContractStatus.size());
		// 각각의 리스트를 인덱스별로 가져와서 출력
		for (int i = 0; i < maxIndex; i++) {
			String familyInfoLine = (i < familyInfo.size()) ? familyInfo.get(i).toString() : "";
			String insuranceNameLine = (i < unpaidInsuranceName.size()) ? unpaidInsuranceName.get(i) : "";
			String contractStatusLine = (i < unpaidContractStatus.size()) ? unpaidContractStatus.get(i) : "";
			String combined = insuranceNameLine + " " + contractStatusLine;
			System.out.printf("%20s %20s \n", centerAlign(familyInfoLine, 30), centerAlign(combined, 30));
		}

		System.out.print("\n\n• 대상자 제외 버튼 선택 (Y/N) : ");
		String choice = inputReader.readLine().trim();
		if (choice.equals("Y")) {
			// A1. 대상자에서 제외하고 싶은 경우
			customerListImpl.deleteUnpaidCustomer(customerListImpl.retrieveCustomer(selectedCustomerId));
			System.out.println("\n[System] 대상자에서 제외되었습니다.");
		}
	}

//End Of uc26) 보험료 미납자를 조회하다. 	
// uc36) 고객 납입 여부를 등록하다. 
	private static void showPaymentManagement(BufferedReader inputReader, CustomerListImpl customerListImpl,
			ContractListImpl contractListImpl, PaymentListImpl paymentListImpl, InsuranceListImpl insuranceListImpl)
			throws Exception {

		ArrayList<Contract> contractList = contractListImpl.retrieve();
		ArrayList<Customer> customerList = customerListImpl.retrieve();
		ArrayList<Insurance> insuranceList = insuranceListImpl.retrieve();
		ArrayList<String> customerId = new ArrayList<String>();
		ArrayList<String> customerInfo = new ArrayList<String>();
		ArrayList<String> insuranceId = new ArrayList<String>();
		ArrayList<String> insuranceName = new ArrayList<String>();
		ArrayList<Integer> premium = new ArrayList<Integer>();

		for (Contract contract : contractList) {
			customerId.add(contract.getCustomerID());
			insuranceId.add(contract.getInsuranceID());
			premium.add(contract.getPremium());
		}

		// customerId와 customerList를 매치하여 customerName 가져오기
		Map<String, String> customerNameMap = new HashMap<>();
		Map<String, String> customerPHMap = new HashMap<>();
		for (Customer customer : customerList) {
			customerNameMap.put(customer.getCustomerID(), customer.getCustomerName());
			customerPHMap.put(customer.getCustomerID(), customer.getPnumber());
		}

		for (String id : customerId) {
			String name = customerNameMap.get(id);
			String PH = customerPHMap.get(id);
			if (name != null && PH != null)
				customerInfo.add(name + " " + PH);
		}

		// insuranceId와 insuranceList를 매치하여 insuranceName 가져오기
		Map<String, String> insuranceNameMap = new HashMap<>();
		for (Insurance insurance : insuranceList) {
			insuranceNameMap.put(insurance.getInsuranceID(), insurance.getInsuranceName());
		}

		for (String id : insuranceId) {
			String name = insuranceNameMap.get(id);
			if (name != null)
				insuranceName.add(name);
		}

		// premium과 insuranceName 인덱스 별로 합치기
		ArrayList<String> premiumInsuranceList = new ArrayList<>();
		int maxIndex = Math.max(premium.size(), insuranceName.size());
		for (int i = 0; i < maxIndex; i++) {
			String premiumStr = (i < premium.size()) ? premium.get(i).toString() : "";
			String insuranceNameStr = (i < insuranceName.size()) ? insuranceName.get(i) : "";
			String combinedLine = insuranceNameStr + " " + premiumStr;
			premiumInsuranceList.add(combinedLine);
		}
		// customerInfo와 premiumInsuranceList 인덱스 별로 합치기
		ArrayList<String> customerPaymentList = new ArrayList<>();
		int maxIndex2 = Math.max(customerInfo.size(), premiumInsuranceList.size());
		for (int i = 0; i < maxIndex2; i++) {
			String customerInfoStr = (i < customerInfo.size()) ? customerInfo.get(i) : "";
			String premiumInsuranceStr = (i < premiumInsuranceList.size()) ? premiumInsuranceList.get(i) : "";
			String combinedLine = customerInfoStr + " " + premiumInsuranceStr;
			customerPaymentList.add(combinedLine);
		}

		System.out.println("\n[ 납입 관리 화면 ]");
		System.out.println("___________________________________________________________________\n");
		System.out.printf("%10s %10s %20s %10s\n", centerAlign("고객명", 10), centerAlign("고객 연락처", 10),
				centerAlign("보험명", 13), centerAlign("납입내역버튼", 8));
		System.out.println("___________________________________________________________________");
		int paymentBtn = 0;
		for (String paymentLine : customerPaymentList) {
			String[] paymentInfo = paymentLine.split(" ");
			String customerInfoStr = (paymentInfo.length > 0) ? paymentInfo[0] : "";
			String insuranceNameStr = (paymentInfo.length > 1) ? paymentInfo[1] : "";
			String premiumStr = (paymentInfo.length > 2) ? paymentInfo[2] : "";
			System.out.printf("%10s %10s %20s %10s\n", centerAlign(customerInfoStr, 10),
					centerAlign(insuranceNameStr, 10), centerAlign(premiumStr, 10), Integer.toString(paymentBtn));
			paymentBtn++;
		}

		System.out.print("\n\n• 납입내역버튼 선택 (버튼 번호 입력 또는 X 입력하여 종료): ");
		String choice = inputReader.readLine().trim();

		while (!choice.equalsIgnoreCase("X")) {
			int selectedBtn = Integer.parseInt(choice);
			if (selectedBtn >= 0 && selectedBtn < customerPaymentList.size()) {
				String selectedPaymentLine = customerPaymentList.get(selectedBtn);
				String[] selectedPaymentInfo = selectedPaymentLine.split(" ");
				String selectedCustomerName = selectedPaymentInfo[0];
				String selectedInsuranceName = selectedPaymentInfo[2];
				String selectedCustomerId = customerId.get(selectedBtn);
				String selectedInsuranceId = insuranceId.get(selectedBtn);
				String paymentStatus = "";
				ArrayList<String> selectedPaymentList = paymentListImpl.retreiveDateStatusById(selectedCustomerId,
						selectedInsuranceId);
				ArrayList<String> selectedPremium = contractListImpl.retreivePremiumById(selectedCustomerId,
						selectedInsuranceId);

				System.out.println("\n[ 납입 내역 ]");
				System.out.println(
						"____________________________________________________________________________________________________\n");
				System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", centerAlign("고객명", 15),
						centerAlign("보험명", 15), centerAlign("보험료", 15), centerAlign("납입일", 15), centerAlign("납입여부", 15),
						centerAlign("납입상태변경버튼", 15));
				System.out.println(
						"____________________________________________________________________________________________________");

				int paymentStatusBtn = 0;
				for (int i = 0; i < Math.min(selectedPaymentList.size(), selectedPremium.size()); i++) {
					String paymentLine = selectedPaymentList.get(i);
					String[] paymentInfo = paymentLine.split(" ");
					String paymentDate = (paymentInfo.length > 0) ? paymentInfo[0] : "";
					paymentStatus = (paymentInfo.length > 1) ? paymentInfo[1] : "";

					System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", centerAlign(selectedCustomerName, 15),
							centerAlign(selectedInsuranceName, 15), centerAlign(selectedPremium.get(i), 15),
							centerAlign(paymentDate, 20), centerAlign(paymentStatus, 12),
							centerAlign(Integer.toString(paymentStatusBtn), 15));
					paymentStatusBtn++;
				}

				System.out.print("\n\n• 납입상태변경버튼 선택 (버튼 번호 입력 또는 Q 입력하여 종료): ");
				String paymentUpdateBtn = inputReader.readLine().trim();
				if (paymentUpdateBtn.matches("\\d+")) {
					int selectedPaymentUpdateBtn = Integer.parseInt(paymentUpdateBtn);
					if (selectedPaymentUpdateBtn >= 0 && selectedPaymentUpdateBtn < Math.min(selectedPaymentList.size(),
							selectedPremium.size())) {
						selectedCustomerId = customerId.get(selectedPaymentUpdateBtn);
						selectedInsuranceId = insuranceId.get(selectedPaymentUpdateBtn);

						if (contractListImpl.updateCancellation(selectedCustomerId, selectedInsuranceId)
								&& paymentListImpl.updateCancellation(selectedCustomerId, selectedInsuranceId)) {
							System.out.println("[System] 납입 상태를 변경했습니다.");

						} else {
							System.out.println("[System] 납입 상태 변경 실패");
						}
					}
				}

				return;

			} else {
				System.out.println("[System] 유효하지 않은 버튼 번호입니다.");
				// 다시 선택
				System.out.print("\n\n• 납입내역버튼 선택 (버튼 번호 입력 또는 Q 입력하여 종료): ");
				choice = inputReader.readLine().trim();
			}
		}
	}

// End Of uc36) 고객 납입 여부를 등록하다. 
//uc39) 보험료를 납부한다.  	
	private static void showInsurancePayment(BufferedReader inputReader, CustomerListImpl customerListImpl,
			ContractListImpl contractListImpl, PaymentListImpl paymentListImpl, InsuranceListImpl insuranceList)
			throws Exception {
		System.out.println("\n[ 보험자 정보 확인 ]");
		System.out.print("이름 : ");
		String customerName = inputReader.readLine().trim();
		System.out.print("전화번호 : ");
		String customerPH = inputReader.readLine().trim();

		// A1. 입력된 정보의 사용자가 없는 경우
		String customerId = customerListImpl.getCustomerIdFromNameAndPH(customerName, customerPH);
		if (customerId == null) {
			System.out.println("\n[System] 등록되지 않은 사용자입니다.");
			return;
		}

		ArrayList<String> customerInsuranceIds = contractListImpl.getInsuranceIdFromCustomerId(customerId);
		ArrayList<Insurance> customerInsuranceInfo = new ArrayList<Insurance>();
		ArrayList<Payment> customerPaymentInfo = paymentListImpl.retreiveCustomerPayment(customerId);

		for (int i = 0; i < customerInsuranceIds.size(); i++) {
			customerInsuranceInfo.add(insuranceList.getInsurancebyId(customerInsuranceIds.get(i)));
		}

		// 결과 출력을 위한 테이블 헤더 출력
		System.out.println(
				"_____________________________________________________________________________________________________________");
		System.out.println(String.format("%10s%10s%10s%10s%10s%10s%10s%10s", "보험명", "보험구분", "납부여부", "보험료", "미납기간",
				"미납금", "선택 버튼", "납부방법변경버튼"));
		System.out.println(
				"_____________________________________________________________________________________________________________");

		// 결과 출력을 위한 변수 초기화
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();

		for (Insurance insurance : customerInsuranceInfo) {
			String insuranceId = insurance.getInsuranceID();
			boolean isPaid = true;
			int unpaidDays = 0;
			int unpaidAmount = 0;

			// 납부 여부 확인 및 미납 기간, 미납금 계산
			for (Payment payment : customerPaymentInfo) {
				if (payment.getInsuranceID().equals(insuranceId)) {
					LocalDate paymentDate = LocalDate.parse(payment.getStringDateOfPayment()); // LocalDate로 파싱
					Date paymentDateConverted = java.sql.Date.valueOf(paymentDate); // Date로 변환
					int daysDiff = (int) ((today.getTime() - paymentDateConverted.getTime()) / (1000 * 60 * 60 * 24));

					if (isPaid) {
						isPaid = payment.isWhetherPayment();
					}

					if (!isPaid && (unpaidDays == 0 || paymentDateConverted.after(today))) {
						unpaidDays = daysDiff;
					}

					if (!payment.isWhetherPayment()) {
						unpaidAmount += insurance.getBasicPremium();
					}
				}
			}

			// 선택 버튼 및 납부 방법 변경 버튼 표시
			String selectionButton = String.format("%02d", index);
			String paymentButton = String.valueOf(10 + index);

			// 결과 출력
			System.out.println(String.format("%10s%10s%13s%13s%8s%14s%8s%10s", insurance.getInsuranceName(),
					insurance.getType(), isPaid, insurance.getBasicPremium(), unpaidDays, unpaidAmount, selectionButton,
					paymentButton));

			index++;
		}

		// 사용자에게 선택 버튼 입력 받기
		System.out.print("\n\n• 선택 버튼 입력 : ");
		String selectionInput = inputReader.readLine().trim();

		if (selectionInput.startsWith("0")) {
			// 보험 선택 후 납입 (완납/일부납)
			insurancePremiumPayment(inputReader, paymentListImpl, customerInsuranceInfo, customerPaymentInfo,
					selectionInput);
		} else if (selectionInput.startsWith("1")) {
			// A2. 고객이 납부 방법 변경 버튼을 클릭할 경우
			paymentMethodChange(selectionInput);
		} else {
			System.out.println("[System] 잘못된 버튼 선택입니다.");
		}

	}

	private static void insurancePremiumPayment(BufferedReader inputReader, PaymentListImpl paymentListImpl,
			ArrayList<Insurance> customerInsuranceInfo, ArrayList<Payment> customerPaymentInfo, String selectionInput)
			throws IOException {
		// 버튼 값이 0으로 시작하는 경우 선택한 인덱스에 해당하는 정보 출력
		int selectedIndex = Integer.parseInt(selectionInput);

		if (selectedIndex >= 0 && selectedIndex < customerInsuranceInfo.size()) {
			// 선택한 인덱스에 해당하는 정보 출력
			System.out.println("\n_______________________________________________________________");
			System.out.println(String.format("%10s%10s%10s%10s", "보험명", "보험구분", "보험료", "납부하기"));
			System.out.println("_______________________________________________________________");

			Insurance selectedInsurance = customerInsuranceInfo.get(selectedIndex);
			String insuranceName = selectedInsurance.getInsuranceName();
			String insuranceType = selectedInsurance.getType();
			int basicPremium = selectedInsurance.getBasicPremium();

			System.out.println(
					String.format("%10s%10s%10s%10s", insuranceName, insuranceType, basicPremium, selectionInput));

			String paymentButtonInput = inputReader.readLine().trim();
			// 입력한 납부하기 버튼과 선택한 버튼이 일치할 경우 납부 방법 출력
			if (paymentButtonInput.equals(selectionInput)) {
				System.out.println("\n_______________________________________________");
				System.out.println(String.format("%25s", "납부 방법 선택"));
				System.out.println("_______________________________________________");
				System.out.println(String.format("%25s", "1. 월보험료(완납)"));
				System.out.println(String.format("%25s", "2. 월보험료(일부납)"));

				System.out.print("\n\n• 납부 방법 버튼 선택 (1/2) : ");
				String paymentMethodInput = inputReader.readLine().trim();

				if (paymentMethodInput.equals("1")) {
					// 월보험료(완납) 버튼을 클릭한 경우
					System.out.println("[System] 월보험료(완납) 납부가 완료되었습니다.");
					// 납입 여부 업데이트
					for (Payment payment : customerPaymentInfo) {
						if (payment.getInsuranceID().equals(selectedInsurance.getInsuranceID())) {
							payment.setWhetherPayment(true);
						}
					}
					paymentListImpl.update();
				} else if (paymentMethodInput.equals("2")) {
					// A3. 월보험료(일부납) 버튼을 클릭할 경우
					System.out.print("\n• 일부 납부할 금액을 입력해 주세요: ");
					String partialPaymentInput = inputReader.readLine().trim();
					int partialPayment = Integer.parseInt(partialPaymentInput);

					if (partialPayment <= 0 || partialPayment > selectedInsurance.getBasicPremium()) {
						System.out.println("[System] 잘못된 금액입니다.");
					} else {
						int remainingAmount = selectedInsurance.getBasicPremium() - partialPayment;
						System.out.println(
								"[System] " + partialPayment + "원 납부가 완료되었습니다. 남은 보험료는 " + remainingAmount + "원입니다.");

						// 보험료 업데이트
						selectedInsurance.setBasicPremium(remainingAmount);

						// 납입 여부 업데이트 (보험료가 0이 된 경우에만 true로 변경)
						if (remainingAmount == 0) {
							for (Payment payment : customerPaymentInfo) {
								if (payment.getInsuranceID().equals(selectedInsurance.getInsuranceID())) {
									payment.setWhetherPayment(true);
								}
							}
						}
						paymentListImpl.update();
					}
				}
			}
		} else {
			System.out.println("[System] 잘못된 버튼 선택입니다.");
		}
	}
// End Of ux39) 보험료를 납부한다. 

// uc40) 보험료 납부 방식을 변경한다.
	private static void paymentMethodChange(String selectionInput) {
		// TODO Auto-generated method stub

	}

}
