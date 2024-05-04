package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIRoleMenuItem;
import com.brijframework.authorization.mapper.RoleMenuItemMapper;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.repository.RoleMenuItemRepository;

@Service
public class RoleMenuItemServiceImpl implements RoleMenuItemService {
	
	@Autowired
	private RoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private RoleMenuItemMapper roleMenuItemMapper;

	@Override
	public UIRoleMenuItem addRoleMenuItem(UIRoleMenuItem uiRoleMenuItem) {
		EORoleMenuItem eoRoleMenuItem = roleMenuItemMapper.mapToDAO(uiRoleMenuItem);
		eoRoleMenuItem=roleMenuItemRepository.save(eoRoleMenuItem);
		return roleMenuItemMapper.mapToDTO(eoRoleMenuItem);
	}

	@Override
	public UIRoleMenuItem updateRoleMenuItem(UIRoleMenuItem uiRoleMenuItem) {
		EORoleMenuItem eoRoleMenuItem = roleMenuItemMapper.mapToDAO(uiRoleMenuItem);
		eoRoleMenuItem=roleMenuItemRepository.save(eoRoleMenuItem);
		return roleMenuItemMapper.mapToDTO(eoRoleMenuItem);
	}

	@Override
	public boolean deleteRoleMenuItem(Long id) {
		roleMenuItemRepository.deleteById(id);
		return true;
	}

	@Override
	public UIRoleMenuItem getRoleMenuItem(Long id) {
		return roleMenuItemMapper.mapToDTO(roleMenuItemRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIRoleMenuItem> getRoleMenuItemList() {
		return roleMenuItemMapper.mapToDTO(roleMenuItemRepository.findAll());
	}

	@Override
	public List<UIRoleMenuItem> getRoleMenuItemList(String type) {
		return roleMenuItemMapper.mapToDTO(roleMenuItemRepository.findAllByType(type));
	}

}
