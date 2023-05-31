package CompensationClaim;

import Dao.CarAccidentDao;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarAccidentListImpl {

	private ArrayList<CarAccident> carAccidentList;
	public CarAccidentDao carAccidentDao;

	public CarAccidentListImpl() throws Exception {
		this.carAccidentDao = new CarAccidentDao();
		this.carAccidentList = carAccidentDao.retrieveAll();
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
	public boolean createCarAccident(CarAccident carAccident) throws Exception {
		if(this.carAccidentList.add(carAccident)) {
			carAccidentDao.create(carAccident);
			return true;
		} else return false;
	}
}//end carAccidentListImpl