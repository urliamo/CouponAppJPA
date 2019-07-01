package Coupons.Logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.Enums.Category;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.DateUtils;
import Coupons.Utils.NameUtils;


/**
 * object returned when user logs in as Company. in charge of login and DAO actions for companies. 
 *
 * @param  companyID int containing the unique ID of the current company using this object instance.
 * @see         JavaBeans.Company
 * @see 		JavaBeans.Coupon
 * @see			Logic.LoginManager
 */
@Controller

public class CouponController {

	@Autowired
	private Coupons.DB.IPurchasesDAO purchasesDAO;
	
	@Autowired
	private Coupons.DB.CompaniesDAO companiesDAO;

	@Autowired
	private Coupons.DB.CouponsDAO couponsDAO;
	
	@Autowired
	private Coupons.DB.CustomerDAO customersDAO;

	public CouponController() {
		super();
	}

	/**
	 * adds a new coupon to the DB.
	 * <p> 
	 * this also checks for coupon validity of date or duplication.
	 *
	 * @param  coupon the new coupon to be added to the DB
	 * @exception coupon already exists
	 * @exception coupon starts after it expired
	 * @exception coupon already expired
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	
	public void addCoupon(Coupon coupon, UserData userData) throws ApplicationException{
			
			validateCoupon(coupon);
			if (couponsDAO.isCompanyCouponTitleExist(coupon.getcompanyId(), coupon.getTitle())) {
				throw new ApplicationException(ErrorType.EXISTING_COUPON_TITLE, ErrorType.EXISTING_COUPON_TITLE.getInternalMessage(), false);
			}
			if (!userData.getType().name().equals("Company"))
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

			if (userData.getCompany() != coupon.getcompanyId())
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			
			if (coupon.getId()!=null) {
				throw new ApplicationException(ErrorType.EXISTING_COUPON_ID, ErrorType.EXISTING_COUPON_ID.getInternalMessage(), false);
			}
			
			//add coupon
		couponsDAO.addCoupon(coupon);
		
		
	}
	
	/**
	 * updates an existing coupon in the DB.
	 *
	 * @param  coupon the  coupon to be added updated in the DB
	 * @exception coupon does not exist
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void updateCoupon(Coupon coupon, UserData userData) throws ApplicationException{
		validateCoupon(coupon);
		if (coupon.getId()<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		if (!couponsDAO.isCouponExists(coupon.getId())) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		}
		if (!userData.getType().name().equals("Company")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (userData.getCompany() != coupon.getcompanyId()) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!couponsDAO.isCouponExists(coupon.getId())) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
				//update coupon
		couponsDAO.updateCoupon(coupon);
	
		
	}
	
	/**
	 * removes a coupon from the DB.
	 * <p>
	 * this also removes any coupons purchased by customers
	 * 
	 * 
	 * @param  coupon the coupon to be removed from the DB
	 * @exception coupon does not exist!
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCoupon(long couponID, UserData userData) throws ApplicationException {
		if (couponID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!userData.getType().name().equals("Company"))
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		if (userData.getCompany() != couponsDAO.getOneCoupon(couponID).getcompanyId())
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

			//check if coupon actually exists
			if (!couponsDAO.isCouponExists(couponID)) {
				throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(), false);
			}
			
		//delete coupon customer purchases
		purchasesDAO.deletePurchaseBycouponId(couponID);
		//delete company coupon
		couponsDAO.deleteCoupon(couponID);
	}
	

	/**
	 * removes a coupon from the DB.
	 * <p>
	 * this also removes any coupons purchased by customers
	 * 
	 * 
	 * @param  coupon the coupon to be removed from the DB
	 * @exception coupon does not exist!
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCompanyCoupons(long companyID, UserData userData) throws ApplicationException {
		if (companyID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!userData.getType().name().equals("Company"))
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		if (userData.getCompany() != companyID)
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

			
			if (!companiesDAO.isCompanyExists(companyID)) {
				throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
			}
			
		//delete coupon customer purchases
		purchasesDAO.deleteCompanyPurchases(companyID);
		//delete company coupons
		couponsDAO.deleteCompanyCoupons(companyID);
	}
	/**
	 * returns all coupons belonging to this company.
	 * 
	 * @param  coupon the new coupon to be added to the DB
	 * @see 		companiesDAO
	 * @return ArrayList of coupon objects belonging to this company
	 */
	
