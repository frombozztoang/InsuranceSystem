package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Contract.Contract;

public class ContractDao extends Dao {

	public ContractDao() throws Exception {
		super.connect();
	}

	public void create(Contract contract) {
		String query = "INSERT INTO contracts (customerID, insuranceID, insurancePeriod, premium, paymentCycle, "
				+ "maxCompensation, dateOfSubscription, dateOfMaturity, maturity, resurrection, cancellation) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		// PreparedStatement 객체를 사용하여 해당 위치에 실제 값을 설정, SQL Injection 공격을 방지
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, contract.getCustomerID());
			statement.setString(2, contract.getInsuranceID());
			statement.setString(3, contract.getInsurancePeriod());
			statement.setDouble(4, contract.getPremium());
			statement.setString(5, contract.getPaymentCycle());
			statement.setDouble(6, contract.getMaxCompensation());
			statement.setDate(7, java.sql.Date.valueOf(contract.getDateOfSubscription()));
			statement.setDate(8, java.sql.Date.valueOf(contract.getDateOfMaturity()));
			statement.setBoolean(9, contract.isMaturity());
			statement.setBoolean(10, contract.isResurrection());
			statement.setBoolean(11, contract.isCancellation());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contract> retrieveByCustomerId(String customerId) {
		List<Contract> contracts = new ArrayList<>();
		String query = "SELECT * FROM contract WHERE customerID = ?";

		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Contract contract = new Contract();
				contract.setCustomerID(resultSet.getString("customerID"));
				contract.setInsuranceID(resultSet.getString("insuranceID"));
				contract.setInsurancePeriod(resultSet.getString("insurancePeriod"));
				contract.setPremium(resultSet.getInt("premium"));
				contract.setPaymentCycle(resultSet.getString("paymentCycle"));
				contract.setMaxCompensation(resultSet.getInt("maxCompensation"));
				String dateString = resultSet.getString("dateOfSubscription");
				LocalDate dateOfSubscription = LocalDate.parse(dateString);
				contract.setDateOfSubscription(dateOfSubscription);
				String dateStringMaturity = resultSet.getString("dateOfMaturity");
				LocalDate dateOfMaturity = LocalDate.parse(dateStringMaturity);
				contract.setDateOfMaturity(dateOfMaturity);
				contract.setMaturity(resultSet.getBoolean("maturity"));
				contract.setResurrection(resultSet.getBoolean("resurrection"));
				contract.setCancellation(resultSet.getBoolean("cancellation"));

				contracts.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contracts;
	}

	public List<Contract> retrieveByInsuranceId(String insuranceId) {
		List<Contract> contracts = new ArrayList<>();
		String query = "SELECT * FROM contract WHERE insuranceID = ?";

		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, insuranceId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Contract contract = new Contract();
				contract.setCustomerID(resultSet.getString("customerID"));
				contract.setInsuranceID(resultSet.getString("insuranceID"));
				contract.setInsurancePeriod(resultSet.getString("insurancePeriod"));
				contract.setPremium(resultSet.getInt("premium"));
				contract.setPaymentCycle(resultSet.getString("paymentCycle"));
				contract.setMaxCompensation(resultSet.getInt("maxCompensation"));
				String dateString = resultSet.getString("dateOfSubscription");
				LocalDate dateOfSubscription = LocalDate.parse(dateString);
				contract.setDateOfSubscription(dateOfSubscription);
				String dateStringMaturity = resultSet.getString("dateOfMaturity");
				LocalDate dateOfMaturity = LocalDate.parse(dateStringMaturity);
				contract.setDateOfMaturity(dateOfMaturity);
				contract.setMaturity(resultSet.getBoolean("maturity"));
				contract.setResurrection(resultSet.getBoolean("resurrection"));
				contract.setCancellation(resultSet.getBoolean("cancellation"));

				contracts.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contracts;
	}

