package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;
import com.brijframework.authorization.context.ApiSecurityContext;
import com.brijframework.authorization.exceptions.UnauthorizedAccessException;
import com.brijframework.authorization.global.account.service.UserOnBoardingBillingService;

@RestController
@RequestMapping("/api/device/user/billing")
@CrossOrigin("*")
public class DeviceUserBillingController {

	@Autowired
	private UserOnBoardingBillingService userOnBoardingBillingService; 
	
	@PostMapping
	public Response add(@RequestBody UIUserOnBoardingBilling dto, @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		EOUserAccount currentAccount = ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		dto.setUserAccountId(currentAccount.getId());
		Response response=new Response();
		try {
			response.setData(userOnBoardingBillingService.update(dto,headers));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@PutMapping
	public Response update(@RequestBody UIUserOnBoardingBilling dto, @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		EOUserAccount currentAccount = ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		dto.setUserAccountId(currentAccount.getId());
		Response response=new Response();
		try {
			response.setData(userOnBoardingBillingService.update(dto,headers));
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
		EOUserAccount currentAccount = ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Long userAccountId=currentAccount.getId();
		Response response=new Response();
		try {
			response.setData(userOnBoardingBillingService.findAllByUserAccountId(userAccountId));
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
