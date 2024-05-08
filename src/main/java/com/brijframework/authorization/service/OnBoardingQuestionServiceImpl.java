package com.brijframework.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIOnBoardingQuestion;
import com.brijframework.authorization.mapper.GenericMapper;
import com.brijframework.authorization.mapper.OnBoardingQuestionMapper;
import com.brijframework.authorization.model.onboarding.EOOnBoardingQuestion;
import com.brijframework.authorization.repository.OnBoardingQuestionRepository;
import com.brijframework.rest.crud.service.impl.CrudServiceImpl;

@Service
public class OnBoardingQuestionServiceImpl extends CrudServiceImpl<UIOnBoardingQuestion, EOOnBoardingQuestion, Long> implements OnBoardingQuestionService {

	@Autowired
	private OnBoardingQuestionRepository onBoardingQuestionRepository;
	
	@Autowired
	private OnBoardingQuestionMapper onBoardingQuestionMapper;
	
	@Override
	public JpaRepository<EOOnBoardingQuestion, Long> getRepository() {
		return onBoardingQuestionRepository;
	}

	@Override
	public GenericMapper<EOOnBoardingQuestion, UIOnBoardingQuestion> getMapper() {
		return onBoardingQuestionMapper;
	}

}
