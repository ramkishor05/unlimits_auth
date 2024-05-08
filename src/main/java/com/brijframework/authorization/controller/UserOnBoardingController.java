package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.service.UserOnBoardingQuestionService;

@RestController
@RequestMapping("/api/user/onboarding")
@CrossOrigin("*")
public class UserOnBoardingController {

	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService;
	
	@PutMapping("/question")
	public UIUserOnBoardingQuestion updateOnBoardQuesrion(@RequestBody UIUserOnBoardingQuestion userOnBoardingQuestion, @RequestHeader("username") String username){
    	return userOnBoardingQuestionService.saveOnBoardingQuestion(userOnBoardingQuestion, username);
	}
}
