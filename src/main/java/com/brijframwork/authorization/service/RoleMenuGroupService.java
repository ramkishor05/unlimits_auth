package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UIRoleMenuGroup;

public interface RoleMenuGroupService {

	UIRoleMenuGroup addRoleMenuGroup(UIRoleMenuGroup uiRoleMenuGroup);

	UIRoleMenuGroup updateRoleMenuGroup(UIRoleMenuGroup uiRoleMenuGroup);

	boolean deleteRoleMenuGroup(Long id);

	UIRoleMenuGroup getRoleMenuGroup(Long id);

	List<UIRoleMenuGroup> getRoleMenuGroupList();

}
