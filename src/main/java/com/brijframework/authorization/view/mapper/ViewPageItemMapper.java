package com.brijframework.authorization.view.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.view.entities.pages.EOViewPageItem;
import com.brijframework.authorization.view.model.pages.UIViewPageItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface ViewPageItemMapper extends GenericMapper<EOViewPageItem, UIViewPageItem> {

}
