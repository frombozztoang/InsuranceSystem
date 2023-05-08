package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

import Contract.ContractListImpl;
import Contract.Payment;
import Contract.PaymentList;
import Contract.PaymentListImpl;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader SystemInput = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			printMenu();
			String firstSelect = SystemInput.readLine().trim();
			String secondSelect = "";
			String thirdSelect = "";

			switch (firstSelect) {

			case "1":
				System.out.println("[계약 리스트]");
//				showList(new ContractListImpl("Database/Contract.txt").retrieve());
				System.out.println("1-1. 납입내역버튼");
				secondSelect = SystemInput.readLine().trim();
				break;
			case "X":
				System.out.println("종료");
				return;
			default:
				System.out.println("유효하지 않은 선택 값입니다.");
			}

			switch (secondSelect) {
			case "1-1":
				System.out.println("[납입 리스트]");
				showList(new PaymentListImpl("Database/Payment").retrieve());
				System.out.println("a. 납입상태변경버튼");
				System.out.println("0. 뒤로 가기");
				thirdSelect = SystemInput.readLine().trim();
				break;
			}
			
			switch (thirdSelect) {
			case "a":
				System.out.println("훙");
				break;
			default:
				System.out.println("유효하지 않은 선택 값입니다.");
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

	private static void printMenu() {
		System.out.println("-------- MENU --------");
		System.out.println("1. 납입 관리");
		System.out.println("X. Exit");
		System.out.println("----------------------");
	}

}
