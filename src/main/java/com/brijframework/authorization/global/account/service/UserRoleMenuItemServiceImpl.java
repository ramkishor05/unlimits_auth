package com.brijframework.authorization.global.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuItem;
import com.brijframework.authorization.account.repository.UserRoleMenuItemRepository;
import com.brijframework.authorization.global.account.mapper.GlobalUserRoleMenuItemMapper;

@Service
public class UserRoleMenuItemServiceImpl extends CrudServiceImpl<UIUserRoleMenuItem, EOUserRoleMenuItem, Long> implements UserRoleMenuItemService {
	
	@Autowired
	private UserRoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private GlobalUserRoleMenuItemMapper roleMenuItemMapper;

	@Override
	public List<UIUserRoleMenuItem> getRoleMenuItemList(String type) {
		return roleMenuItemMapper.mapToDTO(roleMenuItemRepository.findAllByType(type));
	}

	@Override
	public JpaRepository<EOUserRoleMenuItem, Long> getRepository() {
		return roleMenuItemRepository;
	}

	@Override
	public GenericMapper<EOUserRoleMenuItem, UIUserRoleMenuItem> getMapper() {
		return roleMenuItemMapper;
	}

}
