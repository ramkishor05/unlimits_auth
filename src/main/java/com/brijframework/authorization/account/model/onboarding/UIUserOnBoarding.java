package com.brijframework.authorization.account.model.onboarding;

import java.util.List;

import com.brijframework.authorization.model.UIModel;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;

public class UIUserOnBoarding extends UIModel {

	private UIViewMenuItem roleMenuItem;
	
	private Boolean onBoardingStatus;

	private Integer onBoardingLevel;
	
	private Boolean onBoardingActive;
	
	private List<UIUserOnBoardingQuestion> questions;

	public UIViewMenuItem getRoleMenuItem() {
		return roleMenuItem;
	}

	public void setRoleMenuItem(UIViewMenuItem roleMenuItem) {
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

	public List<UIUserOnBoardingQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<UIUserOnBoardingQuestion> questions) {
		this.questions = questions;
	}
	
}
