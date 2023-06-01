package Dao;

import CompensationClaim.CarAccident;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CarAccidentDao extends Dao{
	public CarAccidentDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(CarAccident carAccident) throws Exception {
		String query = "insert into CarAccident(CCID, type, dateTime, carNumber, driverName, licenseNumber, accidentDetail) values ('"+
				carAccident.getCCID() + "','" + carAccident.getType() + "','" + carAccident.getDateTime() + "','" + carAccident.getCarNumber() + "','"
				+ "','" + carAccident.getDriverName() + carAccident.getLicenseNumber() + "','" + carAccident.getAccidentDetail() + ");";
		System.out.println(query);
		super.create(query);
	}
	public void createAll(ArrayList<CarAccident> carAccidentList) throws Exception {
		CarAccident carAccident;
		for(int i=0;i<=carAccidentList.size();i++) {
			carAccident = carAccidentList.get(i);
			String query = "insert into CarAccident(CCID, type, dateTime, carNumber, driverName, licenseNumber, accidentDetail) values ('"+
					carAccident.getCCID() + "','" + carAccident.getType() + "','" + carAccident.getDateTime() + "','" + carAccident.getCarNumber() + "','"
					+ "','" + carAccident.getDriverName() + carAccident.getLicenseNumber() + "','" + carAccident.getAccidentDetail() + ");";
			System.out.println(query);
		super.create(query);
		}
	}
	public ArrayList<CarAccident> retrieveAll() throws Exception {
		String query = "select * from CarAccident;";
		System.out.println(query);
		ResultSet results = super.retrieve(query);
        ArrayList<CarAccident> carAccidentList = new ArrayList<CarAccident>();
		CarAccident carAccident;
        while (results.next()){
			carAccident = new CarAccident();
			carAccident.setCCID(results.getString("CCID"));
			carAccident.setType(results.getString("type"));
			carAccident.setDateTime(results.getTimestamp("dateTime").toLocalDateTime());
			carAccident.setCarNumber(results.getString("carNumber"));
			carAccident.setDriverName(results.getString("driverName"));
			carAccident.setLicenseNumber(results.getString("licenseNumber"));
			carAccident.setAccidentDetail(results.getString("accidentDetail"));
			carAccidentList.add(carAccident);
        }
		return carAccidentList;
	}

	public void updateById(String CCID, String column, String content) throws Exception {
		String query = "UPDATE CarAccident SET"+column+"="+content+"WHERE CCID="+CCID+";";
		System.out.println(query);
		super.update(query);
	}
	public void deleteById(String CCID) throws Exception {
		String query = "DELETE FROM CarAccident WHERE CCID="+CCID+";";
		System.out.println(query);
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		String query = "DELETE FROM CarAccident;";
		System.out.println(query);
		super.delete(query);
	}
}
