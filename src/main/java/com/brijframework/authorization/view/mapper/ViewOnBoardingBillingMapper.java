package com.brijframework.authorization.view.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingBilling;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingBilling;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface ViewOnBoardingBillingMapper extends GenericMapper<EOViewOnBoardingBilling, UIViewOnBoardingBilling>{

}
