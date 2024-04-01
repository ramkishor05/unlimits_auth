package com.brijframwork.authorization.beans;

public class UIUserOnBoarding {

	private long id;
	
	private UIMenuItem roleMenuItem;
	
	private Boolean onBoardingStatus;

	private Integer onBoardingLevel;
	
	private Boolean onBoardingActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UIMenuItem getRoleMenuItem() {
		return roleMenuItem;
	}

	public void setRoleMenuItem(UIMenuItem roleMenuItem) {
		this.roleMenuItem = roleMenuItem;
	}

	public Boolean getOnBoardingStatus() {
		if(onBoardingStatus==null) {
			return false;
		}
		return onBoardingStatus;
	}

	public void setOnBoardingStatus(Boolean onBoardingStatus) {
		this.onBoardingStatus = onBoardingStatus;
	}
	
	public Integer getOnBoardingLevel() {
		if(onBoardingLevel==null) {
			return 0;
		}
		return onBoardingLevel;
	}

	public void setOnBoardingLevel(Integer onBoardingLevel) {
		this.onBoardingLevel = onBoardingLevel;
	}

	public Boolean getOnBoardingActive() {
		if(onBoardingActive==null) {
			return false;
		}
		return onBoardingActive;
	}

	public void setOnBoardingActive(Boolean onBoardingActive) {
		this.onBoardingActive = onBoardingActive;
	}
	
}
