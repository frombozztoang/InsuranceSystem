
package Contract;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import Dao.PaymentDao;
import Insurance.Insurance;

public class PaymentListImpl {
	private ArrayList<Payment> paymentList;
	private PaymentDao paymentDao;
	public Payment m_Payment;

	public PaymentListImpl() throws Exception {
		this.paymentDao = new PaymentDao();
		this.paymentList = paymentDao.retrieveAll();
	}

	public static LocalDate stringToDate(String dateString) {
		String[] dateParts = dateString.split("-");
		int year = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[2]);
		return LocalDate.of(year, month, day);

	}

	public void finalize() throws Throwable {

	}

//	public boolean add(String paymentInfo) throws ParseException, IOException {
//		if (this.paymentList.add(new Payment())) {
//			updateFile("data/Payment.txt");
//			return true;
//		}
//		return false;
//	}

//	private void updateFile(String string) throws IOException {
//		File file = new File(string);
//		if (!file.exists())
//			file.createNewFile();
//		String paymentInfo = "";
//		if (!paymentList.isEmpty()) {
//			paymentInfo = paymentList.get(0).toString();
//		}
//
//		BufferedWriter paymentFileWriter = new BufferedWriter(new FileWriter(file));
//		for (int i = 1; i < this.paymentList.size(); i++) {
//			paymentInfo = paymentInfo + "\r\n" + paymentList.get(i).toString();
//		}
//
//		paymentFileWriter.write(paymentInfo);
//		paymentFileWriter.flush();
//		paymentFileWriter.close();
//
//	}

	public boolean delete() {
		return false;
	}

	public ArrayList<Payment> retrieve() throws Exception {
		if (this.paymentList.size() == 0)
			throw new Exception("payment 데이터가 없습니다.");
		return this.paymentList;
	}

	public ArrayList<Payment> retreiveCustomerPayment(String customerID) {
		ArrayList<Payment> customerPayment = new ArrayList<Payment>();
		for (int i = 0; i < this.paymentList.size(); i++) {
			if (paymentList.get(i).getCustomerID().equals(customerID)) {
				customerPayment.add(paymentList.get(i));
			}
		}
		return customerPayment;
	}

	public ArrayList<Payment> retreiveCustomerInsurancePayment(String customerID, String insuranceID) {
		ArrayList<Payment> customerPayment = new ArrayList<Payment>();
		for (int i = 0; i < this.paymentList.size(); i++) {
			if (paymentList.get(i).getCustomerID().equals(customerID)
					&& paymentList.get(i).getInsuranceID().equals(insuranceID)) {
				customerPayment.add(paymentList.get(i));
			}
		}
		return customerPayment;
	}

	public ArrayList<String> retreiveDateStatusById(String customerID, String insuranceId) {
		ArrayList<String> dateAndStatus = new ArrayList<String>();
		for (int i = 0; i < this.paymentList.size(); i++) {
			if (paymentList.get(i).getCustomerID().equals(customerID)
					&& paymentList.get(i).getInsuranceID().equals(insuranceId)) {
				dateAndStatus.add(paymentList.get(i).getDateOfPayment() + " " + paymentList.get(i).isWhetherPayment());
			}
		}
		return dateAndStatus;
	}

	public Boolean updateWhetherPayment(String customerId, String insuranceId) throws IOException {
		for (int i = 0; i < this.paymentList.size(); i++) {
			if (this.paymentList.get(i).getCustomerID().equals(customerId)
					&& paymentList.get(i).getInsuranceID().equals(insuranceId)) {

				boolean newWhetherPayment = !this.paymentList.get(i).isWhetherPayment();
				if (paymentDao.updateWhetherPayment(customerId, insuranceId, newWhetherPayment)) {
					this.paymentList.get(i).setWhetherPayment(newWhetherPayment); // paymentList 업데이트
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<String> retreiveUnpaidCustomerId() {
		ArrayList<String> unPaidCustomerId = new ArrayList<String>();
		HashSet<String> uniqueCustomerId = new HashSet<String>(); // 중복 값을 없애기 위한 HashSet

		for (int i = 0; i < this.paymentList.size(); i++) {
			if (Boolean.toString(paymentList.get(i).isWhetherPayment()).equals("false")) {
				uniqueCustomerId.add(paymentList.get(i).getCustomerID());
			}
		}

		// HashSet의 값을 ArrayList에 복사
		unPaidCustomerId.addAll(uniqueCustomerId);

		return unPaidCustomerId;
	}

	public boolean update(Payment updatedPayment) throws IOException {
		for (int i = 0; i < this.paymentList.size(); i++) {
			Payment payment = this.paymentList.get(i);
			if (payment.match(updatedPayment.getInsuranceID(), updatedPayment.getCustomerID())) {
				this.paymentList.set(i, updatedPayment);
				paymentDao.update(updatedPayment);
				return true;
			}
		}
		return false;
	}
}
// end PaymentListImpl
