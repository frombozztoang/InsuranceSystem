package CompensationClaim;

import java.time.LocalDateTime;

public class CarAccident extends CompensationClaim {

	private String type;
	private LocalDateTime dateTime;
	private String place;
	private String carNumber;
	private String driverName;
	private String licenseNumber;
	private String accidentDetail;

	public CarAccident() {

	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getAccidentDetail() {
		return accidentDetail;
	}
	public void setAccidentDetail(String accidentDetail) {
		this.accidentDetail = accidentDetail;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public String toString() {
		String stringReturn =  this.CCID + " " + this.type + " " + this.dateTime + " " + this.place + " " + this.carNumber + " "
				+ this.driverName + " " + this.licenseNumber + " " + this.accidentDetail;
		return stringReturn;
	}
}// end CarAccident