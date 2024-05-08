package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.authorization.beans.Response;
import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.service.UserOnBoardingQuestionService;
import com.brijframework.rest.crud.controller.CrudController;
import com.brijframework.rest.crud.service.CrudService;

@RestController
@RequestMapping("/api/user/onboarding/question")
@CrossOrigin("*")
public class UserOnboardingQuestionController extends CrudController<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> {

	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService; 
	
	@Override
	public CrudService<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> getService() {
		return userOnBoardingQuestionService;
	}
	
	@GetMapping("/by/useraccount/{userAccountId}")
	public Response findAll(@PathVariable Long userAccountId){
		Response response=new Response();
		try {
			response.setData(userOnBoardingQuestionService.findAllByUserAccountId(userAccountId));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
