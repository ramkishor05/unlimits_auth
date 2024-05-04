package com.brijframework.authorization.model.onboarding;

import com.brijframework.authorization.model.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ONBOARDING_ANSWER", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "QUESTION_ID", "VALUE" }) })
public class EOUserOnBoardingAnswer extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "QUESTION_ID")
	@ManyToOne
	private EOUserOnBoardingQuestion question;

	@Column(name = "VALUE")
	private String value;

	public EOUserOnBoardingQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EOUserOnBoardingQuestion question) {
		this.question = question;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
