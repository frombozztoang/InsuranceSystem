package UI;

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

	public static void main(String[] args) throws IOException, ParseException {
		compensationClaimList = new CompensationClaimListImpl("data/CompensationClaim.txt");
		InsuranceListImpl insuranceList = new InsuranceListImpl("data/Insurance.txt");
		TermsListImpl termsListImpl = new TermsListImpl("data/Terms.txt");

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
					//break;
				default:
					System.out.println("Invalid Choice !!!");
			}
		}
	}

	private static void retrieveCompensationClaim(CompensationClaimListImpl compensationClaimList, BufferedReader inputReader) throws IOException {
		System.out.println("****************** Compensation Claim List *******************");
		System.out.println("보험ID 고객ID 청구ID 접수자명 접수자전화번호 보험계약자와의 관계 구비서류파일경로 은행 계좌번호 예금주명");
		//원래는 보험금 청구 ID(보험ID+고객ID), 접수자명, 보험명만 출력해야하나 TUI에서는 list전체를 보여줌
		System.out.println("1. 보험금 청구 내용 보기(미구현)");
		System.out.println("2. 손해사정");
		System.out.println("3. 결정보험금 지급 요청");
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
				survey.requestBanking();
		}
	}

	private static void createSurvey(BufferedReader inputReader) throws IOException {
	}

	private static void createCompensationClaim(InsuranceListImpl insuranceList, CompensationClaimListImpl compensationClaimList, BufferedReader inputReader) throws IOException, ParseException {
		CompensationClaim compensationClaim = new CompensationClaim();
		System.out.println("****************** Compensation Claim *******************");
		System.out.println("개인정보 확인을 위해 고객ID를 입력해주세요");
		compensationClaim.setCustomerID(Integer.parseInt(inputReader.readLine().trim()));
		//고객 ID가 없을 경우 Alter 추가해야함
		System.out.println("고객님이 가입하신 보험 정보는 아래와 같습니다.\n" +
				"보험금을 청구할 보험ID를 입력하세요.");
//		고객이 가입한 보험리스트 조회 method (in contract)
		compensationClaim.setInsuranceID(Integer.parseInt(inputReader.readLine().trim()));
		compensationClaim.setCCID(Integer.parseInt(Integer.toString(compensationClaim.getInsuranceID()) + Integer.toString(compensationClaim.getCustomerID())));
		// id로 insurance 객체 찾아오기
		System.out.println("보험종류: {insurance.type}\n" +
				"보험명: {insurance.insuranceName}\n" +
				"보험금 청구를 위한 서류 목록(하나의 압축파일로 업로드)\n" +
				"1. 보험금 청구서" +
				"  2. 개인(신용)정보처리동의서" +
				"  3. 수익자 신분증(앞면) 사본" +
				"  4. 수익자 통장 사본\n" +
				"보험종류가 자동차보험인 경우, 5. 교통사고 사실 확인원  6. 교통사고신속처리협의서 추가 제출");
		System.out.print("접수자명: ");
		compensationClaim.setReceptionistName(inputReader.readLine().trim());
		System.out.print("접수자 전화번호: ");
		compensationClaim.setReceptionistPNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("보험계약자와의 관계(예:부): ");
		compensationClaim.setRelationshipOfContractor(inputReader.readLine().trim());
		System.out.print("구비서류 업로드란: ");
		compensationClaim.setDocumentFilePath(inputReader.readLine().trim());
		System.out.print("은행: ");
		compensationClaim.setBank(inputReader.readLine().trim());
		System.out.print("계좌번호: ");
		compensationClaim.setAccountNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("예금주명: ");
		compensationClaim.setAccountHolderName(inputReader.readLine().trim());
		if (compensationClaimList.createCompensationClaim(compensationClaim)) {
			if (insuranceList.getInsuranceTypebyId(compensationClaim.getInsuranceID()) == "Car")
				createCarAccident(compensationClaim, inputReader);
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		} else System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}

	private static void createCarAccident(CompensationClaim compensationClaim, BufferedReader inputReader) throws IOException, ParseException {
		CarAccident carAccident = new CarAccident();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("****************** Compensation Claim - Car Accident*******************");
		System.out.print("사고유형: ");
		carAccident.setType(inputReader.readLine().trim());
		System.out.print("사고날짜: ");
		carAccident.setDate(formatter.parse(inputReader.readLine().trim()));
		System.out.print("사고시간: ");
		carAccident.setTime(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("사고장소: ");
		carAccident.setPlace(inputReader.readLine().trim());
		System.out.print("차량번호: ");
		carAccident.setCarNumber(inputReader.readLine().trim());
		System.out.print("운전자 성명: ");
		carAccident.setDriverName(inputReader.readLine().trim());
		System.out.print("면허번호: ");
		carAccident.setLicenseNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("사고 내용: ");
		carAccident.setAccidentDetail(inputReader.readLine().trim());
		if (carAccidentList.createCarAccident(carAccident))
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		else System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}
	private static void designInsurance(InsuranceListImpl insuranceListImpl,TermsListImpl termsListImpl,BufferedReader inputReader) throws IOException {
		String choice = "";
		while(!choice.equals("x")) {
			System.out.println("****************** Insurance DESIGN MENU *******************");
			System.out.println("1. 설계한 보험 조회 2. 새 보험 설계 3. 약관 관리 4. 판매중인 보험 조회 x. 종료");
			System.out.println("선택 : ");
			choice = inputReader.readLine().trim();
			if(choice.equals("1")) showDesignedInsurance(insuranceListImpl, inputReader);
			else if(choice.equals("2")) createInsurance(insuranceListImpl, inputReader);
			else if(choice.equals("3")) termsManagement(insuranceListImpl, termsListImpl, inputReader);
			else if(choice.equals("4")) showOnSaleInsurance(insuranceListImpl, inputReader, "Manager");
			else if(!choice.equals("x")) System.out.println("Invalid Choice !!!");}}

	private static void termsManagement(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,BufferedReader inputReader) throws IOException {
		System.out.println("****************** Terms Management MENU *******************");
		System.out.println("1. 약관 조회 2. 새 약관 등록");
		System.out.println("선택 : ");
		String choice = inputReader.readLine().trim();
		switch(choice) {
			case "1":
				showList(termsListImpl.retrieveAllTerms());
				break;
			case "2":
				createTerms(termsListImpl, inputReader);
				break;
			default:
				System.out.println("Invalid Choice !!!");}}

	private static void createTerms(TermsListImpl termsListImpl, BufferedReader inputReader) throws NumberFormatException, IOException {
		Terms terms = new Terms();
		System.out.println("--------Terms Info---------");
		System.out.println("약관 ID : "); terms.setTermsID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("약관명  : "); terms.setTermsName(inputReader.readLine().trim());
		System.out.println("지급보험금 산정방식(,로 이어서 작성) : "); terms.setCalculatedMoneyMethod(inputReader.readLine().trim());
		System.out.println("보장내용 : "); terms.setTermsContent(inputReader.readLine().trim());
		if(termsListImpl.createTerms(terms)) System.out.println("등록되었습니다.");
		else System.out.println("등록되지 않았습니다.");		}

	private static void showDesignedInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		showList(insuranceListImpl.getUnregisteredInsuranceList());
		System.out.println("****************** UnregisteredInsurance MENU *******************");
		System.out.println("1. 선택한 보험 수정/삭제하기 2. 판매 중으로 보험 등록하기 3. 금융감독원에 인가 요청 x. 종료");
		System.out.println("선택 : ");
		String choice = inputReader.readLine().trim();
		switch(choice) {
			case "1" :
				updateInsuranceDetail(insuranceListImpl, inputReader);
				break;
			case "2" :
				registerInsurance(insuranceListImpl, inputReader);
				break;
			case "3" :
				requestAuthorization(insuranceListImpl, inputReader);
				break;
			case "x" : break;
			default :
				System.out.println("Invalid Choice !!!");	}}

	private static void requestAuthorization(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		System.out.println("인가를 요청할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		System.out.println("선택 : ");
		String insuranceID = inputReader.readLine().trim();
		if(!insuranceID.equals("x")) {
			if(!insuranceID.equals(null)) {
				String insuranceName = insuranceListImpl.requestAuthorization(Integer.valueOf(insuranceID));
				if(!insuranceName.equals(null)) System.out.println(insuranceName + "인가 신청이 완료되었습니다. 인가 완료까지 수 일이 소요될 수 있습니다.");
				else System.out.println("신청에 실패했습니다.");}
			else System.out.println("아무것도 입력되지 않았습니다.");	}
	}

	private static void registerInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		System.out.println("판매중으로 등록할 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		System.out.println("선택 : ");
		String insuranceID = inputReader.readLine().trim();
		if(!insuranceID.equals("x")) {
			if(!insuranceID.equals(null)) {
				System.out.println("금융감독원에 의해 인가받은 보험이 확실합니까?");
				System.out.println("Y/N : ");
				String choice = inputReader.readLine().trim();
				if(choice.equals("Y")) {
					if(insuranceListImpl.updateAuthorization(Integer.valueOf(insuranceID), true)) System.out.println("신규 보험 등록이 완료되었습니다.");
					else System.out.println("신청에 실패했습니다.");}}
			else System.out.println("아무것도 입력되지 않았습니다.");		}}

	private static void showOnSaleInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader, String who) throws IOException{
		System.out.println("****************** Insurance MENU *******************");
		String insuranceType = "";
		while(!insuranceType.equals("x")) {
			System.out.println("조회하실 보험 종류를 입력하세요");
			System.out.println("1. 전체, 2. 자동차보험, 3. 상해/질병보험, 4. 암보험, 5. 화재보험 x. 종료");
			System.out.println("선택 : ");
			insuranceType = inputReader.readLine().trim();
			if (insuranceType.equals("1")) showList(insuranceListImpl.getOnSaleInsuranceList());
			else if (insuranceType.equals("2")) showList(insuranceListImpl.retrieveInsurance("Car"));
			else if (insuranceType.equals("3")) showList(insuranceListImpl.retrieveInsurance("Disease"));
			else if (insuranceType.equals("4")) showList(insuranceListImpl.retrieveInsurance("Cancer"));
			else if (insuranceType.equals("5")) showList(insuranceListImpl.retrieveInsurance("Fire"));
			else if(!insuranceType.equals("x")) System.out.println("Invalid Choice !!!");
		}
		if(who.equals("Customer")) {
			System.out.println("보험 신청 메뉴로 이동하시겠습니까? (Y/N)");
			String choice = inputReader.readLine().trim();
			if(choice.equals("Y")) {
				//보험가입신청메소드();
			}
		}
		else { System.out.println("설계 메뉴로 돌아갑니다.");}
	}

	private static void updateInsuranceDetail(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		System.out.println("수정/삭제할 설계서의 보험 ID를 입력하세요. 없으면 x를 입력하세요");
		String insuranceID = "";
		Insurance insurance;
		if(!insuranceID.equals("x")) {
			if(!insuranceID.equals(null)) {
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
						System.out.println("Invalid Choice !!");}}}}

	private static void createInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException{
		Insurance insurance = new Insurance();
		System.out.println("--------Insurance Info---------");
		System.out.println("보험 ID : "); insurance.setInsuranceID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("보험 이름 : "); insurance.setInsuranceName(inputReader.readLine().trim());
		System.out.println("보험 종류 : "); insurance.setType(inputReader.readLine().trim());
		System.out.println("보험가입금액 : "); insurance.setMaxCompensation(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("보험 기간 : "); insurance.setPeriodOfInsurance(inputReader.readLine().trim());
		System.out.println("납입 주기 : "); insurance.setPaymentCycle(inputReader.readLine().trim());
		System.out.println("납입 기간 : "); insurance.setPaymentPeriod(inputReader.readLine().trim());
		System.out.println("가입 나이 : "); insurance.setAgeOfTarget(inputReader.readLine().trim());
		System.out.println("기본 보험료 : "); insurance.setBasicPremium(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("요율 : "); insurance.setRate(inputReader.readLine().trim());
		System.out.println("배당 여부(False/True) : "); insurance.setDistributionStatus(Boolean.parseBoolean(inputReader.readLine().trim()));
		while(true) {
			System.out.println("보장 내용(약관ID, 콤마로 구분해주세요) : ");
			if(insurance.setTermsIDList(inputReader.readLine().trim())==false) System.out.println("약관ID가 존재하지 않습니다. 다시 입력해주세요");
			else break;}
		System.out.println("주의사항 : "); insurance.setPrecaution(inputReader.readLine().trim());
		System.out.println("보험 면책 기간 : "); insurance.setInsuranceClausePeriod(Integer.valueOf(inputReader.readLine().trim()));
		if(insuranceListImpl.createInsurance(insurance)) System.out.println("등록되었습니다.");
		else System.out.println("등록되지 않았습니다.");
	}

	private static void updateInsurance(Insurance insurance, InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException{
		String choice  = "";

		System.out.println("수정할 정보를 선택하세요.");
		System.out.println("1. 보험 ID, 2. 보험 이름, 3. 보험 종류, 4. 보험가입금액, 5. 보험 기간, 6. 납입 주기, 7. 납입 기간, 8. 가입 나이, 9. 기본 보험료, 10. 요율, "
				+ "11. 배당 여부(False/True), 12. 보장 내용(약관ID, 콤마로 구분해주세요), 13. 주의사항, 14. 보험 면책 기간");
		choice  = inputReader.readLine().trim();
		System.out.println("수정할 내용을 입력하세요");
		String content  = inputReader.readLine().trim();
		switch (choice) {
			case ("1") : insurance.setInsuranceID(Integer.valueOf(content));break;
			case ("2") : insurance.setInsuranceName(content); break;
			case ("3") : insurance.setType(content);break;
			case ("4") : insurance.setMaxCompensation(Integer.valueOf(content)); break;
			case ("5") : insurance.setPeriodOfInsurance(content); break;
			case ("6") : insurance.setPaymentCycle(content); break;
			case ("7") : insurance.setPaymentPeriod(content); break;
			case ("8") : insurance.setAgeOfTarget(content); break;
			case ("9") : insurance.setBasicPremium(Integer.valueOf(content));break;
			case ("10") : insurance.setRate(content); break;
			case ("11") : insurance.setDistributionStatus(Boolean.parseBoolean(content)); break;
			case ("12"):
				if(insurance.setTermsIDList(content)==false) System.out.println("약관 ID가 존재하지 않아 정보가 저장되지 않았습니다."); break;
			case("13"): insurance.setPrecaution(content); break;
			case("14"): insurance.setInsuranceClausePeriod(Integer.valueOf(content)); break;
			default: System.out.println("Invalid Choice !!!");
		}

		if(insuranceListImpl.updateinsurance(insurance)) System.out.println("수정이 저장되었습니다.");
		else System.out.println("수정에 실패했습니다.");}

	private static void deleteInsurance(InsuranceListImpl insuranceListImpl, String insuranceID, BufferedReader inputReader) throws IOException{
		System.out.println("보험ID : " + insuranceID + "를 삭제하시겠습니까? (Y/N)");
		String choice  = inputReader.readLine().trim();
		if(choice.equals("Y"))
			if(insuranceListImpl.deleteInsurance(Integer.valueOf(insuranceID))) System.out.println("삭제되었습니다.");
			else System.out.println("삭제에 실패하였습니다.");
		else if(choice.equals("N")) System.out.println("삭제가 취소되었습니다.");
		else System.out.println("Invalid Choice !!!");
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
		System.out.println("x. Exit");
	}
}

