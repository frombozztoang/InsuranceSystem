package UI;

import CompensationClaim.CarAccident;
import CompensationClaim.CarAccidentListImpl;
import CompensationClaim.CompensationClaim;
import CompensationClaim.CompensationClaimListImpl;
import CompensationClaim.Survey;
import Contract.ContractList;
import Insurance.Insurance;
import Insurance.InsuranceListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

	private static CompensationClaimListImpl compensationClaimList;
	private static CarAccidentListImpl carAccidentList;
	private static ContractList contract;
	private static Survey survey;

	public static void main(String[] args) throws IOException, ParseException {
		compensationClaimList = new CompensationClaimListImpl("data/CompensationClaim.txt");
		InsuranceListImpl insuranceList = new InsuranceListImpl("data/Insurance.txt");

		String userChoice = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		showMenu();
		userChoice = inputReader.readLine().trim();
		switch(userChoice) {
			case "1":
				createCompensationClaim(insuranceList, compensationClaimList, inputReader);
				break;
			case "2":
				retrieveCompensationClaim(compensationClaimList, inputReader);
				break;
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
		switch(userChoice) {
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
		compensationClaim.setCCID(Integer.parseInt(Integer.toString(compensationClaim.getInsuranceID())+Integer.toString(compensationClaim.getCustomerID())));
		// id로 insurance 객체 찾아오기
		System.out.println("보험종류: {insurance.type}\n" +
				"보험명: {insurance.insuranceName}\n" +
				"보험금 청구를 위한 서류 목록(하나의 압축파일로 업로드)\n" +
				"1. 보험금 청구서" +
				"  2. 개인(신용)정보처리동의서" +
				"  3. 수익자 신분증(앞면) 사본" +
				"  4. 수익자 통장 사본\n" +
				"보험종류가 자동차보험인 경우, 5. 교통사고 사실 확인원  6. 교통사고신속처리협의서 추가 제출");
		System.out.print("접수자명: "); compensationClaim.setReceptionistName(inputReader.readLine().trim());
		System.out.print("접수자 전화번호: "); compensationClaim.setReceptionistPNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("보험계약자와의 관계(예:부): "); compensationClaim.setRelationshipOfContractor(inputReader.readLine().trim());
		System.out.print("구비서류 업로드란: "); compensationClaim.setDocumentFilePath(inputReader.readLine().trim());
		System.out.print("은행: "); compensationClaim.setBank(inputReader.readLine().trim());
		System.out.print("계좌번호: "); compensationClaim.setAccountNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("예금주명: "); compensationClaim.setAccountHolderName(inputReader.readLine().trim());
		if(compensationClaimList.createCompensationClaim(compensationClaim)){
			if(insuranceList.getInsuranceTypebyId(compensationClaim.getInsuranceID()) == "Car")
				createCarAccident(compensationClaim, inputReader);
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		}
		else System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}

	private static void createCarAccident(CompensationClaim compensationClaim, BufferedReader inputReader) throws IOException, ParseException {
		CarAccident carAccident = new CarAccident();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("****************** Compensation Claim - Car Accident*******************");
		System.out.print("사고유형: "); carAccident.setType(inputReader.readLine().trim());
		System.out.print("사고날짜: "); carAccident.setDate(formatter.parse(inputReader.readLine().trim()));
		System.out.print("사고시간: "); carAccident.setTime(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("사고장소: "); carAccident.setPlace(inputReader.readLine().trim());
		System.out.print("차량번호: "); carAccident.setCarNumber(inputReader.readLine().trim());
		System.out.print("운전자 성명: "); carAccident.setDriverName(inputReader.readLine().trim());
		System.out.print("면허번호: "); carAccident.setLicenseNumber(Integer.parseInt(inputReader.readLine().trim()));
		System.out.print("사고 내용: "); carAccident.setAccidentDetail(inputReader.readLine().trim());
		if(carAccidentList.createCarAccident(carAccident))
			System.out.println(compensationClaim.getCCID() + "신청이 완료되었습니다. 심사 결과에 따라 보상이 제한되거나 거절될 수 있습니다.");
		else System.out.println("신청에 실패하였습니다. 다시 시도해주십시오.");
	}


	private static void showList(ArrayList<?> dataList) {
		String list ="";
		for(int i=0; i<dataList.size(); i++) {
			list += dataList.get(i)+ "\n";}
		System.out.println(list);}

	private static void showMenu() {
		System.out.println("****************** Initial Menu *******************");
		System.out.println("1. 보험금 청구");
		System.out.println("2. 보험금 청구 내역");
		System.out.println("x. Exit");
	}
}
