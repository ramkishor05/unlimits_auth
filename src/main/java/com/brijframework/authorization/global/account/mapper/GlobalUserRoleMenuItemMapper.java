package com.brijframework.authorization.global.account.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface GlobalUserRoleMenuItemMapper extends GenericMapper<EOUserRoleMenuItem, UIUserRoleMenuItem> {

}
