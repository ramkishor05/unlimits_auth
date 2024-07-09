package com.brijframework.authorization.account.entities;

import com.brijframework.authorization.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ACCOUNT_SERVICE")
public class EOUserAccountService extends EOEntityObject{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "TYPE")
	private String type;

	@JoinColumn(name = "USER_ID")
	@ManyToOne
	private EOUserAccount userAccount;
	
	public EOUserAccountService() {
		
	}

	public EOUserAccountService(String type, EOUserAccount userAccount) {
		super();
		this.type = type;
		this.userAccount = userAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
}
