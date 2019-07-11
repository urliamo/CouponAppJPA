package Coupons.Jobs;

	import java.util.TimerTask;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	import Coupons.DB.ICouponsDAO;
	import Coupons.DB.ICustomersDAO;

	/**
	 * This function create a thread class for delete expired coupons
	 * 
	 * @author Lichay
	 *
	 */
	@Component
	public class DailyJobTask extends TimerTask {

		@Autowired
		private ICouponsDAO couponsDao;
		
		@Autowired
		private ICustomersDAO customersDao;

		@Override
		public void run() {

			couponsDao.deleteExpiredCoupon();
			customersDao.setAllCustomersEligibile();
		}

	}


