package com.brijframework.authorization.global.account.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;

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
