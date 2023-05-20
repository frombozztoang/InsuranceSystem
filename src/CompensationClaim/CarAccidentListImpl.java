package CompensationClaim;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarAccidentListImpl {

	private ArrayList<CarAccident> carAccidentList;
	public CarAccident carAccident;

	public CarAccidentListImpl(String carAccidentFileName) throws IOException {
		BufferedReader carAccidentFile = new BufferedReader(new FileReader(carAccidentFileName));
		this.carAccidentList = new ArrayList<CarAccident>();
		while (carAccidentFile.ready()) {
			CarAccident carAccident = stringToCarAccident(carAccidentFile.readLine());
			if (carAccident!=null) this.carAccidentList.add(carAccident);
		}
		carAccidentFile.close();
	}

	private CarAccident stringToCarAccident(String carAccidentInfo) {
		CarAccident carAccident = new CarAccident();
		StringTokenizer stringTokenizer = new StringTokenizer(carAccidentInfo);
		carAccident.setCCID(stringTokenizer.nextToken());
		carAccident.setInsuranceID(stringTokenizer.nextToken());
		carAccident.setCustomerID(stringTokenizer.nextToken());
		carAccident.setReceptionistName(stringTokenizer.nextToken());
		carAccident.setReceptionistPNumber(stringTokenizer.nextToken());
		carAccident.setRelationshipOfContractor(stringTokenizer.nextToken());
		carAccident.setDocumentFilePath(stringTokenizer.nextToken());
		carAccident.setBank(stringTokenizer.nextToken());
		carAccident.setAccountNumber(stringTokenizer.nextToken());
		carAccident.setAccountHolderName(stringTokenizer.nextToken());
		carAccident.setType(stringTokenizer.nextToken());
		carAccident.setDateTime(LocalDateTime.parse(stringTokenizer.nextToken()));
		carAccident.setPlace(stringTokenizer.nextToken());
		carAccident.setCarNumber(stringTokenizer.nextToken());
		carAccident.setDriverName(stringTokenizer.nextToken());
		carAccident.setLicenseNumber(stringTokenizer.nextToken());
		carAccident.setAccidentDetail(stringTokenizer.nextToken());
		return carAccident;
	}

	public void finalize() throws Throwable {

	}
	public boolean add(){
		return false;
	}

	public boolean delete(){
		return false;
	}

	public ArrayList<CarAccident> retrieve(){
		return carAccidentList;
	}

	public boolean update(){
		return false;
	}
	public boolean createCarAccident(CarAccident carAccident) {
		if(this.carAccidentList.add(carAccident)) {
			updateFile("data/CarAccident.txt");
			return true;
		} else return false;
	}
	private void updateFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			String carAccidentInfo = "";
			if(carAccidentList.size()>=1) carAccidentInfo = carAccidentList.get(0).toString();
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 1; i < this.carAccidentList.size(); i++)
				carAccidentInfo = carAccidentInfo + "\r\n" + carAccidentList.get(i).toString();
			fileWriter.write(carAccidentInfo);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}//end carAccidentListImpl