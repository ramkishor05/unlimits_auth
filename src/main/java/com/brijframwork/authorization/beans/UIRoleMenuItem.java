package com.brijframwork.authorization.beans;

public class UIRoleMenuItem {
	
	private Long id;
	
	private String idenNo;
	
	private UIUserRole userRole;

	private UIMenuItem menuItem;
	
	private UIRoleMenuGroup roleMenuGroup;

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

	public UIMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(UIMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public UIRoleMenuGroup getRoleMenuGroup() {
		return roleMenuGroup;
	}

	public void setRoleMenuGroup(UIRoleMenuGroup roleMenuGroup) {
		this.roleMenuGroup = roleMenuGroup;
	} 
}
