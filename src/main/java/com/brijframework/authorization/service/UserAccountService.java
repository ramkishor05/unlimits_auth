package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brijframework.authorization.beans.PasswordReset;
import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.beans.UIUserProfile;
import com.brijframework.authorization.beans.UserDetailResponse;
import com.brijframework.authorization.constant.Authority;

public interface UserAccountService extends UserDetailsService {

	boolean register(UIUserAccount userDetailRequest, Authority authority);
	
	boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);

	UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount);

	UIUserProfile getUserProfile(Long id);

	List<UserDetailResponse> getUsers();

	boolean updateOnboarding(Long id, boolean onboarding, String idenNo);
	
	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

	UIUserAccount resetPassword(PasswordReset passwordReset);

	UIUserAccount saveOtp(PasswordReset passwordReset);

	
}
