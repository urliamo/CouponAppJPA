package Coupons.Jobs;

import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;


/**
 * runnable class in charge of deleting expired coupons.
 *
 * @param  quit boolean controlling when to stop thread
 * @param couponsDBDAO DB access object for coupons
 * @param date the current time
 * @see 		JavaBeans.Coupon
 */
public class DailyJob implements Runnable {
	
	private boolean quit = false;
	@Autowired
	private  Coupons.DB.ICouponsDAO couponsDAO;
	@Autowired
	private  Coupons.DB.ICustomersDAO customersDAO;
	
	private LocalDate date = LocalDate.now();
	
	public void setQuit(boolean quit) {
		this.quit = quit;
	}
	
	
	public void run() {
		while(!quit) {
			try {
				//check if date changes since last check
			if (date.isBefore(LocalDate.now())) {
				//update current date
				date = LocalDate.now();
				//get expired coupons
				couponsDAO.deleteExpiredCoupon();
				customersDAO.setAllCustomersEligibile();

			}
			//wait 1 hour and check for date change
			try {
					Thread.sleep(1000*60*60);
				}
			 catch (InterruptedException Ex) {
				 System.out.println(Ex.getMessage());
		    }
			}
			catch(Exception Ex){
				 System.out.println(Ex.getMessage());

			}
		}
	}
	
	public DailyJob() {
		super();
		
	}
	
	public void stop() {
		this.setQuit(true);
		
	}
}
