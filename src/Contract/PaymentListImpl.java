
package Contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class PaymentListImpl {
	private ArrayList<Payment> paymentList;
	public Payment m_Payment;

	public PaymentListImpl(String paymentFileName) throws ParseException, IOException {
		BufferedReader paymentFile = new BufferedReader(new FileReader(paymentFileName));
		this.paymentList = new ArrayList<Payment>();
		while (paymentFile.ready()) {
			Payment payment = makePayment(paymentFile.readLine());
			if (payment != null)
				this.paymentList.add(payment);
		}
		paymentFile.close();
	}

	public class DateConverter {
		public static LocalDate stringToDate(String dateString) {

			String[] dateParts = dateString.split("-");
			int year = Integer.parseInt(dateParts[0]);
			int month = Integer.parseInt(dateParts[1]);
			int day = Integer.parseInt(dateParts[2]);
			return LocalDate.of(year, month, day);

		}
	}

	public Payment makePayment(String paymentInfo) throws ParseException {
		Payment payment = new Payment();

		StringTokenizer stringTokenizer = new StringTokenizer(paymentInfo);
		payment.setCustomerID(Integer.parseInt(stringTokenizer.nextToken()));
		payment.setInsuranceID(Integer.parseInt(stringTokenizer.nextToken()));
		payment.setStringDateOfPayment(stringTokenizer.nextToken());
		LocalDate date = DateConverter.stringToDate(payment.getStringDateOfPayment());
		payment.setDateOfPayment(date);
		payment.setWhetherPayment(Boolean.parseBoolean(stringTokenizer.nextToken()));
		return payment;

	}

	public void finalize() throws Throwable {

	}

	public boolean add(String paymentInfo) throws ParseException, IOException {
		if (this.paymentList.add(new Payment())) {
			updateFile("Database/Payment.txt");
			return true;
		}
		return false;
	}

	private void updateFile(String string) throws IOException {
		File file = new File(string);
		if (!file.exists())
			file.createNewFile();
		String paymentInfo = "";
		if(!paymentList.isEmpty()) {
			paymentInfo = paymentList.get(0).toString();
		}

		BufferedWriter paymentFileWriter = new BufferedWriter(new FileWriter(file));
		for (int i = 1; i < this.paymentList.size(); i++) {
			paymentInfo = paymentInfo + "\r\n" + paymentList.get(i).toString();
		}

		paymentFileWriter.write(paymentInfo);
		paymentFileWriter.flush();
		paymentFileWriter.close();

	}

	public boolean delete() {
		return false;
	}

	public ArrayList<Payment> retrieve() throws Exception {
		if (this.paymentList.size() == 0)
			throw new Exception("payment 데이터가 없습니다.");
		return this.paymentList;
	}

	public boolean update() {
		return false;
	}

	public Boolean updateWheterPayment(int customerID, int insuranceID) throws IOException {
		for (int i = 0; i < this.paymentList.size(); i++) {
			if (this.paymentList.get(i).match(customerID, insuranceID)) {
				this.paymentList.get(i).setWhetherPayment(this.paymentList.get(i).updatePayment());
				updateFile("Database/Payment.txt");
				return true;
			}
		}

		return false; // exception
	}

}
// end PaymentListImpl