	public Coupon getCoupon(long couponID) throws ApplicationException{
		if (couponID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		//check if coupon actually exists
		if (!couponsDAO.isCouponExists(couponID)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		
		return couponsDAO.getOneCoupon(couponID);
	}
	
	/**
	 * returns a list of all company coupon IDs of a 
	 * 
	 * @param  companyID the ID of the company coupons to return
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	
	/*public List<Long> getCompanyCouponIDs(long companyID, UserData userData) throws ApplicationException{
		if (companyID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (userData.getCompany() != companyID)
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		if (companiesDAO.isCompanyExists(companyID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		return couponsDAO.getCompanyCouponsID(companyID);
	}*/
	
	/**
	 * returns a list of all  coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returnes
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	public List<Coupon> getCouponsByCategory(Category category) throws ApplicationException
	{
		//get list of all company coupons
		
		List<Coupon> coupons = couponsDAO.getAllCouponsByCategory(category);

		//remove coupons with different category from list
		
		return coupons;

	}
	
	public List<Coupon> getAllCoupons() throws ApplicationException {

		// only customer can see coupon list for purchase
		return couponsDAO.getAllCoupons();

	}
	/**
	 * returns a list of all company coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returned
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	public List<Coupon> getCompanyCouponsByCategory(long companyID, Category category, UserData userData) throws ApplicationException
	{
		//get list of all company coupons
		if (companyID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		
		if (userData.getType().name().equals("Company")) {
			if (companyID != userData.getCompany()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}		
		}
		if (userData.getType().name().equals("Customer")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!companiesDAO.isCompanyExists(companyID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		if (category == null) {
			throw new ApplicationException(ErrorType.INVALID_CATEGORY, ErrorType.INVALID_CATEGORY.getInternalMessage(), false);
		}
		return couponsDAO.getCompanyCouponsByCategory(companyID, category);
	}
	/**
	 * returns a list of all company coupons of a specified max price
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public List<Coupon> getCompanyCouponsByMaxPrice(long companyID, double maxPrice, UserData userData) throws ApplicationException{
		if (companyID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		
		if (userData.getType().name().equals("Company")) {
			if (companyID != userData.getCompany()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}		
		}
		if (userData.getType().name().equals("Customer")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!companiesDAO.isCompanyExists(companyID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		if (maxPrice <0) {
			throw new ApplicationException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage(), false);
		}
		return couponsDAO.getCompanyCouponsByMaxPrice(companyID, maxPrice);
	}
	/**
	 * returns a list of all company coupons
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public List<Coupon> getCompanyCoupons(long companyID, UserData userData) throws ApplicationException{
		if (companyID<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
		}
		
		if (userData.getType().name().equals("Company")) {
			if (companyID != userData.getCompany()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}		
		}
		if (userData.getType().name().equals("Customer")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		if (!companiesDAO.isCompanyExists(companyID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		List<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);

		return coupons;
	}

	private void validateCoupon(Coupon coupon) throws ApplicationException {
		
		DateUtils.validateDates(coupon.getstartDate(), coupon.getendDate());
		if (coupon.getcompanyId()<1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!companiesDAO.isCompanyExists(coupon.getcompanyId())) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		}
	
		if (coupon.getPrice() < 0)
			throw new ApplicationException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage(), false);
		if (coupon.getCategory() == null)
			throw new ApplicationException(ErrorType.INVALID_CATEGORY, ErrorType.INVALID_CATEGORY.getInternalMessage(), false);
		if (!(coupon.getImage().contains(".")) || coupon.getImage().charAt(coupon.getImage().length() - 1) == '.' || coupon.getImage().charAt(0) == '.')
			throw new ApplicationException(ErrorType.INVALID_IMAGE, ErrorType.INVALID_IMAGE.getInternalMessage(), false);

		NameUtils.isValidName(coupon.getTitle());
		
	}
	
	/**
	 * returns a list of all company coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returned
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	public List<Coupon> getCustomerCouponsByCategory(long customerID, Category category, UserData userData) throws ApplicationException
	{
		//get list of all company coupons
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
		if (!customersDAO.isCustomerIDExist(customerID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		if (category == null) {
			throw new ApplicationException(ErrorType.INVALID_CATEGORY, ErrorType.INVALID_CATEGORY.getInternalMessage(), false);
		}
		return couponsDAO.getCustomerCouponsByCategory(customerID, category);
	}
	/**
	 * returns a list of all company coupons of a specified max price
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public List<Coupon> getCustomerCouponsByMaxPrice(long customerID, double maxPrice, UserData userData) throws ApplicationException{
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
	
		if (maxPrice <0) {
			throw new ApplicationException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage(), false);
		}
		if (!customersDAO.isCustomerIDExist(customerID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		return couponsDAO.getCustomerCouponsByMaxPrice(customerID, maxPrice);
	}
	/**
	 * returns a list of all company coupons
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public List<Coupon> getCustomerCoupons(long customerID, UserData userData) throws ApplicationException{
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
		if (!customersDAO.isCustomerIDExist(customerID)) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);

		}
		List<Coupon> coupons = couponsDAO.getCustomerCoupons(customerID);

		return coupons;
	}
}
