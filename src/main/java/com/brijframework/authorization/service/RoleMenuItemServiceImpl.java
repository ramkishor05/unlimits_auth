package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.global.beans.UIRoleMenuItem;
import com.brijframework.authorization.mapper.RoleMenuItemMapper;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.repository.RoleMenuItemRepository;

@Service
public class RoleMenuItemServiceImpl extends CrudServiceImpl<UIRoleMenuItem, EORoleMenuItem, Long> implements RoleMenuItemService {
	
	@Autowired
	private RoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private RoleMenuItemMapper roleMenuItemMapper;

	@Override
	public List<UIRoleMenuItem> getRoleMenuItemList(String type) {
		return roleMenuItemMapper.mapToDTO(roleMenuItemRepository.findAllByType(type));
	}

	@Override
	public JpaRepository<EORoleMenuItem, Long> getRepository() {
		return roleMenuItemRepository;
	}

	@Override
	public GenericMapper<EORoleMenuItem, UIRoleMenuItem> getMapper() {
		return roleMenuItemMapper;
	}

}
