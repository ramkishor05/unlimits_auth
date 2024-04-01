package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UIRoleMenuItem;

public interface RoleMenuItemService {

	UIRoleMenuItem addRoleMenuItem(UIRoleMenuItem uiRoleMenuItem);

	UIRoleMenuItem updateRoleMenuItem(UIRoleMenuItem uiRoleMenuItem);

	boolean deleteRoleMenuItem(Long id);

	UIRoleMenuItem getRoleMenuItem(Long id);

	List<UIRoleMenuItem> getRoleMenuItemList();

	List<UIRoleMenuItem> getRoleMenuItemList(String type);

}
