package com.brijframework.authorization.account.model.menus;

import com.brijframework.authorization.account.model.UIUserRole;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;

public class UIUserRoleMenuGroup {

	private Long id;

	private String idenNo;

	private UIUserRole userRole;

	private UIViewMenuGroup menuGroup;

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

	public UIViewMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(UIViewMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}
}
