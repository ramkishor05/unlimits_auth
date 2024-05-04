package com.brijframework.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;

@Repository
@Transactional
public interface UserOnBoardingQuestionRepository  extends JpaRepository<EOUserOnBoardingQuestion, Long>{

	List<EOUserOnBoardingQuestion> findByUserAccount(EOUserAccount eoUserAccount);

}
