package com.brijframework.authorization.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIOnBoardingQuestion;
import com.brijframework.authorization.model.onboarding.EOOnBoardingQuestion;
import com.brijframework.authorization.service.OnBoardingQuestionService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/onboarding/question")
@CrossOrigin("*")
@Hidden
public class GlobalOnboardingQuestionController extends CrudController<UIOnBoardingQuestion, EOOnBoardingQuestion, Long> {

	@Autowired
	private OnBoardingQuestionService onBoardingQuestionService; 
	
	@Override
	public CrudService<UIOnBoardingQuestion, EOOnBoardingQuestion, Long> getService() {
		return onBoardingQuestionService;
	}
}
