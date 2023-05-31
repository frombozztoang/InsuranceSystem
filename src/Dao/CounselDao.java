package Dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import Counsel.Counsel;

public class CounselDao extends Dao {
	public CounselDao() {
		try {
			super.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void create(Counsel counsel)  throws Exception {
		String query = "insert into counsel (counselID,customerID,content,managerName,requirement, dateOfCounsel) values ( " 
	             +counsel.getCounselID() + ", " +
				" '" + counsel.getCustomerID() + "', " +
				" '" + counsel.getContent()+ "', " +
				" '" + counsel.getManagerName()+ "', " +
				" '" + counsel.getRequirement() + "', " + 
				" '" + counsel.getDateOfCounsel() + "' )";
		super.create(query);
	}
	public ResultSet retrieveByCustomerID(String CustomerID) throws Exception {
		//쿼리 제조
		String query = "select * from Counsel where CustomerID ="+CustomerID+";";
		return super.retrieve(query);
	}
	
	public ArrayList<Counsel> retrieveAll() throws Exception {
		String query = "select * from Counsel;";
		ResultSet results = super.retrieve(query);
        ArrayList<Counsel> CounselList = new ArrayList<Counsel>();
        Counsel counsel;
        while (results.next()){
        	counsel = new Counsel(); 
        	counsel.setCounselID(results.getString("CounselID"));
        	counsel.setCustomerID(results.getString("CustomerID"));
        	counsel.setContent(results.getString("Content"));
        	counsel.setManagerName(results.getString("ManagerName"));
        	counsel.setRequirement(results.getString("Requirement"));
        	String dateString3 = resultSet.getString("DateOfCounsel");
        	LocalDate dateOfCounsel = LocalDate.parse(dateString3);
			counsel.setDateOfCounsel(dateOfCounsel);
			CounselList.add(counsel);
        }  
		return CounselList;
	}

	public void deleteByCustomerId(String CustomerID) throws Exception {
		//쿼리 제조
		String query = "DELETE FROM CounselApplication WHERE customerID="+CustomerID+";";
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		//쿼리 제조
		String query = "DELETE FROM CounselApplication;";
		super.delete(query);
	}
}