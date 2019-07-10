package Coupons.JavaBeans;

public class FightResults {
	
	private Coupon customerCoupon;
	private Coupon opponentCoupon;
	
	
	public FightResults(Coupon customerCoupon, Coupon opponentCoupon) {
		super();
		this.setCustomerPrice(customerCoupon);
		this.setOpponentPrice(opponentCoupon);
	}
	
	public FightResults() {
		super();
	}

	public Coupon getCustomerPrice() {
		return customerCoupon;
	}
	public void setCustomerPrice(Coupon customerCoupon) {
		this.customerCoupon = customerCoupon;
	}
	public Coupon getOpponentPrice() {
		return opponentCoupon;
	}
	public void setOpponentPrice(Coupon opponentCoupon) {
		this.opponentCoupon = opponentCoupon;
	}


}
