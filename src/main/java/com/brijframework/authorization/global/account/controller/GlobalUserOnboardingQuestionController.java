package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;
import com.brijframework.authorization.global.account.service.UserOnBoardingQuestionService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/user/onboarding/question")
@CrossOrigin("*")
@Hidden
public class GlobalUserOnboardingQuestionController extends CrudController<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> {

	@Autowired
	private UserOnBoardingQuestionService globalUserOnBoardingQuestionService; 
	
	@Override
	public CrudService<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> getService() {
		return globalUserOnBoardingQuestionService;
	}
}
