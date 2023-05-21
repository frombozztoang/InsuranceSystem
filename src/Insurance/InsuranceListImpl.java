package Insurance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InsuranceListImpl {

	private ArrayList<Insurance> insuranceList;

	public InsuranceListImpl(String insuranceFileName) throws FileNotFoundException, IOException {
		BufferedReader insuranceFile = new BufferedReader(new FileReader(insuranceFileName));
		this.insuranceList = new ArrayList<Insurance>();
		while (insuranceFile.ready()) {
			Insurance insurance = makeStringToInsurance(insuranceFile.readLine());
			if (insurance != null)
				this.insuranceList.add(insurance);
		}
		insuranceFile.close();
	}

	private Insurance makeStringToInsurance(String insuranceInfo) throws FileNotFoundException, IOException {
		Insurance insurance = new Insurance();
		StringTokenizer stringTokenizer = new StringTokenizer(insuranceInfo);
		insurance.setInsuranceID(stringTokenizer.nextToken());
		insurance.setInsuranceName(stringTokenizer.nextToken());
		insurance.setType(stringTokenizer.nextToken());
		insurance.setMaxCompensation(Integer.valueOf(stringTokenizer.nextToken()));
		insurance.setPeriodOfInsurance(stringTokenizer.nextToken());
		insurance.setPaymentCycle(stringTokenizer.nextToken());
		insurance.setPaymentPeriod(stringTokenizer.nextToken());
		insurance.setAgeOfTarget(stringTokenizer.nextToken());
		insurance.setBasicPremium(Integer.valueOf(stringTokenizer.nextToken()));
		insurance.setRate(stringTokenizer.nextToken());
		insurance.setDistributionStatus(Boolean.parseBoolean(stringTokenizer.nextToken()));
		insurance.setTermsIDList(stringTokenizer.nextToken());
		insurance.setInsuranceClausePeriod(stringTokenizer.nextToken());
		insurance.setAuthorization(Boolean.parseBoolean(stringTokenizer.nextToken()));
		String precaution = "";
		while (stringTokenizer.hasMoreTokens())
			precaution = precaution + " " + stringTokenizer.nextToken();
		insurance.setPrecaution(precaution);
		return insurance;
	}

	public String requestAuthorization(String insuranceID) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.matchId(insuranceID))
				return insurance.getInsuranceName();
			// 보험 인가 요청 (금감원)
			// if(금감원.requestAuthorization(insurance))return true;
		}
		return "";
	}

	public boolean updateAuthorization(String insuranceID, boolean authorization) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			if (this.insuranceList.get(i).matchId(insuranceID)) {
				this.insuranceList.get(i).setAuthorization(authorization);
				updateFile("Insurance.txt");
				return true;
			}
		}
		return false;
	}

	private void updateFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			String insuranceInfo = "";
			if (insuranceList.size() >= 1)
				insuranceInfo = insuranceList.get(0).toString();
			BufferedWriter insuranceFileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 1; i < this.insuranceList.size(); i++)
				insuranceInfo = insuranceInfo + "\r\n" + insuranceList.get(i).toString();
			insuranceFileWriter.write(insuranceInfo);
			insuranceFileWriter.flush();
			insuranceFileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createInsurance(Insurance insurance) throws FileNotFoundException, IOException {
		if (this.insuranceList.add(insurance)) {
			updateFile("Insurance.txt");
			return true;
		} else
			return false;
	}

	public ArrayList<Insurance> retrieveInsurance(String type) {
		ArrayList<Insurance> correctinsuranceList = new ArrayList<Insurance>();
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.matchType(type) && insurance.isAuthorization() == true)
				correctinsuranceList.add(insurance);
		}
		return correctinsuranceList;
	}

	public Insurance retrieveInsuranceDetail(String insuranceID) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			if (this.insuranceList.get(i).matchId(insuranceID))
				return this.insuranceList.get(i);
		}
		return null;
	}

	public boolean deleteInsurance(String insuranceId) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.matchId(insuranceId))
				if (this.insuranceList.remove(insurance)) {
					updateFile("Insurance.txt");
					return true;
				} else
					return false;
		}
		return false;
	}

	public ArrayList<Insurance> getOnSaleInsuranceList() {
		ArrayList<Insurance> onSaleInsurance = new ArrayList<Insurance>();
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.isAuthorization())
				onSaleInsurance.add(insurance);
		}
		return onSaleInsurance;
	}

	public ArrayList<Insurance> getUnregisteredInsuranceList() {
		ArrayList<Insurance> unregisteredInsurance = new ArrayList<Insurance>();
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (!insurance.isAuthorization())
				unregisteredInsurance.add(insurance);
		}
		return unregisteredInsurance;
	}

	public boolean updateinsurance(Insurance updateInsurance) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.matchId(updateInsurance.getInsuranceID()))
				this.insuranceList.set(i, updateInsurance);
			updateFile("Insurance.txt");
			return true;
		}
		return false;
	}

	public String getInsuranceTypebyId(String insuranceID) {
		for (int i = 0; i < this.insuranceList.size(); i++) {
			Insurance insurance = (Insurance) this.insuranceList.get(i);
			if (insurance.matchId(insuranceID))
				return insurance.getType();
		}
		return null;
	}

	public ArrayList<String> getInsuranceNameTypeInfoById(String insuranceId) {
		ArrayList<String> insuranceNameType = new ArrayList<String>();
		for (Insurance insurance : insuranceList) {
			if (insurance.getInsuranceID().equals(insuranceId)) {
				insuranceNameType.add(insurance.getInsuranceName());
				insuranceNameType.add(insurance.getType());
			}
		}
		return insuranceNameType;
	}
}
