package com.brijframework.authorization.global.account.service;

import java.util.List;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;

public interface UserOnBoardingService {

	void initOnBoarding(EOUserAccount eoUserAccount);

	void saveOnBoardingStatus(boolean onboarding, String idenNo, List<EOUserOnBoardingMenu> onBoardingList);

	boolean updateOnBoardingStatus(boolean onboarding, String idenNo, EOUserAccount eoUserAccount);

}
