package com.brijframework.authorization.model.onboarding;

import java.util.List;

import com.brijframework.authorization.beans.UIUserOnBoardingAnswer;
import com.brijframework.authorization.model.EOEntityObject;
import com.brijframework.authorization.model.EOUserAccount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ONBOARDING_QUESTION", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "USER_ACCOUNT_ID", "QUESTION_ID" }) })
public class EOUserOnBoardingQuestion extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "USER_ACCOUNT_ID", nullable = false)
	@ManyToOne
	private EOUserAccount userAccount;

	@JoinColumn(name = "QUESTION_ID", nullable = false)
	@ManyToOne
	private EOOnBoardingQuestion question;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<UIUserOnBoardingAnswer> answers;

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public EOOnBoardingQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EOOnBoardingQuestion question) {
		this.question = question;
	}

	public List<UIUserOnBoardingAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<UIUserOnBoardingAnswer> answers) {
		this.answers = answers;
	}

}
