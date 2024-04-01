package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIMenuGroup;
import com.brijframwork.authorization.model.menus.EOMenuGroup;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface MenuGroupMapper extends GenericMapper<EOMenuGroup, UIMenuGroup> {

}
