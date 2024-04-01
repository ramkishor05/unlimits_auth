package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UIUserRole;

public interface CustRoleService {

	UIUserRole addUserRole(UIUserRole uiUserRole);

	UIUserRole updateUserRole(UIUserRole uiUserRole);

	Boolean deleteUserRole(Long id);

	UIUserRole getUserRole(Long id);

	List<UIUserRole> getUserRoleList();
	
	List<UIUserRole> getUserRoleList(String type);


}
