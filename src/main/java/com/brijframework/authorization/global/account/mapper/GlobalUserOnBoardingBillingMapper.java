package com.brijframework.authorization.global.account.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface GlobalUserOnBoardingBillingMapper extends GenericMapper<EOUserOnBoardingBilling, UIUserOnBoardingBilling> {

	@Override
	@Mapping(target = "userAccountId", source = "userAccount.id")
	UIUserOnBoardingBilling mapToDTO(EOUserOnBoardingBilling eoRole);
	
	@Mapping(source  = "userAccountId", target  = "userAccount.id")
	@Override
    EOUserOnBoardingBilling mapToDAO(UIUserOnBoardingBilling eoRoleDTO);
}
