package com.brijframework.authorization.global.account.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingAnswer;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingAnswer;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface GlobalUserOnBoardingQuestionMapper extends GenericMapper<EOUserOnBoardingQuestion, UIUserOnBoardingQuestion> {

	@Override
	@Mapping(target = "userAccountId", source = "userAccount.id")
	UIUserOnBoardingQuestion mapToDTO(EOUserOnBoardingQuestion eoRole);
	
	@Mapping(source  = "userAccountId", target  = "userAccount.id")
	@Override
    EOUserOnBoardingQuestion mapToDAO(UIUserOnBoardingQuestion eoRoleDTO);
	
	UIUserOnBoardingAnswer toAnswerDTO(EOUserOnBoardingAnswer eoUserOnBoardingAnswer);
}
