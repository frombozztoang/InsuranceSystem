package Dao;

import CompensationClaim.Survey;
import Insurance.Insurance;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SurveyDao extends Dao{
	public SurveyDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(Survey survey) throws Exception {
		String query = "insert into Survey(CCID, managerName, reportFilePath, surveyFee, decisionMoney, responsibility, responsibilityReason) values ('"+
				survey.getCCID() + "','" + survey.getManagerName() + "','" + survey.getReportFilePath() + "','" + survey.getSurveyFee() + "','"
				+ survey.getDecisionMoney() + "','" + survey.isResponsibility() + "','" + survey.getResponsibilityReason() + ");";
		System.out.println(query);
		super.create(query);
	}
	public void createAll(ArrayList<Survey> surveyList) throws Exception {
		Survey survey;
		for(int i=0;i<=surveyList.size();i++) {
			survey = surveyList.get(i);
			String query = "insert into Survey(CCID, managerName, reportFilePath, surveyFee, decisionMoney, responsibility, responsibilityReason) values ('"+
					survey.getCCID() + "','" + survey.getManagerName() + "','" + survey.getReportFilePath() + "','" + survey.getSurveyFee() + "','"
					+ survey.getDecisionMoney() + "','" + survey.isResponsibility() + "','" + survey.getResponsibilityReason() + ");";
			System.out.println(query);
		super.create(query);
		}
	}
	public ArrayList<Survey> retrieveAll() throws Exception {
		String query = "select * from Survey;";
		System.out.println(query);
		ResultSet results = super.retrieve(query);
        ArrayList<Survey> surveyList = new ArrayList<Survey>();
		Survey survey;
        while (results.next()){
			survey = new Survey();
			survey.setCCID(results.getString("CCID"));
			survey.setManagerName(results.getString("managerName"));
			survey.setReportFilePath(results.getString("reportFilePath"));
			survey.setSurveyFee(results.getInt("surveyFee"));
			survey.setDecisionMoney(results.getInt("decisionMoney"));
			survey.setResponsibility(results.getBoolean("responsibility"));
			survey.setResponsibilityReason(results.getString("responsibilityReason"));
			surveyList.add(survey);
        }  
		return surveyList;
	}
	public void update(Survey survey) throws Exception {
		//쿼리 제조
		String query =  "UPDATE Survey SET CCID = '"+survey.getCCID()+"', managerName = '"+survey.getManagerName()+"', reportFilePath = "
				+survey.getReportFilePath()+", surveyFee = '"+survey.getSurveyFee()+"', decisionMoney = '"+survey.getDecisionMoney()+"', "
				+ "responsibility = '"+survey.isResponsibility()+"', responsibilityReason = '"+survey.getResponsibilityReason()+" WHERE CCID ='" +survey.getCCID()+"';";
		super.update(query);
	}
	public void updateById(String CCID, String column, String content) throws Exception {
		String query = "UPDATE Survey SET"+column+"="+content+"WHERE CCID="+CCID+";";
		System.out.println(query);
		super.update(query);
	}
	public void deleteById(String CCID) throws Exception {
		String query = "DELETE FROM Survey WHERE CCID="+CCID+";";
		System.out.println(query);
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		String query = "DELETE FROM Survey;";
		System.out.println(query);
		super.delete(query);
	}
}
