package com.brijframework.authorization.account.entities.onboarding;

import java.util.Date;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.entities.EOPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ONBOARDING_BILLING")
public class EOUserOnBoardingBilling extends EOPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "START_AT")
	private Date startAt;
	
	@Column(name = "EXPRIED_AT")
	private Date expriedAt;

	@JoinColumn(name = "USER_ACCOUNT_ID", nullable = false)
	@ManyToOne
	private EOUserAccount userAccount;

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
