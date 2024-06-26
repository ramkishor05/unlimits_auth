package com.brijframework.authorization.view.model.onboadring;

import java.util.List;

import com.brijframework.authorization.model.UIModel;

public class UIViewOnBoardingQuestion extends UIModel {

	private String question;
	
	private String hintText;

	private String type;
	
	private boolean required;

	private List<UIViewOnBoardingOptions> options;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	

	public String getHintText() {
		return hintText;
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<UIViewOnBoardingOptions> getOptions() {
		return options;
	}

	public void setOptions(List<UIViewOnBoardingOptions> options) {
		this.options = options;
	}

}
