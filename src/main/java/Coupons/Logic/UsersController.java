package Coupons.Logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.DB.ICompaniesDAO;
import Coupons.DB.IUsersDAO;
import Coupons.Enums.ClientType;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.NameUtils;
import Coupons.Utils.PasswordUtils;

//import com.avi.coupons.beans.User;
//import com.avi.coupons.beans.UserData;
//import com.avi.coupons.dao.CustomersDAO;
//import com.avi.coupons.dao.UsersDAO;
//import com.avi.coupons.enums.ClientType;
//import com.avi.coupons.enums.ErrorType;
//import com.avi.coupons.exceptions.ApplicationException;
//import com.avi.coupons.utils.IdUtils;
//import com.avi.coupons.utils.NameUtils;
//import com.avi.coupons.utils.PasswordUtils;
//import com.avi.coupons.utils.TypeUtils;

@Controller
public class UsersController {
	
	@Autowired
	private IUsersDAO usersDao;
	
	@Autowired
	private ICompaniesDAO companiesDAO;
	
	@Autowired
	private ICacheManager cacheManager;

	public UsersController() {
		
	}
	
	public LoginData login(LoginForm loginForm) throws ApplicationException {
		if (loginForm == null)
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

		NameUtils.isValidName(loginForm.getUserName());
		PasswordUtils.isValidPassword(loginForm.getPassword());

		if (!usersDao.existsByUserName(loginForm.getUserName())) {
			throw new ApplicationException(ErrorType.USERNAME_DOES_NOT_EXISTS, ErrorType.USERNAME_DOES_NOT_EXISTS.getInternalMessage(),false);
		}
		User user = usersDao.findByUserNameAndPassword(loginForm.getUserName(), loginForm.getPassword());

		if (user == null)
			throw new ApplicationException(ErrorType.LOGIN_FAILED, ErrorType.LOGIN_FAILED.getInternalMessage(), true);

		Long company_Id = null;
		if (user.getCompany() != null) {
			company_Id = user.getCompany().getCompanyID();
		}
		UserData userData = new UserData(user.getId(),user.getUserName(),ClientType.Company, company_Id);

		int token = generateEncryptedToken(loginForm.getUserName());
		cacheManager.put(token, userData);
		LoginData loginData = new LoginData(token,userData.getType(),userData.getUserID(), userData.getCompanyID());
		return loginData;
	}

	

	private int generateEncryptedToken(String username) {
		String token = "this too" + username + "shall hash";
		
		return token.hashCode();
	}

	public long createUser(User user, UserData userData) throws ApplicationException {
		if (user == null) {
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
		}
		if (userData.getUserID() < 1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!userData.getType().name().equals("Administrator")) {
			if (!user.getType().name().equals("Customer"))
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		}
 
		
		NameUtils.isValidName(user.getUserName());
		PasswordUtils.isValidPassword(user.getPassword());
		
		if (usersDao.existsByEmail(user.getEmail())) {
			throw new ApplicationException(ErrorType.EXISTING_EMAIL, ErrorType.EXISTING_EMAIL.getInternalMessage(), false);
		}
		if ((user.getCompany() != null && user.getType().equals(ClientType.Customer))) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_TYPE, ErrorType.COMPANY_ID_NOT_TYPE.getInternalMessage(), false);
		}
		if (user.getType().equals(ClientType.Company)) {
			if(user.getCompany() == null) {
			throw new ApplicationException(ErrorType.COMPANY_TYPE_NO_ID, ErrorType.COMPANY_TYPE_NO_ID.getInternalMessage(), false);
			}
			if (user.getCompany().getCompanyID() < 1) {
				throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
				
			}
			if(!companiesDAO.existsById(user.getCompany().getCompanyID())) {
				throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage(), false);
			}
		}
		
		return usersDao.save(user).getId();
	}

	

	public void updateUser(User user, UserData userData) throws ApplicationException {

		if (user == null) {
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
		}
		
		if (!userData.getType().name().equals("Administrator")) {
			if (!user.getType().name().equals("Customer"))
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		}

		
		NameUtils.isValidName(user.getUserName());
		PasswordUtils.isValidPassword(user.getPassword());
		if (user.getId() < 1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!usersDao.existsById(user.getId())) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		if (usersDao.existsByUserName(user.getUserName())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, ErrorType.NAME_IS_ALREADY_EXISTS.getInternalMessage(), false);
		}
		usersDao.save(user);
	}

	public void deleteUser(long userId, UserData userData) throws ApplicationException {
		if (!userData.getType().name().equals("Administrator")) {
			if (userId != userData.getUserID())
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		}
		if (userId < 1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		if (!usersDao.existsById(userId)) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(), false);
		}
		usersDao.deleteById(userId); 
	}

	
	/*public void deleteUsersByCompanyId(long companyId) throws ApplicationException {


		if ( companiesDAO.getCompanyByID(companyId) == null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		usersDao.deleteCompanysUsers(companyId);

	}*/

	public List<User> getAllUsers(UserData userData) throws ApplicationException
	{
		if(!userData.getType().name().equals("Administrator"))
		throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);

		List<User> users = new ArrayList<User>();

		usersDao.findAll().forEach(users::add);

		return users;
	}
	
	
	public User getUser(long userId, UserData userData) throws ApplicationException {

		if (!userData.getType().name().equals("Administrator")) {
			if (userId != userData.getUserID()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}
		}
		
		if (userId < 1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		
		if (!usersDao.existsById(userId)) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(),	false);
		}
		return usersDao.findById(userId).get();

	}
	public String getUserName(long userId, UserData userData) throws ApplicationException {

		if (!userData.getType().name().equals("Administrator")) {
			if (userId != userData.getUserID()) {
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), true);
			}
		}
		
		if (userId < 1) {
			throw new ApplicationException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage(), false);
			
		}
		
		if (!usersDao.existsById(userId)) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(),	false);
		}
		return usersDao.findById(userId).get().getUserName();

	}

}
