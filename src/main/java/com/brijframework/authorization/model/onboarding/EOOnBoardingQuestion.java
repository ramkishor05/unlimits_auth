package com.brijframework.authorization.model.onboarding;

import java.util.List;

import com.brijframework.authorization.model.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ONBOARDING_QUESTION", uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "QUESTION" }) })
public class EOOnBoardingQuestion extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "IDEN_NO")
	private String idenNo;

	@Column(name = "QUESTION")
	private String question;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "REQUIRED")
	private boolean required;

	@OneToMany(mappedBy = "question")
	private List<EOOnBoardingOptions> options;

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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

	public List<EOOnBoardingOptions> getOptions() {
		return options;
	}

	public void setOptions(List<EOOnBoardingOptions> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "EOOnBoardingQuestion [idenNo=" + idenNo + ", question=" + question + ", type=" + type + ", required="
				+ required + "]";
	}

	
}
