package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Customer.FamilyHistory;

public class FamilyHistoryDao extends Dao {
		public FamilyHistoryDao() {
			try {
				super.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void create(FamilyHistory familyHistory)  throws Exception {
			String query = "insert into counsel (customerID,diseaseName,relationship) values ( " 
		             +familyHistory.getCustomerID() + ", " +
					" '" + familyHistory.getDiseaseName() + "', " +
					" '" + familyHistory.getRelationship()+ "', " + "' )";
			super.create(query);
		}
		public ResultSet retrieveByCustomerID(String CustomerID) throws Exception {
			//쿼리 제조
			String query = "select * from FamilyHistory where CustomerID ="+CustomerID+";";
			return super.retrieve(query);
		}
		

		public void deleteByCustomerId(String CustomerID) throws Exception {
			//쿼리 제조
			String query = "DELETE FROM FamilyHistory WHERE customerID="+CustomerID+";";
			super.delete(query);
		}
		public void deleteAll() throws Exception {
			//쿼리 제조
			String query = "DELETE FROM FamilyHistory;";
			super.delete(query);
		}
		public ArrayList<FamilyHistory> retrieveAll() throws SQLException {
			String query = "select * from familyhistory;";
	        ResultSet results = super.retrieve(query);
	        ArrayList<FamilyHistory> familyHistories = new ArrayList<FamilyHistory>();
	        while (results.next()) {
	        	FamilyHistory familyHistory = new FamilyHistory();
	        	familyHistory.setCustomerID(results.getString("customerID"));
	        	familyHistory.setDiseaseName(results.getString("diseaseName"));
	        	familyHistory.setRelationship(results.getString("relationship"));
	        	familyHistories.add(familyHistory);
	        }  
	        return familyHistories;
		}
	}

