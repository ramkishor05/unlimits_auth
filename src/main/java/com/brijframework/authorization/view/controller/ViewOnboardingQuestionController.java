package com.brijframework.authorization.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingQuestion;
import com.brijframework.authorization.view.service.ViewOnBoardingQuestionService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/onboarding/question")
@CrossOrigin("*")
@Hidden
public class ViewOnboardingQuestionController implements CrudController<UIViewOnBoardingQuestion, EOViewOnBoardingQuestion, Long> {

	@Autowired
	private ViewOnBoardingQuestionService onBoardingQuestionService; 
	
	@Override
	public CrudService<UIViewOnBoardingQuestion, EOViewOnBoardingQuestion, Long> getService() {
		return onBoardingQuestionService;
	}
}
