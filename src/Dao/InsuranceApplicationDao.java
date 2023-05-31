package Dao;

import Insurance.Insurance;
import Insurance.InsuranceApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class InsuranceApplicationDao extends Dao{
	public InsuranceApplicationDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(InsuranceApplication insuranceApplication) throws Exception {
		String query = "insert into InsuranceApplication(applicationID, insuranceID, customerID, createdAt, insurancePeriod, paymentCycle, subscriptionFilePath, premium, maxCompensation, approval, reasonOfApproval) values ('"+
				insuranceApplication.getApplicationID() + "','" + insuranceApplication.getInsuranceID() + "','" + ",'" + insuranceApplication.getCustomerID() + "','" + insuranceApplication.getCreatedAt() + "','"
				+ insuranceApplication.getInsurancePeriod() + "','" + insuranceApplication.getPaymentCycle() + "','" + insuranceApplication.getSubscriptionFilePath() + "'," + insuranceApplication.getPremium() + ",'"
				+ insuranceApplication.getMaxCompensation() + "'," + insuranceApplication.isApproval() + ",'" + insuranceApplication.getReasonOfApproval()+");";
		System.out.println(query);
		super.create(query);
	}
	public void createAll(ArrayList<InsuranceApplication> insuranceApplicationList) throws Exception {
		InsuranceApplication insuranceApplication;
		for(int i=0;i<=insuranceApplicationList.size();i++) {
			insuranceApplication = insuranceApplicationList.get(i);
			String query = "insert into InsuranceApplication(applicationID, insuranceID, customerID, createdAt, insurancePeriod, paymentCycle, subscriptionFilePath, premium, maxCompensation, approval, reasonOfApproval) values ('"+
					insuranceApplication.getApplicationID() + "','" + insuranceApplication.getInsuranceID() + "','" + ",'" + insuranceApplication.getCustomerID() + "','" + insuranceApplication.getCreatedAt() + "','"
					+ insuranceApplication.getInsurancePeriod() + "','" + insuranceApplication.getPaymentCycle() + "','" + insuranceApplication.getSubscriptionFilePath() + "'," + insuranceApplication.getPremium() + ",'"
					+ insuranceApplication.getMaxCompensation() + "'," + insuranceApplication.isApproval() + ",'" + insuranceApplication.getReasonOfApproval()+");";
			System.out.println(query);
		super.create(query);
		}
	}
	public ArrayList<InsuranceApplication> retrieveAll() throws Exception {
		String query = "select * from InsuranceApplication;";
		System.out.println(query);
		ResultSet results = super.retrieve(query);
        ArrayList<InsuranceApplication> insuranceApplicationList = new ArrayList<InsuranceApplication>();
		InsuranceApplication insuranceApplication;
        while (results.next()){
			insuranceApplication = new InsuranceApplication();
			insuranceApplication.setApplicationID(results.getString("applicationID"));
			insuranceApplication.setInsuranceID(results.getString("insuranceID"));
			insuranceApplication.setCustomerID(results.getString("customerID"));
			insuranceApplication.setCreatedAt(results.getTimestamp("createdAt").toLocalDateTime().toLocalDate());
			insuranceApplication.setInsurancePeriod(results.getString("insurancePeriod"));
			insuranceApplication.setPaymentCycle(results.getString("paymentCycle"));
			insuranceApplication.setSubscriptionFilePath(results.getString("subscriptionFilePath"));
			insuranceApplication.setPremium(results.getInt("premium"));
			insuranceApplication.setMaxCompensation(results.getInt("maxCompensation"));
			insuranceApplication.setApproval(results.getBoolean("approval"));
			insuranceApplication.setReasonOfApproval(results.getString("reasonOfApproval"));
			insuranceApplicationList.add(insuranceApplication);
        }  
		return insuranceApplicationList;
	}
	public void update(InsuranceApplication insuranceApplication) throws Exception {
		//쿼리 제조
		String query =  "UPDATE InsuranceApplication SET applicationID = '"+insuranceApplication.getApplicationID()+"', insuranceID = '"+insuranceApplication.getInsuranceID()+"', customerID = "
				+insuranceApplication.getCustomerID()+", createdAt = '"+insuranceApplication.getCreatedAt()+"', insurancePeriod = '"+insuranceApplication.getInsurancePeriod()+"', "
				+ "paymentCycle = '"+insuranceApplication.getPaymentCycle()+"', subscriptionFilePath = '"+insuranceApplication.getSubscriptionFilePath()+"', premium = "+insuranceApplication.getPremium()+", "
				+ "maxCompensation = '"+insuranceApplication.getMaxCompensation()+"', approval = '"+insuranceApplication.isApproval()+"', reasonOfApproval = "+insuranceApplication.getReasonOfApproval()+" WHERE applicationID ='" +insuranceApplication.getApplicationID()+"';";
		super.update(query);
	}
	public void updateById(String applicationID, String column, String content) throws Exception {
		String query = "UPDATE InsuranceApplication SET"+column+"="+content+"WHERE applicationID="+applicationID+";";
		System.out.println(query);
		super.update(query);
	}
	public void deleteById(String applicationID) throws Exception {
		String query = "DELETE FROM InsuranceApplication WHERE applicationID="+applicationID+";";
		System.out.println(query);
		super.delete(query);
	}
	public void deleteAll() throws Exception {
		String query = "DELETE FROM InsuranceApplication;";
		System.out.println(query);
		super.delete(query);
	}
}
