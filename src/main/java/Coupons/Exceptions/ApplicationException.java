package Coupons.Exceptions;

import Coupons.Enums.ErrorType;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorType errorType;
	private boolean isCritical;

	// constructor

		/**
		 * @param errorType  Receive an error type
		 * @param message    Receive a message
		 * @param isCritical Receive is critical
		 * @param throwable  Receive a throwable
		 */
		public ApplicationException(ErrorType errorType, String message, boolean isCritical, Throwable throwable) {

			super(message, throwable);
			this.errorType = errorType;
			this.isCritical = isCritical;

		}

		/**
		 * @param errorType  Receive an error type
		 * @param message    Receive a message
		 * @param isCritical Receive is critical
		 */
		public ApplicationException(ErrorType errorType, String message, boolean isCritical) {

			super(message);
			this.errorType = errorType;
			this.isCritical = isCritical;

		}

	
	public ErrorType getErrorType(){
		return this.errorType;
	}
	public boolean isErrorCritical(){
		return this.isCritical;
	}
}

