package com.brijframework.authorization.service;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.rest.crud.service.CrudService;

public interface UserOnBoardingQuestionService extends CrudService<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long>{

	void initOnBoarding(EOUserAccount eoUserAccount);

	UIUserOnBoardingQuestion saveOnBoardingQuestion(UIUserOnBoardingQuestion userOnBoardingQuestion,
			String username);

}
