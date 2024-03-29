package Coupons.JavaBeans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "purchases")
public class Purchase {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PURCHASE_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long purchaseId;

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
			super();
		}


		public long getPurchaseID() {
			return purchaseId;
		}


		public void setPurchaseID(long purchaseID) {
			this.purchaseId = purchaseID;
		}


		public Coupon getCoupon() {
			return coupon;
		}


		public void setCoupon(Coupon coupon) {
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
			return "Purchase [purchaseID=" + purchaseId + ", couponID=" + coupon.getCouponId() + ", amount=" + amount + "]";
		}


		public Customer getCustomer() {
			return customer;
		}


		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
}
