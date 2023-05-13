package Counsel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Customer.Customer;

public class CounselApplicationListImpl implements CounselApplicationList{

	private ArrayList<CounselApplication> CounselApplicationList;
	private CounselList counselList;
	private String content;
	public CounselApplicationListImpl(String councelFileName) throws IOException, ParseException {
		BufferedReader customerFile = new BufferedReader(new FileReader(councelFileName));
		this.CounselApplicationList = new ArrayList<CounselApplication>();
		while (customerFile.ready()) {
			CounselApplication councelApplication = makeCouncel(customerFile.readLine());
			if (councelApplication != null)
				add(councelApplication);
		}
		customerFile.close();
	}
	
	private CounselApplication makeCouncel(String councelInfo) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		CounselApplication councelApplication = new CounselApplication();

		StringTokenizer stringTokenizer = new StringTokenizer(councelInfo);
		councelApplication.setCategory(stringTokenizer.nextToken());
		councelApplication.setCounselID(Integer.parseInt(stringTokenizer.nextToken()));
		councelApplication.setCustomerID(Integer.parseInt(stringTokenizer.nextToken()));
		String dateStr = stringTokenizer.nextToken(); // yyyy-mm-dd
		int year = Integer.parseInt(dateStr.substring(0, 4)); // yyyy
		int month = Integer.parseInt(dateStr.substring(5, 7)); // mm
	    int day = Integer.parseInt(dateStr.substring(8, 10)); // dd
	    LocalDate date1 = LocalDate.of(year, month, day);
		councelApplication.setDateOfFirst(date1);
		dateStr = stringTokenizer.nextToken(); // yyyy-mm-dd
		year = Integer.parseInt(dateStr.substring(0, 4)); // yyyy
		month = Integer.parseInt(dateStr.substring(5, 7)); // mm
	    day = Integer.parseInt(dateStr.substring(8, 10)); // dd
	    LocalDate date2 = LocalDate.of(year, month, day);
		councelApplication.setDateOfSecond(date2);
		councelApplication.setRequirement(stringTokenizer.nextToken());
		councelApplication.setManagerName(stringTokenizer.nextToken());
		return councelApplication;
	}

	@Override
	public boolean add(CounselApplication counselApplication) {
		if(this.CounselApplicationList.add(counselApplication)) return true;	
		else return false;
	}

	@Override
	public boolean delete(int counselID) {
		for(CounselApplication counselApplication : this.CounselApplicationList) {
			if(counselApplication.getCounselID() == counselID) {
				if(CounselApplicationList.remove(counselApplication)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean update(CounselApplication counselApplication, int counselID) {
		for(CounselApplication ucounselApplication : this.CounselApplicationList) {
			if(ucounselApplication.getCounselID() == counselID) {
				ucounselApplication.setCategory(counselApplication.getCategory());
				ucounselApplication.setRequirement(counselApplication.getRequirement());
			}
		}
		return false;
	}

	@Override
	public ArrayList<CounselApplication> retrieve() {
		// TODO Auto-generated method stub
		return CounselApplicationList;
	}
}
