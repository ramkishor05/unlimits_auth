package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIRoleMenuItem;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface RoleMenuItemMapper extends GenericMapper<EORoleMenuItem, UIRoleMenuItem> {

}
