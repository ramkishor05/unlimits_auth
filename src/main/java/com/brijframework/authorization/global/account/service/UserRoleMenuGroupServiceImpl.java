package com.brijframework.authorization.global.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuGroup;
import com.brijframework.authorization.account.repository.UserRoleMenuGroupRepository;
import com.brijframework.authorization.global.account.mapper.GlobalUserRoleMenuGroupMapper;

@Service
public class UserRoleMenuGroupServiceImpl extends CrudServiceImpl<UIUserRoleMenuGroup, EOUserRoleMenuGroup, Long> implements UserRoleMenuGroupService {
	
	@Autowired
	private UserRoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private GlobalUserRoleMenuGroupMapper roleMenuGroupMapper;

	@Override
	public JpaRepository<EOUserRoleMenuGroup, Long> getRepository() {
		return roleMenuGroupRepository;
	}

	@Override
	public GenericMapper<EOUserRoleMenuGroup, UIUserRoleMenuGroup> getMapper() {
		return roleMenuGroupMapper;
	}

	
}
