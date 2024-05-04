package com.brijframework.authorization.service;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.model.EOUserAccount;

public interface UserOnBoardingQuestionService {

	void initOnBoarding(EOUserAccount eoUserAccount);

	UIUserOnBoardingQuestion saveOnBoardingQuestion(UIUserOnBoardingQuestion userOnBoardingQuestion,
			String username);

}
