package UI;

import CompensationClaim.*;
import Contract.Contract;
import Contract.ContractListImpl;
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
import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.Terms;
import Insurance.TermsListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) throws Exception {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
		CompensationClaimListImpl compensationClaimList = new CompensationClaimListImpl("data/CompensationClaim.txt");
		SurveyListImpl surveyList = new SurveyListImpl("data/Survey.txt");
		CarAccidentListImpl carAccidentList = new CarAccidentListImpl("data/CarAccident.txt");
		InsuranceListImpl insuranceList = new InsuranceListImpl("data/Insurance.txt");
		TermsListImpl termsListImpl = new TermsListImpl("data/Terms.txt");
		ContractListImpl contractListImpl = new ContractListImpl("data/Contract.txt");
	      CounselApplicationListImpl counselApplicationListImpl = new CounselApplicationListImpl("data/CounselList.txt");
	      CustomerListImpl customerListImpl = new CustomerListImpl("data/Customer.txt");
	      FamilyHistoryListImpl familyHistoryListImpl = new FamilyHistoryListImpl("data/FamilyHistory.txt");
		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		while (!userChoice.equals("x")) {
			showMenu();
			userChoice = inputReader.readLine().trim();
			switch (userChoice) {
			case "1":
				createCompensationClaim(insuranceList, compensationClaimList, carAccidentList, inputReader);
				break;
			case "2":
				retrieveCompensationClaim(insuranceList, compensationClaimList, carAccidentList, surveyList, inputReader);
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
			case "6":
	                        showCustomerList(customerListImpl, inputReader, 
	            		familyHistoryListImpl, contractListImpl, insuranceList);
	            break;
	         case "7":
	        	 showCouncel(inputReader, counselApplicationListImpl);
	        	break;
	         case "8":
	        	 showManageConsultation(inputReader, counselApplicationListImpl,
		        			customerListImpl);
	        	break;
			default:
				System.out.println("Invalid Choice !!!");

			}
		}
	}
	private static void showManageConsultation(BufferedReader inputReader, 
			   CounselApplicationListImpl counselApplicationListImpl, CustomerListImpl customerListImpl) 
					   throws IOException, ParseException {
		if(selectConsultationCase(inputReader)) { // 상담 정보 조회
			String id = null;
			do {
				System.out.print("고객 아이디 : ");
				id = inputReader.readLine().trim();
			}while(!checkInputId(id));
	   
			System.out.print("날짜 : "); // yyyymmdd
			String dateStr = inputReader.readLine().trim();
			int year = Integer.parseInt(dateStr.substring(0, 4));
			int month = Integer.parseInt(dateStr.substring(4, 6));
			int day = Integer.parseInt(dateStr.substring(6, 8));
			LocalDate date = LocalDate.of(year, month, day);
			CounselApplication counselApplication = 
					getConsuleInfo(Integer.parseInt(id), date, counselApplicationListImpl);
	   
			if(counselApplication == null) {
				System.out.println("일치하는 data가 하나도 없습니다.");
				return;
			}
			Customer customer = getCustomerFromCouncels(counselApplication, customerListImpl);
			showCounselSchedule(counselApplication, customer);
			if(!getCustomerDetails(inputReader)) return;
			if(counselApplication.getCounsel().getContent() == null) {
				System.out.println("상담을 아직 진행하지 않아 내용이 없습니다.");
				return;
			}
			showDetailcounselInfo(counselApplication, customer);

			if(selectRetOrDel(inputReader)) // 내용 조회
				showContentInfo(customer, counselApplication.getCounsel());
			else { // 내용 삭제
				counselApplicationListImpl.delete(counselApplication.getCounselID());
				System.out.println("삭제되었습니다");
			}
		}else { // 상담 정보 등록
			if(selectScheduleOrContent(inputReader)) {
				System.out.println("\n[상담 일정 등록]");
				CounselApplication newCounsel = getNewCounsel(inputReader);
				if(counselApplicationListImpl.add(newCounsel))
					System.out.println("상담 일정을 등록하였습니다.");
				else System.out.println("등록에 실패했습니다.");
			}else {
				System.out.println("\n상담 내용 등록");
				String input = null;
				boolean isInputed = false;
				do {
					input = inputCustomerId(inputReader);
					isInputed = input.length() != 0;
					if(!isInputed) System.out.println("조건을 최소 하나라도 기입했는지 체크해주세요.");
				}while(!isInputed);
				Customer customer = customerListImpl.retrieveCustomer(Integer.parseInt(input));
				if(customer == null) {
					System.out.println("관련 상담 일정이 하나도 존재하지 않습니다.");
					return;
				}
				List<CounselApplication> selectedCouncels = 
						getCounselList(customer, counselApplicationListImpl);
				showCounseList(customer, selectedCouncels);
				if(!selectContentAcquire(inputReader)) return;
				String content = inputContent(inputReader); // 상담내용
				CounselApplication councelApp = null;
				if(selectedCouncels.size() == 1) // Basic
					councelApp = selectedCouncels.get(0);
				else if(selectedCouncels.size() > 2) // A3.
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
		int id = Integer.parseInt(inputReader.readLine().trim());
		for(CounselApplication counselApp : selectedCouncels) {
			if(counselApp.getCounselID() == id) return counselApp;
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
			if(!allInput) System.out.println("항목을 모두 입력해주세요.");
		}while(!allInput);
		
		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(4, 6));
		int day = Integer.parseInt(dateStr.substring(6, 8));
		LocalDate date = LocalDate.of(year, month, day);
		newCounsel.setDateOfFirst(date);
		newCounsel.setDateOfSecond(date);
		newCounsel.getCounsel().setManagerName(manager);
		newCounsel.setCustomerID(Integer.parseInt(id));
		newCounsel.setRequirement(req);
		
		return newCounsel;
	}
	private static boolean isAllInput(String dateStr, String manager, String id, String req) {
		if(dateStr.length() == 0) return false;
		if(manager.length() == 0) return false;
		if(id.length() == 0) return false;
		if(req.length() == 0) return false;
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
	private static List<CounselApplication> getCounselList(Customer customer, 
			   CounselApplicationListImpl counselApplicationListImpl) {
		List<CounselApplication> selectedCouncels = new ArrayList<CounselApplication>();
		ArrayList<CounselApplication> counsels = counselApplicationListImpl.retrieve();
		for(CounselApplication counselApplication : counsels) {
			if(counselApplication.getCustomerID() == customer.getCustomerID())
				selectedCouncels.add(counselApplication);
		}
		return selectedCouncels;
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
		for(CounselApplication counselApplication : selectedCouncels) {
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
	private static Customer getCustomerFromCouncels(
			   CounselApplication counselApplication, CustomerListImpl customerListImpl) {
		Customer selectedCustomer = null;
		for(Customer customer : customerListImpl.retrieve()) {
			if(customer.getCustomerID() == counselApplication.getCustomerID())
				selectedCustomer = customer;
		}
		return selectedCustomer;
	}
	private static CounselApplication getConsuleInfo(int id, LocalDate date, 
			   CounselApplicationListImpl counselApplicationListImpl) {
		   ArrayList<CounselApplication> counselApplications = counselApplicationListImpl.retrieve();
		   for(CounselApplication counselApplication : counselApplications) {
			   if(counselApplication.getCustomerID() != id) continue;
			   if(counselApplication.getDateOfFirst().equals(date)) return counselApplication;
			   if(counselApplication.getDateOfSecond().equals(date)) return counselApplication;
		   }
		   return null;
	   }
	private static boolean checkInputId(String id) {
		   if(id.equals("")) {
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
		   if(counselApplication == null) return;
		   counselApplicationListImpl.add(counselApplication); // 상담 내역 추가
		   System.out.println("상담 신청이 완료되었습니다.\n신청하신 상담일자에 전화드릴 예정입니다.");
	   }
	 private static CounselApplication getNewCouncel(BufferedReader inputReader) throws IOException, ParseException {
		   System.out.println("상담 신청 입력");
		   System.out.print("고객 ID : ");
		   int id = Integer.parseInt(inputReader.readLine().trim());
		   System.out.print("1지망 일시 : ");
		   String dateStr = inputReader.readLine().trim();
		   int year = Integer.parseInt(dateStr.substring(0, 4));
		   int month = Integer.parseInt(dateStr.substring(4, 6));
		   int day = Integer.parseInt(dateStr.substring(6, 8));
		   LocalDate date1 = LocalDate.of(year, month, day);
		   System.out.print("2지망 일시 : ");
		   dateStr = inputReader.readLine().trim();
		   year = Integer.parseInt(dateStr.substring(0, 4));
		   month = Integer.parseInt(dateStr.substring(4, 6));
		   day = Integer.parseInt(dateStr.substring(6, 8));
		   LocalDate date2 = LocalDate.of(year, month, day);
		   System.out.println("제출하겠습니까?");
		   System.out.print("1. 예, 2. 아니오 : ");
		   if(!inputReader.readLine().trim().equals("1")) return null;
		   CounselApplication counselApplication = new CounselApplication();
		   counselApplication.setCustomerID(id);
		   counselApplication.setDateOfFirst(date1);
		   counselApplication.setDateOfSecond(date2);
		   return counselApplication;
	 }
	 private static void showfMaturityList(BufferedReader inputReader, 
			   ContractListImpl contractListImpl, 
			   CustomerListImpl customerListImpl, 
			   FamilyHistoryListImpl familyHistoryListImpl,
			   InsuranceListImpl insuranceList) throws Exception { // 만기계약 대상자 조회
		   TargetType targetType = showKeepContract(inputReader); // 계약유지대상자 조회화면 출력 및 대상자 입력 - Enum 반환
		   ArrayList<Customer> customerList = null;
		   
		   if(targetType == TargetType.RESURRECT_CANDIDATES) { // 1. 부활 대상자
			   customerList = customerListImpl.getResurrectCandidates(contractListImpl); // 부활 대상자들 받아옴
			   showResurrectContracts(customerList); // 부활 대상자 출력
			   boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
			   if (!isShowDetail) return;
			   Customer customer = getCustomerFromResurrect(customerListImpl, inputReader); // 부활 대상자에서 고객 조회
			   if (customer == null) {
				   System.out.println("입력하신 고객 정보가 없습니다.");
				   return;
			   }
			   showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			   // 리스트에서 지운다 -> 전체 계약 목록
			   if(!selectCustomerDelete(inputReader)) return;
			   customerListImpl.deleteResurrectCandidatesCustomer(customer);
			   contractListImpl.setResurrectFromCustomer(customer);
			   System.out.println("대상자에서 제외되었습니다.");
		   }else if(targetType == TargetType.EXPIRED_CONTRACTS) { // 2. 만기계약자
			   customerList = customerListImpl.getExpiredContracts(contractListImpl); // 만기계약 대상자들 받아옴
			   showExpiredContracts(customerList); // 만기계약 대상자 출력
			   boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
			   if (!isShowDetail) return;
			   Customer customer = getCustomerFromExpired(customerListImpl, inputReader); // 만기계약 대상자에서 고객 조회
			   if (customer == null) {
				   System.out.println("입력하신 고객 정보가 없습니다.");
				   return;
			   }
			   showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			   // 리스트에서 지운다 -> 전체 계약 목록
			   if(!selectCustomerDelete(inputReader)) return;
			   customerListImpl.deleteExpiredCustomer(customer);
			   contractListImpl.setMaturityFromCustomer(customer);
			   System.out.println("대상자에서 제외되었습니다.");
		   }else if(targetType == TargetType.UNPAID_CUSTOMERS) { // 3. 보험료 미납자
			   customerList = customerListImpl.getUnpaidContracts(contractListImpl); // 미납 대상자들 받아옴
			   showUnPaidContracts(customerList); // 미납 대상자 출력
			   boolean isShowDetail = getCustomerDetails(inputReader); // 세부정보 보기 출력
			   if (!isShowDetail) return;
			   Customer customer = getCustomerFromUnpaid(customerListImpl, inputReader); // 미납 대상자에서 고객 조회
			   if (customer == null) {
				   System.out.println("입력하신 고객 정보가 없습니다.");
				   return;
			   }
			   showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			   if(!selectCustomerDelete(inputReader)) return;
			   customerListImpl.deleteUnpaidCustomer(customer);
			   contractListImpl.setWheaterPaymentFromCustomer(customer);
			   System.out.println("대상자에서 제외되었습니다.");
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
		 for(Customer customer : customerList) {
			 System.out.println("아이디: " + customer.getCustomerID() + 
					 ", 이름: " + customer.getCustomerName() + 
					 ", 성별: " + customer.getEGender().getGenderStr());
		 }
	 }
	 private static void showExpiredContracts(ArrayList<Customer> customerList) {
		 System.out.println("만기 계약자 리스트");
		 for(Customer customer : customerList) {
			 System.out.println("아이디: " + customer.getCustomerID() + 
					 ", 이름: " + customer.getCustomerName() + 
					 ", 성별: " + customer.getEGender().getGenderStr());
		 }
	 }
	 private static void showUnPaidContracts(ArrayList<Customer> customerList) {
		 System.out.println("미납 대상자 리스트");
		 for(Customer customer : customerList) {
			 System.out.println("아이디: " + customer.getCustomerID() + 
					 ", 이름: " + customer.getCustomerName() + 
					 ", 성별: " + customer.getEGender().getGenderStr());
		 }
	 }
	 private static Customer getCustomerFromUnpaid(CustomerListImpl customerListImpl, 
			 BufferedReader inputReader) throws NumberFormatException, IOException {
		 System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");	
		 System.out.print("아이디 : ");
		 int id = Integer.parseInt(inputReader.readLine().trim());
		 return customerListImpl.retrieveCustomerFromUnpaid(id);
	 }
	 private static Customer getCustomerFromExpired(CustomerListImpl customerListImpl, 
			   BufferedReader inputReader) throws NumberFormatException, IOException { 
		 System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");	
		 System.out.print("아이디 : ");
		 int id = Integer.parseInt(inputReader.readLine().trim());
		 return customerListImpl.retrieveCustomerFromExpired(id);
	 }
	 private static Customer getCustomerFromResurrect(CustomerListImpl customerListImpl, BufferedReader inputReader) throws NumberFormatException, IOException {
		 System.out.println("세부정보를 확인할 고객의 아이디를 입력하세요");	
		 System.out.print("아이디 : ");
		 int id = Integer.parseInt(inputReader.readLine().trim());
		 return customerListImpl.retrieveCustomerFromResurrect(id);
	 }
	 private static void showCustomerList(CustomerListImpl customerListImpl, BufferedReader inputReader,
			FamilyHistoryListImpl familyHistoryListImpl, ContractListImpl contractListImpl,
			InsuranceListImpl insuranceList)throws Exception  {
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
				 if(userChoice.equals("")) 
					 System.out.println("조건을 기입했는지 체크해주세요.");
			 }while(userChoice.equals(""));
			 int id = Integer.parseInt(userChoice);
			 Customer customer = customerListImpl.retrieveCustomer(id); // 아이디에 따른 고객정보 받아옴
			 if (customer == null) {
				   System.out.println("조건에 맞는 고객 정보가 하나도 없습니다.\n조건을 확인해주세요.");
				   return;
			 }
			 showCustomerInfo(customer); // id 이름 생일 성별
			 boolean isRetrieveDetail = getCustomerDetails(inputReader); // 세부정보 읽을지 확인
			 if (isRetrieveDetail) showCustomerDetailInfos(customer, familyHistoryListImpl, contractListImpl, insuranceList); // 고객 세부정보 출력
			 else return;
			 if (inputDelOrUpd(inputReader)) {
				 customerListImpl.delete(id); // 삭제 or 업뎃 여부 입력 후 해당 고객 삭제
				 System.out.println("고객 정보가 삭제되었습니다.");
			 }
			 else customerListImpl.update(getUpdatedCustomer(inputReader), id); // 고객 정보 입력 받아서 해당 고객 업데이트
			 break;
		   case "2":
			   System.out.println("[계약 유지 대상자 조회]");
			   showfMaturityList(inputReader, contractListImpl, customerListImpl,
	        			familyHistoryListImpl, insuranceList);
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
		   upCustomer.setCustomerID(Integer.parseInt(inputReader.readLine().trim()));
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
			ContractListImpl contractListImpl, InsuranceListImpl insuranceList)throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("\n[고객 세부 정보]");
		 showCustomerInfo(customer); // 고객 ID, 이름, 생일, 성별
		 showCustomerInfoDetail(customer); // 전번 주소 직업
		 showFamilyHistory(getFamilyHistoryFromId(customer.getCustomerID(), familyHistoryListImpl));
		 // 가족력 리스트(질환명,가족관계)
		 List<Contract> contracts = getContractFromId(customer.getCustomerID(), contractListImpl);
		 List<Insurance> insurances = getInsuranceFromId(contracts, insuranceList);
		 showInsuranceList(contracts, insurances);
		 // 보유 계약 리스트(보험명/만기 여부/해지여부/납입 여부)
	}
	 private static void showInsuranceList(List<Contract> contracts, List<Insurance> insurances) {
		   for(Contract contract : contracts) {
			   Insurance selectedInsurance = null;
			   for(Insurance insurance : insurances) {
				   selectedInsurance = insurance;
			   }
			   System.out.println("보험명 : " + selectedInsurance.getInsuranceName());
			   System.out.println("만기 여부 : " + contract.isMaturity());
			   System.out.println("해지여부 : " + contract.isCancellation());
			   System.out.println("부활 여부 : " + contract.isResurrection() + "\n");
		   }
		   
	   }
		
	private static FamilyHistory getFamilyHistoryFromId(int id, FamilyHistoryListImpl familyHistoryListImpl) { // 고객 아이디에 맞는 가족력 반환
		   ArrayList<FamilyHistory> familyHistories = familyHistoryListImpl.retrieve();
		   for(FamilyHistory familyHistory : familyHistories) {
			   if (familyHistory.getCustomerID() == id) {
				   return familyHistory;
			   }
		   }
		   return null;
	   }
	 private static List<Insurance> getInsuranceFromId(List<Contract> contracts, InsuranceListImpl insuranceList) {
		   ArrayList<Insurance> selectedInsurances = new ArrayList<Insurance>();
		   for(Contract contract : contracts) { // 계약된 보험 아이디가 여기 있음
			   Insurance insurance = insuranceList.retrieveInsuranceDetail(contract.getInsuranceID());
			   selectedInsurances.add(insurance);
		   }
		   return selectedInsurances;
	   }
	private static List<Contract> getContractFromId(int id, ContractListImpl contractListImpl) throws Exception {
		   List<Contract> selectedContracts = new ArrayList<Contract>();
		   ArrayList<Contract> contracts = contractListImpl.retrieve();
		   for(Contract contract : contracts) {
			   if (contract.getCustomerID() == id) {
				   selectedContracts.add(contract);
			   }
		   }
		   // 여기서 한 사람당 계약 정보가 둘 이상일 때 첫 번째 계약만 값을 받아옴
		   return selectedContracts;
	   }
	 private static void showFamilyHistory(FamilyHistory familyHistory) {
		   System.out.println("질환명 : " + familyHistory.getDiseaseName());
		   System.out.println("가족관계 : " + familyHistory.getRelationship());
	   }
	private static boolean getCustomerDetails(BufferedReader inputReader)throws IOException {
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
	private static String inputCustomerId(BufferedReader inputReader)throws IOException  {
		System.out.print("고객 아이디를 입력해 주세요 : ");
		return inputReader.readLine().trim();
	}
	private static void createCompensationClaim(InsuranceListImpl insuranceList, CompensationClaimListImpl compensationClaimList,
												CarAccidentListImpl carAccidentList, BufferedReader inputReader)
			throws IOException, ParseException {
		CompensationClaim compensationClaim = new CompensationClaim();
		System.out.println("****************** Compensation Claim *******************");
		System.out.println("개인정보 확인을 위해 고객ID를 입력해주세요");
		compensationClaim.setCustomerID(inputReader.readLine().trim());
		/* A3. 존재하지 않는 고객ID를 입력한 경우 */
		System.out.println("고객님이 가입하신 보험 정보는 아래와 같습니다.\n" + "보험금을 청구할 보험ID를 입력하세요.");
//		고객이 가입한 보험리스트 조회 method (in contract)
		compensationClaim.setInsuranceID(inputReader.readLine().trim());
		compensationClaim.setCCID(compensationClaim.getInsuranceID()+compensationClaim.getCustomerID());
		System.out.println(compensationClaim.getInsuranceID());
//		Insurance selectedInsurance = insuranceList.retrieveInsuranceDetail(compensationClaim.getInsuranceID());
		//왜 오류가나지..
			System.out.println("보험종류: " + /*selectedInsurance.getType() + */"보험명: "/* + selectedInsurance.getType() + "\n"*/
				+ "보험금 청구를 위한 서류 목록(하나의 압축파일로 업로드)\n" + "1. 보험금 청구서" + "  2. 개인(신용)정보처리동의서" + "  3. 수익자 신분증(앞면) 사본"
				+ "  4. 수익자 통장 사본\n" + "보험종류가 자동차보험인 경우, 5. 교통사고 사실 확인원  6. 교통사고신속처리협의서 추가 제출");
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
		if (compensationClaimList.createCompensationClaim(compensationClaim)) {
			if (insuranceList.getInsuranceTypebyId(compensationClaim.getInsuranceID()) == "Car")
				createCarAccident(compensationClaim, carAccidentList, inputReader);
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		} else
			System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}
	private static void retrieveCompensationClaim(InsuranceListImpl insuranceList, CompensationClaimListImpl compensationClaimList, CarAccidentListImpl carAccidentList, SurveyListImpl surveyList, BufferedReader inputReader) throws IOException {
		System.out.println("****************** Compensation Claim List *******************");
		System.out.println("보험ID 고객ID 청구ID 접수자명 접수자전화번호 보험계약자와의 관계 구비서류파일경로 은행 계좌번호 예금주명");
		showList(compensationClaimList.retrieve());
		// 원래는 보험금 청구 ID(보험ID+고객ID), 접수자명, 보험명만 출력해야하나 TUI에서는 list전체를 보여줌
		System.out.println("1. 손해사정");
		System.out.println("2. 결정보험금 지급 요청");
		String userChoice = "";
		userChoice = inputReader.readLine().trim();
		switch (userChoice) {
		case "1":
			createSurvey(compensationClaimList, surveyList, inputReader);
			break;
		case "2":
//			survey.requestBanking();
			break;
		}
	}

	private static void createSurvey(CompensationClaimListImpl compensationClaimList, SurveyListImpl surveyList, BufferedReader inputReader) throws IOException {
		Survey survey = new Survey();
		System.out.println("****************** Survey *******************");
		System.out.println("손해사정할 청구ID를 입력하세요: ");
		survey.setCCID(inputReader.readLine().trim());
//		if(survey.getCCID() == compensationClaimList.match()getCCID())
//			System.out.println(carAccidentList);
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
		} else
			System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}



	private static void createCarAccident(CompensationClaim compensationClaim, CarAccidentListImpl carAccidentList, BufferedReader inputReader)
			throws IOException, ParseException {
		CarAccident carAccident = new CarAccident();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("****************** Compensation Claim - Car Accident*******************");
		System.out.print("사고유형: ");
		carAccident.setType(inputReader.readLine().trim());
		System.out.print("사고일시 (형식 예시-2023년 01월 01일 17시 30분): ");
		carAccident.setDateTime(LocalDateTime.parse(inputReader.readLine().trim()));
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
		if (carAccidentList.createCarAccident(carAccident))
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		else
			System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
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
		terms.setTermsID(inputReader.readLine().trim());
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
				String insuranceName = insuranceListImpl.requestAuthorization(insuranceID);
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
					if (insuranceListImpl.updateAuthorization(insuranceID, true))
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
				insurance = insuranceListImpl.retrieveInsuranceDetail(insuranceID);
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
		insurance.setInsuranceID(inputReader.readLine().trim());
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
						+ "11. 배당 여부(False/True), 12. 보장 내용(약관ID, 콤마로 구분해주세요), 13. 주의사항, 14. 보험 면책 기간");		choice = inputReader.readLine().trim();
		System.out.println("수정할 내용을 입력하세요");
		String content = inputReader.readLine().trim();
		switch (choice) {
		case ("1"):
			insurance.setInsuranceID(content);
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
			if (insuranceListImpl.deleteInsurance(insuranceID))
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

	private static void showMenu() {
		System.out.println("****************** Initial Menu *******************");
		System.out.println("1. 보험금 청구");
		System.out.println("2. 보험금 청구 내역(직원용)");
		System.out.println("3. 보험 조회");
		System.out.println("4. 보험 설계(직원용)");
		System.out.println("5. 계약 관리");
		System.out.println("6. 고객 정보 조회");
		System.out.println("7. 고객 상담 신청");
		System.out.println("8. 상담 정보 관리");
		System.out.println("x. Exit");
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
				System.out.print("\n[System] 해지할 계약 리스트의 번호를 입력하세요. ");
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

		// 이미 해지된 보험인 경우
		if (getCustomerMaturity) {
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
					showContractList(contractListImpl, systemInput); // showContract 메서드 호출					showContractList(contractListImpl, systemInput); // showContract �޼��� ȣ��
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
				showContractList(contractListImpl, systemInput); // showContract 메서드 호출ȣ��
			}
		} else {
			System.out.println("\n[System] 잘못된 계약 리스트 번호입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출istImpl, systemInput); // showContract �޼��� ȣ��
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
		boolean getCustomerCancellation = contractListImpl.getCustomerCancellation(customerID, contractIndex);
		// 이미 해지된 보험인 경우
		if (getCustomerCancellation) {
			System.out.println("\n[System] 이미 중도 해지된 보헙입니다.");
			showContractList(contractListImpl, systemInput); // showContract 메서드 호출
		}
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
}