	public void update(Contract contract) {
		String query = "UPDATE contract SET insurancePeriod = ?, premium = ?, paymentCycle = ?, "
				+ "maxCompensation = ?, dateOfSubscription = ?, dateOfMaturity = ?, maturity = ?, "
				+ "resurrection = ?, cancellation = ? WHERE customerID = ? AND insuranceID = ?";

		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, contract.getInsurancePeriod());
			statement.setDouble(2, contract.getPremium());
			statement.setString(3, contract.getPaymentCycle());
			statement.setDouble(4, contract.getMaxCompensation());
			statement.setDate(5, java.sql.Date.valueOf(contract.getDateOfSubscription()));
			statement.setDate(6, java.sql.Date.valueOf(contract.getDateOfMaturity()));
			statement.setBoolean(7, contract.isMaturity());
			statement.setBoolean(8, contract.isResurrection());
			statement.setBoolean(9, contract.isCancellation());
			statement.setString(10, contract.getCustomerID());
			statement.setString(11, contract.getInsuranceID());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCancellation(String customerId, String insuranceId, boolean cancellation) {
		String query = "UPDATE contract SET cancellation = ? WHERE customerID = ? AND insuranceID = ?";

		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setBoolean(1, cancellation);
			statement.setString(2, customerId);
			statement.setString(3, insuranceId);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contract> retrieveByCustomerInsuranceId(String customerId, String insuranceId) {
		List<Contract> contracts = new ArrayList<>();
		String query = "SELECT * FROM contract WHERE customerID = ? AND insuranceID = ?";

		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerId);
			statement.setString(2, insuranceId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Contract contract = new Contract();
				contract.setCustomerID(resultSet.getString("customerID"));
				contract.setInsuranceID(resultSet.getString("insuranceID"));
				contract.setInsurancePeriod(resultSet.getString("insurancePeriod"));
				contract.setPremium(resultSet.getInt("premium"));
				contract.setPaymentCycle(resultSet.getString("paymentCycle"));
				contract.setMaxCompensation(resultSet.getInt("maxCompensation"));
				String dateString = resultSet.getString("dateOfSubscription");
				LocalDate dateOfSubscription = LocalDate.parse(dateString);
				contract.setDateOfSubscription(dateOfSubscription);
				String dateStringMaturity = resultSet.getString("dateOfMaturity");
				LocalDate dateOfMaturity = LocalDate.parse(dateStringMaturity);
				contract.setDateOfMaturity(dateOfMaturity);
				contract.setMaturity(resultSet.getBoolean("maturity"));
				contract.setResurrection(resultSet.getBoolean("resurrection"));
				contract.setCancellation(resultSet.getBoolean("cancellation"));

				contracts.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contracts;
	}

	public ArrayList<Contract> retrieveAll() throws SQLException {
		String query = "select * from contract;";
		ResultSet results = super.retrieve(query);
		ArrayList<Contract> contractList = new ArrayList<Contract>();

		while (results.next()) {
			Contract contract = new Contract();
			contract.setCustomerID(results.getString("customerID"));
			contract.setInsuranceID(results.getString("insuranceID"));
			contract.setInsurancePeriod(results.getString("insurancePeriod"));
			contract.setPremium(results.getInt("premium"));
			contract.setPaymentCycle(results.getString("paymentCycle"));
			contract.setMaxCompensation(results.getInt("maxCompensation"));
			String dateString = results.getString("dateOfSubscription");
			LocalDate dateOfSubscription = LocalDate.parse(dateString);
			contract.setDateOfSubscription(dateOfSubscription);
			String dateStringMaturity = results.getString("dateOfMaturity");
			LocalDate dateOfMaturity = LocalDate.parse(dateStringMaturity);
			contract.setDateOfMaturity(dateOfMaturity);
			contract.setMaturity(results.getBoolean("maturity"));
			contract.setResurrection(results.getBoolean("resurrection"));
			contract.setCancellation(results.getBoolean("cancellation"));

			contractList.add(contract);
		}
		return contractList;
	}

}
