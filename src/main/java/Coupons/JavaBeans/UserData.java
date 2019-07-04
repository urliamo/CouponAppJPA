package Coupons.JavaBeans;


import Coupons.Enums.ClientType;


public class UserData {
	
	private long userID;
	private String userName;
	private ClientType type;
	private Long companyID;
	
	
	public UserData(long userID,String userName, ClientType type, long companyID) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setType(type);
		this.setCompanyID(companyID);
	}

	public String getUserName() {
		return userName;
	}
	
	
	public void setUserName(String userId) {
		this.userName = userId;
	}
	
	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}



	
	
	

}
