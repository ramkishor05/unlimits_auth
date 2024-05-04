package com.brijframework.authorization.service;

import java.util.List;

import com.brijframework.authorization.beans.UIUserRole;

public interface UserRoleService {

	UIUserRole addUserRole(UIUserRole uiUserRole);

	UIUserRole updateUserRole(UIUserRole uiUserRole);

	Boolean deleteUserRole(Long id);

	UIUserRole getUserRole(Long id);

	List<UIUserRole> getUserRoleList();
	
	List<UIUserRole> getUserRoleList(String type);


}
