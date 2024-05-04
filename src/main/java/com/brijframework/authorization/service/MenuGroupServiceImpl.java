package com.brijframework.authorization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIMenuGroup;
import com.brijframework.authorization.beans.UIMenuItem;
import com.brijframework.authorization.mapper.MenuGroupMapper;
import com.brijframework.authorization.mapper.MenuItemMapper;
import com.brijframework.authorization.model.menus.EOMenuGroup;
import com.brijframework.authorization.model.menus.EORoleMenuGroup;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.repository.MenuGroupRepository;
import com.brijframework.authorization.repository.RoleMenuGroupRepository;

@Service
public class MenuGroupServiceImpl implements MenuGroupService {
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private MenuGroupRepository menuGroupRepository;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;
	
	@Autowired
	private MenuItemMapper menuItemMapper;

	@Override
	public UIMenuGroup addMenuGroup(UIMenuGroup uiMenuGroup) {
		EOMenuGroup eoMenuGroup = menuGroupMapper.mapToDAO(uiMenuGroup);
		eoMenuGroup=menuGroupRepository.save(eoMenuGroup);
		
		return menuGroupMapper.mapToDTO(eoMenuGroup);
	}

	@Override
	public UIMenuGroup updateMenuGroup(UIMenuGroup uiMenuGroup) {
		EOMenuGroup eoMenuGroup = menuGroupMapper.mapToDAO(uiMenuGroup);
		eoMenuGroup=menuGroupRepository.save(eoMenuGroup);
		return menuGroupMapper.mapToDTO(eoMenuGroup);
	}

	@Override
	public boolean deleteMenuGroup(Long id) {
		menuGroupRepository.deleteById(id);
		return true;
	}

	@Override
	public UIMenuGroup getMenuGroup(Long id) {
		return menuGroupMapper.mapToDTO(menuGroupRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList() {
		return menuGroupMapper.mapToDTO(menuGroupRepository.findAll());
	}

	@Override
	public List<UIMenuGroup> getMenuGroupList(String type) {
		return menuGroupMapper.mapToDTO(menuGroupRepository.findAllByType(type));
	}
	
	@Override
	public List<UIMenuGroup> getMenuGroupListByRole(Long roleId) {
		List<EORoleMenuGroup> eoRoleMenuGroups = roleMenuGroupRepository.findAllByRoleId(roleId);
		List<UIMenuGroup> uiMenuGroups=new ArrayList<UIMenuGroup>();
		for(EORoleMenuGroup eoRoleMenuGroup: eoRoleMenuGroups) {
			UIMenuGroup uiMenuGroup = menuGroupMapper.mapToDTO(eoRoleMenuGroup.getMenuGroup());
			for(EORoleMenuItem eoRoleMenuItem: eoRoleMenuGroup.getRoleMenuItems()) {
				UIMenuItem uiMenuItem = menuItemMapper.mapToDTO(eoRoleMenuItem.getMenuItem());
				uiMenuItem.setHomePage(eoRoleMenuItem.isHomePage());
				uiMenuGroup.getMenuItems().add(uiMenuItem);
			}
			uiMenuGroup.getMenuItems().sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
			uiMenuGroups.add(uiMenuGroup);
		}
		uiMenuGroups.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));

		return uiMenuGroups;
	}
}
