package com.brijframework.authorization.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingBilling;
import com.brijframework.authorization.view.mapper.ViewOnBoardingBillingMapper;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingBilling;
import com.brijframework.authorization.view.repository.ViewOnBoardingBillingRepository;

@Service
public class ViewOnBoardingBillingServiceImpl extends CrudServiceImpl<UIViewOnBoardingBilling, EOViewOnBoardingBilling, Long> implements ViewOnBoardingBillingService {

	@Autowired
	private ViewOnBoardingBillingRepository onBoardingBillingRepository;
	
	@Autowired
	private ViewOnBoardingBillingMapper onBoardingBillingMapper;
	
	@Override
	public JpaRepository<EOViewOnBoardingBilling, Long> getRepository() {
		return onBoardingBillingRepository;
	}

	@Override
	public GenericMapper<EOViewOnBoardingBilling, UIViewOnBoardingBilling> getMapper() {
		return onBoardingBillingMapper;
	}

}
