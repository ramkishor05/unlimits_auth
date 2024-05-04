package com.brijframework.authorization.beans;

import java.util.List;

public class UIOnBoardingQuestion {

	private Long id;

	private String question;

	private String type;

	private boolean required;

	private List<UIOnBoardingOptions> options;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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

	public List<UIOnBoardingOptions> getOptions() {
		return options;
	}

	public void setOptions(List<UIOnBoardingOptions> options) {
		this.options = options;
	}

}
