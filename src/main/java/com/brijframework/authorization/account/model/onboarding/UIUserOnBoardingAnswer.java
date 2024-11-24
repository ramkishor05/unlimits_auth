package com.brijframework.authorization.account.model.onboarding;

import com.brijframework.authorization.model.UIModel;

public class UIUserOnBoardingAnswer extends UIModel {
	
	private String value;
	private String desciption;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
}
