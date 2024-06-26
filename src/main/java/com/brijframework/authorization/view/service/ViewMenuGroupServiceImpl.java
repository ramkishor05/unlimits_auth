package com.brijframework.authorization.view.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.repository.UserRoleMenuGroupRepository;
import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.mapper.ViewMenuGroupMapper;
import com.brijframework.authorization.view.mapper.ViewMenuItemMapper;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;
import com.brijframework.authorization.view.repository.ViewMenuGroupRepository;

@Service
public class ViewMenuGroupServiceImpl extends CrudServiceImpl<UIViewMenuGroup, EOViewMenuGroup, Long> implements ViewMenuGroupService {
	
	@Autowired
	private UserRoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private ViewMenuGroupRepository menuGroupRepository;
	
	@Autowired
	private ViewMenuGroupMapper menuGroupMapper;
	
	@Autowired
	private ViewMenuItemMapper menuItemMapper;
	
	@Override
	public JpaRepository<EOViewMenuGroup, Long> getRepository() {
		return menuGroupRepository;
	}

	@Override
	public GenericMapper<EOViewMenuGroup, UIViewMenuGroup> getMapper() {
		return menuGroupMapper;
	}

	@Override
	public List<UIViewMenuGroup> getMenuGroupListByRole(Long roleId) {
		List<EOUserRoleMenuGroup> eoRoleMenuGroups = roleMenuGroupRepository.findAllByRoleId(roleId);
		List<UIViewMenuGroup> uiMenuGroups=new ArrayList<UIViewMenuGroup>();
		for(EOUserRoleMenuGroup eoRoleMenuGroup: eoRoleMenuGroups) {
			UIViewMenuGroup uiMenuGroup = menuGroupMapper.mapToDTO(eoRoleMenuGroup.getMenuGroup());
			for(EOUserRoleMenuItem eoRoleMenuItem: eoRoleMenuGroup.getRoleMenuItems()) {
				UIViewMenuItem uiMenuItem = menuItemMapper.mapToDTO(eoRoleMenuItem.getMenuItem());
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
