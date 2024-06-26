package com.brijframework.authorization.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingAnswer;

@Repository
@Transactional
public interface UserOnBoardingAnswerRepository  extends JpaRepository<EOUserOnBoardingAnswer, Long>{

}
