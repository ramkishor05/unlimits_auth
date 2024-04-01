package com.brijframwork.authorization.mapper;

import static com.brijframwork.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframwork.authorization.contants.Constants.SPRING;

import org.mapstruct.Mapper;

import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.model.menus.EOMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface MenuItemMapper extends GenericMapper<EOMenuItem, UIMenuItem> {

}
