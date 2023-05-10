package CompensationClaim;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarAccidentListImpl {

	private ArrayList<CarAccident> carAccidentList;
	public CarAccident carAccident;

	public CarAccidentListImpl() {

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