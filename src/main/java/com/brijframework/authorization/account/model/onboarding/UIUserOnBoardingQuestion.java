package com.brijframework.authorization.account.model.onboarding;

import java.util.List;

import com.brijframework.authorization.model.UIModel;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingQuestion;

public class UIUserOnBoardingQuestion extends UIModel {

	private UIViewOnBoardingQuestion question;

	private List<UIUserOnBoardingAnswer> answers;
	
	private Long userAccountId;

	public UIViewOnBoardingQuestion getQuestion() {
		if(question==null) {
			question=new UIViewOnBoardingQuestion();
		}
		return question;
	}

	public void setQuestion(UIViewOnBoardingQuestion question) {
		this.question = question;
	}

	public List<UIUserOnBoardingAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<UIUserOnBoardingAnswer> answers) {
		this.answers = answers;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

}
