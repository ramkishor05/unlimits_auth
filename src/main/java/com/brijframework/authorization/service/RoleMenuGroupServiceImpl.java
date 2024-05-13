package com.brijframework.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.beans.UIRoleMenuGroup;
import com.brijframework.authorization.mapper.RoleMenuGroupMapper;
import com.brijframework.authorization.model.menus.EORoleMenuGroup;
import com.brijframework.authorization.repository.RoleMenuGroupRepository;

@Service
public class RoleMenuGroupServiceImpl extends CrudServiceImpl<UIRoleMenuGroup, EORoleMenuGroup, Long> implements RoleMenuGroupService {
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private RoleMenuGroupMapper roleMenuGroupMapper;

	@Override
	public JpaRepository<EORoleMenuGroup, Long> getRepository() {
		return roleMenuGroupRepository;
	}

	@Override
	public GenericMapper<EORoleMenuGroup, UIRoleMenuGroup> getMapper() {
		return roleMenuGroupMapper;
	}

	
}
