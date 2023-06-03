package Counsel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import Customer.Customer;
import Dao.CounselApplicationDao;


public class CounselApplicationListImpl implements CounselApplicationList{

	private ArrayList<CounselApplication> CounselApplicationList;
	private CounselApplicationDao counselApplicationDao;

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
	
	public CounselApplicationListImpl() throws Exception {
		this.counselApplicationDao = new CounselApplicationDao();
		this.CounselApplicationList = counselApplicationDao.retrieveAll();
	}
	
	private CounselApplication makeCouncel(String councelInfo) throws ParseException {
		CounselApplication councelApplication = new CounselApplication();

		StringTokenizer stringTokenizer = new StringTokenizer(councelInfo);
		councelApplication.setCategory(stringTokenizer.nextToken());
		councelApplication.setCounselID(stringTokenizer.nextToken());
		councelApplication.setCustomerID(stringTokenizer.nextToken());
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
		Counsel counsel = councelApplication.getCounsel();
		counsel.setManagerName(stringTokenizer.nextToken());
		counsel.setContent(null);
		return councelApplication;
	}

	public static List<CounselApplication> getCounselList(Customer customer, 
			   CounselApplicationListImpl counselApplicationListImpl) {
		List<CounselApplication> selectedCouncels = new ArrayList<CounselApplication>();
		ArrayList<CounselApplication> counsels = counselApplicationListImpl.retrieve();
		for(CounselApplication counselApplication : counsels) {
			if(counselApplication.getCustomerID().equals(customer.getCustomerID()) )
				selectedCouncels.add(counselApplication);
		}
		return selectedCouncels;
	}

	public  CounselApplication getConsuleInfo(String id, LocalDate date, 
			   CounselApplicationListImpl counselApplicationListImpl) {
		   ArrayList<CounselApplication> counselApplications = counselApplicationListImpl.retrieve();
		   for(CounselApplication counselApplication : counselApplications) {
			   if(!counselApplication.getCustomerID().equals(id)) continue;
			   if(counselApplication.getDateOfFirst().equals(date)) return counselApplication;
			   if(counselApplication.getDateOfSecond().equals(date)) return counselApplication;
		   }
		   return null;
	}

	@Override
	public boolean add(CounselApplication counselApplication) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String counselID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CounselApplication counselApplication, String counselID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CounselApplication> retrieve() {
		// TODO Auto-generated method stub
		return null;
	}
}
