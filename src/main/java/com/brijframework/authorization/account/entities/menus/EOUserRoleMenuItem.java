
package com.brijframework.authorization.account.entities.menus;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_MENU_ITEM_UNIQUE;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_MENU_ITEM;

import java.util.List;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;
import com.brijframework.authorization.entities.EOEntityObject;
import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = USER_ROLE_MENU_ITEM, uniqueConstraints= {
		@UniqueConstraint(name=USER_ROLE_MENU_ITEM_UNIQUE , columnNames = { "USER_ROLE_ID","MENU_ITEM_ID"})})
public class EOUserRoleMenuItem extends EOEntityObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@Column(name = "OWNER_ID", nullable = true)
	private Long ownerId;

	@ManyToOne
	@JoinColumn(name = "USER_ROLE_ID", nullable = false, unique = false)
	private EOUserRole userRole;

	@ManyToOne
	@JoinColumn(name = "MENU_ITEM_ID", nullable = false, unique = false)
	private EOViewMenuItem menuItem;
	
	@ManyToOne
	@JoinColumn(name = "MENU_GROUP_ID", nullable = false, unique = false)
	private EOUserRoleMenuGroup  roleMenuGroup;
	
	@Column(name = "IS_HOME_PAGE")
	private boolean homePage=false;

	@Column(name = "ON_BOARDING", nullable = true)
	private Boolean onBoarding;
	
	@Column(name = "ON_BOARDING_LEVEL", nullable = true)
	private Integer onBoardingLevel;
	
	@OneToMany(mappedBy = "roleMenuItem", cascade = CascadeType.ALL)
	private List<EOUserOnBoardingMenu> userOnBoardingList;
	
	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOViewMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(EOViewMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public EOUserRoleMenuGroup getRoleMenuGroup() {
		return roleMenuGroup;
	}

	public void setRoleMenuGroup(EOUserRoleMenuGroup roleMenuGroup) {
		this.roleMenuGroup = roleMenuGroup;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public Boolean getOnBoarding() {
		if(onBoarding==null) {
			return false;
		}
		return onBoarding;
	}

	public void setOnBoarding(Boolean onBoarding) {
		this.onBoarding = onBoarding;
	}

	public Integer getOnBoardingLevel() {
		return onBoardingLevel;
	}

	public void setOnBoardingLevel(Integer onBoardingLevel) {
		this.onBoardingLevel = onBoardingLevel;
	}

	public List<EOUserOnBoardingMenu> getUserOnBoardingList() {
		return userOnBoardingList;
	}

	public void setUserOnBoardingList(List<EOUserOnBoardingMenu> userOnBoardingList) {
		this.userOnBoardingList = userOnBoardingList;
	}

	@Override
	public String toString() {
		return "EORoleMenuItem [idenNo=" + idenNo + ", ownerId=" + ownerId + ", userRole=" + userRole + ", menuItem="
				+ menuItem + ", roleMenuGroup=" + roleMenuGroup + ", homePage=" + homePage + ", onBoarding="
				+ onBoarding + ", onBoardingLevel=" + onBoardingLevel + ", userOnBoardingList=" + userOnBoardingList
				+ "]";
	}

	
	
}
