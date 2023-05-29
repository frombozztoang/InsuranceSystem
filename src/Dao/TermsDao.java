package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import Insurance.Terms;

public class TermsDao extends Dao{
	public TermsDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean create(Terms terms) throws Exception {
		//쿼리 제조
		String query = "insert into Terms(termsID, termsName, calculatedMoneyMethod, termsContent) values ('"+ 
		terms.getTermsID()+"','"+terms.getTermsName() + "','"+ terms.getCalculatedMoneyMethod() +"','"+terms.getTermsContent()+"');";
		System.out.println(query);
		return super.create(query);
	}
	public ArrayList<Terms> retrieveAll() throws Exception {
		//쿼리 제조
		String query = "select * from Terms;";
		System.out.println(query);
		ResultSet results =  super.retrieve(query);
		ArrayList<Terms> termsList = new ArrayList<Terms>();
		Terms terms;
		while (results.next()){
			terms = new Terms();
			terms.setTermsID(results.getString("termsID"));
			terms.setTermsName(results.getString("termsName"));
			terms.setTermsContent(results.getString("termsContent"));
			terms.setCalculatedMoneyMethod(results.getString("calculatedMoneyMethod"));
			termsList.add(terms);
		}
		return termsList;
	}
	public ResultSet retrieveById(String type) throws Exception {
		//쿼리 제조
		String query = "select * from Terms where type ="+type+";";
		System.out.println(query);
		return super.retrieve(query);
	}
}
