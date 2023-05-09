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
			System.out.println("1. ������ ���� ��ȸ 2. �� ���� ���� 3. ��� ���� 4. �Ǹ����� ���� ��ȸ x. ����");
			System.out.println("���� : ");
			choice = inputReader.readLine().trim();
			if(choice.equals("1")) showDesignedInsurance(insuranceListImpl, inputReader);
			else if(choice.equals("2")) createInsurance(insuranceListImpl, inputReader); 
			else if(choice.equals("3")) termsManagement(insuranceListImpl, termsListImpl, inputReader);
			else if(choice.equals("4")) showOnSaleInsurance(insuranceListImpl, inputReader, "Manager");
			else if(!choice.equals("x")) System.out.println("Invalid Choice !!!");}}

	private static void termsManagement(InsuranceListImpl insuranceListImpl, TermsListImpl termsListImpl,BufferedReader inputReader) throws IOException {
		System.out.println("****************** Terms Management MENU *******************");
		System.out.println("1. ��� ��ȸ 2. �� ��� ���");
		System.out.println("���� : ");
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
		System.out.println("��� ID : "); terms.setTermsID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("�����  : "); terms.setTermsName(inputReader.readLine().trim()); 
		System.out.println("���޺���� �������(,�� �̾ �ۼ�) : "); terms.setCalculatedMoneyMethod(inputReader.readLine().trim());
		System.out.println("���峻�� : "); terms.setTermsContent(inputReader.readLine().trim());				
		if(termsListImpl.createTerms(terms)) System.out.println("��ϵǾ����ϴ�.");
		else System.out.println("��ϵ��� �ʾҽ��ϴ�.");		}

	private static void showDesignedInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		showList(insuranceListImpl.getUnregisteredInsuranceList()); 
		System.out.println("****************** UnregisteredInsurance MENU *******************");
		System.out.println("1. ������ ���� ����/�����ϱ� 2. �Ǹ� ������ ���� ����ϱ� 3. ������������ �ΰ� ��û x. ����");
		System.out.println("���� : ");
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
		System.out.println("�ΰ��� ��û�� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		System.out.println("���� : ");
		String insuranceID = inputReader.readLine().trim();
		if(!insuranceID.equals("x")) {
			if(!insuranceID.equals(null)) {
			String insuranceName = insuranceListImpl.requestAuthorization(Integer.valueOf(insuranceID));
			if(!insuranceName.equals(null)) System.out.println(insuranceName + "�ΰ� ��û�� �Ϸ�Ǿ����ϴ�. �ΰ� �Ϸ���� �� ���� �ҿ�� �� �ֽ��ϴ�.");
			else System.out.println("��û�� �����߽��ϴ�.");} 
			else System.out.println("�ƹ��͵� �Էµ��� �ʾҽ��ϴ�.");	}
		}

	private static void registerInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		System.out.println("�Ǹ������� ����� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		System.out.println("���� : ");
		String insuranceID = inputReader.readLine().trim();
		if(!insuranceID.equals("x")) {
			if(!insuranceID.equals(null)) {
				System.out.println("������������ ���� �ΰ����� ������ Ȯ���մϱ�?");
				System.out.println("Y/N : ");
				String choice = inputReader.readLine().trim();
				if(choice.equals("Y")) { 
					if(insuranceListImpl.updateAuthorization(Integer.valueOf(insuranceID), true)) System.out.println("�ű� ���� ����� �Ϸ�Ǿ����ϴ�.");
					else System.out.println("��û�� �����߽��ϴ�.");}} 
			else System.out.println("�ƹ��͵� �Էµ��� �ʾҽ��ϴ�.");		}}

	private static void showOnSaleInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader, String who) throws IOException{
		System.out.println("****************** Insurance MENU *******************");
		String insuranceType = "";
		while(!insuranceType.equals("x")) {
		System.out.println("��ȸ�Ͻ� ���� ������ �Է��ϼ���");
		System.out.println("1. ��ü, 2. �ڵ�������, 3. ����/��������, 4. �Ϻ���, 5. ȭ�纸�� x. ����");
		System.out.println("���� : ");
		insuranceType = inputReader.readLine().trim();	
		if (insuranceType.equals("1")) showList(insuranceListImpl.getOnSaleInsuranceList()); 		
		else if (insuranceType.equals("2")) showList(insuranceListImpl.retrieveInsurance("Car")); 		
		else if (insuranceType.equals("3")) showList(insuranceListImpl.retrieveInsurance("Disease")); 
		else if (insuranceType.equals("4")) showList(insuranceListImpl.retrieveInsurance("Cancer")); 
		else if (insuranceType.equals("5")) showList(insuranceListImpl.retrieveInsurance("Fire")); 
		else if(!insuranceType.equals("x")) System.out.println("Invalid Choice !!!");
		}
		if(who.equals("Customer")) { 
			System.out.println("���� ��û �޴��� �̵��Ͻðڽ��ϱ�? (Y/N)");
			String choice = inputReader.readLine().trim();	
			if(choice.equals("Y")) {
			//���谡�Խ�û�޼ҵ�();
			} 
		}
		else { System.out.println("���� �޴��� ���ư��ϴ�.");}
		}	

	private static void updateInsuranceDetail(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException {
		System.out.println("����/������ ���輭�� ���� ID�� �Է��ϼ���. ������ x�� �Է��ϼ���");
		String insuranceID = "";
		Insurance insurance;
		if(!insuranceID.equals("x")) {
		if(!insuranceID.equals(null)) {
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
					System.out.println("Invalid Choice !!");}}}}

	private static void createInsurance(InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException{
		Insurance insurance = new Insurance();
		System.out.println("--------Insurance Info---------");
		System.out.println("���� ID : "); insurance.setInsuranceID(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� �̸� : "); insurance.setInsuranceName(inputReader.readLine().trim()); 
		System.out.println("���� ���� : "); insurance.setType(inputReader.readLine().trim());
		System.out.println("���谡�Աݾ� : "); insurance.setMaxCompensation(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� �Ⱓ : "); insurance.setPeriodOfInsurance(inputReader.readLine().trim());
		System.out.println("���� �ֱ� : "); insurance.setPaymentCycle(inputReader.readLine().trim());
		System.out.println("���� �Ⱓ : "); insurance.setPaymentPeriod(inputReader.readLine().trim());
		System.out.println("���� ���� : "); insurance.setAgeOfTarget(inputReader.readLine().trim());
		System.out.println("�⺻ ����� : "); insurance.setBasicPremium(Integer.valueOf(inputReader.readLine().trim()));
		System.out.println("���� : "); insurance.setRate(inputReader.readLine().trim());
		System.out.println("��� ����(False/True) : "); insurance.setDistributionStatus(Boolean.parseBoolean(inputReader.readLine().trim()));
		while(true) {
		System.out.println("���� ����(���ID, �޸��� �������ּ���) : "); 
		if(insurance.setTermsIDList(inputReader.readLine().trim())==false) System.out.println("���ID�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���");
		else break;}
		System.out.println("���ǻ��� : "); insurance.setPrecaution(inputReader.readLine().trim());
		System.out.println("���� ��å �Ⱓ : "); insurance.setInsuranceClausePeriod(Integer.valueOf(inputReader.readLine().trim()));
		if(insuranceListImpl.createInsurance(insurance)) System.out.println("��ϵǾ����ϴ�.");
		else System.out.println("��ϵ��� �ʾҽ��ϴ�.");
	}
	
	private static void updateInsurance(Insurance insurance, InsuranceListImpl insuranceListImpl, BufferedReader inputReader) throws IOException{
		String choice  = "";
		
		System.out.println("������ ������ �����ϼ���.");
		System.out.println("1. ���� ID, 2. ���� �̸�, 3. ���� ����, 4. ���谡�Աݾ�, 5. ���� �Ⱓ, 6. ���� �ֱ�, 7. ���� �Ⱓ, 8. ���� ����, 9. �⺻ �����, 10. ����, "
				+ "11. ��� ����(False/True), 12. ���� ����(���ID, �޸��� �������ּ���), 13. ���ǻ���, 14. ���� ��å �Ⱓ");
		choice  = inputReader.readLine().trim();
		System.out.println("������ ������ �Է��ϼ���");
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
			if(insurance.setTermsIDList(content)==false) System.out.println("��� ID�� �������� �ʾ� ������ ������� �ʾҽ��ϴ�."); break;
		case("13"): insurance.setPrecaution(content); break;
		case("14"): insurance.setInsuranceClausePeriod(Integer.valueOf(content)); break;
		default: System.out.println("Invalid Choice !!!");
		}
		
		if(insuranceListImpl.updateinsurance(insurance)) System.out.println("������ ����Ǿ����ϴ�.");
		else System.out.println("������ �����߽��ϴ�.");}	
	
	private static void deleteInsurance(InsuranceListImpl insuranceListImpl, String insuranceID, BufferedReader inputReader) throws IOException{
		System.out.println("����ID : " + insuranceID + "�� �����Ͻðڽ��ϱ�? (Y/N)");
		String choice  = inputReader.readLine().trim();
		if(choice.equals("Y")) 
			if(insuranceListImpl.deleteInsurance(Integer.valueOf(insuranceID))) System.out.println("�����Ǿ����ϴ�.");
			else System.out.println("������ �����Ͽ����ϴ�.");
		else if(choice.equals("N")) System.out.println("������ ��ҵǾ����ϴ�.");
		else System.out.println("Invalid Choice !!!");}
	
	private static void showList(ArrayList<?> dataList) {
		String list ="";
		for(int i=0; i<dataList.size(); i++) {
			list += dataList.get(i)+ "\n";}
		System.out.println(list);}

	private static void showMenu() {
		System.out.println("****************** MENU *******************");
		System.out.println("1. ���� ��ȸ");
		System.out.println("2. ���� ����");}
}

