package com.brijframework.authorization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_PROFILE")
public class EOUserProfile extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "PREFERRED_NAME")
	private String preferredName;
	
	@Column(name = "PIC_URL")
	@Lob
	private String pictureURL;

	@OneToOne(mappedBy = "userProfile")
	private EOUserAccount userAccount;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
