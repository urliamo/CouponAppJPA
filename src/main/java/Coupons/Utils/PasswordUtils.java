package Coupons.Utils;

import java.util.regex.Pattern;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;


public class PasswordUtils {

	/**
	 * @param password This function receive a password and check if the password
	 *                 valid
	 * @throws ApplicationException Throw an exception by name
	 */
//	    This checkPassword has the following policy :

//	    At least 8 chars
	//
//	    Contains at least one digit
	//
//	    Contains at least one lower alpha char and one upper alpha char
	//
//	    Contains at least one char within a set of special chars (@#%$^ etc.)
	//
//	    Does not contain space, tab, etc.
	
	public static void isValidPassword(String password) throws ApplicationException {

		Pattern ptr = Pattern.compile("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z");

		if (!ptr.matcher(password).matches()) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD, ErrorType.INVALID_PASSWORD.getInternalMessage(), false);
		}
	}

}
