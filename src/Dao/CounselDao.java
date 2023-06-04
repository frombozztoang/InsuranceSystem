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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(Counsel counsel) throws Exception {
		//쿼리 제조
		String query = "insert into Counsel(counselID,customerID,content,managerName,requirement, dateOfCounsel) values ( " +
				" '" + counsel.getCounselID()+ "', " +
				" '" + counsel.getCustomerID()+ "', " +
				" '" + counsel.getContent()+ "', " +
				" '" + counsel.getManagerName()+ "', " +
				" '" + counsel.getRequirement()+ "', " +
				" '" + counsel.getDateOfCounsel() + "')";
		super.create(query);
	}

	public ArrayList<Counsel> retrieveAll() throws Exception {
		String query = "select * from Counsel;";
		ResultSet results = super.retrieve(query);
        ArrayList<Counsel> counselList = new ArrayList<Counsel>();
        Counsel counsel;
        while (results.next()){
        	counsel = new Counsel(); 
        	counsel.setCounselID(results.getString("counselID"));
        	counsel.setCustomerID(results.getString("customerID"));
        	counsel.setContent(results.getString("content"));
        	counsel.setManagerName(results.getString("managerName"));
        	counsel.setRequirement(results.getString("requirement"));
			String dateString = resultSet.getString("dateOfCounsel");
			LocalDate dateOfCounsel = LocalDate.parse(dateString);
			counsel.setDateOfCounsel(dateOfCounsel);
        	counselList.add(counsel);
        }  
		return counselList;
	}

	public void deleteByCustomerId(String CustomerID) throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Counsel WHERE customerID='"+CustomerID+"';";
		super.delete(query);
	}
	public void deleteByCounselId(String counselID) throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Counsel WHERE counselID='"+counselID+"';";
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Counsel;";
		super.delete(query);
	}

	public void updateCounsel(Counsel updateCounsel) throws Exception {
		String query =  
				"UPDATE Counsel "
						+ "SET content = '"+updateCounsel.getContent()+"'"
						+" WHERE CounselID='"+updateCounsel.getCounselID()+"';";
					super.update(query);
	}
}
