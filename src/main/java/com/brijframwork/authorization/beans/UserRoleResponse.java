package com.brijframwork.authorization.beans;

import java.io.Serializable;
import java.util.List;

public class UserRoleResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private int position;
	
	private String roleName;
	
	private String roleId;
	
	private List<UIMenuGroup> roleMenuGroups;
	
	private List<UIMenuItem> roleMenuItems;
	
	private List<UIHeaderItem> roleHeaderItems;
	
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

	public List<UIMenuGroup> getRoleMenuGroups() {
		return roleMenuGroups;
	}

	public void setRoleMenuGroups(List<UIMenuGroup> roleMenuGroups) {
		this.roleMenuGroups = roleMenuGroups;
	}

	public List<UIMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(List<UIMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	public List<UIHeaderItem> getRoleHeaderItems() {
		return roleHeaderItems;
	}

	public void setRoleHeaderItems(List<UIHeaderItem> roleHeaderItems) {
		this.roleHeaderItems = roleHeaderItems;
	}

}