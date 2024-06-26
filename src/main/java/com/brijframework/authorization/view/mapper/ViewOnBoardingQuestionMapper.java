package com.brijframework.authorization.view.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingQuestion;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface ViewOnBoardingQuestionMapper extends GenericMapper<EOViewOnBoardingQuestion, UIViewOnBoardingQuestion> {

}
