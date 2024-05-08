package com.brijframework.authorization.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.authorization.beans.UIOnBoardingQuestion;
import com.brijframework.authorization.model.onboarding.EOOnBoardingQuestion;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface OnBoardingQuestionMapper extends GenericMapper<EOOnBoardingQuestion, UIOnBoardingQuestion> {

}
