package Coupons.JavaBeans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer {
	
	@Id
	@Column(name = "CUSTOMER_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long customer_Id;

	@Column(name = "FIRST_NAME", nullable = false, unique = false, length = 20)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = true, unique = false, length = 20)
	private String lastName;

	@Column(name = "PHONE_NUMBER", nullable = false, unique = false, length = 10)
	private String phoneNumber;

	@Column(name = "EMAIL", nullable = false, unique = false, length = 25)
	private String email;

	@JoinColumn(name = "USER")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Purchase> purchases;

	    
	    public String getLastName() {
			return lastName;
		}
		public void setLastName(String last_name) {
			this.lastName = last_name;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String first_name) {
			this.firstName = first_name;
		}
		
		public long getCustomerId() {
			return customer_Id;
		}
		public void setCustomerId(long id) {
			this.customer_Id = id;
		}
		
		public Customer() {
			super();
			
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}

		
		
}