package Coupons.Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import Coupons.Enums.Category;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.Purchase;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.DateUtils;
import Coupons.Utils.NameUtils;

@Controller

public class PurchasesController {
	
	
	@Autowired
	private Coupons.DB.ICouponsDAO couponsDAO;

	@Autowired
	private Coupons.DB.IPurchasesDAO purchasesDAO;

	@Autowired
	private Coupons.DB.ICustomersDAO customerDAO;
	
	@Autowired
	private Coupons.DB.ICompaniesDAO companiesDAO;


	


	/**
	 * adds a new coupon puchase to the customer
	 * <p>
	 * this also checks the coupon is available, not out of date and is not already owned by the customer.
	 *
	 * @param coupon the coupon to be purchased by the customer
	 * @see 		DB.couponsDAO
	 * @throws		coupon out of stock!
	 * @throws		coupon out of date!
	 * @throws 		coupon already owned by customer!
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 5)
public void purchaseCoupon(Purchase purchase, UserData userData) {
	try
	{
		if (purchase == null || purchase.getCoupon() == null) {
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
		}
		if (purchase.getCustomer().getCustomerId()<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		if (!customerDAO.existsById(purchase.getCustomer().getCustomerId())) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		if (!userData.getType().name().equals("Customer")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (purchase.getCustomer().getCustomerId() != userData.getUserID()) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (purchase.getAmount()<1) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, ErrorType.INVALID_AMOUNT.getInternalMessage(), false);
		}
		Coupon couponDB = couponsDAO.findById(purchase.getCoupon().getCouponId()).get();
	
		if (couponDB.getAmount()<purchase.getAmount()) {
			throw new ApplicationException(ErrorType.NOT_ENOUGH_COUPONS_IN_STOCK, ErrorType.NOT_ENOUGH_COUPONS_IN_STOCK.getInternalMessage()+couponDB.getAmount(), false);
		}
		DateUtils.validateDates(couponDB.getstartDate(), couponDB.getendDate());
		if (couponDB.getCompany().getId()<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		if (!companiesDAO.existsById(couponDB.getCompany().getId())) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		}
		if (!couponsDAO.existsById(couponDB.getCouponId())) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		}
		if (couponDB.getPrice() <0)
			throw new ApplicationException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage(), false);
		
		NameUtils.isValidName(couponDB.getTitle());		
		couponDB.setAmount(couponDB.getAmount()-purchase.getAmount());

		// update coupon
		couponsDAO.save(couponDB);

		Customer customer = customerDAO.findById(purchase.getCustomer().getCustomerId()).get();
		purchase.setCoupon(couponDB);
		purchase.setCustomer(customer);
		purchase.setDate(new Date());

		purchasesDAO.save(purchase);

	}
	catch(Exception Ex){
		 System.out.println(Ex.getMessage());

	}
}

public void deleteCustomerPurchases(long customerId, UserData userData) throws ApplicationException {

		if (customerId<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		
		if (userData.getType().name().equals("Customer")) {
			if (customerId != userData.getUserID()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}		
		}
		if (userData.getType().name().equals("Company")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!customerDAO.existsById(customerId)) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		purchasesDAO.deleteById(customerId);

	
	
}

public void deleteCompanyPurchases(long companyId, UserData userData) throws ApplicationException {
	
		if (companyId<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		
		if (userData.getType().name().equals("Company")) {
			if (companyId != userData.getCompanyID()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}		
		}
		if (userData.getType().name().equals("Customer")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!companiesDAO.existsById(companyId)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		purchasesDAO.deleteById(companyId);

	
}

public void deletePurchase(long purchaseID, UserData userData) throws ApplicationException {

	if (purchaseID<1) {
		throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
	}
	if (!purchasesDAO.existsById(purchaseID)) {
		throw new ApplicationException(ErrorType.PURCHASE_ID_DOES_NOT_EXIST,ErrorType.PURCHASE_ID_DOES_NOT_EXIST.getInternalMessage(), false);
	}
	if (!userData.getType().name().equals("Customer")) {
		if (!purchasesDAO.existsByPurchaseIdAndCustomerCustomerId(purchaseID, userData.getUserID())) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH,ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
	}
	
	purchasesDAO.deleteById(purchaseID);

}
/*public void deleteCouponPurchases(long couponId, UserData userData)  throws ApplicationException{
	
		if (couponId<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!userData.getType().name().equals("Company"))
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		if (userData.getCompany() != couponsDAO.getOneCoupon(couponId).getCompany_id())
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		if (!couponsDAO.isCouponExists(couponId)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		purchasesDAO.deletePurchaseBycouponId(couponId);

	}

*/
/**
 * returns a list of all customer coupons 
 * @see 		DB.CouponDAO
 * @see			JavaBeans.Coupon
 * @see			DB.CustomerDAO
 * @return 		ArrayList of coupons belonging to this customer	
 * @throws ApplicationException 
 * @throws 		customer has no coupons
 */
	public List<Coupon> getCustomerCoupons(long customerID, UserData userData) throws ApplicationException {

			if (customerID<1) {
				throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			}
			
			if (userData.getType().name().equals("Customer")) {
				if (customerID != userData.getUserID()) {
					throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
				}		
			}
			if (userData.getType().name().equals("Company")) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}
			if (!customerDAO.existsById(customerID)) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

			}
			return couponsDAO.findByPurchasesCustomerCustomerId(customerID);
			

	}


public List<Purchase> getCustomerPurchases(long customerId, UserData userData) throws ApplicationException {
	// TODO Auto-generated method stub
	if (customerId<1) {
		throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
	}
	
	if (userData.getType().name().equals("Customer")) {
		if (customerId != userData.getUserID()) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}		
	}
	if (userData.getType().name().equals("Company")) {
		throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
	}
	if (!customerDAO.existsById(customerId)) {
		throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

	}
	return purchasesDAO.findByCustomerCustomerId(customerId);
}


public List<Purchase> getAllPurchases(UserData userData) throws ApplicationException {
	
	if (!userData.getType().name().equals("Administrator")) {
		
		throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		
	}
	// TODO Auto-generated method stub
	List<Purchase> purchases = new ArrayList<Purchase>();

	purchasesDAO.findAll().forEach(purchases::add);

	return purchases;

}


public int getCustomerPurchaseAmount(long customerId, UserData userData) throws ApplicationException {

	if (userData.getType().name().equals("Company")) {
		throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
	}
	
	if (userData.getType().name().equals("Customer")) {
		if (customerId != userData.getUserID())
			throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);

	}

	if (customerId<1) {
		throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
	}
	
	if (!customerDAO.existsById(customerId)) {
		throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
	}
	

	List<Purchase> purchases = purchasesDAO.findByCustomerCustomerId(customerId);

	int amount = 0;

	for (int i = 0; i < purchases.size(); i++) {
		amount += purchases.get(i).getAmount();
	}

	return amount;

}



	
}
