package com.brijframwork.authorization.beans;

import java.util.ArrayList;
import java.util.List;

public class UserDetailResponse {

	private long id;

	private String username;
	
	private String password;

	private String accountName;
	
	private String registeredEmail;
	
	private String registeredMobile;

	private String type;

	private Long ownerId;
	
	private Boolean onBoarding;

	private UserRoleResponse userRole;

	private UIUserProfile userProfile;

	private List<UIUserOnBoarding> onBoardingList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public String getRegisteredMobile() {
		return registeredMobile;
	}

	public void setRegisteredMobile(String registeredMobile) {
		this.registeredMobile = registeredMobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Boolean getOnBoarding() {
		if(onBoarding==null) {
			return true;
		}
		return onBoarding;
	}

	public void setOnBoarding(Boolean onBoarding) {
		this.onBoarding = onBoarding;
	}

	public UserRoleResponse getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleResponse userRole) {
		this.userRole = userRole;
	}

	public UIUserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UIUserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<UIUserOnBoarding> getOnBoardingList() {
		if(onBoardingList==null) {
			onBoardingList=new ArrayList<UIUserOnBoarding>();
		}
		return onBoardingList;
	}

	public void setOnBoardingList(List<UIUserOnBoarding> onBoardingList) {
		this.onBoardingList = onBoardingList;
	}
}
