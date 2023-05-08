package Contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.StringTokenizer;

public class Payment {

//	Payment class는 계약에 대한 납입 정보를 나타내며, 납입 여부를 갱신하는 메소드를 제공한다.

	private int customerID; // 고객 ID
	private int insuranceID; // 보험ID
	private Date dateOfPayment; // 납입일
	private boolean whetherPayment; // 납입 여부
	private String stringDateOfPayment;

	public Payment(String inputPaymentInfo) throws ParseException {
		StringTokenizer stringTokenizer = new StringTokenizer(inputPaymentInfo);

		this.customerID = Integer.parseInt(stringTokenizer.nextToken());
		this.insuranceID = Integer.parseInt(stringTokenizer.nextToken());
		this.stringDateOfPayment = stringTokenizer.nextToken();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // string to Date formatter
		Date date = format.parse(stringDateOfPayment);
		this.dateOfPayment = format.parse(stringDateOfPayment);

		this.whetherPayment = Boolean.parseBoolean(stringTokenizer.nextToken());
	}

	public boolean match(int customerID, int insuranceID) {
		return (this.customerID == customerID) && (this.insuranceID == insuranceID);
	}

	public Date getDateOfPayment() {
		return this.dateOfPayment;
	}

	public boolean getWhetherPayment() {
		return this.whetherPayment;
	}

	public String toString() {
		String stringPaymentInfo = this.customerID + " " + this.insuranceID + " " + this.dateOfPayment + " "
				+ this.whetherPayment;
		return stringPaymentInfo;

	}

	public void finalize() throws Throwable {

	}

	// 납입 여부를 변경하는 메소드
	public boolean updatePayment(boolean inputUpdatePayment) {
		this.whetherPayment = inputUpdatePayment;
		return this.whetherPayment;
	}
}// end Payment