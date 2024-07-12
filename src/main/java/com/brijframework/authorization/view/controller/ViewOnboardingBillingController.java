package com.brijframework.authorization.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingBilling;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingBilling;
import com.brijframework.authorization.view.service.ViewOnBoardingBillingService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/onboarding/billing")
@CrossOrigin("*")
@Hidden
public class ViewOnboardingBillingController implements CrudController<UIViewOnBoardingBilling, EOViewOnBoardingBilling, Long> {

	@Autowired
	private ViewOnBoardingBillingService onBoardingBillingService; 
	
	@Override
	public CrudService<UIViewOnBoardingBilling, EOViewOnBoardingBilling, Long> getService() {
		return onBoardingBillingService;
	}
}
