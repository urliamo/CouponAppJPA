package Coupons.Logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Company;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.EmailUtils;
import Coupons.Utils.NameUtils;


/**
 * object returned when user logs in as admin. in charge of login and DAO actions for admins. 
 *
 * @see         JavaBeans.Company
 * @see 		JavaBeans.Customer
 */

@Controller
public class CompanyController {

	@Autowired
	private Coupons.DB.CompaniesDAO companiesDAO;
	@Autowired
	private Coupons.DB.IPurchasesDAO purchasesDAO;
	
	@Autowired
	private Coupons.DB.IUsersDAO usersDAO;
	
	@Autowired
	private Coupons.DB.CouponsDAO couponsDAO;

	/**
	 *adds a new company to the DB using the DAO.
	 *
	 * @param  company the new company to be added to the DB.
	 * @see 		DB.companiesDAO
	 * @see 		JavaBeans.Company
	 * @throws company already exists!
	 */
	public long addCompany(Company company,UserData userData) throws ApplicationException{
		if (company == null) 
		{
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), true);
		}
		if (!userData.getType().name().equals("Administrator")) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), false);

		}

		
		NameUtils.isValidName(company.getName());
		EmailUtils.isValidEmail(company.getEmail());

		if (company.getCompanyID() != null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_MUST_BE_ASSIGNED, ErrorType.COMPANY_ID_MUST_BE_ASSIGNED.getInternalMessage(), true);
		}
		
		if (companiesDAO.isCompanyMailExist(company.getEmail())) {
			throw new ApplicationException(ErrorType.EXISTING_EMAIL, ErrorType.EXISTING_EMAIL.getInternalMessage(), false);
		}
		if (companiesDAO.isCompanyNameExist(company.getName())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, ErrorType.NAME_IS_ALREADY_EXISTS.getInternalMessage(), false);
		}
			return companiesDAO.addCompany(company);
		
	}
	
	/**
	 *updates an existing company in the DB using the DAO.
	 *
	 * @param  company the company to be updates
	 * @see 		companiesDAO
	 * @see 		JavaBeans.Company
	 * @throws 		company does not exist!
	 * @throws 		company name cannot be updated!
	 */
	public void updateCompany(Company company,UserData userData) throws ApplicationException{
		if (company == null) 
		{
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
		}
		if (!userData.getType().name().equals("Administrator")) {
			if (userData.getCompany()!=company.getCompanyID()) {
			throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
		}
		}
		NameUtils.isValidName(company.getName());
		EmailUtils.isValidEmail(company.getEmail());
		if (company.getCompanyID() < 1|| company.getCompanyID()==null) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (companiesDAO.isCompanyMailExist(company.getEmail())) {
			throw new ApplicationException(ErrorType.EXISTING_EMAIL, ErrorType.EXISTING_EMAIL.getInternalMessage(), false);
		}
		if (companiesDAO.isCompanyNameExist(company.getName())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, ErrorType.NAME_IS_ALREADY_EXISTS.getInternalMessage(), false);
		}
		if (!companiesDAO.isCompanyExists(company.getCompanyID())) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
				
		if (companiesDAO.getCompanyByID(company.getCompanyID()).getName()!= company.getName()) {
			throw new ApplicationException(ErrorType.NAME_IS_IRREPLACEABLE, ErrorType.NAME_IS_IRREPLACEABLE.getInternalMessage(), false);
		}
		else {
			companiesDAO.updateCompany(company);
		}
			
	
	}
	
	/**
	 *removes an existing company from the DB using the DAO.
	 *<P>
	 *this also removes any coupons belonging to the company.
	 *
	 * @param  company the company to be removed
	 * @see 		companiesDAO
	 * @see 		JavaBeans.Company
	 * @throws 		company does not exist!
	 */
		public void deleteCompany(long companyId, UserData userData) throws ApplicationException{
			try {
				
				if (!userData.getType().name().equals("Administrator")) {
					if (userData.getCompany()!=companyId) {
					throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
					}
				}
				
				if (companyId < 1) {
					throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
					
				}
				if (!companiesDAO.isCompanyExists(companyId)) {
					throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
				}
				
				//remove company coupon purchases 
				purchasesDAO.deleteCompanyPurchases(companyId);
				//remove company coupons 
				couponsDAO.deleteCompanyCoupons(companyId);
				//remove company users
				usersDAO.deleteCompanysUsers(companyId);
				//remove company from DB
				companiesDAO.deleteCompany(companyId);
				
				
			}
			catch(Exception Ex) {
				 System.out.println(Ex.getMessage());

			}
		}
		
		/**
		 *	returns an ArrayList of Company objects with all companies using the DAO.
		 *
		 * @see 		companiesDAO
		 * @see 		JavaBeans.Company
		 * @return		ArrayList of all companies
		 */
		public List<Company> getAllCompanies(UserData userData) throws ApplicationException{
			if (!userData.getType().name().equals("Administrator")) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

			}
			return companiesDAO.getAllCompanies();
		}
		
		/**
		 *	returns a company of the specified ID
		 *
		 * @param		companyID long containing the ID of the company to be returned
		 * @see 		companiesDAO
		 * @see 		JavaBeans.Company
		 * @return		Company object with the company data of the specified ID.
		 */
		public Company getCompany(long id, UserData userData) throws ApplicationException{
			if (!userData.getType().name().equals("Administrator")) {
				if (userData.getCompany()!=id) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
				}
			}
			if (id < 1) {
				throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
				
			}
			
			if  (!companiesDAO.isCompanyExists(id)) {
				throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST,ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
				}
			return companiesDAO.getCompanyByID(id);
		}

		
 	}

