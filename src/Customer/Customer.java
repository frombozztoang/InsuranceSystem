package Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {

   private static final long serialVersionUID = 1L;
   private String address;
   private int customerID;
   private String customerName;
   private String job;
   private String pnumber;
   private String SSN;
   private ArrayList<Customer> customerList;
   
   // composition
   public FamilyHistory familyHistory;


   public Customer() {
	   this.familyHistory = new FamilyHistory();
   }

   public boolean deleteCustomer(int customerID) {
      Customer customerToRemove = null;
      for (Customer customer : customerList) {
         if (customer.getCustomerID() == customerID) {
            customerToRemove = customer;
            break;
         }
      }
      if (customerToRemove != null) {
         customerList.remove(customerToRemove);
         return true;
      } else {
         return false;
      }
   }

   public ArrayList<Customer> retrieveMaturityCustomer() {
      ArrayList<Customer> maturityCustomers = new ArrayList<>();
      // 만기 계약이 있는 고객을 목록에 추가
      for (Customer customer : customerList) {
         if (customer.hasMaturityContract()) {
            maturityCustomers.add(customer);
         }
      }

      return maturityCustomers;
   }

   private boolean hasMaturityContract() {
      return false;
   }

   public ArrayList<Customer> retrieveNonPaymentCustomer() {
      ArrayList<Customer> nonPaymentCustomers = new ArrayList<>();
      // 미결제 고객을 목록에 추가
      for (Customer customer : customerList) {
         if (customer.hasOutstandingPayment()) {
            nonPaymentCustomers.add(customer);
         }
      }

      return nonPaymentCustomers;
   }

   private boolean hasOutstandingPayment() {
      return false;
   }
   public ArrayList<Customer> retrieveResurrectionCustomer() {
      ArrayList<Customer> resurrectionCustomers = new ArrayList<>();
      // 목록에 부활 대상 고객 추가
      for (Customer customer : customerList) {
         if (customer.isEligibleForResurrection()) {
            resurrectionCustomers.add(customer);
         }
      }

      return resurrectionCustomers;
   }

   private boolean isEligibleForResurrection() {
      return false;
   }

   public boolean updateCustomer(Customer updatedCustomer) {
      for (Customer customer : customerList) {
         if (customer.getCustomerID() == updatedCustomer.getCustomerID()) {
            // customer 정보를 업데이트한다.
            customer.setCustomerName(updatedCustomer.getCustomerName());
            customer.setPnumber(updatedCustomer.getPnumber());
            // 성공적으로 업데이트
            return true;
         }
      }
//customer가 없거나 update가 성공적이지 않음.
      return false;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public int getCustomerID() {
      return customerID;
   }

   public void setCustomerID(int customerID) {
      this.customerID = customerID;
   }

   public String getCustomerName() {
      return customerName;
   }

   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }

   public String getJob() {
      return job;
   }

   public void setJob(String job) {
      this.job = job;
   }

   public String getPnumber() {
      return pnumber;
   }

   public void setPnumber(String pnumber) {
      this.pnumber = pnumber;
   }

   public String getSSN() {
      return SSN;
   }

   public void setSSN(String SSN) {
      this.SSN = SSN;
   }
}