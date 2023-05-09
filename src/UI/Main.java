package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
import java.util.ArrayList;

import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.Terms;
import Insurance.TermsListImpl;

public class Main {
	
	public static void main(String[] args) throws IOException{
		
		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in)); 		
		InsuranceListImpl insuranceListImpl = new InsuranceListImpl("Insurance.txt");
		TermsListImpl termsListImpl = new TermsListImpl("Terms.txt");
			while(!userChoice.equals("x")) {
				showMenu();				
				userChoice = inputReader.readLine().trim();
			switch(userChoice) {
			case "1":
				showOnSaleInsurance(insuranceListImpl, inputReader, "Customer");
				break;
			case "2":
				designInsurance(insuranceListImpl,termsListImpl, inputReader);			
				//break;		
			default:
				System.out.println("Invalid Choice !!!");}}}

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
		else System.out.println("Invalid Choice !!!");}
	
	private static void showList(ArrayList<?> dataList) {
		String list ="";
		for(int i=0; i<dataList.size(); i++) {
			list += dataList.get(i)+ "\n";}
		System.out.println(list);}

	private static void showMenu() {
		System.out.println("****************** MENU *******************");
		System.out.println("1. 보험 조회");
		System.out.println("2. 보험 설계");}
}

