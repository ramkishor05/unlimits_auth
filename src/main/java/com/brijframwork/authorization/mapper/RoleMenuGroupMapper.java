package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIRoleMenuGroup;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface RoleMenuGroupMapper extends GenericMapper<EORoleMenuGroup, UIRoleMenuGroup> {

}
