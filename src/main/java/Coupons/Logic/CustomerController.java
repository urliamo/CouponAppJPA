package Coupons.Logic;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.FightResults;
import Coupons.JavaBeans.Purchase;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.EmailUtils;
import Coupons.Utils.NameUtils;



@Controller

public class CustomerController{
	
	@Autowired
	private Coupons.DB.ICustomersDAO customerDAO;
	@Autowired
	private Coupons.DB.ICouponsDAO couponsDAO;

	@Autowired
	private Coupons.DB.IUsersDAO usersDAO;
	
	@Autowired
	private Coupons.DB.IPurchasesDAO purchasesDAO;
	
	public CustomerController() {
		super();
	}


	/**
	 * returns the DB data of this customer
	 * 
	 * @see 		DB.customerDBDAO
	 * @see			JavaBeans.Customer
	 * @return 		customer object with this customers' data
	 */
	public Customer getCustomerByID(long customerID, UserData userData) throws ApplicationException{
		if (userData == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		if (!userData.getType().name().equals("Administrator")) {
			if (customerID != userData.getUserID()) {
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
			}
		}
		if (!customerDAO.existsById(customerID)) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		return  customerDAO.findById(customerID).get();	
		
	}
	
	
	public void deleteCustomer(long customerId, UserData userData) {
		try
		{
			if (userData == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (!userData.getType().name().equals("Administrator")) {
				if (customerId != userData.getUserID()) {
					throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
				}
			}
			if (!customerDAO.existsById(customerId))
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

			if (!usersDAO.existsById(customerId))
				throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(),false);
			customerDAO.deleteById(customerId);
		}
		catch(Exception Ex){
			 System.out.println(Ex.getMessage());

		}
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 5)
	public void createCustomer(Customer customer) throws ApplicationException {
		try
			{
			if (customer == null) {
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage() ,false);
			}
			if (customer.getUser() == null) {
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
			}
			NameUtils.isValidName(customer.getFirstName());
			NameUtils.isValidName(customer.getLastName());
			User customerUser= customer.getUser();
 			long userID = usersDAO.save(customerUser).getId();
			customer.setCustomerId(userID);
			customer.setEligibile(true);
			customer.getUser().setId(userID);
			customerDAO.save(customer);
			}
		
		catch(Exception Ex){
			System.out.println(Ex.getMessage());
			}

		}
	public void updateCustomer(Customer customer, UserData userData) throws ApplicationException {
		try
			{
			if (userData == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (customer == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (customer.getUser() == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (!userData.getType().name().equals("Administrator")) {
				if (customer.getCustomerId() != userData.getUserID()) {
					throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
				}
			}
			if (userData.getType().name().equals("Administrator")|| userData.getType().name().equals("Company"))
				throw new ApplicationException(ErrorType.FIELD_IS_IRREPLACEABLE,ErrorType.FIELD_IS_IRREPLACEABLE.getInternalMessage(), false);
			
			NameUtils.isValidName(customer.getFirstName());
			NameUtils.isValidName(customer.getLastName());
			

			if (customer.getCustomerId() < 1) {
				throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
				
			}
			User innerUser = customer.getUser();

			EmailUtils.isValidEmail(innerUser.getEmail());
			if (innerUser.getId() < 1) {
				throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
				
			}
			NameUtils.isValidName(innerUser.getUserName());

			if (!customerDAO.existsById(customer.getCustomerId())) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
			}
			
			User userFromDataBase = usersDAO.findById(customer.getCustomerId()).get();

			if (!userFromDataBase.getUserName().equals(customer.getUser().getUserName())) {

				if (usersDAO.existsByUserName(customer.getUser().getUserName()))

					throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS,ErrorType.NAME_IS_ALREADY_EXISTS.getInternalMessage(), false);

			}

			usersDAO.save(innerUser);
			customerDAO.save(customer);
			}
		
		catch(Exception Ex){
			System.out.println(Ex.getMessage());
			}

		}
	
	
	
	public String getCustomerName(long customerId, UserData userData) throws ApplicationException {
		if (userData == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		if (!userData.getType().name().equals("Administrator")) {
			if (customerId != userData.getUserID()) {
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
			}
		}
		if (!customerDAO.existsById(customerId))
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		if (!usersDAO.existsById(userData.getUserID()))
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		Customer customer = customerDAO.findById(customerId).get();
		String name = customer.getFirstName()+" "+customer.getLastName();

		return name;

	}

	public Customer getOpponentById(UserData userData) throws ApplicationException {
		// TODO Auto-generated method stub
		if (userData == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		if (!userData.getType().name().equals("Customer")) {
		
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
			
		}
		if (!customerDAO.isCustomerEligible(userData.getUserID())) {
			throw new ApplicationException(ErrorType.CUSTOMER_NOT_ELIGIBLE, ErrorType.CUSTOMER_NOT_ELIGIBLE.getInternalMessage(), true);

		}
		
		List<Customer> customers = new ArrayList<Customer>();

		customerDAO.getOpponents(userData.getUserID()).forEach(customers::add);
	
		if (customers.isEmpty()){
			throw new ApplicationException(ErrorType.NO_OPPONENTS_AVAILABLE, ErrorType.NO_OPPONENTS_AVAILABLE.getInternalMessage(), true);

		}
			
		Customer opponent = customers.get((int) Math.random()*customers.size());
		
		return opponent;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 10)
	public FightResults fightOpponent(long opponentId, UserData userData) throws ApplicationException {
		// TODO Auto-generated method stub
		if (userData == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		if (!userData.getType().name().equals("Customer")) {
		
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
			
		}
		if (!customerDAO.isCustomerEligible(userData.getUserID())) {
			throw new ApplicationException(ErrorType.CUSTOMER_NOT_ELIGIBLE, ErrorType.CUSTOMER_NOT_ELIGIBLE.getInternalMessage(), false);

		}
		if (opponentId < 1 || userData.getUserID()<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!customerDAO.existsById(opponentId) || !customerDAO.existsById(userData.getUserID()))
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		List<Coupon> opponentCoupons = new ArrayList<Coupon>();
		couponsDAO.findByPurchasesCustomerCustomerId(opponentId).forEach(opponentCoupons::add);
		List<Coupon> customerCoupons = new ArrayList<Coupon>();
		couponsDAO.findByPurchasesCustomerCustomerId(userData.getUserID()).forEach(customerCoupons::add);
		for (Coupon c: customerCoupons) {
			if (c.getAmount()<1) {
				customerCoupons.remove(c);
			}
		}
		if (customerCoupons.isEmpty()){
			throw new ApplicationException(ErrorType.NO_VALID_COUPONS_TO_USE, ErrorType.NO_VALID_COUPONS_TO_USE.getInternalMessage(), true);

		}
		Coupon customerCoupon = customerCoupons.get((int) Math.random()*customerCoupons.size());
		Coupon opponentCoupon = opponentCoupons.get((int) Math.random()*customerCoupons.size());
		if (customerCoupon.getPrice()>opponentCoupon.getPrice()) {
		Purchase bonus = new Purchase();
		bonus.setAmount(1);
		bonus.setCoupon(customerCoupon);
		bonus.setCustomer(customerDAO.findById(userData.getUserID()).get());
		bonus.setDate(new Date());
		purchasesDAO.save(bonus);
		}
		FightResults fightResults = new FightResults(customerCoupon,opponentCoupon);
		customerDAO.setCustomerEligibile(false, userData.getUserID());
		return fightResults;
	}
	
	
	public List<Customer> getAllCustomers(UserData userData) throws ApplicationException {
		// TODO Auto-generated method stub
		if (userData == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		if (!userData.getType().name().equals("Administrator")) {
		
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);
			
		}
		
		
		List<Customer> customers = new ArrayList<Customer>();

		customerDAO.findAll().forEach(customers::add);

		return customers;
	}
	
}