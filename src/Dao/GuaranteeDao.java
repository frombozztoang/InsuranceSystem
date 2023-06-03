package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import Insurance.Guarantee;


public class GuaranteeDao extends Dao{
	public GuaranteeDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(Guarantee guarantee) throws Exception {
		//쿼리 제조
		String query = "insert into Guarantee(insuranceID, termsID) values ('"+  
				guarantee.getInsuranceID() + "','" + guarantee.getTermsID() +"');";
		super.create(query);
	}

	public ResultSet retrieveByInsuranceID(String insuranceID) throws Exception {
		//쿼리 제조
		String query = "select * from Guarantee where insuranceID ="+insuranceID+";";
		return super.retrieve(query);
	}
	
	public ArrayList<Guarantee> retrieveAll() throws Exception {
		String query = "select * from Guarantee;";
		ResultSet results = super.retrieve(query);
        ArrayList<Guarantee> guaranteeList = new ArrayList<Guarantee>();
        Guarantee guarantee;
        while (results.next()){
        	guarantee = new Guarantee(); 
        	guarantee.setInsuranceID(results.getString("insuranceID"));
        	guarantee.setTermsID(results.getString("termsID"));
        	guaranteeList.add(guarantee);
        }  
		return guaranteeList;
	}

	public void deleteByInsuranceId(String insuranceID) throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Guarantee WHERE insuranceID="+insuranceID+";";
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Guarantee;";
		super.delete(query);
	}
}
