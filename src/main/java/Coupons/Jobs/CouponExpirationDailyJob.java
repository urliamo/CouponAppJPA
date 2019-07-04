package Coupons.Jobs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Coupons.JavaBeans.Coupon;

/**
 * runnable class in charge of deleting expired coupons.
 *
 * @param  quit boolean controlling when to stop thread
 * @param couponsDBDAO DB access object for coupons
 * @param date the current time
 * @see 		JavaBeans.Coupon
 */
public class CouponExpirationDailyJob implements Runnable {
	
	private boolean quit = false;
	@Autowired
	private  Coupons.DB.ICouponsDAO couponsDAO;
	@Autowired
	private  Coupons.DB.IPurchasesDAO purchasesDAO;
	
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
	
	public CouponExpirationDailyJob() {
		super();
		
	}
	
	public void stop() {
		this.setQuit(true);
		
	}
}
