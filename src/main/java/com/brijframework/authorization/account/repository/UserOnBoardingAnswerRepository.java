package com.brijframework.authorization.account.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingAnswer;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;

@Repository
@Transactional
public interface UserOnBoardingAnswerRepository  extends CustomRepository<EOUserOnBoardingAnswer, Long>{

	boolean deleteAllByQuestion(EOUserOnBoardingQuestion entity);

}
