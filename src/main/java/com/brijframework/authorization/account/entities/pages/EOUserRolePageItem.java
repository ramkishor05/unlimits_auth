
package com.brijframework.authorization.account.entities.pages;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_PAGE_ITEM;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_PAGE_ITEM_UNIQUE;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.entities.EOEntityObject;
import com.brijframework.authorization.view.entities.pages.EOViewPageItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = USER_ROLE_PAGE_ITEM, uniqueConstraints= {
		@UniqueConstraint(name=USER_ROLE_PAGE_ITEM_UNIQUE , columnNames = { "USER_ROLE_ID","PAGE_ITEM_ID"})})
public class EOUserRolePageItem extends EOEntityObject {


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
	@JoinColumn(name = "PAGE_ITEM_ID", nullable = false, unique = false)
	private EOViewPageItem pageItem;
	
	@ManyToOne
	@JoinColumn(name = "PAGE_GROUP_ID", nullable = false, unique = false)
	private EOUserRolePageGroup  rolePageGroup;
	
	@Column(name = "IS_HOME_PAGE")
	private boolean homePage=false;

	@Column(name = "ON_BOARDING", nullable = true)
	private Boolean onBoarding;
	
	@Column(name = "ON_BOARDING_LEVEL", nullable = true)
	private Integer onBoardingLevel;

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

	public EOViewPageItem getPageItem() {
		return pageItem;
	}

	public void setPageItem(EOViewPageItem pageItem) {
		this.pageItem = pageItem;
	}

	public EOUserRolePageGroup getRolePageGroup() {
		return rolePageGroup;
	}

	public void setRolePageGroup(EOUserRolePageGroup rolePageGroup) {
		this.rolePageGroup = rolePageGroup;
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


	@Override
	public String toString() {
		return "EORolePageItem [idenNo=" + idenNo + ", ownerId=" + ownerId + ", userRole=" + userRole + ", pageItem="
				+ pageItem + ", rolePageGroup=" + rolePageGroup + ", homePage=" + homePage + ", onBoarding="
				+ onBoarding + ", onBoardingLevel=" + onBoardingLevel
				+ "]";
	}

	
	
}
