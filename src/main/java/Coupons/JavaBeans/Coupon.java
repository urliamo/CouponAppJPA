package Coupons.JavaBeans;

//import java.sql.Date;
import java.util.Date;
//import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Coupons.Enums.Category;

public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COUPON_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long coupon_Id;

	@Column(name = "CATEGORY", nullable = false, unique = false, length = 40)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(name = "TITLE", nullable = false, unique = false, length = 25)
	private String title;

	@Column(name = "DESCRIPTION", nullable = true, unique = false, length = 255)
	private String description;

	@Column(name = "START_DATE", nullable = false, unique = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE", nullable = false, unique = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "AMOUNT", nullable = false, unique = false, columnDefinition = "INT(11) UNSIGNED")
	private int amount;

	@Column(name = "PRICE", nullable = false, unique = false, columnDefinition = "DOUBLE UNSIGNED")
	private double price;

	@Column(name = "IMAGE", nullable = true, unique = false, length = 50)
	private String image;

	@JoinColumn(name = "COMPANY", nullable = false, unique = false)
	@ManyToOne
	private Company company;

	@JsonIgnore
	@OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Purchase> purchases;

    
    
    //----------Setters & Getters-----------------------//
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getCouponId() {
		return coupon_Id;
	}
	public void setCouponId(Long couponId) {
		this.coupon_Id = couponId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		
		this.startDate = startDate;
	}
	public Date getendDate() {
		return endDate;
	}
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
    
	//-------------Constructors-----------------------------------------------//

	public Coupon() {
		super();
	
	}
    
}
