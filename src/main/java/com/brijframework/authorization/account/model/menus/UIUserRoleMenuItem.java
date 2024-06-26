package com.brijframework.authorization.account.model.menus;

import com.brijframework.authorization.account.model.UIUserRole;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;

public class UIUserRoleMenuItem {
	
	private Long id;
	
	private String idenNo;
	
	private UIUserRole userRole;

	private UIViewMenuItem menuItem;
	
	private UIUserRoleMenuGroup roleMenuGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public UIUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UIUserRole userRole) {
		this.userRole = userRole;
	}

	public UIViewMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(UIViewMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public UIUserRoleMenuGroup getRoleMenuGroup() {
		return roleMenuGroup;
	}

	public void setRoleMenuGroup(UIUserRoleMenuGroup roleMenuGroup) {
		this.roleMenuGroup = roleMenuGroup;
	} 
}
