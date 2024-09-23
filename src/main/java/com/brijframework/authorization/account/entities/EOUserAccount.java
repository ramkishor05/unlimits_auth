
package com.brijframework.authorization.account.entities;

import java.sql.Timestamp;
import java.util.List;

import org.unlimits.rest.crud.beans.IUserAccount;

import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingPage;
import com.brijframework.authorization.entities.EOEntityObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ACCOUNT", uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "USERNAME", "ROLE_ID" }),
		@UniqueConstraint(columnNames = { "ACCOUNT_MOBILE", "ROLE_ID" }) ,
		@UniqueConstraint(columnNames = { "ACCOUNT_EMAIL", "ROLE_ID" }) 
	}
)
public class EOUserAccount extends EOEntityObject implements IUserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;
	
	@Column(name = "ACCOUNT_MOBILE")
	private String registeredMobile;

	@Column(name = "ACCOUNT_EMAIL")
	private String registeredEmail;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "OWNER_ID", nullable = true)
	private Long ownerId;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", unique = false)
	private EOUserRole userRole;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROFILE_ID", unique = false)
	private EOUserProfile userProfile;

	@Column(name = "ON_BOARDING", nullable = true)
	private Boolean onBoarding;

	@Column(name = "RESET_PASSWORD_TOKEN", nullable = true)
	private String resetPasswordToken;

	@Column(name = "RESET_PASSWORD_SENT_AT", nullable = true)
	private Timestamp resetPasswordSentAt;
	
	@OneToMany(mappedBy = "userAccount")
	private List<EOUserAccountService> authServices;

	@OneToMany(mappedBy = "userAccount")
	private List<EOUserOnBoardingMenu> onBoardingMenuList;

	@OneToMany(mappedBy = "userAccount")
	private List<EOUserOnBoardingPage> onBoardingPageList;

	@OneToMany(mappedBy = "userAccount")
	private List<EOUserOnBoardingBilling> onBoardingBillingList;

	@PrePersist
	public void init() {
		onBoarding = true;
		super.init();
	}

	@Override
	public Long getId() {
		return super.getId();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegisteredMobile() {
		return registeredMobile;
	}

	public void setRegisteredMobile(String registeredMobile) {
		this.registeredMobile = registeredMobile;
	}

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public EOUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EOUserRole userRole) {
		this.userRole = userRole;
	}

	public EOUserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(EOUserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Boolean getOnBoarding() {
		return onBoarding;
	}

	public void setOnBoarding(Boolean onBoarding) {
		this.onBoarding = onBoarding;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Timestamp getResetPasswordSentAt() {
		return resetPasswordSentAt;
	}

	public void setResetPasswordSentAt(Timestamp resetPasswordSentAt) {
		this.resetPasswordSentAt = resetPasswordSentAt;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public List<EOUserOnBoardingMenu> getOnBoardingMenuList() {
		return onBoardingMenuList;
	}

	public void setOnBoardingMenuList(List<EOUserOnBoardingMenu> onBoardingMenuList) {
		this.onBoardingMenuList = onBoardingMenuList;
	}

	public List<EOUserOnBoardingPage> getOnBoardingPageList() {
		return onBoardingPageList;
	}

	public void setOnBoardingPageList(List<EOUserOnBoardingPage> onBoardingPageList) {
		this.onBoardingPageList = onBoardingPageList;
	}

	public List<EOUserOnBoardingBilling> getOnBoardingBillingList() {
		return onBoardingBillingList;
	}

	public void setOnBoardingBillingList(List<EOUserOnBoardingBilling> onBoardingBillingList) {
		this.onBoardingBillingList = onBoardingBillingList;
	}

	@Override
	public String toString() {
		return "EOUserLogin [id=" + getId() + ", username=" + username + ", password=" + password + ", accountName="
				+ accountName + ", type=" + type + ", userRole=" + userRole + ", userProfile=" + userProfile + "]";
	}

}
