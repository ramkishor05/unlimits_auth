package com.brijframework.authorization.view.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;

@Repository
@Transactional
public interface ViewOnBoardingQuestionRepository  extends JpaRepository<EOViewOnBoardingQuestion, Long>{

}
