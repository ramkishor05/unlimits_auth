package com.brijframework.authorization.view.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface ViewMenuGroupMapper extends GenericMapper<EOViewMenuGroup, UIViewMenuGroup> {

}
