package Coupons.Jobs;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Coupons.Exceptions.ApplicationException;

/**
 * This class create a timer
 * 
 * @author Lichay
 *
 */
@Component
public class DailyJob {

	// Creating a task
	@Autowired
	private DailyJobTask task;

	/**
	 * This function create timer with task
	 * 
	 * @throws ApplicationException This function can throw an applicationException
	 */
	@PostConstruct
	public void createTimer() throws ApplicationException {

		// Creating a timer
		Timer timer = new Timer("daily job");

		timer.scheduleAtFixedRate(task, 0, 1000);

		System.out.println("Timer task started");

	}

}
	
