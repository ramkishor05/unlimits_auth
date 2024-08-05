package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;
import com.brijframework.authorization.exceptions.UnauthorizedAccessException;
import com.brijframework.authorization.global.account.service.UserOnBoardingQuestionService;

@RestController
@RequestMapping("/api/device/onboarding/question")
@CrossOrigin("*")
public class DeviceOnboardingQuestionController {

	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService; 
	
	@PutMapping
	public Response<Object> update(@RequestBody UIUserOnBoardingQuestion dto, @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Response<Object> response=new Response<Object>();
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
	public Response<Object> findUseraccount(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Long userAccountId=currentAccount.getId();
		Response<Object> response=new Response<Object>();
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
