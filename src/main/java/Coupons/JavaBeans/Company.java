package Coupons.JavaBeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

	@Entity
	@Table(name = "companies")
	@JsonIgnoreProperties({ "coupons", "users" })
	public class Company implements Serializable {

		// properties


		/**
	 * 
	 */
	private static final long serialVersionUID = 9214119109803733570L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "COMPANY_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
		private long companyId;

		@Column(name = "NAME", nullable = false, unique = true, length = 25)
		private String name;


		@Column(name = "EMAIL", nullable = false, unique = false, length = 25)
		private String email;

		@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
		private List<Coupon> coupons;

		@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
		private List<User> users;

		// constructor

		/**
		 * Constructor for create a show for this class
		 */
		public Company() {
			super();

		}
	public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    	
    }



    public void setId(long id) {
        this.companyId = id;
    }

    public String getName() {
        return this.name;
    }



    public String getEmail() {
        return this.email;
    }

    public long getId() {
        return this.companyId;
    }
    

    
  

	
}