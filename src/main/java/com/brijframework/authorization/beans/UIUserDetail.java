package com.brijframework.authorization.beans;

import java.util.List;

import com.brijframework.authorization.constant.Authority;

public class UIUserDetail {

	private String username;
	private String registeredMobile;
	private String registeredEmail;
	private String accountName;
	private Authority authority;
	
	private List<UIUserOnBoardingQuestion>  boardingQuestions;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegisteredMobile() {
		return registeredMobile;
	}

	public void setRegisteredMobile(String registeredMobile) {
		this.registeredMobile = registeredMobile;
	}

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public List<UIUserOnBoardingQuestion> getBoardingQuestions() {
		return boardingQuestions;
	}

	public void setBoardingQuestions(List<UIUserOnBoardingQuestion> boardingQuestions) {
		this.boardingQuestions = boardingQuestions;
	}
	
}
