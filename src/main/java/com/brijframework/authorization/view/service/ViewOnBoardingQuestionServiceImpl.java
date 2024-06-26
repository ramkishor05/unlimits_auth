package com.brijframework.authorization.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;
import com.brijframework.authorization.view.mapper.ViewOnBoardingQuestionMapper;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingQuestion;
import com.brijframework.authorization.view.repository.ViewOnBoardingQuestionRepository;

@Service
public class ViewOnBoardingQuestionServiceImpl extends CrudServiceImpl<UIViewOnBoardingQuestion, EOViewOnBoardingQuestion, Long> implements ViewOnBoardingQuestionService {

	@Autowired
	private ViewOnBoardingQuestionRepository onBoardingQuestionRepository;
	
	@Autowired
	private ViewOnBoardingQuestionMapper onBoardingQuestionMapper;
	
	@Override
	public JpaRepository<EOViewOnBoardingQuestion, Long> getRepository() {
		return onBoardingQuestionRepository;
	}

	@Override
	public GenericMapper<EOViewOnBoardingQuestion, UIViewOnBoardingQuestion> getMapper() {
		return onBoardingQuestionMapper;
	}

}
