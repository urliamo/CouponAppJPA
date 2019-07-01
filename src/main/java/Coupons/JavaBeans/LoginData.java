package Coupons.JavaBeans;

import Coupons.Enums.ClientType;

public class LoginData {
	
	private long userId;
	private Integer token;
	private Long companyId;
	private ClientType type;
	
	public LoginData( int token, ClientType type,long userId, Long companyId) {
		this.setCompanyId(companyId);
		this.setClientType(type);
		this.setUserId(userId);
		this.setToken(token);
	}
	
	public LoginData() {
	
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}


	public ClientType getClientType() {
		return type;
	}


	public void setClientType(ClientType clientType) {
		this.type = clientType;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
