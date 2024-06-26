package com.brijframework.authorization.view.entities.onboarding;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import java.util.List;

import com.brijframework.authorization.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = VIEW_ONBOARDING_QUESTION, uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "QUESTION" }) })
public class EOViewOnBoardingQuestion extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "IDEN_NO")
	private String idenNo;

	@Column(name = "QUESTION")
	private String question;
	
	@Column(name = "HINT_TEXT")
	private String hintText;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "REQUIRED")
	private boolean required;

	@OneToMany(mappedBy = "question")
	private List<EOViewOnBoardingOptions> options;

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

	public List<EOViewOnBoardingOptions> getOptions() {
		return options;
	}

	public void setOptions(List<EOViewOnBoardingOptions> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "EOViewOnBoardingQuestion [idenNo=" + idenNo + ", question=" + question + ", hintText=" + hintText
				+ ", type=" + type + ", required=" + required + ", options=" + options + "]";
	}

	

}
