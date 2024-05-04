package com.brijframework.authorization.beans;

import java.util.List;

public class UIUserOnBoardingQuestion {

	private Long id;

	private UIOnBoardingQuestion question;

	private List<UIUserOnBoardingAnswer> answers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UIOnBoardingQuestion getQuestion() {
		return question;
	}

	public void setQuestion(UIOnBoardingQuestion question) {
		this.question = question;
	}

	public List<UIUserOnBoardingAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<UIUserOnBoardingAnswer> answers) {
		this.answers = answers;
	}

}
