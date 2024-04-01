package com.brijframwork.authorization.model.headers;

import com.brijframwork.authorization.model.EOEntityObject;
import com.brijframwork.authorization.model.EOUserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ROLE_HEADER_ITEM", uniqueConstraints= {@UniqueConstraint(columnNames = { "USER_ROLE_ID","HEADER_ITEM_ID" })})
public class EORoleHeaderItem extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@Column(name = "OWNER_ID", nullable = true)
	private Long ownerId;

	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID", nullable = false)
	private EOUserRole userRole;
	
	@OneToOne
	@JoinColumn(name = "HEADER_ITEM_ID", nullable = false)
	private EOHeaderItem headerItem;

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

	public EOHeaderItem getHeaderItem() {
		return headerItem;
	}

	public void setHeaderItem(EOHeaderItem headerItem) {
		this.headerItem = headerItem;
	}
}
