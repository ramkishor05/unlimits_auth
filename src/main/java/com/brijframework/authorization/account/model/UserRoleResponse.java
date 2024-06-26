package com.brijframework.authorization.account.model;

import java.io.Serializable;
import java.util.List;

import com.brijframework.authorization.view.model.menus.UIViewHeaderItem;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;

public class UserRoleResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private int position;
	
	private String roleName;
	
	private String roleId;
	
	private List<UIViewMenuGroup> roleMenuGroups;
	
	private List<UIViewMenuItem> roleMenuItems;
	
	private List<UIViewHeaderItem> roleHeaderItems;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<UIViewMenuGroup> getRoleMenuGroups() {
		return roleMenuGroups;
	}

	public void setRoleMenuGroups(List<UIViewMenuGroup> roleMenuGroups) {
		this.roleMenuGroups = roleMenuGroups;
	}

	public List<UIViewMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(List<UIViewMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	public List<UIViewHeaderItem> getRoleHeaderItems() {
		return roleHeaderItems;
	}

	public void setRoleHeaderItems(List<UIViewHeaderItem> roleHeaderItems) {
		this.roleHeaderItems = roleHeaderItems;
	}

}