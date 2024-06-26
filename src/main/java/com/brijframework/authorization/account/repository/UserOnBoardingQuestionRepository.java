package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;

@Repository
@Transactional
public interface UserOnBoardingQuestionRepository  extends JpaRepository<EOUserOnBoardingQuestion, Long>{

	List<EOUserOnBoardingQuestion> findAllByUserAccount(EOUserAccount eoUserAccount);

	/**
	 * @param userAccountId
	 * @return
	 */
	List<EOUserOnBoardingQuestion> findAllByUserAccountId(Long userAccountId);

}
