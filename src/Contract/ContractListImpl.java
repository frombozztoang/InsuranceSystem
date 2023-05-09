package Contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;


public class ContractListImpl {

	private ArrayList<Contract> contractList;
	public Contract m_Contract;

	public ContractListImpl(String contractFileName) throws IOException, ParseException {
		BufferedReader contractFile = new BufferedReader(new FileReader(contractFileName));
		this.contractList = new ArrayList<Contract>();
		while (contractFile.ready()) {
			Contract contract = makeContract(contractFile.readLine());
			if (!contract.equals(""))
				this.contractList.add(contract);
		}
		contractFile.close();
	}

	private Contract makeContract(String contractInfo) throws ParseException {
		Contract contract = new Contract();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		
		StringTokenizer stringTokenizer = new StringTokenizer(contractInfo);
		contract.setCustomerID(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setInsuranceID(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setInsurancePeriod(stringTokenizer.nextToken());
		contract.setPremium(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setPaymentCycle(stringTokenizer.nextToken());
		contract.setMaxCompensation(Integer.parseInt(stringTokenizer.nextToken()));
		contract.setStringDateOfSubscription(stringTokenizer.nextToken());
		Date dateOfSubscription = format.parse(contract.getStringDateOfSubscription());
		contract.setDateOfSubscription(dateOfSubscription);
		contract.setStringDateOfMaturity(stringTokenizer.nextToken());
		Date dateOfMaturity = format.parse(contract.getStringDateOfMaturity());
		contract.setDateOfMaturity(dateOfMaturity);
		contract.setMaturity(Boolean.parseBoolean(stringTokenizer.nextToken()));
		contract.setResurrection(Boolean.parseBoolean(stringTokenizer.nextToken()));
		contract.setCancellation(Boolean.parseBoolean(stringTokenizer.nextToken()));
		
		return contract;
	}

	public void finalize() throws Throwable {

	}
	public boolean add() throws IOException{
		if (this.contractList.add(new Contract())) {
			updateFile("Database/Contract.txt");
			return true;
		}
		return false;
	}

	private void updateFile(String string) throws IOException {
		File file = new File(string);
		if (!file.exists())
			file.createNewFile();
		String contractInfo = "";
		BufferedWriter contractFileWriter = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < this.contractList.size(); i++)
			contractInfo = contractInfo + "\r\n" + contractList.get(i).toString();

		contractFileWriter.write(contractInfo);
		contractFileWriter.flush();
		contractFileWriter.close();
		
	}

	public boolean delete(){
		return false;
	}

	public ArrayList<Contract> retrieve() throws Exception{
		if (this.contractList.size() == 0)
			throw new Exception("payment 데이터가 없습니다.");
		return this.contractList;
	}

	public boolean update(){
		return false;
	}
}//end ContractListImpl