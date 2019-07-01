package Coupons.JavaBeans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import Coupons.Enums.ClientType;


@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long user_Id;

	@Column(name = "USER_NAME", nullable = false, unique = true, length = 25)
	private String userName;

	@Column(name = "EMAIL", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false, unique = false, length = 50)
	private String password;

	@Column(name = "CATEGORY", nullable = false, unique = false, length = 40)
	@Enumerated(EnumType.STRING)
	private ClientType type;

	@JoinColumn(name = "COMPANY", nullable = true, unique = false)
	@ManyToOne
	private Company company;
	
	
	
	public User() {
		super();
	}

	public long getId() {
		return user_Id;
	}

	public void setId(Long id) {
		this.user_Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompany() {
		
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
