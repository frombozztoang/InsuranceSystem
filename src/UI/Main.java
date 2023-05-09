package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

import Contract.Contract;
import Contract.ContractListImpl;
import Contract.Payment;
import Contract.PaymentList;
import Contract.PaymentListImpl;

public class Main {

	public static void main(String[] args) throws Exception {
		ContractListImpl contractListImpl = new ContractListImpl("Database/Contract.txt");

		BufferedReader SystemInput = new BufferedReader(new InputStreamReader(System.in));
		String Choice = "";
		while (!Choice.equals("X")) {
			printMenu();
			Choice = SystemInput.readLine().trim();

			switch (Choice) {
			case "1":
				showContractList(contractListImpl, SystemInput);
				break;

			}

		}

	}

	private static void showContractList(ContractListImpl contractListImpl, BufferedReader systemInput)
			throws Exception {

		System.out.println("-------- MENU --------");
		System.out.println("1. 납입내역버튼");
		System.out.println("X. 뒤로가기");
		System.out.println("----------------------");

		System.out.println("[계약 리스트]");
		showList(contractListImpl.retrieve());

		String Choice = systemInput.readLine().trim();
		while (!Choice.equals("X")) {
			switch (Choice) {
			case "1":
				showPaymentList(new PaymentListImpl("Database/Payment.txt"), systemInput);
				break;

			}

		}

	}

	private static void showPaymentList(PaymentListImpl paymentListImpl, BufferedReader systemInput) throws Exception {
		System.out.println("-------- MENU --------");
		System.out.println("1. 납입상태변경버튼");
		System.out.println("X. 뒤로가기");
		System.out.println("----------------------");

		System.out.println("[납입 리스트]");
		showList(paymentListImpl.retrieve());

		String Choice = systemInput.readLine().trim();
		while (!Choice.equals("X")) {
			switch (Choice) {
			case "1":
				updatePayment(paymentListImpl, systemInput);
				return;
			}

		}
	}

	private static void showList(ArrayList<?> arrayList) {
		String list = "";
		for (int i = 0; i < arrayList.size(); i++) {
			list += arrayList.get(i) + "\n";
		}
		System.out.println(list);

	}

	private static void updatePayment(PaymentListImpl paymentListImpl, BufferedReader systemInput)
			throws IOException, ParseException {
		System.out.println("계약의 납입 상태를 변경할 고객의 ID와 보험의 ID를 입력하세요.");
		System.out.print("고객 ID : ");
		String customerID = systemInput.readLine().trim();
		System.out.print("보험 ID : ");
		String insuranceID = systemInput.readLine().trim();
		

		Boolean match = paymentListImpl.updateWheterPayment(Integer.parseInt(customerID), Integer.parseInt(insuranceID));

		if (match) {
			System.out.println("납입 상태를 변경했습니다.");
			return;
		} else {
			System.out.println("잘못된 정보입니다.");
		}

	}

	private static void printMenu() {
		System.out.println("-------- MENU --------");
		System.out.println("1. 납입 관리");
		System.out.println("X. Exit");
		System.out.println("----------------------");
	}

}
