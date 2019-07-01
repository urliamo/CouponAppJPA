package Coupons.Utils;


import java.util.Date;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;

public class DateUtils {


	
	public static void validateDates(Date startDate, Date endDate) throws ApplicationException {
		
			if (startDate == null || endDate == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			Date currentDate = new Date();

			// check if date is valid
			if (startDate.after(endDate) || startDate.equals(endDate) || endDate.before(currentDate))
				throw new ApplicationException(ErrorType.INVALID_DATES, ErrorType.INVALID_DATES.getInternalMessage(), false);

		
	}
	

}
