package com.brijframework.authorization.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframework.authorization.beans.UIRoleMenuItem;
import com.brijframework.authorization.model.menus.EORoleMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface RoleMenuItemMapper extends GenericMapper<EORoleMenuItem, UIRoleMenuItem> {

}
