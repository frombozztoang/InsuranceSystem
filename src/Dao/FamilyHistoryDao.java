package Dao;

import java.sql.ResultSet;
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
	}

