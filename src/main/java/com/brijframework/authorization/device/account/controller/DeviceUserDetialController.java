package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.service.UserAccountService;
import com.brijframework.authorization.exceptions.UnauthorizedAccessException;

@RestController
@RequestMapping("/api/device/user/detail")
@CrossOrigin("*")
public class DeviceUserDetialController {

	@Autowired
    private UserAccountService userDetailService;
	
	@GetMapping("/profile")
	public Response updateUserProfile(){
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Response response=new Response();
		try {
			response.setData(userDetailService.getUserProfile(currentAccount));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@PutMapping("/profile")
	public Response updateUserProfile(@RequestBody UIUserProfile uiUserProfile){
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		Response response=new Response();
		try {
			response.setData(userDetailService.updateUserProfile(currentAccount, uiUserProfile));
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
