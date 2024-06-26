package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;
import static com.brijframework.authorization.constant.Constants.CLIENT_USER_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;
import com.brijframework.authorization.exceptions.UserNotFoundException;
import com.brijframework.authorization.global.account.service.UserOnBoardingQuestionService;

@RestController
@RequestMapping("/api/device/onboarding/question")
@CrossOrigin("*")
public class DeviceOnboardingQuestionController {

	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService; 
	
	@PutMapping
	public Response update(@RequestBody UIUserOnBoardingQuestion dto, @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		List<String> list = headers.get(CLIENT_USER_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		Response response=new Response();
		try {
			response.setData(userOnBoardingQuestionService.update(dto,headers));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping
	public Response findUseraccount(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
		List<String> list = headers.get(CLIENT_USER_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		Long userAccountId=Long.valueOf(list.get(0));
		Response response=new Response();
		try {
			response.setData(userOnBoardingQuestionService.findAllByUserAccountId(userAccountId));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
