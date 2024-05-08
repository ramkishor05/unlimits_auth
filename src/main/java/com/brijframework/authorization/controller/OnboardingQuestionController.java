package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.authorization.beans.UIOnBoardingQuestion;
import com.brijframework.authorization.model.onboarding.EOOnBoardingQuestion;
import com.brijframework.authorization.service.OnBoardingQuestionService;
import com.brijframework.rest.crud.controller.CrudController;
import com.brijframework.rest.crud.service.CrudService;

@RestController
@RequestMapping("/api/onboarding/question")
@CrossOrigin("*")
public class OnboardingQuestionController extends CrudController<UIOnBoardingQuestion, EOOnBoardingQuestion, Long> {

	@Autowired
	private OnBoardingQuestionService onBoardingQuestionService; 
	
	@Override
	public CrudService<UIOnBoardingQuestion, EOOnBoardingQuestion, Long> getService() {
		return onBoardingQuestionService;
	}
}
