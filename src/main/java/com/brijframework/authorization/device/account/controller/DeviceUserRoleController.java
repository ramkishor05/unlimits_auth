package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.service.UserRoleService;
import com.brijframework.authorization.constant.Authority;

@RestController
@RequestMapping("/api/device/user/role")
public class DeviceUserRoleController{
	
	@Autowired
    private UserRoleService userRolelService;

	@GetMapping
	public Response<Object> findAll(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
		Response<Object> response=new Response<Object>();
		try {
			response.setData(userRolelService.getUserRoleListByPositions(Arrays.asList(Authority.USER.getPosition())));
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
