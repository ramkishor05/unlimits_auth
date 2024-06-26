package com.brijframework.authorization.account.entities.headers;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.entities.EOEntityObject;
import com.brijframework.authorization.view.entities.headers.EOViewHeaderItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLE_HEADER_ITEM", uniqueConstraints= {
		@UniqueConstraint(name="USER_ROLE_HEADER_ITEM_UNIQUE", columnNames = { "USER_ROLE_ID","HEADER_ITEM_ID"})})
public class EOUserRoleHeaderItem extends EOEntityObject {

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
	@JoinColumn(name = "HEADER_ITEM_ID", nullable = false, unique = false)
	private EOViewHeaderItem headerItem;

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

	public EOViewHeaderItem getHeaderItem() {
		return headerItem;
	}

	public void setHeaderItem(EOViewHeaderItem headerItem) {
		this.headerItem = headerItem;
	}

	@Override
	public String toString() {
		return "EORoleHeaderItem [idenNo=" + idenNo + ", ownerId=" + ownerId + ", userRole=" + userRole
				+ ", headerItem=" + headerItem + "]";
	}
	
	
}
