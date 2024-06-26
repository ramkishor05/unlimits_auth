package com.brijframework.authorization.view.entities.menus;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import com.brijframework.authorization.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = VIEW_MENU_ITEM, uniqueConstraints = {@UniqueConstraint(columnNames = { "IDEN_NO" }) })
public class EOViewMenuItem extends EOEntityObject {

	private static final long serialVersionUID = 1L;

	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "URL")
	private String url;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ICON")
	private String icon;
		
	@Column(name = "ON_BOARDING", nullable = true)
	private Boolean onBoarding;
	
	@ManyToOne
	@JoinColumn(name = "MENU_GROUP_ID")
	private EOViewMenuGroup  menuGroup;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
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

	public EOViewMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(EOViewMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	@Override
	public String toString() {
		return "EOMenuItem [id=" + getId() + ", idenNo=" + idenNo + ", title=" + title + ", url=" + url + ", type=" + type
				+ ", icon=" + icon + ", orderSequence=" + getOrderSequence() + ", menuGroup=" + menuGroup + "]";
	}
}
