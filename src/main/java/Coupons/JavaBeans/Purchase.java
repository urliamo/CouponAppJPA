package Coupons.JavaBeans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Purchase {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PURCHASE_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long purchase_Id;

	@Column(name = "AMOUNT", nullable = false, unique = false, columnDefinition = "INT(11) UNSIGNED")
	private int amount;

	@Column(name = "DATE", nullable = false, unique = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	


	@JoinColumn(name = "CUSTOMER", nullable = false, unique = false)
	@ManyToOne
	private Customer customer;

	@JoinColumn(name = "COUPON", nullable = false, unique = false)
	@ManyToOne
	private Coupon coupon;
		
		
		
		public Purchase() {
		}


		public long getPurchaseID() {
			return purchase_Id;
		}


		public void setPurchaseID(long purchaseID) {
			this.purchase_Id = purchaseID;
		}


		public Coupon getCoupon() {
			return coupon;
		}


		public void setCouponID(Coupon coupon) {
			this.coupon = coupon;
		}


		public int getAmount() {
			return amount;
		}


		public void setAmount(int amount) {
			this.amount = amount;
		}

		public Date getDate() {
			return date;
		}


		public void setDate(Date date) {
			this.date = date;
		}
		@Override
		public String toString() {
			return "Purchase [purchaseID=" + purchase_Id + ", couponID=" + coupon.getCouponId() + ", amount=" + amount + "]";
		}


		public Customer getCustomer() {
			return customer;
		}


		public void setCustomerID(Customer customer) {
			this.customer = customer;
		}
}
