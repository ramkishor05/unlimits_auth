package com.brijframework.authorization.global.account.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuGroup;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface GlobalUserRoleMenuGroupMapper extends GenericMapper<EOUserRoleMenuGroup, UIUserRoleMenuGroup> {

}
