package com.brijframework.authorization.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface UserOnBoardingQuestionMapper extends GenericMapper<EOUserOnBoardingQuestion, UIUserOnBoardingQuestion> {

	@Override
	@Mapping(target = "userAccountId", source = "userAccount.id")
	UIUserOnBoardingQuestion mapToDTO(EOUserOnBoardingQuestion eoRole);
	
	@Mapping(source  = "userAccountId", target  = "userAccount.id")
	@Override
    EOUserOnBoardingQuestion mapToDAO(UIUserOnBoardingQuestion eoRoleDTO);
}
