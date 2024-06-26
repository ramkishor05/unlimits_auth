
package com.brijframework.authorization.account.entities.menus;

import java.util.List;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.entities.EOEntityObject;
import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLE_MENU_GROUP", uniqueConstraints = {
		@UniqueConstraint(name="USER_ROLE_MENU_GROUP_UNIQUE" , columnNames = { "USER_ROLE_ID", "MENU_GROUP_ID"}) })
public class EOUserRoleMenuGroup extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "IDEN_NO")
	private String idenNo;

	@ManyToOne
	@JoinColumn(name = "USER_ROLE_ID", nullable = false, unique = false )
	private EOUserRole userRole;

	@ManyToOne
	@JoinColumn(name = "MENU_GROUP_ID", nullable = false, unique = false)
	private EOViewMenuGroup menuGroup;

	@OneToMany(mappedBy = "roleMenuGroup", cascade = CascadeType.ALL)
	private List<EOUserRoleMenuItem> roleMenuItems;
		
	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOViewMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(EOViewMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<EOUserRoleMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(List<EOUserRoleMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	@Override
	public String toString() {
		return "EORoleMenuGroup [id=" + getId() + ", userRole=" + userRole + ", menuGroup=" + menuGroup + "";
	}

	
}
