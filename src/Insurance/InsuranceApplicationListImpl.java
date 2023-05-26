package Insurance;

import CompensationClaim.CompensationClaim;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InsuranceApplicationListImpl {

	private ArrayList<InsuranceApplication> insuranceApplicationList;
	public InsuranceApplication InsuranceApplication;

	public InsuranceApplicationListImpl(String insuranceApplicationFileName) throws IOException {
		BufferedReader insuranceApplicationFile = new BufferedReader(new FileReader(insuranceApplicationFileName));
		this.insuranceApplicationList = new ArrayList<InsuranceApplication>();
		while (insuranceApplicationFile.ready()) {
			InsuranceApplication insuranceApplication = stringToInsuranceApplication(insuranceApplicationFile.readLine());
			if (insuranceApplication!=null) this.insuranceApplicationList.add(insuranceApplication);
		}
		insuranceApplicationFile.close();
	}

	private InsuranceApplication stringToInsuranceApplication(String insuranceApplicationInfo) {
		InsuranceApplication insuranceApplication = new InsuranceApplication();
		StringTokenizer stringTokenizer = new StringTokenizer(insuranceApplicationInfo);
		insuranceApplication.setApplicationID(stringTokenizer.nextToken());
		insuranceApplication.setInsuranceID(stringTokenizer.nextToken());
		insuranceApplication.setCustomerID(stringTokenizer.nextToken());
		insuranceApplication.setCreatedAt(LocalDate.parse(stringTokenizer.nextToken()));
		insuranceApplication.setInsurancePeriod(stringTokenizer.nextToken());
		insuranceApplication.setPaymentCycle(stringTokenizer.nextToken());
		insuranceApplication.setSubscriptionFilePath(stringTokenizer.nextToken());
		insuranceApplication.setPremium(Integer.parseInt(stringTokenizer.nextToken()));
		insuranceApplication.setMaxCompensation(Integer.parseInt(stringTokenizer.nextToken()));
		insuranceApplication.setApproval(Boolean.parseBoolean(stringTokenizer.nextToken()));
		insuranceApplication.setReasonOfApproval(stringTokenizer.nextToken());
		return insuranceApplication;
	}

	public void finalize() throws Throwable {

	}
	public boolean add(){
		return false;
	}

	public boolean delete(String applicationId){
		for (int i = 0; i < this.insuranceApplicationList.size(); i++) {
			InsuranceApplication insuranceApplication = (InsuranceApplication) this.insuranceApplicationList.get(i);
			if (insuranceApplication.matchId(applicationId))
				if (this.insuranceApplicationList.remove(insuranceApplication)) {
					updateFile("InsuranceApplication.txt");
					return true;
				} else
					return false;
		}
		return false;
	}
	public ArrayList<InsuranceApplication> retrieve(){
		return insuranceApplicationList;
	}

	public boolean update(){
		return false;
	}

	public boolean createInsuranceApplication(InsuranceApplication insuranceApplication) {
		if(this.insuranceApplicationList.add(insuranceApplication)) {
			updateFile("data/InsuranceApplication.txt");
			return true;}
		else return false;
	}
	public InsuranceApplication getApplicationbyId(String applicationID) {
		for(int i=0;i<this.insuranceApplicationList.size();i++) {
			InsuranceApplication insuranceApplication = (InsuranceApplication) this.insuranceApplicationList.get(i);
			if(insuranceApplication.matchId(applicationID))
				return insuranceApplication;
		}
		return null;
	}
	private void updateFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			String insuranceApplicationInfo = "";
			if(insuranceApplicationList.size()>=1) insuranceApplicationInfo = insuranceApplicationList.get(0).toString();
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 1; i < this.insuranceApplicationList.size(); i++)
				insuranceApplicationInfo = insuranceApplicationInfo + "\r\n" + insuranceApplicationList.get(i).toString();
			fileWriter.write(insuranceApplicationInfo);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}//end InsuranceApplicationListImpl