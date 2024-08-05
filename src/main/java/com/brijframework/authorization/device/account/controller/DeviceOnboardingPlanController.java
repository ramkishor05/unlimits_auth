package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.exceptions.UnauthorizedAccessException;
import com.brijframework.authorization.view.service.ViewOnBoardingBillingService;

@RestController
@RequestMapping("/api/device/onboarding/plans")
@CrossOrigin("*")
public class DeviceOnboardingPlanController {

	@Autowired
	private ViewOnBoardingBillingService viewOnBoardingBillingService; 
	
	@GetMapping
	public Response<Object> findAllByUser(@RequestHeader(required =false)  MultiValueMap<String,String> headers, WebRequest webRequest){
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Map<String, Object> filters=new HashMap<String, Object>();
		webRequest.getParameterMap().forEach((key,values)->{
			filters.put(key, values[0]);
		});
		Response<Object> response=new Response<Object>();
		try {
			response.setData(viewOnBoardingBillingService.findAll(headers, filters));
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
