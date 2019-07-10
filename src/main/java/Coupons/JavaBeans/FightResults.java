package Coupons.JavaBeans;

public class FightResults {
	
	private double customerPrice;
	private double opponentPrice;
	
	
	public FightResults(double customerPrice, double opponentPrice) {
		super();
		this.setCustomerPrice(customerPrice);
		this.setOpponentPrice(opponentPrice);
	}
	
	public FightResults() {
		super();
	}

	public double getCustomerPrice() {
		return customerPrice;
	}
	public void setCustomerPrice(double customerPrice) {
		this.customerPrice = customerPrice;
	}
	public double getOpponentPrice() {
		return opponentPrice;
	}
	public void setOpponentPrice(double opponentPrice) {
		this.opponentPrice = opponentPrice;
	}


}
