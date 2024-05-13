package com.brijframework.authorization.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;

public interface UserOnBoardingQuestionService extends CrudService<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long>{

	void initOnBoarding(EOUserAccount eoUserAccount);

	UIUserOnBoardingQuestion saveOnBoardingQuestion(UIUserOnBoardingQuestion userOnBoardingQuestion,
			String username);

	/**
	 * @param userAccountId
	 * @return
	 */
	List<UIUserOnBoardingQuestion> findAllByUserAccountId(Long userAccountId);

}
