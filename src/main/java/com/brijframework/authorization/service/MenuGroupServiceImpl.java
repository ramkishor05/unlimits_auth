package com.brijframework.authorization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

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
public class MenuGroupServiceImpl extends CrudServiceImpl<UIMenuGroup, EOMenuGroup, Long> implements MenuGroupService {
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private MenuGroupRepository menuGroupRepository;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;
	
	@Autowired
	private MenuItemMapper menuItemMapper;
	
	@Override
	public JpaRepository<EOMenuGroup, Long> getRepository() {
		return menuGroupRepository;
	}

	@Override
	public GenericMapper<EOMenuGroup, UIMenuGroup> getMapper() {
		return menuGroupMapper;
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
