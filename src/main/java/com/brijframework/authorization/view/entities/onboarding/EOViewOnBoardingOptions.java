package com.brijframework.authorization.view.entities.onboarding;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import com.brijframework.authorization.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = VIEW_ONBOARDING_OPTIONS, uniqueConstraints = {
		@UniqueConstraint(columnNames = { "QUESTION_ID", "VALUE" }) })
public class EOViewOnBoardingOptions extends EOEntityObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@JoinColumn(name = "QUESTION_ID")
	@ManyToOne
	private EOViewOnBoardingQuestion question;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "DESCIPTION")
	private String desciption;

	public EOViewOnBoardingQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EOViewOnBoardingQuestion question) {
		this.question = question;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	@Override
	public String toString() {
		return "EOViewOnBoardingOptions [question=" + question + ", value=" + value + "]";
	}
	
	
	
}
