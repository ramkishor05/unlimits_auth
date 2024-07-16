package com.brijframework.authorization.view.repository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;

@Repository
@Transactional
public interface ViewOnBoardingQuestionRepository  extends CustomRepository<EOViewOnBoardingQuestion, Long>{

}
