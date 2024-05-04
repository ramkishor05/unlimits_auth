package com.brijframework.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.onboarding.EOUserOnBoardingAnswer;

@Repository
@Transactional
public interface UserOnBoardingAnswerRepository  extends JpaRepository<EOUserOnBoardingAnswer, Long>{

}
