package Coupons.JavaBeans;

public class FightResults {
	
	private Coupon customerCoupon;
	private Coupon opponentCoupon;
	
	
	public FightResults(Coupon customerCoupon, Coupon opponentCoupon) {
		this.setCustomerCoupon(customerCoupon);
		this.setOpponentCoupon(opponentCoupon);
	}
	
	public FightResults() {
		super();
	}

	public Coupon getCustomerCoupon() {
		return customerCoupon;
	}
	public void setCustomerCoupon(Coupon customerCoupon) {
		this.customerCoupon = customerCoupon;
	}
	public Coupon getOpponentCoupon() {
		return opponentCoupon;
	}
	public void setOpponentCoupon(Coupon opponentCoupon) {
		this.opponentCoupon = opponentCoupon;
	}


}
