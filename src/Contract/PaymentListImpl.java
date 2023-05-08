package Contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class PaymentListImpl {

	private ArrayList<Payment> paymentList;
	public Payment m_Payment;

	public PaymentListImpl(String paymentFileName) throws ParseException, IOException {
		BufferedReader paymentFile = new BufferedReader(new FileReader(paymentFileName));
		this.paymentList = new ArrayList<Payment>();
		while (paymentFile.ready()) {
			String paymentInfo = paymentFile.readLine();
			if (!paymentInfo.equals(""))
				this.paymentList.add(new Payment(paymentInfo));
		}
		paymentFile.close();

	}

	public void finalize() throws Throwable {

	}

	public boolean add(String paymentInfo) throws ParseException, IOException {
		if (this.paymentList.add(new Payment(paymentInfo))) {
			updateFile("Database/Payment.txt");
			return true;
		}
		return false;
	}

	private void updateFile(String string) throws IOException {
		File file = new File(string);
		if (!file.exists()) {
			file.createNewFile();
			String paymentInfo = "";
			BufferedWriter paymentFileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < this.paymentList.size(); i++) {
				paymentInfo = paymentInfo + "\r\n" + paymentList.get(i).toString();
			}
			paymentFileWriter.write(paymentInfo);
			paymentFileWriter.flush();
			paymentFileWriter.close();
		}

	}

	public boolean delete() {
		return false;
	}

	public ArrayList<Payment> retrieve() throws Exception {
		if(this.paymentList.size()==0) throw new Exception("payment 데이터가 없습니다.");
		return this.paymentList;
	}

	public boolean update() {
		return false;
	}
}// end PaymentListImpl