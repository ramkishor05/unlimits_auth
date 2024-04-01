package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.onboarding.EOUserOnBoarding;

public interface UserOnBoardingService {

	void initOnBoarding(EOUserAccount eoUserAccount);

	void saveOnBoardingStatus(boolean onboarding, String idenNo, List<EOUserOnBoarding> onBoardingList);

	boolean updateOnBoardingStatus(boolean onboarding, String idenNo, EOUserAccount eoUserAccount);

}
