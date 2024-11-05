package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.model.UIGlobalDashboard;
import com.brijframework.authorization.global.account.service.GlobalDashboardService;

/**
 *  @author ram kishor
 */
@RestController
@RequestMapping(value = "/api/global/dashboard")
@CrossOrigin("*")
public class GlobalDashboardController {
	
	/**
	 * 
	 */
	public static final String SUCCESSFULLY_PROCCEED = "Successfully procceed";
	/**
	 * 
	 */
	public static final String FAILED = "0";
	/**
	 * 
	 */
	public static final String SUCCESS = "1";
	
	@Autowired
	private GlobalDashboardService globalDashboardService;
	
	@GetMapping
	public Response<UIGlobalDashboard> getGlobalClientDashboard() {
		Response<UIGlobalDashboard> response=new Response<UIGlobalDashboard>();
		try {
			response.setData(globalDashboardService.getDashboard());
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

}
