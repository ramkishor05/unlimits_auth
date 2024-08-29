package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingAnswer;

@Repository
@Transactional
public interface UserOnBoardingAnswerRepository  extends CustomRepository<EOUserOnBoardingAnswer, Long>{

	void deleteAllByQuestionId(Long userQuestionId);
	
	List<EOUserOnBoardingAnswer> findAllByQuestionId(Long userQuestionId);

}
