package com.brijframework.authorization.account.model.onboarding;

import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingBilling;

public class UIUserOnBoardingBilling extends UIViewOnBoardingBilling{

	private Long userAccountId;

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
}
