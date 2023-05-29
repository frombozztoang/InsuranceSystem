package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import Insurance.Insurance;

public class InsuranceDao extends Dao{
	public InsuranceDao() {
		try {
			super.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean create(Insurance insurance) throws Exception {
		//쿼리 제조
		String query = "insert into Insurance(insuranceID, insuranceName, type, maxCompensation, periodOfInsurance, paymentCycle, paymentPeriod, ageOfTarget, basicPremium, rate, distributionStatus, termsIDList, insuranceClausePeriod, precaution, authorization) values ('"+    
		insurance.getInsuranceID() + "','" + insurance.getInsuranceName() + "','" + insurance.getType() + "'," + insurance.getMaxCompensation()
		+ ",'" + insurance.getPeriodOfInsurance() + "','" + insurance.getPaymentCycle() + "','" + insurance.getPaymentPeriod() + "','" + insurance.getAgeOfTarget()
		+ "'," + insurance.getBasicPremium() + ",'" + insurance.getRate() + "'," + insurance.isDistributionStatus() + ",'" + insurance.getTermsIDList()  + "','"
		+ insurance.getInsuranceClausePeriod() + "','" + insurance.getPrecaution() + "'," + insurance.isAuthorization()+");";
		return super.create(query);
	}
	public void createAll(ArrayList<Insurance> insuranceList) throws Exception {
		//쿼리 제조
		Insurance insurance;
		for(int i=0;i<=insuranceList.size();i++) {
		insurance = insuranceList.get(i);
		String query = "insert into Insurance(insuranceID, insuranceName, type, maxCompensation, periodOfInsurance, paymentCycle, paymentPeriod, ageOfTarget, basicPremium, rate, distributionStatus, termsIDList, insuranceClausePeriod, precaution, authorization) values ('"+    
		insurance.getInsuranceID() + "','" + insurance.getInsuranceName() + "','" + insurance.getType() + "'," + insurance.getMaxCompensation()
		+ ",'" + insurance.getPeriodOfInsurance() + "','" + insurance.getPaymentCycle() + "','" + insurance.getPaymentPeriod() + "','" + insurance.getAgeOfTarget()
		+ "'," + insurance.getBasicPremium() + ",'" + insurance.getRate() + "'," + insurance.isDistributionStatus() + ",'" + insurance.getTermsIDList()  + "','"
		+ insurance.getInsuranceClausePeriod() + "','" + insurance.getPrecaution() + "'," + insurance.isAuthorization()+");";
		super.create(query);
		}
	}
	public ResultSet retrieveById(String type) throws Exception {
		//쿼리 제조
		String query = "select * from Insurance where type ="+type+";";
		return super.retrieve(query);
	}
	public ResultSet retrieveByType(String insuranceID) throws Exception {
		//쿼리 제조
		String query = "select * from Insurance where insuranceID ="+insuranceID+";";
		return super.retrieve(query);
	}
	public ArrayList<Insurance> retrieveAll() throws Exception {
		String query = "select * from Insurance;";
		ResultSet results = super.retrieve(query);
        ArrayList<Insurance> insuranceList = new ArrayList<Insurance>();
        Insurance insurance;
        while (results.next()){
            insurance = new Insurance(); 
            insurance.setInsuranceID(results.getString("insuranceID"));
            insurance.setInsuranceName(results.getString("insuranceName"));
            insurance.setType(results.getString("type"));
            insurance.setMaxCompensation(results.getInt("maxCompensation"));
            insurance.setPeriodOfInsurance(results.getString("periodOfInsurance"));
            insurance.setPaymentCycle(results.getString("paymentCycle"));
            insurance.setPaymentPeriod(results.getString("paymentPeriod"));
            insurance.setAgeOfTarget(results.getString("ageOfTarget"));
            insurance.setBasicPremium(results.getInt("basicPremium"));
            insurance.setRate(results.getString("rate"));
            insurance.setTermsIDListFromDB(results.getString("TermsIDList"));
            insurance.setDistributionStatus(results.getBoolean("distributionStatus"));
            insurance.setInsuranceClausePeriod(results.getString("insuranceClausePeriod"));
            insurance.setAuthorization(results.getBoolean("authorization"));
            insurance.setPrecaution(results.getString("precaution"));
            insuranceList.add(insurance);
        }  
		return insuranceList;
	}

	public boolean update(Insurance insurance) throws Exception {
		//쿼리 제조
		String query =  "UPDATE Insurance SET insuranceName = '"+insurance.getInsuranceName()+"', type = '"+insurance.getType()+"', maxCompensation = "
		+insurance.getMaxCompensation()+", periodOfInsurance = '"+insurance.getPeriodOfInsurance()+"', paymentCycle = '"+insurance.getPaymentCycle()+"', "
        + "paymentPeriod = '"+insurance.getPaymentPeriod()+"', ageOfTarget = '"+insurance.getAgeOfTarget()+"', basicPremium = "+insurance.getBasicPremium()+", "
        + "rate = '"+insurance.getRate()+"', distributionStatus = "+insurance.isDistributionStatus()+", termsIDList = '"+insurance.getTermsIDList()+"', "
        + "insuranceClausePeriod = '"+insurance.getInsuranceClausePeriod()+"', precaution = '"+insurance.getPrecaution()+"', authorization = "+insurance.isAuthorization()+" WHERE insuranceID ='" +insurance.getInsuranceID()+"';";
		return super.update(query);
	}

	public boolean deleteById(String insuranceId) throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Insurance WHERE insuranceID="+insuranceId+";";
		return super.delete(query);
	}
	public boolean deleteAll() throws Exception {
		//쿼리 제조
		String query = "DELETE FROM Insurance;";
		return super.delete(query);
	}
}
