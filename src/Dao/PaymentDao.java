package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Contract.Payment;

public class PaymentDao extends Dao {

	public PaymentDao() throws Exception {
		super.connect();
	}

	public void create(Payment payment) {
		String query = "INSERT INTO Payment (customerID, insuranceID, dateOfPayment, whetherPayment) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			// 값 설정
			statement.setString(1, payment.getCustomerID());
			statement.setString(2, payment.getInsuranceID());
			statement.setObject(3, payment.getDateOfPayment());
			statement.setBoolean(4, payment.isWhetherPayment());

			// 실행
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Payment> retrieveAll() {
		List<Payment> paymentList = new ArrayList<>();
		String query = "SELECT * FROM Payment";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				// 결과에서 값을 읽어와 Payment 객체 생성 및 추가
				Payment payment = new Payment();
				payment.setCustomerID(resultSet.getString("customerID"));
				payment.setInsuranceID(resultSet.getString("insuranceID"));
				payment.setDateOfPayment(resultSet.getObject("dateOfPayment", LocalDate.class));
				payment.setWhetherPayment(resultSet.getBoolean("whetherPayment"));

				paymentList.add(payment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paymentList;
	}

	public List<Payment> retrieveCustomerPayment(String customerID) {
		List<Payment> paymentList = new ArrayList<>();
		String query = "SELECT * FROM Payment WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				// 결과에서 값을 읽어와 Payment 객체 생성 및 추가
				Payment payment = new Payment();
				payment.setCustomerID(resultSet.getString("customerID"));
				payment.setInsuranceID(resultSet.getString("insuranceID"));
				payment.setDateOfPayment(resultSet.getObject("dateOfPayment", LocalDate.class));
				payment.setWhetherPayment(resultSet.getBoolean("whetherPayment"));

				paymentList.add(payment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paymentList;
	}

	public void update(Payment payment) {
		String query = "UPDATE Payment SET insuranceID = ?, dateOfPayment = ?, whetherPayment = ? WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			// 값 설정
			statement.setString(1, payment.getInsuranceID());
			statement.setObject(2, payment.getDateOfPayment());
			statement.setBoolean(3, payment.isWhetherPayment());
			statement.setString(4, payment.getCustomerID());

			// 실행
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateWhetherPayment(String customerID, String insuranceID, boolean whetherPayment) {
		String query = "UPDATE Payment SET whetherPayment = ? WHERE customerID = ? AND insuranceID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			// 값 설정
			statement.setBoolean(1, whetherPayment);
			statement.setString(2, customerID);
			statement.setString(3, insuranceID);

			// 실행
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> retrieveUnpaidCustomerId() {
		List<String> unpaidCustomerIds = new ArrayList<>();
		String query = "SELECT customerID FROM Payment WHERE whetherPayment = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setBoolean(1, false);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				unpaidCustomerIds.add(resultSet.getString("customerID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return unpaidCustomerIds;
	}

	public Payment retrieveDateStatusById(String customerID, String insuranceID) {
		Payment payment = null;
		String query = "SELECT dateOfPayment, whetherPayment FROM Payment WHERE customerID = ? AND insuranceID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			statement.setString(2, insuranceID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				payment = new Payment();
				payment.setDateOfPayment(resultSet.getObject("dateOfPayment", LocalDate.class));
				payment.setWhetherPayment(resultSet.getBoolean("whetherPayment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return payment;
	}
}
