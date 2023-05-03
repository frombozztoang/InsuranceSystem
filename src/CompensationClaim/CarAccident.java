package CompensationClaim;

import java.util.Date;

public class CarAccident extends CompensationClaim {

	private String accidentDetail;
	private String carNumber;
	private Date date;
	private String driverName;
	private int licenseNumber;
	private String place;
	private int time;
	private String type;

	public CarAccident() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public String createCompensationClaim() {
		return "";
	}
}// end CarAccident