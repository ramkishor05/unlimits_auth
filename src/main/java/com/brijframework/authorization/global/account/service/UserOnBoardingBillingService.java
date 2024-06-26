package com.brijframework.authorization.global.account.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;

public interface UserOnBoardingBillingService extends CrudService<UIUserOnBoardingBilling, EOUserOnBoardingBilling, Long>{

	UIUserOnBoardingBilling saveOnBoardingBilling(UIUserOnBoardingBilling userOnBoardingBilling,
			String username);

	/**
	 * @param userAccountId
	 * @return
	 */
	List<UIUserOnBoardingBilling> findAllByUserAccountId(Long userAccountId);

}
