package Coupons.JavaBeans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	@Column(name = "CUSTOMER_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long customerId;

	@Column(name = "FIRST_NAME", nullable = false, unique = false, length = 20)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = true, unique = false, length = 20)
	private String lastName;

	@JoinColumn(name = "USER")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Purchase> purchases;
	
	@Column(name = "ELIGIBLE", nullable = false, columnDefinition = "boolean default true")
	private boolean isEligibile;

	    
	    public boolean isEligibile() {
		return isEligibile;
		}
		    
		public List<Purchase> getPurchases() {
				return purchases;
			}
		
		public void setPurchases(List<Purchase> purchases) {
			this.purchases = purchases;
		}
		
		public void setEligibile(boolean isEligibile) {
			this.isEligibile = isEligibile;
		}
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
			return customerId;
		}
		public void setCustomerId(long id) {
			this.customerId = id;
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
