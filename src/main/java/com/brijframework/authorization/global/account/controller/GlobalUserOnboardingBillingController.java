package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;
import com.brijframework.authorization.global.account.service.UserOnBoardingBillingService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/user/onboarding/billing")
@CrossOrigin("*")
@Hidden
public class GlobalUserOnboardingBillingController implements CrudController<UIUserOnBoardingBilling, EOUserOnBoardingBilling, Long> {

	@Autowired
	private UserOnBoardingBillingService onBoardingBillingService; 
	
	@Override
	public CrudService<UIUserOnBoardingBilling, EOUserOnBoardingBilling, Long> getService() {
		return onBoardingBillingService;
	}
}
