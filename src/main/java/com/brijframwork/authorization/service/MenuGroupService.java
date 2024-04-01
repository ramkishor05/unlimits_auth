package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UIMenuGroup;

public interface MenuGroupService {

	UIMenuGroup addMenuGroup(UIMenuGroup uiMenuGroup);

	UIMenuGroup updateMenuGroup(UIMenuGroup uiMenuGroup);

	boolean deleteMenuGroup(Long id);

	UIMenuGroup getMenuGroup(Long id);

	List<UIMenuGroup> getMenuGroupList();

	List<UIMenuGroup> getMenuGroupList(String type);

	List<UIMenuGroup> getMenuGroupListByRole(Long roleId);
}
