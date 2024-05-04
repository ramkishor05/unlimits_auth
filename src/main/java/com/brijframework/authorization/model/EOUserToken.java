package com.brijframework.authorization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_TOKEN")
public class EOUserToken extends EOEntityObject{

	private static final long serialVersionUID = 1L;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "TARGET")
	private String target;
	
	@Column(name = "TYPE")
	private String type;

	@JoinColumn(name = "USER_ID")
	@ManyToOne
	private EOUserAccount userAccount;
	
	public EOUserToken() {
		
	}

	public EOUserToken(String source, String target, String type, EOUserAccount userAccount) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
		this.userAccount = userAccount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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
