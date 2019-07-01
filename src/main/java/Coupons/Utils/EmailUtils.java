package Coupons.Utils;

import java.util.regex.Pattern;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;

public class EmailUtils {

	/**
	 * @param email This function receives an email String and checks if valid 
	 * 
	 * @throws ApplicationException Throw an exception for invalid\empty mail
	 */
	
	public static void isValidEmail(String email) throws ApplicationException {
			Pattern ptr = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
					if (!ptr.matcher(email).matches())
						throw new ApplicationException(ErrorType.INVALID_EMAIL, ErrorType.INVALID_EMAIL.getInternalMessage(), false);
		
		if (email == null || email.isEmpty())
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);
	}

}
