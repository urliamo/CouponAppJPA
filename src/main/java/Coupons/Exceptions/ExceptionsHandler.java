package Coupons.Exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ExceptionHandler
	public void handleError(Throwable exception, HttpServletResponse response) {
		exception.printStackTrace();
		
		if (exception instanceof ApplicationException) {
			ApplicationException applicationException = (ApplicationException) exception;
			
		}
	}

}
